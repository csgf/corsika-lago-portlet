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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%//
  // Application peference Form
  //
  // This form defines values for the GirdEngine interaction
  // These parameters are:
  //	o 	bdiiHost - Hostname of the VO' top BDII
  //	o	pxServerHost - Hostname of the Proxy Robot server
  // 	o	pxRobotId - Proxy Robot Identifier
  //	o	RobotVO - Proxy Robot Virtual Organization
  // 	o	pxRobotRole - Proxy Robot Role
  //	o	pxUserProxy - A complete path to an user proxy
  //	o	pxRobotRenewalFlag - Proxy Robot renewal flag;
  //	 	(When specified it overrides the use of robot proxy) 
%>

<jsp:useBean id="pref_actionURL"                     class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_logLevel"                      class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_numInfrastructures"            class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_currInfrastructure"            class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_gridOperationId"               class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_gridOperationDesc"             class="java.lang.String" scope="request"/>
<!-- Infrastructure data (begin) -->
<jsp:useBean id="pref_enableInfrastructure"          class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_nameInfrastructure"            class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_acronymInfrastructure"         class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_bdiiHost"                      class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_wmsHosts"                      class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxServerHost"                  class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxServerPort"                  class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxServerSecure"                class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxRobotId"                     class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxRobotVO"                     class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxRobotRole"                   class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxRobotRenewalFlag"            class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_softwareTags"                  class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pxUserProxy"                   class="java.lang.String" scope="request"/>     
<!-- Infrastructure data (end) -->
<jsp:useBean id="pref_jobRequirements"               class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_pilotScript"                   class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_sciGwyUserTrackingDB_Hostname" class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_sciGwyUserTrackingDB_Username" class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_sciGwyUserTrackingDB_Password" class="java.lang.String" scope="request"/>
<jsp:useBean id="pref_sciGwyUserTrackingDB_Database" class="java.lang.String" scope="request"/>
<portlet:defineObjects />

<table>
<tr>
<td align="left" valign="top">
<img align="left" style="padding:10px 10px;" src="<%=renderRequest.getContextPath()%>/images/AppLogo.png" width="80%"/>
</td>
<td>
<p><h4>Application preferences</h4>
Edit the application preferences:
<p><input type="checkbox"  id="help" onclick="showHelp()" />Click here to get help on configuration values<br/></p>
<div style="display:none" id="show_help">
<ul>
  <li><b>Log Level</b> - Set the favorite log level: (trace,debug,info,warn,error,fatal)</li>
  <li><b>Application id</b> - GridEngine' User tracking DB application identifier</li>
  <li><b>Num Infrastructures</b> - Shows the number of infrastructures configured (not editable)</li>
  <li><b>For each infrastructure:</b>
      <ul>
      <li><b>Infrastructure name</b> - A description of the infrastructure</li>
      <li><b>Infrastructure acronym</b> - A short name that identifies the infrastructure</li>
      <li><b>BDII Host</b> - Hostname of the VO' top BDII</li>
      <li><b>Proxy Server</b> - Hostname of the Proxy Robot server</li>
      <li><b>Proxy Server port</b> - Port of the Proxy Robot server</li>
      <li><b>Proxy Secure connection</b> - Set 'true' for a secure connection</li>
      <li><b>RobotId</b> - Proxy Robot Identifier</li>
      <li><b>RobotVO</b> - Proxy Robot Virtual Organization</li>
      <li><b>RobotRole</b> - Proxy Robot Role</li>
      <li><b>RobotRenewal</b> - Proxy Robot renewal flag; if 'true' enables long job execution (&gt; 12hr)</li>
      <li><b>Software Tags</b> - This is a ';' separated list of software tags</li>
      <li><b>UserProxy</b> - A complete path to an user proxy (When specified it overrides the use of the robot proxy)</li>
      <p> Please use the <b>&lt;</b>/<b>&gt;</b> buttons to change infrastructure and <b>+</b>/<b>-</b> buttons to add or remove infrastructure settings.
      </ul>     
  <li><b>Pilot script</b> - Name of the script that controls the job execution into the infrastructure</li>
 
</ul>
</div>
Pressing the <b>'Set preferences'</b> Save the new preferences<br>
Pressing the <b>'Cancel'</b> No changes will be done to the current preferences<br>
Pressing the <b>'Reset'</b> Button all input fields will be initialized.
<br>
</td>
<tr>
</table>
<%
// Below the edit submission web form 
%>
<table>
<form id="<portlet:namespace />Preferences" action="<%=pref_actionURL %>" method="post">
    <input type="hidden" name="pref_action" id="actionId" value="none">
<tr>   
  <td></td>  
  <td align="center"><b>Generic preferences</b></td>
</tr>    
<tr>
  <td align="right"><b>Log Level </b></td>
  <td><input type="text" style="width: 320px; padding: 2px" name="pref_logLevel" id="logLevel" value="<%=pref_logLevel %>"></td>
