
package acme.features.students.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.helpers.CreditCardHelper;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository	repository;

	@Autowired
	protected ConfigurationRepository		configuration;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && enrolment.getDraftMode() && super.getRequest().getPrincipal().hasRole(student);

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
		String cardNumber;
		String nibble;

		cardNumber = super.getRequest().getData("card", String.class).trim();
		nibble = cardNumber.isEmpty() || !cardNumber.matches("^\\d{16}$") ? "" : cardNumber.substring(cardNumber.length() - 4);

		super.bind(object, "motivation", "goals", "holder");
		object.setNibble(nibble);

	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		String CVV;
		String expiryDate;
		String creditCardNumber;

		CVV = super.getRequest().getData("CVV", String.class);
		expiryDate = super.getRequest().getData("expiryDate", String.class);
		creditCardNumber = super.getRequest().getData("card", String.class);

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;
			existing = this.repository.findOneEnrolmentByCode(object.getCode());
			super.state(existing == null || object.equals(existing), "code", "student.enrolment.form.error.duplicated");
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

		if (!super.getBuffer().getErrors().hasErrors("holder")) {

			boolean status;
			String message;

			message = object.getHolder();
			status = this.configuration.hasSpam(message);

			super.state(!status, "holder", "student.enrolment.error.spam");

		}

		super.state(creditCardNumber != null && !creditCardNumber.isEmpty(), "card", "student.enrolment.form.error.nullCard");
		super.state(!(creditCardNumber != null && !creditCardNumber.isEmpty()) || CreditCardHelper.hasCorrectCardNumberFormat(creditCardNumber), "card", "student.enrolment.form.error.invalidFormatCard");
		super.state(!(creditCardNumber != null && !creditCardNumber.isEmpty() && CreditCardHelper.hasCorrectCardNumberFormat(creditCardNumber) && (object.getNibble() == null ? true : creditCardNumber.substring(0, 5) == object.getNibble()))
			|| CreditCardHelper.hasValidCreditNumber(creditCardNumber), "card", "student.enrolment.form.error.invalidCardNumber");

		if (!super.getBuffer().getErrors().hasErrors("holder"))
			super.state(object.getHolder() != null && !object.getHolder().isEmpty(), "holder", "student.enrolment.form.error.nullHolder");

		super.state(CVV != null && !CVV.isEmpty(), "CVV", "student.enrolment.form.error.nullCVV");
		super.state(!(CVV != null && !CVV.isEmpty()) || CreditCardHelper.hasCorrectCVVFormat(CVV), "CVV", "student.enrolment.form.error.invalidCVV");

		super.state(expiryDate != null && !expiryDate.isEmpty(), "expiryDate", "student.enrolment.form.error.nullExpiryDate");
		super.state(!(expiryDate != null && !expiryDate.isEmpty()) || CreditCardHelper.hasCorrectExpiryDateFormat(expiryDate), "expiryDate", "student.enrolment.form.error.invalidExpiryDateFormat");
		super.state(!(expiryDate != null && !expiryDate.isEmpty() && CreditCardHelper.hasCorrectExpiryDateFormat(expiryDate)) || CreditCardHelper.hasValidExpiryDate(expiryDate), "expiryDate", "student.enrolment.form.error.invalidExpiryDate");
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
		String cardNumber;
		cardNumber = super.getRequest().getData("card", String.class).trim();
		String CVV;
		CVV = super.getRequest().getData("CVV", String.class).trim();
		String expiryDate;
		expiryDate = super.getRequest().getData("expiryDate", String.class).trim();

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "holder");
		tuple.put("CVV", CVV);
		tuple.put("expiryDate", expiryDate);
		tuple.put("card", cardNumber);

		super.getResponse().setData(tuple);
	}

}
