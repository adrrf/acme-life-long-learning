
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.entities.audit.Mark;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditPublishService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		object.setDraftMode(false);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit audit;
			String code;

			code = object.getCode();
			audit = this.repository.findOneAuditByCode(code);
			super.state(audit == null || audit.equals(object), "code", "auditor.audit.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		int id;

		Collection<AuditingRecord> record;
		Collection<Mark> marks;

		id = super.getRequest().getData("id", int.class);

		record = this.repository.findManyAuditingRecordByAuditId(id);

		marks = record.stream().map(r -> r.getMark()).collect(Collectors.toList());

		final Map<Mark, Integer> countMap = new HashMap<>();
		for (final Mark e : marks)
			countMap.put(e, countMap.getOrDefault(e, 0) + 1);

		Mark mark = null;
		int maxCount = 0;
		for (final Map.Entry<Mark, Integer> entry : countMap.entrySet())
			if (entry.getValue() > maxCount || entry.getValue() == maxCount && (mark == null || entry.getKey().ordinal() < mark.ordinal())) {
				mark = entry.getKey();
				maxCount = entry.getValue();
			}

		object.setMark(mark);
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");

		super.getResponse().setData(tuple);
	}

}
