
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.audit.AuditingRecord;
import acme.entities.audit.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;


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

		if (!super.getBuffer().getErrors().hasErrors("subject")) {
			boolean status;
			String message;

			message = object.getSubject();
			status = this.configuration.hasSpam(message);

			super.state(!status, "subject", "auditor.auditing-record.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("assessment")) {
			boolean status;
			String message;

			message = object.getAssessment();
			status = this.configuration.hasSpam(message);

			super.state(!status, "assessment", "auditor.auditing-record.error.spam");
		}

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "finishPeriod", "mark", "link");
		tuple.put("masterId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().getDraftMode());
		tuple.put("markes", choices);

		super.getResponse().setData(tuple);
	}

}
