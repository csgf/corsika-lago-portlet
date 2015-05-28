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

// Importing GridEngine Job libraries 
import it.infn.ct.GridEngine.Job.*;
import it.infn.ct.GridEngine.Job.InfrastructureInfo;
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import it.infn.ct.GridEngine.Job.MultiInfrastructureJobSubmission;

/**
 * Infrastructure information class stores the required information
 * to submit a job into a given infrastructure.
 * This class does not overrides directly the GridEngine' InfrastructureInfo
 * for two reasons:
 *    1) InfrastructureInfo class does not provides 'set' methods
 *       it can be only instanciated once upon creation and then cannot be 
 *       modified anymore
 *    2) Application portlets need some data extensions not used by the
 *       user tracking database InfrastructureInfo class
 * 
 * @see it.infn.ct.GridEngine.Job.InfrastructureInfo
 * 
 * @author <a href="mailto:riccardo.bruno@ct.infn.it">Riccardo Bruno</a>(COMETA)
 */ 
class AppInfrastructureInfo {
    // Line separator
    // (!) Pay attention that altough the use of the LS variable
    //     the replaceAll("\n","") has to be used 
    static final String LS = System.getProperty("line.separator");     
        
    // AppInfrastructureInfo default values
    public static final String DEF_enableInfrastructure ="yes/no";
    public static final String DEF_nameInfrastructure   ="Infrastructure name";
    public static final String DEF_acronymInfrastructure="Infrastructure acronym";     
    public static final String DEF_bdiiHost             ="BDII host";
    public static final String DEF_wmsHosts             ="WMS host";
    public static final String DEF_pxServerHost         ="Robot proxy server host";
    public static final String DEF_pxServerPort         ="Robot proxy server port";
    public static final String DEF_pxServerSecure       ="Robot proxy server secure flag";
    public static final String DEF_pxRobotId            ="Robot proxy id";
    public static final String DEF_pxRobotVO            ="Robot proxy VO"; 
    public static final String DEF_pxRobotRole          ="Robot proxy Role";
    public static final String DEF_pxRobotRenewalFlag   ="Robot proxy renewal flag";
    public static final String DEF_pxUserProxy          ="User proxy";
    public static final String DEF_softwareTags         ="Software tags";                       
    
    // GridEngine' InfrastructureInfo
    // This class owns its values and aligns them 
    // with the GridEngine class
    private InfrastructureInfo infrastructureInfo=null;
    
    // Extension values
    // The portlet code uses the following values
    private String enableInfrastructure;    // Flag that enables/disable the infrastructure   
    private String nameInfrastructure;      // Complete name of the infrastructure
    private String acronymInfrastructure;   // Infrastructure Acronym (short name)
    private String bdiiHost;                // topBDII host name
    private String wmsHosts;                // ';' separated list of enabled WMSes
    private String pxServerHost;            // eTokenServer hostname
    private String pxServerPort;            // eTokenServer port number
    private String pxServerSecure;          // eTokenServer secure connection flag
    private String pxRobotId;               // Robot proxy identifier
    private String pxRobotVO;               // Robot proxy VO
    private String pxRobotRole;             // Robot proxy role
    private String pxRobotRenewalFlag;      // Robot proxy renewal flag
    private String pxUserProxy;             // Holds a path to an User Proxy (test jobSubmissions)
    private String softwareTags;            // ';' separated list of software tags    
    
    /**
     * Standard constructor
     * 
     * Just initialize as empty all AppInfrastructureInfo fields
     * 
     * @see it.infn.ct.GridEngine.Job.InfrastructureInfo
     */
    AppInfrastructureInfo() {
        // Initialize  AppInfrastructureInfo 
         enableInfrastructure
        =nameInfrastructure
        =acronymInfrastructure
        =bdiiHost
        =wmsHosts
        =pxServerHost
        =pxServerPort
        =pxServerSecure
        =pxRobotId
        =pxRobotVO
        =pxRobotRole
        =pxRobotRenewalFlag        
        =pxUserProxy
        =softwareTags
        ="";                  
    } // AppInfrastructureInfo
        
