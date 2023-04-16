
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("select lr from Lecturer lr where lr.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findAllLecturesOfLecturerId(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :courseId")
	Collection<Lecture> findLecturesOfCourseId(int courseId);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

}
