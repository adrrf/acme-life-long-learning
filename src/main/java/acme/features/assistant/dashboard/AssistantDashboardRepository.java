
package acme.features.assistant.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Collection<TutorialSession> getTutorialSessionsByAssistantId(int id);

	@Query("select count(t) from Tutorial t where t.course.isTheory = true and t.assistant.id = :id")
	Integer getTotalTheoryTutorialsByAssistantId(int id);

	@Query("select count(t) from Tutorial t where t.course.isTheory = false and t.assistant.id = :id")
	Integer getTotalHandsOnTutorialsByAssistantId(int id);

	@Query("select avg(t.estimatedTime) from Tutorial t where t.assistant.id = :id")
	Double getAverageTutorialTimeByAssistantId(int id);

	@Query("select stddev(t.estimatedTime) from Tutorial t where t.assistant.id = :id")
	Double getDeviationTutorialTimeByAssistantId(int id);

	@Query("select min(t.estimatedTime) from Tutorial t where t.assistant.id = :id")
	Double getMinTutorialTimeByAssistantId(int id);

	@Query("select max(t.estimatedTime) from Tutorial t where t.assistant.id = :id")
	Double getMaxTutorialTimeByAssistantId(int id);
}
