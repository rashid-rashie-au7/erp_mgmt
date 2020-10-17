/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Client;


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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ClientCreationController implements Initializable {

    @FXML
    private TextField txtcode;
    @FXML
    private TextField txtname;
    @FXML
    private RadioButton rbtmale;
    @FXML
    private RadioButton rbtfemale;
    @FXML
    private TextField txtmob;
    @FXML
    private TextField txtoffice;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtplace;
    @FXML
    private TextField txtstreet;
    @FXML
    private TextField txtland;
    @FXML
    private TextField txtzip;
    @FXML
    private TextField txtdis;
    @FXML
    private TextField txtstate;
    @FXML
    private TextField txtOccp;
    @FXML
    private RadioButton rbtRetail;
    @FXML
    private RadioButton rbtWhole;
    @FXML
    private TextField txtgst;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Color x3;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnsave;
    @FXML
    private Button btnclear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Setcode();
        clear();
        setbuttons();
        listner_mob();
        listner_txtbx();
        
    }    
    
    private void listner_txtbx(){
        txtname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    rbtmale.requestFocus();
                }
            }
        });
        rbtmale.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtmob.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtfemale.setSelected(true);
                    rbtfemale.requestFocus();
                }
            }
        });
        rbtfemale.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtmob.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtmale.setSelected(true);
                    rbtmale.requestFocus();
                }
            }
        });
        txtmob.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtoffice.requestFocus();
                }
            }
        });
        txtoffice.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtemail.requestFocus();
                }
            }
        });
        txtemail.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtplace.requestFocus();
                }
            }
        });
        
        txtplace.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtstreet.requestFocus();
                }
            }
        });
        
        txtstreet.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtland.requestFocus();
                }
            }
        });
        txtland.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtzip.requestFocus();
                }
            }
        });
        txtzip.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtdis.requestFocus();
                }
            }
        });
        txtdis.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtstate.requestFocus();
                }
            }
        });
        
        txtOccp.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    rbtRetail.requestFocus();
                }
            }
        });
        rbtRetail.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
             public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtgst.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtWhole.setSelected(true);
                    rbtWhole.requestFocus();
                }
            }
        });
        rbtWhole.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
             public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtgst.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtRetail.setSelected(true);
                    rbtRetail.requestFocus();
                }
            }
        });
        txtgst.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   btnsave.requestFocus();
                }
            }
        });
    }
    
    private void clear(){
        ToggleGroup grp =new ToggleGroup();
        ToggleGroup grptype = new ToggleGroup();
        rbtmale.setToggleGroup(grp);
        rbtfemale.setToggleGroup(grp);
        rbtmale.setSelected(true);
        rbtRetail.setToggleGroup(grptype);
        rbtWhole.setToggleGroup(grptype);
        rbtRetail.setSelected(true);
        txtname.clear();
        txtmob.clear();
        txtemail.clear();
        txtoffice.clear();
        txtplace.clear();
        txtstreet.clear();
        txtland.clear();
        txtdis.clear();
        txtstate.clear();
        txtzip.clear();
        txtOccp.clear();
        txtgst.clear();
        Setcode();
    }
    
    private void setbuttons(){       
     btnupdate.setVisible(false);
    }

    private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtcode.setText("CUST"+no);
        txtname.requestFocus();
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_client");
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
            Logger.getLogger(ClientCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private boolean save() {
        boolean flag = false;
            try {
                String gender="";
                String type="";
                if(rbtmale.isSelected()){
                    gender="MALE" ; 
                }
                else{
                    gender="FEMALE";
                }
                if(rbtRetail.isSelected()){
                    type="Retailer";
                }
                else{
                    type="Whole Saler";
                }
                
                Statement st = db.con.createStatement();
                System.out.println("insert into " + db.schema + ".tbl_client values(null,'"+ txtcode.getText()+"','"+txtname.getText().toUpperCase().trim()+"','"+ gender +"','"+ txtmob.getText()+"','"+ txtoffice.getText()+"','"+ txtemail.getText()+"','"+ txtplace.getText()+"','"+ txtstreet.getText()+"','" + txtland.getText() + "','" + txtzip.getText() + "','" + txtdis.getText() + "','"+ txtstate.getText()+"','"+ txtOccp.getText()+"','" + type + "','"+ txtgst.getText()+"',1)");
                String query="insert into " + db.schema + ".tbl_client values(null,'"+ txtcode.getText()+"','"+txtname.getText().toUpperCase().trim()+"','"+ gender +"','"+ txtmob.getText()+"','"+ txtoffice.getText()+"','"+ txtemail.getText()+"','"+ txtplace.getText()+"','"+ txtstreet.getText()+"','" + txtland.getText() + "','" + txtzip.getText() + "','" + txtdis.getText() + "','"+ txtstate.getText()+"','"+ txtOccp.getText()+"','" + type + "','"+ txtgst.getText()+"',1)";
                System.out.println(""+query);
                int bool = st.executeUpdate(query);
                if (bool > 0) { 
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Client Saved Successfully!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    clear();
                    ClientMasterController cmc =new ClientMasterController();
                    cmc.populate_table();
                    
                }
                 flag = true;
                }
                else{
                    db.con.rollback();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Details Not Save !", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                    txtname.requestFocus();
                }
                }
                st.close();
                txtname.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(ClientCreationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear();        
        return flag;
    }
   
    private boolean updatedb() {    
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            String gender="";
            String type="";
                if(rbtmale.isSelected()){
                  gender="MALE" ; 
                }
                else{
                    gender="FEMALE";
                }
                if(rbtRetail.isSelected()){
                    type="Retailer";
                }
                else{
                    type="Whole Saler";
                }
            System.out.println("update " + db.schema + ".tbl_client set name= '" + txtname.getText().toUpperCase().trim() + "', gender= '" + gender + "', mob= '" + txtmob.getText()+ "',office='"+txtoffice.getText()+"', email= '" + txtemail.getText()+ "', place= '" + txtplace.getText()+ "', street= '" +txtstreet.getText()+ "', land= '" + txtland.getText() + "', zip= '" + txtzip.getText() + "', dis= '" + txtdis.getText()+ "', state= '" + txtstate.getText()+ "', occp= '" + txtOccp+ "', type= '" +type+ "',gst= '" + txtgst.getText()+ "' where code = '" + txtcode.getText() +"'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_client set name= '" + txtname.getText().toUpperCase().trim() + "', gender= '" + gender + "', mob= '" + txtmob.getText()+ "',office='"+txtoffice.getText()+"', email= '" + txtemail.getText()+ "', place= '" + txtplace.getText()+ "', street= '" +txtstreet.getText()+ "', land= '" + txtland.getText() + "', zip= '" + txtzip.getText() + "', dis= '" + txtdis.getText()+ "', state= '" + txtstate.getText()+ "', occp= '" + txtOccp+ "', type= '" +type+ "',gst= '" + txtgst.getText()+ "' where code = '" + txtcode.getText() +"'");
            if (bool > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Client Updated Successfully!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                clear();
               ClientMasterController cmc =new ClientMasterController();
                       cmc.populate_table();
                btnsave.setVisible(true);
                btnupdate.setVisible(false);
                }
                st.close();
                stage.close();
                flag = true;
            }
            } catch (SQLException ex) {
                Logger.getLogger(ClientCreationController.class.getName()).log(Level.SEVERE, null, ex);
                }
          return flag;
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
        txtoffice.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtoffice.setText(newValue.replaceAll("[^\\d]", ""));
            }
          }
        });
        txtzip.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtzip.setText(newValue.replaceAll("[^\\d]", ""));
            }
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
        Matcher matcher_email = pattern_email.matcher(txtemail.getText());
        Pattern pattern_gst = Pattern.compile(GST_PATTERN);
        Matcher matcher_gst = pattern_gst.matcher(txtgst.getText());
        if (txtname.getText().equalsIgnoreCase("")) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Name", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtname.requestFocus();
                    flag= false;
                    }         
        }else if (txtmob.getText().equalsIgnoreCase("")) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Mobile Number", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtmob.requestFocus();
                    flag= false;
                    }          
        } else if (!txtemail.getText().equals("")) {
            if (!matcher_email.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Vaild Email", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtemail.requestFocus();
                    flag= false;
                    }  
            }
        }else if (!txtgst.getText().equals("")) {
           if (!matcher_gst.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Vaild GSTIN", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtgst.requestFocus();
                     flag= false;
                    }  
            }
        }   
        return flag;     
    }

    public void setStage(Stage stage_add_client) {
        this.stage = stage_add_client;
        this.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            }
        });
    }

   public void fetch_for_update(String id) {
        btnsave.setVisible(false);
        btnupdate.setVisible(true);
        try {
                  Statement st = db.con.createStatement();
                  ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_client where code ='"+id+"'");
                  if (rs.next()) {
                        txtcode.setText(id);
                        txtname.setText(rs.getString("name"));
                        String sex = rs.getString("gender");
                        if(sex.equals("MALE")){
                            rbtmale.setSelected(true);
                        }
                        else{
                            rbtfemale.setSelected(true);
                        }
                        txtmob.setText(rs.getString("mob"));
                        txtemail.setText(rs.getString("email"));
                        txtoffice.setText(rs.getString("office"));                     
                        txtplace.setText(rs.getString("place"));
                        txtstreet.setText(rs.getString("street"));
                        txtland.setText(rs.getString("land"));
                        txtdis.setText(rs.getString("dis"));
                        txtstate.setText(rs.getString("state"));
                        txtzip.setText(rs.getString("zip"));
                        txtOccp.setText(rs.getString("occp"));
                        txtgst.setText(rs.getString("gst"));
                        String type = rs.getString("type");
                        if(type.equals("Retailer")){
                            rbtRetail.setSelected(true);
                        }
                        else{
                            rbtWhole.setSelected(true);
                        }  
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(ClientCreationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void btnupdate_onaction(ActionEvent event) {
         if(validation()){
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Are you sure to Update  this Clients ?", ButtonType.YES, ButtonType.NO);
          alert.showAndWait();
          if (alert.getResult() == ButtonType.YES) {   
            updatedb();
            stage.close();
          }
        } 
    }

    @FXML
    private void btnsave_onaction(ActionEvent event) {
        if (validation()) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you Sure to Save this Clients!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                save();
                stage.close();
                    }
        }
    }

    @FXML
    private void btn_clear_onaction(ActionEvent event) {
        clear();
    }
    
}
