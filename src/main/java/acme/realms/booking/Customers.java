
package acme.realms.booking;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.constraints.ValidEarnedPoints;
import acme.constraints.ValidIdentifier;
import acme.constraints.ValidLongText;
import acme.constraints.ValidShortText;
import acme.datatypes.Phone;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customers extends AbstractRole {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@Automapped
	@ValidIdentifier
	@Column(unique = true)
	private String				identifier;

	@Mandatory
	@Automapped

	private Phone				phone;

	@Mandatory
	@Automapped
	@ValidLongText
	private String				physicalAddress;

	@Mandatory
	@Automapped
	@ValidShortText
	private String				city;

	@Mandatory
	@Automapped
	@ValidShortText
	private String				country;

	@Optional
	@Automapped
	@ValidEarnedPoints
	private Integer				earnedPoints;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
