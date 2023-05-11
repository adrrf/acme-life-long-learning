
package acme.features.authenticated.audit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.entities.audit.Mark;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditShowService extends AbstractService<Authenticated, Audit> {

	@Autowired
	protected AuthenticatedAuditRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Audit audit;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditById(id);
		status = !audit.getDraftMode() && !audit.getCourse().getDraftMode();

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
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
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

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("mark", mark != null ? mark.toString() : "");
		tuple.put("auditor", object.getAuditor().getId());

		super.getResponse().setData(tuple);
	}
}
