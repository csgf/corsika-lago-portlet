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

// AppLogger class (No customizations needed)

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Although developers can use System.out.println to watch their own console outputs
// the use of Java logs is highly recommended.
// Java Log object offers different output levels to show information:
//    trace
//    debug
//    info
//    warn
//    error
//    fatal
// All of them accept a String as parameter containing the proper message to show.
// AppLogger class uses  LogLevel eunerated type to express the log level verbosity
// the setLogLevel method allows the portlet to print-out all logs types equal
// or below the given log level accordingly to the priority:
//       trace,debug,info,warn,erro,fatal
enum LogLevels {
    trace,
    debug,
    info,
    warn,
    error,
    fatal
}
// The AppLogger class wraps the apache.common Log object allowing the user to
// enable/disable log accordingly to a given loglevel; the higher is the level 
// more verbose will be the produced output
class AppLogger {
    // Values associated 
    private static final int   TRACE_LEVEL=6;
    private static final int   DEBUG_LEVEL=5;
    private static final int    INFO_LEVEL=4;
    private static final int    WARN_LEVEL=3;
    private static final int   ERROR_LEVEL=2;
    private static final int   FATAL_LEVEL=1;
    private static final int UNKNOWN_LEVEL=0;

    private Log _log;                        
    private int logLevel=AppLogger.INFO_LEVEL;

    public void setLogLevel(String level) {
        switch(LogLevels.valueOf(level)) {
            case trace:
                logLevel=AppLogger.TRACE_LEVEL;
                break;                    
            case debug:
                logLevel=AppLogger.DEBUG_LEVEL;
                break;
            case info:
                logLevel=AppLogger.INFO_LEVEL;
                break;
            case warn:
                logLevel=AppLogger.WARN_LEVEL;
                break;
            case error:
                logLevel=AppLogger.ERROR_LEVEL;
                break;
            case fatal:
                logLevel=AppLogger.FATAL_LEVEL;
                break;
            default:
                logLevel=AppLogger.UNKNOWN_LEVEL;                                                              
        }            
    }
    public AppLogger(Class cname) {
        _log = LogFactory.getLog(cname);
    }
    public void trace(String s) {
        if(   _log.isTraceEnabled()
            && logLevel >= AppLogger.TRACE_LEVEL)
                _log.trace(s);
    }
    public void debug(String s) {
        if(   _log.isDebugEnabled()
            && logLevel >= AppLogger.DEBUG_LEVEL)
                _log.trace(s);
    }
    public void info(String s) {
        if(   _log.isInfoEnabled()
            && logLevel >= AppLogger.INFO_LEVEL)
                _log.info(s);
    }
    public void warn(String s) {
        if(   _log.isWarnEnabled()
            && logLevel >= AppLogger.WARN_LEVEL)
                _log.warn(s);
    }
    public void error(String s) {
        if(   _log.isErrorEnabled()
            && logLevel >= AppLogger.ERROR_LEVEL)
                _log.error(s);
    }
    public void fatal(String s) {
        if(   _log.isFatalEnabled()
            && logLevel >= AppLogger.FATAL_LEVEL)
                _log.fatal(s);
    }
} // AppLogger
