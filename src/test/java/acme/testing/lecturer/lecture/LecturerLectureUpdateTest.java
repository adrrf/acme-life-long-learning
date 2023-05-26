
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureUpdateTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String recap, final String learningTime, final String body, final String isTheory, final String link) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("isTheory", isTheory);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, recap);
		super.checkColumnHasValue(recordIndex, 2, learningTime);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("isTheory", isTheory);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String recap, final String learningTime, final String body, final String isTheory, final String link) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("recap", recap);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("isTheory", isTheory);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer01");
		for (final Lecture lecture : lectures) {
			param = String.format("id=%d", lecture.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();

			super.signIn("lecturer02", "lecturer02");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor01", "auditor01");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student01", "student01");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
