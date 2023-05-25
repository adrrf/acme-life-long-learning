<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="authenticated.course.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.course.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.course.form.label.recap" path="recap"/>
	<acme:input-money code="authenticated.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-money readonly="true" code="authenticated.course.form.label.exchange" path="exchange"/>
	<acme:input-url code="authenticated.course.form.label.link" path="link"/>
	<acme:input-checkbox readonly="true" code="authenticated.course.form.label.isTheory" path="isTheory"/>
	
	
</acme:form>

<acme:button code="authenticated.course.tutorial.button.list" action="/authenticated/tutorial/list?masterId=${id}"/>

<acme:button code="authenticated.course.audit.button.list" action="/authenticated/audit/list?masterId=${id}"/>
	
<acme:button code="assistant.course.tutorial.button.create" action="/assistant/tutorial/create?courseId=${id}"/>
<jstl:if test="hasRole('Assistant')">
	<acme:button code="assistant.course.tutorial.button.create" action="/assistant/tutorial/create?courseId=${id}"/>
</jstl:if>

<acme:button code="student.course.enrolment.button.create" action="/student/enrolment/create?courseId=${id}" />
<jstl:if test="hasRole('Company')">
	<acme:button code="company.course.practicum.button.create" action="/company/practicum/create?courseId=${id}"/>
</jstl:if>

<jstl:if test="hasRole('Student')">
	<acme:button code="student.course.enrolment.button.create" action="/student/enrolment/create?courseId=${id}"/>
</jstl:if>

<acme:button code="auditor.course.audit.button.create" action="/auditor/audit/create?courseId=${id}"/>

<acme:button code="company.course.practicum.button.create" action="/company/practicum/create?courseId=${id}"/>

<acme:button code="authenticated.course.practicum.button.list" action="/authenticated/practicum/list?masterId=${id}"/>

