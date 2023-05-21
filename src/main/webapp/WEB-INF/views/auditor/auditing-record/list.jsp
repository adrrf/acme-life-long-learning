<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditing-record.list.label.subject" path="subject" width="20%"/>	
	<acme:list-column code="auditor.auditing-record.list.label.assessment" path="assessment" width="40%"/>
	<acme:list-column code="auditor.auditing-record.list.label.duration" path="duration" width="20%"/>
	<acme:list-column code="auditor.auditing-record.list.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.auditing-record.list.label.link" path="link" width="20%"/>
</acme:list>

<acme:button code="auditor.auditing-record.list.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>