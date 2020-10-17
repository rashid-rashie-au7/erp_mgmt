/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_StockTransfer;

import com.miw.control.sbox.SuggessionBox;
import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class TransferController implements Initializable {

    @FXML
    private TableView<objtra> tblview;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colqty;
    @FXML
    private TextField txtid;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField txttime;
    @FXML
    private TextField txtcode;
    @FXML
    private HBox hbxname;
    @FXML
    private Button btnsave;
    @FXML
    private Button btnclear;
    @FXML
    private Hyperlink hplremove;
    @FXML
    private ComboBox<String> cmbwh;
    @FXML
    private TextField txtqty;
    SuggessionBox sbxname =new SuggessionBox();
    ObservableList<String> listname = FXCollections.observableArrayList();
    ObservableList<String> listwh = FXCollections.observableArrayList();
    ObservableList<objtra> tblData = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.DECORATED);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clearall();
        initClock();
        setTable();
        setsbox();
        populateCombo_wh();
        listner_sbox_item();
        lisnterTableClick();
        
    }
     private void clear(){
        txtcode.clear();
        txtqty.clear();
        sbxname.clear();
    }
     
     private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtid.setText("TRA"+no);
        sbxname.requestFocus();
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_stock_transfer");
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
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
     
    private void clearall(){
        txtcode.clear();
        txtqty.clear();
        sbxname.clear();
        cmbwh.getSelectionModel().select(0);
        dpdate.setValue(LocalDate.now());
        Setcode();
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        txttime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
     private void setTable() {
        colid.setCellValueFactory(new PropertyValueFactory<objtra, String>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<objtra, String>("name"));
        colqty.setCellValueFactory(new PropertyValueFactory<objtra, String>("qty"));
        tblview.setItems(tblData);
    }
    
    private void setsbox() {
        sbxname = new SuggessionBox();
        sbxname.setPrefSize(230, 25);
        hbxname.getChildren().add(sbxname);     
    }
    
    private void populateCombo_wh(){
        listwh.clear();
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_wh ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_wh ");
                        while (rs.next()) {
                            listwh.add(rs.getString("name"));
                        }
                        cmbwh.setItems(listwh);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
     private void listner_sbox_item() {
        sbxname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listname.removeAll(listname);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_item where status = 1 and qty>0");
                        while (rs.next()) {
                            listname.add(rs.getString("name"));
                        }
                        sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        sbxname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    //txtprice.requestFocus();
                    setvalue();
                }
            }
        });
        
        txtqty.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                checkStock();    
                }
            }
        });      
    }
    
    private void checkStock() {
        Double Stock = 0.0;
        Double qty = Double.parseDouble(txtqty.getText());
        try {
            Statement stmt = db.con.createStatement();
            System.out.println("select stock from " + db.schema + ".tbl_item where name = '" + sbxname.getText() + "' ");
            ResultSet rst = stmt.executeQuery("select  stock from " + db.schema + ".tbl_item where name = '" + sbxname.getText() + "' ");
            while (rst.next()) {
                Stock = rst.getDouble("stock");
                if (qty > Stock) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, sbxname.getText() + " Out Of Stock", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        txtqty.requestFocus();
                    }
                } else {
                    System.out.println("inside else part");
                    productExistInTbl(sbxname.getText());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void productExistInTbl(String prdt) {
         System.out.println("inside exixst function");
         System.out.println("inside exixst function===========  "+prdt);
        if(tblData.size()>0){
            for (objtra obj : tblData) {
            System.out.println("inside for function===========  "+prdt);  
            if (obj.getName().equalsIgnoreCase(prdt)) {
                System.out.println("inside exixst function  if");
                Alert alert = new Alert(Alert.AlertType.ERROR, " Item Already Exist In The Table ", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {  
                    sbxname.requestFocus();
                }
            } else
            {
                System.out.println("inside exixst function elseeee");
                Addtable();
            }
            }     
        } else{
            Addtable();
        }
   }
    
    private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select code,stock from " + db.schema + ".tbl_item where name = '" + sbxname.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select code,stock from " + db.schema + ".tbl_item where name = '" + sbxname.getText() + "' ");
                while (rst.next()) {
                    txtqty.setText(rst.getString("stock"));
                   txtcode.setText(rst.getString("code"));

                }
                txtqty.requestFocus();
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Addtable() {
        String id = txtcode.getText();
        String prdt = sbxname.getText();
        System.out.println("name== " + prdt);
        String qty = txtqty.getText();
        objtra obj;
        obj = new objtra(id, prdt, qty);
        tblview.getItems().add(obj);
        clear();
    }
        private boolean save()throws SQLException {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("insert into " + db.schema + ".tbl_stock_transfer values(null,'" + txtid.getText() + "','"+dpdate.getValue()+"','"+txttime.getText()+"', '" + cmbwh.getSelectionModel().getSelectedItem() + "',1)");
            String query = "insert into " + db.schema + ".tbl_stock_transfer values(null,'" + txtid.getText() + "','"+dpdate.getValue()+"','"+txttime.getText()+"', '" + cmbwh.getSelectionModel().getSelectedItem() + "',1)";
            System.out.println("" + query);
            int bool = st.executeUpdate(query);
            if (bool > 0) {
                save_items(txtid.getText());
                StockUpdate();
                StockMasterUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Details Sucessfully Saved ", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    clearall();
                    TransferMasterController pmc =new TransferMasterController();
                    pmc.populate_table();
                    stage.close();
                    
                }
                
                flag = true;

            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearall();
        return flag;
    }

    private boolean save_items(String id) {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objtra obj : tblData) {
                if (obj.getId().equals("") && obj.getName().equals("") && obj.getQty().equals("")) {
                    continue;
                }
                System.out.println("insert into " + db.schema + ".tbl_stock_trnsfr_items values(null, '" + id + "','"+obj.getId()+"', '" + obj.getName() + "', " + obj.getQty() + ")");
                bool = st.executeUpdate("insert into " + db.schema + ".tbl_stock_trnsfr_items values(null, '" + id + "','"+obj.getId()+"','" + obj.getName() + "', " + obj.getQty() + ")");
            }

            if (bool > 0) {
                flag = true;
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    private boolean StockMasterUpdate() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objtra obj : tblData) {
                if (obj.getId().equals("") && obj.getName().equals("") && obj.getQty().equals("")) {
                    continue;
                }
                System.out.println("update " + db.schema + ".tbl_item set stock= stock - " + obj.getQty() + " where name='" + obj.getName() + "' ");
                bool = st.executeUpdate("update " + db.schema + ".tbl_item set stock= stock - " + obj.getQty() + " where name='" + obj.getName() + "'");
            }
            if (bool > 0) {
                flag = true;
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }
    
    private boolean StockUpdate() {
       
        Double qtyyyy = 0.0;
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objtra obj : tblData) {
                if (obj.getId().equals("") && obj.getName().equals("") && obj.getQty().equals("")) {
                    continue;
                }
                String database ="";
                String wh = cmbwh.getSelectionModel().getSelectedItem();
                if (wh.equals("TAMILNADU")) {
                    database ="tbl_item_tn";
                }else if (wh.equals("MAHARASHTRA")) {
                    database="tbl_item_mh";
                }
                System.out.println("update " + db.schema + "."+database+" set stock= stock + " + obj.getQty() + " where name='" + obj.getName() + "' ");
                bool = st.executeUpdate("update " + db.schema + "."+database+" set stock= stock + " + obj.getQty() + " where name='" + obj.getName() + "'");
            }
            if (bool > 0) {
                flag = true;
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }

    private boolean table_invalid() {
        boolean flag = true;
        int count = 0;
        for (objtra obj : tblData) {
            count++;
            if (obj.getId().equals("") || obj.getName().equals("") || obj.getQty().equals("") ) {
                if (obj.getId().equals("") && obj.getName().equals("") && obj.getQty().equals("") && count != 1) {
                    flag = false;
                } else {
                    return true;
                }
            } else {
                if (count == tblData.size()) {
                    return false;
                }
            }
        }
        return flag;
    }
    
    private void remove_row() {
        int row = tblview.getSelectionModel().getSelectedIndex();
        if (tblData.size() > 0) {
            tblData.remove(row);
            tblview.getSelectionModel().clearSelection();
        } else {
            tblData.set(row, new objtra("", "", ""));
            tblview.getSelectionModel().clearSelection();
        }
    }
    
    private void lisnterTableClick() {
        tblview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2 && !tblview.getSelectionModel().isEmpty()) {
                    fetch(tblview.getSelectionModel().getSelectedItem());
                }
            }

        });

    }
    private void fetch(objtra obj) {
        sbxname.setText(obj.getName());
        txtcode.setText(obj.getId());
        txtqty.setText(obj.getQty());
        remove_row();
        
    }
    
    @FXML
    private void save_onAction(ActionEvent event) throws SQLException {
        if (table_invalid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                sbxname.requestFocus();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save this Form", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                save();
            }
        }
    }

    @FXML
    private void clear_onaction(ActionEvent event) {
        clearall();
    }

    @FXML
    private void remove_onaction(ActionEvent event) {
        if (tblview.getSelectionModel().getSelectedItem() != null) {
            remove_row();
        }
    }

    public void setStage(Stage stage_add_tra) {
       this.stage = stage_add_tra;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
