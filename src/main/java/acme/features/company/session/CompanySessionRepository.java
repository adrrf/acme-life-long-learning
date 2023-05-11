
package acme.features.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("select ts from Session ts where ts.practicum.id = :id")
	Collection<Session> findManySessionsByPracticumId(int id);

	@Query("select t from Practicum t where t.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select ts from Session ts where ts.id = :id")
	Session findOneSessionById(int id);

	@Query("select ts.practicum from Session ts where ts.id = :id")
	Practicum findOneSessionByPracticumId(int id);

}
