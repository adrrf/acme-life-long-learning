
package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Configuration extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	protected String			currency;

	@Pattern(regexp = ".+(;.+)*")
	@NotBlank
	protected String			acceptedCurrencies;

	@Pattern(regexp = ".+(;.+)*")
	@NotBlank
	protected String			spamWordsEn;

	@Pattern(regexp = ".+(;.+)*")
	@NotBlank
	protected String			spamWordsEs;

	@Min(0)
	@NotNull
	protected double			threshold;
}
