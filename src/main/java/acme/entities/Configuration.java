
package acme.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

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

	@NotBlank
	protected String			acceptedCurrencies;

}
