<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="authenticated.audit.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textbox code="authenticated.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textbox code="authenticated.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-textbox readonly="true" code="authenticated.audit.form.label.mark" path="mark"/>
	
	<acme:button code="authenticated.audit.form.button.auditor" action="/authenticated/auditor/show?id=${auditor}"/>			
				
		
</acme:form>
