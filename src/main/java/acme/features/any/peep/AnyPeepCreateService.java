
package acme.features.any.peep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import watchdog.Watchdog;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;

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
		Peep object;
		Principal principal;
		String nick;
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object = new Peep();
		object.setInstantiationMoment(moment);
		principal = super.getRequest().getPrincipal();

		if (principal.isAuthenticated()) {
			nick = principal.getUsername();
			object.setNick(nick);
		}

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "nick", "title", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("message")) {
			boolean status;
			String message;
			final Collection<String> spam = new ArrayList<String>();
			double threshold;

			message = object.getMessage();
			spam.add("hola");
			threshold = 0.1;
			status = Watchdog.hasSpam(message, spam, threshold);

			super.state(!status, "message", "any.peep.error.spam");
		}
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "nick", "title", "message", "email", "link");

		super.getResponse().setData(tuple);

	}
}
