<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" name="customer" id="row">
	
	

	<!-- Attributes -->
	
	<spring:message code="customer.fullName" var="fullName" />
	<display:column property="fullName" title="${fullName}" sortable="true" />

	<spring:message code="customer.email" var="email" />
	<display:column property="email" title="${email}" sortable="true"/>
	
	<spring:message code="customer.phone" var="phone" />
	<display:column property="phone" title="${phone}" sortable="false" />
	

</display:table>

<display:table pagesize="5" class="displaytag" name="comments"
	requestURI="${requestURI}" id="row">

	<spring:message code="comment.title" var="title" />
	<display:column title="${title}" sortable="false" >
		<jstl:out value="${row.title}"></jstl:out>
	
	</display:column>
	
	<spring:message code="comment.text" var="text" />
	<display:column title="${text}" sortable="false" >
		<jstl:out value="${row.text}"></jstl:out>
	
	</display:column>
	
	<spring:message code="comment.stars" var="stars" />
	<display:column title="${stars}" sortable="false" >
		<jstl:out value="${row.stars}"></jstl:out>
	
	</display:column>
	
	<security:authorize access = "hasRole('ADMIN')">
		<spring:message code="comment.banned" var="banned" />
		<display:column property = "banned" title="${banned}" sortable="false" >
		
	
		</display:column>
	
	</security:authorize>
	
	
	</display:table>
	<security:authorize access="hasRole('CUSTOMER')">

		<a href="comment/create.do?commentableEntityId=${customer.id}">
					<spring:message code="create.comment" />
		</a>

	</security:authorize>