</tr>
<tr>
  <td align="right"><b>Grid operation identifyer </b></td>
  <td><input type="text" style="width: 320px; padding: 2px" name="pref_gridOperationId" id="gridOperationId" value="<%=pref_gridOperationId %>"></td>
</tr>
<tr>
  <td align="right"><b>Infrastructure numbers </b></td>
  <td><input type="text" style="width: 320px; padding: 2px" name="pref_numInfrastructures" id="numInfrastructures" disabled="true" value="<%=pref_numInfrastructures %>"></td>
</tr>
<tr>
  <td align="right"><b>Grid operation description </b></td>
  <td><input type="text" style="width: 320px; padding: 2px" name="pref_gridOperationDesc" id="gridOperationDesc" disabled="true" value="<%=pref_gridOperationDesc %>"></td>
</tr>
<!--
     Infrastructure items
-->
<tr>   
  <td style="background-color:#d0d0d0;"></td>  
  <td style="background-color:#d0d0d0;" align="center"><b>Infrastructure preferences</b></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Enable infrastructure </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_enableInfrastructure" id="infraNumber" value="<%=pref_enableInfrastructure %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Infrastructure number </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_currInfrastructure" id="infraNumber" disabled="true" value="<%=pref_currInfrastructure %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Infrastructure name </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_nameInfrastructure" id="infraName" value="<%=pref_nameInfrastructure %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Infrastructure acronym </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_acronymInfrastructure" id="infraAcronym" value="<%=pref_acronymInfrastructure %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>BDII Host </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_bdiiHost" id="bdiiHost" value="<%=pref_bdiiHost %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>WMS Hosts </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_wmsHosts" id="wmsHosts" value="<%=pref_wmsHosts %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot host server </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxServerHost" id="pxServerHost" value="<%=pref_pxServerHost %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot host port </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxServerPort" id="pxServerPort" value="<%=pref_pxServerPort %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot secure connection </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxServerSecure" id="pxServerSecure" value="<%=pref_pxServerSecure %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot Identifier </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxRobotId" id="pxRobotId" value="<%=pref_pxRobotId %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot Virtual Organization </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxRobotVO" id="pxRobotVO" value="<%=pref_pxRobotVO %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot VO Role </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxRobotRole" id="pxRobotRole" value="<%=pref_pxRobotRole %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>Proxy Robot Renewal Flag </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxRobotRenewalFlag" id="pxRobotRenewalFlag" value="<%=pref_pxRobotRenewalFlag %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;">Local Proxy </td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_pxUserProxy" id="pxUserProxy" value="<%=pref_pxUserProxy %>"></td>
</tr>
<td align="right" style="color: blue; background-color:#d0d0d0;"><b>Software Tags </b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_softwareTags" id="softwareTags" value="<%=pref_softwareTags %>"></td>
</tr>
<!--
    Infrastructure control buttons: <, add, delete, >
-->
<tr> 
  <td style="background-color:#d0d0d0;" align="center">
      <table><tr>      
      <td>
        <input type="submit" onclick="setAction('previous');" value=" &lt; ">                        
      </td>     
      <td>      
        <input type="submit" onclick="setAction('remove');" value=" - ">      
      </td>
      <td>
        <input type="submit" onclick="setAction('add');" value=" + ">
      </td>
      <td>
        <input type="submit" onclick="setAction('next');" value=" &gt; ">
      </td>
      <tr></table>
  </td>
  <td style="background-color:#d0d0d0;" align="center">      
  </td>
</tr>
<!--
    Other application preferences
-->
<tr>
  <td align="right"><b>Application job requirements </b></td>
  <td><textarea name="pref_jobRequirements" id="jobRequirements" rows="1" cols="90%"><c:out value="<%=pref_jobRequirements %>" escapeXml="true"/></textarea></td>
</tr>
<tr>    
  <td align="center" ><input type="submit" onclick="setAction('viewPilot');" value="Application pilot job"></td>
  <td><input type="text" style="width: 320px; padding: 2px" name="pref_pilotScript" id="jobPilotScript" value="<%=pref_pilotScript %>"></td>  
</tr>
<!--
    UsersTrackingDB settings
-->
<tr>
  <td style="background-color:#d0d0d0;"></td>
  <td style="background-color:#d0d0d0;" align="center"><b>UsersTrackingDB Settings</b></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>UserTrackingDB Hostname</b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_sciGwyUserTrackingDB_Hostname" id="sciGwyUserTrackingDB_Hostname" value="<%=pref_sciGwyUserTrackingDB_Hostname %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>UserTrackingDB Username</b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_sciGwyUserTrackingDB_Username" id="sciGwyUserTrackingDB_Username" value="<%=pref_sciGwyUserTrackingDB_Username %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>UserTrackingDB Password</b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_sciGwyUserTrackingDB_Password" id="sciGwyUserTrackingDB_Password" value="<%=pref_sciGwyUserTrackingDB_Password %>"></td>
