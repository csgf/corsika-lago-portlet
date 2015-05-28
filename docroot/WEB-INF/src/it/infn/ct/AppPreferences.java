/**************************************************************************
Copyright (c) 2011:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on
the copyright holders.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author <a href="mailto:riccardo.bruno@ct.infn.it">Riccardo Bruno</a>(COMETA)
****************************************************************************/
package it.infn.ct;

// Java
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

// Import AppLogger
import it.infn.ct.AppLogger;

// Importing GridEngine Job libraries 
import it.infn.ct.GridEngine.Job.*;
import it.infn.ct.GridEngine.Job.InfrastructureInfo;
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import it.infn.ct.GridEngine.Job.MultiInfrastructureJobSubmission;

/** 
 * This object is used to store the values of portlet preferences
 * The init method will initialize their values with corresponding init_*
 * variables when the portlet first starts (see init_Preferences var).
 * Please notice that not all init_* variables have a corresponding pref_* value
 *
 * @see AppInfrastructureInfo
 * 
 * @author <a href="mailto:riccardo.bruno@ct.infn.it">Riccardo Bruno</a>(COMETA)
 */
public class AppPreferences { 
    // Line separator
    // (!) Pay attention that altough the use of the LS variable
    //     the replaceAll("\n","") has to be used 
    private static final String LS = System.getProperty("line.separator");        
    
    private String                      gridOperationDesc;
    private String                      portletVersion;        
    private String                      logLevel;
    private String                      gridOperationId="-1";
    private String                      numInfrastructures;
    private List<AppInfrastructureInfo> appInfrastructuresInfo;    
    private String                      sciGwyUserTrackingDB_Hostname; 
    private String                      sciGwyUserTrackingDB_Username;  
    private String                      sciGwyUserTrackingDB_Password;
    private String                      sciGwyUserTrackingDB_Database;
    private String                      jobRequirements;
    private String                      pilotScript;
    
    // Following values range from 1 to numInfrastructures
    // It is used by the preference edit pane to scroll
    // among inserted infrastructures
    private int                     currPaneInfrastucture;
    private int                     inumInfrastructures;                
    
    // AppLogger
    AppLogger _log=null;
    
