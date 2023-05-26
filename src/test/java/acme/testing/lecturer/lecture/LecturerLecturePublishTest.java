
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Lecture;
import acme.testing.TestHarness;

public class LecturerLecturePublishTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my lectures");
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
	public void test200Negative() {

		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {

		Collection<Lecture> lectures;
		String params;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer01");
		for (final Lecture lecture : lectures)
			if (lecture.getDraftMode()) {
				params = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();

				super.signIn("lecturer02", "lecturer02");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor01", "auditor01");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer01", "lecturer01");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer01");
		for (final Lecture lecture : lectures)
			if (!lecture.getDraftMode()) {
				params = String.format("id=%d", lecture.getId());
				super.request("/lecturer/lecture/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		Collection<Lecture> lectures;
		String params;

		super.signIn("lecturer02", "lecturer02");
		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer01");
		for (final Lecture lecture : lectures) {
			params = String.format("id=%d", lecture.getId());
			super.request("/lecturer/lecture/publish", params);
		}
		super.signOut();
	}

}