</tr>
<tr>
  <td align="right" style="color: blue; background-color:#d0d0d0;"><b>UserTrackingDB Database</b></td>
  <td><input type="text" style="color: blue; background-color: #f0f000; width: 320px; padding: 2px" name="pref_sciGwyUserTrackingDB_Database" id="sciGwyUserTrackingDB_Database" value="<%=pref_sciGwyUserTrackingDB_Database %>"></td>
</tr>
<!--
    Preference buttons
-->
<tr>
  <td></td>
  <td>
    <table align="center"><tr>
    <td>
      <input type="submit" id="setPreferences" value="Set preferences">
      </form>	
    </td>    
    <td>
      <input type="reset" value="Reset">
    </td>
    <td>
      <input type="submit" onclick="setAction('done');" value="Back to application">
    </td>    
    </tr></table>
</tr> 
</form>
</table>

<%
// Below the javascript functions used by the edit web form 
%>
<script type="text/javascript">
    
function setAction(actionValue) {
    var Action = document.getElementById("actionId");
    Action.value=actionValue;    
    // Submit the form
    document.forms[0].submit();
}    
// This function is responsible to enable all textareas
// when the user press the 'reset' form button
function resetForm() {
	// Get components
        var logLevel          =document.getElementById('logLevel');
        var numInfrastructures=document.getElementById('numInfrastructures');
        var infraNumber       =document.getElementById('infraNumber');
        var enableInfra       =document.getElementById('enableInfrastructure');
        var infraName         =document.getElementById('infraName');
        var infraAcronym      =document.getElementById('infraAcronym');
	var bdiiHost          =document.getElementById('bdiiHost');
	var wmsHosts          =document.getElementById('wmsHosts');
	var pxServerHost      =document.getElementById('pxServerHost');
        var pxServerPort      =document.getElementById('pxServerPort');
        var pxServerSecure    =document.getElementById('pxServerSecure');
	var pxRobotId         =document.getElementById('pxRobotId');
	var pxRobotVO         =document.getElementById('pxRobotVO');
	var pxRobotRole       =document.getElementById('pxRobotRole');
	var pxRobotRenewalFlag=document.getElementById('pxRobotRenewalFlag');
        var softwareTags      =document.getElementById('softwareTags');
	var pxUserProxy       =document.getElementById('pxUserProxy');                                                              
        var jobRequirements   =document.getElementById('jobRequirements');
        var jobPilotScript    =document.getElementById('jobPilotScript');
        var sciGwyUserTrackingDB_Hostname = document.getElementById('sciGwyUserTrackingDB_Hostname');
	var sciGwyUserTrackingDB_Username = document.getElementById('sciGwyUserTrackingDB_Username');
        var sciGwyUserTrackingDB_Password = document.getElementById('sciGwyUserTrackingDB_Password');
        var sciGwyUserTrackingDB_Database = document.getElementById('sciGwyUserTrackingDB_Database');
                
	// Assign last preference values
        logLevel.value          ="<%=pref_logLevel%>";
        numInfrastructures      ="<%=pref_numInfrastructures%>";
        infraNumber.value       ="<%=pref_currInfrastructure%>";
        enableInfra.value       ="<%=pref_enableInfrastructure%>";
        infraName.value         ="<%=pref_nameInfrastructure%>"
        infraAcronym.value      ="<%=pref_acronymInfrastructure%>"
	bdiiHost.value          ="<%=pref_bdiiHost%>";
	wmsHosts.value          ="<%=pref_wmsHosts%>";
	pxServerHost.value      ="<%=pref_pxServerHost%>";
        pxServerPort.value      ="<%=pref_pxServerPort%>";
        pxServerSecure.value    ="<%=pref_pxServerSecure%>";
	pxRobotId.value         ="<%=pref_pxRobotId%>";
	pxRobotVO.value         ="<%=pref_pxRobotVO%>";
        pxRobotRole.value       ="<%=pref_pxRobotRole%>";
	pxRobotRenewalFlag.value="<%=pref_pxRobotRenewalFlag%>";
        softwareTags            ="<%=pref_softwareTags%>";
	pxUserProxy.value       ="<%=pref_pxUserProxy%>";        
        jobRequirements.value   ="${fn:escapeXml(pref_jobRequirements)}";
        jobPilotScript.value    ="${fn:escapeXml(pref_jobPilotScript)}";
        sciGwyUserTrackingDB_Hostname = "<%=pref_sciGwyUserTrackingDB_Hostname%>";
        sciGwyUserTrackingDB_Username = "<%=pref_sciGwyUserTrackingDB_Username%>";
        sciGwyUserTrackingDB_Password = "<%=pref_sciGwyUserTrackingDB_Password%>";
        sciGwyUserTrackingDB_Database = "<%=pref_sciGwyUserTrackingDB_Database%>";
}

function showHelp() 
{
  if(document.getElementById("help").checked==true) {  
      
    document.getElementById("show_help").style.display="block";
  }
  else {
    document.getElementById("show_help").style.display="none";
  }
}
</script>

		