    /**
     * AppPreference standard constructor 
     * 
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public AppPreferences() { 
         gridOperationDesc
        =portletVersion
        =logLevel        
        =numInfrastructures
        =sciGwyUserTrackingDB_Hostname
        =sciGwyUserTrackingDB_Username
        =sciGwyUserTrackingDB_Password
        =sciGwyUserTrackingDB_Database                                                   
        =jobRequirements
        =pilotScript
        ="";

        // Initialize Infrastructure Info                
        appInfrastructuresInfo = new ArrayList<AppInfrastructureInfo>();

        // Initialize the paneInfrastructure;
         currPaneInfrastucture // 1-numInfrastructures
        =inumInfrastructures
        =0;                      
    } // AppPreferences
    
    /**
     * AppPreference constructor that assigns an AppLogger object
     * 
     * @param   _log AppLogger object isntance     
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public AppPreferences(AppLogger _log) {
        this();
        this._log=_log;
        _log.info("Logger linked to AppPreferences");        
    }     
        
    /**
     * Set the UserTrackingDB  Operation description (GridOperations)
     * 
     * @param   gridOperationDesc Grid operation description     
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setGridOperationDesc(String gridOperationDesc) {
        this.gridOperationDesc=gridOperationDesc;
    }
    
    /**
     * Get the UserTrackingDB  Operation description (GridOperations)
     *           
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getGridOperationDesc() {
        return gridOperationDesc;
    }
    
    /**
     * Set the UserTrackingDB  Operation identifier (GridOperations)
     * 
     * @param   gridOperationId Grid operation identifier number     
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setGridOperationId(String gridOperationId) {
        this.gridOperationId=gridOperationId;
    }
    
    /**
     * Get the UserTrackingDB  Operation identifier (GridOperations)
     *      
     * @return  Grid operation identifier number if null or empty returns "-1" string
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getGridOperationId() {
        return (   gridOperationId==null
                || gridOperationId.equals(""))?"-1":gridOperationId;
    }
    
    /**
     * Set the number of infrastructures
     * 
     * @param   numInfras The number of infrastructures 1-n     
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setNumInfrastructures(String numInfras) {
         numInfrastructures=numInfras;
        inumInfrastructures=Integer.parseInt(numInfras);   
        if(currPaneInfrastucture==0)
          currPaneInfrastucture=1; // by default assigns the 1st Infrastructure
        // For each unallocated Infrastructure create its space
        for(int i=this.appInfrastructuresInfo.size(); i<inumInfrastructures; i++)
            this.appInfrastructuresInfo.add(new AppInfrastructureInfo());
    } // setNumInfrastructures
    
    /**
     * Get the number of infrastructures
     * 
     * @return  The number of infrastructures
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public int getNumInfrastructures() {
        return inumInfrastructures;            
    } // getNumInfrastructure
    
    /**
     * Get the current infrastructure number (as int)
     * 
     * @return   Infrastructure number (as int)     
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public int getCurrInfrastructure() {
        return currPaneInfrastucture;            
    } // getCurrInfrastructure
    
    /**
     * Set the current infrastructure number (as String)
     * 
     * @param   currInfrastructure Infrastructure number (as String)     
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setCurrInfrastructure(String currInfrastructure) {
        this.currPaneInfrastucture=Integer.parseInt(currInfrastructure);
    } // setCurrInfrastructure
    
    /**
     * Switch to the previous infrastructure (currPaneInfrastructure)
     *           
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void switchNextInfrastructure() {
        if(inumInfrastructures > 0) {
            currPaneInfrastucture++;
            if(currPaneInfrastucture > inumInfrastructures)
                currPaneInfrastucture=1;
        }
    } // switchNextInfrastructure
    
    /**
     * Switch to the next infrastructure (currPaneInfrastructure)
     * 
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void switchPreviousInfrastructure() {
        if(inumInfrastructures > 0) {
            currPaneInfrastucture--;
            if(currPaneInfrastucture <= 0)
                currPaneInfrastucture = inumInfrastructures;  
        }
    } // switchPreviousInfrastructure
        
    /**
     * Remove the current infrastructure (currPaneInfrastructure); it is not possible
     * to have zero infrastructures.
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void delCurrInfrastructure() {
        // It is not possible to have zero Infrastructures
        if(inumInfrastructures > 1) {            
            AppInfrastructureInfo delInfrastructure = appInfrastructuresInfo.remove(currPaneInfrastucture-1);
            // 1st Pane remains unchanged
            if(currPaneInfrastucture > 1) 
                currPaneInfrastucture--;
            if(_log != null) 
                _log.info(LS+"Deleted infrastructure"
                         +LS+"----------------------"
                         +delInfrastructure.dump()
                        );
            // Now reduce the number of insfrastructures
            setNumInfrastructures(""+(--inumInfrastructures));            
        }
    } // delCurrInfrastructure
    
    /**
     * Add a new infrastructure assigning on it default values
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void addNewInfrastructure() {
        // Set adds the new infrastructure into the InfrastructureInfo list
        setNumInfrastructures(""+(++inumInfrastructures));
        // Now setup new default values to the new infrastructure
        appInfrastructuresInfo.get(inumInfrastructures-1).setInfrastructure(
                  AppInfrastructureInfo.DEF_enableInfrastructure
                , AppInfrastructureInfo.DEF_nameInfrastructure
                , AppInfrastructureInfo.DEF_acronymInfrastructure
                , AppInfrastructureInfo.DEF_bdiiHost
                , AppInfrastructureInfo.DEF_wmsHosts
                , AppInfrastructureInfo.DEF_pxServerHost
                , AppInfrastructureInfo.DEF_pxServerPort
                , AppInfrastructureInfo.DEF_pxServerSecure
                , AppInfrastructureInfo.DEF_pxRobotId
                , AppInfrastructureInfo.DEF_pxRobotVO
                , AppInfrastructureInfo.DEF_pxRobotRole
                , AppInfrastructureInfo.DEF_pxRobotRenewalFlag
                , AppInfrastructureInfo.DEF_pxUserProxy
                , AppInfrastructureInfo.DEF_softwareTags
               );        
        // Point the current infrastructure the new-one
        currPaneInfrastucture=inumInfrastructures;        
        // Show changes if possible
        if(_log != null) 
            _log.info(LS+"Added infrastructure"
                        +LS+"-----------------"
                        +appInfrastructuresInfo.get(inumInfrastructures-1).dump()
                    );
    } // addNewInfrastructure
    
    /**
     * Get the portlet logLevel (trace,info,debug,warning,error,fatal)
     * 
     * @return  Portlet logLevel
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getLogLevel() {
        return logLevel;
    }
    
    /**
     * Set the portlet logLevel 
     * 
     * @param   logLevel Portlet logLevel (trace,info,debug,warning,error,fatal)
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setLogLevel(String logLevel) {
        this.logLevel=logLevel;
    }
    
    
    /**
     * Set the portlet version (extracted from portlet.xml)
     * 
     * @param   portletVersion Portlet version
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setPortletVersion(String portletVersion) {
        this.portletVersion=portletVersion;
    }
    
    /**
     * Get the portlet version (extracted from portlet.xml)
     * 
     * @return  Portlet version
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPortletVersion() {
        return portletVersion;    
    }
    
    /**
     * Get the Job' requirements
     * 
     * @return  Job requirements
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getJobRequirements() {
        return jobRequirements;
    }
    
    /**
     * Set the Job' requirements
     * 
     * @param   jobRequirements Job requirements
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements=jobRequirements;
    }    
    
    /**
     * Get the Job' pilot script filename
     * 
     * @return  Pilot script file name (relative to docroot/WEBINF/)
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPilotScript() {
        return pilotScript;
    }
    
    /**
     * Set the Job' pilot script filename
     * 
     * @param   pilotScript Pilot script file name (relative to docroot/WEBINF/)
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public void setPilotScript(String pilotScript) {
        this.pilotScript=pilotScript;
    }
    
    /**
     * Get the current Pane infrastructure; the infrastructure index currently
     * shown in the preference window
     * 
     * @return  Current infrastructure index currently shown
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */    
    public int getCurrPaneInfrastructure() {
        return currPaneInfrastucture;
    }
    
