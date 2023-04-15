
package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.messages.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository			repository;

	@Autowired
	protected ConfigurationRepository	configuration;

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

		if (!super.getBuffer().getErrors().hasErrors("nick")) {
			boolean status;
			String message;

			message = object.getNick();
			status = this.configuration.hasSpam(message);

			super.state(!status, "nick", "any.peep.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "any.peep.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("message")) {
			boolean status;
			String message;

			message = object.getMessage();
			status = this.configuration.hasSpam(message);

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
