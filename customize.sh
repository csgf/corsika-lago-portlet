#!/bin/bash
#
# Script that customizes the mi-hostname' portlet
# just fill properly any environment variable
# accordingly to your application and then run
#
# $ ./customize.sh
# 
# Author: riccardo.bruno@ct.infn.it

# GridEngine home path
GRID_ENGINE_HOME=/opt/GridEngine

# Author' information
# Used to fill-up portlet' licence information
AUTH_EMAIL='riccardo.bruno@ct.infn.it'
AUTH_NAME='Riccardo Bruno'
AUTH_INSTITUTE='INFN'

# Grid Engine Operation description
APP_OPERDESC="$APP_NAME description"

# Grid Engine Operation id
# If not null it enforces the use of the following GridOperationId
# This value can be defined here or setup later from the portlet
# preferences pane. Enabling the auto-registration feature
# the operation id will be autimatically assigned
# Id=9 refers to the default application Id on GILDA development
# virtual machines
APP_OPERATIONID=10

# Application information
APP_NAME=corsika
APP_VERSION=1.0

# Grid Engine Operation description 
APP_OPERDESC="$APP_NAME description"

#
# docroot/WEB-INF/portlet.xml tag values
#

# <portlet-name>
PORTLET_NAME=$APP_NAME-portlet

# <title>
PORTLET_TITLE=$APP_NAME-portlet

# <short-title>
PORTLET_SHTITLE=$APP_NAME-portlet

# <keywords>
PORTLET_KEYWORDS="$APP_NAME-portlet"

# <display-name>
DISPLAY_NAME=$APP_NAME-portlet

# <portlet-class>
# Do not use '-' character; it's illegal in java class name (use '_' instead)
BASE_CLASS=it.infn.ct
CLASS_NAME=$(echo ${APP_NAME}_portlet | sed s/"-"/"_"/g)

#
# Init parameters
#

#init_PortletVersion
# Use this value to specify the portlet version
INI_PVERSION=$APP_VERSION

#init_logLevel
# The portlet template code uses a customizable log levels: trace, info, debug, error, fatal
# Log outputs having a lower level will be not printed out while the portlet executes
INI_LOGLEVEL=info

#
# Infrastructure settings (begin)
#
INIT_NUMINFRASTRUCTURES=1

#
# For each ifnrastructure provide variable names like
#
# INI_n_<Infrastructure param name> to set the n-th infrastructure porperty
#
# The 'n' value must range from 1 to INIT_NUMINFRASTRUCTURES and must start from 1
#

#------------------
#1st Infrastructure
#------------------

INI_1_NAMEINFRA='GridIt Italian Grid Infrastructure'

#acronymInfrastructure
INI_1_ACRONYM='GridIt'

#enableInfrastructure
INI_1_ENABLEINFRA=yes

#init_bdiiHost
# specify the information system BDII in the form ldap://<info_provider_hostname>:<info_provide_port>
INI_1_BDIIHOST='ldap://egee-bdii.cnaf.infn.it:2170'

#init_wmsHost
# The wms host can be obtained from an UI with the gLite command line
# lcg-infosites --vo eumed wms
#
INI_1_WMSHOST='wms://gridrb.fe.infn.it:7443/glite_wms_wmproxy_server;wms://prod-wms-01.ct.infn.it:7443/glite_wms_wmproxy_server;wms://prod-wms-01.pd.infn.it:7443/glite_wms_wmproxy_server;wms://prod-wms-02.ct.infn.it:7443/glite_wms_wmproxy_server;wms://wms004.cnaf.infn.it:7443/glite_wms_wmproxy_server;wms://wms005.cnaf.infn.it:7443/glite_wms_wmproxy_server;wms://wms014.cnaf.infn.it:7443/glite_wms_wmproxy_server;wms://wms024.cnaf.infn.it:7443/glite_wms_wmproxy_server'

#init_pxServerHost
INI_1_PXHOST='etokenserver.ct.infn.it'

