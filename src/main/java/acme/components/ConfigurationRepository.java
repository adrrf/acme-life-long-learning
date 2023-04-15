
package acme.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Query;

import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;
import watchdog.Watchdog;

public interface ConfigurationRepository extends AbstractRepository {

	@Query("select c from Configuration c")
	Collection<Configuration> getSystemConfiguration();

	default boolean hasSpam(final String message) {
		final String language = LocaleContextHolder.getLocale().getLanguage();
		Configuration conf;
		List<String> spam;
		double threshold;

		conf = this.getSystemConfiguration().iterator().next();
		threshold = conf.getThreshold();

		if (language.equals("en"))
			spam = Arrays.asList(conf.getSpamWordsEn().split(";"));
		else
			spam = Arrays.asList(conf.getSpamWordsEs().split(";"));

		return Watchdog.hasSpam(message, spam, threshold);
	}
}
