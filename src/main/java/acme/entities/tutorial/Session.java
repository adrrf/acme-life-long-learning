
package acme.entities.tutorial;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			recap;

	@NotNull
	protected Boolean			isTheory;

	@NotNull
	protected LocalDateTime		startTime;

	@NotNull
	protected LocalDateTime		endTime;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial			tutorial;
}
