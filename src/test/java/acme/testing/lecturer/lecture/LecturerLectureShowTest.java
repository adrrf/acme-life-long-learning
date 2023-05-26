
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureShowTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String recap, final String learningTime, final String body, final String isTheory, final String link) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my lectures");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("isTheory", isTheory);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer01");
		for (final Lecture lecture : lectures)
			if (lecture.getDraftMode()) {
				param = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();

				super.signIn("lecturer02", "lecturer02");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor01", "auditor01");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student01", "student01");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
