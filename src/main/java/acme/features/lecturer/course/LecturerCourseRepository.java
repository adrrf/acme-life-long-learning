
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.course.Lecture;
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

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId")
	Collection<CourseLecture> findManyCourseLectureByCourseId(int courseId);

	@Query("select cn from Configuration cn")
	Configuration findConfiguration();

	@Query("select l from Lecture l where l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id = :courseId)")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

}
