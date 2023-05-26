
package acme.features.students.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select l from Lecture l where l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id = :courseId)")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

}
