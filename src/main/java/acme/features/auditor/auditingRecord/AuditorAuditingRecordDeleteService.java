
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordDeleteService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int recordId;
		AuditingRecord record;

		recordId = super.getRequest().getData("id", int.class);
		record = this.repository.findOneAuditingRecordById(recordId);
		status = record != null && record.getAudit().getDraftMode() && super.getRequest().getPrincipal().hasRole(record.getAudit().getAuditor())
			&& super.getRequest().getPrincipal().getUsername().equals(record.getAudit().getAuditor().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int recordId;

		recordId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordById(recordId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "startPeriod", "finishPeriod", "mark", "link");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "finishPeriod", "mark", "link");
		tuple.put("masterId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
