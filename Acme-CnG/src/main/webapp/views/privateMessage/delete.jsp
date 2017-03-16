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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="message/delete.do" modelAttribute="privateMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox code="message.title" path="title" />
	<br>


	<acme:delete name="delete" code="message.delete"
		confirmationCode="<spring:message code="message.confirm.detele" />" />

	<acme:cancel url="welcome/index.do" code="message.cancel" />

</form:form>