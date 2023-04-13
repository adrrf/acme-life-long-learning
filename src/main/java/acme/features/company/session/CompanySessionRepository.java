
package acme.features.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("select s from Session s where s.practicum.id = :id")
	Collection<Session> findManySessionsByPracticumId(int id);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select s from Session s where s.id = :id")
	Session findOneSessionById(int id);

	@Query("select s.practicum from Session s where s.id = :id")
	Practicum findOneSessionByPracticumId(int id);
}
