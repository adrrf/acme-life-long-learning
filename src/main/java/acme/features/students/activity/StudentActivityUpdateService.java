
package acme.features.students.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.enrolment.Activity;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository	repository;

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
		int sessionId;
		Activity session;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneActivityById(sessionId);
		status = session != null && session.getEnrolment().getDraftMode() && super.getRequest().getPrincipal().hasRole(session.getEnrolment().getStudent())
			&& super.getRequest().getPrincipal().getUsername().equals(session.getEnrolment().getStudent().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int sessionId;

		sessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(sessionId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "recap", "isTheory", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		/*
		 * Date initialDateSystem;
		 * Date endDateSystem;
		 * 
		 * initialDateSystem = new Date(2000, 1, 1, 0, 0);
		 * endDateSystem = new Date(2100, 12, 31, 23, 59);
		 */

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			if (!MomentHelper.isBefore(object.getStartDate(), object.getEndDate()))
				super.state(false, "endDate", "student.enrolment-session.form.error.end-before-start");

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("startDate"))
		 * if (!MomentHelper.isBefore(initialDateSystem, object.getStartDate()))
		 * super.state(false, "startDate", "student.enrolment-session.form.error.startDateNotvalid");
		 * 
		 * if (!super.getBuffer().getErrors().hasErrors("endDate"))
		 * if (!MomentHelper.isBefore(object.getEndDate(), endDateSystem))
		 * super.state(false, "endDate", "student.enrolment-session.form.error.endDateNotvalid");
		 */

		if (!super.getBuffer().getErrors().hasErrors("recap")) {

			boolean status;
			String message;

			message = object.getRecap();
			status = this.configuration.hasSpam(message);

			super.state(!status, "recap", "student.activity.error.spam");

		}

		if (!super.getBuffer().getErrors().hasErrors("title")) {

			boolean status;
			String message;

			message = object.getTitle();
			status = this.configuration.hasSpam(message);

			super.state(!status, "title", "student.activity.error.spam");

		}

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "recap", "isTheory", "startDate", "endDate", "link");
		tuple.put("masterId", object.getEnrolment().getId());
		tuple.put("draftMode", object.getEnrolment().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