    /**
     * Initialization with GridEngine' InfrastructureInfo values
     * Given values will be used to initialize the main object values as well
     * Missing items will be initialized with default values
     * 
     * @param name             Infrastructure name (equivalent to this.acronymInfrastructure
     * @param bdii             The BDII host
     * @param wmslist          The ';' separated list of enabled WMSes
     * @param etokenserver     The Robot proxy server host
     * @param etokenserverport The Robot proxy server port
     * @param proxyid          The Robot proxy server identifier
     * @param vo               The Robot proxy virtual organization
     * @param fqan             The Robot proxy roles
     */
    AppInfrastructureInfo(String name
                        , String bdii
                        , String wmslist[]
                        , String etokenserver
                        , String etokenserverport
                        , String proxyid
                        , String vo
                        , String fqan) {
        infrastructureInfo=new InfrastructureInfo(name
                                                , bdii
                                                , wmslist
                                                , etokenserver
                                                , etokenserverport
                                                , proxyid
                                                , vo
                                                , fqan);        
        // Now aligns the AppInfrastructureInfo values
        InfrastructureInfoToAppInfrastructureInfo();
    } // AppInfrastructureInfo
        
    /**
     * Initialization with GridEngine' InfrastructureInfo values (with software tag)
     * Given values will be used to initialize the main object values as well
     * Missing items will be initialized with default values
     * 
     * @param name             Infrastructure name (equivalent to this.acronymInfrastructure
     * @param bdii             The BDII host
     * @param wmslist          The ';' separated list of enabled WMSes
     * @param etokenserver     The Robot proxy server host
     * @param etokenserverport The Robot proxy server port
     * @param proxyid          The Robot proxy server identifier
     * @param vo               The Robot proxy virtual organization
     * @param fqan             The Robot proxy roles
     * @param swtag            The infrastructure' software tag
     * 
     * @see it.infn.ct.GridEngine.Job.InfrastructureInfo
     */
    AppInfrastructureInfo(String name
                        , String bdii
                        , String wmslist[]
                        , String etokenserver
                        , String etokenserverport
                        , String proxyid
                        , String vo
                        , String fqan
                        , String swtag) {
        infrastructureInfo=new InfrastructureInfo(name
                                                , bdii
                                                , wmslist
                                                , etokenserver
                                                , etokenserverport
                                                , proxyid
                                                , vo
                                                , fqan
                                                , swtag);        
        // Now aligns the AppInfrastructureInfo values
        InfrastructureInfoToAppInfrastructureInfo();
    } // AppInfrastructureInfo
        
    /**
     * Initialization with all AppInfrastructureInfo values
     *  
     * @param enableInfrastructure  Flag that enables (yes) or disable the infrastructure (no)
     * @param nameInfrastructure    Infrastructure long name
     * @param acronymInfrastructure Infrastructure short name
     * @param bdiiHost              The ifrastructure BDII host
     * @param wmsHosts              The ';' separated list of enabled WMSes
     * @param pxServerHost          The Robot proxy server port
     * @param pxServerPort          The Robot proxy server port
     * @param pxServerSecure        The Robot proxy secire connection flag
     * @param pxRobotId             The Robot proxy server identifier
     * @param pxRobotVO             The Robot proxy virtual organization
     * @param pxRobotRole           The Robot proxy roles
     * @param pxRobotRenewalFlag    The Robot proxy renewal flag
     * @param pxUserProxy           The path to a user proxy certificate it ovverides the proxy usage
     * @param softwareTags          The infrastructure' software tag
     * 
     * @see AppInfrastructureInfo
     */
    AppInfrastructureInfo(String enableInfrastructure
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
        this.enableInfrastructure =enableInfrastructure;
        this.nameInfrastructure   =nameInfrastructure;
        this.acronymInfrastructure=acronymInfrastructure;
        this.bdiiHost             =bdiiHost;
        this.wmsHosts             =wmsHosts;
        this.pxServerHost         =pxServerHost;
        this.pxServerPort         =pxServerPort;
        this.pxServerSecure       =pxServerSecure;
        this.pxRobotId            =pxRobotId;
        this.pxRobotVO            =pxRobotVO;
        this.pxRobotRole          =pxRobotRole;
        this.pxRobotRenewalFlag   =pxRobotRenewalFlag;
        this.pxUserProxy          =pxUserProxy;
        this.softwareTags         =softwareTags;
        // Now evaluate the InfrastructureInfo accordingly
        AppInfrastructureInfoToInfrastructureInfo();
    } // AppInfrastructureInfo
    