    // Methods to access directly to the AppInfrastructureInfo items
    
    /**
     * Get the infrastrcture enable flag of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Enable flag (yes/no)
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */    
    public String getEnableInfrastructure(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getEnableInfrastructure();           
        return retValue;
    }
    
    /**
     * Get the infrastrcture name of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Infrastructure full name 
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getNameInfrastructure(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getNameInfrastructure();
        return retValue;
    }
    
    /**
     * Get the infrastrcture arconym of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Infrastructure Arcronym (short name)
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getAcronymInfrastructure(int i) {
       String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getAcronymInfrastructure();
        return retValue;
    }
    
    /**
     * Get the BDII hostname of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  BDII host
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */public String getBdiiHost(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getBdiiHost();
        return retValue;
    }
    
    /**
     * Get the ';' separated list of WMSes of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  WMS list
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getWmsHosts(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getWmsHosts();
        return retValue;
    }
    
    /**
     * Get the Robot proxy server hostname value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Robot proxy server hostname
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxServerHost(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxServerHost();
        return retValue;
    }
    
    /**
     * Get the Robot proxy server port value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Robot proxy server port number
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxServerPort(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxServerPort();
        return retValue;
    }
    
    /**
     * Get the Robot proxy secure connection flag value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Robot proxy secure connection flag value
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxServerSecure(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxServerSecure();
        return retValue;
    }
    
    /**
     * Get the Robot proxy identifier value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Robot proxy identifier
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxRobotId(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxRobotId();
        return retValue;
    }
    
    /**
     * Get the Robot proxy virtual organization value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  Robot virtual organization
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxRobotVO(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxRobotVO();
        return retValue;
    }
    
    /**
     * Get the Robot proxy role value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  RobotRole
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxRobotRole(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxRobotRole();
        return retValue;
    }
    
    /**
     * Get the Robot proxy RenewalFlag value of the i-th AppInfrastructureInfo object
     * 
     * @param   The infrastructure number (0-numInfrastrucrtures-1)
     * @return  RenewalFlag
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxRobotRenewalFlag(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxRobotRenewalFlag();
        return retValue;
    }
    
    /**
     * Get the UserProxy value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  UserProxy
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getPxUserProxy(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getPxUserProxy();
        return retValue;
    }
    
    /**
     * Get the SoftwareTag value of the i-th AppInfrastructureInfo object
     * 
     * @param   i The infrastructure number (0-numInfrastrucrtures-1)
     * @return  SoftwareTag
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo
     */
    public String getSoftwareTags(int i) {
        String retValue="";
        if(appInfrastructuresInfo != null)
            retValue=appInfrastructuresInfo.get(i).getSoftwareTags();
        return retValue;
    } 
    
