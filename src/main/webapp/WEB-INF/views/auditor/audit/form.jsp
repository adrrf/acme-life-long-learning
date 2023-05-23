<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-textbox readonly="true" code="auditor.audit.form.label.mark" path="mark"/>
		</jstl:when>
	</jstl:choose>	
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
	<acme:input-textbox readonly="true" code="auditor.audit.form.label.code" path="code"/>
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	<acme:input-textbox readonly="true" code="auditor.audit.form.label.mark" path="mark"/>
		</jstl:when>
	</jstl:choose>
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.audit.form.button.auditing-record" action="/auditor/auditing-record/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="auditor.audit.form.button.auditing-record" action="/auditor/auditing-record/list?masterId=${id}"/>
			<acme:button code="auditor.audit.form.button.create.auditing-record" action="/auditor/auditing-record/create?masterId=${id}"/>
			<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
			<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			<acme:submit code="auditor.audit.form.button.publish" action="/auditor/audit/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create?courseId=${courseId}"/>
		</jstl:when>
	</jstl:choose>

</acme:form>