    // 
    /**
     * Initialize an AppInfrastructureInfo from the same object (copy)
     * 
     * @param appInfrastructureInfo 
     * 
     * @see AppInfrastructureInfo
     */
    public AppInfrastructureInfo(AppInfrastructureInfo appInfrastructureInfo) {
        this( appInfrastructureInfo.enableInfrastructure
             ,appInfrastructureInfo.nameInfrastructure
             ,appInfrastructureInfo.acronymInfrastructure
             ,appInfrastructureInfo.bdiiHost
             ,appInfrastructureInfo.wmsHosts
             ,appInfrastructureInfo.pxServerHost
             ,appInfrastructureInfo.pxServerPort
             ,appInfrastructureInfo.pxServerSecure
             ,appInfrastructureInfo.pxRobotId
             ,appInfrastructureInfo.pxRobotVO
             ,appInfrastructureInfo.pxRobotRole
             ,appInfrastructureInfo.pxRobotRenewalFlag
             ,appInfrastructureInfo.pxUserProxy
             ,appInfrastructureInfo.softwareTags
            );
    }
    
    // 
    /**
     * Method that keeps aligned GridEngine' InfrastructureInfo with AppInfrastructureInfo 
     * 
     * @see AppInfrastructureInfo
     * @see it.infn.ct.GridEngine.Job.InfrastructureInfo
     */
    private void InfrastructureInfoToAppInfrastructureInfo() {        
        enableInfrastructure="yes";    // Enable infrastructure by default
        if(null != infrastructureInfo) {
            nameInfrastructure   =infrastructureInfo.getName();        
            acronymInfrastructure=nameInfrastructure;
            bdiiHost             =infrastructureInfo.getBDII();
            String wmsStringList=";";
            for(int i=0; i<infrastructureInfo.getWmsList().length; i++)
                wmsStringList+=infrastructureInfo.getWmsList()[i]+";";
            wmsHosts    =wmsStringList.substring(1,wmsStringList.length()-1);
            pxServerHost=infrastructureInfo.getETokenServer    ();
            pxServerPort=infrastructureInfo.getETokenServerPort();
            pxRobotId   =infrastructureInfo.getProxyId         ();
            pxRobotVO   =infrastructureInfo.getVO              ();
            pxRobotRole =infrastructureInfo.getFQAN            ();        
            softwareTags=infrastructureInfo.getSWTag           ();        
        } // infrastructureInfo not NULL
    } // InfrastructureInfoToAppInfrastructureInfo
    
    /**
     * Method to update the GridEngine' InfrastructureInfo object from AppInfrastructureInfo instance
     */
    public void updateInfrastructureInfo() {
        AppInfrastructureInfoToInfrastructureInfo();
    }
        
    /**
     * Method that keeps aligned AppInfrastructureInfo with GridEngine' InfrastructureInfo
     * 
     * @see AppInfrastructureInfo
     * @see it.infn.ct.GridEngine.Job.InfrastructureInfo
     */
    private void AppInfrastructureInfoToInfrastructureInfo() {
        // Aligns date from InfrastructureInfo to the AppInfrastructureInfo        
        infrastructureInfo=new InfrastructureInfo(this.acronymInfrastructure
                                                , this.bdiiHost
                                                , this.wmsHosts.split(";")
                                                , this.pxServerHost
                                                , this.pxServerPort
                                                , this.pxRobotId
                                                , this.pxRobotVO
                                                , this.pxRobotRole
                                                , this.softwareTags);
    }
    
    /**
     * Make a text dump  of the whole AppInfrastructureInfo data
     * 
     * @return The content dump of the AppInfrastructureInfo values 
     * 
     * @see AppInfrastructureInfo
     */
    public String dump() {
        String dump=LS+"    enableInfrastructure : '"+enableInfrastructure +"'"
                   +LS+"    nameInfrastructure   : '"+nameInfrastructure   +"'"
                   +LS+"    acronymInfrastructure: '"+acronymInfrastructure+"'"
                   +LS+"    bdiiHost             : '"+bdiiHost             +"'"
                   +LS+"    wmsHosts             : '"+wmsHosts             +"'"
                   +LS+"    pxServerHost         : '"+pxServerHost         +"'"
                   +LS+"    pxServerPort         : '"+pxServerPort         +"'"
                   +LS+"    pxRobotId            : '"+pxRobotId            +"'"
                   +LS+"    pxRobotRole          : '"+pxRobotRole          +"'"
                   +LS+"    pxRobotVO            : '"+pxRobotVO            +"'"                   
                   +LS+"    pxRobotRenewalFlag   : '"+pxRobotRenewalFlag   +"'"
                   +LS+"    pxUserProxy          : '"+pxUserProxy          +"'"
                   +LS+"    softwareTags         : '"+softwareTags         +"'"                   
                   +LS;
        return dump;
    }
    
