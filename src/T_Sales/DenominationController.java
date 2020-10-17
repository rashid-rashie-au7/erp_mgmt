/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import database.DBMySQL;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class DenominationController implements Initializable {

    @FXML
    private AnchorPane ancrcash;
    @FXML
    public TextField txtamt;
    @FXML
    private TextField txtpaidamt;
    @FXML
    private TextField txtbal;
    @FXML
    private RadioButton rbtcash;
    @FXML
    private RadioButton rbtcard;
    @FXML
    private AnchorPane ancrcard;
    @FXML
    private Button btnSave;
    @FXML
    public Label lblbillno;
    @FXML
    private ComboBox<String> cmbtype;
    @FXML
    private TextField txtname;
    @FXML
    public TextField txtcardamt;
    ObservableList<String> listCardType = FXCollections.observableArrayList( "Visa", "MasterCard","Maestro","RuPay","Credit");
    private Stage stage;
    public static String saveStatus = "";
    @FXML
    private TextField txtno;
    public static Create_SalesController tc;
    @FXML
    private AnchorPane ancrcredit;
    @FXML
    public TextField txtcrditamt;
    @FXML
    private TextField txtadv;
    @FXML
    private TextField txtcrditbal;
    @FXML
    private RadioButton rbtcredit;
    DBMySQL db = new DBMySQL();
    @FXML
    public Label lblclientid;
    @FXML
    public Label lbldate;
    @FXML
    private RadioButton rbtwallet;
    @FXML
    private AnchorPane anchrwallet;
    @FXML
    private TextField txtcrditamt1;
    @FXML
    private TextField txtadv1;
    @FXML
    private TextField txtcrditbal1;
    @FXML
    private TextField txtadv11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        saveStatus = "";
        cmbtype.setItems(listCardType);
        rbtcash.setSelected(true);
        ancrcard.setVisible(false);
        ancrcredit.setVisible(false);
        anchrwallet.setVisible(false);
        listner_radio_selection();
        txtpaidamt.setText("0.00");
        txtname.clear();
        txtno.clear();
        txtadv.setText("0.00");
        txtcrditamt.setText("0.00");
        txtbal.setText("0.00");
        txtcrditbal.setText("0.00");
        txtcardamt.setText("0.00");
        listner_txtbx();
    }    

    private void listner_txtbx(){
        txtpaidamt.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   Double amt=Double.parseDouble(txtamt.getText());
                   Double paidamt= Double.parseDouble(txtpaidamt.getText());
                   Double bal= paidamt - amt;
                   txtbal.setText(String.valueOf(bal));
                   btnSave.requestFocus();
                }
            }
        });
        rbtcash.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtpaidamt.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtcard.setSelected(true);
                    rbtcard.requestFocus();
                }
            }
        });
        rbtcard.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    cmbtype.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtcredit.setSelected(true);
                    rbtcredit.requestFocus();
                }
            }
        });
        
        rbtcredit.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtadv.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtcash.setSelected(true);
                    rbtcash.requestFocus();
                }
            }
        });
        
        txtname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   btnSave.requestFocus();
                }
            }
        });
        
        cmbtype.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtname.requestFocus();
                }
            }
        });
        
        txtpaidamt.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtpaidamt.setText(oldValue);
                Double amt= Double.parseDouble(txtamt.getText());
                Double paid=Double.parseDouble(txtpaidamt.getText());
                Double bal = amt - paid;
                BigDecimal balnc = new BigDecimal(bal).setScale(2, RoundingMode.HALF_EVEN);
                txtbal.setText(String.valueOf(balnc));
            }
        }
    });
        
        txtadv.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtadv.setText(oldValue);
                Double amt= Double.parseDouble(txtcrditamt.getText());
                Double paid=Double.parseDouble(txtadv.getText());
                Double bal = amt-paid;
                BigDecimal balnc = new BigDecimal(bal).setScale(2, RoundingMode.HALF_EVEN);
                txtcrditbal.setText(String.valueOf(balnc));
            }
        }
    });
        
        txtadv.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   Double amt= Double.parseDouble(txtcrditamt.getText());
                Double paid=Double.parseDouble(txtadv.getText());
                Double bal = amt - paid;
                BigDecimal balnc = new BigDecimal(bal).setScale(2, RoundingMode.HALF_EVEN);
                txtcrditbal.setText(String.valueOf(balnc));
                }
            }
        });
        
        txtpaidamt.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                 Double amt= Double.parseDouble(txtamt.getText());
                Double paid=Double.parseDouble(txtpaidamt.getText());
                Double bal = amt-paid;
                BigDecimal balnc = new BigDecimal(bal).setScale(2, RoundingMode.HALF_EVEN);
                txtbal.setText(String.valueOf(balnc));
                }
            }
        });
        
        txtno.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtno.setText(oldValue);
            }
        }
    });
    }
    
    private void listner_radio_selection() {
        ToggleGroup grp =new ToggleGroup();
        rbtcard.setToggleGroup(grp);
        rbtcash.setToggleGroup(grp);
        rbtcredit.setToggleGroup(grp);
        rbtwallet.setToggleGroup(grp);
        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                    if(rbtcash.isSelected()){
                       ancrcash.setVisible(true);
                       ancrcard.setVisible(false);
                       ancrcredit.setVisible(false);
                       anchrwallet.setVisible(false);
                    }else if(rbtcard.isSelected()){
                      ancrcard.setVisible(true);
                      ancrcash.setVisible(false);
                      ancrcredit.setVisible(false);
                      anchrwallet.setVisible(false);
                    }else if(rbtcredit.isSelected()){
                      ancrcard.setVisible(false);
                      anchrwallet.setVisible(false);
                      ancrcash.setVisible(false);
                      ancrcredit.setVisible(true);
                    }else if(rbtwallet.isSelected()){
                      anchrwallet.setVisible(true);
                      ancrcard.setVisible(false);
                      ancrcash.setVisible(false);
                      ancrcredit.setVisible(false);
                    }
            }
        });
    }
    
    private boolean paymenMenthodValid() {
        if (rbtcard.isSelected()) {
            if (cmbtype.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Select Card Type", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                  cmbtype.requestFocus();  
                }
                
                return false;
            }
        }else if(rbtcash.isSelected()) {
            if (txtpaidamt.getText().equalsIgnoreCase("0.00")) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Cash Recived", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    txtpaidamt.requestFocus();
                    } 
                
                return false;
            }
        }else if(rbtcredit.isSelected()) {
            if (txtadv.getText().equalsIgnoreCase("0.00")) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Advance Amount", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    txtadv.requestFocus();
                    } 
                
                return false;
            }
        }
        return true;
    }
    
    private boolean insertIntoPaymentMethod() {
        try {
            String type ="";
            Double cardamt =0.0;
            if (rbtcard.isSelected()) {
                type= "Card";  
                cardamt= Double.parseDouble(txtcardamt.getText());
            }else if(rbtcash.isSelected()){
                type= "Cash";
                cardamt=0.00;
            }else if(rbtcredit.isSelected()){
                type= "Credit";
                cardamt=0.00;
//                insertcreditbill();
            }else if(rbtwallet.isSelected()){
                type= "Wallet";
                cardamt=0.00;
//                insertcreditbill();
            }
            Statement st = db.con.createStatement();
            System.out.println("insert into " + db.schema + ".tbl_payment values(null, '" + lblbillno.getText() + "','" + lblclientid.getText() + "','"+lbldate.getText()+"', '" + type + "', " + txtamt.getText() + ", " + txtpaidamt.getText() + ", " +txtbal.getText()  + ", '" + cmbtype.getSelectionModel().getSelectedItem() + "', '" + txtname.getText() + "', '" +txtno.getText()+ "'," + txtadv.getText() + ", " +txtcrditbal.getText()+ ", 1)");
            int bool = st.executeUpdate("insert into " + db.schema + ".tbl_payment values(null, '" + lblbillno.getText() + "','" + lblclientid.getText() + "','"+lbldate.getText()+"', '" + type + "', " + txtamt.getText() + ", " + txtpaidamt.getText() + ", " +txtbal.getText()  + ", '" + cmbtype.getSelectionModel().getSelectedItem() + "', '" + txtname.getText() + "', '" +txtno.getText()+ "'," + txtadv.getText() + ", " +txtcrditbal.getText()+ ", 1)");
            if (bool > 0) {
                return true;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private boolean insertcreditbill() {
        try {
            Statement st = db.con.createStatement();
            String query="(select name from  " + db.schema + ".tbl_client where code = '"+ lblclientid.getText() +"')";
            System.out.println("insert into " + db.schema + ".tbl_credit values(null, '" + lblbillno.getText() + "','" + query + "','"+lbldate.getText()+"'," + txtcrditamt.getText() + "," + txtadv.getText() + ", " +txtcrditbal.getText()+ ")");
            int bool = st.executeUpdate("insert into " + db.schema + ".tbl_credit values(null, '" + lblbillno.getText() + "','" + query + "','"+lbldate.getText()+"'," + txtcrditamt.getText() + "," + txtadv.getText() + ", " +txtcrditbal.getText()+ ")");
            if (bool > 0) {
                return true;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    @FXML
    private void save_onAction(ActionEvent event) {
   
        if (paymenMenthodValid()) {
            insertIntoPaymentMethod();
            saveStatus = "save";       
            stage.close();
        }
    }

   public void setStage(Stage stagepayment) {
       this.stage=stagepayment;
    }
    
}
