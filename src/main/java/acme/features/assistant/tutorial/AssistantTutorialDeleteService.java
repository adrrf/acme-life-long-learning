
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {

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
		status = tutorial != null && tutorial.getDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

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
	public void bind(final Tutorial object) {
		assert object != null;

		super.bind(object, "code", "title", "recap", "goals", "draftMode");
		object.setDraftMode(true);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		Collection<TutorialSession> sessions;

		sessions = this.repository.findManyTutorialSessionsByTutorialId(object.getId());
		this.repository.deleteAll(sessions);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "recap", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}

}
