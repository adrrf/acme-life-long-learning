
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditingRecordTestRepository extends AbstractRepository {

	@Query("select aud from AuditingRecord aud where aud.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecord> findManyAuditingRecordByAuditorUsername(String username);

	@Query("select aud.id from AuditingRecord aud where aud.subject = :subject")
	int findIdBySubject(String subject);
}
