
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

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

		if (object.getDraftMode())
			nHours = sessions.stream().mapToInt(s -> (int) MomentHelper.computeDuration(s.getStartTime(), s.getEndTime()).toHours()).sum();
		else
			nHours = object.getEstimatedTime();

		tuple = super.unbind(object, "code", "title", "recap", "goals", "draftMode");
		tuple.put("estimatedTime", nHours);

		super.getResponse().setData(tuple);
	}

}
