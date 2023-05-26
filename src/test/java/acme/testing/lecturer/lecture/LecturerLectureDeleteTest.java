
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String recap, final String learningTime, final String body, final String isTheory, final String link) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		final int id = this.repository.findIdByTitle(title);
		super.clickOnSubmit("Delete");

		final String param = String.format("id=%d", id);
		super.request("/lecturer/lecture/show", param);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: this is a deleting, which implies that no data must be entered in any forms.
		// HINT+ Then, there are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/delete");
		super.checkPanicExists();

		super.signIn("administrator01", "administrator01");
		super.request("/lecturer/lecture/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/lecturer/lecture/delete");
		super.checkPanicExists();
		super.signOut();
	}
}
