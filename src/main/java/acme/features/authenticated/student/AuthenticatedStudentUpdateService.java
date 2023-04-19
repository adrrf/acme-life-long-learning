
package acme.features.authenticated.student;

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
import acme.roles.Student;

@Service
public class AuthenticatedStudentUpdateService extends AbstractService<Authenticated, Student> {

	@Autowired
	protected AuthenticatedStudentRepository	repository;

	@Autowired
	protected ConfigurationRepository			configuration;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Student object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneStudentByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Student object) {
		assert object != null;

		super.bind(object, "statement", "strongFeatures", "weakFeatures", "link");
	}

	@Override
	public void validate(final Student object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("statement")) {

			boolean status;
			String message;

			message = object.getStatement();
			status = this.configuration.hasSpam(message);

			super.state(!status, "statement", "authenticated.student.error.spam");

		}

		if (!super.getBuffer().getErrors().hasErrors("strongFeatures")) {

			boolean status;
			String message;

			message = object.getStrongFeatures();
			status = this.configuration.hasSpam(message);

			super.state(!status, "strongFeatures", "authenticated.student.error.spam");

		}

		if (!super.getBuffer().getErrors().hasErrors("weakFeatures")) {

			boolean status;
			String message;

			message = object.getWeakFeatures();
			status = this.configuration.hasSpam(message);

			super.state(!status, "weakFeatures", "authenticated.student.error.spam");

		}
	}

	@Override
	public void perform(final Student object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Student object) {
		Tuple tuple;

		tuple = BinderHelper.unbind(object, "statement", "strongFeatures", "weakFeatures", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
