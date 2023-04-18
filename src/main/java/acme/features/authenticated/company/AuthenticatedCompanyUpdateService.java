
package acme.features.authenticated.company;

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
import acme.roles.Company;

@Service
public class AuthenticatedCompanyUpdateService extends AbstractService<Authenticated, Company> {

	@Autowired
	protected AuthenticatedCompanyRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Company object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneCompanyByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Company object) {
		assert object != null;

		super.bind(object, "name", "VATnumber", "sumary", "link");
	}

	@Override
	public void validate(final Company object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("name")) {
			boolean status;
			String message;

			message = object.getName();
			status = this.configuration.hasSpam(message);

			super.state(!status, "name", "authenticated.company.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("sumary")) {
			boolean status;
			String message;

			message = object.getSumary();
			status = this.configuration.hasSpam(message);

			super.state(!status, "sumary", "authenticated.company.error.spam");
		}

	}

	@Override
	public void perform(final Company object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Company object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "name", "VATnumber", "sumary", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
