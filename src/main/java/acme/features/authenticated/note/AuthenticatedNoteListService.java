
package acme.features.authenticated.note;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

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
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Collection<Note> objects;

		objects = this.repository.findAllNotes();
		objects = objects.stream().filter(n -> {
			Duration duration;
			boolean res;

			duration = MomentHelper.computeDuration(n.getInstantionMoment(), MomentHelper.getCurrentMoment());
			res = duration.get(ChronoUnit.MONTHS) <= 1.0;
			return res;
		}).collect(Collectors.toList());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantionMoment", "title", "author", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}
