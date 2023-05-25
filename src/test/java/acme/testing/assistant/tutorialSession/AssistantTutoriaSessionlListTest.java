
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;
import acme.testing.assistant.tutorial.AssistantTutorialTestRepository;

public class AssistantTutoriaSessionlListTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexTutorial, final int recordIndex, final String title, final String recap, final String duration, final String isTheory) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexTutorial);
		super.clickOnButton("Sessions");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, recap);
		super.checkColumnHasValue(recordIndex, 2, duration);
		super.checkColumnHasValue(recordIndex, 3, isTheory);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: this is a listing, which implies that no data must be entered in any forms.
		// HINT+ Then, there are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show applications that the principal cannot show.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant01");

		for (final Tutorial tutorial : tutorials) {
			param = String.format("masterId=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/list", param);
			super.checkPanicExists();

			super.signIn("assistant02", "assistant02");
			super.request("/assistant/tutorial-session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("administrator01", "administrator01");
			super.request("/assistant/tutorial-session/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
