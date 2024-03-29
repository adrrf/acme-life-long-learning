<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.bulletin.list.label.instantiationMoment" path="instantiationMoment" width="5%"/>
	<acme:list-column code="administrator.bulletin.list.label.title" path="title"/>
	<acme:list-column code="administrator.bulletin.list.label.message" path="message"/>
</acme:list>

<acme:button code="administrator.bulletin.list.button.create" action="/administrator/bulletin/create"/>
