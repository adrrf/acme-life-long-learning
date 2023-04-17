
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	@Autowired
	protected AuthenticatedTutorialRepository repository;


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
		Tutorial tutorial;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(id);
		status = !tutorial.getDraftMode() && !tutorial.getCourse().getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		int id;
		Collection<TutorialSession> sessions;
		int nHours;

		id = super.getRequest().getData("id", int.class);

		sessions = this.repository.findManyTutorialSessionsByTutorialId(id);

		nHours = sessions.stream().mapToInt(s -> (int) MomentHelper.computeDuration(s.getStartTime(), s.getEndTime()).toHours()).sum();

		tuple = super.unbind(object, "code", "title", "recap", "goals");
		tuple.put("estimatedTime", nHours);
		tuple.put("assistant", object.getAssistant().getId());

		super.getResponse().setData(tuple);
	}
}
