<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-moment readonly = "true" code="any.peep.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="any.peep.form.label.title" path="title"/>
	<acme:input-textbox code="any.peep.form.label.nick" path="nick"/>
	<acme:input-textarea code="any.peep.form.label.message" path="message"/>
	<acme:input-email code="any.peep.form.label.email" path="email"/>
	<acme:input-url code="any.peep.form.label.link" path="link"/>
	
	<acme:submit test="${_command == 'create'}" code="any.peep.form.button.create" action="/any/peep/create"/>
	
</acme:form>
