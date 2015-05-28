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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"      %>

<jsp:useBean id="gridOperationDesc" class="java.lang.String"          scope="request"/>
<jsp:useBean id="gridOperationId"   class="java.lang.Integer"         scope="request"/>
<jsp:useBean id="portal"            class="java.lang.String"          scope="request"/>
<jsp:useBean id="appPreferences"    class="it.infn.ct.AppPreferences" scope="request"/>
<portlet:defineObjects />

<table>
<tr>
<% if(gridOperationId > 0) { %>
    <td valign="top">
    <img align="left" style="padding:10px 10px;" src="<%=request.getContextPath()%>/images/AppLogo.png" />
    </td>
    <td>
    <h3>Application registered</h3>
    <p>This portlet has been registered with name: '<b><%=gridOperationDesc %></b>' in '<b><%=portal%></b>' portal having id: '<b><%=gridOperationId%></b>'.</p>    
    <p><input type="checkbox"  id="preferences" onclick="showPreferences()" />Click here to see application' default configurations<br/></p>
    <div style="display:none" id="show_preferences">
    <p><b>Application default settings</b></p><br/>
    <%=appPreferences.htmlDump() %>
    </div>
    <p>Press one of the following buttons:</br> 
    <ul>
        <li><b>'Activate'</b> to start using the application<br/></li>
        <li><b>'Configure'</b> to change the default settings<br/></li>
        <li><b>'About'</b> to get information about this application<br/></li>
    </ul>
    </p>
    <center>
    <table cellspacing="4">
    <tr>
        <td>    
        <form action="<portlet:actionURL portletMode="view">
              <portlet:param name="PortletStatus"   value="ACTION_ACTIVATE"/>
              <portlet:param name="gridOperationId" value="<%=""+gridOperationId %>"/>              
        </portlet:actionURL>" method="post">
        <input type="submit" value="Activate">
        </form>
        </td>
        <td>                  
         <form action="<portlet:renderURL portletMode="edit"></portlet:renderURL>" method="post">
         <input type="submit" value="Configure">           
        </form>    
        </td>
        <td>
        <form action="<portlet:actionURL portletMode="help"></portlet:actionURL>" method="post">
        <input type="submit" value="About">
        </form>    
        </td>
    </tr>
    </table>
    </center>
<% } else { %>
    <td valign="top">
    <img align="left" style="padding:10px 10px;" src="<%=renderRequest.getContextPath()%>/images/warning.gif" />
    </td>
    <td>
    <h3>Application not registered</h3>
    <p>This portlet could not be registered with name: '<b><%=gridOperationDesc %></b>' and '<b><%=portal%></b>' portal.
    <p>Please contact the portal administrator in order to verify the portal registration</p>
    <form action="<portlet:actionURL portletMode="view">
          <portlet:param name="PortletStatus" value="ACTION_NEEDCONF"/>
          <portlet:param name="gridOperationId" value="-1"/>
    </portlet:actionURL>" method="post">
    <input type="submit" value="Retry">
    </form>
    </td>
<% } %>
</tr>
</table>

<%
// Below the javascript functions used by the edit web form 
%>
<script type="text/javascript">

function showPreferences() 
{
  if(document.getElementById("preferences").checked==true) {  
      
    document.getElementById("show_preferences").style.display="block";
  }
  else {
    document.getElementById("show_preferences").style.display="none";
  }
}
</script>
