<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-moment readonly="true" code="administrator.banner.form.label.instationUpdateMoment" path="instationUpdateMoment"/>
	<acme:input-moment code="administrator.banner.form.label.startTime" path="startTime"/>
	<acme:input-moment code="administrator.banner.form.label.finishTime" path="finishTime"/>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.form.label.linkPicture" path="linkPicture"/>
	<acme:input-url code="administrator.banner.form.label.linkDocument" path="linkDocument"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>
