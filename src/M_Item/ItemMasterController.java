
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Item;



import database.DBMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ItemMasterController implements Initializable {

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
    private Button btnAdd;
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
    private Accordion accrtable;
    @FXML
    private TitledPane tpTable;
    @FXML
    private AnchorPane anchrTable;
    @FXML
    private TableView<objitem> tblView;
    @FXML
    private TableColumn colid;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtcat;
    @FXML
    private TextField txtbrand;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colcat;
    @FXML
    private TableColumn colbrand;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn coluom;
    @FXML
    private TableColumn colretail;
    @FXML
    private TableColumn colwhole;
    private Stage stage_add_item = new Stage(StageStyle.UTILITY);
    private Stage stage_edit_item = new Stage(StageStyle.UTILITY);
    private Stage stage = new Stage();
    public static BooleanProperty boolean_status = new SimpleBooleanProperty();
    public static ObservableList<objitem> table_data = FXCollections.observableArrayList();
    public static ObservableList<objitem> table_data_search = FXCollections.observableArrayList();
    private DBMySQL db = new DBMySQL();
    Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
    @FXML
    private TableColumn colgst;


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
        set_accordian(rectangle2D);
        load_titled_pane_icon();
        set_buttons();
        set_table();
        populate_table();
        listner_boolean();
        listner_search();
        listner_adv_search_po_id();
        listner_adv_search_brand();
        listner_adv_search_cat();
        listner_adv_search_item();
        setLayout();
        tpTable.setText(tblView.getItems().size() + " Item Found");
        tblView.itemsProperty().addListener(new ChangeListener<ObservableList<objitem>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<objitem>> ov, ObservableList<objitem> t, ObservableList<objitem> t1) {
                tpTable.setText(tblView.getItems().size() + " Item Found");
            }
        });
        doubleClickTableEdit();
        enterKeyEventOnTableView();
    }    

    @FXML
    private void btnadd_onaction(ActionEvent event) {
        ItemControllerActionIdentified("Add", "");
    }

    @FXML
    private void btnedit_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Item from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {                  
                    tblView.requestFocus();
                    }    
        }
        
        else {
            ItemControllerActionIdentified("Edit", tblView.getSelectionModel().getSelectedItem().getId());
           
        }
    }

    @FXML
    private void btndelete_onaction(ActionEvent event) {
    
       if (tblView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Item from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {                  
                    tblView.requestFocus();
                    }           
        }
        else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to Delete Item!", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                   if (delete(tblView.getSelectionModel().getSelectedItem().getId())) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Item Deleted Sucessfully", ButtonType.OK);
                    alert1.showAndWait();
                    boolean_status.setValue(!boolean_status.getValue());
                }           
                    }
        }
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
    
    private void set_accordian(Rectangle2D rectangle2D1) {
        accrSearch.setPrefWidth(rectangle2D1.getWidth() - anchr_options.getPrefWidth());
        accrtable.setPrefWidth(rectangle2D1.getWidth() - anchr_options.getPrefWidth());
        tpSearch.setGraphic(new ImageView(new Image("/res/1384536324_Search.png", 24, 24, true, true)));
        tp_options.setGraphic(new ImageView(new Image("/res/1385471310_order-1.png", 24, 24, true, true)));
    }

    private void set_buttons() {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
    }

        private void ItemControllerActionIdentified(String type, String id) {
        if (!stage_add_item.isShowing()) {
            stage_add_item = new Stage(StageStyle.DECORATED);
            stage_add_item.initOwner(stage);
            stage_add_item.initModality(Modality.WINDOW_MODAL);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCreation.fxml"));
                Parent root = (Parent) loader.load();
               ItemCreationController icc = loader.getController();
                Scene scene = new Scene(root);
                stage_add_item.setScene(scene);
                stage_add_item.setResizable(false);
                stage_add_item.setTitle("Add Item");
                icc.setStage(stage_add_item);
                stage_add_item.show();
                if (type.equalsIgnoreCase("Edit")) {
                    stage_add_item.setTitle("Edit Item");
                    icc.fetch_for_update( id);
                    } else if (type.equalsIgnoreCase("Add")) {
                  }

            } catch (IOException ex) {
                Logger.getLogger(ItemMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

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
        colid.setCellValueFactory(new PropertyValueFactory<objitem, String>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<objitem, String>("name"));
        colcat.setCellValueFactory(new PropertyValueFactory<objitem, String>("cat"));
        colbrand.setCellValueFactory(new PropertyValueFactory<objitem, String>("brand"));
        colqty.setCellValueFactory(new PropertyValueFactory<objitem, String>("qty"));
        coluom.setCellValueFactory(new PropertyValueFactory<objitem, String>("uom"));
        colgst.setCellValueFactory(new PropertyValueFactory<objitem, String>("gst"));
        colretail.setCellValueFactory(new PropertyValueFactory<objitem, String>("retail"));
        colwhole.setCellValueFactory(new PropertyValueFactory<objitem, String>("whole"));
        table_data.add(new objitem("", "", "", "", "", "", "","",""));
        tblView.setItems(table_data);

    }

    public void populate_table() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,category ,brand, qty, unit,gst,rate,mrp from " + db.schema + ".tbl_item_master where status  = 1");
            ResultSet rs = st.executeQuery("select code, name,category ,brand, qty, unit,gst,rate,mrp from " + db.schema + ".tbl_item_master where status  = 1");
            while (rs.next()) {
                
                table_data.add(new objitem(rs.getString("code"), rs.getString("name"), rs.getString("category"), rs.getString("brand"), rs.getString("qty"), rs.getString("unit"),rs.getString("gst"), rs.getString("mrp"),rs.getString("rate")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ItemMasterController.class.getName()).log(Level.SEVERE, null, ex);
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
        txtcat.clear();
        txtPoid.clear();
        txtSearch.clear();
        txtname.clear();
        txtbrand.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        populate_table();
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
                    for (objitem obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase()) || obj.getName().toLowerCase().contains(text.toLowerCase()) ) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Item Found");
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
                    for (objitem obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Item Found");
                }
            }
        });
    }

    private void listner_adv_search_item() {
        txtname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtname.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objitem obj : table_data) {
                        if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Item Found");
                }
            }
        });
    }

    private void listner_adv_search_cat() {
        txtcat.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtcat.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objitem obj : table_data) {
                        if (obj.getCat().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Item Found");
                }
            }
        });
    }
    
    private void listner_adv_search_brand() {
        txtbrand.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtbrand.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objitem obj : table_data) {
                        if (obj.getBrand().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Item Found");
                }
            }
        });
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

    private boolean delete(String id) {
        try {
                Statement st = db.con.createStatement();
                System.out.println("update " + db.schema + ".tbl_item_master set status= 0 where code =  '" + id + "'");
                int bool = st.executeUpdate("update " + db.schema + ".tbl_item_master set status= 0 where code = '" + id + "'");
                if (bool > 0) {
                    return true;  
                }
            } catch (SQLException ex) {
                Logger.getLogger(ItemMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
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

    public void setStage(Stage stage_Item, Rectangle2D rectangle2D) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                setFadeOutTransition();
            }
        });

//        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.ESCAPE) {
//                    t.consume();
//                    setFadeOutTransition();
//                }
//            }
//        });
//    
//        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.F5) {
//                    t.consume();
//                    btnAdd.fire();
//                }
//            }
//        });       
//        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.F6) {
//                    t.consume();
//                   btnEdit.fire();
//                }
//            }
//        });             
//        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.F8) {
//                    t.consume();
//                   btnClear.fire();
//                }
//            }
//        });       
//        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.F12) {
//                    t.consume();
//                    btnDelete.fire();
//                }
//            }
//        });   
//        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//                if (t.getCode() == KeyCode.F9) {
//                    t.consume();
//                    btnPrint.fire();
//                }
//            }
//        });   
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

  

    
}
