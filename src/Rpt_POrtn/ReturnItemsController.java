/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_POrtn;


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

    
    public ObservableList<objitems> table_data = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private TableView<objitems> tblView;
    @FXML
    private TableColumn colitem;
    @FXML
    private TableColumn coluom;
    @FXML
    private TableColumn colsgst;
    @FXML
    private TableColumn colcgst;
    @FXML
    private TableColumn coligst;
    @FXML
    private TableColumn coltotal;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn colrate;
    @FXML
    private TableColumn coltot;
    @FXML
    private TableColumn colgst;
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
        colitem.setCellValueFactory(new PropertyValueFactory<objitems, String>("item"));
        colqty.setCellValueFactory(new PropertyValueFactory<objitems, String>("qty"));
        coluom.setCellValueFactory(new PropertyValueFactory<objitems, String>("uom"));
        colrate.setCellValueFactory(new PropertyValueFactory<objitems, String>("rate"));
        colsgst.setCellValueFactory(new PropertyValueFactory<objitems, String>("sgst"));
        colcgst.setCellValueFactory(new PropertyValueFactory<objitems, String>("cgst"));
        coligst.setCellValueFactory(new PropertyValueFactory<objitems, String>("igst"));
        coltot.setCellValueFactory(new PropertyValueFactory<objitems, String>("tot"));
        colgst.setCellValueFactory(new PropertyValueFactory<objitems, String>("gst"));
        coltotal.setCellValueFactory(new PropertyValueFactory<objitems, String>("net"));
        table_data.add(new objitems("", "", "", "", "", "", "","","",""));
        tblView.setItems(table_data);

    }

   public void populatetable(String code) {
       table_data.clear();
        try {

            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_portn_items where code='"+code+"'");
            while (rs.next()) {
                table_data.add(new objitems(rs.getString("item"), rs.getString("qty"), rs.getString("unit"), rs.getString("rate"), rs.getString("sgst"), rs.getString("cgst"), rs.getString("igst"), rs.getString("grandtot"),rs.getString("gst"), rs.getString("total")));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(PortnController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

   public void setStage(Stage stage_Sub) {
        this.stage = stage_Sub;
    }
    
}
