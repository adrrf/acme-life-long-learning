
package acme.entities.audit;

import javax.persistence.Transient;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

	protected AuditRepository repo;


	@Transient
	public Mark getMarks(final AuditingRecord auditingRecord) {

		Mark result;

		assert auditingRecord.getMark() instanceof Mark;
		result = auditingRecord.getMark();

		return result;

	}

}
