
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findManyTutorialsByAssistantId(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select a from Assistant a where a.id = :id")
	Assistant findOneAssistantById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findOneTutorialByCode(String code);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findManyTutorialSessionsByTutorialId(int id);
}
