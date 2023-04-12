
package acme.features.lecturers.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select lr from Lecturer lr where lr.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.code = :code")
	Course findeOneCourseByCode(String code);

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	Collection<CourseLecture> findManyCourseLectureByCourseId(int id);

}
