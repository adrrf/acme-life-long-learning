
package acme.features.students.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentPublishService extends AbstractService<Student, Enrolment> {

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
		int EnrolmentId;
		Enrolment Enrolment;

		EnrolmentId = super.getRequest().getData("id", int.class);
		Enrolment = this.repository.findOneEnrolmentById(EnrolmentId);
		status = Enrolment != null && Enrolment.getDraftMode() && super.getRequest().getPrincipal().hasRole(Enrolment.getStudent());

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
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "draftMode");
		object.setDraftMode(false);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment Enrolment;
			String code;

			code = object.getCode();
			Enrolment = this.repository.findOneEnrolmentByCode(code);
			super.state(Enrolment == null || Enrolment.equals(object), "code", "Student.Enrolment.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}

}
