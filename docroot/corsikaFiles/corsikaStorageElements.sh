#!/bin/bash



env > env.log

ls -al ${PWD}



echo "-----------------------------------------------------------------------------------"

echo "Running host ..." `hostname -f`

echo "IP address ....." `/sbin/ifconfig | grep "inet addr:" | head -1 | awk '{print $2}' | awk -F':' '{print $2}'`

echo "Kernel ........." `uname -r`

echo "Distribution ..." `head -n1 /etc/issue`

echo "Arch ..........." `uname -a | awk '{print $12}'`

echo "CPU  ..........." `cat /proc/cpuinfo | grep -i "model name" | head -1 | awk -F ':' '{print $2}'`

echo "Memory ........." `cat /proc/meminfo | grep MemTotal | awk {'print $2'}` KB

echo "Partitions ....." `cat /proc/partitions`

echo "Uptime host ...." `uptime | sed 's/.*up ([^,]*), .*/1/'`

echo "Timestamp ......" `date`

echo "-----------------------------------------------------------------------------------"



export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:.

export VO_NAME=$(voms-proxy-info -vo)

export VO_VARNAME=$(echo ${VO_NAME} | sed s/"\."/"_"/g | sed s/"-"/"_"/g | awk '{ print toupper($1) }')

export VO_SWPATH_NAME="VO_"$VO_VARNAME"_SW_DIR"

