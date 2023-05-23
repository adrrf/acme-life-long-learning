
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String draftMode, final String mark) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {

		Collection<Audit> audits;
		String param;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor01");
		for (final Audit audit : audits)
			if (audit.getDraftMode()) {
				param = String.format("id=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/audit/show", param);
				super.checkPanicExists();

				super.signIn("auditor02", "auditor02");
				super.request("/auditor/audit/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor01", "auditor01");
				super.request("/auditor/audit/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student01", "student01");
				super.request("/auditor/audit/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
