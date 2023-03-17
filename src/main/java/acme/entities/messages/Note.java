
package acme.entities.messages;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	protected Date				instantionMoment;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 76)
	@Pattern(regexp = "^[a-zA-Z0-9]+ - [a-zA-Z]+, [a-zA-Z]+$")
	protected String			author;

	@NotBlank
	@Length(max = 101)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;

}
