/*
 * AuthenticatedFlightCrewMemberUpdateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.technician;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Technician;

@GuiService
public class AuthenticatedTechnicianUpdateService extends AbstractGuiService<Authenticated, Technician> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedTechnicianRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Technician.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Technician object;
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		object = this.repository.findTechnicianByUserAccountId(userAccountId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Technician object) {

		super.bindObject(object, "codigo", "phoneNumber", "specialisation", "annualHealthTest", "yearsOfExperience", "certifications");
	}

	@Override
	public void validate(final Technician object) {

		String cod = object.getCodigo();
		Collection<Technician> codigo = this.repository.findTechnicianCode(cod).stream().filter(x -> x.getId() != object.getId()).toList();

		if (!codigo.isEmpty())
			super.state(false, "codigo", "acme.validation.error.repeat-code");

	}

	@Override
	public void perform(final Technician object) {

		this.repository.save(object);
	}

	@Override
	public void unbind(final Technician object) {

		Dataset dataset;

		dataset = super.unbindObject(object, "codigo", "phoneNumber", "specialisation", "annualHealthTest", "yearsOfExperience", "certifications");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
