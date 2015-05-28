<%
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0"        prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<portlet:defineObjects />
<%//
  // View pilot script
  //
%>
<jsp:useBean id="pilotScript" class="java.lang.String" scope="request"/>

<%
// Below the descriptive area of the view pilot script
%>
<table>
<tr>
<td valign="top">
<img align="left" style="padding:10px 10px;" src="<%=renderRequest.getContextPath()%>/images/AppLogo.png" />
</td>
<td>
Please edit/view the selected pilot script and then press <b>'CHANGE'</b> button to apply your changes.<br>
Press <b>'Cancel'</b> to discard any change and go back to preferences.
</td>
<tr>
</table align="center">
<%
// Below the view/edit pilot script web form 
//
%>
<center>
<form action="<portlet:actionURL portletMode="view"><portlet:param name="PortletStatus" value="ACTION_PILOT"/></portlet:actionURL>" method="post">
<dl>	
	<!-- This block contains: label, file input and textarea for GATE Macro file -->
	<dd>
            <p><b>Pilot script</b>
        </dd>
        <dd>		 		 
	    <textarea style="font-family:monospace; font-size:12px;" id="pilotScript" rows="20" cols="100%" name="pilotScript"><%=pilotScript%></textarea>
	</dd>
	<!-- This block contains form buttons: CHANGE, Cancel and Reset values -->
  	<dd>
  		<td><input type="submit" value="CHANGE"></td>
  		<td><input type="reset" value="Reset values" onClick="resetForm()"></td>                
  	</dd>        
</dl>
</form>
<form action="<portlet:renderURL portletMode="edit">/></portlet:renderURL>" method="post">
    <dd>
        <td><input type="submit" value="Cancel"></td>
    </dd>
</form>
</table>
</center>

<%
// Below the javascript functions used by the GATE web form 
%>
<script type="text/javascript">
//
//  resetForm
//
// This function is responsible to enable all textareas
// when the user press the 'reset' form button
function resetForm() {
	var pilotScript=document.getElementById('pilotScript');
	pilotScript.value="${fn:escapeXml(pilotScript)}";            
}
</script>
