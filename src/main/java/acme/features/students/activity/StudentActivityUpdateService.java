
package acme.features.students.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

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
