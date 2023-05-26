<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-integer code="assistant.dashboard.form.label.nTheoryTutorials" path="nTutorialsTheoricalCourse"/>
	<acme:input-integer code="assistant.dashboard.form.label.nHandsonTutorials" path="nTutorialsHandsOnCourse"/>
	<h2>
	<acme:message code="assistant.dashboard.form.label.statsTutorials"/>
	</h2>
	<table class="table table-sm">
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.average"/>
			</th>
			<td>
				<acme:print value="${ statsTutorialTime.get('average')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.deviation"/>
			</th>
			<td>
				<acme:print value="${ statsTutorialTime.get('deviation')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.min"/>
			</th>
			<td>
				<acme:print value="${ statsTutorialTime.get('min')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.max"/>
			</th>
			<td>
				<acme:print value="${ statsTutorialTime.get('max')}"/>
			</td>
		</tr>
	</table>
	<h2>
	<acme:message code="assistant.dashboard.form.label.statsSessions"/>
	</h2>
<table class="table table-sm">
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.average"/>
			</th>
			<td>
				<acme:print value="${ statsSessionTime.get('average')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.deviation"/>
			</th>
			<td>
				<acme:print value="${ statsSessionTime.get('deviation')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.min"/>
			</th>
			<td>
				<acme:print value="${ statsSessionTime.get('min')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="assistant.dashboard.form.label.max"/>
			</th>
			<td>
				<acme:print value="${ statsSessionTime.get('max')}"/>
			</td>
		</tr>
	</table>

</acme:form> 