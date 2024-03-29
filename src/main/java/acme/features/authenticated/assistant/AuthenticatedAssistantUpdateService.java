
package acme.features.authenticated.assistant;

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
import acme.roles.Assistant;

@Service
public class AuthenticatedAssistantUpdateService extends AbstractService<Authenticated, Assistant> {

	@Autowired
	protected AuthenticatedAssistantRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Assistant object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneAssistantByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Assistant object) {
		assert object != null;

		super.bind(object, "supervisor", "fields", "resume", "link");
	}

	@Override
	public void validate(final Assistant object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("supervisor")) {
			boolean status;
			String message;

			message = object.getSupervisor();
			status = this.configuration.hasSpam(message);

			super.state(!status, "supervisor", "authenticated.assistant.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("fields")) {
			boolean status;
			String message;

			message = object.getSupervisor();
			status = this.configuration.hasSpam(message);

			super.state(!status, "fields", "authenticated.assistant.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("resume")) {
			boolean status;
			String message;

			message = object.getSupervisor();
			status = this.configuration.hasSpam(message);

			super.state(!status, "resume", "authenticated.assistant.error.spam");
		}
	}

	@Override
	public void perform(final Assistant object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Assistant object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "supervisor", "fields", "resume", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
