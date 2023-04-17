
package acme.features.authenticated.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuthenticatedAuditorRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select au from Auditor au where au.userAccount.id = :id")
	Auditor findOneAuditorByUserAccountId(int id);

	@Query("select au from Auditor au where au.id = :id")
	Auditor findOneAuditorByAuditorId(int id);

}
