
package acme.testing.company.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.testing.TestHarness;

public class CompanySessionlShowTest extends TestHarness {

	@Autowired
	protected CompanySessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexPracticum, final int recordIndex, final String title, final String recap, final String startTime, final String endTime, final String link) {
		super.signIn("company02", "company02");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexPracticum);
		super.clickOnButton("Sessions");

		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);

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

		Collection<Practicum> practicums;
		Collection<Session> sessions;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company01");

		for (final Practicum practicum : practicums) {

			sessions = this.repository.findManySessionsByPracticumId(practicum.getId());

			for (final Session session : sessions) {
				param = String.format("id=%d", session.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();

				super.signIn("student02", "student02");
				super.request("/company/session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("administrator01", "administrator01");
				super.request("/company/session/show", param);
				super.checkPanicExists();
				super.signOut();
			}

		}
	}
}
