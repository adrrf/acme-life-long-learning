
package acme.entities.audit;

import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	protected String			subject;

	@NotBlank
	@Length(max = 101)
	protected String			assessment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishPeriod;

	@NotNull
	protected Mark				mark;

	@URL
	protected String			link;

	protected boolean			correction;

	// Derived attributes -----------------------------------------------------


	@Transient
	private Period getAvailibilityPeriod() {

		final Period avaliabilityPeriod = Period.between(this.startPeriod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), this.finishPeriod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		return avaliabilityPeriod;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne
	@Valid
	@NotNull
	protected Audit audit;
}
