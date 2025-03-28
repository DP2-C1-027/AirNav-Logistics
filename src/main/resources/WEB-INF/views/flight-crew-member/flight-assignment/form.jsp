<%--
- form.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form >
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.duty" path="duty" choices="${dutyChoices}" readonly="draftMode"/>
	
	<jstl:if test="${_command == 'show'}">
		<acme:input-moment code="flight-crew-member.flight-assignment.form.label.moment" path="moment" readonly="true" placeholder = "acme.placeholders.form.flightAssignment.moment"/>
	</jstl:if>
	
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.currentStatus" path="currentStatus" choices="${statusChoices}" readonly="draftMode"/>
	<acme:input-textarea code="flight-crew-member.flight-assignment.form.label.remarks" path="remarks" readonly="draftMode" placeholder = "acme.placeholders.form.flightAssignment.remarks"/>
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.flightCrewMember" path="flightCrewMember" choices="${flightCrewMemberChoices}" readonly="${readOnlyCrewMember}"/>
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.leg" path="leg" choices="${legChoices}" readonly="draftMode" />
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="flight-crew-member.flight-assignment.form.button.create" action="/flight-crew-member/flight-assignment/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|publish') && draftMode == false}">
			<acme:button code="flight-crew-member.flight-assignment.form.button.activity-log" action="/flight-crew-member/activity-log/list?assignmentId=${id}"/>
			<acme:submit code="flight-crew-member.flight-assignment.form.button.update" action="/flight-crew-member/flight-assignment/update?assignmentId=${id}"/>
			<acme:submit code="flight-crew-member.flight-assignment.form.button.publish" action="/flight-crew-member/flight-assignment/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show') && draftMode}">
			<acme:button code="flight-crew-member.flight-assignment.form.button.activity-log" action="/flight-crew-member/activity-log/list?assignmentId=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
