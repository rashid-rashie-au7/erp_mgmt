/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;


import PrintersThermal.ThermalPrinterPageSetup;
import com.miw.control.sbox.SuggessionBox;
import database.DBMySQL;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import com.mysql.jdbc.PreparedStatement;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class Create_SalesController implements Initializable {
     private Stage stage = new Stage(StageStyle.UTILITY);
      private Stage stagepayment = new Stage(StageStyle.UTILITY);
    @FXML
    private TextField txtbillno;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtid;
    @FXML
    private HBox hbxName;
    @FXML
    private TextField txtmob;
    @FXML
    private TextField txtplace;
    @FXML
    private TextField txtdist;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    public TableView<objsales> tblview;
    @FXML
    private TableColumn colbar;
    @FXML
    private TableColumn colcode;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn colrate;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colgstamt;
    @FXML
    private TableColumn coltot;
    @FXML
    private AnchorPane anchrtot;
    @FXML
    public Label lblqty;
    @FXML
    public  Label lblitems;
    @FXML
    public TextField txtgrand;
    @FXML
    public TextField txtgstamt;
    @FXML
    public TextField txtdisc;
    @FXML
    public TextField txtnetamt;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
   
    @FXML
    private Hyperlink hpl_remove;
    @FXML
    private AnchorPane anchrtot1;
    @FXML
    private TextField txtsgst;
    @FXML
    private TextField txtcgst;
    @FXML
    private TextField txtigst;
    public SuggessionBox sbxclient;
    ObservableList<String> listclient = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    public ObservableList<objsales> table_data = FXCollections.observableArrayList(objsales ->
    new Observable[]{
        objsales.barcodeProperty(),objsales.codeProperty(),
        objsales.nameProperty(),objsales.rateProperty(),
        objsales.qtyProperty(),objsales.gstProperty(),
        objsales.gstamtProperty(),objsales.totProperty()}
    );
    @FXML
    private Label lblBillNo;   
    @FXML
    private RadioButton rbtsgst;
    @FXML
    private RadioButton rbtigst;
    @FXML
    private Button btnPrint;
    @FXML
    private TextField txtgstin;
    private boolean changeDetected = false;
    @FXML
    private RadioButton rbtmale;
    @FXML
    private RadioButton rbtfemale;
    public String cardType = "";
    public String cardHoldersName = "";
    public String cardNumber = "";
    public String balamt = "";
    public String rcamt = "";
    public String paymentOption = "";
    @FXML
    private TextField txtdiscper;
    @FXML
    private TextField txtship;
    @FXML
    public ComboBox<String> cmbagent;
    private ObservableList<String> listagent = FXCollections.observableArrayList();
    @FXML
    private TextField txtstate;
    @FXML
    private ComboBox<String> cmbwh;
    private ObservableList<String> listwh = FXCollections.observableArrayList();
    @FXML
    private Hyperlink hplImport;
    @FXML
    private TextField txtKfc;
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Setcode();
        initClock(); 
        clear();
        setSbox();
        txtbxListner();
        populateCombo_agent();
        populateCombo_wh();
        set_table();
        tbllistner();
        listner_sbxClient();
        listner_radio_selection();
    }  
    
    private void setSbox(){
        sbxclient = new SuggessionBox();
        hbxName.getChildren().add(sbxclient); 
        sbxclient.setPrefSize(175, 25);
        cmbagent.getSelectionModel().select("LOOMS & WEAVES");
        cmbwh.getSelectionModel().select("KERALA");
        btnPrint.setVisible(false);
    }
    
     private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        txtbillno.setText(pr.toString());
        txtid.requestFocus();
    }
     
     private void populateCombo_agent(){
        listagent.clear();
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_agent ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_agent ");
                        while (rs.next()) {
                            listagent.add(rs.getString("name"));
                        }
                        cmbagent.setItems(listagent);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
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
                        Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select count(billno), max(billno) FROM " + db.schema + ".tbl_bill");
            ResultSet rs = st.executeQuery("select count(billno), max(billno) FROM " + db.schema + ".tbl_bill");
            if (rs.next()) {
                id = rs.getString(1);
                int count = rs.getInt(1);
                if (count == 0) {
                    id = "0";
                } else {
                    id = rs.getString(2);
                }
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    private void listner_sbxClient() {
        sbxclient.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listclient.removeAll(listclient);
                    try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_client where status = 1");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_client where status = 1");
                        while (rs.next()) {
                            listclient.add(rs.getString("name"));
                        }
                        sbxclient.setData(listclient);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        sbxclient.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   setvalue();
                   tblview.requestFocus();
                   tblview.edit(table_data.size() - 1, colbar);
                }
            }
        });
    }
    
     private void txtbxListner(){
//      txtid.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.ENTER) {
//                    
//                    tblview.requestFocus();
//                   tblview.edit(table_data.size() - 1, colbar);
//                }
//                if (t.getCode() == KeyCode.DOWN) {
//                  sbxclient.requestFocus();
//                }
//            }
//        });  
           txtmob.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            txtmob.setText(newValue.replaceAll("[^\\d]", ""));
            }
          }
        });
      
          }
    
     private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select code,gender,mob,place,gst,dis,zip,state from " + db.schema + ".tbl_client where name = '" + sbxclient.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select code,gender,mob,place,gst,dis,zip,state from " + db.schema + ".tbl_client where name = '" + sbxclient.getText() + "' ");
                while (rst.next()) {
                    txtid.setText(rst.getString("code"));
                    txtmob.setText(rst.getString("mob"));
                    txtplace.setText(rst.getString("place"));
                    txtgstin.setText(rst.getString("gst"));
                    txtdist.setText(rst.getString("dis"));
                    if(rst.getString("gender").equals("Male")){
                        rbtmale.setSelected(true);
                    }else if(rst.getString("gender").equals("Female")){
                        rbtfemale.setSelected(true);
                    }
                    txtstate.setText(rst.getString("state"));
                }
               }
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clear(){
      Setcode(); 
      dpDate.setValue(LocalDate.now());
      initClock();
      txtid.clear();
      txtmob.setText("");
      
      txtplace.clear();
      txtgstin.clear();
      txtdist.clear();
      rbtmale.setSelected(true);
      rbtsgst.setSelected(true);
      txtstate.clear();
      txtsgst.setText("0.00");
      lblitems.setText("0");
      lblqty.setText("0");
      txtcgst.setText("0.00");
      txtigst.setText("0.00");
      txtgrand.setText("0.00");
      txtgstamt.setText("0.00");
      txtdisc.setText("0.00");
      txtdiscper.setText("0.00");
      txtnetamt.setText("0.00");
      txtship.setText("0.00");
      txtKfc.setText("0.00");
      cmbagent.setItems(listagent);
      cmbagent.getSelectionModel().select("LOOMS & WEAVES");
      cmbwh.getSelectionModel().select("KERALA");
      table_data.clear();
      table_data.add(new objsales("", "", "", "", "", "","",""));
    }
    
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
       txtTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    private void set_table() {
        Callback<TableColumn, TableCell> txtbarcod = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new txtbarcode();
            }
        };
        colbar.setCellValueFactory(new PropertyValueFactory<objsales, String>("barcode"));
        colbar.setCellFactory(txtbarcod);
        colbar.setEditable(true);
        
        Callback<TableColumn, TableCell> sboxcode = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new sbxcode();
            }
        };
        colcode.setCellValueFactory(new PropertyValueFactory<objsales, String>("code"));
        colcode.setCellFactory(sboxcode);
        colcode.setEditable(true);
        
        Callback<TableColumn, TableCell> sboxname = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new sbxname();
            }
        };
        colname.setCellValueFactory(new PropertyValueFactory<objsales, String>("name"));
        colname.setCellFactory(sboxname);
        colname.setEditable(true);
        
        Callback<TableColumn, TableCell> txtRate = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new txtrate();
            }
        };
        colrate.setCellValueFactory(new PropertyValueFactory<objsales, String>("rate"));
        colrate.setCellFactory(txtRate);
        colrate.setEditable(true);
        
        Callback<TableColumn, TableCell> txtQty = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new txtqty();
            }
        };
        colqty.setCellValueFactory(new PropertyValueFactory<objsales, String>("qty"));
        colqty.setCellFactory(txtQty);
        colqty.setEditable(true);
        
         Callback<TableColumn, TableCell> txtGst = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new txtgst();
            }
        };
        colgst.setCellValueFactory(new PropertyValueFactory<objsales, String>("gst"));
        colgst.setCellFactory(txtGst);
        colgst.setEditable(true);
              
        colgstamt.setCellValueFactory(new PropertyValueFactory<objsales, String>("gstamt"));
        coltot.setCellValueFactory(new PropertyValueFactory<objsales, String>("tot"));
        tblview.setEditable(true);
        table_data.add(new objsales("", "", "", "", "", "","",""));
        tblview.setItems(table_data);
        tblview.getSelectionModel().setCellSelectionEnabled(true);
    }

    private void client(){
            if (!clientExist(sbxclient.getText())) {
                    String gender="";
                    String type="";
                    if(rbtmale.isSelected()){
                        gender = "Male";
                    }else if(rbtfemale.isSelected()){
                        gender = "Female";
                    }
                   type= cmbagent.getSelectionModel().getSelectedItem();
                            saveClient(sbxclient.getText(),gender, txtmob.getText(), txtgstin.getText(), txtplace.getText(), txtdist.getText(),type);
                        } 
            else{
                tblview.edit(table_data.size() - 1, colbar);
            }
    }
    
    private void tbllistner(){    
        txtdisc.textProperty().addListener(new ChangeListener<String>() {
        @Override
       public void changed(ObservableValue<? extends String> ov, String t, String t1) {
            try {
                    if (t1.endsWith("f") || t1.endsWith("d") || t1.endsWith("F") || t1.endsWith("D")) {
                        txtdisc.setText(t);
                    } else if (t1.equals("")) {
                        txtdisc.setText("");
                        t1 = "0.0";
                    }
                    Double.parseDouble(t1);
                    if (new Double(t1) == new Double(txtgrand.getText())) {
                        txtdisc.setText(t);
                    }
                } catch (NumberFormatException e) {
                    txtdisc.setText(t);
                }
            if(!t1.equals("0") || !t1.equals("")){
                    Double netamt= Double.parseDouble(txtgrand.getText()) + Double.parseDouble(txtgstamt.getText()) ;
                    Double disc = Double.parseDouble(txtdisc.getText());
                    Double sum = netamt - disc;
                    txtnetamt.setText(String.valueOf(sum));       
            } else{
               Double grand= Double.parseDouble(txtgrand.getText());
                    Double gst = Double.parseDouble(txtgstamt.getText());
                    Double sum = grand + gst; 
                    txtnetamt.setText(String.valueOf(sum));
            }
        }
    });
        
        txtdiscper.textProperty().addListener(new ChangeListener<String>() {
        @Override
          public void changed(ObservableValue<? extends String> ov, String t, String t1) {
            try {
                    if (t1.endsWith("f") || t1.endsWith("d") || t1.endsWith("F") || t1.endsWith("D")) {
                        txtdiscper.setText(t);
                    } else if (t1.equals("")) {
                        txtdiscper.setText("");
                        t1 = "0.0";
                    }
                    Double.parseDouble(t1);
                    if (new Double(t1) == new Double(txtgrand.getText())) {
                        txtdiscper.setText(t);
                    }
                } catch (NumberFormatException e) {
                    txtdiscper.setText(t);
                }
            if(t1.equals("0") || t1.equals("")){
                 Double grand= Double.parseDouble(txtgrand.getText());
                    Double gst = Double.parseDouble(txtgstamt.getText());
                    Double ship = Double.parseDouble(txtship.getText());
                    Double kfc = Double.parseDouble(txtKfc.getText());
                    Double sum = grand + gst+ship+kfc; 
                    txtnetamt.setText(String.valueOf(sum));    
            } else{
                    Double netamt= Double.parseDouble(txtgrand.getText()) + Double.parseDouble(txtgstamt.getText()) +Double.parseDouble(txtship.getText()) +Double.parseDouble(txtKfc.getText());
                    Double disper = Double.parseDouble(txtdiscper.getText());
                    Double dis = netamt * disper/100;
                    txtdisc.setText(String.valueOf(dis));
                    Double sum = netamt - dis;
                    txtnetamt.setText(String.valueOf(sum));   
            }
               
            
        }
    });
       
         txtid.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    setvalue();
//                    tblview.edit(table_data.size() - 1, colbar);
                }
            }
        });
         txtship.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    Double netamt= Double.parseDouble(txtgrand.getText()) + Double.parseDouble(txtgstamt.getText()) ;
                    Double disper = Double.parseDouble(txtdiscper.getText());
                    Double ship = Double.parseDouble(txtship.getText());
                    Double dis = netamt * disper/100;
                    txtdisc.setText(String.valueOf(dis));
                    Double kfc = Double.parseDouble(txtKfc.getText());
                    Double sum = netamt - dis + ship+kfc;
                    txtnetamt.setText(String.valueOf(sum));   
                }
            }
        });
         
         txtgstin.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    client();
                    setvalue();
                }
            }
        });
         
        colgst.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    settextbox();
                    tblview.requestFocus();
                    tblview.edit(table_data.size() - 1, colbar);
                }
            }
    
        });
        table_data.addListener((Change<? extends objsales> c) -> {
           while (c.next()) {
               if (c.wasAdded()) {
                   System.out.println("Added:");
                   settextbox();
                   c.getAddedSubList().forEach(System.out::println);
                   
                   System.out.println();
               }
               if (c.wasRemoved()) {
                   System.out.println("Removed:");
                   c.getRemoved().forEach(System.out::println);
                   settextbox();
                   System.out.println();
               }
               if (c.wasUpdated()) {
                   System.out.println("Updated:");
                   table_data.subList(c.getFrom(), c.getTo()).forEach(System.out::println);
                   settextbox();
                   System.out.println();
               }
           }
        });
    }
    
     public void settextbox(){
          
          Double grand_tot = 0.0;
          Double gstamt=0.0;
          Double price = 0.0;
          Double quty= 0.0;
          Double gst=0.0;
          Double qtyy=0.0;
          Double qty=0.0;
          Double tot=0.0;
          Double netamt=0.0;
          Double sgst=0.0;
          Double igst=0.0;
          Double cgst=0.0;
          Integer items=0;
          Double kfc =0.0;
          if(tblview.getItems().size()>0){
            for (objsales obj : tblview.getItems()){
                price = Double.parseDouble(obj.getRate());
                System.out.println("column price===="+ price);
                quty= Double.parseDouble(obj.getQty());
                System.out.println("column qty===="+ quty);
                grand_tot += price * quty;
                System.out.println("column grand total===="+ grand_tot);
                gst= Double.parseDouble(obj.getGstamt());
                System.out.println("column gstamt===="+ gst);
                gstamt += gst;
                System.out.println("Colum totalgstamt===="+ gstamt);
                qtyy= Double.parseDouble(obj.getQty());
                qty += qtyy;
                System.out.println("column total qtyyyyyyyy"+  qty);
                tot= Double.parseDouble(obj.getTot());
                System.out.println("column netamt"+  tot);
                netamt += tot;
                System.out.println("column total netamt"+  netamt);
               
                 items =table_data.size();
                System.out.println("itemmmmms"+ items); 
                sgst = gstamt/2;
                cgst =gstamt/2;
            lblitems.setText(items.toString());
            lblqty.setText(qty.toString());
            BigDecimal grand_total = new BigDecimal(grand_tot).setScale(2, RoundingMode.HALF_EVEN);
            txtgrand.setText(grand_total.toString());
            BigDecimal gstamtt = new BigDecimal(gstamt).setScale(2, RoundingMode.HALF_EVEN);
            txtgstamt.setText(gstamtt.toString());
            
            BigDecimal sgstamt = new BigDecimal(sgst).setScale(2, RoundingMode.HALF_EVEN);
            txtsgst.setText(sgstamt.toString());
            BigDecimal cgstamt = new BigDecimal(cgst).setScale(2, RoundingMode.HALF_EVEN);
            txtcgst.setText(cgstamt.toString());
            kfc = Double.parseDouble(txtgrand.getText()) * 1 /100;
                System.out.println("kfc===  "+kfc);
            txtKfc.setText(kfc.toString());
                if (txtgstin.getText().equals("")) {
                  Double netttt = 0.0;
                  netttt=netamt + kfc;
                  txtnetamt.setText(netttt.toString());
                System.out.println("nettttttttttttttttttttttt  "+netttt);  
                }else if (!txtgstin.getText().equals("")) {
                    BigDecimal netamount = new BigDecimal(netamt).setScale(2, RoundingMode.HALF_EVEN);
                    txtnetamt.setText(netamount.toString());
                }
            
            
            }
          }
      }
    
     private void listner_radio_selection() {
        ToggleGroup grp =new ToggleGroup();
        rbtigst.setToggleGroup(grp);
        rbtsgst.setToggleGroup(grp);
        ToggleGroup grp2 =new ToggleGroup();
        rbtmale.setToggleGroup(grp2);
        rbtfemale.setToggleGroup(grp2);
        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                Double igst=0.0;
                Double sgst =0.0;
                Double cgst =0.0;
                Double gst =Double.parseDouble(txtgstamt.getText());
                 if(rbtigst.isSelected()){
                        igst= gst;
                        txtigst.setText(igst.toString());
                        txtsgst.setText("0.0");
                        txtcgst.setText("0.0");
                    }else if(rbtsgst.isSelected()){
                        sgst= gst/2;
                        cgst = gst/2;
                        txtigst.setText("0.0");
                        txtsgst.setText(sgst.toString());
                        txtcgst.setText(cgst.toString()); 
                    }
            }
        });
         cmbwh.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {
            if(cmbwh.getSelectionModel().getSelectedItem().equals("KERALA")){
                rbtsgst.setSelected(true);
            }else
            {
                rbtigst.setSelected(true);
            }
        }    
    });
        
    }
     
      public void fetch_for_update(String id) {
        btnSave.setVisible(false);
        btnPrint.setVisible(true);
            try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_bill where billno = '" + id + "'");
            if (rs.next()) {
                txtbillno.setText(id);
                dpDate.setValue(rs.getDate("date").toLocalDate());
                txtTime.setText(rs.getString("time"));
                sbxclient.setText(rs.getString("client"));               
                txtgrand.setText(rs.getString("grand"));
                txtdisc.setText(rs.getString("discount"));
                txtgstamt.setText(rs.getString("gst"));
                txtnetamt.setText(rs.getString("netamt"));
                txtsgst.setText(rs.getString("sgst"));
                txtship.setText(rs.getString("ship"));
                txtcgst.setText(rs.getString("cgst"));
                txtigst.setText(rs.getString("igst"));
                lblitems.setText(rs.getString("items"));
                lblqty.setText(rs.getString("qty"));
                txtKfc.setText(rs.getString("kfc"));
                cmbagent.getSelectionModel().select(rs.getString("agent"));
                cmbwh.getSelectionModel().select(rs.getString("wh"));
                
                setvalue();
            }
            
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_data.removeAll(table_data);
        try {
            Statement st1 = db.con.createStatement();
            System.out.println("select *from " + db.schema + ".tbl_bill_items  where  billno = '" + id + "'");
            ResultSet rs = st1.executeQuery("select *from " + db.schema + ".tbl_bill_items  where  billno = '" + id + "'");
            while (rs.next()) {
                table_data.add(new objsales(rs.getString("barcode"), rs.getString("code"), rs.getString("name"), rs.getString("rate"), rs.getString("qty"), rs.getString("gst"), rs.getString("gstamt"), rs.getString("netamt")));
            }
            st1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      
    
     private void remove_row() {
        System.out.println("inside remove");
        int row = tblview.getSelectionModel().getSelectedIndex();
        if (table_data.size() > 1) {
            System.out.println("rooowwww  " + row);
            table_data.remove(row);
            settextbox();
            tblview.getSelectionModel().clearSelection();
        } else {
            table_data.set(row, new objsales("", "", "", "","","","",""));
            settextbox();
            tblview.getSelectionModel().clearSelection();
        }
    }
     
     private boolean table_invalid() {
        boolean flag = true;
        int count = 0;
        for (objsales obj : table_data) {
            count++;
            if (obj.getBarcode().equals("") || obj.getCode().equals("") || obj.getName().equals("") || obj.getRate().equals("")  || obj.getQty().equals("") || obj.getGst().equals("") || obj.getGstamt().equals("") || obj.getTot().equals("")) {
                if (obj.getBarcode().equals("") && obj.getCode().equals("") && obj.getName().equals("") && obj.getRate().equals("")  && obj.getQty().equals("") && obj.getGst().equals("") && obj.getGstamt().equals("") && obj.getTot().equals("") && count != 1) {
                    flag = false;
                } else {
                    return true;
                }
            } else {
                if (count == table_data.size()) {
                    return false;
                }
            }
        }
        return flag;
    }
     
     private boolean savebill() throws SQLException {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            String type="";

            
            System.out.println("insert into " + db.schema + ".tbl_bill values(null,'" + txtbillno.getText() + "','"+dpDate.getValue()+"','" + txtTime.getText() + "','"+cmbagent.getSelectionModel().getSelectedItem()+"','" + sbxclient.getText() + "',"+lblitems.getText()+","+lblqty.getText()+","+ txtgrand.getText() +","+txtsgst.getText()+","+ txtcgst.getText() +","+txtigst.getText()+","+ txtgstamt.getText() +","+txtdisc.getText()+","+ txtship.getText() +","+ txtnetamt.getText() +",'"+cmbwh.getSelectionModel().getSelectedItem()+"',00000,"+ txtKfc.getText() +",1 )");
            String query = "insert into " + db.schema + ".tbl_bill values(null,'" + txtbillno.getText() + "','"+dpDate.getValue()+"','" + txtTime.getText() + "','"+cmbagent.getSelectionModel().getSelectedItem()+"','" + sbxclient.getText() + "',"+lblitems.getText()+","+lblqty.getText()+","+ txtgrand.getText() +","+txtsgst.getText()+","+ txtcgst.getText() +","+txtigst.getText()+","+ txtgstamt.getText() +","+txtdisc.getText()+","+ txtship.getText() +","+ txtnetamt.getText() +",'"+cmbwh.getSelectionModel().getSelectedItem()+"',00000,"+ txtKfc.getText() +",1 )";
            int bool = st.executeUpdate(query);
            if (bool > 0) {
                save_billitems(txtbillno.getText());
                flag = true;
            }
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean save_billitems(String billno) {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objsales obj : table_data) {
                if (obj.getBarcode().equals("") && obj.getCode().equals("") && obj.getName().equals("") && obj.getRate().equals("")  && obj.getQty().equals("") && obj.getGst().equals("") && obj.getGstamt().equals("") && obj.getTot().equals(""))  {
                    continue;
                }
                Double tot=0.0;
                tot= Double.parseDouble(obj.getRate()) * Double.parseDouble(obj.getQty());
                String agent= cmbagent.getSelectionModel().getSelectedItem();
                String query="";
                if (agent.equals("LOOMS & WEAVES")) {
                   query = "(select code from " + db.schema + ".tbl_item where code = '"+obj.getCode()+"')";     
                }else if (agent.equals("AMAZON")) {
                    query= "(select amazon from " + db.schema + ".tbl_item where code = '"+obj.getCode()+"')";
                }else if (agent.equals("FLIPKART")) {
                    query= "(select flipkart from " + db.schema + ".tbl_item where code = '"+obj.getCode()+"')";
                }else if (agent.equals("SHOPCLUES")) {
                    query= "(select shop from " + db.schema + ".tbl_item where code = '"+obj.getCode()+"')";
                }
                System.out.println("insert into " + db.schema + ".tbl_bill_items values(null, '" + billno + "', '" + obj.getBarcode() + "', " + query+ ", '" +  obj.getName() + "', " + obj.getRate()+ "," +  obj.getQty()+ ", " + tot + "," +  obj.getGst()+ ", " + obj.getGstamt()+ "," +  obj.getTot()+ ", 1)");
                bool = st.executeUpdate("insert into " + db.schema + ".tbl_bill_items values(null, '" + billno + "', '" + obj.getBarcode() + "', " +query+ ", '" +  obj.getName() + "', " + obj.getRate()+ "," +  obj.getQty()+ ", " + tot + "," +  obj.getGst()+ ", " + obj.getGstamt()+ "," +  obj.getTot()+ ", 1)");
            }
            if (bool > 0) {
                updateStock();
                
                flag = true;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bill Sucessfully Saved ", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    clear();
                }
                
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    

    @FXML
    private void btnsave_onAction(ActionEvent event) throws SQLException {
        if (table_invalid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                tblview.requestFocus();
            }
        } else {
                try {
                    if (!stagepayment.isShowing()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Denomination.fxml"));
                        Parent root = (Parent) loader.load();
                        DenominationController dc = loader.getController();
                        Scene scene = new Scene(root);
                        stagepayment.setScene(scene);
                        stagepayment.setResizable(false);
                        stagepayment.setTitle("Payment Options");
                        dc.txtamt.setText(txtnetamt.getText());
                        dc.txtcardamt.setText(txtnetamt.getText());
                        dc.txtcrditamt.setText(txtnetamt.getText());
                        dc.lblbillno.setText(txtbillno.getText());
                        dc.lblclientid.setText(txtid.getText());
                        dc.lbldate.setText(dpDate.getValue().toString());
                        dc.setStage(stagepayment);
                        stagepayment.showAndWait();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (DenominationController.saveStatus.equalsIgnoreCase("save")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save this Bill", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (!clientExist(sbxclient.getText())) {
                    String gender="";
                    String type="";
                    if(rbtmale.isSelected()){
                        gender = "Male";
                    }else if(rbtfemale.isSelected()){
                        gender = "Female";
                    }
                            saveClient(sbxclient.getText(),gender, txtmob.getText(), txtgstin.getText(), txtplace.getText(), txtdist.getText(), type);
                        } 
                               if (savebill()) {    
                                System.err.println("Print working-------------->");
                                ThermalPrinterPageSetup tps = new ThermalPrinterPageSetup(new String (txtbillno.getText()));
                                if (tps.print()) {
//                                    table_data.clear();
                                    btnClear.fire();
                                    sbxclient.clear();
                                    SalesMasterController emc =new SalesMasterController();
                                    emc.populate_table();
                                    System.out.println("Print Successful");
                                   
                                } else {
                                    System.out.println("Print Not Successful");
                                }
                            
                        }
                
            }
        }
    }
    }

    private void saveClient(String clientName, String gender,String mob, String gstin, String place, String dist,String type) {
        String clientId = "";
        Integer pr = Integer.parseInt(setClientId()) + 1;
        String no = String.format("%04d", pr);
        clientId ="CUST"+no;
        try {
            Statement st = db.con.createStatement();
            System.out.println("insert into " + db.schema + ".tbl_client values(null, '" + clientId + "', '" + clientName.toUpperCase() + "','"+gender+"', '" + mob + "', '', '', '" + place + "','','','', '" + dist + "', '','','" + type + "', '" + gstin + "',  1)");
            int bool = st.executeUpdate("insert into " + db.schema + ".tbl_client values(null, '" + clientId + "', '" + clientName.toUpperCase() + "','"+gender+"', '" + mob + "', '', '', '" + place + "','','','', '" + dist + "', '"+txtstate.getText()+"','','" + type + "', '" + gstin + "',  1)");
            if (bool > 0) {
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean updateStock() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objsales obj : table_data) {
                if (obj.getBarcode().equals("") && obj.getCode().equals("") && obj.getName().equals("") && obj.getRate().equals("")  && obj.getQty().equals("") && obj.getGst().equals("") && obj.getGstamt().equals("") && obj.getTot().equals(""))  {
                    continue;
                }
                String wh = cmbwh.getSelectionModel().getSelectedItem();
                String tbl = "";
                if (wh.equals("TAMILNADU")) {
                  tbl= "tbl_item_tn"; 
                }else if (wh.equals("MAHARASHTRA")) {
                    tbl = "tbl_item_mh";
                }else{
                    tbl = "tbl_item";
                }
                System.out.println("update " + db.schema + "."+tbl+" set stock=stock -"+obj.getQty()+"  where code ='" + obj.getCode() + "' ");
            bool = st.executeUpdate("update " + db.schema + "."+tbl+" set stock= stock -"+obj.getQty()+"  where code ='" + obj.getCode() + "'");

            }
            if (bool > 0) {
                flag = true;
               
                }
                
            
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
        
    }
    
    private String setClientId() {
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
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
       
    private boolean clientExist(String clientName) {
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_client where name = '" + clientName + "'");
            if (rs.next()) {
                return true;
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @FXML
    private void btnclear_onAction(ActionEvent event) {
        sbxclient.clear();
        clear();
    }


    @FXML
    private void hplremove_onAction(ActionEvent event) {
         if (tblview.getSelectionModel().getSelectedItem() != null) {
            remove_row();
        }
        
    }
   
    @FXML
    private void btnprint_onAction(ActionEvent event) {
        System.err.println("Print working-------------->");
                                ThermalPrinterPageSetup tps = new ThermalPrinterPageSetup(new String (txtbillno.getText()));
                                if (tps.print()) {
//                                    clear();
//                                    sbxclient.clear();
                                    System.out.println("Print Successful");
                                } else {
                                    System.out.println("Print Not Successful");
                                }
                            
        
    }
    
    public void setStage(Stage stage_add_sales) {
        this.stage = stage_add_sales;
        this.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                    SalesMasterController emc =new SalesMasterController();
                    emc.populate_table();
                }
            }
        });
    }

    @FXML
    private void Import_Onaction(ActionEvent event) throws IOException {
            boolean transaction_status = false;
            try {
                  db.con.setAutoCommit(false);
                  if (delete_tbl_bill()) {
                        if (importExecl()) {
                           populate_table();
                           db.con.commit();
                        transaction_status = true;   
                      }                         
                  }
                  if (transaction_status = true) {
//                        db.con.rollback();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bill Added Sucessfully", ButtonType.OK);
                        alert.showAndWait();
                  }
                  else if (transaction_status = false) {
                        db.con.rollback();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Database/Excel importing Error Please Contact Service Provider", ButtonType.OK);
                        alert.showAndWait();                 
                  }
                  db.con.setAutoCommit(true);
            }
            catch (SQLException ex) {
                  Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
   private boolean delete_tbl_bill() {
            boolean flag = false;
            try {
                  Statement st = db.con.createStatement();
                  System.out.println("delete from " + db.schema + ".tbl_excel_bill");
                  int bool = st.executeUpdate("delete from " + db.schema + ".tbl_excel_bill ");
                  if (bool > 0) {
                        flag = true;
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return flag;
      }
    
   private boolean importExecl(){
       System.out.println("inside import");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(
                 new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx")
               
        );
        File file = fileChooser.showOpenDialog(stage);
              try{
            db.con.setAutoCommit(false);
            PreparedStatement pstm = null ;
                   System.out.println("getin app");
//            FileInputStream input = new FileInputStream("C:\\Users\\RASHI\\Desktop\\Book1.xls");
                FileInputStream input = new FileInputStream(file.getAbsolutePath());
              System.out.println("Add file");
                  System.out.println("input file=====  "+input);
            POIFSFileSystem fs = new POIFSFileSystem( input );
              System.out.println("Inputi");
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for(int i=0; i<=sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);               
                String code = row.getCell(0).getStringCellValue();
                System.out.println("name.."+code);
                Double rate = (Double) row.getCell(1).getNumericCellValue();
                System.out.println("rate.."+rate);
                int qty = (int) row.getCell(2).getNumericCellValue();
                System.out.println("qty.."+qty);
                Double tot = (Double) row.getCell(3).getNumericCellValue();
                System.out.println("tot.."+tot);
                Double gst = (Double) row.getCell(4).getNumericCellValue();
                System.out.println("gst.."+gst);
                Double gstamt = (Double) row.getCell(5).getNumericCellValue();
                System.out.println("gstamt.."+gstamt);
                Double net = (Double) row.getCell(6).getNumericCellValue();
                System.out.println("net.."+net);
                String sql = "insert into " + db.schema + ".tbl_excel_bill values(null,'" + code + "'," + rate+ ", " +qty + ", " +tot+ "," + gst + "," +gstamt+ ", " + net+")"; 
                System.out.println(sql);
                pstm = (PreparedStatement) db.con.prepareStatement(sql);
                pstm.execute();
                System.out.println("Import rows "+i);
            }
            db.con.commit();
            pstm.close();
//            db.con.close();
            input.close();
            System.out.println("Success import excel to mysql table");          
        }catch(SQLException ex){
            System.out.println(ex);
                  System.out.println("catch 1");
        }catch(IOException ioe){
            System.out.println(ioe);
                  System.out.println("catch 2");
        }
         return false;
   }
   
   private void populate_table(){
     table_data.removeAll(table_data);
        try {
            Statement stmt1 = db.con.createStatement();
            System.out.println("select p.barcode,p.code,p.name,b.rate,b.qty,b.gst,b.gstamt,b.netamt from " + db.schema + ".tbl_item p , " + db.schema + ".tbl_excel_bill b WHERE p.flipkart= b.code || p.amazon = b.code || p.shop = b.code || p.others = b.code");
            ResultSet rs = stmt1.executeQuery("select p.barcode,p.code,p.name,b.rate,b.qty,b.gst,b.gstamt,b.netamt from " + db.schema + ".tbl_item p , " + db.schema + ".tbl_excel_bill b WHERE p.flipkart= b.code || p.amazon = b.code || p.shop = b.code || p.others = b.code");
            while (rs.next()) {   
                table_data.add(new objsales(rs.getString("barcode"), rs.getString("code"), rs.getString("name"), rs.getString("rate"), rs.getString("qty"), rs.getString("gst"), rs.getString("gstamt"), rs.getString("netamt")));
            }
            stmt1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }  
   }
   
   
}
