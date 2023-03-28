
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findManyTutorialSessionsByTutorialId(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select ts from TutorialSession ts where ts.id = :id")
	TutorialSession findOneTutorialSessionById(int id);

	@Query("select ts.tutorial from TutorialSession ts where ts.id = :id")
	Tutorial findOneTutorialSessionByTutorialId(int id);

}
