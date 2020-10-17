/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_SalesReturn;

import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ReturnItemsController implements Initializable {

    @FXML
    private TableView<objitems> tblview;
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
    public ObservableList<objitems> table_data = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        set_table();
        populatetable("");
    }   
    
    private void set_table() {
        colbar.setCellValueFactory(new PropertyValueFactory<objitems, String>("bar"));
        colcode.setCellValueFactory(new PropertyValueFactory<objitems, String>("code"));
        colname.setCellValueFactory(new PropertyValueFactory<objitems, String>("name"));
        colrate.setCellValueFactory(new PropertyValueFactory<objitems, String>("rate"));
        colqty.setCellValueFactory(new PropertyValueFactory<objitems, String>("qty"));
        colgst.setCellValueFactory(new PropertyValueFactory<objitems, String>("gst"));
        colgstamt.setCellValueFactory(new PropertyValueFactory<objitems, String>("gstamt"));
        coltot.setCellValueFactory(new PropertyValueFactory<objitems, String>("tot")); 
        table_data.add(new objitems("", "", "", "", "", "", "",""));
        tblview.setItems(table_data);

    }

   public void populatetable(String code) {
       table_data.clear();
        try {

            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_sales_rtn_items where rt_code='"+code+"'");
            while (rs.next()) {
                table_data.add(new objitems(rs.getString("barcode"), rs.getString("code"), rs.getString("name"), rs.getString("rate"),rs.getString("qty"), rs.getString("tot"), rs.getString("gstamt"),rs.getString("netamt")));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

   public void setStage(Stage stage_Sub) {
        this.stage = stage_Sub;
    }
    
}
