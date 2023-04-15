
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		Assistant assistant;
		int courseId;
		Course course;

		object = new Tutorial();
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setDraftMode(true);
		object.setAssistant(assistant);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		int assistantId;
		Assistant assistant;
		int courseId;
		Course course;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		assistant = this.repository.findOneAssistantById(assistantId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "title", "recap", "goals", "draftMode");
		object.setAssistant(assistant);
		object.setDraftMode(true);
		object.setCourse(course);
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
			super.state(tutorial == null, "code", "assistant.tutorial.form.error.duplicated-code");
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
		int assistantId;
		Assistant assistant;
		int courseId;
		Course course;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		assistant = this.repository.findOneAssistantById(assistantId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		tuple = super.unbind(object, "code", "title", "recap", "goals", "draftMode");
		tuple.put("estimatedTime", 0);
		tuple.put("courseId", super.getRequest().getData("courseId", int.class));
		tuple.put("assistant", assistant);
		tuple.put("course", course);

		super.getResponse().setData(tuple);
	}

}
