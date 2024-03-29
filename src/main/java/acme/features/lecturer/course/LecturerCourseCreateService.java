
package acme.features.lecturer.course;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

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
		Course object;
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());

		super.bind(object, "code", "title", "recap", "retailPrice", "link", "draftMode");
		object.setDraftMode(true);
		object.setLecturer(lecturer);
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findeOneCourseByCode(object.getCode());
			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			Configuration configuration;

			configuration = this.repository.findConfiguration();

			super.state(Arrays.asList(configuration.getAcceptedCurrencies().trim().split(";")).contains(object.getRetailPrice().getCurrency()), "retailPrice", configuration.getAcceptedCurrencies());
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() > 0, "retailPrice", "lecturer.course.form.error.negative-retailprice");

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

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());

		tuple = super.unbind(object, "code", "title", "recap", "retailPrice", "link", "draftMode");
		tuple.put("lecturer", lecturer);

		super.getResponse().setData(tuple);
	}

}
