
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.BinderHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorShowService extends AbstractService<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorRepository repository;


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
		Auditor object;
		int id;

		id = super.getRequest().getData("id", int.class);

		object = this.repository.findOneAuditorByAuditorId(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Auditor object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "firm", "professionalID", "certifications", "link");

		super.getResponse().setData(tuple);
	}

}
