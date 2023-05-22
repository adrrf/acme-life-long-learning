
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String recap, final String retailPrice, final String link, final String draftMode, final String isTheory) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, recap);

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
		super.request("/lecturer/course/list");
		super.checkPanicExists();

		super.signIn("auditor01", "auditor01");
		super.request("/lecturer/course/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/lecturer/course/list");
		super.checkPanicExists();
		super.signOut();
	}
}
