
package acme.entities.review;

import javax.persistence.Entity;
import javax.validation.constraints.Past;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.constraints.ValidLongText;
import acme.constraints.ValidReviewScore;
import acme.constraints.ValidShortText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review extends AbstractEntity {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@Automapped
	@ValidShortText
	private String				name;

	@Mandatory
	@Automapped
	@Past
	private java.util.Date		moment;

	@Mandatory
	@Automapped
	@ValidShortText
	private String				subject;

	@Mandatory
	@Automapped
	@ValidLongText
	private String				text;

	@Optional
	@Automapped
	@ValidReviewScore
	private Double				score;

	@Mandatory
	@Automapped
	private Boolean				recommended;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
