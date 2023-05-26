
package acme.testing.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	Collection<Audit> findManyAuditsByAuditorUsername(String username);

	@Query("select aud from AuditingRecord aud where aud.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditorUsername(String username);

	@Query("select a.id from  Audit a where a.code = :code")
	int findIdByCode(String code);
}
