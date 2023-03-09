
package acme.entities.enrolment;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WorkBook extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;
	// Attributes -------------------------------------------------------------

	@NotBlank
	protected String			roles;

	@NotNull
	protected Boolean			draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	protected List<Activity>	activities;

	@OneToOne(optional = false)
	@Valid
	@NotNull
	protected Enrolment			enrolment;

}
