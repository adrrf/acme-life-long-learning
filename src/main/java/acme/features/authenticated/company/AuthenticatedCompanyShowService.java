
package acme.features.authenticated.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.BinderHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AuthenticatedCompanyShowService extends AbstractService<Authenticated, Company> {

	@Autowired
	protected AuthenticatedCompanyRepository repository;


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
		Company object;
		int id;

		id = super.getRequest().getData("id", int.class);

		object = this.repository.findOneCompanyByCompanyId(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Company object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "name", "VATnumber", "sumary", "link");

		super.getResponse().setData(tuple);
	}
}
