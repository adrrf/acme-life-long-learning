
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ConfigurationRepository;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.BinderHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorUpdateService extends AbstractService<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Auditor object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneAuditorByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Auditor object) {
		assert object != null;

		super.bind(object, "firm", "professionalID", "certifications", "link");
	}

	@Override
	public void validate(final Auditor object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("firm")) {
			boolean status;
			String message;

			message = object.getFirm();
			status = this.configuration.hasSpam(message);

			super.state(!status, "firm", "authenticated.auditor.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("professionalID")) {
			boolean status;
			String message;

			message = object.getFirm();
			status = this.configuration.hasSpam(message);

			super.state(!status, "professionalID", "authenticated.auditor.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("certifications")) {
			boolean status;
			String message;

			message = object.getFirm();
			status = this.configuration.hasSpam(message);

			super.state(!status, "certifications", "authenticated.auditor.error.spam");
		}
	}

	@Override
	public void perform(final Auditor object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Auditor object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "firm", "professionalID", "certifications", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
