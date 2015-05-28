#!/bin/sh
set -x 


######VARIABLES NECESARIAS#####################
#
#Nota: esto puede modificarse sin problema. Lo he hecho pensando en la simplicidad
#$1: tipo. PID / FILE

# $2 (segundo parametro de entrada): archivo de entrada

# $3 ejecutable

echo "Current path"
pwd
echo "Folder content"
ls -plah


if [ $1 == "PID" ]; then
	echo "Dowload input file"
	python URLParser.py $2
	
else
	echo "No need to download"
fi




echo "existing files;"
ls

echo "download files from Storage Element"
VO_NAME=$(voms-proxy-info -vo)

export LCG_CATALOG_TYPE=lfc
#GridIT
#export LCG_GFAL_INFOSYS=egee-bdii.cnaf.infn.it:2170
#export LFC_HOST=lfcserver.cnaf.infn.it

#EUMED
export LCG_GFAL_INFOSYS=bdii.eumedgrid.eu:2170
export LFC_HOST=lfc.ulakbim.gov.tr


lcg-cp --vo ${VO_NAME} lfn:/grid/${VO_NAME}/rodriguez/$3.bz2 file:$3.bz2


rc=$?
if [[ $rc != 0 ]] ; then
    echo "Could not download input files, exiting..."
    exit 1
fi


echo "uncompressing source" 
tar xvf $3.bz2


rc=$?
if [[ $rc != 0 ]] ; then
    echo "Could not unzcompress executable files, exiting..."
    exit 2
fi


echo "compilong"
cd $3
chmod +x coconut
./coconut -b

rc=$?
if [[ $rc != 0 ]] ; then
    echo "Compiling problems, exiting..."
    exit 3
fi


echo "prepare input files"
cp ../$2 run

cd run

if [ -e corsika73500Linux_QGSII_gheisha ]; then
        echo "execute corsika73500Linux_QGSII_gheisha"
        ./corsika73500Linux_QGSII_gheisha < $2 

        rc=$?
        if [[ $rc != 0 ]] ; then
            echo "Running problems with corsika73500Linux_QGSII_gheisha, exiting..."
            rm DAT*
            cd ..
            echo "cleaning past compilations"
            make clean
            echo "forcing 64 bits compilation"
            ./coconut -b --disable-M32

            echo "executing"
            cp ../$2 run
            cd run
            ./corsika73500Linux_QGSII_gheisha < $2

	    rc=$?
    	    if [[ $rc != 0 ]] ; then
		echo "Could not find a solution. Aborting execution, sorry"
		exit 5
	    fi
        fi
        echo "Successful execution"

elif [ -e corsika73500Linux_EPOS_gheisha ]; then
        echo "execute corsika73500Linux_EPOS_gheisha"
        ./corsika73500Linux_EPOS_gheisha < $2

        rc=$?
        if [[ $rc != 0 ]] ; then
            echo "Running problems with corsika73500Linux_EPOS_gheisha, tryingg different compilation"

	    rm DAT*
            cd ..
            echo "cleaning past compilations"
            make clean
            echo "forcing 64 bits compilation"
            ./coconut -b --disable-M32

            echo "executing"
            cp ../$2 run
            cd run
            ./corsika73500Linux_EPOS_gheisha < $2

            rc=$?
            if [[ $rc != 0 ]] ; then
                echo "Could not find a solution. Aborting execution, sorry"
                exit 6  
            fi  
        fi
        echo "Successful execution"
else
        echo "Problem finding executable, will abort"
        exit 7
fi


mkdir results
cp DAT* results
tar czvf corsika-Files.tar.gz results/*
cp corsika-Files.tar.gz ..
cp corsika-Files.tar.gz ../..

echo "estoy en " 
pwd
echo "aqui hay"
ls

cd ..
echo "estoy2 en "
pwd
echo "aqui hay"
ls

cd ..
echo "estoy3 en "
pwd
echo "aqui hay"
ls
