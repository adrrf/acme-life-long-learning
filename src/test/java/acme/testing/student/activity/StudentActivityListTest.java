
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexEnrolment, final int recordIndexActivity, final String title, final String recap, final String isTheory, final String startDate, final String endDate, final String link) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexEnrolment);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndexActivity, 0, title);
		super.checkColumnHasValue(recordIndexActivity, 1, recap);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/student/activity/list");
		super.checkPanicExists();

		super.signIn("auditor01", "auditor01");
		super.request("/student/activity/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/student/activity/list");
		super.checkPanicExists();
		super.signOut();
	}

}
