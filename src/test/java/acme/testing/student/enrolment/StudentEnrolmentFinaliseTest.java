
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentFinaliseTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String card, final String holder, final String CVV, final String expiryDate) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("card", card);
		super.fillInputBoxIn("holder", holder);
		super.fillInputBoxIn("CVV", CVV);
		super.fillInputBoxIn("expiryDate", expiryDate);

		super.clickOnSubmit("Finalise");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String card, final String holder, final String CVV, final String expiryDate) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("card", card);
		super.fillInputBoxIn("holder", holder);
		super.fillInputBoxIn("CVV", CVV);
		super.fillInputBoxIn("expiryDate", expiryDate);

		super.clickOnSubmit("Finalise");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("lecturer01");
		for (final Enrolment e : enrolments)
			if (e.getDraftMode()) {
				params = String.format("id=%d", e.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();

				super.signIn("student02", "student02");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor01", "auditor01");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student01", "student01");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student01");
		for (final Enrolment e : enrolments)
			if (!e.getDraftMode()) {
				params = String.format("id=%d", e.getId());
				super.request("/employer/job/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student01", "student01");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student01");
		for (final Enrolment e : enrolments) {
			params = String.format("id=%d", e.getId());
			super.request("/employer/job/publish", params);
		}
		super.signOut();
	}

}
