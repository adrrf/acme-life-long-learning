
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(final String username);

	@Query("select t.id from Tutorial t where t.code = :code")
	int findIdByCode(final String code);

}
