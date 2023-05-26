<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-moment readonly = "true" code="any.offer.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="any.offer.form.label.heading" path="heading"/>
	<acme:input-textarea code="any.offer.form.label.summary" path="summary"/>
	<acme:input-money code="any.offer.form.label.price" path="price"/>
	<acme:input-url code="any.offer.form.label.link" path="link"/>
	<acme:input-moment code="any.offer.form.label.startDate" path="startDate"/>
	<acme:input-moment code="any.offer.form.label.endDate" path="endDate"/>
	
</acme:form>
