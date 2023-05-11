
package acme.features.students.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;


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
		object.setDraftMode(true);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment Enrolment;
			String code;

			code = object.getCode();
			Enrolment = this.repository.findOneEnrolmentByCode(code);
			super.state(Enrolment == null || Enrolment.equals(object), "code", "student.enrolment.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("motivation")) {

			boolean status;
			String message;

			message = object.getMotivation();
			status = this.configuration.hasSpam(message);

			super.state(!status, "motivation", "student.enrolment.error.spam");

		}

		if (!super.getBuffer().getErrors().hasErrors("goals")) {

			boolean status;
			String message;

			message = object.getGoals();
			status = this.configuration.hasSpam(message);

			super.state(!status, "goals", "student.enrolment.error.spam");

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

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}

}
