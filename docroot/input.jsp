
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



<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<%//
  // Multi infrastructure portlet 
  //
%>

<%
// Below the descriptive area of the GATE web form 
%>
<table>
	<tr>
		<td valign="top"><img align="left" style="padding: 10px 10px;"
			src="<%=renderRequest.getContextPath()%>/images/AppLogo.png" /></td>
		<td> 
			<b> CORSIKA, LAGO VERSION </b>
			<br>
			this portlet is devoted to execute Corsika in remote Grid infrastructures.			
			
			<br> Pressing the <b>'Reset'</b> Button all input fields
			will be initialized.<br> Pressing the <b>'About'</b> Button
			information about the application will be shown
		</td>
	<tr>
</table align="center">
<%
// Below the application submission web form 
//
// The <form> tag contains a portlet parameter value called 'PortletStatus' the value of this item
// will be read by the processAction portlet method which then assigns a proper view mode before
// the call to the doView method.
// PortletStatus values can range accordingly to values defined into Enum type: Actions
// The processAction method will assign a view mode accordingly to the values defined into
// the Enum type: Views. This value will be assigned calling the function: setRenderParameter
//
%>
<center>
	<form enctype="multipart/form-data"
		action="<portlet:actionURL portletMode="view"><portlet:param name="PortletStatus" value="ACTION_SUBMIT"/></portlet:actionURL>"
		method="post">
		<dl>
		
			<dd>
				<p>
					<b> Corsika version</b>
				</p>
				<p>
					<select name="corsikaVersion">
						   <option value="corsika-73500-lago-single">LAGO single</option> 
						   <option value="corsika-73500-lago-array">LAGO array</option> 
						   <! -- <option value="corsika-73500-lago-epos-thining">LAGO epos-thining</option> -->
						   <option value="corsika-73500-lago-thin-qsj2">LAGO epos-thining-qsj2</option>

					</select>
				</p>
				</dd>
			
			<!-- This block contains: label, file input and textarea for GATE Macro file -->

			<dd>
			
				<p>
					<b>Is it a parametric job? </b> </br>
					If so, remember that input file must be a .tar.gz 
					with a single folder containing all the inputs, and 
					that folder must ONLY contain inputs
				</br>
				<input type="radio" name="parametricJob" value="no" checked="checked">No
				<input type="radio" name="parametricJob" value="yes">Yes
			</dd>
			
			<dd>
			
				<p>
					<b>Do you want to receive an email alert when the execution has finished? </b> </br>
				</br>
				<input type="radio" name="emailWhenFinished" value="no" checked="checked">No
				<input type="radio" name="emailWhenFinished" value="yes">Yes
			</dd>
			
			<dd>
			
				<p>
					<b>Do you want to store the results in a remote Storage Element? </b> </br>
				</br>
				<input type="radio" name="useStorageElement" value="no" checked="checked">No
				<input type="radio" name="useStorageElement" value="yes">Yes
			</dd>
			
						
			<dd>
					<p>
					<b>Input data</b> 
					<br>
					There are two options: local input file, or PID of a remote input file. 
				</p>

				
		
				<p>
					Local input file:   <input type="file"
						name="file_inputFile" id="upload_inputFileId" accept="*.*"
						onchange="uploadInputFile()" />
				</p>
				<p>PID of remote input file:   <input type="text"
						name="jobPID" id="jobPID" />
				</p>
			</dd>
			<dd>

			<!-- This block contains the experiment name -->
			<dd>
				<p>
					<b>job identifyer</b>
					<br>
					Choose a name to identify your job in this page (by default it is set to the name of the input file). 
				</p>
				<textarea id="jobIdentifierId" rows="1" cols="60%"
					name="JobIdentifier">Corsika</textarea>
			</dd>
			<!-- This block contains form buttons: Demo, SUBMIT and Reset values -->
			<dd>
				<td><input type="button" value="Submit" onClick="preSubmit()"></td>
				<td><input type="reset" value="Reset values"
					onClick="resetForm()"></td>
			</dd>
		</dl>
	</form>
	
	
	</table>
</center>

<%
// Below the javascript functions used by the GATE web form 
%>
<script type="text/javascript">
//
// preSubmit
//
function preSubmit() {  
    var jobPID=document.getElementById('jobPID');
    var inputFileName=document.getElementById('upload_inputFileId');
    var jobIdentifier=document.getElementById('jobIdentifierId');
    
    var state_inputFileName=false;
    var state_jobIdentifier=false;
    var state_jobPID=false;
    
    if(jobPID.value=="") state_jobPID=true;
    if(inputFileName.value=="") state_inputFileName=true;
    if(jobIdentifier.value=="") state_jobIdentifier=true;    
       
    var missingFields="";
    if ((state_jobPID) && (state_inputFileName)) {
    	
    	if(state_jobPID) missingFields+="  Job PID\n";
        if(state_inputFileName) missingFields+="  Input file\n";

    }
    if(state_jobIdentifier) missingFields+="  Job identifier\n";
    if(missingFields == "") {
      document.forms[0].submit();
    }
    else {
      alert("You cannot send an inconsisten job submission!\nMissing fields:\n"+missingFields);
        
    }
}
//
//  uploadMacroFile
//
// This function is responsible to disable the related textarea and 
// inform the user that the selected input file will be used
function uploadInputFile() {
	disabled = true;
	document.getElementById('jobPID').disabled = true;
	var inputFileName = document.getElementById('upload_inputFileId').value;
	inputFileName = inputFileName.split(/(\\|\/)/g).pop()
    	document.getElementById('jobIdentifierId').value = inputFileName;
    
}

//
//  resetForm
//
// This function is responsible to enable all textareas
// when the user press the 'reset' form button
function resetForm() {
	var currentTime = new Date();
	var jobPID=document.getElementById('jobPID').disabled = false;
	var inputFileName=document.getElementById('upload_inputFileId');
	var jobIdentifier=document.getElementById('jobIdentifierId');
        
        // Enable the textareas
	inputFileName.disabled=false;        			
            
	// Reset the job identifier
        jobIdentifier.value="Job execution of: "+currentTime.getDate()+"/"+currentTime.getMonth()+"/"+currentTime.getFullYear()+" - "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds();
}



</script>
