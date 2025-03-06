
package acme.entities.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidPromotionCode;
import acme.constraints.ValidShortText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Service extends AbstractEntity {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidShortText
	@Automapped
	private String				name;

	@Mandatory
	@Automapped
	@ValidUrl
	private String				picture;

	@Optional
	@Automapped
	@Min(0)
	private Double				averageDwellTime;

	@Optional
	@Automapped
	@ValidPromotionCode
	@Column(unique = true)
	private String				promotionCode;

	@Optional
	@Automapped
	@ValidMoney
	private Money				money;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