    /**
     * Make a hatml dump of the whole AppInfrastructureInfo data
     * 
     * @return The HTML content dump of the AppInfrastructureInfo values inside a <table></table> block
     * 
     * @see AppInfrastructureInfo
     */    
    public String htmlDump() {
        String htmlDump= LS+"<table border=\"1\" cellpadding=\"10\" cellspacing=\"2\">"
                        +LS+"<tr><td aligh=\"right\"><b>Enable Infrastructure</b></td><td>'" +enableInfrastructure +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Name Infrastructure</b></td><td>'"   +nameInfrastructure   +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Acronym Infrastructure</b></td><td>'"+acronymInfrastructure+"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>BDII Host</b></td><td>'"             +bdiiHost             +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>WMS Hosts</b></td><td>'"             +wmsHosts             +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Robot Server Host</b></td><td>'"     +pxServerHost         +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Robot Server Port</b></td><td>'"     +pxServerPort         +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Robot Id</b></td><td>'"              +pxRobotId            +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Robot Role</b></td><td>'"            +pxRobotRole          +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Robot VO</b></td><td>'"              +pxRobotVO            +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>User Proxy</b></td><td>'"            +pxUserProxy          +"'</td></tr>"
                        +LS+"<tr><td aligh=\"right\"><b>Software Tags</b></td><td>'"         +softwareTags         +"'</td></tr>"
                        +LS+"</table>"
                        +LS;
        return htmlDump;
    }
    
    // Application infrastrucure info may be changed at run-time
    // as soon as the SubmitJob requests the InfrastructureInfo objct
    // it must be aligned with the last changes        
    
    /**
     * Returns the GridEngine' InfrastructureInfo object initialized with data contained in AppInfrastructureInfo
     * 
     * @return InfrastructureInfo object aligned with AppInfrastructureInfo data
     * 
     * @see InfrastructureInfo
     * @see AppInfrastructureInfo
     */
    public InfrastructureInfo getInfrastructureInfo() {        
        AppInfrastructureInfoToInfrastructureInfo();
        return infrastructureInfo; 
    } // getInfrastructureInfo    
    
    //
    // get/set Methods
    //    
    
    /**
     * Returns the AppInfrastructureInfo infrastructure enable flag
     * 
     * @return Enable infrastructure flag (yes/no) 
     * 
     * @see AppInfrastructureInfo
     */
    public String getEnableInfrastructure() {
        return enableInfrastructure;
    }
    /**
     * Set the AppInfrastructureInfo infrastructure enable flag
     * 
     * @param enableInfrastructure Infrastructure enable flag (yes/no)
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setEnableInfrastructure(String enableInfrastructure) {
        this.enableInfrastructure=enableInfrastructure;
    }
    
    /**
     * Returns the AppInfrastructureInfo infrastructure full name
     * 
     * @return The infrastructure full name 
     * 
     * @see AppInfrastructureInfo
     */
    public String getNameInfrastructure() {
        return nameInfrastructure;
    }
    
    /**
     * Set the AppInfrastructureInfo infrastructure full name
     * 
     * @param nameInfrastructure  Infrastructure full name
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setNameInfrastructure(String nameInfrastructure) {
        this.nameInfrastructure=nameInfrastructure;
    }
    
    /**
     * Returns the AppInfrastructureInfo infrastructure short name
     * 
     * @return The infrastructure short name 
     * 
     * @see AppInfrastructureInfo
     */    
    public String getAcronymInfrastructure() {
        return acronymInfrastructure;
    }
    
