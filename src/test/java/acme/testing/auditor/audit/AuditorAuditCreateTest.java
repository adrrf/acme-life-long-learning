
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Postive(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Courses", "List all available courses");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Create audit");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, conclusion);
		super.checkColumnHasValue(recordIndex, 2, strongPoints);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);

		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Courses", "List all available courses");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.clickOnButton("Create audit");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/create");
		super.checkPanicExists();

		super.signIn("lecturer01", "lecturer01");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();
	}

}
