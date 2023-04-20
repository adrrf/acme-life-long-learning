
package acme.features.students.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentListService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Enrolment> objects;
		int studentId;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyEnrolmentsByStudentId(studentId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");
		tuple.put("courseTitle", object.getCourse().getTitle());
		super.getResponse().setData(tuple);
	}

}
