
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.draftMode = false and p.course.draftMode = false and p.course.id = :courseId")
	Collection<Practicum> findManyPracticum(int courseId);

	@Query("select p from Practicum p where p.id = :practicumId")
	Practicum findOnePracticumById(int practicumId);

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select s from Session s where s.practicum.id = :id")
	Collection<Session> findManySessionsByPracticumId(int id);
}
