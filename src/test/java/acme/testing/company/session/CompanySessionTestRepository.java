
package acme.testing.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanySessionTestRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.userAccount.username = :username")
	Collection<Practicum> findManyPracticumsByCompanyUsername(final String username);

	@Query("select s from Session s where s.practicum.id = :id")
	Collection<Session> findManySessionsByPracticumId(final int id);
}
