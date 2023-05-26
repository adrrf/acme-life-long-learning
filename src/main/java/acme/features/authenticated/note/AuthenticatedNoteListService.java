
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


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
		Collection<Note> objects;
		Date limit;

		limit = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		objects = this.repository.findNotesByLimit(limit);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantionMoment", "title", "author", "message");

		super.getResponse().setData(tuple);
	}

}
