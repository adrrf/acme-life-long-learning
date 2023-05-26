
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumUpdateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String recap, final String goals) {
		super.signIn("company01", "company01");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("recap", recap);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("goals", goals);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String recap, final String goals) {
		super.signIn("company01", "company01");

		super.clickOnMenu("Practicums", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("recap", recap);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/company/practicum/update");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/company/practicum/update");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/company/practicum/update");
		super.checkPanicExists();
		super.signOut();
	}
}
