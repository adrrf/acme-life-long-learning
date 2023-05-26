
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository	repository;

	@Autowired
	protected ConfigurationRepository				configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		status = tutorial != null && tutorial.getDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && super.getRequest().getPrincipal().getUsername().equals(tutorial.getAssistant().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		Tutorial tutorial;
		int tutorialId;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		object = new TutorialSession();
		object.setTutorial(tutorial);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;

		Tutorial tutorial;
		int tutorialId;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);

		super.bind(object, "title", "recap", "isTheory", "startTime", "endTime");
		object.setTutorial(tutorial);

	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "assistant.tutorial-session.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("recap")) {
			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "assistant.tutorial-session.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime"))
			if (!MomentHelper.isBefore(object.getStartTime(), object.getEndTime()))
				super.state(false, "endTime", "assistant.tutorial-session.form.error.end-before-start");
			else {
				final long days = MomentHelper.computeDuration(MomentHelper.getCurrentMoment(), object.getStartTime()).toDays();
				if (days < 1)
					super.state(false, "startTime", "assistant.tutorial-session.form.error.day-ahead");
				else {
					final int hours = (int) MomentHelper.computeDuration(object.getStartTime(), object.getEndTime()).toHours();
					if (!(1 <= hours && hours <= 5))
						super.state(false, "endTime", "assistant.tutorial-session.form.error.duration");
				}
			}
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tutorial tutorial;
		int tutorialId;

		Tuple tuple;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		tuple = super.unbind(object, "title", "recap", "isTheory", "startTime", "endTime");
		tuple.put("tutorial", tutorial);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getTutorial().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
