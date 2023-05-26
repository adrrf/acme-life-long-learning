
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(masterId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && super.getRequest().getPrincipal().getUsername().equals(audit.getAuditor().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyAuditingRecordsByAuditId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		java.time.Duration duration;

		duration = MomentHelper.computeDuration(object.getStartPeriod(), object.getFinishPeriod());

		tuple = super.unbind(object, "assessment", "mark", "link");

		if (object.isCorrection())
			tuple.put("subject", object.getSubject() + "*");
		else
			tuple.put("subject", object.getSubject());

		tuple.put("duration", duration.toHours());

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecord> objects) {
		assert objects != null;

		int masterId;
		Audit audit;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(masterId);
		showCreate = audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setGlobal("masterId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
