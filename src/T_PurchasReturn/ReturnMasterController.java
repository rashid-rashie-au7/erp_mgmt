/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_PurchasReturn;


import database.DBMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ReturnMasterController implements Initializable {

    @FXML
    private HBox hbx_main;
    @FXML
    private SplitPane sp_main;
    @FXML
    private AnchorPane anchr_sidebar;
    @FXML
    private Accordion accr_option;
    @FXML
    private TitledPane tp_options;
    @FXML
    private AnchorPane anchr_options;
    @FXML
    private VBox vbx_btn;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrint;
    @FXML
    private AnchorPane anchr_main;
    @FXML
    private VBox vbxMain;
    @FXML
    private Accordion accrSearch;
    @FXML
    private TitledPane tpSearch;
    @FXML
    private AnchorPane anchr_search;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnShow;
    @FXML
    private Button btnHide;
    @FXML
    private Button btnClear;
    @FXML
    private AnchorPane anchrAdvSearch;
    @FXML
    private TextField txtPoid;
    @FXML
    private TextField txtRetunID;
    @FXML
    private TextField txtSupplier;
    @FXML
    private DatePicker dpDate;
    @FXML
    private RadioButton rbtOpen;
    @FXML
    private RadioButton rbtBoth;
    @FXML
    private RadioButton rbtclose;
    @FXML
    private Accordion accrtable;
    @FXML
    private TitledPane tpTable;
    @FXML
    private AnchorPane anchrTable;
    @FXML
    private TableView<objPO> tblView;
    @FXML
    private TableColumn colreturnid;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn colinvoice;
    @FXML
    private TableColumn colsup;
    @FXML
    private TableColumn coltot;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colnet;
    private final Stage stage_add_po = new Stage(StageStyle.DECORATED);
    private final Stage stageEditPO = new Stage(StageStyle.DECORATED);
    private Stage stage = new Stage();
    public static BooleanProperty boolean_status = new SimpleBooleanProperty();
    public static ObservableList<objPO> table_data = FXCollections.observableArrayList();
    public static ObservableList<objPO> table_data_search = FXCollections.observableArrayList();
    private final DBMySQL db = new DBMySQL();
    Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        rbtBoth.setSelected(true);
    
        load_titled_pane_icon();
        set_buttons();
        set_table();
        populate_table();
        listner_boolean();
        listner_radio_selection();
//        populate_table_open();
//        populate_tbl_closed();
        listner_search();
        listner_adv_search_po_id();
        listner_adv_search_suplier();
        listner_adv_search_date();
        setLayout();
        tpTable.setText(tblView.getItems().size() + " PO Found");
        tblView.itemsProperty().addListener(new ChangeListener<ObservableList<objPO>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<objPO>> ov, ObservableList<objPO> t, ObservableList<objPO> t1) {
                tpTable.setText(tblView.getItems().size() + " PO Found");
            }
        });
        doubleClickTableEdit();
        enterKeyEventOnTableView();
    }    

    private void set_buttons() {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
        dpDate.setValue(LocalDate.now());
    }
    private void load_titled_pane_icon() {
//        tpSearch.setExpanded(true);
        accrSearch.setExpandedPane(tpSearch);
        tpSearch.setAnimated(true);
        tpTable.setAnimated(true);
        accr_option.setExpandedPane(tp_options);
        accrtable.setExpandedPane(tpTable);
        tpSearch.setAnimated(true);
        
    }

    private void set_table() {
        colid.setCellValueFactory(new PropertyValueFactory<objPO, String>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<objPO, String>("date"));
        colinvoice.setCellValueFactory(new PropertyValueFactory<objPO, String>("invoice"));
        colsup.setCellValueFactory(new PropertyValueFactory<objPO, String>("sup"));
        coltot.setCellValueFactory(new PropertyValueFactory<objPO, String>("tot"));
        colgst.setCellValueFactory(new PropertyValueFactory<objPO, String>("gst"));
        colnet.setCellValueFactory(new PropertyValueFactory<objPO, String>("net"));
        table_data.add(new objPO("", "", "", "", "", "", ""));
        tblView.setItems(table_data);

    }

    public void populate_table() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_po where status  = 1");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_po where status  = 1");
            while (rs.next()) {
                
                table_data.add(new objPO(rs.getString("code"), rs.getString("date"), rs.getString("invoice"), rs.getString("suplier"), rs.getString("grandtot"), rs.getString("gst"), rs.getString("total")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listner_boolean() {
        boolean_status.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                populate_table();
            }
        });
    }

    public void clear() {
        
        txtPoid.clear();
        txtSearch.clear();
        dpDate.setValue(LocalDate.now());
        rbtBoth.setSelected(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        populate_table();
    }

    private void listner_radio_selection() {
        ToggleGroup grp =new ToggleGroup();
        rbtOpen.setToggleGroup(grp);
        rbtclose.setToggleGroup(grp);
        rbtBoth.setToggleGroup(grp);
        rbtOpen.setUserData("open");
        rbtclose.setUserData("closed");
        rbtBoth.setUserData("open_closed");

        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                if (rbtBoth.isSelected()) {
                    populate_table();
                    tpTable.setText(table_data.size() + " Purchase Order Found");
                }
                else if (rbtclose.isSelected()) {
                    populate_tbl_closed();
                 tpTable.setText(table_data.size() + " Purchase Order Found");
                }
                else if (rbtOpen.isSelected()) {
                    populate_table_open();
                    tpTable.setText(table_data.size() + " Purchase Order Found");
                }
            }
        });
    }

    private void populate_tbl_closed() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_po where po_status = 1 AND status  = 1");
            while (rs.next()) {
               
                table_data.add(new objPO(rs.getString("code"), rs.getString("date"), rs.getString("invoice"), rs.getString("suplier"), rs.getString("grandtot"), rs.getString("gst"), rs.getString("total")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void populate_table_open() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_po where  status  = 1");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_po where status  = 1");
            while (rs.next()) {
                if (rs.getInt("po_status") == 0) {
                    status = "Open";
                }
                else {
                    status = "Closed";
                }
                table_data.add(new objPO(rs.getString("code"), rs.getString("date"), rs.getString("invoice"), rs.getString("suplier"), rs.getString("grandtot"), rs.getString("gst"), rs.getString("total")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listner_search() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtSearch.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objPO obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase()) || obj.getInvoice().toLowerCase().contains(text.toLowerCase())  || obj.getSup().toLowerCase().contains(text.toLowerCase()) || obj.getTot().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " PO Found");
                }
            }
        });
    }

    private void listner_adv_search_po_id() {
        txtPoid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtPoid.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objPO obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " PO Found");
                }
            }
        });
    }

    private void listner_adv_search_suplier() {
        txtSupplier.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtSupplier.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objPO obj : table_data) {
                        if (obj.getSup().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " PO Found");
                }
            }
        });
    }

    private void listner_adv_search_date() {
        dpDate.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                table_data.removeAll(table_data);
        
        try {
            
            String status = "";
        if(rbtBoth.isSelected()){
            status = "date='"+dpDate.getValue()+"' and status  = 1";
        }else if(rbtOpen.isSelected()){
            status = " date='"+dpDate.getValue()+"' and status  = 1 and po_status = 0 ";
        }else if(rbtclose.isSelected()){
            status = " date='"+dpDate.getValue()+"' and status  = 1 and po_status='1' ";
        }
            Statement st = db.con.createStatement();
            
            System.out.println("select * from " + db.schema + ".tbl_po where "+status+"");
            System.out.println("date listner");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_po where "+status+"");
            while (rs.next()) {
               
                table_data.add(new objPO(rs.getString("code"), rs.getString("date"), rs.getString("invoice"), rs.getString("suplier"), rs.getString("grandtot"), rs.getString("gst"), rs.getString("total")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
                      
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Bills Found");
                
            }
        });
    }

    private void load_fxml() {
        try {
            if (stageEditPO.isIconified()) {
                stageEditPO.setIconified(false);
            }
            else if (!stageEditPO.isShowing()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Returns.fxml"));
                Parent root = (Parent) loader.load();
                ReturnsController cpc = loader.getController();
                Scene scene = new Scene(root);
                stageEditPO.setScene(scene);
                stageEditPO.setTitle("Return Purchase " + tblView.getSelectionModel().getSelectedItem().getId());
                stageEditPO.setResizable(false);
                cpc.setStage(stageEditPO);
                cpc.fetch_for_update(tblView.getSelectionModel().getSelectedItem().getId());
                stageEditPO.show();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setLayout() {
        tpSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!tpSearch.isExpanded()) {
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), accrtable);
                    translateTransition.setToY(-210);
                    translateTransition.play();

                    accrtable.setPrefHeight(rectangle2D.getHeight() - anchrAdvSearch.getHeight());
                    tblView.setPrefHeight(rectangle2D.getHeight() -  anchrAdvSearch.getHeight());
                }
                else if (tpSearch.isExpanded()) {
                    if (anchrAdvSearch.getOpacity() == 1) {
                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), accrtable);
                        translateTransition.setToY(0);
                        translateTransition.play();

                        accrtable.setPrefHeight(rectangle2D.getHeight() - 50);
                        tblView.setPrefHeight(rectangle2D.getHeight() - 50);
                    }
                    else if (anchrAdvSearch.getOpacity() == 0) {
                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), accrtable);
                        translateTransition.setToY(-210);
                        translateTransition.play();

                        accrtable.setPrefHeight(rectangle2D.getHeight() - 150);
                        tblView.setPrefHeight(rectangle2D.getHeight() -  150);
                    }
                }
            }
        });
    }
    
    private void doubleClickTableEdit() {
        tblView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!tblView.getSelectionModel().isEmpty() && t.getClickCount() == 2) {
                    btnEdit.fire();
                }
            }
        });
    }

    private void enterKeyEventOnTableView() {
        tblView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER && !tblView.getSelectionModel().isEmpty()) {
                    btnEdit.fire();
                    t.consume();
                }
            }
        });
    }

    @FXML
    private void btnedit_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a Purchase Order from table",ButtonType.OK);
            alert.showAndWait();
              if(alert.getResult() == ButtonType.OK){
              tblView.requestFocus(); 
           }
        }
        
        else {
            load_fxml();
        }
    }

    @FXML
    private void btndelete_onaction(ActionEvent event) {
    }

    @FXML
    private void btnprint_onaction(ActionEvent event) {
    }

    @FXML
    private void btnshow_onaction(ActionEvent event) {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
        txtPoid.requestFocus();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), anchrAdvSearch);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), accrtable);
        translateTransition.setFromY(accrtable.getTranslateY());
        translateTransition.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
        parallelTransition.play();

        accrtable.setPrefHeight(rectangle2D.getHeight() - 50);
        tblView.setPrefHeight(rectangle2D.getHeight() - 50);
    }

    @FXML
    private void btnhide_onaction(ActionEvent event) {
        btnHide.setVisible(false);
        btnShow.setVisible(true);
        txtSearch.requestFocus();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), anchrAdvSearch);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), accrtable);
        translateTransition.setFromY(accrtable.getTranslateY());
        translateTransition.setToY(-150);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
        parallelTransition.play();

        accrtable.setPrefHeight(rectangle2D.getHeight()  - 100);
        tblView.setPrefHeight(rectangle2D.getHeight() -  100);
    }

    @FXML
    private void btnClear_onaction(ActionEvent event) {
         clear();
    }

    public void setStage(Stage stage_portn) {
        this .stage= stage_portn;
    }
    
}
