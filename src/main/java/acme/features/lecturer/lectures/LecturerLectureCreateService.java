
package acme.features.lecturer.lectures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecturer lecturer;
		Lecture object;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Lecture();
		object.setLecturer(lecturer);
		object.setIsTheory(false);
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());

		super.bind(object, "title", "recap", "learningTime", "body", "isTheory", "link", "draftMode");
		object.setLecturer(lecturer);
		object.setDraftMode(true);
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

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
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());

		tuple = super.unbind(object, "title", "recap", "learningTime", "body", "isTheory", "link", "draftMode");
		tuple.put("lecturer", lecturer);

		super.getResponse().setData(tuple);

	}

}
