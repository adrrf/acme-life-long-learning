
package acme.features.students.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findManyCourse();

	@Query("select c from Course c where c.draftMode = false and c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select lr from Lecturer lr where  lr.id = :lecturerId")
	Lecturer findOneLecturerById(int lecturerId);

	/*
	 * @Query("select l from Lecture l where l.course.id = :id")
	 * Collection<Lecture> findManyLecturesByCourseId(int id);
	 */

}
