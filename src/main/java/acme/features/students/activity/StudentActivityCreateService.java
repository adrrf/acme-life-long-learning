
package acme.features.students.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int EnrolmentId;
		Enrolment Enrolment;

		EnrolmentId = super.getRequest().getData("masterId", int.class);
		Enrolment = this.repository.findOneEnrolmentById(EnrolmentId);
		status = Enrolment != null && Enrolment.getDraftMode() && super.getRequest().getPrincipal().hasRole(Enrolment.getStudent()) && super.getRequest().getPrincipal().getUsername().equals(Enrolment.getStudent().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		Enrolment Enrolment;
		int EnrolmentId;

		EnrolmentId = super.getRequest().getData("masterId", int.class);
		Enrolment = this.repository.findOneEnrolmentById(EnrolmentId);
		object = new Activity();
		object.setEnrolment(Enrolment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		Enrolment Enrolment;
		int EnrolmentId;

		EnrolmentId = super.getRequest().getData("masterId", int.class);
		Enrolment = this.repository.findOneEnrolmentById(EnrolmentId);

		super.bind(object, "title", "recap", "isTheory", "startDate", "endDate", "link");
		object.setEnrolment(Enrolment);

	}

	@Override
	@SuppressWarnings("deprecation")
	public void validate(final Activity object) {
		assert object != null;

		/*
		 * Date initialDateSystem;
		 * Date endDateSystem;
		 * 
		 * initialDateSystem = new Date(2000, 01, 01, 00, 00);
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

		Enrolment Enrolment;
		int EnrolmentId;

		Tuple tuple;

		EnrolmentId = super.getRequest().getData("masterId", int.class);
		Enrolment = this.repository.findOneEnrolmentById(EnrolmentId);
		tuple = super.unbind(object, "title", "recap", "isTheory", "startDate", "endDate", "link");
		tuple.put("Enrolment", Enrolment);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getEnrolment().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
