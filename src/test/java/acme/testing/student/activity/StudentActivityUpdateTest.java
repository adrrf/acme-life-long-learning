
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Activity;
import acme.testing.TestHarness;

public class StudentActivityUpdateTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String recap, final String isTheory, final String startDate, final String endDate, final String link) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("isTheory", isTheory);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, recap);

		super.clickOnListingRecord(recordIndex);
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
	@CsvFileSource(resources = "/student/activity/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String recap, final String isTheory, final String startDate, final String endDate, final String link) {

		super.signIn("student01", "student01");

		super.clickOnMenu("Students", "List my enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("isTheory", isTheory);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student01");
		for (final Activity a : activities) {
			param = String.format("id=%d", a.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/update", param);
			super.checkPanicExists();

			super.signIn("student02", "student02");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor01", "auditor01");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer01", "lecturer01");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
