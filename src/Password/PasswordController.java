/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Password;

import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import static miw.Tools.filter;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class PasswordController implements Initializable {

    @FXML
    private Font x1;
    @FXML
    private PasswordField passFieldCurrAdminPass;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField passFieldNewPass;
    @FXML
    private PasswordField passFieldReTypeNewPass;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClear;
    DBMySQL db = new DBMySQL();
    public int id;
    private Stage stage = new Stage(StageStyle.DECORATED);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clear();
        username();
    }   
    
    private void username(){
        txtUsername.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    fetchid();
                }
            }
        });
    }
     private void fetchid() {
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select id from " + db.schema + ".login where username = '"+txtUsername.getText()+"' ");
            if (rs.next()) {
               id = rs.getInt("id");
                System.out.println("id===="+id);
               if(id == 0){
                 Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid Username",ButtonType.OK);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        txtUsername.requestFocus();
                    }
                    else{
                        passFieldCurrAdminPass.requestFocus();
                    }
               }
               
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        passFieldCurrAdminPass.requestFocus();
    }

    private void setFadeOutTransition() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage.close();
                t.consume();
            }
        });
    }

    private boolean currAdminPasswordValid(String currentPassword) {
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select password from " + db.schema + ".login where username = '"+txtUsername.getText()+"'");
            if (rs.next()) {
                if (rs.getString("password").equals(currentPassword)) {
                    return true;
                }
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean update(String username, String password, int id) {
        try {
            Statement st = db.con.createStatement();
            int bool = st.executeUpdate("update " + db.schema + ".login set username = '" + filter(username) + "', password = '" + filter(password) + "' where id = "+id+" ");
            if (bool > 0) {
                return true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void clear() {
        passFieldCurrAdminPass.clear();
        passFieldNewPass.clear();
        passFieldReTypeNewPass.clear();
    }

    @FXML
    private void btnUpdateOnaction(ActionEvent event) {
     if (passFieldCurrAdminPass.getText().equals("")) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Enter Current Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    passFieldCurrAdminPass.requestFocus();
                    }
        }
        else if (txtUsername.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Enter Username!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                   txtUsername.requestFocus();
                }
        }
        else if (passFieldNewPass.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Enter a New Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                   passFieldNewPass.requestFocus();
                    }  
        }
        else if (passFieldReTypeNewPass.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Retype New Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                     passFieldReTypeNewPass.requestFocus();
                    }
        }
        else if (!currAdminPasswordValid(passFieldCurrAdminPass.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Incorrect Current Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    passFieldCurrAdminPass.requestFocus();
                    }
        }
        else if (!passFieldNewPass.getText().equals(passFieldReTypeNewPass.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Check New Password and Retype Password!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    passFieldNewPass.clear();
                    passFieldReTypeNewPass.clear();
                    passFieldNewPass.requestFocus();
                    }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to update?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    if (update(txtUsername.getText(), passFieldNewPass.getText(),id)) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Password Sucessfully Updated!", ButtonType.OK);
                        alert1.showAndWait();
                        if (alert1.getResult() == ButtonType.OK) {
                            clear();
                        }
                    }
                }
            }
        }
    
    @FXML
    private void btnClearOnAction(ActionEvent event) {
        clear();
    }

    public void setStage(Stage stage_pass) {
         this.stage = stage_pass;
        this.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                    setFadeOutTransition();
                }
            }
        });
    }
    
}