    /**
     * Set the AppPreference Science Gateway' User Tracking DB database hostname    
     * 
     * @param   sciGwyUserTrackingDB_Hostname User Tracking DB database hostname
     * 
     * @see         AppPreferences
     * 
     */
    public void setSciGwyUserTrackingDB_Hostname(String sciGwyUserTrackingDB_Hostname) {
        this.sciGwyUserTrackingDB_Hostname=sciGwyUserTrackingDB_Hostname;
    }
    
    /**
     * Get the AppPreference Science Gateway' User Tracking DB database hostname    
     * 
     * @return  User tracking database hostname
     * 
     * @see         AppPreferences
     * 
     */
    public String getSciGwyUserTrackingDB_Hostname() {
        return sciGwyUserTrackingDB_Hostname;
    }
    
    /**
     * Set the AppPreference Science Gateway' User Tracking DB database username    
     * 
     * @param   sciGwyUserTrackingDB_Username User tracking database username
     * 
     * @see         AppPreferences
     * 
     */
    public void setSciGwyUserTrackingDB_Username(String sciGwyUserTrackingDB_Username) {
        this.sciGwyUserTrackingDB_Username=sciGwyUserTrackingDB_Username;
    }
    
    /**
     * Get the AppPreference Science Gateway' User Tracking DB database username    
     * 
     * @return  User tracking database username
     * 
     * @see         AppPreferences
     * 
     */
    public String getSciGwyUserTrackingDB_Username() {
        return sciGwyUserTrackingDB_Username;
    }
    
    /**
     * Set the AppPreference Science Gateway' User Tracking DB database password    
     * 
     * @param   sciGwyUserTrackingDB_Password User tracking database password
     * 
     * @see         AppPreferences
     * 
     */
    public void setSciGwyUserTrackingDB_Password(String sciGwyUserTrackingDB_Password) {
        this.sciGwyUserTrackingDB_Password=sciGwyUserTrackingDB_Password;
    }
    
    /**
     * Get the AppPreference Science Gateway' User Tracking DB database password    
     * 
     * @return  User tracking database password
     * 
     * @see         AppPreferences
     * 
     */
    public String getSciGwyUserTrackingDB_Password() {
        return sciGwyUserTrackingDB_Password;
    }
    
    /**
     * Set the AppPreference Science Gateway' User Tracking DB database name    
     * 
     * @param   sciGwyUserTrackingDB_Database User tracking database name
     * 
     * @see         AppPreferences
     * 
     */
    public void setSciGwyUserTrackingDB_Database(String sciGwyUserTrackingDB_Database) {
        this.sciGwyUserTrackingDB_Database=sciGwyUserTrackingDB_Database;
    }
    /**
     * Get the AppPreference Science Gateway' User Tracking DB database name    
     * 
     * @return  User tracking database name
     * 
     * @see         AppPreferences
     * 
     */
    public String getSciGwyUserTrackingDB_Database() {
        return sciGwyUserTrackingDB_Database;
    }
    
