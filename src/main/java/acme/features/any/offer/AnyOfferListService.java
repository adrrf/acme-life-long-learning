
package acme.features.any.offer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Offer;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyOfferListService extends AbstractService<Any, Offer> {

	@Autowired
	protected AnyOfferRepository repository;


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
		Collection<Offer> objects;

		objects = this.repository.findOffers();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "price");

		super.getResponse().setData(tuple);
	}

}