#init_pxServerPort
INI_1_PXPORT=8082

#init_pxServerSecure
INI_1_PXSECURE=true

#init_pxRobotId
INI_1_ROBOTID=bc779e33367eaad7882b9dfaa83a432c

#init_pxRobotVO
INI_1_ROBOTVO=gridit

#init_pxRobotRole
INI_1_ROBOROLE=gridit

#init_pxRobotRenewalFlag (true/false)
INI_1_RENEWALFLAG=true

#init_pxUserProxy
INI_1_USERPROXY=''

#init_softwareTags
INI_1_SWTAGS=''

#
# Infrastructure settings (end)
#

# Following settings should not be used by standard portlets
# they have been left for historical reasons or to handle
# particular cases where the portlet needs to access directy
# to the GridEngine' UserTracking database

#init_sciGwyUserTrackingDB_Hostname  
UTDB_HOSTNAME=localhost

#init_sciGwyUserTrackingDB_Username
UTDB_USERNAME=tracking_user

#init_sciGwyUserTrackingDB_Password
UTDB_PASSWORD=usertracking         

#init_sciGwyUserTrackingDB_Database
UTDB_DATABASE=userstracking

#init_jobRequirements
# More requirements can be specified by a ';' separated list of items
INI_JOBREQUIREMENTS=''

#init_pilotScript
# Specify here the filename of the pilot script
# the name refers to the docroot/WEB-INF/job/ directory 
INI_PILOTSCRIPT='pilot_script.sh'

#
# docroot/WEB-INF/liferay-display.xml tag values
#
PORTLET_CATEGORYNAME=LAGO
PORTLET_IDENTIFIER=$PORTLET_NAME

#
# docroot/WEB-INF/liferay-portlet.xml tag values
#

# <portlet-name>
LFRY_PORTLETNAME=$PORTLET_NAME
# <css-class-wrapper> (! this filed does not accept '-')
LFRY_CSSCLWRAPPER=$(echo $CLASS_NAME | sed s/"-"/"_"/g)

#
# docroot/WEB-INF/glassfish-web.xml tag values
#
GLFH_CONTEXTROOT=$PORTLET_NAME

#-----------------------------
# Customization script ...
#-----------------------------

