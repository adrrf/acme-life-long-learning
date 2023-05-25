
package acme.testing.auditor.auditingRecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditingRecordListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String subject, final String assessment, final String duration, final String mark, final String link) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.clickOnListingRecord(2);

		super.clickOnButton("Auditing Record");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, assessment);
		super.checkColumnHasValue(recordIndex, 2, duration);
		super.checkColumnHasValue(recordIndex, 3, mark);
		super.checkColumnHasValue(recordIndex, 4, link);

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

		super.signIn("auditor01", "auditor01");
		super.request("/auditor/auditing-record/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/auditor/auditing-record/list-mine");
		super.checkPanicExists();
		super.signOut();
	}
}
