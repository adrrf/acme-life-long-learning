
package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select a from Company a where a.userAccount.id = :id")
	Company findOneCompanyByUserAccountId(int id);

	@Query("select c from Company c where c.id = :id")
	Company findOneCompanyByCompanyId(int id);
}
