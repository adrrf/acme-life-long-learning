
package acme.features.students.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


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
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Enrolment object) {

		assert object != null;
		final Tuple tuple;
		int id;
		final int nHours;
		Collection<Activity> activities;

		id = super.getRequest().getData("id", int.class);
		activities = this.repository.findManyActivitiesByEnrolmentId(id);
		nHours = activities.stream().mapToInt(a -> (int) MomentHelper.computeDuration(a.getStartDate(), a.getEndDate()).toHours()).sum();

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "holder");
		tuple.put("card", object.getNibble());
		tuple.put("estimatedTime", nHours);
		super.getResponse().setData(tuple);
	}

}
