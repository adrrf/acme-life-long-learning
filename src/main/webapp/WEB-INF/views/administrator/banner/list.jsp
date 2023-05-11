<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.instationUpdateMoment" path="instationUpdateMoment" width="5%"/>
	<acme:list-column code="administrator.banner.list.label.startTime" path="startTime"/>
	<acme:list-column code="administrator.banner.list.label.finishTime" path="finishTime"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan"/>
	<acme:list-column code="administrator.banner.list.label.linkPicture" path="linkPicture"/>
	<acme:list-column code="administrator.banner.list.label.linkDocument" path="linkDocument"/>
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>