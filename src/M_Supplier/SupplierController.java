/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Supplier;


import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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
public class SupplierController implements Initializable {

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtPname;
    @FXML
    private TextField txtmob;
    @FXML
    private TextField txtOffice;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtweb;
    @FXML
    private TextField txtBank;
    @FXML
    private TextField txtBranch;
    @FXML
    private TextField txtIfsc;
    @FXML
    private TextField txtAcno;
    @FXML
    private TextField txtGst;
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
    private Button btnUpdate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
     private Stage stage_e = new Stage(StageStyle.UTILITY);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listner_txtbx();
        clear();
        setbuttons();
        Setcode();
        listner_mob();
    } 
    
    private void listner_txtbx(){
        txtName.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
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
                    txtState.requestFocus();
                }
            }
        });
        txtState.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtPname.requestFocus();
                }
            }
        });
        txtPname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtmob.requestFocus();
                }
            }
        });
        txtmob.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtOffice.requestFocus();
                }
            }
        });
        
        txtOffice.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
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
                    txtweb.requestFocus();
                }
            }
        });
        txtweb.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtBank.requestFocus();
                }
            }
        });
        txtBank.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtBranch.requestFocus();
                }
            }
        });
        txtBranch.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtIfsc.requestFocus();
                }
            }
        });
        
        txtIfsc.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtAcno.requestFocus();
                }
            }
        });
        txtAcno.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtGst.requestFocus();
                }
            }
        });
        txtGst.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    btnSave.requestFocus();
                }
            }
        });      
    }
    
     private void clear(){
        txtName.clear();
        txtStreet.clear();
        txtCity.clear();
        txtState.clear();
        txtPname.clear();
        txtmob.clear();
        txtOffice.clear();
        txtEmail.clear();
        txtweb.clear();
        txtBank.clear();
        txtBranch.clear();
        txtIfsc.clear();        
        txtAcno.clear();
//        txtGst.clear();
        txtGst.setText("");
        Setcode();
    }
    
    private void setbuttons(){       
        btnUpdate.setVisible(false);
    }

    private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtCode.setText("SUP"+no);
        txtName.requestFocus();
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_supplier");
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
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private boolean save() {
        boolean flag = false;
            try {
                Statement st = db.con.createStatement();
                System.out.println("insert into " + db.schema + ".tbl_supplier values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ txtStreet.getText() +"','"+ txtCity.getText()+"','"+ txtState.getText()+"','"+ txtPname.getText()+"','"+ txtmob.getText()+"','" + txtOffice.getText() + "','" + txtEmail.getText() + "','" + txtweb.getText() + "','"+ txtBank.getText()+"','"+ txtBranch.getText()+"','" + txtIfsc.getText() + "','"+ txtAcno.getText()+"','"+ txtGst.getText()+"',1)");
                String query="insert into " + db.schema + ".tbl_supplier values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ txtStreet.getText() +"','"+ txtCity.getText()+"','"+ txtState.getText()+"','"+ txtPname.getText()+"','"+ txtmob.getText()+"','" + txtOffice.getText() + "','" + txtEmail.getText() + "','" + txtweb.getText() + "','"+ txtBank.getText()+"','"+ txtBranch.getText()+"','" + txtIfsc.getText() + "','"+ txtAcno.getText()+"','"+ txtGst.getText()+"',1)";
                System.out.println(""+query);
                int bool = st.executeUpdate(query);
                if (bool > 0) { 
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Supplier Saved Successfully!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    clear();
//                    populatetable();
                    SuppilerMasterController smc =new SuppilerMasterController();
                            smc.populate_table();
                    
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
                Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear();        
        return flag;
    }
   
    private boolean updatedb() {    
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_supplier set name= '" + txtName.getText().toUpperCase().trim() + "', street= '" + txtStreet.getText() + "', city= '" + txtCity.getText()+ "', state= '" + txtState.getText()+ "', pname= '" + txtPname.getText()+ "', mob= '" + txtmob.getText() + "', office= '" +txtOffice.getText()+ "', email= '" + txtEmail.getText() + "', website= '" + txtweb.getText() + "', bank= '" + txtBank.getText()+ "', branch= '" + txtBranch.getText()+ "', ifsc= '" +txtIfsc.getText()+ "', acno= '" + txtAcno.getText()+ "',gst= '" + txtGst.getText()+ "' where code = '" + txtCode.getText() +"'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_supplier set name= '" + txtName.getText().toUpperCase().trim() + "', street= '" + txtStreet.getText() + "', city= '" + txtCity.getText()+ "', state= '" + txtState.getText()+ "', pname= '" + txtPname.getText()+ "', mob= '" + txtmob.getText() + "', office= '" +txtOffice.getText()+ "', email= '" + txtEmail.getText() + "', website= '" + txtweb.getText() + "', bank= '" + txtBank.getText()+ "', branch= '" + txtBranch.getText()+ "', ifsc= '" +txtIfsc.getText()+ "', acno= '" + txtAcno.getText()+ "',gst= '" + txtGst.getText()+ "' where code = '" + txtCode.getText() +"'");
            if (bool > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                clear();
               SuppilerMasterController smc = new SuppilerMasterController();
               smc.populate_table();
                btnSave.setVisible(true);
                btnUpdate.setVisible(false);
                stage.close();
                }
                flag = true;
            }   else{
                db.con.rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Details Not Update !", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                txtName.requestFocus();
                }   
            }
            } catch (SQLException ex) {
                Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
                }
        return flag;  
    }
    
    private void deletedb(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Do you Want Delete this Supplier ?", ButtonType.YES, ButtonType.NO);
          alert.showAndWait();
          if (alert.getResult() == ButtonType.YES) {         
            try {
                Statement st = db.con.createStatement();
                System.out.println("update " + db.schema + ".tbl_supplier set status= 0 where code = '" + txtCode.getText()+ "'");
                int bool = st.executeUpdate("update " + db.schema + ".tbl_supplier set status= 0 where code = '" + txtCode.getText()+ "'");
                if (bool > 0) {
                    clear();
//                    populatetable();
                    btnSave.setVisible(true);
                    btnUpdate.setVisible(false);
                   
                }
            } catch (SQLException ex) {
                Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
    }
    
    private void listner_mob() {       
        txtmob.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtmob.setText(newValue.replaceAll("[^\\d]", ""));
            }
          }
        });
        
        txtOffice.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtOffice.setText(newValue.replaceAll("[^\\d]", ""));
            }
          }
        });
        txtAcno.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtAcno.setText(newValue.replaceAll("[^\\d]", ""));
            }
          }
        });
        txtGst.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            if (!newValue.matches("\\d*")) {
            txtGst.setText(newValue.toUpperCase());
//            }
          }
        });

    }
    
    private boolean validation(){
        boolean flag = true;
        final String EMAIL_PATTERN
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final String GST_PATTERN ="^([0][1-9]|[1-2][0-9]|[3][0-5])([a-zA-Z]{5}[0-9]{4}"
                                 + "[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$";
        Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher_email = pattern_email.matcher(txtEmail.getText());
        Pattern pattern_gst = Pattern.compile(GST_PATTERN);
        Matcher matcher_gst = pattern_gst.matcher(txtGst.getText());
        if (txtName.getText().equalsIgnoreCase("")) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Name", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtName.requestFocus();
                    flag= false;
                    }  
        } else if (txtCity.getText().equalsIgnoreCase("")) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter City", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtCity.requestFocus();
                     flag= false;
                    }          
        }else if (txtmob.getText().equalsIgnoreCase("")) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Mobile Number", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtmob.requestFocus();
                     flag= false;
                    }          
        }  
        else if (txtGst.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter GSTIN", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtGst.requestFocus();
                     flag= false;
                    }  
        }else if (!txtGst.getText().equals("")) {
           if (!matcher_gst.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Vaild GSTIN", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtGst.requestFocus();
                     flag= false;
                    }  
            }
        }   else if (!txtEmail.getText().equals("")) {
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
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you Sure to Update this Supplier!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    updatedb();
                }
        }
    }

    @FXML
    private void btn_save_onAction(ActionEvent event) {
        if (validation()) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you Sure to Save this Supplier!", ButtonType.OK);
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
                  ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_supplier where code ='"+id+"'");
                  if (rs.next()) {
                        txtCode.setText(id);
                        txtName.setText(rs.getString("name"));
                        txtStreet.setText(rs.getString("street"));
                        txtCity.setText(rs.getString("city"));
                        txtPname.setText(rs.getString("pname"));
                        txtmob.setText(rs.getString("mob"));
                        txtOffice.setText(rs.getString("office"));
                        txtEmail.setText(rs.getString("email"));
                        txtweb.setText(rs.getString("website"));
                        txtBank.setText(rs.getString("bank"));
                        txtBranch.setText(rs.getString("branch"));
                        txtIfsc.setText(rs.getString("ifsc"));
                        txtAcno.setText(rs.getString("acno"));
                        txtGst.setText(rs.getString("gst"));
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

   public void setStage(Stage stage_add) {
       this.stage = stage_add;
        this.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            }
        });
    }

    
    
    
}

