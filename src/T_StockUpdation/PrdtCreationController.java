/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_StockUpdation;

import com.miw.control.sbox.SuggessionBox;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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
public class PrdtCreationController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCode;
    @FXML
    private HBox hbx_Cat;
    @FXML
    private HBox hbxSubcat;
    @FXML
    private HBox hbxBrand;
    @FXML
    private TextField txtHsn;
    @FXML
    private HBox hbxUom;
    @FXML
    private HBox hbxGst;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtRate;
    @FXML
    private TextField txtMrp;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClear;
    public SuggessionBox sbxcat;
    public SuggessionBox sbxsubcat;
    public SuggessionBox sbxbrand;
    public SuggessionBox sbxUom;
    public SuggessionBox sbxgst;
    ObservableList<String> listcat = FXCollections.observableArrayList();
    ObservableList<String> listsubcat = FXCollections.observableArrayList();
    ObservableList<String> listbrand = FXCollections.observableArrayList();
    ObservableList<String> listuom = FXCollections.observableArrayList();
    ObservableList<String> listgst = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private TextField txtbatch;
    @FXML
    private TextField txtbarcode;
    @FXML
    private DatePicker dpexpiry;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Color x21;
    @FXML
    private Font x11;
    @FXML
    private TextField txtqty;
    @FXML
    private Color x211;
    @FXML
    private Font x111;
    @FXML
    private TextField txtamazon;
    @FXML
    private TextField txtflipkart;
    @FXML
    private TextField txtshop;
    @FXML
    private TextField txtother;
    @FXML
    private CheckBox chkexp;
    @FXML
    private Label lblexp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Setcode();
        setSbox();
        clear();
        listner_sbxCat();
        listner_sbxSubcat();
        listner_sbxUom();
        listner_sbxbrand();
        listner_sbxgst();
        listner_txtbx();     
    }   
    private void listner_txtbx(){
        txtName.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxcat.requestFocus();
                }
            }
        });
        
        txtHsn.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtqty.requestFocus();
                }
            }
        });
        txtqty.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxUom.requestFocus();
                }
            }
        });
        txtamazon.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtflipkart.requestFocus();
                }
            }
        });
        txtflipkart.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtshop.requestFocus();
                }
            }
        });
        txtshop.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtother.requestFocus();
                }
            }
        });
        txtother.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtRate.requestFocus();
                }
            }
        });
        txtRate.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtMrp.requestFocus();
                }
            }
        });
        
        txtMrp.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxgst.requestFocus();
                }
            }
        });
        
        txtRate.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtRate.setText(oldValue);
            }
        }
    });
        
        txtMrp.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtMrp.setText(oldValue);
            }
        }
    });
        
        txtStock.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtStock.setText(oldValue);
            }
        }
    });
        chkexp.selectedProperty().addListener(new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (chkexp.isSelected()) {
            dpexpiry.setVisible(true);
            lblexp.setVisible(true);
        }else
        {
            dpexpiry.setVisible(false);
            lblexp.setVisible(false);
        }
    }
});
    }
    
    private void clear(){
        chkexp.setSelected(false);
        dpexpiry.setVisible(false);
        lblexp.setVisible(false);
        txtName.clear();
        sbxcat.clear();
        sbxsubcat.clear();
        sbxbrand.clear();
        txtHsn.clear();
        sbxUom.clear();
        sbxgst.clear();
        txtStock.setText("0.0");
        txtqty.clear();
        txtRate.setText("0.0");
        txtMrp.setText("0.0");
        txtamazon.clear();
        txtflipkart.clear();
        txtshop.clear();
        txtother.clear();
        dpexpiry.setValue(LocalDate.now());
        Setcode();
    }
    
    private void setSbox(){
        chkexp.setSelected(false);
        dpexpiry.setVisible(false);
        lblexp.setVisible(false);
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        sbxcat = new SuggessionBox();
        sbxcat.setPrefSize(160, 25);
        hbx_Cat.getChildren().add(sbxcat);
        sbxsubcat = new SuggessionBox();
        sbxsubcat.setPrefSize(160, 25);
        hbxSubcat.getChildren().add(sbxsubcat);
        sbxbrand = new SuggessionBox();
        sbxbrand.setPrefSize(160, 25);
        hbxBrand.getChildren().add(sbxbrand);
        sbxUom = new SuggessionBox();
        sbxUom.setPrefSize(160, 25);
        hbxUom.getChildren().add(sbxUom);
        sbxgst = new SuggessionBox();
        sbxgst.setPrefSize(160, 25);
        hbxGst.getChildren().add(sbxgst);
    }
    
    private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtCode.setText("LMW-"+no);
        txtName.requestFocus();
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_item");
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
            Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void listner_sbxCat() {
        sbxcat.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listcat.removeAll(listcat);
                    try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_catgry where status = 1 ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_catgry where status = 1 ");
                        while (rs.next()) {
                            listcat.add(rs.getString("name"));
                        }
                        sbxcat.setData(listcat);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxcat.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxsubcat.requestFocus();
                }
            }
        });
    }

    private void listner_sbxSubcat() {
        sbxsubcat.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listsubcat.removeAll(listsubcat);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_subcatgry where status = 1");
                        while (rs.next()) {
                            listsubcat.add(rs.getString("name"));
                        }
                        sbxsubcat.setData(listsubcat);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxsubcat.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxbrand.requestFocus();
                }
            }
        });
         txtamazon.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            if (!newValue.matches("\\d*")) {
            txtamazon.setText(newValue.toUpperCase());
//            }
          }
        });
          txtflipkart.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            if (!newValue.matches("\\d*")) {
            txtflipkart.setText(newValue.toUpperCase());
//            }
          }
        });
           txtshop.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            if (!newValue.matches("\\d*")) {
            txtshop.setText(newValue.toUpperCase());
//            }
          }
        });
            txtother.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            txtother.setText(newValue.toUpperCase());
          }
        });
    }

    private void listner_sbxUom() {
        sbxUom.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listuom.removeAll(listuom);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select shcode from " + db.schema + ".tbl_unit where status = 1");
                        while (rs.next()) {
                            listuom.add(rs.getString("shcode"));
                        }
                        sbxUom.setData(listuom);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxUom.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtamazon.requestFocus();
                }
            }
        });
    }

    private void listner_sbxgst() {
        sbxgst.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listgst.removeAll(listgst);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_tax where status = 1");
                        while (rs.next()) {
                            listgst.add(rs.getString("name"));
                        }
                        sbxgst.setData(listgst);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxgst.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtStock.requestFocus();
                }
            }
        });
    }

    private void listner_sbxbrand() {
        sbxbrand.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listbrand.removeAll(listbrand);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_brand where status = 1");
                        while (rs.next()) {
                            listbrand.add(rs.getString("name"));
                        }
                        sbxbrand.setData(listbrand);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxbrand.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtHsn.requestFocus();
                }
            }
        });
    }
    
    private boolean save() {
        boolean flag = false;
            try {
                String sgst="(select sgst from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
                String cgst="(select cgst from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
                String igst="(select igst from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
                String gst="(select total from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
                Statement st = db.con.createStatement();
                Statement st1 = db.con.createStatement();
                Statement st2 = db.con.createStatement();
                int chk = 0;
                if (chkexp.isSelected()){
                    chk = 1;                    
                }else{
                   chk = 0;
                }
                System.out.println("insert into " + db.schema + ".tbl_item values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ sbxcat.getText()+"','"+ sbxsubcat.getText()+"','"+ sbxbrand.getText()+"','"+ txtHsn.getText()+"','"+ txtqty.getText()+"','"+ sbxUom.getText()+"'," + gst + ","+ sgst+","+ cgst+","+ igst+"," + txtRate.getText() + "," + txtMrp.getText() + ",'','',"+chk+",'"+dpexpiry.getValue()+"',"+txtStock.getText()+",'"+txtamazon.getText()+"','"+txtflipkart.getText()+"','"+txtshop.getText()+"','"+txtother.getText()+"',1)");
                String query="insert into " + db.schema + ".tbl_item values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ sbxcat.getText()+"','"+ sbxsubcat.getText()+"','"+ sbxbrand.getText()+"','"+ txtHsn.getText()+"','"+ txtqty.getText()+"','"+ sbxUom.getText()+"'," + gst + ","+ sgst+","+ cgst+","+ igst+"," + txtRate.getText() + "," + txtMrp.getText() + ",'','',"+chk+",'"+dpexpiry.getValue()+"',"+txtStock.getText()+",'"+txtamazon.getText()+"','"+txtflipkart.getText()+"','"+txtshop.getText()+"','"+txtother.getText()+"',1)";
                String querytn="insert into " + db.schema + ".tbl_item_tn values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ sbxcat.getText()+"','"+ sbxsubcat.getText()+"','"+ sbxbrand.getText()+"','"+ txtHsn.getText()+"','"+ txtqty.getText()+"','"+ sbxUom.getText()+"'," + gst + ","+ sgst+","+ cgst+","+ igst+"," + txtRate.getText() + "," + txtMrp.getText() + ",'','',"+chk+",'"+dpexpiry.getValue()+"',0.0,'"+txtamazon.getText()+"','"+txtflipkart.getText()+"','"+txtshop.getText()+"','"+txtother.getText()+"',1)";
                String querymh="insert into " + db.schema + ".tbl_item_mh values(null,'"+ txtCode.getText()+"','"+txtName.getText().toUpperCase().trim()+"','"+ sbxcat.getText()+"','"+ sbxsubcat.getText()+"','"+ sbxbrand.getText()+"','"+ txtHsn.getText()+"','"+ txtqty.getText()+"','"+ sbxUom.getText()+"'," + gst + ","+ sgst+","+ cgst+","+ igst+"," + txtRate.getText() + "," + txtMrp.getText() + ",'','',"+chk+",'"+dpexpiry.getValue()+"',0.0,'"+txtamazon.getText()+"','"+txtflipkart.getText()+"','"+txtshop.getText()+"','"+txtother.getText()+"',1)";
                System.out.println(""+query);
                System.out.println(""+querytn);
                System.out.println(""+querymh);
                int bool;
                System.out.println("before for loop");
                for(bool=1;bool<=4;bool++){
                    System.out.println("inside for loop");
                    switch (bool) {
                        case 1:
                            System.out.println("switch case1");
                            st.executeUpdate(query);
                            st.close();
                            break;
                        case 2:
                             System.out.println("switch case2");
                            st1.executeUpdate(querytn);
                            st1.close();
                            break;
                        case 3:
                             System.out.println("switch case3");
                                st2.executeUpdate(querymh);
                            st2.close();
                            break;
                        default:
                            break;
                    }
                    if (bool > 3) { 
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item Saved Successfully!", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    StockMasterController smc= new StockMasterController();
                    smc.populate_table();
                    }
                    flag = true;
                }
                }
                 clear();
            } catch (SQLException ex) {
                   if (ex.getMessage().equals("Duplicate entry '"+txtName.getText().toUpperCase()+"' for key 'Name_unique'")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Item "+txtName.getText()+" Already Exist", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    txtName.requestFocus();
                    }
                
            }
            else {
                System.out.println(ex.getMessage());
            }
                Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
            }      
        return flag;
    }
   
    private void updatedb() {        
        try {
            Statement st = db.con.createStatement();
            Statement st1 = db.con.createStatement();
            Statement st2 = db.con.createStatement();
            String sgst="(select sgst from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
            String cgst="(select cgst from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
            String igst="(select igst from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')"; 
            String gst="(select total from " + db.schema + ".tbl_tax where name = '" + sbxgst.getText() + "')";
              int chk = 0;
                if (chkexp.isSelected()){
                    chk = 1;                    
                }else{
                   chk = 0;
                }
            System.out.println("update " + db.schema + ".tbl_item set name= '" + txtName.getText().toUpperCase().trim() + "', cat= '" + sbxcat.getText()+ "', subcat= '" + sbxsubcat.getText()+ "', brand= '" + sbxbrand.getText()+ "', hsn= '" + txtHsn.getText()+ "',qty="+txtqty.getText()+", unit= '" + sbxUom.getText()+ "', gst= " + gst+ ", sgst= " + sgst + ", cgst= " + cgst+ ", igst= " + igst+ ", rate= " + txtRate.getText()+ ", mrp= " + txtMrp.getText()+ ",expcheck="+chk+", expdate= '" +dpexpiry.getValue() + "',stock="+txtStock.getText()+" where code = '" + txtCode.getText()+"'");
            String query = "update " + db.schema + ".tbl_item set name= '" + txtName.getText().toUpperCase().trim() + "', cat= '" + sbxcat.getText()+ "', subcat= '" + sbxsubcat.getText()+ "', brand= '" + sbxbrand.getText()+ "', hsn= '" + txtHsn.getText()+ "',qty="+txtqty.getText()+", unit= '" + sbxUom.getText()+ "', gst= " + gst+ ", sgst= " + sgst + ", cgst= " + cgst+ ", igst= " + igst+ ", rate= '" + txtRate.getText()+ "', mrp= '" + txtMrp.getText()+ "',expcheck="+chk+", expdate= '" +dpexpiry.getValue() + "',stock="+txtStock.getText()+",amazon='"+txtamazon.getText()+"',flipkart='"+txtflipkart.getText()+"',shop='"+txtshop.getText()+"',others='"+txtother.getText()+"' where code = '" + txtCode.getText() +"'";
            String querytn = "update " + db.schema + ".tbl_item_tn set name= '" + txtName.getText().toUpperCase().trim() + "', cat= '" + sbxcat.getText()+ "', subcat= '" + sbxsubcat.getText()+ "', brand= '" + sbxbrand.getText()+ "', hsn= '" + txtHsn.getText()+ "',qty="+txtqty.getText()+", unit= '" + sbxUom.getText()+ "', gst= " + gst+ ", sgst= " + sgst + ", cgst= " + cgst+ ", igst= " + igst+ ", rate= '" + txtRate.getText()+ "', mrp= '" + txtMrp.getText()+ "',expcheck="+chk+", expdate= '" +dpexpiry.getValue() + "',amazon='"+txtamazon.getText()+"',flipkart='"+txtflipkart.getText()+"',shop='"+txtshop.getText()+"',others='"+txtother.getText()+"' where code = '" + txtCode.getText() +"'";
            String querymh = "update " + db.schema + ".tbl_item_mh set name= '" + txtName.getText().toUpperCase().trim() + "', cat= '" + sbxcat.getText()+ "', subcat= '" + sbxsubcat.getText()+ "', brand= '" + sbxbrand.getText()+ "', hsn= '" + txtHsn.getText()+ "',qty="+txtqty.getText()+", unit= '" + sbxUom.getText()+ "', gst= " + gst+ ", sgst= " + sgst + ", cgst= " + cgst+ ", igst= " + igst+ ", rate= '" + txtRate.getText()+ "', mrp= '" + txtMrp.getText()+ "',expcheck="+chk+", expdate= '" +dpexpiry.getValue() + "',amazon='"+txtamazon.getText()+"',flipkart='"+txtflipkart.getText()+"',shop='"+txtshop.getText()+"',others='"+txtother.getText()+"' where code = '" + txtCode.getText() +"'";        
            int bool =0;
              for(bool=1;bool<=4;bool++){
                    System.out.println("inside for loop");
                    switch (bool) {
                        case 1:
                            System.out.println("switch case1");
                            st.executeUpdate(query); 
                            st.close();
                            break;
                        case 2:
                             System.out.println("switch case2");
                            st1.executeUpdate(querytn);
                            st1.close();
                            break;
                        case 3:
                             System.out.println("switch case3");
                                st2.executeUpdate(querymh);
                            st2.close();
                            break;
                        default:
                            break;
                    }
            if (bool > 3) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully!", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    StockMasterController smc= new StockMasterController();
                    smc.populate_table();
                    }
                clear();
                btnSave.setVisible(true);
                btnUpdate.setVisible(false);
               stage.close();
            }
              }
            } catch (SQLException ex) {
                if (ex.getMessage().equals("Duplicate entry '"+txtName.getText()+"' for key 'Name_unique'")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Item "+txtName.getText()+" Already Exist", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtName.requestFocus();
                    }
                
            }
            else {
                System.out.println(ex.getMessage());
            }
