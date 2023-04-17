
package acme.features.authenticated.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.BinderHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedAssistantShowService extends AbstractService<Authenticated, Assistant> {

	@Autowired
	protected AuthenticatedAssistantRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Assistant object;
		int id;

		id = super.getRequest().getData("id", int.class);

		object = this.repository.findOneAssistantByAssistantId(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Assistant object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "supervisor", "fields", "resume", "link");

		super.getResponse().setData(tuple);
	}
}
