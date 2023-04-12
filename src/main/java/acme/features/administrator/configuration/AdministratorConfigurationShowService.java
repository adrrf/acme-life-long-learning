
package acme.features.administrator.configuration;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorConfigurationShowService extends AbstractService<Administrator, Configuration> {

	@Autowired
	protected AdministratorConfigurationRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Configuration object;
		Collection<Configuration> objects;

		objects = this.repository.getSystemConfiguration();
		object = objects.iterator().next();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Configuration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "currency", "acceptedCurrencies");

		super.getResponse().setData(tuple);
	}

}
