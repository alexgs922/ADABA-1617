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
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>


	<form action="requestOffer/customer/listByKeyword.do" method="get">
	<label><spring:message code="requestOffer.search.keyword"/></label>
	<input type="text" name="keyword"/> <br />
	<br>
	<input type="submit" value="<spring:message code="requestOffer.search" />" /> 	 
	<input type="button" value="<spring:message code="requestOffer.return" />"
	onclick="javascript: history.back()" />
</form>