//                Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
           }
          
    }
    
    
    @FXML
    private void Save_Onaction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to Save this Item!", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    save();
                    }
    }

    @FXML
    private void update_onAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to update this Item!", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    updatedb();
                    }
   
    }

    @FXML
    private void clear_onAction(ActionEvent event) {
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
                  ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_item where code ='"+id+"'");
                  if (rs.next()) {
                        txtCode.setText(id);
                        txtName.setText(rs.getString("name"));
                        sbxcat.setText(rs.getString("cat"));
                        sbxsubcat.setText(rs.getString("subcat"));
                        sbxbrand.setText(rs.getString("brand"));
                        txtHsn.setText(rs.getString("hsn"));
                        txtamazon.setText(rs.getString("amazon"));
                        txtflipkart.setText(rs.getString("flipkart"));
                        txtshop.setText(rs.getString("shop"));
                        txtother.setText(rs.getString("others"));
                       sbxUom.setText(rs.getString("unit"));
                        String sgst= rs.getString("sgst");
                        String cgst= rs.getString("cgst");
                        String igst= rs.getString("igst");
                        fetch_tax(sgst, cgst, igst);
                        txtStock.setText(rs.getString("stock"));
                        txtqty.setText(rs.getString("qty"));
                        txtRate.setText(rs.getString("rate"));
                        txtMrp.setText(rs.getString("mrp"));
                        txtbarcode.setText(rs.getString("barcode"));
                        txtbatch.setText(rs.getString("batch"));
                        int chk = rs.getInt("expcheck");
                        if(chk == 1){
                            chkexp.setSelected(true);
                        }else if(chk == 0){
                            chkexp.setSelected(false);
                        }
                        dpexpiry.setValue(rs.getDate("expdate").toLocalDate());                   
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

   private void fetch_tax(String sgst,String cgst,String igst){
        try {
                  Statement st = db.con.createStatement();
                  System.out.println("select name from " + db.schema + ".tbl_tax where sgst ="+sgst+" and cgst ="+cgst+" and igst="+igst+" ");
                  ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_tax where sgst ="+sgst+" and cgst ="+cgst+" and igst="+igst+" ");
                  if (rs.next()) {
                      sbxgst.setText(rs.getString("name"));
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(PrdtCreationController.class.getName()).log(Level.SEVERE, null, ex);
            }
   }
   
    public void setStage(Stage stage_add_item) {
        this.stage = stage_add_item;
        this.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    t.consume();
                    setFadeOutTransition();
                    stage.close();
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
                if (t.getCode() == KeyCode.F10) {
                    t.consume();
                   btnSave.fire();
                }
            }
        });        
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F11) {
                    t.consume();
                   btnUpdate.fire();
                }
            }
        });          
        
    }
}
