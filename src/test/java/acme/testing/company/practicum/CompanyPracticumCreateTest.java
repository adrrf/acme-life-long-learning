
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumCreateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexCourse, final int recordIndex, final String code, final String title, final String recap, final String goals, final String courseCode, final String courseTitle) {
		super.signIn("company01", "company01");

		super.clickOnMenu("Courses", "List all available courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexCourse);
		super.checkFormExists();

		super.clickOnButton("Create practicum");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("recap", recap);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, courseCode);
		super.checkColumnHasValue(recordIndex, 3, courseTitle);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("totalTime", "0");

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndexCourse, final int recordIndex, final String code, final String title, final String recap, final String goals, final String courseCode, final String courseTitle) {
		super.signIn("company01", "company01");

		super.clickOnMenu("Courses", "List all available courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexCourse);
		super.checkFormExists();

		super.clickOnButton("Create practicum");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("recap", recap);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/company/practicum/create");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();
	}
}
