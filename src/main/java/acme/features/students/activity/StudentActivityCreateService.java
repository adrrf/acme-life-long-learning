
package acme.features.students.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


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
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			if (!MomentHelper.isBefore(object.getStartDate(), object.getEndDate()))
				super.state(false, "endDate", "Student.Enrolment-session.form.error.end-before-start");
		/*
		 * else {
		 * final int days = (int) MomentHelper.computeDuration(MomentHelper.getCurrentMoment(), object.getStartDate()).toDays();
		 * if (days < 1)
		 * super.state(false, "startDate", "Student.Enrolment-session.form.error.day-ahead");
		 * else {
		 * final int hours = (int) MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toHours();
		 * if (!(1 <= hours && hours <= 5))
		 * super.state(false, "endDate", "Student.Enrolment-session.form.error.duration");
		 * }
		 * }
		 */
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
