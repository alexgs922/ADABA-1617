<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="customer/register.do" modelAttribute="customer">


	<fieldset>
		<legend><spring:message code="customer.userAccountInfo"/></legend>
		
		<acme:textbox code="customer.username" path="username"/>
		<acme:password code="customer.password" path="password"/>
		<acme:password code="customer.passwordConf" path="passwordCheck"/>
	</fieldset>
			
	<fieldset>
		<legend><spring:message code="customer.info"/></legend>
		
		<acme:textbox code="customer.name" path="fullName"/>
		<acme:textbox code="customer.email" path="email"/>
		<acme:textbox code="customer.phone" path="phone"/>
	</fieldset>

	
	
	<form:checkbox path="termsOfUse"/>
	<spring:message code="customer.termsOfUse.confirmation"/> 
	<a href="customer/dataProtection.do">
		<spring:message code="customer.termsOfUse.link" />
	</a>
	<form:errors cssClass="error" path="termsOfUse" />
	
	<p class="mandatory"><spring:message code="mandatory.fields" /></p>
	
	<acme:submit name="save" code="consumer.accept"/>			
		
	<acme:cancel url="welcome/index.do" code="customer.cancel"/>
	
</form:form>