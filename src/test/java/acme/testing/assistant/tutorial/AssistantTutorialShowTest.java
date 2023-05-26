
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;
import acme.testing.assistant.tutorialSession.AssistantTutorialSessionTestRepository;

public class AssistantTutorialShowTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String recap, final String goals, final String estimatedTime) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: this is a listing, which implies that no data must be entered in any forms.
		// HINT+ Then, there are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant01");

		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();

			super.signIn("assistant02", "assistant02");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("administrator01", "administrator01");
			super.request("/assistant/tutorial/show", param);
			super.checkPanicExists();
			super.signOut();
		}

	}
}
