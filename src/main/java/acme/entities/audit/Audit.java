
package acme.entities.audit;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9][0-9]{3}")
	protected String				code;

	@NotBlank
	@Length(max = 101)
	protected String				conclusion;

	@NotBlank
	@Length(max = 101)
	protected String				strongPoints;

	@NotBlank
	@Length(max = 101)
	protected String				weakPoints;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@OneToMany
	@Valid
	@NotNull
	protected List<AuditingRecord>	auditingRecords;
}
