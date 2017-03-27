<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h3>
	<spring:message
		code="administrator.ratioOffersVsRequest" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="ratioOffersVsRequest" id="row">

	<spring:message
		code="administrator.ratioOffersVsRequest"
		var="ratioOffersVsRequest" />
	<display:column
		title="${ratioOffersVsRequest}"
		sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>

<h3>
	<spring:message
		code="administrator.averageNumberOfOffersAndRequestPerCustomer" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="averageNumberOfOffersAndRequestPerCustomer" id="row">

	<spring:message
		code="administrator.averageNumberOfOffersAndRequestPerCustomer"
		var="averageNumberOfOffersAndRequestPerCustomer" />
	<display:column
		title="${averageNumberOfOffersAndRequestPerCustomer}"
		sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>


<h3>
	<spring:message
		code="administrator.findAverageNumberApplicationsPerOfferOrRequest" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findAverageNumberApplicationsPerOfferOrRequest" id="row">

	<spring:message
		code="administrator.findAverageNumberApplicationsPerOfferOrRequest"
		var="findAverageNumberApplicationsPerOfferOrRequest" />
	<display:column
		title="${findAverageNumberApplicationsPerOfferOrRequest}"
		sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>

<h3>
	<spring:message
		code="administrator.findCustomersWithMoreAcceptedApplications" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findCustomersWithMoreAcceptedApplications" id="row">

	<spring:message code="administrator.customer.name" var="nameActor" />
	<display:column property="fullName" title="${nameActor}" sortable="false" />

	<spring:message code="administrator.customer.email" var="emailActor" />
	<display:column property="email" title="${emailActor}"
		sortable="false" />


</display:table>

<h3>
	<spring:message
		code="administrator.findCustomersWithMoreDeniedApplications" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findCustomersWithMoreDeniedApplications" id="row">

	<spring:message code="administrator.customer.name" var="nameActor" />
	<display:column property="fullName" title="${nameActor}" sortable="false" />

	<spring:message code="administrator.customer.email" var="emailActor" />
	<display:column property="email" title="${emailActor}"
		sortable="false" />


</display:table>


<h3>
	<spring:message code="administrator.findAverageNumberCommentsPerActorOfferRequest" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findAverageNumberCommentsPerActorOfferRequest" id="row">

	<spring:message code="administrator.findAverageNumberCommentsPerActorOfferRequest"
		var="findAverageNumberCommentsPerActorOfferRequest" />
	<display:column title="${findAverageNumberCommentsPerActorOfferRequest}" sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>

<h3>
	<spring:message code="administrator.averageNumberCommentsPostedByAdministratorAndCustomer" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="averageNumberCommentsPostedByAdministratorAndCustomer" id="row">

	<spring:message code="administrator.averageNumberCommentsPostedByAdministratorAndCustomer"
		var="averageNumberCommentsPostedByAdministratorAndCustomer" />
	<display:column title="${averageNumberCommentsPostedByAdministratorAndCustomer}" sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>

<h3>
	<spring:message
		code="administrator.findActorsWhoPostedPlus10PercentOfAverageNumberOfComments" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findActorsWhoPostedPlus10PercentOfAverageNumberOfComments" id="row">

	<spring:message code="administrator.actor.name" var="nameActor" />
	<display:column property="fullName" title="${nameActor}" sortable="false" />

	<spring:message code="administrator.actor.email" var="emailActor" />
	<display:column property="email" title="${emailActor}"
		sortable="false" />


</display:table>

<h3>
	<spring:message
		code="administrator.findActorsWhoPostedLess10PercentOfAverageNumberOfComments" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findActorsWhoPostedLess10PercentOfAverageNumberOfComments" id="row">

	<spring:message code="administrator.actor.name" var="nameActor" />
	<display:column property="fullName" title="${nameActor}" sortable="false" />

	<spring:message code="administrator.actor.email" var="emailActor" />
	<display:column property="email" title="${emailActor}"
		sortable="false" />


</display:table>


<h3>
	<spring:message
		code="administrator.minAvgMaxNumberMessagesSentPerActor" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="minAvgMaxNumberMessagesSentPerActor" id="row">

	<spring:message
		code="administrator.minAvgMaxNumberMessagesSentPerActor"
		var="minAvgMaxNumberMessagesSentPerActor" />
	<display:column title="${minAvgMaxNumberMessagesSentPerActor}"
		sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>

<h3>
	<spring:message
		code="administrator.minAvgMaxNumberMessagesReceivedPerActor" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="minAvgMaxNumberMessagesReceivedPerActor" id="row">

	<spring:message
		code="administrator.minAvgMaxNumberMessagesReceivedPerActor"
		var="minAvgMaxNumberMessagesReceivedPerActor" />
	<display:column title="${minAvgMaxNumberMessagesReceivedPerActor}"
		sortable="false">
		<fmt:formatNumber value="${row}" type="number" maxFractionDigits="3"
			minFractionDigits="3" />
	</display:column>


</display:table>

<h3>
	<spring:message
		code="administrator.findActorWithMoreMessagesSent" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findActorWithMoreMessagesSent" id="row">

	<spring:message code="administrator.actor.name" var="nameActor" />
	<display:column property="fullName" title="${nameActor}" sortable="false" />

	<spring:message code="administrator.actor.email" var="emailActor" />
	<display:column property="email" title="${emailActor}"
		sortable="false" />


</display:table>


<h3>
	<spring:message
		code="administrator.findActorWithMoreMessagesReceived" />
</h3>

<display:table class="displaytag" keepStatus="true"
	name="findActorWithMoreMessagesReceived" id="row">

	<spring:message code="administrator.actor.name" var="nameActor" />
	<display:column property="fullName" title="${nameActor}" sortable="false" />

	<spring:message code="administrator.actor.email" var="emailActor" />
	<display:column property="email" title="${emailActor}"
		sortable="false" />


</display:table>


