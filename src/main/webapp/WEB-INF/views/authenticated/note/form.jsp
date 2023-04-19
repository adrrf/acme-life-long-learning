<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.note.form.label.author" path="author"/>
	<acme:input-textarea code="authenticated.note.form.label.message" path="message"/>
	<acme:input-moment readonly = "true" code="authenticated.note.form.label.instantionMoment" path="instantionMoment"/>
	<acme:input-email code="authenticated.note.form.label.email" path="email"/>
	<acme:input-url code="authenticated.note.form.label.link" path="link"/>
	
	<jstl:choose>
	<jstl:when test="${_command == 'create'}">
	<acme:input-checkbox code="authenticated.note.form.button.confirmation" path="confirmation"/>
	<acme:submit code="authenticated.note.form.button.create" action="/authenticated/note/create"/>		</jstl:when>
	</jstl:choose>
</acme:form>