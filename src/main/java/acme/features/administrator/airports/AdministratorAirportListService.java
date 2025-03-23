
package acme.features.administrator.airports;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.airport.Airport;

@GuiService
public class AdministratorAirportListService extends AbstractGuiService<Administrator, Airport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorAirportRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Airport> anouncements;

		anouncements = this.repository.findAllAirport();

		super.getBuffer().addData(anouncements);
	}

	@Override
	public void unbind(final Airport airport) {
		Dataset dataset;

		dataset = super.unbindObject(airport, "name", "codigo", "operationalScope");

		super.getResponse().addData(dataset);

	}
}
