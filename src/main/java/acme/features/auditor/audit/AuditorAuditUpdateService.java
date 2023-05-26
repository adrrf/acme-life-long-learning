
package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository	repository;

	@Autowired
	protected ConfigurationRepository	configuration;


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

		super.bind(object, "conclusion", "strongPoints", "weakPoints", "draftMode");
		object.setDraftMode(true);
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

		if (!super.getBuffer().getErrors().hasErrors("conclusion")) {
			boolean status;
			String message;

			message = object.getConclusion();
			status = this.configuration.hasSpam(message);

			super.state(!status, "conclusion", "auditor.audit.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("strongPoints")) {
			boolean status;
			String message;

			message = object.getStrongPoints();
			status = this.configuration.hasSpam(message);

			super.state(!status, "strongPoints", "auditor.audit.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("weakPoints")) {
			boolean status;
			String message;

			message = object.getWeakPoints();
			status = this.configuration.hasSpam(message);

			super.state(!status, "weakPoints", "auditor.audit.error.spam");
		}
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

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
