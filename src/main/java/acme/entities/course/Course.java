
package acme.entities.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@Length(max = 101)
	@NotBlank
	protected String			recap;

	protected Boolean			isTheory;

	protected Money				retailPrice;

	protected Boolean			draftMode;

}
