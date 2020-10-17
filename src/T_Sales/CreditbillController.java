/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;


import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class CreditbillController implements Initializable {

    @FXML
    private TextField txtbill;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtbillamt;
    @FXML
    private TextField txtpaid;
    @FXML
    private TextField txtamt;
    @FXML
    private TextField txtbal;
    @FXML
    private TableView<objcredit> tbldata;
    @FXML
    private TableColumn colno;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colbillamt;
    @FXML
    private TableColumn colpaid;
    @FXML
    private TableColumn colbal;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
    private Stage stage = new Stage(StageStyle.UTILITY);
    public static ObservableList<objcredit> table_data = FXCollections.observableArrayList();
    public static ObservableList<objcredit> table_data_search = FXCollections.observableArrayList();
    private DBMySQL db = new DBMySQL();
    @FXML
    private DatePicker dpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clear();
        set_table();
        listner_adv_search_name();
        listner_adv_search_bill();
        lisnterTableClick();
    }    
    
    private void clear(){
        dpdate.setValue(LocalDate.now());
        txtbill.clear();
        txtname.clear();
        txtbillamt.clear();
        txtpaid.clear();
        txtamt.clear();
        txtbal.clear();
        populate_table();
    }
    
        private void set_table() {
        colno.setCellValueFactory(new PropertyValueFactory<objcredit, String>("no"));
        colname.setCellValueFactory(new PropertyValueFactory<objcredit, String>("name"));
        colbillamt.setCellValueFactory(new PropertyValueFactory<objcredit, String>("billamt"));
        colpaid.setCellValueFactory(new PropertyValueFactory<objcredit, String>("paid"));
        colbal.setCellValueFactory(new PropertyValueFactory<objcredit, String>("bal"));
        
        table_data.add(new objcredit("", "", "", "", ""));
        tbldata.setItems(table_data);

    }
    
    public void populate_table() {
        table_data.removeAll(table_data);
        try {
            Statement st = db.con.createStatement();
            System.out.println("select p.billno, p.billamt,p.adv ,p.creditbal,c.name from " + db.schema + ".tbl_payment p," + db.schema + ".tbl_client c where p.creditbal > 0 and p.client = c.code");
            ResultSet rs = st.executeQuery("select p.billno, p.billamt,p.adv ,p.creditbal,c.name from " + db.schema + ".tbl_payment p," + db.schema + ".tbl_client c where p.creditbal > 0 and p.client = c.code");
            while (rs.next()) {
                
                table_data.add(new objcredit(rs.getString("billno"), rs.getString("name"), rs.getString("billamt"), rs.getString("adv"), rs.getString("creditbal")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(CreditbillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listner_adv_search_name(){
        txtname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtname.getText();
                if (text.equals("")) {
                    tbldata.setItems(table_data);
                }
                else {
                    for (objcredit obj : table_data) {
                        if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tbldata.setItems(table_data_search);
                    
                }
            }
        });
    }
    
    private void listner_adv_search_bill(){
        txtbill.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtbill.getText();
                if (text.equals("")) {
                    tbldata.setItems(table_data);
                }
                else {
                    for (objcredit obj : table_data) {
                        if (obj.getNo().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tbldata.setItems(table_data_search);
                    
                }
            }
        });
    }
    
    private void lisnterTableClick() {
        tbldata.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.PRIMARY && !tbldata.getSelectionModel().isEmpty()) {
                    fetch(tbldata.getSelectionModel().getSelectedItem());
                }
            }
        });        
        txtamt.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                  Double balnc =0.0;
                  Double bal = Double.parseDouble(txtbal.getText());;
                  Double amnt = Double.parseDouble(txtamt.getText());
                  balnc = bal - amnt;
                  txtbal.setText(String.valueOf(balnc));
                }
            }
        });
    }
   
    private void fetch(objcredit obj) {
       
        txtbill.setText(obj.getNo());
        txtname.setText(obj.getName()); 
        txtbillamt.setText(obj.getBillamt());
        txtpaid.setText(obj.getPaid());
        txtbal.setText(obj.getBal());
    }
    
    
    private boolean save() {
        boolean flag = false;
            try {
                Statement st = db.con.createStatement();
                System.out.println("insert into " + db.schema + ".tbl_credit values(null,'"+ txtbill.getText()+"','"+txtname.getText()+"','"+dpdate.getValue()+"','"+txtbillamt.getText()+"','"+txtpaid.getText()+"','"+txtamt.getText()+"','"+txtbal.getText()+"')");
                String query="insert into " + db.schema + ".tbl_credit values(null,'"+ txtbill.getText()+"','"+txtname.getText()+"','"+dpdate.getValue()+"','"+txtbillamt.getText()+"','"+txtpaid.getText()+"','"+txtamt.getText()+"','"+txtbal.getText()+"')";
                System.out.println(""+query);
                int bool = st.executeUpdate(query);
                if (bool > 0) {
                    updatedb();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Credit Details Saved Successfully!", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    clear();
                    populate_table();
                    }
                    flag = true;
                }
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(CreditbillController.class.getName()).log(Level.SEVERE, null, ex);
            }
                  
        return flag;
    }
   
    private void updatedb() {        
        try {
            Double adv = 0.0;
            Double bal = Double.parseDouble(txtbal.getText());;
            Double amnt = Double.parseDouble(txtamt.getText());
            Double paid = Double.parseDouble(txtpaid.getText());
            
            adv = paid + amnt;
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_payment set adv= " + adv + ", creditbal= '" + bal+ "' where billno = '" + txtbill.getText()+ "'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_payment set adv= " + adv + ", creditbal= '" + bal+ "' where billno = '" + txtbill.getText()+ "'");
            if (bool > 0) {
                            }
            } catch (SQLException ex) {
                Logger.getLogger(CreditbillController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
 
    @FXML
    private void btn_save_onAction(ActionEvent event) {
        if (txtamt.getText().equalsIgnoreCase("")) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Amount", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    txtamt.requestFocus();
                    }  
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save Details", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    save();
                    }  
            }
    }

    @FXML
    private void btnclear_onAction(ActionEvent event) {
        clear();
    }

    public void setStage(Stage stage_return) {
       this.stage= stage_return;
               stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                setFadeOutTransition();
            }
        });
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    t.consume();
                    setFadeOutTransition();
                }
            }
        });
        
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F8) {
                    t.consume();
                    btnClear.fire();
                }
            }
        });
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F9) {
                    t.consume();
//                   btnPrint.fire();
                }
            }
        });
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F10) {
                    t.consume();
                   btnSave.fire();
                }
            }
        });
        
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
 
    
}
