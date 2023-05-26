
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class StudentEnrolmentDeleteTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		final int id = this.repository.findIdByCode(code);
		super.clickOnSubmit("Delete");

		final String param = String.format("id=%d", id);
		super.request("/student/enrolment/show", param);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: this is a deleting, which implies that no data must be entered in any forms.
		// HINT+ Then, there are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/delete");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/student/enrolment/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/student/enrolment/delete");
		super.checkPanicExists();
		super.signOut();
	}

}
