
package acme.entities.messages;

import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 76)
	protected String			heading;

	@NotBlank
	@Length(max = 101)
	protected String			summary;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				startDate;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				endDate;

	protected Money				price;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Period getAvaliabilityPeriod() {
		final Period avaliabilityPeriod = Period.between(this.startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), this.endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		return avaliabilityPeriod;

	}

	// Relationships ----------------------------------------------------------

}
