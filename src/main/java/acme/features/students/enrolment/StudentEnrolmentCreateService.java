
package acme.features.students.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		Student Student;
		int courseId;
		Course course;

		object = new Enrolment();
		Student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setDraftMode(true);
		object.setStudent(Student);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		int StudentId;
		Student Student;
		int courseId;
		Course course;

		StudentId = super.getRequest().getPrincipal().getActiveRoleId();
		Student = this.repository.findOneStudentById(StudentId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "motivation", "goals", "draftMode");
		object.setStudent(Student);
		object.setDraftMode(true);
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment Enrolment;
			String code;

			code = object.getCode();
			Enrolment = this.repository.findOneEnrolmentByCode(code);
			super.state(Enrolment == null, "code", "Student.Enrolment.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		int StudentId;
		Student Student;
		int courseId;
		Course course;

		StudentId = super.getRequest().getPrincipal().getActiveRoleId();
		Student = this.repository.findOneStudentById(StudentId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");
		tuple.put("courseId", super.getRequest().getData("courseId", int.class));
		tuple.put("Student", Student);
		tuple.put("course", course);

		super.getResponse().setData(tuple);
	}

}
