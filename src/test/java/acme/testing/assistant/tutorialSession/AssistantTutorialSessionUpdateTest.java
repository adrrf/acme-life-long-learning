
package acme.testing.assistant.tutorialSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;
import acme.testing.assistant.tutorial.AssistantTutorialTestRepository;

public class AssistantTutorialSessionUpdateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexTutorial, final int recordIndex, final String title, final String recap, final String startTime, final String endTime, final String isTheory, final String duration) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexTutorial);
		super.checkFormExists();

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("isTheory", isTheory);

		super.clickOnSubmit("Update");

		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, recap);
		super.checkColumnHasValue(recordIndex, 2, duration);
		super.checkColumnHasValue(recordIndex, 3, isTheory);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);
		super.checkInputBoxHasValue("startTime", startTime);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndexTutorial, final int recordIndex, final String title, final String recap, final String startTime, final String endTime, final String isTheory) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexTutorial);
		super.checkFormExists();

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("isTheory", isTheory);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorialSession/update");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/assistant/tutorialSession/update");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/assistant/tutorialSession/update");
		super.checkPanicExists();
		super.signOut();
	}
}
