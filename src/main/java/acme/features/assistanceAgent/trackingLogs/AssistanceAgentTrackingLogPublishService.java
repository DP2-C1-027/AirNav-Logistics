/*
 * EmployerJobPublishService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistanceAgent.trackingLogs;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.claims.Indicator;
import acme.entities.claims.TrackingLog;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentTrackingLogPublishService extends AbstractGuiService<AssistanceAgent, TrackingLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentTrackingLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		AssistanceAgent assistance;
		boolean status;
		int claimId;
		TrackingLog claim;
		assistance = (AssistanceAgent) super.getRequest().getPrincipal().getActiveRealm();
		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findOneTrackingLogById(claimId);
		status = claim != null && claim.isDraftMode() && super.getRequest().getPrincipal().hasRealm(assistance);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrackingLog TrackingLog;
		int id;

		id = super.getRequest().getData("id", int.class);
		TrackingLog = this.repository.findOneTrackingLogById(id);

		super.getBuffer().addData(TrackingLog);
	}

	@Override
	public void bind(final TrackingLog TrackingLog) {
		super.bindObject(TrackingLog, "lastUpdateMoment", "stepUndergoing", "resolutionPercentage", "indicator", "claim");
	}

	@Override
	public void validate(final TrackingLog trackingLog) {
		if (trackingLog.getIndicator() != null && (trackingLog.getIndicator() == Indicator.ACCEPTED || trackingLog.getIndicator() == Indicator.REJECTED)) {

			super.state(trackingLog.getResolutionPercentage() != null && trackingLog.getResolutionPercentage() == 100, "resolutionPercentage", "assistance-agent.tracking-log.form.error.percentage-not-100");

			super.state(trackingLog.getResolutionDetails() != null && !trackingLog.getResolutionDetails().isEmpty(), "resolutionDetails", "assistance-agent.tracking-log.form.error.resolution-required");
		}
		//  porcentaje debe ser >= al último TrackingLog
		if (trackingLog.getClaim() != null && trackingLog.getClaim().getId() != 0 && !super.getBuffer().getErrors().hasErrors("resolutionPercentage")) {

			// Obtener el último TrackingLog ordenado por fecha descendente
			List<TrackingLog> previousLogs = this.repository.findTrackingLogsByClaimIdOrderedByDateDesc(trackingLog.getClaim().getId());

			if (!previousLogs.isEmpty()) {
				TrackingLog lastLog = previousLogs.get(0);

				super.state(trackingLog.getResolutionPercentage() != null && trackingLog.getResolutionPercentage() >= lastLog.getResolutionPercentage(), "resolutionPercentage", "assistance-agent.tracking-log.form.error.percentage-not-increasing");
			}
		}

		Claim claim = this.repository.findClaimByTrackingLogId(trackingLog.getId());
		boolean isValid = claim != null && !claim.isDraftMode();
		super.state(isValid, "*", "customer.booking.error.unpublishedPassengers.message");

	}

	@Override
	public void perform(final TrackingLog TrackingLog) {
		TrackingLog.setDraftMode(false);
		this.repository.save(TrackingLog);
	}

	@Override
	public void unbind(final TrackingLog trackingLog) {
		Dataset dataset;
		Collection<Claim> claims;
		AssistanceAgent assistance = (AssistanceAgent) super.getRequest().getPrincipal().getActiveRealm();

		claims = this.repository.findAllClaimsByAgent(assistance.getId());
		dataset = super.unbindObject(trackingLog, "lastUpdateMoment", "stepUndergoing", "resolutionPercentage", "indicator");

		SelectChoices claimsChoices = SelectChoices.from(claims, "passengerEmail", trackingLog.getClaim());
		dataset.put("claim", claimsChoices);

		SelectChoices statusChoices = SelectChoices.from(Indicator.class, trackingLog.getIndicator());
		dataset.put("indicator", statusChoices);

		super.getResponse().addData(dataset);
	}
}
