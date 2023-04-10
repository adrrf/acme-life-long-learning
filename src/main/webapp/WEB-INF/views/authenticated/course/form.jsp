<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="authenticated.course.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.course.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.course.form.label.recap" path="recap"/>
	<acme:input-money code="authenticated.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="authenticated.course.form.label.link" path="link"/>
	
</acme:form>

<acme:button code="assistant.course.tutorial.button.create" action="/assistant/tutorial/create?courseId=${id}"/>
