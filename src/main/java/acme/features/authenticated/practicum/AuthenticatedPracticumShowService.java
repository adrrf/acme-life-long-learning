
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Practicum practicum;

		id = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(id);
		status = !practicum.getDraftMode() && !practicum.getCourse().getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		int id;
		Collection<Session> sessions;
		int nHours;

		id = super.getRequest().getData("id", int.class);

		sessions = this.repository.findManySessionsByPracticumId(id);

		nHours = sessions.stream().mapToInt(s -> (int) MomentHelper.computeDuration(s.getStartTime(), s.getEndTime()).toHours()).sum();

		tuple = super.unbind(object, "code", "title", "recap", "goals");
		tuple.put("totalTime", nHours);
		tuple.put("company", object.getCompany().getId());

		super.getResponse().setData(tuple);
	}
}
