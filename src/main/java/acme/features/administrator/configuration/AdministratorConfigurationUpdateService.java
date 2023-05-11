
package acme.features.administrator.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorConfigurationUpdateService extends AbstractService<Administrator, Configuration> {

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
	public void bind(final Configuration object) {
		assert object != null;

		super.bind(object, "currency", "acceptedCurrencies", "spamWordsEn", "spamWordsEs", "threshold");
	}

	@Override
	public void validate(final Configuration object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("currency")) {
			List<String> currencies;
			currencies = Arrays.asList(object.getAcceptedCurrencies().split(";"));
			super.state(currencies.contains(object.getCurrency()), "currency", "administrator.configuration.form.error.not-accepted");
		}
	}

	@Override
	public void perform(final Configuration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Configuration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "currency", "acceptedCurrencies", "spamWordsEn", "spamWordsEs", "threshold");

		super.getResponse().setData(tuple);
	}
}