    // 
    /**
     * Assigns to the i-th AppInfrastructureInfo object all its values 
     * (i: 0-numInfrastructures-1). If i > numInfrastructure a new infrastructure
     * object will be created
     * 
     * @param       i                     Infrastructure number (0 - numInfrastrucrtures-1)
     * @param       enableInfrastructure  Infrastructure enabling flag
     * @param       nameInfrastructure    Infrastructure full name
     * @param       acronymInfrastructure Infrastructure short name
     * @param       bdiiHost              BDII host
     * @param       wmsHosts              A ';' separated string of WMSes
     * @param       pxServerHost          Robot proxy server host
     * @param       pxServerPort          Robot proxy server port
     * @param       pxServerSecure        Robot proxy secure flag
     * @param       pxRobotId             Robot proxy identifier
     * @param       pxRobotVO             Robot proxy virtual organization
     * @param       pxRobotRole           Robot proxy role
     * @param       pxRobotRenewalFlag    Robot proxy renewal flag
     * @param       pxUserProxy           Path to a given proxy (overrides Robot)
     * @param       softwareTags          Infrastructure software tag
     * 
     * @see         AppInfrastructureInfo
     * @see         AppPreferences
     * 
     */
    public void setInfrastructure(
              int i
            , String enableInfrastructure
            , String nameInfrastructure
            , String acronymInfrastructure
            , String bdiiHost
            , String wmsHosts
            , String pxServerHost
            , String pxServerPort
            , String pxServerSecure
            , String pxRobotId
            , String pxRobotVO
            , String pxRobotRole
            , String pxRobotRenewalFlag
            , String pxUserProxy
            , String softwareTags
            ) {
        int j=i+1;
        if(j <= appInfrastructuresInfo.size())           
            appInfrastructuresInfo.get(i).setInfrastructure(                    
                                                  enableInfrastructure
                                                , nameInfrastructure
                                                , acronymInfrastructure
                                                , bdiiHost
                                                , wmsHosts
                                                , pxServerHost
                                                , pxServerPort
                                                , pxServerSecure
                                                , pxRobotId
                                                , pxRobotVO
                                                , pxRobotRole                    
                                                , pxRobotRenewalFlag                    
                                                , pxUserProxy
                                                , softwareTags
                                               );
        else appInfrastructuresInfo.add(new AppInfrastructureInfo(
                                                  enableInfrastructure
                                                , nameInfrastructure
                                                , acronymInfrastructure
                                                , bdiiHost
                                                , wmsHosts
                                                , pxServerHost
                                                , pxServerPort
                                                , pxServerSecure
                                                , pxRobotId
                                                , pxRobotVO
                                                , pxRobotRole                    
                                                , pxRobotRenewalFlag                    
                                                , pxUserProxy
                                                , softwareTags
                                            ));        
        // Updates the number of infrastructures
        numInfrastructures=""+(inumInfrastructures=appInfrastructuresInfo.size());   
        // Could be the 1st; assign to it the currPane
        if(inumInfrastructures==1) this.currPaneInfrastucture=1;
    } // setInfrastructure  
    
    
    /**
     * AppPreferences item names    
     * 
     * @see         AppPreferences
     * 
     */
    private enum Items {
         gridOperationDesc
        ,portletVersion     
        ,logLevel
        ,gridOperationId
        ,numInfrastructures       
        ,sciGwyUserTrackingDB_Hostname 
        ,sciGwyUserTrackingDB_Username  
        ,sciGwyUserTrackingDB_Password
        ,sciGwyUserTrackingDB_Database
        ,jobRequirements
        ,pilotScript
    }
    
