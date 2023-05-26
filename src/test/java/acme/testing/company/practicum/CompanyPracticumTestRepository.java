
package acme.testing.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumTestRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.userAccount.username = :username")
	Collection<Practicum> findManyPracticumsByCompanyUsername(final String username);

	@Query("select p.id from Practicum p where p.code = :code")
	int findIdByCode(final String code);

}