    /**
     * Set the AppInfrastructureInfo infrastructure short name
     * 
     * @param acronymInfrastructure Infrastructure short name
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setAcronymInfrastructure(String acronymInfrastructure) {
        this.acronymInfrastructure=acronymInfrastructure;
    }
    
    /**
     * Returns the AppInfrastructureInfo infrastructure BDII hostname
     * The hostname must be in the form: bdii://<server_hostname>:2170
     * 
     * @return The infrastructure BDII hostname 
     * 
     * @see AppInfrastructureInfo
     */
    public String getBdiiHost() {
        return bdiiHost;
    }
    
    /**
     * Set the AppInfrastructureInfo infrastructure BDII hostname
     * The hostname must be in the form: bdii://<server_hostname>:2170
     * 
     * @param  The infrastructure BDII hostname in the form: bdii://<server_hostname>:2170
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setBdiiHost(String bdiiHost) {
        this.bdiiHost=bdiiHost;
    }
        
    /**
     * Returns the AppInfrastructureInfo infrastructure ';' separated list of WMSes resource names
     * Please be aware that WMS resources start with wms:// instead of https://
     * 
     * @return The infrastructure BDII hostname 
     * 
     * @see AppInfrastructureInfo
     */
    public String getWmsHosts() {
        return wmsHosts;
    }    
    
    /**
     * Set the AppInfrastructureInfo infrastructure ';' separated list of WMSes resource names
     * Please be aware that WMS resources start with wms://... instead of https://...
     * 
     * @param  The ';' separated list of WMS resource names
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setWmsHosts(String wmsHosts) {
        this.wmsHosts=wmsHosts;
    }
    
    
    /**
     * Returns the AppInfrastructureInfo infrastructure Proxy server hostname     
     * 
     * @return The robot proxy hostname
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxServerHost() {
        return pxServerHost;
    }
    
    /**
     * AppInfrastructureInfo infrastructure Proxy server hostname      
     * 
     * @param  pxServerHost Robot proxy server hostname
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxServerHost(String pxServerHost) {
        this.pxServerHost=pxServerHost;
    }
    
    /**
     * Returns the AppInfrastructureInfo infrastructure Proxy server port     
     * 
     * @return Robot proxy port
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxServerPort() {
        return pxServerPort;
    }
    
    /**
     * Set the AppInfrastructureInfo infrastructure Proxy server port      
     * 
     * @param  pxServerPort Robot proxy server port
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxServerPort(String pxServerPort) {
        this.pxServerPort=pxServerPort;
    }
    
    /**
     * Returns the AppInfrastructureInfo infrastructure Proxy server secure connection flag     
     * 
     * @return Robot proxy secure connection flag (true/false)
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxServerSecure() {
        return pxServerSecure;
    }
    
    /**
     * Set the AppInfrastructureInfo infrastructure Proxy server secure connection flag      
     * 
     * @param  pxServerSecure Robot proxy server secure connection flag
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxServerSecure(String pxServerSecure) {
        this.pxServerSecure=pxServerSecure;
    }
    
    /**
     * Returns the AppInfrastructureInfo Robot proxy identifier     
     * 
     * @return Robot proxy identifier
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxRobotId() {
        return pxRobotId;
    }
    
    /**
     * Set the AppInfrastructureInfo Robot proxy identifier
     * 
     * @param  pxRobotId Robot proxy identifier (A unique number that identifies the Robot certificate in the Robot proxy server)
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxRobotId(String pxRobotId) {
        this.pxRobotId=pxRobotId;
    }
    
    /**
     * Returns the AppInfrastructureInfo Robot proxy virtual organization
     * 
     * @return Robot proxy virtual organization
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxRobotVO() {
        return pxRobotVO;
    }
    
    /**
     * Set the AppInfrastructureInfo Robot proxy virtual organization
     * 
     * @param  pxRobotVO Robot proxy virtual organization
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxRobotVO(String pxRobotVO) {
        this.pxRobotVO=pxRobotVO;
    }
    
    /**
     * Returns the AppInfrastructureInfo Robot proxy role
     * 
     * @return Robot proxy role
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxRobotRole() {
        return pxRobotRole;
    }
    
    /**
     * Set the AppInfrastructureInfo Robot proxy role
     * 
     * @param  pxRobotRole Robot proxy role
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void getPxRobotRole(String pxRobotRole) {
        this.pxRobotRole=pxRobotRole;
    }
    
    /**
     * Returns the AppInfrastructureInfo Robot proxy renewal flag
     * 
     * @return Robot proxy ronewal flag
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxRobotRenewalFlag() {
        return pxRobotRenewalFlag;
    }
    
    /**
     * Set the AppInfrastructureInfo Robot proxy renewal flag
     * 
     * @param  pxRobotRenewalFlag Robot proxy renewal flag (yes/no)
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxRobotRenewalFlag(String pxRobotRenewalFlag) {
        this.pxRobotRenewalFlag=pxRobotRenewalFlag;
    }
    
    /**
     * Returns the AppInfrastructureInfo UserProxy path
     * When specified the user proxy will override the use of RobotProxy while submitting jobs
     * 
     * @return Full path to a UserProxy file
     * 
     * @see AppInfrastructureInfo
     */
    public String getPxUserProxy() {
        return pxUserProxy;
    }
    
