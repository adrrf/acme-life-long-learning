
package acme.features.students.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneActivityByEnrolmentId(enrolmentId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent()) && super.getRequest().getPrincipal().getUsername().equals(enrolment.getStudent().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
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
