
package acme.features.authenticated.lecturer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface AuthenticatedLecturerRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select lr from Lecturer lr where lr.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);

}
