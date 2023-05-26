
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String recap, final String goals, final int estimatedTime) {
		super.signIn("assistant01", "assistant01");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		final int id = this.repository.findIdByCode(code);
		super.clickOnSubmit("Delete");

		final String param = String.format("id=%d", id);
		super.request("/assistant/tutorial/show", param);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/delete");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/assistant/tutorial/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/assistant/tutorial/delete");
		super.checkPanicExists();
		super.signOut();
	}
}