    /**
     * Updates AppPreferences' value (prefValue) of a given preference item (prefItem)    
     * 
     * @param       prefItem  The preference item name
     * @param       prefValue The preference item value      
     * 
     * @see         AppPreferences.Items
     * @see         AppPreferences
     * 
     */
    public void updateValue(String prefItem, String prefValue ) {        
        switch(Items.valueOf(prefItem)) {
            case gridOperationDesc:
                if(   !prefValue.equals("")                  
                   && !gridOperationDesc.equals(prefValue)   
                  ) gridOperationDesc=prefValue;
                break;
            case portletVersion:
                if(   !prefValue.equals("")                  
                   && !portletVersion.equals(prefValue)   
                  ) portletVersion=prefValue;
                break;
            case logLevel:
                if(   !prefValue.equals("")               
                   && !logLevel.equals(prefValue)  
                  ) logLevel=prefValue;
                break;     
            case gridOperationId:
                if(   !prefValue.equals("")             
                   && !gridOperationId.equals(prefValue) 
                  ) gridOperationId=prefValue;
                break;    
            case numInfrastructures:
                if(   !prefValue.equals("")              
                   && !numInfrastructures.equals(prefValue)
                  ) setNumInfrastructures(prefValue);
                break;  
            case sciGwyUserTrackingDB_Hostname:
                if(   !prefValue.equals("")              
                   && !sciGwyUserTrackingDB_Hostname.equals(prefValue)
                  ) sciGwyUserTrackingDB_Hostname=prefValue;
                break;  
            case sciGwyUserTrackingDB_Username:
                if(   !prefValue.equals("")              
                   && !sciGwyUserTrackingDB_Username.equals(prefValue)
                  ) sciGwyUserTrackingDB_Username=prefValue;
                break;  
            case sciGwyUserTrackingDB_Password:
                if(   !prefValue.equals("")              
                   && !sciGwyUserTrackingDB_Password.equals(prefValue)
                  ) sciGwyUserTrackingDB_Password=prefValue;
                break;  
            case sciGwyUserTrackingDB_Database:
                if(   !prefValue.equals("")              
                   && !sciGwyUserTrackingDB_Database.equals(prefValue)
                  ) sciGwyUserTrackingDB_Database=prefValue;
                break; 
            case jobRequirements:
                if(   !prefValue.equals("")              
                   && !jobRequirements.equals(prefValue)
                  ) jobRequirements=prefValue;
                break;  
            case pilotScript:
                if(   !prefValue.equals("")              
                   && !pilotScript.equals(prefValue)
                  ) pilotScript=prefValue;
                break;  
            default:;
        }
    }
        
    /**
     * Updates the i-th InfrastructureInfo object assigning the 'prefValue' value to the
     * given 'prefItem'.     
     * 
     * @param       prefItem  Infrastructure number (0 <= i < numInfrastrucrtures)
     * @param       prefValue Preference item name
     * 
     * @see         updateInfrastructureValue
     * @see         AppInfrastructureInfo.Items
     * @see         AppInfrastructureInfo               
     * 
     */
    public void updateInfrastructureValue(int i, String prefItem, String prefValue) {        
        appInfrastructuresInfo.get(i).updateInfrastructureValue(prefItem,prefValue);
    }
    
    /**
     * Returns an array of GridEngine InfrastructureInfo object isntance related to
     * enabled instances of AppInfrastructureInfo items
     * 
     * @return Array of acrivated GridEngine' InfrastructureInfo infrastructures data
     */
    public InfrastructureInfo[] getEnabledInfrastructures() {
        //
        // Determine the number of enabled infrastructures
        //
        int numEnabledInfrastructures=0;
        for(int i=0; i<getNumInfrastructures(); i++) {
            // Update the GridEngine' InfrastructureInfo data
            appInfrastructuresInfo.get(i).updateInfrastructureInfo();
            // Counts the number of enabled infrastructures
            if(appInfrastructuresInfo.get(i).getEnableInfrastructure().equalsIgnoreCase("yes"))                    
            numEnabledInfrastructures++;
        }
        if(_log !=null) 
            _log.info("Enabled infrastructures: '"+numEnabledInfrastructures+"'");

        // Initialize the array of GridEngine' infrastructure objects
        InfrastructureInfo infrastructuresInfo[] = new InfrastructureInfo[numEnabledInfrastructures];
        // For each infrastructure
        for(int i=0,h=0; i<getNumInfrastructures(); i++) {
            int j=i+1;
            // Take care of wms list
            // GridEngine supports a list of WMSes as an array of string
            // while the AppPreferences uses a ';' separated list of entries
            // Following code makes the necessary conversion
            String wmsHostList[]=null;
            if(     null != getWmsHosts(i) 
                && !getWmsHosts(i).equals("")) {
                wmsHostList = getWmsHosts(i).split(";");
                String showWMSList=LS+"wmsHostList"
                                  +LS+"-----------";                                         
                for(int k=0; k<wmsHostList.length; k++)
                    showWMSList+=LS+wmsHostList[k];
                if(null != _log) _log.info(showWMSList);
            } // if wmsList
            if(appInfrastructuresInfo.get(i).getEnableInfrastructure().equalsIgnoreCase("yes")) {
                // Build the infrastructure object and assign it to the infrastructure array
                // (!)Not yet used values:
                //    pxServerSecure
                //    pxRobotRenewalFlag
                //    pxUserProxy                
                infrastructuresInfo[h++] = new InfrastructureInfo( getAcronymInfrastructure(i)
                                                                  ,             getBdiiHost(i)                                                                
                                                                  ,             wmsHostList
                                                                  ,         getPxServerHost(i)
                                                                  ,         getPxServerPort(i)
                                                                  ,            getPxRobotId(i)
                                                                  ,            getPxRobotVO(i)
                                                                  ,          getPxRobotRole(i)                                                                
                                                                  ,         getSoftwareTags(i)
                                                                 );                 
                // Shows the added infrastructure
                if(_log !=null) 
                    _log.info(LS+appInfrastructuresInfo.get(i).dump());
            } // Add enabled infrastructure
            else {
                if(_log !=null)
                    _log.info(LS+"Disabled infrastructure: "
                             +LS+appInfrastructuresInfo.get(i).dump());  
            }
        } // for each infrastructure
        return infrastructuresInfo;
    }
    
