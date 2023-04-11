
package acme.features.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.Session;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("select c from Company c where c.id = :id")
	Company findOneCompanyById(int id);

	@Query("select s from Session s where s.id = :id")
	Session findOneSessionById(int id);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select c from Company c where c.id in (select p.company.id from Practicum p where p.id = :practicumId)")
	Company findOneCompanyByPracticumId(int practicumId);

	@Query("select p from Practicum p")
	Collection<Practicum> findAllPractium();

	@Query("select s from Session s where s.practicum.id = :practicumId")
	Collection<Session> findManySessionsByPracticumId(int practicumId);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findManyPracticumsByCompanyId(int companyId);
}
