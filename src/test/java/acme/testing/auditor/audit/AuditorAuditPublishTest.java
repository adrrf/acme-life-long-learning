
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditPublishTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("auditor01", "auditor01");

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test200Negative() { //No se puede dar un test negativo para publish, puesto que los audits siempre se pueden publicar
	}

	@Test
	public void test300Hacking() {

		Collection<Audit> audits;
		String params;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor01");
		for (final Audit audit : audits)
			if (audit.getDraftMode()) {
				params = String.format("id=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/audit/publish", params);
				super.checkPanicExists();

				super.signIn("auditor02", "auditor02");
				super.request("/auditor/audit/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer01", "lecturer01");
				super.request("/auditor/audit/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		Collection<Audit> audits;
		String params;

		super.signIn("auditor01", "auditor01");
		audits = this.repository.findManyAuditsByAuditorUsername("auditor01");
		for (final Audit audit : audits)
			if (!audit.getDraftMode()) {
				params = String.format("id=%d", audit.getId());
				super.request("/auditor/audit/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		Collection<Audit> audits;
		String params;

		super.signIn("auditor02", "auditor02");
		audits = this.repository.findManyAuditsByAuditorUsername("auditor01");
		for (final Audit audit : audits) {
			params = String.format("id=%d", audit.getId());
			super.request("/auditor/audit/publish", params);
		}
		super.signOut();
	}

}