export VO_SWPATH_CONTENT=$(echo $VO_SWPATH_NAME | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')

export OMP_NUM_THREADS=1

export path="/dpm"




PID=$1  #pid or file

INPUT_FILE=$2

CORSIKA_VERSION=$3

MIDDLEWARE=$4

METADATA_HOST=$5 #glibrary.ct.infn.it

USERNAME=$6  #larocca

DESC="$7"


PROXY_RFC=${X509_USER_PROXY}

export DPM_HOST=$8   #prod-se-03.ct.infn.it

export DPNS_HOST=$8

chmod 600 ${PROXY_RFC}



if [ $9 == "PID" ]; then
	echo "Dowload input file"
	python URLParser.py $2
	
else
	echo "No need to download"
fi


DATETIME=`date +%Y%m%d%H%M%S`

RELEASE=`uname -r | awk -F'.x86_64' '{print $1}' | awk -F'.' '{print $NF}'`



# Function to fetch the DPM paths with dpns-* CLI

fetch_DPM_folder_recurse()     

{

    for i in $(dpns-ls /$1)

    do

        if [ $? -eq 0 ] ; then

            if [ "X${i}" != "Xhome" ] ; then

                    fetch_DPM_folder_recurse "$1/$i"

            else 

                echo "$1/$i"

            fi

        fi

    done

}



echo;echo "[ Settings ]"

echo "-----------------------------------------"

echo "MIDDLEWARE        : "${MIDDLEWARE}

echo "VO_NAME           : "${VO_NAME}

echo "VO_VARNAME        : "${VO_VARNAME}

echo "DEFAULT_SE        : "${DPM_HOST}

echo "VO_SWPATH_NAME    : "${VO_SWPATH_NAME}

echo "VO_SWPATH_CONTENT : "${VO_SWPATH_CONTENT}


echo "-----------------------------------------"

echo "INPUT_FILE          : "${INPUT_FILE}

echo "METADATA_HOST       : "${METADATA_HOST}

echo "USERNAME            : "${USERNAME}

echo "DESCRIPTION         : "${DESC}

echo "RFC PROXY           : "${PROXY_RFC}

echo "-----------------------------------------"



# Check if the STORAGE PATH does already exist on the EMI-3 DPM Storage Element

echo;echo "[ CHECHING/CREATING the Storage Path ... ]"

DPM_STORAGE_PATH=`fetch_DPM_folder_recurse $path`

dpns-mkdir ${DPM_STORAGE_PATH}/${VO_NAME}/CORSIKA 2>/dev/null >/dev/null



if [ $? -eq 1 ] ; then

		echo "The Storage Path already does exist on the SE"

	else

		echo dpns-mkdir ${DPM_STORAGE_PATH}/${VO_NAME}/CORSIKA 2>/dev/null >/dev/null

		echo "The Storage Path has been successfully created on the SE"
fi






echo "REAL APPLICATION STARTS HERE"


echo "download executable"
echo "TODO: set correct VO and LFC_HOST here"
#export LFC_HOST=lfc-egee.bifi.unizar.es
#lcg-cp --vo fusion lfn:/grid/fusion/rodriguez/$CORSILA_VERSION.bz2 file:$CORSIKA_VERSION.bz2


lcg-cp --vo ${VO_NAME} lfn:/grid/${VO_NAME}/rodriguez/$CORSILA_VERSION.bz2 file:$CORSIKA_VERSION.bz2


if [[ $rc != 0 ]] ; then
    echo "Could not download executable and/or input files, exiting..."
    exit 1
fi


echo "uncompressing source" 
tar xvf $CORSIKA_VERSION.bz2


rc=$?
if [[ $rc != 0 ]] ; then
    echo "Could not unzcompress executable files, exiting..."
    exit 2
fi


echo "compiling executable"
cd $CORSIKA_VERSION
chmod +x coconut
./coconut -b

rc=$?
if [[ $rc != 0 ]] ; then
    echo "Compiling problems, exiting..."
    exit 3
fi


echo;echo "[ STARTING CORSIKA with input file = "$INPUT_FILE " ]"
echo "prepare input files"
cp ../$INPUT_FILE run

cd run

if [ -e corsika73500Linux_QGSII_gheisha ]; then
	echo "execute corsika73500Linux_QGSII_gheisha"
	./corsika73500Linux_QGSII_gheisha < $INPUT_FILE > ../../output_file

	rc=$?
	if [[ $rc != 0 ]] ; then
	    echo "Running problems with corsika73500Linux_QGSII_gheisha, exiting..."
	    exit 5
	fi
	echo "Successful execution"

elif [ -e corsika73500Linux_EPOS_gheisha ]; then
        echo "execute corsika73500Linux_EPOS_gheisha"
        ./corsika73500Linux_EPOS_gheisha < $INPUT_FILE > ../../output_file

        rc=$?
        if [[ $rc != 0 ]] ; then
            echo "Running problems with corsika73500Linux_EPOS_gheisha, exiting..."
            exit 6
        fi
        echo "Successful execution"
else
	echo "Problem finding executable, will abort (no inocent fetus were killed though)"
	exit 7
fi 

if [ ! -f ../../output_file ]; then
	echo "output file not found".
	exit 8
fi



RESULTS_FILE=results_${USERNAME}_${DATETIME}.tar.gz

tar czf $RESULTS_FILE  DAT* output* 2>/dev/null

echo "Now I am in " 
pwd
echo "And we have here: "
ls 


SIZE=`cat $RESULTS_FILE | wc -c`



#######################################

# Using gLibrary Data Management APIs #

#  to upload output files to Grid SE  #

#######################################


#THIS IS HOW I USED TO DO IT
#lcg-cr --vo fusion -d griddpm01.ifca.es -l lfn:/grid/fusion/rodriguez/output$1.tar.gz file:output$1.tar.gz


echo; echo "[ GETTING the short-lived URL where the actual file(s) should be uploaded ]"

URL=`./curl -3 -k -E ${PROXY_RFC} https://${METADATA_HOST}/api/dm/put/${VO_NAME}/$RESULTS_FILE/${DPM_HOST}/${DPM_STORAGE_PATH}/${VO_NAME}/CORSIKA/ \

| grep -i redirect \

| awk '{print $2}' \

| awk -F',' '{print $1}' \

| awk -F'"' '{print $2}' \

| awk -F'"' '{print $1}'`



if [ "X${URL}" != "X" ] ; then

	echo "URL = "${URL}

	echo; echo "[ UPLOADING output file(s) in progress ]"

	curl -T $RESULTS_FILE -X PUT ${URL}


	echo; echo "[ ADDING a new entry with its metadata of a give type ]"

	TIMESTAMP=`date +%F' '%T`

	./curl -3 -k -E ${PROXY_RFC} -X POST \

       -d "__Replicas=https://${DPM_HOST}/${DPM_STORAGE_PATH}/${VO_NAME}/CORSIKA/$RESULTS_FILE&FileName=$RESULTS_FILE&Size=${SIZE}&Description=${DESC}&FileType=GZIP&Creator=${USERNAME}&SubmissionDate=${TIMESTAMP}" \

       https://${METADATA_HOST}/api/CORSIKA/Jobs/

else 

	echo "Some errors occurred during the file registration. Please, check log messages"

	echo "./curl -3 -k -E ${PROXY_RFC} https://${METADATA_HOST}/api/dm/put/${VO_NAME}/${RESULTS_FILE}/${DPM_HOST}/${DPM_STORAGE_PATH}/${VO_NAME}/CORSIKA/"

	./curl -3 -k -E ${PROXY_RFC} \

	https://${METADATA_HOST}/api/dm/put/${VO_NAME}/${RESULTS_FILE}/${DPM_HOST}/${DPM_STORAGE_PATH}/${VO_NAME}/CORSIKA/

	exit

fi
