/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_PurchasReturn;


import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ReturnsController implements Initializable {

    @FXML
    private TextField txtReturnID;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtInvoice;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtmob;
    @FXML
    private TextField txtgstno;
    @FXML
    private TableView<objtbl> tblView;
    @FXML
    private TableColumn colitem;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn coluom;
    @FXML
    private TableColumn colrate;
    @FXML
    private TableColumn colsgst;
    @FXML
    private TableColumn colcgst;
    @FXML
    private TableColumn coligst;
    public ObservableList<objtbl> table_data = FXCollections.observableArrayList();
    @FXML
    private TableColumn coltot;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn coltotal;
    @FXML
    private TextField txtsgst;
    @FXML
    private TextField txtcgst;
    @FXML
    private TextField txtigst;
    @FXML
    private TextField txtgrandtot;
    @FXML
    private TextField txtgstamt;
    @FXML
    private TextField txttot;
    @FXML
    private Button btnupdate;
    @FXML
    private TextField txtPoID;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private TableColumn colselect;
    @FXML
    private TextField txtsup;
    private static DecimalFormat df = new DecimalFormat(".##");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Setcode();
        set_table();
    }   
    
    private void set_table() {
        colitem.setCellValueFactory(new PropertyValueFactory<objtbl, String>("item"));
        colqty.setCellValueFactory(new PropertyValueFactory<objtbl, String>("qty"));
        coluom.setCellValueFactory(new PropertyValueFactory<objtbl, String>("uom"));
        colrate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("rate"));
        colsgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("sgst"));
        colcgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("cgst"));
        coligst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("igst"));
        coltot.setCellValueFactory(new PropertyValueFactory<objtbl, String>("tot"));
        colgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("gst"));
        coltotal.setCellValueFactory(new PropertyValueFactory<objtbl, String>("net"));
        colselect.setCellValueFactory(new PropertyValueFactory<objtbl, String>("select"));
        table_data.add(new objtbl("", "", "", "", "", "", "","","","",""));
        tblView.setItems(table_data);

    }
    
    public void set_total() {
        Double grand = 0.0;
        Double gst = 0.0;
        Double net = 0.0;
        Double sgst=0.0;
        Double igst=0.0;
        Double cgst=0.0;

        for (int i = 0; i < tblView.getItems().size(); i++) {
            objtbl sm = tblView.getItems().get(i);
            if(sm.getSelect().isSelected()){
                System.out.println("checked===  "+sm.getItem());
               continue; 
            } else{
                Double amt = Double.parseDouble(sm.getTot());
                grand = grand + amt;
                Double gstamt = Double.parseDouble(sm.getGst());
                gst = gst + gstamt;
                Double netamt = Double.parseDouble(sm.getNet());
                net = net + netamt;
                sgst = Double.parseDouble(sm.getTot()) * Double.parseDouble(sm.getSgst())/100;
                cgst = Double.parseDouble(sm.getTot()) * Double.parseDouble(sm.getCgst())/100;
                igst = Double.parseDouble(sm.getTot()) * Double.parseDouble(sm.getIgst())/100;
            
        }
    }
        
        if (table_data.size() > 0) {
               txttot.setText(df.format(net));
               txtgstamt.setText(df.format(gst));
               txtgrandtot.setText(df.format(grand));
               txtsgst.setText(df.format(sgst));
               txtcgst.setText(df.format(cgst));
               txtigst.setText(df.format(igst));

        } else {
            txtgstamt.setText("0.0");
            txtgrandtot.setText("0.0");
            txtsgst.setText("0.0");
            txtcgst.setText("0.0");
            txtigst.setText("0.0");
            txttot.setText("0.0");
        }
    }
  
    private boolean save_POitems(String poid) {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            Statement stmt = db.con.createStatement();
            for (objtbl obj : table_data) {
                if (obj.getItem().equals("") && obj.getQty().equals("") && obj.getUom().equals("") && obj.getRate().equals("")  && obj.getSgst().equals("") && obj.getCgst().equals("") && obj.getIgst().equals("") && obj.getTot().equals("")&& obj.getGst().equals("") && obj.getNet().equals(""))  {
                    continue;
                }
                if (obj.getSelect().isSelected()) {
                   Double tot=0.0;
                tot= Double.parseDouble(obj.getRate()) * Double.parseDouble(obj.getQty());
                System.out.println("insert into " + db.schema + ".tbl_portn_items values(null, '" + txtReturnID.getText() + "', '" + obj.getItem()+ "', '" + obj.getQty()+ "', '" +  obj.getUom()+ "', " + obj.getRate()+ "," +  obj.getSgst()+ ", " + obj.getCgst() + "," +  obj.getIgst()+ ", " + obj.getTot()+ "," + obj.getGst()+ "," +  obj.getNet()+ ", 1)");
                bool = stmt.executeUpdate("insert into " + db.schema + ".tbl_portn_items values(null, '" + txtReturnID.getText() + "', '" + obj.getItem()+ "', '" + obj.getQty()+ "', '" +  obj.getUom()+ "', " + obj.getRate()+ "," +  obj.getSgst()+ ", " + obj.getCgst() + "," +  obj.getIgst()+ ", " + obj.getTot()+ "," + obj.getGst()+ "," +  obj.getNet()+ ", 1)"); 
                } else {
                Double tot=0.0;
                tot= Double.parseDouble(obj.getRate()) * Double.parseDouble(obj.getQty());
                System.out.println("insert into " + db.schema + ".tbl_po_items values(null, '" + poid + "', '" + obj.getItem()+ "', '" + obj.getQty()+ "', '" +  obj.getUom()+ "', " + obj.getRate()+ "," +  obj.getSgst()+ ", " + obj.getCgst() + "," +  obj.getIgst()+ ", " + obj.getTot()+ "," + obj.getGst()+ "," +  obj.getNet()+ ", 1)");
                bool = st.executeUpdate("insert into " + db.schema + ".tbl_po_items values(null, '" + poid + "', '" + obj.getItem()+ "', '" + obj.getQty()+ "', '" +  obj.getUom()+ "', " + obj.getRate()+ "," +  obj.getSgst()+ ", " + obj.getCgst() + "," +  obj.getIgst()+ ", " + obj.getTot()+ "," + obj.getGst()+ "," +  obj.getNet()+ ", 1)");
                }
            }
            if (bool > 0) {
                flag = true;    
            }
            stmt.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    private boolean updateStock() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objtbl obj : table_data) {
                if (obj.getItem().equals("") && obj.getQty().equals("") && obj.getUom().equals("") && obj.getRate().equals("")  && obj.getSgst().equals("") && obj.getCgst().equals("") && obj.getIgst().equals("") && obj.getTot().equals("")&& obj.getGst().equals("") && obj.getNet().equals(""))  {
                    continue;
                }
                if(obj.getSelect().isSelected()){
                   System.out.println("update " + db.schema + ".tbl_item_master set qty =qty - "+obj.getQty()+"  where name ='" + obj.getItem()+ "' ");
            bool = st.executeUpdate("update " + db.schema + ".tbl_item_master set qty = qty  - "+obj.getQty()+"  where name ='" + obj.getItem()+ "'");
 
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
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
        
    }
        private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtReturnID.setText("PORTN"+no);
        
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select count(code), max(code) FROM " + db.schema + ".tbl_po_rtn");
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_po_rtn");
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
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void updatePO() {
        try {
            boolean transaction_status = false;
            db.con.setAutoCommit(false);
            if (update_po(txtPoID.getText(),txtsgst.getText(),txtcgst.getText(),txtigst.getText(),txtgrandtot.getText(),txtgstamt.getText(),txttot.getText())) {
                if(savereturn()){
                if (delete_po_open_db_items(txtPoID.getText())) {
                    if (save_POitems(txtPoID.getText())) {
                        if (updateStock()) {
                            transaction_status = true;
                            db.con.commit();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Purchase Return Saved Successfully", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.YES) {
                                
                                }
                              ReturnMasterController rmc =new ReturnMasterController();
                                rmc.populate_table();
                        }
                    }
                }
               }
            }
            if (!transaction_status) {        
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database Transaction Error: Cannot update Purchase Return", ButtonType.OK);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.OK) {
                                    db.con.rollback();
                                }
            }
            db.con.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean delete_po_open_db_items(String po_id) {
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("delete from " + db.schema + ".tbl_po_items where code = '" + po_id + "'");
            int bool = st.executeUpdate("delete from " + db.schema + ".tbl_po_items where code = '" + po_id + "'");
            if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    private boolean update_po(String po_id,String sgst, String cgst, String igst, String grand,String gst,String tot) {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_po set sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grandtot ="+grand+",gst="+gst+",total="+tot+" where code = '" + po_id + "'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_po set sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grandtot ="+grand+",gst="+gst+",total="+tot+" where code = '" + po_id + "'");
            if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
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
        for (int i = 0; i < tblView.getItems().size(); i++) {
            objtbl sm = tblView.getItems().get(i);
            if(sm.getSelect().isSelected()){
                System.out.println("checked===  "+sm.getItem());
                Double amt = Double.parseDouble(sm.getTot());
            sum = sum + amt;
            Double gstamt = Double.parseDouble(sm.getGst());
            gst = gst + gstamt;
            Double netamt = Double.parseDouble(sm.getNet());
            grand = grand + netamt;
              
            }else{
             continue;   
        }
    }
            System.out.println("insert into " + db.schema + ".tbl_po_rtn values(null, '" + txtReturnID.getText() + "', '" + dpDate.getValue()+ "', '" + txtTime.getText()+ "', '" +  txtPoID.getText()+ "', '" + txtsup.getText()+ "','" +  txtInvoice.getText() + "',"+sum+","+gst+","+grand+")");
            int bool = st.executeUpdate("insert into " + db.schema + ".tbl_po_rtn values(null, '" + txtReturnID.getText() + "', '" + dpDate.getValue()+ "', '" + txtTime.getText()+ "', '" +  txtPoID.getText()+ "', '" + txtsup.getText()+ "','" +  txtInvoice.getText() + "',"+sum+","+gst+","+grand+")");
             if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    @FXML
    private void btnupdate_onaction(ActionEvent event) {
        set_total();
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Return Purchase Order ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                updatePO();
                stage.close();
            }
    }

    public void setStage(Stage stageEditPO) {
        this.stage = stageEditPO;
      
    }
    private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select city,state,mob,gst from " + db.schema + ".tbl_supplier where name = '" + txtsup.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select  city,state,mob,gst from " + db.schema + ".tbl_supplier where name = '" + txtsup.getText() + "' ");
                while (rst.next()) {
                    txtCity.setText(rst.getString("city"));
                    txtState.setText(rst.getString("state"));
                    txtmob.setText(rst.getString("mob"));
                    txtgstno.setText(rst.getString("gst"));
                    
                }
               }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fetch_for_update(String id) {
            try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_po where code = '" + id + "'");
            if (rs.next()) {
                txtPoID.setText(id);
                dpDate.setValue(rs.getDate("date").toLocalDate());
                txtTime.setText(rs.getString("time"));
                txtInvoice.setText(rs.getString("invoice"));
                txtsup.setText(rs.getString("suplier"));               
                txtgrandtot.setText(rs.getString("grandtot"));
                txtgstamt.setText(rs.getString("gst"));
                txttot.setText(rs.getString("total"));
                txtsgst.setText(rs.getString("sgst"));
                txtcgst.setText(rs.getString("cgst"));
                txtigst.setText(rs.getString("igst"));
                setvalue();
            }
            
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_data.removeAll(table_data);
        try {
            Statement st1 = db.con.createStatement();
            System.out.println("select *from " + db.schema + ".tbl_po_items  where  code = '" + id + "'");
            ResultSet rs = st1.executeQuery("select *from " + db.schema + ".tbl_po_items  where  code = '" + id + "'");
            while (rs.next()) {
                table_data.add(new objtbl(rs.getString("item"), rs.getString("qty"), rs.getString("unit"), rs.getString("rate"), rs.getString("sgst"), rs.getString("cgst"), rs.getString("igst"), rs.getString("grandtot"),rs.getString("gst"), rs.getString("total"),""));
            }
            st1.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnsController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
