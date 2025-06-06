/*
 * FlightCrewMemberDashboardRepository.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.flightCrewMember.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flightAssignment.CurrentStatus;
import acme.entities.flightAssignment.FlightAssignment;

@Repository
public interface FlightCrewMemberDashboardRepository extends AbstractRepository {

	@Query("SELECT DISTINCT l.arrivalAirport.name FROM FlightAssignment f JOIN f.leg l WHERE f.flightCrewMember.id = :flightCrewMemberId ORDER BY f.moment DESC")
	List<String> findLastFiveDestinations(int flightCrewMemberId, Pageable pageable);

	@Query("SELECT COUNT(DISTINCT a.flightAssignment.leg) FROM ActivityLog a WHERE a.severityLevel BETWEEN :innerRange AND :outerRange")
	Integer countLegsWithSeverity(int innerRange, int outerRange);

	@Query("SELECT f FROM FlightAssignment f JOIN f.leg l WHERE f.flightCrewMember.id = :flightCrewMemberId ORDER BY l.scheduledArrival DESC")
	List<FlightAssignment> findFlightAssignment(int flightCrewMemberId, Pageable pageable);

	@Query("SELECT DISTINCT fa.flightCrewMember.userAccount.username FROM FlightAssignment fa WHERE fa.leg.id = :legId")
	List<String> findCrewMembersInLastLeg(int legId);

	@Query("SELECT COUNT(f) FROM FlightAssignment f WHERE f.flightCrewMember.id = :flightCrewMemberId AND f.currentStatus = :status")
	int countFlightAssignmentsByStatus(int flightCrewMemberId, CurrentStatus status);

	@Query("SELECT COUNT(f) FROM FlightAssignment f WHERE f.moment >= :moment AND f.flightCrewMember.id = :crewMemberId")
	Integer countFlightAssignmentsLastYear(Date moment, int crewMemberId);

	@Query("SELECT COUNT(fa) FROM FlightAssignment fa WHERE fa.flightCrewMember.id = :crewMemberId AND EXTRACT(YEAR FROM fa.leg.scheduledArrival) = :year AND EXTRACT(MONTH FROM fa.leg.scheduledArrival) = :month")
	Integer countFlightAssignmentsPerMonthAndYear(int crewMemberId, int year, int month);

}
