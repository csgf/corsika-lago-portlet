************
CORSIKA-LAGO
************

============
About
============

.. image:: images/logo.png
   :height: 100px
   :align: left
   :target: http://labdpr.cab.cnea.gov.ar/lago/
-------------


LAGO is an international project from more than 80 scientists of 8 latinamerican countries started in 2005 (complete list of the collaboration members and their institutions). The LAGO project aims at observing Gamma Ray Bursts (GRBs) by the single particle technique using water Cherenkov detectors (WCD). It consists of various sites at high altitude, in order to reach a good sensitivity to the faint signal expected from the high energy photons from GRBs.

CORSIKA (COsmic Ray SImulations for KAscade) is a program for detailed simulation of extensive air showers initiated by high energy cosmic ray particles. Protons, light nuclei up to iron, photons, and many other particles may be treated as primaries.

============
Installation
============
Following instructions are meant for science gateway maintainers while generic users can skip this section.
To install the portlet it is enough to install the war file into the application server and then configure the preference settings into the portlet preferences pane.

Preferences are splitted in three separate parts: Generic, Infrastructures and the application execution setting. 
The generic part contains the **Log level** which contains one of following values, sorted by decreasing level: info, debug, warning and error. 

The **Application Identifier** refers to theId field value of the GridEngine 'UsersTracking'database table: GridInteractions.
The infrastructure part consists of different settings related to the destination of users job execution. The fields belonging to this category are:

 **Enable infrastructure**: A true/false flag which enables or disable the current infrastructure;

 **Infrastructure Name**: The infrastructure name for these settings;   

 **Infrastructure Acronym**: A short name representing the infrastructure;

 **BDII host**: The Infrastructure information system endpoint (URL). Infrastructure preferences have been thought initially for the elite Grid based infrastructures; 

 **WMS host**: It is possible to specify which is the brokering service endpoint (URL);

 **Robot Proxy values**: This is a collection of several values which configures the robot proxy settings (Host, Port, proxyID, VO, Role, proxy renewal);

 **Job requirements**: This field contains the necessary statements to specify a job execution requirement, such as a particular software, a particular number of CPUs/RAM, etc.

.. image:: images/settings.jpg

Actually, depending on the infrastructure, some of the fields above have an overloaded meaning. Please contact the support for further information or watch existing production portlet settings.


============
Usage
============

To run the Corsika simulation the user has to:

- Select the type of simulation to perform, corresponding to one of the three Corsika versions: single, array or epos-thining-qsj2.

- Choose whether the input file corresponds to a single simulation or to a group of them. In the later, files must be compressed on tar.gz format. 

- Choose whether to receive a confirmation email at the end of the execution-

- Choose what to do with the output files. If a Storage Element is desired, files will be uploaded and remain there for a long time. If not, they will be stored in the Science Gateway, altough their lifetime will probably be shorter. 

- Last but not least, choose an input file or insert a PID where the input file is stored. 

- The system will automatically create a name for the run. If a different one is desired, it can be freely modified. 

Each run will produce:

- std.txt: the standard output file;

- std.err: the standard error file;

- abinit.log: the application log file;

- some additional log files. By default, only the std OUT/ERR files will be provided;

- .tar.gz: the application results available through the gLibrary_ Metadata Server.

.. image:: images/input.png
   :align: center

A typical simulation produces, at the end, the following files:

.. code:: bash

	]$ tree 26_144903
	26_144903
	|-- corsika-Error.txt
	|-- corsika-Output.txt
	`-- results
	    |-- DAT030014
	    |-- DAT030014-0014-0527176.input
	    `-- DAT030014.dbase


.. _here: https://science-gateway.chain-project.eu/corsika_browse

To inspect Corsika log files:

- *navigate* the digital repository for the application clicking [ here_ ];

- *select* the digital assets of any interest for downloading as shown in the figure below:

.. image:: images/browse.png
      :align: center

============
References
============

.. _1: http://agenda.ct.infn.it/event/1110/

* CHAIN-REDS Conference: *"Open Science at the Global Scale: Sharing e-Infrastructures, Sharing Knowledge, Sharing Progress"* – March 31, 2015 – Brussels, Belgium [1_];

============
Contributors
============
Please feel free to contact us any time if you have any questions or comments.

.. _Sci-Track: http://rdgroups.ciemat.es/web/sci-track/
.. _INFN: http://www.ct.infn.it/

:Authors:
 `Manuel RODRIGUEZ-PASCUAL <mailto:manuel.rodriguez@ciemat.es>`_ - CIEMAT Sci-Track

 `Giuseppe LA ROCCA <mailto:giuseppe.larocca@ct.infn.it>`_ - Italian National Institute of Nuclear Physics (INFN_)



