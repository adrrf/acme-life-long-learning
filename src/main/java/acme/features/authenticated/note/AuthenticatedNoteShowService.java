
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteShowService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Note object;
		int noteId;
		Date limit;

		noteId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneNoteById(noteId);
		limit = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);

		status = MomentHelper.isAfter(object.getInstantionMoment(), limit);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Note object;
		int noteId;

		noteId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneNoteById(noteId);

		super.getBuffer().setData(object);
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
