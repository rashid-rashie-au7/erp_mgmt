/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Employ;

import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class EmployController implements Initializable {

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private RadioButton rbtMale;
    @FXML
    private RadioButton rbtFemale;
    @FXML
    private TextField txtMob;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtQul;
    @FXML
    private ComboBox<String> cmbDept;
    @FXML
    private TextField txtHouse;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtDis;
    @FXML
    private TextField txtState;
    @FXML
    private ComboBox<String> cmbId;
    @FXML
    private TextField txtIdno;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private DatePicker dpJoin;
    @FXML
    private TextField txtSalary;
    @FXML
    private TextField txtComments;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Color x22;
    @FXML
    private Font x12;
    @FXML
    private Color x3;
    @FXML
    private Font x11;
    @FXML
    private Color x31;
    @FXML
    private Font x13;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.DECORATED);
    private ObservableList<String> listdept = FXCollections.observableArrayList();
    private ObservableList<String> listid = FXCollections.observableArrayList();
    @FXML
    private Label lblpass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Setcode();
        clear();
        setbuttons();
        populateCombo_dept();
        populateCombo_id();
        listner_mob();
        listner_txtbx();
    }    
    
    
    private void listner_txtbx(){
        txtName.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    rbtMale.requestFocus();
                }
            }
        });
        rbtMale.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtMob.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtFemale.setSelected(true);
                    rbtFemale.requestFocus();
                }
            }
        });
        rbtFemale.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtMob.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtMale.setSelected(true);
                    rbtMale.requestFocus();
                }
            }
        });
        txtMob.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtEmail.requestFocus();
                }
            }
        });
        txtEmail.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtQul.requestFocus();
                }
            }
        });
        txtQul.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    cmbDept.requestFocus();
                }
            }
        });
        
        cmbDept.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtHouse.requestFocus();
                }
            }
        });
        
        txtHouse.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtStreet.requestFocus();
                }
            }
        });
        txtStreet.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtCity.requestFocus();
                }
            }
        });
        txtCity.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtDis.requestFocus();
                }
            }
        });
        txtDis.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtState.requestFocus();
                }
            }
        });
        
        txtState.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    cmbId.requestFocus();
                }
            }
        });
        cmbId.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtIdno.requestFocus();
                }
            }
        });
        txtIdno.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtUsername.requestFocus();
                }
            }
        });
        txtUsername.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtPassword.requestFocus();
                }
            }
        });
        
        txtPassword.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    dpJoin.requestFocus();
                }
            }
        });
        dpJoin.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtSalary.requestFocus();
                }
            }
        });
        txtSalary.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtComments.requestFocus();
                }
            }
        });
          txtComments.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   btnSave.requestFocus();
                }
            }
        });
    }
    
    private void clear(){
        ToggleGroup grp =new ToggleGroup();
        rbtMale.setToggleGroup(grp);
        rbtFemale.setToggleGroup(grp);
        rbtMale.setSelected(true);
        txtName.clear();
        txtMob.clear();
        txtEmail.clear();
        txtQul.clear();
        cmbDept.setValue(null);
        txtHouse.clear();
        txtStreet.clear();
        txtCity.clear();
        txtDis.clear();
        txtState.clear();
        cmbId.setValue(null);
        txtIdno.clear();
        txtUsername.clear();
        txtPassword.clear();
        dpJoin.setValue(LocalDate.now());
        txtSalary.setText("0.0");
        txtComments.clear();
        Setcode();
    }
    
    private void setbuttons(){       
        cmbDept.setItems(listdept);
        cmbId.setItems(listid);
        btnUpdate.setVisible(false);
    }

    private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtCode.setText("EMP"+no);
        txtName.requestFocus();
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_employ");
            if (rs.next()) {
                id = rs.getString(1);
                int count = rs.getInt(1);
                if (count == 0) {
                    id = "0";
                } else {                   
                   String str = rs.getString(2);                   
                String[] part = str.split("(?<=\\D)(?=\\d)");
                id=part[1];
                }
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void populateCombo_dept(){
        listdept.clear();
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_dept ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_dept ");
                        while (rs.next()) {
                            listdept.add(rs.getString("name"));
                        }
                        cmbDept.setItems(listdept);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
    private void populateCombo_id(){
        listid.clear();
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_id ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_id ");
                        while (rs.next()) {
                            listid.add(rs.getString("name"));
                        }
                        cmbId.setItems(listid);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
    private boolean save() {
        boolean flag = false;
            try {
                String gender="";
                if(rbtMale.isSelected()){
                  gender="MALE" ; 
                }
                else{
                    gender="FEMALE";
                }
                Statement st = db.con.createStatement();
                System.out.println("insert into " + db.schema + ".tbl_employ values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ gender +"','"+ txtMob.getText()+"','"+ txtEmail.getText()+"','"+ txtQul.getText()+"','"+ cmbDept.getSelectionModel().getSelectedItem() +"','"+ txtHouse.getText()+"','" + txtStreet.getText() + "','" + txtCity.getText() + "','" + txtDis.getText() + "','"+ txtState.getText()+"','"+ cmbId.getSelectionModel().getSelectedItem()+"','"+ txtIdno.getText()+"','" + txtUsername.getText() + "','"+ txtPassword.getText()+"','" + dpJoin.getValue() + "',"+ txtSalary.getText()+",'"+ txtComments.getText()+"',1)");
                String query="insert into " + db.schema + ".tbl_employ values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ gender +"','"+ txtMob.getText()+"','"+ txtEmail.getText()+"','"+ txtQul.getText()+"','"+ cmbDept.getSelectionModel().getSelectedItem() +"','"+ txtHouse.getText()+"','" + txtStreet.getText() + "','" + txtCity.getText() + "','" + txtDis.getText() + "','"+ txtState.getText()+"','"+ cmbId.getSelectionModel().getSelectedItem()+"','"+ txtIdno.getText()+"','" + txtUsername.getText() + "','"+ txtPassword.getText()+"','" + dpJoin.getValue() + "',"+ txtSalary.getText()+",'"+ txtComments.getText()+"',1)";
                System.out.println(""+query);
                int bool = st.executeUpdate(query);
                if (bool > 0) { 
                    login();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    clear();
//                    populatetable();
                    EmployMasterController emc =new EmployMasterController();
                    emc.populate_table();
                    }
                    flag = true;

                }
                else{
                    db.con.rollback();
                     Alert alert = new Alert(Alert.AlertType.ERROR, "Details Not Save !", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                txtName.requestFocus();
                }
                }
                st.close();
                txtName.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear();        
        return flag;
    }
    
       private boolean login() {
        boolean flag = false;
            try {     
                Statement st = db.con.createStatement();
                System.out.println("insert into " + db.schema + ".login values(null,'"+ txtUsername.getText()+"','"+txtPassword.getText()+"','"+ cmbDept.getSelectionModel().getSelectedItem() +"','"+txtCode.getText() +"')");
                String query="insert into " + db.schema + ".login values(null,'"+ txtUsername.getText()+"','"+txtPassword.getText()+"','"+ cmbDept.getSelectionModel().getSelectedItem() +"','"+txtCode.getText() +"')";
                System.out.println(""+query);
                int bool = st.executeUpdate(query);
                if (bool > 0) { 
                    flag = true;
                }
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear();        
        return flag;
    }
       
       private boolean Updatelogin() {
        boolean flag = false;
            try {     
                Statement st = db.con.createStatement();
                System.out.println("update " + db.schema + ".login set dept= '" + cmbDept.getSelectionModel().getSelectedItem() + "',username= '" + txtUsername.getText()+ "',password= '" + txtPassword.getText()+ "' where emp_code = '" + txtCode.getText() +"'");
                int bool = st.executeUpdate("update " + db.schema + ".login set dept= '" + cmbDept.getSelectionModel().getSelectedItem() + "',username= '" + txtUsername.getText()+ "',password= '" + txtPassword.getText()+ "' where emp_code = '" + txtCode.getText() +"'");
                if (bool > 0) { 
                    flag = true;
                }
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear();        
        return flag;
    }
   
    private boolean updatedb() {    
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            String gender="";
                if(rbtMale.isSelected()){
                  gender="MALE" ; 
                }
                else{
                    gender="FEMALE";
                }
            System.out.println("update " + db.schema + ".tbl_employ set name= '" + txtName.getText().toUpperCase().trim() + "', gender= '" + gender + "', mob= '" + txtMob.getText()+ "', email= '" + txtEmail.getText()+ "', qul= '" + txtQul.getText()+ "', dept= '" + cmbDept.getSelectionModel().getSelectedItem() + "', house= '" +txtHouse.getText()+ "', street= '" + txtStreet.getText() + "', city= '" + txtCity.getText() + "', dist= '" + txtDis.getText()+ "', state= '" + txtState.getText()+ "', idcard= '" + cmbId.getSelectionModel().getSelectedItem()+ "', idno= '" + txtIdno.getText()+ "',username= '" + txtUsername.getText()+ "',password= '" + txtPassword.getText()+ "',join_date= '" + dpJoin.getValue() + "',salary= '" + txtSalary.getText()+ "',remark= '" + txtComments.getText()+ "' where code = '" + txtCode.getText() +"'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_employ set name= '" + txtName.getText().toUpperCase().trim() + "', gender= '" + gender + "', mob= '" + txtMob.getText()+ "', email= '" + txtEmail.getText()+ "', qul= '" + txtQul.getText()+ "', dept= '" + cmbDept.getSelectionModel().getSelectedItem() + "', house= '" +txtHouse.getText()+ "', street= '" + txtStreet.getText() + "', city= '" + txtCity.getText() + "', dist= '" + txtDis.getText()+ "', state= '" + txtState.getText()+ "', idcard= '" + cmbId.getSelectionModel().getSelectedItem()+ "', idno= '" + txtIdno.getText()+ "',username= '" + txtUsername.getText()+ "',password= '" + txtPassword.getText()+ "',join_date= '" + dpJoin.getValue() + "',salary= '" + txtSalary.getText()+ "',remark= '" + txtComments.getText()+ "' where code = '" + txtCode.getText() +"'");
            if (bool > 0) {
                Updatelogin();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                clear();
               EmployMasterController emc =new EmployMasterController();
                       emc.populate_table();
                btnSave.setVisible(true);
                btnUpdate.setVisible(false);
                }
                st.close();
                stage.close();
                flag = true;
            }
            } catch (SQLException ex) {
                Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
                }
          return flag;
    }
    
    private void listner_mob() {       
        txtMob.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtMob.setText(newValue.replaceAll("[^\\d]", ""));
            }
          }
        });
    }
    
    private boolean validation(){
        boolean flag = true;
        final String EMAIL_PATTERN
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher_email = pattern_email.matcher(txtEmail.getText());
        if (txtName.getText().equalsIgnoreCase("")) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Name", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtName.requestFocus();
                    flag= false;
                    }  
        } else if (cmbDept.getSelectionModel().getSelectedItem()==null) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select a Department", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    cmbDept.requestFocus();
                    flag= false;
                    }          
        }else if (txtMob.getText().equalsIgnoreCase("")) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Mobile Number", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtMob.requestFocus();
                    flag= false;
                    }          
        }  
        else if (txtUsername.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Username", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtUsername.requestFocus();
                    flag= false;
                    }  
        } else if (txtPassword.getText().equalsIgnoreCase("")) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Password", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtPassword.requestFocus();
                    flag= false;
                    }  
        }  else if (!txtEmail.getText().equals("")) {
            if (!matcher_email.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Vaild Email", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtEmail.requestFocus();
                    flag= false;
                    }  
            }
        }
        return flag;
        
    }
    
    @FXML
    private void btnupdate_onAction(ActionEvent event) {
        if(validation()){
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Are you sure to Update  this Employee ?", ButtonType.YES, ButtonType.NO);
          alert.showAndWait();
          if (alert.getResult() == ButtonType.YES) {   
            updatedb();
          }
        } 
    }

    @FXML
    private void btn_save_onAction(ActionEvent event) {
        if (validation()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you Sure to Save this Employee!", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                save();
            }
        }
    }

    @FXML
    private void btnclear_onAction(ActionEvent event) {
        clear();
    }

    
    private void setFadeOutTransition() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage.close();
            }
        });
    
    }

    public void fetch_for_update(String id) {
       btnSave.setVisible(false);
       btnUpdate.setVisible(true);
        try {
                  Statement st = db.con.createStatement();
                  ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_employ where code ='"+id+"'");
                  if (rs.next()) {
                        txtCode.setText(id);
                        txtName.setText(rs.getString("name"));
                        String sex = rs.getString("gender");
                        if(sex.equals("MALE")){
                            rbtMale.setSelected(true);
                        }
                        else{
                            rbtFemale.setSelected(true);
                        }
                        txtMob.setText(rs.getString("mob"));
                        txtEmail.setText(rs.getString("email"));
                        txtQul.setText(rs.getString("qul"));                     
                        cmbDept.setValue(rs.getString("dept"));
                        txtHouse.setText(rs.getString("house"));
                        txtStreet.setText(rs.getString("street"));
                        txtCity.setText(rs.getString("city"));
                        txtDis.setText(rs.getString("dist"));
                        txtState.setText(rs.getString("state"));
                        cmbId.setValue(rs.getString("idcard"));
                        txtIdno.setText(rs.getString("idno"));
                        txtUsername.setText(rs.getString("username"));
                        txtPassword.setText(rs.getString("password"));
//                        java.sql.Date dp =rs.getDate("join_date");
                        dpJoin.setValue(rs.getDate("join_date").toLocalDate());
                        txtSalary.setText(rs.getString("salary"));
                        txtComments.setText(rs.getString("remark"));
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(EmployController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

   public void setStage(Stage stage_add_emp) {
        this.stage = stage_add_emp;
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
