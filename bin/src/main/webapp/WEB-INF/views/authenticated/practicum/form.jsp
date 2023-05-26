<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.practicum.form.label.code" path="name"/>
	<acme:input-textbox code="authenticated.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.practicum.form.label.recap" path="recap"/>
	<acme:input-textarea code="authenticated.practicum.form.label.goals" path="goals"/>
	<acme:input-integer readonly = "true" code="authenticated.practicum.form.label.totalTime" path="totalTime"/>
	
	<acme:button code="authenticated.practicum.form.button.company" action="/authenticated/company/show?id=${company}"/>
</acme:form>
