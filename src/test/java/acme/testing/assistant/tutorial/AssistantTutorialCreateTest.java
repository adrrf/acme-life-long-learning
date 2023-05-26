
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexCourse, final int recordIndex, final String code, final String title, final String recap, final String goals, final String courseCode, final String courseTitle) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Courses", "List all available courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexCourse);
		super.checkFormExists();

		super.clickOnButton("Create tutorial");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("recap", recap);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Assistant", "List my tutorials");
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
		super.checkInputBoxHasValue("estimatedTime", "0");

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndexCourse, final int recordIndex, final String code, final String title, final String recap, final String goals, final String courseCode, final String courseTitle) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Courses", "List all available courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexCourse);
		super.checkFormExists();

		super.clickOnButton("Create tutorial");
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
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.signOut();
	}
}
