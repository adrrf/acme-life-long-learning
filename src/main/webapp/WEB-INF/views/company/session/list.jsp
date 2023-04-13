<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.session.list.label.title" path="title" width="10%"/>
	<acme:list-column code="company.session.list.label.recap" path="recap" width="40%"/>
	<acme:list-column code="company.session.list.label.duration" path="duration" width="40%"/>
	<acme:list-column code="company.session.list.label.link" path="link" width="40%"/>
</acme:list>

<acme:button code="company.session.list.button.create" action="/company/session/create?masterId=${masterId}"/>