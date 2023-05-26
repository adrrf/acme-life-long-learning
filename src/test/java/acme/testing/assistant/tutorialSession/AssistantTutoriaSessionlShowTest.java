
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutoriaSessionlShowTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexTutorial, final int recordIndex, final String title, final String recap, final String startTime, final String endTime, final String isTheory) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexTutorial);
		super.clickOnButton("Sessions");

		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);
		super.checkInputBoxHasValue("isTheory", isTheory);

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
		Collection<TutorialSession> sessions;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant01");

		for (final Tutorial tutorial : tutorials) {

			sessions = this.repository.findManySessionsByTutorialId(tutorial.getId());

			for (final TutorialSession session : sessions) {
				param = String.format("id=%d", session.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();

				super.signIn("assistant02", "assistant02");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("administrator01", "administrator01");
				super.request("/assistant/tutorial-session/show", param);
				super.checkPanicExists();
				super.signOut();
			}

		}
	}
}
