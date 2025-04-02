/*
 * AssistanceAgentClaimCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistanceAgent.claims;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.claims.ClaimType;
import acme.entities.legs.Leg;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimCreateService extends AbstractGuiService<AssistanceAgent, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		AssistanceAgent assistance;
		boolean status;
		assistance = (AssistanceAgent) super.getRequest().getPrincipal().getActiveRealm();

		status = super.getRequest().getPrincipal().hasRealm(assistance);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Claim claim;
		AssistanceAgent assistance;
		assistance = (AssistanceAgent) super.getRequest().getPrincipal().getActiveRealm();
		claim = new Claim();
		claim.setRegistrationMoment(MomentHelper.getCurrentMoment());
		claim.setRegisteredBy(assistance);
		claim.setDraftMode(true);

		super.getBuffer().addData(claim);
	}

	@Override
	public void bind(final Claim claim) {
		claim.setDraftMode(true);

		super.bindObject(claim, "registrationMoment", "passengerEmail", "description", "indicator", "linkedTo", "type");

	}

	@Override
	public void validate(final Claim claim) {
		;
	}

	@Override
	public void perform(final Claim claim) {
		this.repository.save(claim);
	}

	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;
		Collection<Leg> legs;
		legs = this.repository.findAllLegs();

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "indicator", "type");
		SelectChoices legsChoices = SelectChoices.from(legs, "flightNumber", claim.getLinkedTo());
		SelectChoices statusChoices = SelectChoices.from(ClaimType.class, claim.getType());
		dataset.put("typeChoice", statusChoices);
		dataset.put("linkedTo", legsChoices);

		super.getResponse().addData(dataset);
	}

}
