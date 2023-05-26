
package acme.testing.auditor.auditingRecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String subject, final String assessment, final String startPeriod, final String finishPeriod, final String mark, final String link) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Auditing Records");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("finishPeriod", finishPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, assessment);
		super.checkColumnHasValue(recordIndex, 3, mark);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("finishPeriod", finishPeriod);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String subject, final String assessment, final String startPeriod, final String finishPeriod, final String mark, final String link) {

		super.signIn("Auditor01", "auditor01");

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Auditing Records");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("finishPeriod", finishPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/auditor/auditing-record/show");
		super.checkPanicExists();

		super.signIn("auditor02", "auditor02");
		super.request("/auditor/auditing-record/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer01", "lecturer01");
		super.request("/auditor/auditing-record/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/auditor/auditing-record/show");
		super.checkPanicExists();
		super.signOut();

	}
}
