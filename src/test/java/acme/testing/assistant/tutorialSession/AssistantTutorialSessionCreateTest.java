
package acme.testing.assistant.tutorialSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;
import acme.testing.assistant.tutorial.AssistantTutorialTestRepository;

public class AssistantTutorialSessionCreateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexTutorial, final int recordIndex, final String title, final String recap, final String startTime, final String endTime, final String isTheory, final String duration) {
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

		super.checkListingExists();

		super.signOut();
	}

	@Test
	public void test200Negative(final int recordIndexTutorial, final int recordIndex, final String title, final String recap, final String startTime, final String endTime, final String isTheory) {

	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorialSession/create");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/assistant/tutorialSession/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/assistant/tutorialSession/create");
		super.checkPanicExists();
		super.signOut();
	}
}