    /**
     * Returns a text string containing the values dump of a given InfrastructureInfo     
     * object. It also requires the inrastructure number.
     * 
     * @return      String with HTML statements inside a 'table' block
     * @param       i                  Infrastructure number (1-numInfrastrucrtures)
     * @param       infrastructureInfo InfrastructureInfo object
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo 
     * @see         dump
     * @see         htmlDump
     * 
     */
    private String dumpInfrastructure(int i, AppInfrastructureInfo infrastructureInfo) {
        String dump="";
        if(infrastructureInfo != null) {
            dump =LS+"Infrastructure #"+i;
            dump+=LS+infrastructureInfo.dump();
        }
        return dump;
    } // dumpInfrastructure    
        
    /**
     * Dump the i-th infrastructure
     * 
     * @param i The i-th infrastructure in the preference List (0<=i<appInfrastructuresInfo.size())
     * @return A text string with the infrastructure dump
     * 
     * @see         AppInfrastructureInfo 
     * @see         dump
     * @see         htmlDump     * 
     */
    public String dumpInfrastructure(int i) {
        String dump="";
        if(   appInfrastructuresInfo != null
          && 0 <= i
          &&      i < appInfrastructuresInfo.size()) {
           dump=dumpInfrastructure(i+1,appInfrastructuresInfo.get(i));
       } 
       return dump;
    }
    
    /**
     * Returns a text string containing all Infrastructure info values
     * included into the AppPreferences object
     *  
     * @return      String with HTML statements inside a 'table' block
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo 
     * @see         dump
     * @see         htmlDump
     * 
     */
    private String dumpInfrastructures() {
        String dump="";
        int i=1;
        Iterator it=appInfrastructuresInfo.iterator();
        while(it.hasNext())
            dump+=dumpInfrastructure(i++,(AppInfrastructureInfo)it.next());        
        return dump;
    } // dumpInfrastructures
        
    /**
     * Returns an html string containing the values dump of a given InfrastructureInfo     
     * object. It also requires the inrastructure number.
     * 
     * @return      String with HTML statements inside a 'table' block
     * 
     * @param       i                  Infrastructure number (1-numInfrastrucrtures)
     * @param       infrastructureInfo InfrastructureInfo object
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo 
     * @see         dump
     * @see         htmlDump
     * 
     */
    private String htmlDumpInfrastructure(int i, AppInfrastructureInfo infrastructureInfo) {
        String htmlDump="";
        if(infrastructureInfo != null) {            
            htmlDump =LS+"<b>Infrastructure #"+i+"</b>";
            htmlDump+=LS+infrastructureInfo.htmlDump();
        }
        return htmlDump;
    } // htmlDumpInfrastructure
    
