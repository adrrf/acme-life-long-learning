
package acme.entities.enrolment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Activity extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			recap;

	@NotNull
	protected Boolean			isTheory;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				startDate;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				endDate;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	protected Enrolment			enrolment;

}
