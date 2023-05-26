
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Postive(final int recordIndex, final String code, final String motivation, final String goals) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List all available courses");
		super.checkListingExists();

		super.clickOnListingRecord(0);

		super.clickOnButton("Create enrolment");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, motivation);
		super.checkColumnHasValue(recordIndex, 2, goals);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);

		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String motivation, final String goals) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List all available courses");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.clickOnButton("Create enrolment");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/create");
		super.checkPanicExists();

		super.signIn("auditor01", "auditor01");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();
	}

}
