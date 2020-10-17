/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Purchase;


import com.miw.control.sbox.SuggessionBox;
import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class Create_POController implements Initializable {

    @FXML
    private TextField txtPoID;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtTime;
    @FXML
    private RadioButton rbtOpen;
    @FXML
    private RadioButton rbtClose;
    @FXML
    private TextField txtInvoice;
    @FXML
    private DatePicker dpCloseDate;
    @FXML
    private TextField txtCloseTime;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtmob;
    @FXML
    private HBox hbxSupplier;
    @FXML
    public  TableView<objitem> tblView;   
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
    private TextField txttot;
    @FXML
    private Button btnsave;
    @FXML
    private Button btnclear;
    @FXML
    private Button btnupdate;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private TextField txtgstno;
    @FXML
    private TextField txtgstamt;
    public SuggessionBox sbxSup;
    ObservableList<String> listsup = FXCollections.observableArrayList();
    @FXML
    private Label lblClose;
    private static DecimalFormat df = new DecimalFormat(".##");
    @FXML
    private TableColumn coltot;
    public ObservableList<objitem> table_data = FXCollections.observableArrayList(objitem ->
    new Observable[]{
        objitem.itemProperty(),objitem.qtyProperty(),
        objitem.rateProperty(),objitem.sgstProperty(),
        objitem.cgstProperty(),objitem.igstProperty(),
        objitem.gstProperty(),objitem.totProperty(),objitem.totalProperty()}
    );
    @FXML
    private Hyperlink hplRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        Setcode(); 
        set_table();
        setbuttons();
        clear();
        listner_txtbx();
        listner_sbxSup();
        tbllistner();
    }    

    private void listner_txtbx(){
        txtInvoice.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    rbtOpen.requestFocus();
                }
            }
        });
        rbtOpen.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxSup.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtClose.setSelected(true);
                    rbtClose.requestFocus();
                    if(rbtClose.isSelected())
                    {
                        dpCloseDate.setVisible(true);
                        txtCloseTime.setVisible(true);
                        lblClose.setVisible(true);
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        System.out.println( sdf.format(cal.getTime()) );
                        txtCloseTime.setText(sdf.format(cal.getTime()));
                    }
                }
            }
        });
        rbtClose.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxSup.requestFocus();
                }else if(t.getCode()==KeyCode.SPACE){
                    rbtOpen.setSelected(true);
                    rbtOpen.requestFocus();
                    if(rbtOpen.isSelected())
                    {
                        dpCloseDate.setVisible(false);
                        txtCloseTime.setVisible(false);
                        lblClose.setVisible(false);
                    }
                }
            }
        });
        rbtClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (rbtClose.isSelected()) {
                   dpCloseDate.setVisible(true);
                   txtCloseTime.setVisible(true);
                   lblClose.setVisible(true);
                   Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    System.out.println( sdf.format(cal.getTime()) );
                    txtCloseTime.setText(sdf.format(cal.getTime()));
                }
            }
        });
        
        rbtOpen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (rbtOpen.isSelected()) {
                   dpCloseDate.setVisible(false);
                   txtCloseTime.setVisible(false); 
                   lblClose.setVisible(false);
                }
            }
        });
       
    }
    
    private void clear(){
        ToggleGroup grp =new ToggleGroup();
        rbtOpen.setToggleGroup(grp);
        rbtClose.setToggleGroup(grp);
        rbtOpen.setSelected(true);
        if(rbtOpen.isSelected())
            {
              dpCloseDate.setVisible(false);
              txtCloseTime.setVisible(false);
              lblClose.setVisible(false);
            }
        sbxSup.clear();
        txtmob.clear();
        txtCity.clear();
        txtState.clear();       
        txtgstno.clear();
        txtsgst.setText("0.0");
        txtcgst.setText("0.0");
        txtigst.setText("0.0");
        txtgrandtot.setText("0.0");
        txtgstamt.setText("0.0");
        txttot.setText("0.0");
        dpDate.setValue(LocalDate.now());
        dpCloseDate.setValue(LocalDate.now());
        txtInvoice.clear();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );
        txtTime.setText(sdf.format(cal.getTime()));
        Setcode();
        
        table_data.clear();
        table_data.add(new objitem("", "", "", "", "", "","","","",""));
