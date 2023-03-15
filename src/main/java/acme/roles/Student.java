
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.enrolment.Enrolment;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			statement;

	@NotBlank
	@Length(max = 101)
	protected String			strongFeatures;

	@NotBlank
	@Length(max = 101)
	protected String			weakFeatures;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@OneToOne

	@Valid
	protected Enrolment			enrolment;
}
