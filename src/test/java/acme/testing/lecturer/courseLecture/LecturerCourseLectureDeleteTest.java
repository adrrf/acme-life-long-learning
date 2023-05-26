
package acme.testing.lecturer.courseLecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseLectureDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String lecture) {

		super.signIn("lecturer01", "lecturer01");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Lectures");
		super.clickOnButton("Delete lecture");
		super.fillInputBoxIn("lecture", lecture);
		super.clickOnSubmit("Delete lecture");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Lectures");
		super.checkListingEmpty();

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
		super.request("/lecturer/course-lecture/delete-lecture");
		super.checkPanicExists();

		super.signIn("auditor01", "auditor01");
		super.request("/lecturer/course-lecture/delete-lecture");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student01", "student01");
		super.request("/lecturer/course-lecture/delete-lecture");
		super.checkPanicExists();
		super.signOut();
	}
}