    /**
     * Set the AppInfrastructureInfo UserProxy path
     * 
     * @param  pxUserProxy Full path to a UserProxy file
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setPxUserProxy(String pxUserProxy) {
        this.pxUserProxy=pxUserProxy;
    }
    
    /**
     * Returns the AppInfrastructureInfo Infrastructure software tag
     * 
     * @return Infrastructure software tag
     * 
     * @see AppInfrastructureInfo
     */
    public String getSoftwareTags() {
        return softwareTags;
    }
    
    /**
     * Set the AppInfrastructureInfo software tag
     * 
     * @param  softwareTags Infrastructure software tag
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setSoftwareTags(String softwareTags) {
        this.softwareTags=softwareTags;
    }
    
    /**
     * Make a full copy of a give InfrastructureInfo object
     * 
     * @param infrastructureInfo An AppInfrastructureInfo instance
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void copy(AppInfrastructureInfo infrastructureInfo) {        
        this.nameInfrastructure   =infrastructureInfo.nameInfrastructure;    
        this.acronymInfrastructure=infrastructureInfo.acronymInfrastructure; 
        this.bdiiHost             =infrastructureInfo.bdiiHost;              
        this.wmsHosts             =infrastructureInfo.wmsHosts;              
        this.pxServerHost         =infrastructureInfo.pxServerHost;          
        this.pxServerPort         =infrastructureInfo.pxServerPort;          
        this.pxServerSecure       =infrastructureInfo.pxServerSecure;        
        this.pxRobotId            =infrastructureInfo.pxRobotId;             
        this.pxRobotVO            =infrastructureInfo.pxRobotVO;             
        this.pxRobotRole          =infrastructureInfo.pxRobotRole;           
        this.pxRobotRenewalFlag   =infrastructureInfo.pxRobotRenewalFlag;    
        this.pxUserProxy          =infrastructureInfo.pxUserProxy;           
        this.softwareTags         =infrastructureInfo.softwareTags;          
    }
    
    /**
     * Evaluates all the AppInfrastructureInfo values
     * 
     * @param enableInfrastructure  Flag that enables (yes) or disable the infrastructure (no)
     * @param nameInfrastructure    Infrastructure long name
     * @param acronymInfrastructure Infrastructure short name
     * @param bdiiHost              The ifrastructure BDII host
     * @param wmsHosts              The ';' separated list of enabled WMSes
     * @param pxServerHost          The Robot proxy server port
     * @param pxServerPort          The Robot proxy server port
     * @param pxServerSecure        The Robot proxy secire connection flag
     * @param pxRobotId             The Robot proxy server identifier
     * @param pxRobotVO             The Robot proxy virtual organization
     * @param pxRobotRole           The Robot proxy roles
     * @param pxRobotRenewalFlag    The Robot proxy renewal flag
     * @param pxUserProxy           The path to a user proxy certificate it ovverides the proxy usage
     * @param softwareTags          The infrastructure' software tag
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void setInfrastructure(
             String enableInfrastructure
            ,String nameInfrastructure    
            ,String acronymInfrastructure
            ,String bdiiHost              
            ,String wmsHosts              
            ,String pxServerHost          
            ,String pxServerPort          
            ,String pxServerSecure        
            ,String pxRobotId             
            ,String pxRobotVO             
            ,String pxRobotRole           
            ,String pxRobotRenewalFlag    
            ,String pxUserProxy           
            ,String softwareTags
            ) {
            this.enableInfrastructure =enableInfrastructure;
            this.nameInfrastructure   =nameInfrastructure;    
            this.acronymInfrastructure=acronymInfrastructure; 
            this.bdiiHost             =bdiiHost;              
            this.wmsHosts             =wmsHosts;              
            this.pxServerHost         =pxServerHost;          
            this.pxServerPort         =pxServerPort;          
            this.pxServerSecure       =pxServerSecure;        
            this.pxRobotId            =pxRobotId;             
            this.pxRobotVO            =pxRobotVO;             
            this.pxRobotRole          =pxRobotRole;           
            this.pxRobotRenewalFlag   =pxRobotRenewalFlag;    
            this.pxUserProxy          =pxUserProxy;           
            this.softwareTags         =softwareTags; 
    } // setInfrastructure
    
    /**
     * AppInfrastructureInfo items names
     */
    private enum Items {
         enableInfrastructure
        ,nameInfrastructure   
        ,acronymInfrastructure
        ,bdiiHost             
        ,wmsHosts             
        ,pxServerHost         
        ,pxServerPort         
        ,pxServerSecure       
        ,pxRobotId            
        ,pxRobotVO            
        ,pxRobotRole          
        ,pxRobotRenewalFlag   
        ,pxUserProxy          
        ,softwareTags        
    }
    /**
     * Method that evaluates a give AppInfrastructure item name with the given item value
     * @param prefItem  AppInfrastructureInfo item name
     * @param prefValue AppInfrastructureInfo item value
     * @return none
     * 
     * @see AppInfrastructureInfo
     */
    public void updateInfrastructureValue (String prefItem, String prefValue) {
        switch(Items.valueOf(prefItem)) {
            case enableInfrastructure:
                if(   !prefValue.equals("")                  
                   && !enableInfrastructure.equals(prefValue)   
                  ) enableInfrastructure=prefValue;
                break;
            case nameInfrastructure:
                if(   !prefValue.equals("")                  
                   && !nameInfrastructure.equals(prefValue)   
                  ) nameInfrastructure=prefValue;
                break;
            case acronymInfrastructure:
                if(   !prefValue.equals("")                  
                   && !acronymInfrastructure.equals(prefValue)   
                  ) acronymInfrastructure=prefValue;
                break;
           case bdiiHost:
                if(   !prefValue.equals("")                  
                   && !bdiiHost.equals(prefValue)   
                  ) bdiiHost=prefValue;
                break;
           case wmsHosts:
                if(   !prefValue.equals("")                  
                   && !wmsHosts.equals(prefValue)   
                  ) wmsHosts=prefValue;
                break;
           case pxServerHost:
                if(   !prefValue.equals("")                  
                   && !pxServerHost.equals(prefValue)   
                  ) pxServerHost=prefValue;
                break; 
           case pxServerPort:
                if(   !prefValue.equals("")                  
                   && !pxServerPort.equals(prefValue)   
                  ) pxServerPort=prefValue;
                break; 
           case pxServerSecure:
                if(   !prefValue.equals("")                  
                   && !pxServerSecure.equals(prefValue)   
                  ) pxServerSecure=prefValue;
                break;     
           case pxRobotId:
                if(   !prefValue.equals("")                  
                   && !pxRobotId.equals(prefValue)   
                  ) pxRobotId=prefValue;
                break; 
           case pxRobotVO:
                if(   !prefValue.equals("")                  
                   && !pxRobotVO.equals(prefValue)   
                  ) pxRobotVO=prefValue;
                break;   
           case pxRobotRole:
                if(   !prefValue.equals("")                  
                   && !pxRobotRole.equals(prefValue)   
                  ) pxRobotRole=prefValue;
                break;       
           case pxRobotRenewalFlag:
                if(   !prefValue.equals("")                  
                   && !pxRobotRenewalFlag.equals(prefValue)   
                  ) pxRobotRenewalFlag=prefValue;
                break;       
           case pxUserProxy:
                if(   !prefValue.equals("")                  
                   && !pxUserProxy.equals(prefValue)   
                  ) pxUserProxy=prefValue;
                break;     
           case softwareTags:
                if(   !prefValue.equals("")                  
                   && !softwareTags.equals(prefValue)   
                  ) softwareTags=prefValue;
                break;    
           default:;
        } // switch
    } // updateInfrastructureValue
} // AppInfrastructureInfo

