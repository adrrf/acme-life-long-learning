<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.recap" path="recap"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.goals" path="goals"/>
	<acme:input-integer readonly = "true" code="authenticated.tutorial.form.label.time" path="estimatedTime"/>

	<acme:button code="authenticated.tutorial.form.button.assistant" action="/authenticated/assistant/show?id=${assistant}"/>			
				
		
</acme:form>
