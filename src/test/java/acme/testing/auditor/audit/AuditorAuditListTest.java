
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.entities.audit.Mark;
import acme.testing.TestHarness;

public class AuditorAuditListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String draftMode, final Mark mark) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, conclusion);
		super.checkColumnHasValue(recordIndex, 2, strongPoints);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/list");
		super.checkPanicExists();

		super.signIn("lecturer01", "lecturer01");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();
	}
}
