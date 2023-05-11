
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select aud from AuditingRecord aud where aud.audit.id = :id")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select aud from AuditingRecord aud where aud.id = :id")
	AuditingRecord findOneAuditingRecordById(int id);

	@Query("select aud.audit from AuditingRecord aud where aud.id = :id")
	Audit findOneAuditingRecordByAuditId(int id);

}
