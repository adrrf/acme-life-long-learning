
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialUpdateService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


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

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "assistant.tutorial.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("recap")) {
			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "assistant.tutorial.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("goals")) {
			boolean status;
			String message;

			message = object.getGoals();
			status = this.configuration.hasSpam(message);

			super.state(!status, "goals", "assistant.tutorial.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial tutorial;
			String code;

			code = object.getCode();
			tutorial = this.repository.findOneTutorialByCode(code);
			super.state(tutorial == null || tutorial.equals(object), "code", "assistant.tutorial.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "recap", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}

}