    /**
     * Returns an HTML string containing a dump of the i-th infrastructure
     * 
     * @param i The i-th infrastructure in the preference List (0<=i<appInfrastructuresInfo.size())
     * @return A HTML string with the infrastructure dump
     * 
     * @see         AppInfrastructureInfo 
     * @see         dump
     * @see         htmlDump
     */
    public String htmlDumpInfrastructure(int i) {
        String dump="";
        if(   appInfrastructuresInfo != null
          && 0 <= i
          &&      i < appInfrastructuresInfo.size()) {
           dump=htmlDumpInfrastructure(i+1,appInfrastructuresInfo.get(i));
       } 
       return dump;
    }
    
    /**
     * Returns a html string containing all Infrastructure info values
     * included into the AppPreferences object
     *  
     * @return      String with HTML statements inside a 'table' block
     * 
     * @see         AppPreferences
     * @see         AppInfrastructureInfo 
     * @see         dump
     * @see         htmlDump
     * 
     */
    private String htmlDumpInfrastructures() {
        String htmlDump="";
        int i=1;
        Iterator it=appInfrastructuresInfo.iterator();
        while(it.hasNext())                              
            htmlDump+=htmlDumpInfrastructure(i++,(AppInfrastructureInfo)it.next());        
        return htmlDump;
    } // htmlDumpInfrastructures
    
    /**
     * Returns a text string containing the AppPreferences values dump
     *  
     * @return      String with HTML statements inside a 'table' block
     * 
     * @see         AppPreferences
     */
    public String dump() {
        String dump=LS+"Preference values:"
                    +LS+"-----------------"
                    +LS+"pref_logLevel                     : '"+logLevel                     +"'"    
                    +LS+"pref_gridOperationId              : '"+gridOperationId              +"'"
                    +LS+"pref_gridOperationDesc            : '"+gridOperationDesc            +"'"
                    +LS+"pref_numInfrastructures           : '"+numInfrastructures           +"'"
                    +LS+"pref_currInfrastructure           : '"+getCurrInfrastructure()      +"'"
                    +LS+dumpInfrastructures()                       
                    +LS+"pref_jobRequirements              : '"+jobRequirements              +"'"
                    +LS+"pref_pilotScript                  : '"+pilotScript                  +"'" 
                    +LS+"pref_sciGwyUserTrackingDB_Hostname: '"+sciGwyUserTrackingDB_Hostname+"'"
                    +LS+"pref_sciGwyUserTrackingDB_Username: '"+sciGwyUserTrackingDB_Username+"'"
                    +LS+"pref_sciGwyUserTrackingDB_Password: '"+sciGwyUserTrackingDB_Password+"'"
                    +LS+"pref_sciGwyUserTrackingDB_Database: '"+sciGwyUserTrackingDB_Database+"'"                  
                    +LS;
        return dump;
    } // dumpPreferences
    
    /**
     * Returns a html string containing the AppPreferences values dump
     *  
     * @return      String with HTML statements inside a 'table' block
     * 
     * @see         AppPreferences
     */
    public String htmlDump() {
        String htmlDump="<table border=\"1\" cellpadding=\"10\" cellspacing=\"2\">"
                   +LS+ "<tr><td><b>Log level</b></td><td>'"          +logLevel          +"'</td></tr>"
                   +LS+ "<tr><td><b>Num Infrastructures</b></td><td>'"+numInfrastructures+"'</td></tr>"
                   +LS+ "<tr><td><b>Grid operation Id</b></td><td>'"  +gridOperationId   +"'</td></tr>"
                   +LS+ "<tr><td><b>Grid opreation Desc</b></td><td>'"+gridOperationDesc +"'</td></tr>"
                   +LS+ "</table>"
                   +LS+ htmlDumpInfrastructures()
                   +LS+ "<table border=\"1\" cellpadding=\"10\" cellspacing=\"2\">"
                   +LS+ "<tr><td><b>JobRequirements</b></td><td>'"    +jobRequirements   +"'</td></tr>"
                   +LS+ "<tr><td><b>PilotScript</b></td><td>'"        +pilotScript       +"'</td></tr>"
                   +LS+ "</table>"
                   +LS;
        return htmlDump;
    } // htmlDump        
} // AppPreferences
