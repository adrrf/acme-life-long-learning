
package acme.components;

import org.springframework.web.client.RestTemplate;

import acme.forms.MoneyExchange;
import acme.framework.components.datatypes.Money;
import acme.framework.helpers.StringHelper;
import acme.framework.repositories.AbstractRepository;

public interface MoneyExchangeRepository extends AbstractRepository {

	public static MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result;
		RestTemplate api;
		ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount, targetAmount, rate;
		Money target;

		try {
			api = new RestTemplate();

			sourceCurrency = source.getCurrency();
			sourceAmount = source.getAmount();

			record = api.getForObject( //
				"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
				ExchangeRate.class, //
				sourceCurrency, //
				targetCurrency //
			);

			assert record != null;
			rate = record.getRates().get(targetCurrency);
			targetAmount = rate * sourceAmount;

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			result = new MoneyExchange();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setDate(record.getDate());
			result.setTarget(target);
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}
}
