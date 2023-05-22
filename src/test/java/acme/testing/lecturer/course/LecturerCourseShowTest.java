
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;

public class LecturerCourseShowTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String recap, final String retailPrice, final String link, final String draftMode, final String isTheory) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("recap", recap);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
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

		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer01");
		for (final Course course : courses)
			if (course.getDraftMode()) {
				param = String.format("id=%d", course.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();

				super.signIn("lecturer02", "lecturer02");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor01", "auditor01");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student01", "student01");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
