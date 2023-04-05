
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assistant extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	protected String			supervisor;

	@Pattern(regexp = "^(\\w+)(;\\w+)*;?$")
	@NotBlank
	@Length(max = 101)
	protected String			fields;

	@NotBlank
	@Length(max = 101)
	protected String			resume;

	@URL
	protected String			link;
}
