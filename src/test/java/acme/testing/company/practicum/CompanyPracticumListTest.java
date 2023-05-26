
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticumListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String courseCode, final String courseTitle) {
		super.signIn("company01", "company01");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, courseCode);
		super.checkColumnHasValue(recordIndex, 3, courseTitle);

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

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/list");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator01", "administrator01");
		super.request("/company/practicum/list");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer01", "lecturer01");
		super.request("/company/practicum/list");
		super.checkPanicExists();
		super.signOut();
	}
}
