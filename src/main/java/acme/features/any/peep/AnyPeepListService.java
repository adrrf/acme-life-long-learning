
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepListService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


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
		Collection<Peep> objects;

		objects = this.repository.findPeeps();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "nick", "title");

		super.getResponse().setData(tuple);
	}

}
