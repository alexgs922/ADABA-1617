<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" name="applications"
	requestURI="${requestURI}" id="row">

	<spring:message code="application.requestOffer" var="applicationRequestOffer" />
	<display:column title="${applicationRequestOffer}" sortable="true" >
		<jstl:out value="${row.requestOffer.title}"></jstl:out>
	
	</display:column>
	
	<spring:message code="application.moment" var="applicationMoment" />
	<display:column title="${applicationMoment}" sortable="true" >
		<jstl:out value="${row.requestOffer.moment}"></jstl:out>
	
	</display:column>
	
	<spring:message code="application.status" var="applicationStatus" />
	<display:column property = "status" title="${applicationStatus}" sortable="true" />
	
	
		
</display:table>