//        set_total();
    }
    
    private void setbuttons(){       
        sbxSup = new SuggessionBox();
        hbxSupplier.getChildren().add(sbxSup);
        sbxSup.setPrefSize(201, 25);
        btnupdate.setVisible(false);
    }
    
    private void listner_sbxSup() {
        sbxSup.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listsup.removeAll(listsup);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_supplier where status = 1");
                        while (rs.next()) {
                            listsup.add(rs.getString("name"));
                        }
                        sbxSup.setData(listsup);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        sbxSup.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   setvalue();
                   tblView.edit(table_data.size() - 1, colitem);
                }
            }
        });
    }
    
      
    private void tbllistner(){
        
        table_data.addListener((ListChangeListener.Change<? extends objitem> c) -> {
           while (c.next()) {
               if (c.wasAdded()) {
                   System.out.println("Added:");
                   set_total();
                   c.getAddedSubList().forEach(System.out::println);
                   
                   System.out.println();
               }
               if (c.wasRemoved()) {
                   System.out.println("Removed:");
                   c.getRemoved().forEach(System.out::println);
                   set_total();
                   System.out.println();
               }
               if (c.wasUpdated()) {
                   System.out.println("Updated:");
                   table_data.subList(c.getFrom(), c.getTo()).forEach(System.out::println);
                   set_total();
                   System.out.println();
               }
           }
        });
    }
    
    public void set_total() {
        Double sum = 0.0;
        Double gst = 0.0;
        Double grand = 0.0;
        Double sgst=0.0;
        Double igst=0.0;
        Double cgst=0.0;

        for (int i = 0; i < tblView.getItems().size(); i++) {
            objitem sm = tblView.getItems().get(i);
            Double amt = Double.parseDouble(sm.getQty()) * Double.parseDouble(sm.getRate());
            System.out.println("rate=================="+sm.getRate());
            Double gstamt = Double.parseDouble(sm.getGst());
            Double grandtot = Double.parseDouble(sm.getTotal());
            gst=gst+gstamt;
            grand=grand+grandtot;
            sum=sum+amt;
            sgst = Double.parseDouble(sm.getTot()) * Double.parseDouble(sm.getSgst())/100;
            cgst = Double.parseDouble(sm.getTot()) * Double.parseDouble(sm.getCgst())/100;
            igst = Double.parseDouble(sm.getTot()) * Double.parseDouble(sm.getIgst())/100;
        }
        if (table_data.size() > 0) {
               txttot.setText(df.format(grand));
               txtgstamt.setText(df.format(gst));
               txtgrandtot.setText(df.format(sum));
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
    
     private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select city,state,mob,gst from " + db.schema + ".tbl_supplier where name = '" + sbxSup.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select  city,state,mob,gst from " + db.schema + ".tbl_supplier where name = '" + sbxSup.getText() + "' ");
                while (rst.next()) {
                    txtCity.setText(rst.getString("city"));
                    txtState.setText(rst.getString("state"));
                    txtmob.setText(rst.getString("mob"));
                    txtgstno.setText(rst.getString("gst"));
                    
                }
               }
        } catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Setcode() throws NumberFormatException {
        Integer pr = Integer.parseInt(setId()) + 1;
        String no = String.format("%04d", pr);
        txtPoID.setText("PR"+no);
        sbxSup.requestFocus();
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select count(code), max(code) FROM " + db.schema + ".tbl_po");
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_po");
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
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
   private void set_table() {

        Callback<TableColumn, TableCell> sbxItem = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new SboxCellItem();
            }
        };
        colitem.setCellValueFactory(new PropertyValueFactory<objitem, String>("item"));
        colitem.setCellFactory(sbxItem);
        colitem.setEditable(true);
        
        Callback<TableColumn, TableCell> cellFactoryQty = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextfieldQty();
            }
        };
        colqty.setCellValueFactory(new PropertyValueFactory<objitem, String>("qty"));
        colqty.setCellFactory(cellFactoryQty);
        colqty.setEditable(true);
        
        Callback<TableColumn, TableCell> cellFactoryUom = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextfieldUom();
            }
        };
        coluom.setCellValueFactory(new PropertyValueFactory<objitem, String>("uom"));
        coluom.setCellFactory(cellFactoryUom);
        
        Callback<TableColumn, TableCell> cellFactoryRate = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextfieldRate();
            }
        };
        colrate.setCellValueFactory(new PropertyValueFactory<objitem, String>("rate"));
        colrate.setCellFactory(cellFactoryRate);
        colrate.setEditable(true);
        
        Callback<TableColumn, TableCell> cellFactorySgst = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextfieldSgst();
            }
        };
        colsgst.setCellValueFactory(new PropertyValueFactory<objitem, String>("sgst"));
        colsgst.setCellFactory(cellFactorySgst);
        colsgst.setEditable(true);
        
        Callback<TableColumn, TableCell> cellFactoryCgst = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextfieldCgst();
            }
        };
        colcgst.setCellValueFactory(new PropertyValueFactory<objitem, String>("cgst"));
        colcgst.setCellFactory(cellFactoryCgst);
        colcgst.setEditable(true);
        
        Callback<TableColumn, TableCell> cellFactoryIgst = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextFieldIgst();
            }
        };
        coligst.setCellValueFactory(new PropertyValueFactory<objitem, String>("igst"));
        coligst.setCellFactory(cellFactoryIgst);
        coligst.setEditable(true);
        coltot.setCellValueFactory(new PropertyValueFactory<objitem, String>("tot"));
        colgst.setCellValueFactory(new PropertyValueFactory<objitem, String>("gst"));
        coltotal.setCellValueFactory(new PropertyValueFactory<objitem, String>("total"));
        tblView.setEditable(true);
        table_data.add(new objitem("", "", "", "", "", "","","","",""));
        tblView.setItems(table_data);
        tblView.getSelectionModel().setCellSelectionEnabled(true);
    }
   
   

    public void fetch_for_update(String id) {
        btnsave.setVisible(false);
        btnupdate.setVisible(true);
            try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_po where code = '" + id + "'");
            if (rs.next()) {
                txtPoID.setText(id);
                dpDate.setValue(rs.getDate("date").toLocalDate());
                txtTime.setText(rs.getString("time"));
                txtInvoice.setText(rs.getString("invoice"));
                if(rs.getString("po_status").equals("0")){
                    rbtOpen.setSelected(true);
                }else if (rs.getString("po_status").equals("1")){
                    rbtClose.setSelected(true);
                    dpCloseDate.setValue(rs.getDate("c_date").toLocalDate());              
                    txtCloseTime.setText(rs.getString("c_time"));
                }
                sbxSup.setText(rs.getString("suplier"));               
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
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_data.removeAll(table_data);
        try {
            Statement st1 = db.con.createStatement();
            System.out.println("select *from " + db.schema + ".tbl_po_items  where  code = '" + id + "'");
            ResultSet rs = st1.executeQuery("select *from " + db.schema + ".tbl_po_items  where  code = '" + id + "'");
            while (rs.next()) {
                table_data.add(new objitem(rs.getString("item"), rs.getString("qty"), rs.getString("unit"), rs.getString("rate"), rs.getString("sgst"), rs.getString("cgst"), rs.getString("igst"), rs.getString("grandtot"),rs.getString("gst"), rs.getString("total")));
            }
            st1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
     private void remove_row() {
        System.out.println("inside remove");
        int row = tblView.getSelectionModel().getSelectedIndex();
        if (table_data.size() > 1) {
            System.out.println("rooowwww  " + row);
            table_data.remove(row);
            set_total();
            tblView.getSelectionModel().clearSelection();
        } else {
            table_data.set(row, new objitem("", "", "", "","","","","","",""));
            set_total();
            tblView.getSelectionModel().clearSelection();
        }
    }
     
     private boolean table_invalid() {
        boolean flag = true;
        int count = 0;
        for (objitem obj : table_data) {
            count++;
            if (obj.getItem().equals("") || obj.getQty().equals("") || obj.getUom().equals("") || obj.getRate().equals("")  || obj.getSgst().equals("") || obj.getCgst().equals("") || obj.getIgst().equals("") || obj.getTot().equals("")|| obj.getGst().equals("") || obj.getTotal().equals("")) {
                if (obj.getItem().equals("") && obj.getQty().equals("") && obj.getUom().equals("") && obj.getRate().equals("")  && obj.getSgst().equals("") && obj.getCgst().equals("") && obj.getIgst().equals("") && obj.getTot().equals("") && obj.getGst().equals("") && obj.getTotal().equals("") && count != 1) {
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
     
     private boolean savePO() throws SQLException {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            String type="";
            if(rbtOpen.isSelected()){
                type="0";
            }else if(rbtClose.isSelected()){
                type="1";
            }
            
            System.out.println("insert into " + db.schema + ".tbl_po values(null,'" + txtPoID.getText() + "','"+dpDate.getValue()+"','" + txtTime.getText() + "','" + txtInvoice.getText() + "',"+type+",'"+dpCloseDate.getValue()+"','" + txtCloseTime.getText() + "','" + sbxSup.getText() + "',"+txtsgst.getText()+","+txtcgst.getText()+","+ txtigst.getText() +","+ txtgrandtot.getText() +","+txtgstamt.getText()+","+ txttot.getText() +",1 )");
            String query = "insert into " + db.schema + ".tbl_po values(null,'" + txtPoID.getText() + "','"+dpDate.getValue()+"','" + txtTime.getText() + "','" + txtInvoice.getText() + "',"+type+",'"+dpCloseDate.getValue()+"','" + txtCloseTime.getText() + "','" + sbxSup.getText() + "',"+txtsgst.getText()+","+txtcgst.getText()+","+ txtigst.getText() +","+ txtgrandtot.getText() +","+txtgstamt.getText()+","+ txttot.getText() +",1 )";
            int bool = st.executeUpdate(query);
            if (bool > 0) {
                save_POitems(txtPoID.getText());
                flag = true;
            }
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    private boolean save_POitems(String poid) {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objitem obj : table_data) {
                if (obj.getItem().equals("") && obj.getQty().equals("") && obj.getUom().equals("") && obj.getRate().equals("")  && obj.getSgst().equals("") && obj.getCgst().equals("") && obj.getIgst().equals("") && obj.getTot().equals("")&& obj.getGst().equals("") && obj.getTotal().equals(""))  {
                    continue;
                }
                Double tot=0.0;
                tot= Double.parseDouble(obj.getRate()) * Double.parseDouble(obj.getQty());
                System.out.println("insert into " + db.schema + ".tbl_po_items values(null, '" + poid + "', '" + obj.getItem()+ "', '" + obj.getQty()+ "', '" +  obj.getUom()+ "', " + obj.getRate()+ "," +  obj.getSgst()+ ", " + obj.getCgst() + "," +  obj.getIgst()+ ", " + obj.getTot()+ "," + obj.getGst()+ "," +  obj.getTotal()+ ", 1)");
                bool = st.executeUpdate("insert into " + db.schema + ".tbl_po_items values(null, '" + poid + "', '" + obj.getItem()+ "', '" + obj.getQty()+ "', '" +  obj.getUom()+ "', " + obj.getRate()+ "," +  obj.getSgst()+ ", " + obj.getCgst() + "," +  obj.getIgst()+ ", " + obj.getTot()+ "," + obj.getGst()+ "," +  obj.getTotal()+ ", 1)");
            }
            if (bool > 0) {
                flag = true;    
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    private boolean updateStock() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objitem obj : table_data) {
                if (obj.getItem().equals("") && obj.getQty().equals("") && obj.getUom().equals("") && obj.getRate().equals("")  && obj.getSgst().equals("") && obj.getCgst().equals("") && obj.getIgst().equals("") && obj.getTot().equals("")&& obj.getGst().equals("") && obj.getTotal().equals(""))  {
                    continue;
                }
            System.out.println("update " + db.schema + ".tbl_item_master set qty =qty +"+obj.getQty()+"  where name ='" + obj.getItem()+ "' ");
            bool = st.executeUpdate("update " + db.schema + ".tbl_item_master set qty = qty +"+obj.getQty()+"  where name ='" + obj.getItem()+ "'");

            }
            if (bool > 0) {
                flag = true;
               
                }
                
            
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
        
    }

    @FXML
    private void btnsave_onaction(ActionEvent event) throws SQLException {
        if (table_invalid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                tblView.requestFocus();
            }
        } else if (sbxSup.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select A Supplier", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                sbxSup.requestFocus();
            }
        } else {    
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save this Purchase", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
               savePO();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Purchase Sucessfully Saved ", ButtonType.YES, ButtonType.NO);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.YES) {
                  clear();
                    PurchaseMasterController pmc =new PurchaseMasterController();
                    pmc.populate_table();
                }
            }
        }
    }
    

    @FXML
    private void clear_onaction(ActionEvent event) {
        clear();
    }
    
    private void update_po_open() {
        try {
            boolean transaction_status = false;
            db.con.setAutoCommit(false);  
            if (update_po_open_db(txtPoID.getText(), dpDate.getValue().toString(), txtTime.getText(), txtInvoice.getText() , sbxSup.getText() ,txtsgst.getText(),txtcgst.getText(),txtigst.getText(),txtgrandtot.getText(),txtgstamt.getText(),txttot.getText())) {
            if (delete_po_open_db_items(txtPoID.getText())) {
                    if (save_POitems(txtPoID.getText())) {
                        transaction_status = true;
                        db.con.commit();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Purchase Order updated successfully", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.YES) {
                                    clear();  
                                }
                    PurchaseMasterController.boolean_status.set(!PurchaseMasterController.boolean_status.getValue());
                    PurchaseMasterController pmc =new PurchaseMasterController();
                    pmc.populate_table();
                    }
                }
            }
            if (!transaction_status) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database Transaction Error: Cannot update Purchae Order open", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.YES) {
                                    db.con.rollback();
                                }
                
            }
            db.con.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update_po_closed() {
        try {
            boolean transaction_status = false;
            db.con.setAutoCommit(false);
            if (update_po_closed_db(txtPoID.getText(),dpDate.getValue().toString(), txtTime.getText(),txtInvoice.getText(), dpCloseDate.getValue().toString(), txtCloseTime.getText(),sbxSup.getText(),txtsgst.getText(),txtcgst.getText(),txtigst.getText(),txtgrandtot.getText(),txtgstamt.getText(),txttot.getText())) {
                if (delete_po_open_db_items(txtPoID.getText())) {
                    if (save_POitems(txtPoID.getText())) {
                        if (updateStock()) {
                            transaction_status = true;
                            db.con.commit();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Purchase Order Closed Successfully", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.YES) {
                                   clear();
                                }
                            PurchaseMasterController.boolean_status.set(!PurchaseMasterController.boolean_status.getValue());
                              PurchaseMasterController pmc =new PurchaseMasterController();
                                pmc.populate_table();
                        }
                    }
                }
            }
            if (!transaction_status) {        
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database Transaction Error: Cannot update Purchae Order closed", ButtonType.OK);
                            alert.showAndWait();
                                if (alert.getResult() == ButtonType.OK) {
                                    db.con.rollback();
                                }
            }
            db.con.setAutoCommit(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean update_po_open_db(String po_id, String date, String time, String invoice, String sup,String sgst, String cgst, String igst, String grand,String gst,String tot) {
        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_po set date= '" + date + "', time = '"+time+"', invoice = '" + invoice + "', suplier = '" + sup + "', po_status = 0,sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grandtot ="+grand+",gst="+gst+",total="+tot+" where code = '" + po_id + "'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_po set date= '" + date + "', time = '"+time+"', invoice = '" + invoice + "', suplier = '" + sup + "', po_status = 0,sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grandtot ="+grand+",gst="+gst+",total="+tot+" where code = '" + po_id + "'");
            if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
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
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    private boolean update_po_closed_db(String po_id, String date, String time, String invoice,String cdate,String ctime, String sup,String sgst, String cgst, String igst, String grand,String gst,String tot) {

        boolean flag = false;
        try {
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_po set date= '" + date + "', time = '"+time+"', invoice = '" + invoice + "', c_date = '" + cdate + "', c_time = '" + ctime + "', suplier = '" + sup + "', po_status = 1,sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grandtot ="+grand+",gst="+gst+",total="+tot+" where code = '" + po_id + "'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_po set date= '" + date + "', time = '"+time+"', invoice = '" + invoice + "', c_date = '" + cdate + "', c_time = '" + ctime + "', suplier = '" + sup + "', po_status = 1,sgst ="+sgst+",cgst ="+cgst+", igst ="+igst+",grandtot ="+grand+",gst="+gst+",total="+tot+" where code = '" + po_id + "'");
            if (bool > 0) {
                flag = true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(Create_POController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    @FXML
    private void btnupdate_onaction(ActionEvent event) {
        if (sbxSup.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select A Supplier", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                sbxSup.requestFocus();
            }
        }
        else if (rbtOpen.isSelected()) {
            if (table_invalid()) {
              Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
               tblView.requestFocus();
            }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update Purchase Order open ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
               update_po_open();
               stage.close();
            }
            }
        }
        else if (rbtClose.isSelected()) {
             if (table_invalid()) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
               tblView.requestFocus();
            }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update Purchase Order Close ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                update_po_closed();
                stage.close();
            }
            }
        }
    }
    
    
     public void setStage(Stage stage_add_po) {
       this.stage= stage_add_po;
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

    @FXML
    private void removeOnaction(ActionEvent event) {
        remove_row();
    }
}
