<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.course.form.label.code"
		path="code" />
	<acme:input-textbox code="authenticated.course.form.label.title"
		path="title" />
	<acme:input-textarea code="authenticated.course.form.label.recap"
		path="recap" />
	<acme:input-money code="authenticated.course.form.label.retailPrice"
		path="retailPrice" />
	<acme:input-money readonly="true"
		code="authenticated.course.form.label.exchange" path="exchange" />
	<acme:input-url code="authenticated.course.form.label.link" path="link" />
	<acme:input-checkbox readonly="true"
		code="authenticated.course.form.label.isTheory" path="isTheory" />


</acme:form>

<acme:button code="authenticated.course.tutorial.button.list"
	action="/authenticated/tutorial/list?masterId=${id}" />


<acme:button code="authenticated.course.audit.button.list" action="/authenticated/audit/list?masterId=${id}"/>
	
<jstl:if test="${isAssistant}">
	<acme:button code="assistant.course.tutorial.button.create" action="/assistant/tutorial/create?courseId=${id}"/>
</jstl:if>

<jstl:if test="${isCompany}">
	<acme:button code="authenticated.course.practicum.button.list"
		action="/authenticated/practicum/list?masterId=${id}"/>
</jstl:if>

<jstl:if test="${isAuditor}">
	<acme:button code="auditor.course.audit.button.create" action="/auditor/audit/create?courseId=${id}"/>
</jstl:if>




