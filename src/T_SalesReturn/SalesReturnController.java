
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_SalesReturn;


import database.DBMySQL;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
public class SalesReturnController implements Initializable {

    @FXML
    private Label lblBillNo;
    @FXML
    private TextField txtbillno;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtmob;
    @FXML
    private TextField txtplace;
    @FXML
    private TextField txtdist;
    @FXML
    private TextField txtgstin;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private TableView<objSalesRT> tblview;
    @FXML
    private TableColumn colbar;
    @FXML
    private TableColumn colcode;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colrate;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colgstamt;
    @FXML
    private TableColumn coltot;
    @FXML
    private AnchorPane anchrtot;
    @FXML
    private Label lblqty;
    @FXML
    private Label lblitems;
    @FXML
    private TextField txtgrand;
    @FXML
    private TextField txtgstamt;
    @FXML
    private TextField txtdiscper;
    @FXML
    private TextField txtdisc;
    @FXML
    private TextField txtship;
    @FXML
    private TextField txtnetamt;
    @FXML
    private AnchorPane anchrtot1;
    @FXML
    private TextField txtsgst;
    @FXML
    private TextField txtcgst;
    @FXML
    private TextField txtigst;
    @FXML
    private RadioButton rbtsgst;
    @FXML
    private RadioButton rbtigst;
    @FXML
    private Button btnPrint;
    @FXML
    private TextField txtstate;
    @FXML
    private Label lblBillNo1;
    @FXML
    private TextField txtrtID;
    @FXML
    private TextField txtname;
    @FXML
    private TableColumn colselect;
    @FXML
    private TextField txtagent;
    @FXML
    private TextField txtwh;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    private static DecimalFormat df = new DecimalFormat(".##");
    public ObservableList<objSalesRT> table_data = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Setcode();
        set_table();
        initClock();
    }    
    
    private void set_table() {
        colbar.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("bar"));
        colcode.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("code"));
        colname.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("name"));
        colrate.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("rate"));
        colqty.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("qty"));
        colgst.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("gst"));
        colgstamt.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("gstamt"));
        coltot.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("tot"));
        colselect.setCellValueFactory(new PropertyValueFactory<objSalesRT, String>("select"));
        table_data.add(new objSalesRT("", "", "", "", "", "", "","",""));
        tblview.setItems(table_data);

    }
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
       txtTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        dpDate.setValue(LocalDate.now());
    }
    
    private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtrtID.setText("SLSRTN"+no);
        
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select count(code), max(code) FROM " + db.schema + ".tbl_sales_rtn");
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_sales_rtn");
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
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void updatebill() {
        try {
            boolean transaction_status = false;
            db.con.setAutoCommit(false);
            if (update_bill(txtbillno.getText(),txtsgst.getText(),txtcgst.getText(),txtigst.getText(),txtgrand.getText(),txtgstamt.getText(),txtnetamt.getText())) {
                if(savereturn()){
                if (delete_bill_items(txtbillno.getText())) {
                    if (save_bill_items(txtbillno.getText())) {
                        if (updateStock()) {
                            transaction_status = true;
                            db.con.commit();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sales Return Saved Successfully", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.YES) {
                                
                                }
//                              SalesReturnMasterController srmc = new SalesReturnMasterController();
//                                srmc.populate_table();
                        }
                    }
                }
               }
            }
            if (!transaction_status) {        
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database Transaction Error: Cannot update Sales Return", ButtonType.OK);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.OK) {
                                    db.con.rollback();
                                }
            }
            db.con.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean update_bill(String billno,String sgst, String cgst, String igst, String grand,String gst,String tot) {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_bill set items = "+lblitems.getText()+",qty ="+lblqty.getText()+", sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grand ="+grand+",gst="+gst+",netamt="+tot+" where billno = '" + billno + "'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_bill set items = "+lblitems.getText()+",qty ="+lblqty.getText()+", sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grand ="+grand+",gst="+gst+",netamt="+tot+" where billno  = '" + billno + "'");
            if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    private boolean savereturn() {
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();     
       Double sum = 0.0;
       Double gst = 0.0;
       Double grand = 0.0;
        for (int i = 0; i < tblview.getItems().size(); i++) {
            objSalesRT sm = tblview.getItems().get(i);
            if(sm.getSelect().isSelected()){
                System.out.println("checked===  "+sm.getName());
                Double amt = Double.parseDouble(sm.getQty()) * Double.parseDouble(sm.getRate()) ;
            sum = sum + amt;
            Double gstamt = Double.parseDouble(sm.getGstamt());
            gst = gst + gstamt;
                System.out.println("gsttttt  "+gst);
            Double netamt = Double.parseDouble(sm.getTot());
            grand = grand + netamt;
              
            }else{
             continue;   
        }
    }
            System.out.println("insert into " + db.schema + ".tbl_sales_rtn values(null, '" + txtrtID.getText() + "', '" + dpDate.getValue()+ "', '" + txtTime.getText()+ "', '" +  txtbillno.getText()+ "', '" + txtname.getText()+ "','" +  txtmob.getText() + "',"+sum+","+gst+","+grand+")");
            int bool = st.executeUpdate("insert into " + db.schema + ".tbl_sales_rtn values(null, '" + txtrtID.getText() + "', '" + dpDate.getValue()+ "', '" + txtTime.getText()+ "', '" +  txtbillno.getText()+ "', '" + txtname.getText()+ "','" +  txtmob.getText() + "',"+sum+","+gst+","+grand+")");
             if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    private boolean delete_bill_items(String po_id) {
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("delete from " + db.schema + ".tbl_bill_items where billno = '" + po_id + "'");
            int bool = st.executeUpdate("delete from " + db.schema + ".tbl_bill_items where billno = '" + po_id + "'");
            if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    private boolean save_bill_items(String billno) {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            Statement stmt = db.con.createStatement();
            for (objSalesRT obj : table_data) {
                if (obj.getBar().equals("") && obj.getCode().equals("") && obj.getName().equals("") && obj.getRate().equals("")  && obj.getQty().equals("") && obj.getGst().equals("") && obj.getGstamt().equals("") && obj.getTot().equals(""))  {
                    continue;
                }
                if (obj.getSelect().isSelected()) {
                   Double tot=0.0;
                tot= Double.parseDouble(obj.getRate()) * Double.parseDouble(obj.getQty());
                System.out.println("insert into " + db.schema + ".tbl_sales_rtn_items values(null, '" + txtrtID.getText() + "', '" + obj.getBar() + "', '" +obj.getCode()+ "', '" +  obj.getName() + "', " + obj.getRate()+ "," +  obj.getQty()+ ", " + tot + "," +  obj.getGst()+ ", " + obj.getGstamt()+ "," +  obj.getTot()+ ", 1)");
                bool = stmt.executeUpdate("insert into " + db.schema + ".tbl_sales_rtn_items values(null, '" + txtrtID.getText() + "', '" + obj.getBar() + "', '" +obj.getCode()+ "', '" +  obj.getName() + "', " + obj.getRate()+ "," +  obj.getQty()+ ", " + tot + "," +  obj.getGst()+ ", " + obj.getGstamt()+ "," +  obj.getTot()+ ", 1)");
                } else {
                Double tot=0.0;
                tot= Double.parseDouble(obj.getRate()) * Double.parseDouble(obj.getQty());
                System.out.println("insert into " + db.schema + ".tbl_bill_items values(null, '" + billno + "', '" + obj.getBar() + "', " + obj.getCode()+ ", '" +  obj.getName() + "', " + obj.getRate()+ "," +  obj.getQty()+ ", " + tot + "," +  obj.getGst()+ ", " + obj.getGstamt()+ "," +  obj.getTot()+ ", 1)");
                bool = st.executeUpdate("insert into " + db.schema + ".tbl_bill_items values(null, '" + billno + "', '" + obj.getBar() + "', " +obj.getCode()+ ", '" +  obj.getName() + "', " + obj.getRate()+ "," +  obj.getQty()+ ", " + tot + "," +  obj.getGst()+ ", " + obj.getGstamt()+ "," +  obj.getTot()+ ", 1)");
                }
            }
            if (bool > 0) {
                flag = true;    
            }
            stmt.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    private boolean updateStock() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objSalesRT obj : table_data) {
                if (obj.getBar().equals("") && obj.getCode().equals("") && obj.getName().equals("") && obj.getRate().equals("")  && obj.getQty().equals("") && obj.getGst().equals("") && obj.getGstamt().equals("") && obj.getTot().equals(""))  {
                    continue;
                }
                String wh = txtwh.getText();
                String tbl = "";
                if (wh.equals("TAMILNADU")) {
                  tbl= "tbl_item_tn"; 
                }else if (wh.equals("MAHARASHTRA")) {
                    tbl = "tbl_item_mh";
                }else{
                    tbl = "tbl_item";
                }
                if(obj.getSelect().isSelected()){
                   System.out.println("update " + db.schema + "."+tbl+" set stock=stock + "+obj.getQty()+"  where code ='" + obj.getCode() + "' ");
                   bool = st.executeUpdate("update " + db.schema + "."+tbl+" set stock= stock + "+obj.getQty()+"  where code ='" + obj.getCode() + "'");
                }else{               
                    continue;
                }   
            }
            if (bool > 0) {
                flag = true;  
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
        
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
          if(tblview.getItems().size()>0){
            for (objSalesRT obj : tblview.getItems()){
                if(obj.getSelect().isSelected()){
                System.out.println("checked===  "+obj.getName());
               continue; 
            } else{
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
//            lblitems.setText(items.toString());
//            lblqty.setText(qty.toString());
//            BigDecimal grand_total = new BigDecimal(grand_tot).setScale(2, RoundingMode.HALF_EVEN);
//            txtgrand.setText(grand_total.toString());
//            BigDecimal gstamtt = new BigDecimal(gstamt).setScale(2, RoundingMode.HALF_EVEN);
//            txtgstamt.setText(gstamtt.toString());
//            BigDecimal netamount = new BigDecimal(netamt).setScale(2, RoundingMode.HALF_EVEN);
//            txtnetamt.setText(netamount.toString());
//            BigDecimal sgstamt = new BigDecimal(sgst).setScale(2, RoundingMode.HALF_EVEN);
//            txtsgst.setText(sgstamt.toString());
//            BigDecimal cgstamt = new BigDecimal(cgst).setScale(2, RoundingMode.HALF_EVEN);
//            txtcgst.setText(cgstamt.toString());
                }
            }
            lblitems.setText(items.toString());
            lblqty.setText(qty.toString());
            BigDecimal grand_total = new BigDecimal(grand_tot).setScale(2, RoundingMode.HALF_EVEN);
            txtgrand.setText(grand_total.toString());
            BigDecimal gstamtt = new BigDecimal(gstamt).setScale(2, RoundingMode.HALF_EVEN);
            txtgstamt.setText(gstamtt.toString());
            BigDecimal netamount = new BigDecimal(netamt).setScale(2, RoundingMode.HALF_EVEN);
            txtnetamt.setText(netamount.toString());
            BigDecimal sgstamt = new BigDecimal(sgst).setScale(2, RoundingMode.HALF_EVEN);
            txtsgst.setText(sgstamt.toString());
            BigDecimal cgstamt = new BigDecimal(cgst).setScale(2, RoundingMode.HALF_EVEN);
            txtcgst.setText(cgstamt.toString());
          }
    } 
    @FXML
    private void btnprint_onAction(ActionEvent event) {
       settextbox();
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Return This Sales ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
               updatebill();
                stage.close();
            }
    }

    public void setStage(Stage stage_add_sales) {
       this.stage= stage_add_sales;
    }

    public void fetch_for_update(String bill) {
      try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_bill where billno = '" + bill + "'");
            if (rs.next()) {
                txtbillno.setText(bill);
//                dpDate.setValue(rs.getDate("date").toLocalDate());
//                txtTime.setText(rs.getString("time"));
                txtname.setText(rs.getString("client"));               
                txtgrand.setText(rs.getString("grand"));
                txtdisc.setText(rs.getString("discount"));
                txtgstamt.setText(rs.getString("gst"));
                txtship.setText(rs.getString("ship"));
                txtnetamt.setText(rs.getString("netamt"));
                txtsgst.setText(rs.getString("sgst"));
                txtcgst.setText(rs.getString("cgst"));
                txtigst.setText(rs.getString("igst"));
                lblitems.setText(rs.getString("items"));
                lblqty.setText(rs.getString("qty"));
                txtagent.setText(rs.getString("agent"));
                txtwh.setText(rs.getString("wh"));
                setvalue();
            }
            
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_data.removeAll(table_data);
        try {
            Statement st1 = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_bill_items  where  billno = '" + bill + "'");
            ResultSet rs = st1.executeQuery("select * from " + db.schema + ".tbl_bill_items  where  billno = '" + bill + "'");
            while (rs.next()) {
                table_data.add(new objSalesRT(rs.getString("barcode"), rs.getString("code"), rs.getString("name"), rs.getString("rate"), rs.getString("qty"), rs.getString("gst"), rs.getString("gstamt"), rs.getString("netamt"),""));
            }
            st1.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select code,gender,mob,place,gst,dis,zip,state from " + db.schema + ".tbl_client where name = '" + txtname.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select code,gender,mob,place,gst,dis,zip,state from " + db.schema + ".tbl_client where name = '" + txtname.getText() + "' ");
                while (rst.next()) {
                    txtid.setText(rst.getString("code"));
                    txtmob.setText(rst.getString("mob"));
                    txtplace.setText(rst.getString("place"));
                    txtgstin.setText(rst.getString("gst"));
                    txtdist.setText(rst.getString("dis"));
                    txtstate.setText(rst.getString("state"));
                }
               }
        } catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
