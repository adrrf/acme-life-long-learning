
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
public class AuditorAuditListService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Audit> objects;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyAuditsByAuditorId(auditorId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		int id;
		Collection<AuditingRecord> record;
		Collection<Mark> marks;

		id = object.getId();

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

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("mark", mark != null ? mark.toString() : "");

		super.getResponse().setData(tuple);
	}

}
