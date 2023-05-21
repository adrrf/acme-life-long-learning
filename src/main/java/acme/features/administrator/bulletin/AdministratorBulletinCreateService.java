
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.messages.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;

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
		Bulletin object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object = new Bulletin();
		object.setInstantiationMoment(moment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "message", "flag", "link");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "administrator.bulletin.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("message")) {
			boolean status;
			String message;

			message = object.getMessage();
			status = this.configuration.hasSpam(message);

			super.state(!status, "message", "administrator.offer.error.spam");
		}

	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "title", "message", "flag", "link");

		super.getResponse().setData(tuple);

	}

}
