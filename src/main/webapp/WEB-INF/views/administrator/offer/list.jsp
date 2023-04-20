<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.offer.list.label.instantiationMoment" path="instantiationMoment" width="5%"/>
	<acme:list-column code="administrator.offer.list.label.heading" path="heading"/>
	<acme:list-column code="administrator.offer.list.label.price" path="price"/>
</acme:list>

<acme:button code="administrator.offer.list.button.create" action="/administrator/offer/create"/>