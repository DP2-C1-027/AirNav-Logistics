/*
 * AuthenticatedFlightCrewMemberCreateService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.customers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.features.authenticated.GeneratorValidCode;
import acme.realms.Customers;

@GuiService
public class AuthenticatedCustomersCreateService extends AbstractGuiService<Authenticated, Customers> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedCustomersRepository repository;

	// AbstractService<Authenticated, Customers> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Customers.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Customers object;
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		object = new Customers();
		object.setUserAccount(userAccount);
		object.setCodigo(GeneratorValidCode.generateValidCode(object));

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Customers object) {

		super.bindObject(object, "codigo", "phone", "physicalAddress", "city", "country", "earnedPoints");
	}

	@Override
	public void validate(final Customers object) {

		String cod = object.getCodigo();
		if (cod.matches("^[A-Z]{2,3}\\d{6}$")) {
			Collection<Customers> codigo = this.repository.findCustomerCode(cod);
			if (!codigo.isEmpty())
				super.state(false, "codigo", "acme.validation.error.repeat-code");
		} else
			super.state(false, "codigo", "acme.validation.error.pattern-code");
	}

	@Override
	public void perform(final Customers object) {

		this.repository.save(object);
	}

	@Override
	public void unbind(final Customers object) {
		Dataset dataset;

		dataset = super.unbindObject(object, "codigo", "phone", "physicalAddress", "city", "country", "earnedPoints");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
