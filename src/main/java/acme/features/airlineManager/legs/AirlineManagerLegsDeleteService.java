
package acme.features.airlineManager.legs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.claims.Claim;
import acme.entities.flightAssignment.ActivityLog;
import acme.entities.flightAssignment.FlightAssignment;
import acme.entities.flights.Flight;
import acme.entities.legs.Leg;
import acme.entities.legs.LegStatus;
import acme.features.assistanceAgent.claims.AssistanceAgentClaimRepository;
import acme.features.flightCrewMember.activityLog.FlightCrewMemberActivityLogRepository;
import acme.features.flightCrewMember.flightAssignment.FlightCrewMemberFlightAssignmentRepository;
import acme.realms.AirlineManager;

@GuiService
public class AirlineManagerLegsDeleteService extends AbstractGuiService<AirlineManager, Leg> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AirlineManagerLegsRepository				repository;

	@Autowired
	private AssistanceAgentClaimRepository				claimRepository;

	@Autowired
	private FlightCrewMemberFlightAssignmentRepository	assignmentRepository;

	@Autowired
	private FlightCrewMemberActivityLogRepository		activityRepository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Leg leg;
		AirlineManager manager;

		masterId = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(masterId);
		manager = leg == null ? null : leg.getFlight().getAirlineManager();
		status = leg != null && leg.isDraftMode() && super.getRequest().getPrincipal().hasRealm(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Leg leg;
		int id;

		id = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(id);

		super.getBuffer().addData(leg);
	}

	@Override
	public void bind(final Leg leg) {

		super.bindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status", "departureAirport", "arrivalAirport", "aircraft", "flight");
	}

	@Override
	public void validate(final Leg leg) {
		;
	}

	@Override
	public void perform(final Leg leg) {
		Collection<Claim> claims;
		Collection<FlightAssignment> assignments;
		Collection<ActivityLog> activities;

		assignments = this.assignmentRepository.findFlightAssignmentsByLegId(leg.getId());
		activities = this.activityRepository.findActivityLogsByLegId(leg.getId());
		claims = this.claimRepository.findClaimsByLegId(leg.getId());

		this.claimRepository.deleteAll(claims);
		this.activityRepository.deleteAll(activities);
		this.assignmentRepository.deleteAll(assignments);
		this.repository.delete(leg);
	}

	@Override
	public void unbind(final Leg leg) {

		int airlineManagerId;
		Collection<Flight> flights;
		Collection<Airport> airports;
		Collection<Aircraft> aircrafts;
		SelectChoices choicesFlight;
		SelectChoices choicesArrivalAirports;
		SelectChoices choicesDepartureAirports;
		SelectChoices choicesAircraft;
		SelectChoices choicesStatus;
		Dataset dataset;

		airlineManagerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		flights = this.repository.findFlightsByAirlineManagerId(airlineManagerId);
		airports = this.repository.getAllAirports();
		aircrafts = this.repository.getAllAircrafts();
		choicesFlight = SelectChoices.from(flights, "tag", leg.getFlight());
		choicesArrivalAirports = SelectChoices.from(airports, "codigo", leg.getArrivalAirport());
		choicesDepartureAirports = SelectChoices.from(airports, "codigo", leg.getDepartureAirport());
		choicesAircraft = SelectChoices.from(aircrafts, "registrationNumber", leg.getAircraft());
		choicesStatus = SelectChoices.from(LegStatus.class, leg.getStatus());

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status", "draftMode", "flight", "arrivalAirport", "departureAirport", "aircraft");
		dataset.put("flight", choicesFlight.getSelected().getKey());
		dataset.put("flights", choicesFlight);
		dataset.put("arrivalAirport", choicesArrivalAirports.getSelected().getKey());
		dataset.put("arrivalAirports", choicesArrivalAirports);
		dataset.put("departureAirport", choicesDepartureAirports.getSelected().getKey());
		dataset.put("departureAirports", choicesDepartureAirports);
		dataset.put("aircraft", choicesAircraft.getSelected().getKey());
		dataset.put("aircrafts", choicesAircraft);
		dataset.put("statuses", choicesStatus);
		super.addPayload(dataset, leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status", "draftMode", "flight", "arrivalAirport", "departureAirport", "aircraft");

		super.getResponse().addData(dataset);
	}
}
