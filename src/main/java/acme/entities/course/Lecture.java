
package acme.entities.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	@Column(unique = true)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			recap;

	@Min(0)
	@NotNull
	protected Integer			learningTime;

	@NotBlank
	@Length(max = 101)
	protected String			body;

	@NotNull
	protected Boolean			isTheory;

	@URL
	protected String			link;

	@NotNull
	protected Boolean			draftMode;

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	protected Lecturer			lecturer;

}
