
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentShowTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String holder, final String card) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("holder", holder);
		super.checkInputBoxHasValue("card", card);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student01");
		for (final Enrolment e : enrolments)
			if (e.getDraftMode()) {
				param = String.format("id=%d", e.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/show", param);
				super.checkPanicExists();

				super.signIn("student02", "student02");
				super.request("/student/enrolment/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor01", "auditor01");
				super.request("/student/enrolment/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer01", "lecturer01");
				super.request("/student/enrolment/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
