
package acme.features.administrator.offer;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.configuration.Configuration;
import acme.entities.messages.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object = new Offer();
		object.setInstantiationMoment(moment);

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

			super.state(!status, "heading", "administrator.offer.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("summary")) {
			boolean status;
			String message;

			message = object.getSummary();
			status = this.configuration.hasSpam(message);

			super.state(!status, "summary", "administrator.offer.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			Configuration configuration;

			configuration = this.repository.findConfiguration();

			super.state(Arrays.asList(configuration.getAcceptedCurrencies().trim().split(";")).contains(object.getPrice().getCurrency()), "price", configuration.getAcceptedCurrencies());
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
