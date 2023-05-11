
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.messages.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int OfferId;
		Offer Offer;

		OfferId = super.getRequest().getData("id", int.class);
		Offer = this.repository.findOfferById(OfferId);
		status = Offer != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "startDate", "endDate", "price", "link");

	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("heading")) {

			boolean status;
			String message;

			message = object.getHeading();
			status = this.configuration.hasSpam(message);

			super.state(!status, "heading", "administrator.Offer.error.spam");

		}

		if (!super.getBuffer().getErrors().hasErrors("summary")) {

			boolean status;
			String message;

			message = object.getSummary();
			status = this.configuration.hasSpam(message);

			super.state(!status, "summary", "administrator.Offer.error.spam");

		}
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "startDate", "endDate", "price", "link");

		super.getResponse().setData(tuple);
	}

}
