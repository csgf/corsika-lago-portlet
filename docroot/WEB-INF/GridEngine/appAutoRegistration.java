/**
 * appAutoRegistration feature
 *
 * @author <a href="riccardo.bruno@ct.infn.it">Riccardo Bruno</a> 
 *
 * The autoregistration feature allows the web applications to auto-register into the GridOperations table 
 * of the GridEngine' UserTracking database. GridOperatons uses the id field as primary key but each
 * record is uniquely identified by the couple (portalName, applicationDescription)
 * The following code provides two class methods:
 * 
 *    getOperationId    - A private method that returns the correspondint id value of a given couple (portalName, applicationDescription)
 *                        it returns -1 in case the given couple does not exists yet in the table
 *    registerOperation - The public method called by the web application that checks if the given couple (portalName, applicationDescription)
 *                        exists and in case it does not exist insert a new entry inside the table GridOperation associated to the given couple
 *
 * This file has to be included into the Class: UsersTrackingDBInterface 
 * into the GridEngine code file: /it/infn/ct/GridEngine/UsersTracking/UsersTrackingDBInterface.java
 *
 */ 

        /**
        * Returns the id of a given couple (Application Portal Name, Application Description)
        * if the requested Grid operation record does not exists a negative value will be returned (-1)
        *
        * @param appPortal      Application portal name
        * @param appDescription Application portal description
        * @return  The UserTracking GridOperation identifier (GridOperations table index) if the couple (appPortal,appDescription) exists otherwise it returns -1
        * 
        */
        private int getOperationId(String appPortal, String appDescription) {
            int transactionId=-1;
            if (CreateDBConnection()) {
                String query = "SELECT id FROM GridOperations WHERE  portal='"+appPortal+"' and  description='"+appDescription+"'";
                try {
                    PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next() && transactionId == -1)
                        transactionId = rs.getInt(1);
                    rs.close();
                    pstmt.close();
                }                    
                catch(SQLException e) {
                    System.out.println("Caught SQL exception: "+e.toString());
                }
                CloseDBConnection();
            }
            return transactionId;
        }

        /**
        * Verify first that a given couple (Application Portal Name, Application Description) exists or not
        * into the UserTrackingDB and register it in case it is not yet inserted in GridOperations table
        *
        * @param appPortal       Application portal name
        * @param appDescription  Application portal description
        * @return The UserTracking GridOperation identifier (GridOperations table index) if the couple (appPortal,appDescription) exists or it has been successfully registered otherwise it returns -1
        * 
        */
        public int registerOperation(String appPortal, String appDescription) {
            int transactionId=getOperationId(appPortal, appDescription);
            if(transactionId < 0) {
                if (CreateDBConnection()) {
                    String query="INSERT INTO GridOperations (portal,description) VALUES ('"+appPortal+"','"+appDescription+"');";
                    try {                            
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate(query);
                        stmt.close();
                    } catch(SQLException e) {
                        System.out.println("Caught SQL exception: "+e.toString());
                    }
                    CloseDBConnection();
                }
                transactionId=getOperationId(appPortal, appDescription);
            }
            return transactionId;
        }
