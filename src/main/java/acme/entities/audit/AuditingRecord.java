
package acme.entities.audit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			subject;

	@NotBlank
	@Length(max = 101)
	protected String			assessment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				startPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishPeriod;

	@NotNull
	protected Mark				mark;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	//	@Transient
	//	protected Period			avaliabilityPeriod	= Period.between(this.startPeriod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), this.finishPeriod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

	// Relationships ----------------------------------------------------------
}
