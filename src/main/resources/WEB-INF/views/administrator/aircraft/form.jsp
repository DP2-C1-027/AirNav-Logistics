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
	<acme:input-textbox code="administrator.aircraft.form.label.model" path="model"/>
	<acme:input-textbox code="administrator.aircraft.form.label.registrationNumber" path="registrationNumber"/>
	<acme:input-textbox code="administrator.aircraft.form.label.capacity" path="capacity"/>
	<acme:input-textbox code="administrator.aircraft.form.label.cargoWeight" path="cargoWeight"/>
	<acme:input-select code="administrator.aircraft.form.label.status" path="status" choices="${types}"/>
	<acme:input-textbox code="administrator.aircraft.form.label.details" path="details"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="administrator.aircraft.form.label.confirmation" path="confirmation"/>
			<acme:submit code="administrator.aircraft.form.button.create" action="/administrator/aircraft/create?airlineId=${airlineId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update')}">
			<acme:input-checkbox code="administrator.aircraft.form.label.confirmation" path="confirmation"/>
			<acme:submit code="administrator.aircraft.form.button.update" action="/administrator/aircraft/update?airlineId=${airlineId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
