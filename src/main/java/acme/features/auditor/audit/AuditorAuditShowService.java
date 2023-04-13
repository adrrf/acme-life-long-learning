
package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

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
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);
		//Calcular el mark que mas se repita
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");

		super.getResponse().setData(tuple);
	}

}
