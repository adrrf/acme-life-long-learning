
package acme.testing.company.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

public class CompanySessionlListTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexPracticum, final int recordIndex, final String title, final String recap, final String link) {
		super.signIn("company02", "company02");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndexPracticum);
		super.clickOnButton("Sessions");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, recap);

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
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company01");

		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/session/list", param);
			super.checkPanicExists();

			super.signIn("student02", "student02");
			super.request("/company/session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("administrator01", "administrator01");
			super.request("/company/session/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
