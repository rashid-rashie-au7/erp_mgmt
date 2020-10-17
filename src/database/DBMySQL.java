package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javafx.scene.control.Dialogs;

public class DBMySQL {

      public Connection con = null;
      private Properties configProp;
      String username = "";
      String password = "";
      String url = "";
      String driver = "";
      public String schema = "";

      public DBMySQL() {
            try {
                  configProp = new Properties();
                  Thread currentThread = Thread.currentThread();
                  ClassLoader contextClassLoader = currentThread.getContextClassLoader();
                  InputStream propertiesStream = contextClassLoader.getResourceAsStream("database/DBConfig.properties");
                  configProp.load(propertiesStream);
            }
            catch (IOException e) {
                  //e.printStackTrace();
                  int l = e.getStackTrace().length;
                  StackTraceElement str[] = e.getStackTrace();
                  for (int i = 0; i < l; i++) {
                  }
            }
            driver = configProp.getProperty("db.driver");
            url = configProp.getProperty("db.url");
            username = configProp.getProperty("db.username");
            password = configProp.getProperty("db.password");
            schema = configProp.getProperty("db.schema");
            try {
                  Class.forName(driver);
                  con = DriverManager.getConnection(url, username, password);
            }
            catch (ClassNotFoundException | SQLException e) {
//                  Dialogs.showInformationDialog(null, "Please contact Software provider", "DB Error!");
                  int l = e.getStackTrace().length;
                  StackTraceElement str[] = e.getStackTrace();
                  for (int i = 0; i < l; i++) {
                  }
            }
      }
}
