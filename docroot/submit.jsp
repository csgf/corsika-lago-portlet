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

<portlet:defineObjects />
<%//
  // Application Submission page
  //
  //
  // The portlet code assigns the jobIdentifier as input parameter for this jsp file
  //
%>
<jsp:useBean id="jobIdentifier" class="java.lang.String" scope="request"/>

<%
// Below the submission web form
//
// It only have a button that will show the input form again for a new job submission
%>
<table>
<tr>
  <td valign="top"><img align="left" style="padding:10px 10px;" src="<%=renderRequest.getContextPath()%>/images/AppLogo.png" /></td>
  <td>
  Your job has been <b>successfully</b> submitted; you may get reference to it with identifier:<br>
  <b><%= jobIdentifier %></b><br>
  Have a look on <a href="/my-jobs">MyJobs</a> area to get more information about all your submitted jobs.
  </td>
</tr>
<tr>
<td align="center"><form action="<portlet:actionURL portletMode="view"><portlet:param name="PortletStatus" value="ACTION_INPUT"/></portlet:actionURL>" method="post">
<input type="submit" value="Run a new application"></form></td>
<td>Press the <b>Run a new application</b> button to start another job submission</td>
</tr>
</table>

