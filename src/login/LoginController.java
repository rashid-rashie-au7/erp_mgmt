/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;
import database.DBMySQL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import static miw.Tools.filter;

/**
 * FXML Controller class
 *
 * @author miw101
 */
public class LoginController implements Initializable {

    public static String Login_id = "";
    public static String sms_sendername = "";
    public static String sms_type = "";
    public static String sms_scheduledticket = "";
    public static String sms_checkout_ticket = "";
    public static String sms_companyname = "SPARK";
    public static String service_tax_no = "AACCL6304KSD001";
    public static String service_amount = "0.0";
    

    private Stage stage;
    public static boolean error_flag = false;
    String userName = "";
    String passWord = "";
    DBMySQL db = new DBMySQL();
    
    @FXML
    private Button button_click;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button cancel;
    static public Stage stage_controller;
    @FXML
    private Label compony_name;
    @FXML
    private Label compny_address;
    public static String userType = "";
    Properties properties = new Properties();
    private String name = "";
    private String location = "";
    private String ph = "";
    @FXML
    private Font x1;
    @FXML
    private Color x2;
    @FXML
    private Font x3;
    public static String trackingId = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        checkScehmaExist();

        companySettings();

        password.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    login();
                }
            }
        });
    }

    @FXML
    private void button_click_action(ActionEvent event) {
        login();
    }

    public void login() {
       
        userName = trim(filter(username.getText()));
        passWord = filter(password.getText());
        if (userName.length() > 0 && passWord.length() > 0) {

            if (verifyAdmin()) {
               
                Login_id = userName;
                showHomePage();
            } 
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                username.requestFocus();
                }
            }
        } else {
              Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                username.requestFocus();
                }
        }
    }

    @FXML
    private void cancel_onaction(ActionEvent event) {
        setFadeTransition();
    }

    public void setStageAndSetupListeners(Stage stage) {
        stage_controller = stage;
    }

    public void setstage(Stage stage) {
        this.stage = stage;
        stage_controller.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                setFadeTransition();
            }
        });
    }

    private void setFadeTransition() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage_controller.getScene().getRoot());
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
    }

    private void showHomePage() {
        Stage stage = new Stage();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setResizable(false);
        stage.setTitle("De Forum");
        stage.getIcons().add(new javafx.scene.image.Image("/login/sm.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MDI/MDI.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            MDI.MDIController obj = loader.getController();
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage.setX(rectangle2D.getMinX());
            stage.setY(rectangle2D.getMinY());
            stage.setWidth(rectangle2D.getWidth());
            stage.setHeight(rectangle2D.getHeight());
            obj.setStage(stage, rectangle2D);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean verifyAdmin() {
        try {
            Statement stlogin = db.con.createStatement();
            ResultSet rsLogin = stlogin.executeQuery("select username, password from " + db.schema + ".login where username= '" + userName + "' and password ='" + passWord + "' and id = 1");
            if (rsLogin.next()) {
                if ((rsLogin.getString("username").equals(username.getText())) && (rsLogin.getString("password").equals(password.getText()))) {
                    userType = "Admin";
                    stage_controller.close();
                    return true;
                }
            }
            stlogin.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

   

    public void companySettings() {
        InputStream inputStream = this.getClass().getResourceAsStream("/login/company_settings.properties");
        try {
            properties.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        name = properties.getProperty("name");
        location = properties.getProperty("location");
        ph = properties.getProperty("ph");
        service_tax_no = properties.getProperty("service_tax_no");
        compony_name.setText(name);
        compny_address.setText(location);
    }

    private void checkScehmaExist() {
        Properties property = new Properties();
        InputStream stream = getClass().getResourceAsStream("/database/DBConfig.properties");
        try {
            property.load(stream);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        final String usernames = property.getProperty("db.username");
        final String pass = property.getProperty("db.password");
        final String schema = property.getProperty("db.schema");
        final String host = property.getProperty("host");
        final String port = property.getProperty("port");
        final String database = property.getProperty("database");
        final String mysql = property.getProperty("mysql");
        boolean flag = false;
        try {
            ResultSet res = db.con.getMetaData().getCatalogs();
            while (res.next()) {
                String databases = res.getString("TABLE_CAT");
                if (databases.equalsIgnoreCase(schema)) {
                    flag = true;
                    System.err.println("Database Found");
                }
            }
        } catch (Exception e) {
            int l = e.getStackTrace().length;
            StackTraceElement str[] = e.getStackTrace();
            for (int i = 0; i < l; i++) {
            }
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }

        if (!flag) {
            System.err.println("Database Not Found");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database Not Found:Restore Database ", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SQL File", "*.sql");
                fileChooser.getExtensionFilters().add(extensionFilter);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    String path = file.getAbsolutePath().replaceAll("\\\\", "/");
                    String[] command = new String[]{"\"" + mysql + "\"", "" + database + "", "--user=" + usernames + "", "--password=" + pass + "", "-e", "source " + path};
                    try {
                        Process restore = Runtime.getRuntime().exec(command);
                        int i = restore.waitFor();
                        if (i == 0) {
                            Dialogs.showInformationDialog(stage, null, "DB Restore Complete", "Restore Database");
                        } else {
                            Dialogs.showErrorDialog(stage, null, "DB Restore Failed", "Restore Database");
                        }
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                   
             
        }
            
        }
    }

    }
