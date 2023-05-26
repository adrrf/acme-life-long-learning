
package acme.features.lecturer.lectures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureId;
		Lecture lecture;

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		status = lecture != null && lecture.getDraftMode() && super.getRequest().getPrincipal().hasRole(lecture.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "recap", "learningTime", "body", "isTheory", "link", "draftMode");
		object.setDraftMode(true);

	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			Lecture existing;

			existing = this.repository.findOneLectureByTitle(object.getTitle());
			super.state(existing == null || existing.equals(object), "title", "lecturer.lecture.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("learningTime"))
			super.state(object.getLearningTime() > 0, "learningTime", "lecturer.lecture.form.error.negative-learningTime");

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "lecturer.course.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("recap")) {
			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "lecturer.course.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("body")) {
			boolean status;
			String message;

			message = object.getBody();
			status = this.configuration.hasSpam(message);

			super.state(!status, "body", "lecturer.course.form.error.spam");
		}
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "recap", "learningTime", "body", "isTheory", "link", "draftMode");

		super.getResponse().setData(tuple);
	}

}
