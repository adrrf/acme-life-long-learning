
package acme.features.any.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findManyCourse();

	@Query("select c from Course c where c.draftMode = false and c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select l from Lecture l where l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id = :courseId)")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

}
