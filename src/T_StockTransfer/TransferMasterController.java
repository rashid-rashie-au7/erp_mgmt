/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_StockTransfer;

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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class TransferMasterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage stage_add_tra = new Stage(StageStyle.DECORATED);
    private Stage stageEditemp = new Stage(StageStyle.DECORATED);
    private Stage stage = new Stage();
    public static BooleanProperty boolean_status = new SimpleBooleanProperty();
    public static ObservableList<objtbl> table_data = FXCollections.observableArrayList();
    public static ObservableList<objtbl> table_data_search = FXCollections.observableArrayList();
    private DBMySQL db = new DBMySQL();
    Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
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
    private TextField txtid;
    @FXML
    private TextField txtwh;
    @FXML
    private DatePicker dpdate;
    @FXML
    private Accordion accrtable;
    @FXML
    private TitledPane tpTable;
    @FXML
    private AnchorPane anchrTable;
    @FXML
    private TableView<objtbl> tblView;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn coltime;
    @FXML
    private TableColumn colwh;
    @FXML
    private TableColumn colitems;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        load_titled_pane_icon();
        set_buttons();
        set_table();
        populate_table();
        listner_boolean();
            
    }    

    private void set_buttons() {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
    }
 
    private void load_titled_pane_icon() {
        accrSearch.setExpandedPane(tpSearch);
        tpSearch.setAnimated(true);
        tpTable.setAnimated(true);
        accr_option.setExpandedPane(tp_options);
        accrtable.setExpandedPane(tpTable);
        tpSearch.setAnimated(true);
        
    }

    private void set_table() {
        colid.setCellValueFactory(new PropertyValueFactory<objtbl, String>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<objtbl, String>("time"));
        colwh.setCellValueFactory(new PropertyValueFactory<objtbl, String>("wh"));
        colitems.setCellValueFactory(new PropertyValueFactory<objtbl, String>("item"));
        table_data.add(new objtbl("", "", "", ""));
        tblView.setItems(table_data);
    }

    public void populate_table() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_stock_transfer where status  = 1");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_stock_transfer where status  = 1");
            while (rs.next()) {
                
                table_data.add(new objtbl(rs.getString("code"), rs.getString("date"), rs.getString("time"), rs.getString("wh")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(TransferMasterController.class.getName()).log(Level.SEVERE, null, ex);
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
        dpdate.setValue(LocalDate.now());
        txtid.clear();
        txtSearch.clear();
        txtwh.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        populate_table();
    }
     
     private void StockControllerActionIdentified(String type, String id) {
        if (!stage_add_tra.isShowing()) {
            stage_add_tra = new Stage(StageStyle.DECORATED);
            stage_add_tra.initOwner(stage);
            stage_add_tra.initModality(Modality.WINDOW_MODAL);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Transfer.fxml"));
                Parent root = (Parent) loader.load();
                TransferController sc = loader.getController();
                Scene scene = new Scene(root);
                stage_add_tra.setScene(scene);
                stage_add_tra.setResizable(false);
                stage_add_tra.setTitle("Add Transfer");
                sc.setStage(stage_add_tra);
                stage_add_tra.show();
                if (type.equalsIgnoreCase("Edit")) {
                    stage_add_tra.setTitle("Edit Transfer");
                    sc.fetch_for_update( id);
                    } else if (type.equalsIgnoreCase("Add")) {
                  }

            } catch (IOException ex) {
                Logger.getLogger(TransferMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                    for (objtbl obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase()) || obj.getWh().toLowerCase().contains(text.toLowerCase()) ) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Data Found");
                }
            }
        });
    }

    private void listner_adv_search_po_id() {
        txtid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtid.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objtbl obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Employ Found");
                }
            }
        });
    }

    private void listner_adv_search_name(){
        txtwh.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtwh.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objtbl obj : table_data) {
                        if (obj.getWh().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Data Found");
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
    private void btnadd_onaction(ActionEvent event) {
        StockControllerActionIdentified("Add", "");
    }

    @FXML
    private void btnedit_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Transfer from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                   tblView.requestFocus();
                    }
        }     
        else {
            StockControllerActionIdentified("Edit", tblView.getSelectionModel().getSelectedItem().getId());
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
    }

    @FXML
    private void btnhide_onaction(ActionEvent event) {
    }

    @FXML
    private void btnClear_onaction(ActionEvent event) {
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
     
    public void setStage(Stage stage_stktra) {
       this.stage= stage_stktra;

        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    t.consume();
                    setFadeOutTransition();
                }
            }
        });
    
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F5) {
                    t.consume();
                    btnAdd.fire();
                }
            }
        });       
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F6) {
                    t.consume();
                   btnEdit.fire();
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
                if (t.getCode() == KeyCode.F12) {
                    t.consume();
                    btnDelete.fire();
                }
            }
        });   
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F9) {
                    t.consume();
                    btnPrint.fire();
                }
            }
        });   
    }
    
}
