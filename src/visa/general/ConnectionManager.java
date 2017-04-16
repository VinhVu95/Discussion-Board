package visa.general;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * To create a Connection object for use
 */
public class ConnectionManager  implements Constant{


  static {
      try {
          Class.forName(DRIVER_NAME).newInstance();
      }
      catch (Exception e){
          e.printStackTrace();
      }
  }

  /**
   * Get a database connection object.
   *
   * @return Connection object to the database.
   * @throws SQLException
   */
 // Database CHARSET=utf8

	    	//Database Collation = utf8_general_ci
  public static Connection getConnection() throws SQLException {
	  return DriverManager.getConnection(URL + "?useUnicode=true&characterEncoding=UTF-8", USERNAME, PASSWORD);
  }
  
  
  
  /**
   * close the given connection, statement and resultset
   *
   * @param conn the connection object to be closed
   * @param stmt the statement object to be closed
   * @param rs the resultset object to be closed
   */
  public static void close(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close ResultSet", ex);
    }
    
    close(conn,stmt);
  }
  
  
  /**
   * close the given connection, statement
   *
   * @param conn the connection object to be closed
   * @param stmt the statement object to be closed
   */
  public static void close(Connection conn, Statement stmt) {
    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close Statement", ex);
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close Connection", ex);
    }
  }
}
