
package acme.components;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.forms.MoneyExchange;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface RateRepository extends AbstractRepository {

	@Query("select c from Configuration c")
	Collection<Configuration> getSystemConfiguration();

	@Query("select r.rate from Rate r where r.source = :source and r.target = :target")
	Double rate(String source, String target);

	@Query("select r.date from Rate r")
	Collection<Date> dates();

	default void getRate() {
		MoneyExchange exchange;
		Configuration config;
		String moneda;
		String[] aux;
		final List<String> monedas = new ArrayList<>();
		final Money money = new Money();
		final Tuple tuple;
		Collection<Date> dates;
		final Date lastDate;

		config = this.getSystemConfiguration().iterator().next();
		moneda = config.getCurrency();
		aux = config.getAcceptedCurrencies().split(";");

		for (final String s : aux)
			monedas.add(s);

		dates = this.dates();
		if (dates.isEmpty())
			for (final String m : monedas) {
				final Rate rate = new Rate();
				money.setAmount(1.0);
				money.setCurrency(m);
				exchange = MoneyExchangeRepository.computeMoneyExchange(money, moneda);
				rate.setSource(exchange.getSource().getCurrency());
				rate.setTarget(exchange.getTarget().getCurrency());
				rate.setRate(exchange.getTarget().getAmount());
				rate.setDate(MomentHelper.getCurrentMoment());
				this.save(rate);
			}
		else {
			Duration duration;
			lastDate = dates.iterator().next();

			duration = MomentHelper.computeDuration(lastDate, MomentHelper.getCurrentMoment());

			if (duration.toDays() > 1)
				for (final String m : monedas) {
					final Rate rate = new Rate();
					money.setAmount(1.0);
					money.setCurrency(m);
					exchange = MoneyExchangeRepository.computeMoneyExchange(money, moneda);
					rate.setSource(exchange.getSource().getCurrency());
					rate.setTarget(exchange.getTarget().getCurrency());
					rate.setRate(exchange.getTarget().getAmount());
					rate.setDate(MomentHelper.getCurrentMoment());
					this.save(rate);
				}
		}
	}

}