#
# Generates the portlet.xml file
#
INFO_INFRA=$(mktemp)
INI_INFRASTRUCTURES=$(mktemp)
# Create first the Infrastructure block
for ((i=1; i<=$INIT_NUMINFRASTRUCTURES; i++))
do
  echo "                <!--"                    >> $INI_INFRASTRUCTURES
  echo "                     Infrastructure #"$i >> $INI_INFRASTRUCTURES
  echo "                -->"                     >> $INI_INFRASTRUCTURES
  echo ""                                        >> $INI_INFRASTRUCTURES

  # Get infrastructure variable name
  INI_NAMEINFRA=$(export   "INI_"$i"_NAMEINFRA"  ; echo "INI_"$i"_NAMEINFRA"  | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_ACRONYM=$(export     "INI_"$i"_ACRONYM"    ; echo "INI_"$i"_ACRONYM"    | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_ENABLEINFRA=$(export "INI_"$i"_ENABLEINFRA"; echo "INI_"$i"_ENABLEINFRA"| awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_BDIIHOST=$(export    "INI_"$i"_BDIIHOST"   ; echo "INI_"$i"_BDIIHOST"   | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_WMSHOST=$(export     "INI_"$i"_WMSHOST"    ; echo "INI_"$i"_WMSHOST"    | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_PXHOST=$(export      "INI_"$i"_PXHOST"     ; echo "INI_"$i"_PXHOST"     | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_PXPORT=$(export      "INI_"$i"_PXPORT"     ; echo "INI_"$i"_PXPORT"     | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_PXSECURE=$(export    "INI_"$i"_PXSECURE"   ; echo "INI_"$i"_PXSECURE"   | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_ROBOTID=$(export     "INI_"$i"_ROBOTID"    ; echo "INI_"$i"_ROBOTID"    | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_ROBOTVO=$(export     "INI_"$i"_ROBOTVO"    ; echo "INI_"$i"_ROBOTVO"    | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_ROBOROLE=$(export    "INI_"$i"_ROBOROLE"   ; echo "INI_"$i"_ROBOROLE"   | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_RENEWALFLAG=$(export "INI_"$i"_RENEWALFLAG"; echo "INI_"$i"_RENEWALFLAG"| awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_USERPROXY=$(export   "INI_"$i"_USERPROXY"  ; echo "INI_"$i"_USERPROXY"  | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  INI_SWTAGS=$(export      "INI_"$i"_SWTAGS"     ; echo "INI_"$i"_SWTAGS"     | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
  
  # Prepare the informative pane
  echo "Infrastructure #"$i                         >> $INFO_INFRA
  echo "INI_${i}_NAMEINFRA  : '"$INI_NAMEINFRA"'"   >> $INFO_INFRA
  echo "INI_${i}_ACRONYM    : '"$INI_ACRONYM"'"     >> $INFO_INFRA
  echo "INI_${i}_ENABLEINFRA: '"$INI_ENABLEINFRA"'" >> $INFO_INFRA
  echo "INI_${i}_BDIIHOST   : '"$INI_BDIIHOST"'"    >> $INFO_INFRA
  echo "INI_${i}_WMSHOST    : '"$INI_WMSHOST"'"     >> $INFO_INFRA 
  echo "INI_${i}_PXHOST     : '"$INI_PXHOST"'"      >> $INFO_INFRA
  echo "INI_${i}_PXPORT     : '"$INI_PXPORT"'"      >> $INFO_INFRA 
  echo "INI_${i}_PXSECURE   : '"$INI_PXSECURE"'"    >> $INFO_INFRA
  echo "INI_${i}_ROBOTID    : '"$INI_ROBOTID"'"     >> $INFO_INFRA
  echo "INI_${i}_ROBOTVO    : '"$INI_ROBOTVO"'"     >> $INFO_INFRA
  echo "INI_${i}_ROBOROLE   : '"$INI_ROBOROLE"'"    >> $INFO_INFRA
  echo "INI_${i}_RENEWALFLAG: '"$INI_RENEWALFLAG"'" >> $INFO_INFRA
  echo "INI_${i}_USERPROXY  : '"$INI_USERPROXY"'"   >> $INFO_INFRA
  echo "INI_${i}_SWTAGS     : '"$INI_SWTAGS"'"      >> $INFO_INFRA
  echo                                              >> $INFO_INFRA

  cat >> $INI_INFRASTRUCTURES <<EOF
         	<init-param>
            		<name>${i}_nameInfrastructure</name>
            		<value>${INI_NAMEINFRA}</value>
         	</init-param>
		<init-param>             
			<name>${i}_acronymInfrastructure</name>
			<value>${INI_ACRONYM}</value>
		</init-param>
		<init-param>    
			<name>${i}_bdiiHost</name>
			<value>${INI_BDIIHOST}</value>
		</init-param>
        	<init-param>
            		<name>${i}_enableInfrastructure</name>
            		<value>${INI_ENABLEINFRA}</value>
        	</init-param>
		<init-param>    
			<name>${i}_wmsHosts</name>
			<value>${INI_WMSHOST}</value>
		</init-param>
		<init-param>
			<name>${i}_pxServerHost</name>
			<value>${INI_PXHOST}</value>
		</init-param>
		<init-param>
			<name>${i}_pxServerPort</name>
			<value>${INI_PXPORT}</value>
			</init-param>
		<init-param>
			<name>${i}_pxServerSecure</name>
			<value>${INI_PXSECURE}</value>
		</init-param>
		<init-param>
			<name>${i}_pxRobotId</name>
			<value>${INI_ROBOTID}</value>
		</init-param>
		<init-param>
			<name>${i}_pxRobotVO</name>
			<value>${INI_ROBOTVO}</value>
		</init-param>
		<init-param>
			<name>${i}_pxRobotRole</name>
			<value>${INI_ROBOROLE}</value>
		</init-param>
		<init-param>
			<name>${i}_pxRobotRenewalFlag</name>
			<value>${INI_RENEWALFLAG}</value>
		</init-param>
        <init-param>
            <name>${i}_softwareTags</name>
            <value>${INI_SWTAGS}</value>
        </init-param>		
EOF
done
# Generate the 1st part of portlet.xml file
cat > docroot/WEB-INF/portlet.xml <<EOF
<?xml version="1.0"?>

<portlet-app
	version="2.0"
	xmlns="http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd"
>
	<portlet>
		<portlet-name>${PORTLET_NAME}</portlet-name>
		<display-name>${DISPLAY_NAME}</display-name>
		<portlet-class>${BASE_CLASS}.${CLASS_NAME}</portlet-class>
		<!--
                     Application init parameters
                -->
                <init-param>
        		<name>gridOperationDesc</name>
        		<value>${APP_OPERDESC}</value>
		</init-param>
		<init-param>
                        <name>portletVersion</name>
                        <value>${INI_PVERSION}</value>
                </init-param>
                <init-param>
                        <name>logLevel</name>
                        <value>${INI_LOGLEVEL}</value>
                </init-param>
		<init-param>
                        <name>gridOperationId</name>
                        <value>${APP_OPERATIONID}</value>
                </init-param>
                <!--
                     Infrastructure parameters can be specified prefixing
                     the name with the infrastructure number: n_<property>
                -->
                <init-param>
                        <name>numInfrastructures</name>
                        <value>${INIT_NUMINFRASTRUCTURES}</value>
                </init-param>
EOF
# Add infrastructures into portlet.xml file
cat $INI_INFRASTRUCTURES >> docroot/WEB-INF/portlet.xml
rm -f $INI_INFRASTRUCTURES
# Continue with remaining content of portlet.xml file
cat >> docroot/WEB-INF/portlet.xml <<EOF
		<init-param>
			<name>sciGwyUserTrackingDB_Hostname</name>
			<value>${UTDB_HOSTNAME}</value>
		</init-param>
		<init-param>
			<name>sciGwyUserTrackingDB_Username</name>
			<value>${UTDB_USERNAME}</value>
		</init-param>
		<init-param>
			<name>sciGwyUserTrackingDB_Password</name>
			<value>${UTDB_PASSWORD}</value>
		</init-param>
		<init-param>
			<name>sciGwyUserTrackingDB_Database</name>
			<value>${UTDB_DATABASE}</value>
		</init-param>
		<init-param>    
			<name>jobRequirements</name>
			<value>${INI_JOBREQUIREMENTS}</value>
		</init-param>            
		<init-param>    
			<name>pilotScript</name>
			<value>${INI_PILOTSCRIPT}</value>
		</init-param>
		<expiration-cache>0</expiration-cache>
		<supports>
			<mime-type>text/html</mime-type>
			<portlet-mode>view</portlet-mode>
			<portlet-mode>edit</portlet-mode>
			<portlet-mode>help</portlet-mode>
		</supports>
		<portlet-info>
			<title>${PORTLET_TITLE}</title>
			<short-title>${PORTLET_SHTITLE}</short-title>
			<keywords>${PORTLET_KEYWORDS}</keywords>
		</portlet-info>
		<security-role-ref>
			<role-name>administrator</role-name>
		</security-role-ref>
		<security-role-ref>
			<role-name>guest</role-name>
		</security-role-ref>
		<security-role-ref>
			<role-name>power-user</role-name>
		</security-role-ref>
		<security-role-ref>
			<role-name>user</role-name>
		</security-role-ref>
	</portlet>
</portlet-app>
EOF

# Create the portlet class directory
mkdir -p docroot/WEB-INF/src/$(echo $BASE_CLASS | sed s/'\.'/'\/'/g)

#
# Generates the liferay-display.xml
#
cat > docroot/WEB-INF/liferay-display.xml <<EOF
<?xml version="1.0"?>
<!DOCTYPE display PUBLIC "-//Liferay//DTD Display 6.0.0//EN" "http://www.liferay.com/dtd/liferay-display_6_0_0.dtd">

<display>
	<category name="${PORTLET_CATEGORYNAME}">
		<portlet id="${PORTLET_IDENTIFIER}" />
	</category>
</display>
EOF

#
# Generates the docroot/WEB-INF/liferay-portlet.xml
#
cat > docroot/WEB-INF/liferay-portlet.xml << EOF
<?xml version="1.0"?>
<!DOCTYPE liferay-portlet-app PUBLIC "-//Liferay//DTD Portlet Application 6.0.0//EN" "http://www.liferay.com/dtd/liferay-portlet-app_6_0_0.dtd">

<liferay-portlet-app>
	<portlet>
		<portlet-name>${LFRY_PORTLETNAME}</portlet-name>
		<icon>/icon.png</icon>
		<instanceable>true</instanceable>
		<header-portlet-css>/css/main.css</header-portlet-css>
		<footer-portlet-javascript>/js/main.js</footer-portlet-javascript>
		<css-class-wrapper>${LFRY_CSSCLWRAPPER}</css-class-wrapper>
	</portlet>
	<role-mapper>
		<role-name>administrator</role-name>
		<role-link>Administrator</role-link>
	</role-mapper>
	<role-mapper>
		<role-name>guest</role-name>
		<role-link>Guest</role-link>
	</role-mapper>
	<role-mapper>
		<role-name>power-user</role-name>
		<role-link>Power User</role-link>
	</role-mapper>
	<role-mapper>
		<role-name>user</role-name>
		<role-link>User</role-link>
	</role-mapper>
</liferay-portlet-app>
EOF

#
# Generates the docroot/WEB-INF/glassfish-web.xml
# 
# first part the context-root
cat > docroot/WEB-INF/glassfish-web.xml << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE glassfish-web-app PUBLIC "-//GlassFish.org//DTD GlassFish Application Server 3.1 Servlet 3.0//EN" "http://glassfish.org/dtds/glassfish-web-app_3_0-1.dtd">
<glassfish-web-app error-url="">
  <context-root>/${GLFH_CONTEXTROOT}</context-root>
EOF
# second part the class-loader tag
find $GRID_ENGINE_HOME/lib -name '*.jar' | awk 'BEGIN { printf("<class-loader delegate=\"true\" extra-class-path=\""); s=0 } { if(s==0) sep=""; else sep=":"; printf("%s%s",sep,$1); s=s+1 } END{ printf("\"/>\n");}' >> docroot/WEB-INF/glassfish-web.xml
# third part the xml file conclusion
cat >> docroot/WEB-INF/glassfish-web.xml << EOF
  <jsp-config>
    <property name="keepgenerated" value="true">
      <description>Keep a copy of the generated servlet class' java code.</description>
    </property>
  </jsp-config>
</glassfish-web-app>
EOF

#
# Generates the java code
#
cat docroot/WEB-INF/src/it/infn/ct/mi_hostname_portlet.java | sed s/"mi_hostname_portlet"/"${CLASS_NAME}"/g | sed s/"@author <a href=\"mailto:riccardo.bruno@ct.infn.it\">riccardo bruno<\/a>(COMETA)"/"@author <a href=\"mailto:${AUTH_EMAIL}\">${AUTH_NAME}<\/a>(${AUTH_INSTITUTE})"/ | sed s/"hostname-Output.txt"/"${APP_NAME}-Output.txt"/ | sed s/"hostname-Error.txt"/"${APP_NAME}-Error.txt"/ | sed s/"hostname-Files.tar.gz"/"${APP_NAME}-Files.tar.gz"/ > docroot/WEB-INF/src/$(echo $BASE_CLASS | sed s/'\.'/'\/'/g)/${CLASS_NAME}.java

#
# docroot/edit.jsp 
#
mv docroot/edit.jsp docroot/edit.jsp_old
cat docroot/edit.jsp_old | sed s/"mi-hostname-portlet"/"${CLASS_NAME}"/g > docroot/edit.jsp 
rm -f docroot/edit.jsp_old

#
# docroot/help.jsp
#
mv docroot/help.jsp docroot/help.jsp_old
cat docroot/help.jsp_old | sed s/'Author: Riccardo Bruno (COMETA)'/'Author: ${AUTH_NAME} (${AUTH_INSTITUTE}) - ${AUTH_EMAIL}'/g > docroot/help.jsp
rm -f docroot/help.jsp_old

#
# docroot/WEB-INF/job/pilot_script.sh
#
NEW_PILOT=$(mktemp)
cat docroot/WEB-INF/job/pilot_script.sh > $NEW_PILOT
cat $NEW_PILOT | sed s/"# hostname - portlet pilot script"/"# ${APP_NAME} - portlet pilot script"/ | sed s/"hostname-Files.tar.gz"/"${APP_NAME}-Files.tar.gz"/ > docroot/WEB-INF/job/pilot_script.sh
rm -f $NEW_PILOT

#--------------------------------
# Final Report
#--------------------------------
echo ""
echo "Reporting configured variables ..."
echo ""
echo "AUTH_EMAIL          : '"$AUTH_EMAIL"'"
echo "AUTH_NAME           : '"$AUTH_NAME"'"
echo "AUTH_INSTITUTE      : '"$AUTH_INSTITUTE"'"
echo "PORTLET_NAME        : '"$PORTLET_NAME"'"
echo "PORTLET_TITLE       : '"$PORTLET_TITLE"'"
echo "PORTLET_SHTITLE     : '"$PORTLET_SHTITLE"'"
echo "PORTLET_KEYWORDS    : '"$PORTLET_KEYWORDS"'"
echo "DISPLAY_NAME        : '"$DISPLAY_NAME"'"
echo "BASE_CLASS          : '"$BASE_CLASS"'"
echo "CLASS_NAME          : '"$CLASS_NAME"'"
echo "UTDB_APPID          : '"$UTDB_APPID"'"
echo "UTDB_HOSTNAME       : '"$UTDB_HOSTNAME"'"
echo "UTDB_USERNAME       : '"$UTDB_USERNAME"'"
echo "UTDB_PASSWORD       : '"$UTDB_PASSWORD"'"
echo "UTDB_DATABASE       : '"$UTDB_DATABASE"'"
echo "INI_JOBREQUIREMENTS : '"$INI_JOBREQUIREMENTS"'"
echo "INI_PILOTSCRIPT     : '"$INI_PILOTSCRIPT"'"
echo "PORTLET_CATEGORYNAME: '"$PORTLET_CATEGORYNAME"'"
echo "PORTLET_IDENTIFIER  : '"$PORTLET_IDENTIFIER"'"
echo "LFRY_PORTLETNAME    : '"$LFRY_PORTLETNAME"'"
echo "LFRY_CSSCLWRAPPER   : '"$LFRY_CSSCLWRAPPER"'"
echo "GLFH_CONTEXTROOT    : '"$GLFH_CONTEXTROOT"'"
echo ""
echo "Defined infrastructures:"
cat $INFO_INFRA
echo ""
echo "Customization done!"
echo ""
rm -f $INFO_INFRA

# Check directory name
DIR_NAME=$(basename $(pwd))
if [ $DIR_NAME != $PORTLET_NAME ]
then
  echo "!ATTENTION: The portlet name and the current direcory have a different name"
  echo "            Please rename your directory to: '"$PORTLET_NAME"'"
  echo ""
fi

# Remove existing classes
rm -rf docroot/WEB-INF/classes
mkdir -p docroot/WEB-INF/classes

