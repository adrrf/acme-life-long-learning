
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		TutorialSession session;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTutorialSessionById(sessionId);
		status = session != null && session.getTutorial().getDraftMode() && super.getRequest().getPrincipal().hasRole(session.getTutorial().getAssistant())
			&& super.getRequest().getPrincipal().getUsername().equals(session.getTutorial().getAssistant().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int sessionId;

		sessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSessionById(sessionId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;

		super.bind(object, "title", "recap", "isTheory", "startTime", "endTime");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "recap", "isTheory", "startTime", "endTime");
		tuple.put("masterId", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
