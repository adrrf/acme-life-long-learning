
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.entities.audit.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && super.getRequest().getPrincipal().getUsername().equals(audit.getAuditor().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		Audit audit;
		int auditId;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		object = new AuditingRecord();
		object.setAudit(audit);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		Audit audit;
		int auditId;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);

		super.bind(object, "subject", "assessment", "startPeriod", "finishPeriod", "mark", "url");
		object.setAudit(audit);

	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startPeriod") && !super.getBuffer().getErrors().hasErrors("finishPeriod"))
			if (!MomentHelper.isBefore(object.getStartPeriod(), object.getFinishPeriod()))
				super.state(false, "finishPeriod", "auditor.auditing-record.form.error.end-before-start");
			else {
				final int hours = (int) MomentHelper.computeDuration(object.getStartPeriod(), object.getFinishPeriod()).toHours();
				if (hours < 1)
					super.state(false, "startPeriod", "auditor.auditing-record.form.error.hour-ahead");

			}

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

		Audit audit;
		int auditId;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "finishPeriod", "mark", "link");
		tuple.put("audit", audit);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("markes", choices);
		tuple.put("draftMode", object.getAudit().getDraftMode());

		super.getResponse().setData(tuple);
	}
}
