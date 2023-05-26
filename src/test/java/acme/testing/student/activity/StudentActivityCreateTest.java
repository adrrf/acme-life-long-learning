
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Postive(final int recordIndexEnrolment, final int recordIndexActivity, final String title, final String recap, final String isTheory, final String startDate, final String endDate, final String link) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexEnrolment);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("isTheory", isTheory);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

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

		super.clickOnListingRecord(recordIndexActivity);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("isTheory", isTheory);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String recap, final String isTheory, final String startDate, final String endDate, final String link) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("isTheory", isTheory);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/student/activity/create");
		super.checkPanicExists();

		super.signIn("auditor01", "auditor01");
		super.request("/student/activity/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/student/activity/create");
		super.checkPanicExists();
		super.signOut();
	}

}
