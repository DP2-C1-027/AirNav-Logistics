
package acme.features.airlineManager.flights;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Flight;
import acme.realms.AirlineManager;

@GuiService
public class AirlineManagerFlightShowService extends AbstractGuiService<AirlineManager, Flight> {

	@Autowired
	private AirlineManagerFlightRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Flight flight;
		AirlineManager manager;

		id = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(id);

		manager = flight == null ? null : flight.getAirlineManager();

		status = flight != null && super.getRequest().getPrincipal().hasRealm(manager);

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Flight flight;
		int id;

		id = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(id);

		super.getBuffer().addData(flight);
	}

	@Override
	public void unbind(final Flight flight) {

		Dataset dataset;
		dataset = super.unbindObject(flight, "tag", "indication", "cost", "description", "draftMode");

		super.getResponse().addData(dataset);
	}

}
