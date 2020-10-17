/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelbill;

import com.mysql.jdbc.PreparedStatement;
import database.DBMySQL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ExcelBillController implements Initializable {
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn colcode;
    @FXML
    private TableColumn coldesp;
    @FXML
    private TableColumn colrate;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn coltot;
    @FXML
    private TableColumn colsgst;
    @FXML
    private TableColumn colcgst;
    @FXML
    private TableColumn coligst;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colnet;
    @FXML
    private TableColumn colclient;
    @FXML
    private TableColumn colmob;
    @FXML
    private TableColumn coladd;
    @FXML
    private TableColumn colgstin;
    @FXML
    private TableColumn colzip;
    @FXML
    private TableColumn colst;
    @FXML
    private TableColumn colpay;
    @FXML
    private RadioButton rbtKL;
    @FXML
    private RadioButton rbtMH;
    @FXML
    private RadioButton rbtTN;
    @FXML
    private Button btnsave;
    @FXML
    private Button btnimport;
    @FXML
    private TableView<objtbl> tbldata;
    DBMySQL db = new DBMySQL();
    public ObservableList<objtbl> table_data = FXCollections.observableArrayList();
    @FXML
    private Button btnclear;
    @FXML
    private ComboBox<String> cmbagent;
    @FXML
    private Label lblcount;
    @FXML
    private TableColumn coltime;
    private static DecimalFormat df = new DecimalFormat(".##");
    @FXML
    private TextField txtsgst;
    @FXML
    private TextField txtcgst;
    @FXML
    private TextField txtigst;
    @FXML
    private TextField txtgrand;
    @FXML
    private TextField txtgst;
    @FXML
    private TextField txtnet;
    private ObservableList<String> listagent = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        set_table();
             populateCombo_agent();
             clear();
    }  
    
    private void clear(){
        rbtKL.isSelected();
        cmbagent.getSelectionModel().select(null);
        txtsgst.setText("0.0");
        txtcgst.setText("0.0");
        txtigst.setText("0.0");
        txtgst.setText("0.0");
        txtgrand.setText("0.0");
        txtnet.setText("0.0");
        lblcount.setText("0");
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
                        Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
    private void set_table() {
        ToggleGroup grp = new ToggleGroup();
        rbtKL.setToggleGroup(grp);
        rbtMH.setToggleGroup(grp);
        rbtTN.setToggleGroup(grp);
        rbtKL.setSelected(true);
        colid.setCellValueFactory(new PropertyValueFactory<objtbl, String>("odid"));
        coldate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<objtbl, String>("time"));
        colcode.setCellValueFactory(new PropertyValueFactory<objtbl, String>("code"));
        coldesp.setCellValueFactory(new PropertyValueFactory<objtbl, String>("desc"));
        colrate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("rate"));
        colqty.setCellValueFactory(new PropertyValueFactory<objtbl, String>("qty"));
        coltot.setCellValueFactory(new PropertyValueFactory<objtbl, String>("tot"));
        colsgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("sgst"));
        colcgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("cgst"));
        coligst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("igst"));
        colgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("gst"));
        colnet.setCellValueFactory(new PropertyValueFactory<objtbl, String>("net"));
        colclient.setCellValueFactory(new PropertyValueFactory<objtbl, String>("client"));
        colmob.setCellValueFactory(new PropertyValueFactory<objtbl, String>("mob"));
        coladd.setCellValueFactory(new PropertyValueFactory<objtbl, String>("add"));
        colgstin.setCellValueFactory(new PropertyValueFactory<objtbl, String>("gstin"));
        colzip.setCellValueFactory(new PropertyValueFactory<objtbl, String>("zip"));
        colst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("st"));
        colpay.setCellValueFactory(new PropertyValueFactory<objtbl, String>("pay"));
        table_data.add(new objtbl("", "", "", "", "", "", "","","","","","","","","","","","","",""));
        tbldata.setItems(table_data);

    }

    public void setStage(Stage stage_mass) {
    this.stage = stage_mass;  
    }
    
    private boolean importExecl(){
        boolean flag = false;
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
            for(int i=1; i<=sheet.getLastRowNum(); i++){
//                for(int i=1; i<=14; i++){
                row = sheet.getRow(i);               
               Date  date = row.getCell(2).getDateCellValue();
                System.out.println("date.."+date);       
                    Calendar cal = Calendar.getInstance(); 
                cal.setTime(date);
                String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +cal.get(Calendar.DATE);
                String formatedTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)+ ":" +cal.get(Calendar.SECOND);
                System.out.println("formatedDate : " + formatedDate); 
                System.out.println("formatedTime : " + formatedTime); 
                String odID = row.getCell(3).getStringCellValue();
                System.out.println("order id.."+odID);
                int qty = (int) row.getCell(4).getNumericCellValue();
                System.out.println("qty.."+qty);
                String item = row.getCell(5).getStringCellValue();
                System.out.println("name.."+item);
                String code = row.getCell(7).getStringCellValue();
                System.out.println("code.."+code);
                 String noSpaceStr = code.replaceAll("\\s", ""); 
                String st = row.getCell(8).getStringCellValue();
                System.out.println("state.."+st);
                int zip = (int)row.getCell(9).getNumericCellValue();
                System.out.println("zip.."+zip);
                Double net = (Double) row.getCell(10).getNumericCellValue();
                System.out.println("net.."+net);
                Double rate = (Double) row.getCell(11).getNumericCellValue();
                Double rt = rate/qty;
                System.out.println("rate.."+rt);
                Double tot = (Double) row.getCell(11).getNumericCellValue();
                System.out.println("tot.."+tot);
                 Double gstamt = (Double) row.getCell(12).getNumericCellValue();
                System.out.println("gstamt.."+gstamt);
                Double cgst = (Double) row.getCell(13).getNumericCellValue();
                System.out.println("cgst.."+cgst);
                Double cgsts = cgst *100;
                Double sgst = (Double) row.getCell(14).getNumericCellValue();
                System.out.println("sgst.."+sgst);
                Double sgsts = sgst *100;
                Double igst = (Double) row.getCell(15).getNumericCellValue();
                System.out.println("igst.."+igst);
                Double igsts = igst *100;
                String gstin = row.getCell(16).getStringCellValue();
                System.out.println("gstin.."+gstin);
                String name = row.getCell(17).getStringCellValue();
                System.out.println("name.."+name);
                int mob = (int)row.getCell(18).getNumericCellValue();
                System.out.println("mob.."+mob);
                String sql = "insert into " + db.schema + ".tbl_excel_bill values(null,'" + formatedDate + "','"+formatedTime+"','" +odID+ "', '" +noSpaceStr + "', '" +item+ "'," + rt + "," +qty+ ", " + tot+"," +sgsts+ "," + cgsts + "," +igsts+ ", " + gstamt+"," +net+ ",'" + name + "','" +mob+ "', '" + st+"','" +gstin+ "','" + zip + "','" +st+ "', 'cod')"; 
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
            flag = true;
        }catch(SQLException ex){
            System.out.println(ex);
                  System.out.println("catch 1");
        }catch(IOException ioe){
            System.out.println(ioe);
                  System.out.println("catch 2");
        }
         return flag;
   }
    private boolean populate_table(){
        boolean flag = false;
     table_data.removeAll(table_data);
        try {
            Statement stmt1 = db.con.createStatement();
//             System.out.println("select p.barcode,p.code,p.name,b.rate,b.qty,b.gst,b.gstamt,b.netamt from " + db.schema + ".tbl_item p , " + db.schema + ".tbl_excel_bill b WHERE p.flipkart= b.code || p.amazon = b.code || p.shop = b.code || p.others = b.code");
            System.out.println("select orderid,in_date,in_time,item_code,decrp,rate,qty,tot,sgst,cgst,igst,gstamt,netamt,client_name,mob,addrs,gstin,zipcode,state,pay from " + db.schema + ".tbl_excel_bill ");
            ResultSet rs = stmt1.executeQuery("select orderid,in_date,in_time,item_code,descrp,rate,qty,tot,sgst,cgst,igst,gstamt,netamt,client_name,mob,addrs,gstin,zipcode,state,pay from " + db.schema + ".tbl_excel_bill ");
            while (rs.next()) {   
                table_data.add(new objtbl(rs.getString("orderid"), rs.getString("in_date"),rs.getString("in_time"), rs.getString("item_code"), rs.getString("descrp"), rs.getString("rate"), rs.getString("qty"), rs.getString("tot"), rs.getString("sgst"), rs.getString("cgst"), rs.getString("igst"), rs.getString("gstamt"), rs.getString("netamt"), rs.getString("client_name"), rs.getString("mob"), rs.getString("addrs"), rs.getString("gstin"), rs.getString("zipcode"), rs.getString("state"), rs.getString("pay")));
            }
            lblcount.setText(String.valueOf(table_data.size()));
            flag = true;
            stmt1.close();
            settextbox();
        } catch (SQLException ex) {
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return flag;
   }
    
        private boolean checktbl() {
            boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) FROM " + db.schema + ".tbl_excel_bill");
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    System.out.println("count =="+count);
                    flag= true;
                } else {  
                  System.out.println("count =="+count);
                  delete_tbl_bill();
                  flag = true;
                }
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
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
                  Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return flag;
      }

    private boolean save_POitems() {
        boolean flag = false;
        int bool = 0,bool1 =0,bool2=0;
        try {
            String wh="";
                if (rbtKL.isSelected()) {
                    wh ="KERALA";
                }else if (rbtMH.isSelected()) {
                    wh="MAHARASTRA";
                }else if (rbtTN.isSelected()) {
                    wh ="TAMILNADU";
                }
            Statement st = db.con.createStatement();
            Statement st1 = db.con.createStatement();
            Statement st2 = db.con.createStatement();
            
            for (objtbl obj : table_data) {
                String billno = "";
                Integer pr = Integer.parseInt(setbillId()) + 1;
                billno = pr.toString();
                String clientId = "";
                Integer prt = Integer.parseInt(setClientId()) + 1;
                String no = String.format("%04d", prt);
                clientId ="CUST"+no;
                if (obj.getOdid().equals("") && obj.getDate().equals("") && obj.getTime().equals("") && obj.getCode().equals("")  && obj.getDesc().equals("") && obj.getQty().equals("") && obj.getRate().equals("") && obj.getTot().equals("")&& obj.getSgst().equals("") && obj.getCgst().equals("")&& obj.getIgst().equals("") && obj.getGst().equals("") && obj.getNet().equals("")&& obj.getClient().equals("") && obj.getMob().equals("")&& obj.getAdd().equals("") && obj.getGstin().equals("") && obj.getSt().equals("")&& obj.getPay().equals(""))  {
                    continue;
                }
                Double sgst = (Double.parseDouble(obj.getTot())*Double.parseDouble(obj.getSgst()))/100;
                
                Double cgst = (Double.parseDouble(obj.getTot())*Double.parseDouble(obj.getCgst()))/100;
                Double igst = (Double.parseDouble(obj.getTot())*Double.parseDouble(obj.getIgst()))/100;
                    System.out.println("insert into  " + db.schema + ".tbl_bill values(null, " + billno + ", '" + obj.getDate()+ "', '" + obj.getTime()+ "', 'AMAZON', '" + obj.getClient()+ "',1, " + obj.getQty()+ "," +obj.getTot()+ ", " +df.format(sgst)+ "," +df.format(cgst)+ "," + df.format(igst) + ","+ obj.getGst() +",0.0,0.0,"+obj.getNet()+",'"+wh+"','"+obj.getOdid()+"',1)");
                    bool = st.executeUpdate("insert into " + db.schema + ".tbl_bill values(null, " + billno + ", '" + obj.getDate()+ "', '" + obj.getTime()+ "', 'AMAZON', '" + obj.getClient()+ "',1, " + obj.getQty()+ "," +obj.getTot()+ "," +df.format(sgst)+ "," +df.format(cgst)+ "," +df.format(igst) + ","+ obj.getGst() +",0.0,0.0,"+obj.getNet()+",'"+wh+"','"+obj.getOdid()+"',1)");
                    System.out.println("insert into " + db.schema + ".tbl_bill_items values(null, '" + billno + "', '1', '" +obj.getCode()+ "', '" + obj.getDesc() + "', " + obj.getRate()+ " , " + obj.getQty() + "," +  obj.getTot()+ ",0.0," +  obj.getGst()+ "," + obj.getNet()+ ", 1)");
                    bool1 = st1.executeUpdate("insert into " + db.schema + ".tbl_bill_items values(null, '" + billno + "', '1', '" +obj.getCode()+ "', '" + obj.getDesc() + "', " + obj.getRate()+ "," + obj.getQty() + "," +  obj.getTot()+ ",0.0," +  obj.getGst()+ "," + obj.getNet()+ ", 1)");
                    System.out.println("insert into " + db.schema + ".tbl_client values(null, '" + clientId + "', '" + obj.getClient().toUpperCase() + "','MALE', '" +obj.getMob() + "', '', '', '" + obj.getAdd() + "','','','"+obj.getZip()+"', '', '"+obj.getSt()+"','','', '" +obj.getGstin() + "',  1)");
                    bool2 = st2.executeUpdate("insert into " + db.schema + ".tbl_client values(null, '" + clientId + "', '" + obj.getClient().toUpperCase() + "','MALE', '" +obj.getMob() + "', '', '', '" + obj.getAdd() + "','','','"+obj.getZip()+"', '', '"+obj.getSt()+"','','', '" +obj.getGstin() + "',  1)");

            }if (bool > 0) {
                flag = true;    
            }
            st.close();
             st1.close();
              st2.close();
        } 
         catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    private boolean updateStock() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objtbl obj : table_data) {
                String wh = "";
                if (rbtKL.isSelected()) {
                    wh ="tbl_item";
                }else if (rbtMH.isSelected()) {
                    wh="tbl_item_mh";
                }else if (rbtTN.isSelected()) {
                    wh ="tbl_item_tn";
                }
                System.out.println("update " + db.schema + "."+wh+" set stock=stock -"+obj.getQty()+"  where flipkart= '" + obj.getCode()+ "' || amazon = '" + obj.getCode()+ "' || shop = '" + obj.getCode()+ "' || others = '" + obj.getCode()+ "'");
            bool = st.executeUpdate("update " + db.schema + "."+wh+" set stock= stock -"+obj.getQty()+"  where flipkart= '" + obj.getCode()+ "' || amazon = '" + obj.getCode()+ "' || shop = '" + obj.getCode()+ "' || others = '" + obj.getCode()+ "'");
            
            }
            if (bool > 0) {
                System.out.println("row "+bool+ " inserted");
                flag = true;
               
                }
                
            
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("catch in update"+ ex);
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    private String setbillId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
//            System.out.println("select count(billno), max(billno) FROM " + db.schema + ".tbl_bill");
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
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public void settextbox(){
          
          Double grand_tot = 0.0;
          Double gstamt=0.0;
          Double netamt=0.0;
          Double sgst=0.0;
          Double igst=0.0;
          Double cgst=0.0;
          if(tbldata.getItems().size()>0){
            for (objtbl obj : tbldata.getItems()){
                grand_tot += Double.parseDouble(obj.getTot());
                System.out.println("column grand total===="+ grand_tot);
                gstamt +=  Double.parseDouble(obj.getGst());
                System.out.println("Colum totalgstamt===="+ gstamt);
                netamt += Double.parseDouble(obj.getNet());
                System.out.println("column total netamt"+  netamt);
                sgst += (Double.parseDouble(obj.getTot()) * Double.parseDouble(obj.getSgst()))/100;
                cgst += (Double.parseDouble(obj.getTot()) * Double.parseDouble(obj.getCgst()))/100;
                igst += (Double.parseDouble(obj.getTot()) * Double.parseDouble(obj.getIgst()))/100;
            
            BigDecimal grand_total = new BigDecimal(grand_tot).setScale(2, RoundingMode.HALF_EVEN);
            txtgrand.setText(grand_total.toString());
            BigDecimal gstamtt = new BigDecimal(gstamt).setScale(2, RoundingMode.HALF_EVEN);
            txtgst.setText(gstamtt.toString());
            BigDecimal netamount = new BigDecimal(netamt).setScale(2, RoundingMode.HALF_EVEN);
            txtnet.setText(netamount.toString());
            BigDecimal sgstamt = new BigDecimal(sgst).setScale(2, RoundingMode.HALF_EVEN);
            txtsgst.setText(sgstamt.toString());
            BigDecimal cgstamt = new BigDecimal(cgst).setScale(2, RoundingMode.HALF_EVEN);
            txtcgst.setText(cgstamt.toString());
            BigDecimal igstamt = new BigDecimal(igst).setScale(2, RoundingMode.HALF_EVEN);
            txtigst.setText(igstamt.toString());
            }
          }
      }
    
    private boolean savebill() throws SQLException {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            String type="";
            Calendar cal = Calendar.getInstance();
            Date date=cal.getTime();
            Date time=cal.getTime();
            DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate=dateformat.format(date);
            String formattedtime=timeformat.format(time);
            String wh = "";
                if (rbtKL.isSelected()) {
                    wh ="KERALA";
                }else if (rbtMH.isSelected()) {
                    wh="MAHARASTRA";
                }else if (rbtTN.isSelected()) {
                    wh ="TAMILNADU";
                }
            System.out.println("insert into " + db.schema + ".tbl_mass_bill values(null,'" + formattedDate + "','"+formattedtime+"'," + lblcount.getText() + ","+txtsgst.getText()+"," + txtcgst.getText() + ","+txtigst.getText()+","+txtgrand.getText()+","+ txtgst.getText() +","+txtnet.getText()+",'"+ wh+"','"+cmbagent.getSelectionModel().getSelectedItem()+"')");
            String query = "insert into " + db.schema + ".tbl_mass_bill values(null,'" + formattedDate + "','"+formattedtime+"','" + lblcount.getText() + "',"+txtsgst.getText()+"," + txtcgst.getText() + ","+txtigst.getText()+","+txtgrand.getText()+","+ txtgst.getText() +","+txtnet.getText()+",'"+ wh +"','"+cmbagent.getSelectionModel().getSelectedItem()+"')";
            int bool = st.executeUpdate(query);
            if (bool > 0) {
                flag = true;
            }
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    private boolean table_invalid() {
        boolean flag = true;
        int count = 0;
        for (objtbl obj : table_data) {
            count++;
            if (obj.getOdid().equals("") || obj.getCode().equals("") || obj.getDesc().equals("") || obj.getRate().equals("")  || obj.getQty().equals("") || obj.getGst().equals("") || obj.getGst().equals("") || obj.getTot().equals("")) {
                if (obj.getOdid().equals("") && obj.getCode().equals("") && obj.getDesc().equals("") && obj.getRate().equals("")  && obj.getQty().equals("") && obj.getGst().equals("") && obj.getGst().equals("") && obj.getTot().equals("") && count != 1) {
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
    @FXML
    private void saveonaction(ActionEvent event) { 
        if (table_invalid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                tbldata.requestFocus();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save this Bills", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
               try {
            boolean transaction_status = false;
            db.con.setAutoCommit(false);  
                    if (save_POitems()) {
                        savebill();
                        if (updateStock()) {     
                        transaction_status = true;
                        db.con.commit();
                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Bills Saved Successfully", ButtonType.YES, ButtonType.NO);
                            alert1.showAndWait();
                                if (alert1.getResult() == ButtonType.YES) {
                                    
                                }
                    }
                    }
            if (!transaction_status) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Database Transaction Error: Cannot Save the bills", ButtonType.YES, ButtonType.NO);
                            alert2.showAndWait();
                                if (alert2.getResult() == ButtonType.YES) {
                                    db.con.rollback();
                                }
                
            }
            db.con.setAutoCommit(true);
        }
        catch (SQLException ex) {
            System.out.println("catc======"+ex);
            Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        }
            
             
    }
    

    @FXML
    private void clearonaction(ActionEvent event) {
    }

    @FXML
    private void importonaction(ActionEvent event) {
        boolean transaction_status = false;
            try {
                  db.con.setAutoCommit(false);
                  if (checktbl()) {
                        if (importExecl()) {
                            if (populate_table()) {
                                db.con.commit();
                        transaction_status = true;  
                            } 
                            
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
                  Logger.getLogger(ExcelBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
