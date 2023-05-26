
package acme.features.any.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Offer;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyOfferShowService extends AbstractService<Any, Offer> {

	@Autowired
	protected AnyOfferRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int offerId;
		Offer offer;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(offerId);
		status = offer != null;

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
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "startDate", "endDate", "price", "link");

		super.getResponse().setData(tuple);
	}

}
