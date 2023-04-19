
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.messages.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


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
		Note object;
		Date now;

		now = MomentHelper.getCurrentMoment();
		object = new Note();
		object.setInstantionMoment(now);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "title", "author", "email", "link", "message");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "authenticated.note.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("author")) {
			boolean status;
			String message;

			message = object.getAuthor();
			status = this.configuration.hasSpam(message);

			super.state(!status, "author", "authenticated.note.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("message")) {
			boolean status;
			String message;

			message = object.getMessage();
			status = this.configuration.hasSpam(message);

			super.state(!status, "message", "authenticated.note.form.error.spam");
		}

		if (confirmation)
			super.state(confirmation, "confirmation", "authenticated.note.form.error.confirmation");
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		Date now;

		now = MomentHelper.getCurrentMoment();
		object.setInstantionMoment(now);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantionMoment", "title", "author", "message", "email", "link");
		tuple.put("draftMode", true);
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}

}
