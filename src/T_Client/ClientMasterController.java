/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Client;


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
public class ClientMasterController implements Initializable {

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
    private TextField txtname;
    @FXML
    private TextField txtMob;
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
    private TableColumn colname;
    @FXML
    private TableColumn colmob;
    @FXML
    private TableColumn colemail;
    @FXML
    private TableColumn colplace;
    @FXML
    private TableColumn coldis;
    @FXML
    private TableColumn coltype;
    private Stage stage = new Stage();
    private Stage stage_add_client = new Stage(StageStyle.UTILITY);
    private Stage stage_return = new Stage(StageStyle.UTILITY);
     public static BooleanProperty boolean_status = new SimpleBooleanProperty();
    public static ObservableList<objtbl> table_data = FXCollections.observableArrayList();
    public static ObservableList<objtbl> table_data_search = FXCollections.observableArrayList();
    private DBMySQL db = new DBMySQL();
    Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
    @FXML
    private TextField txtid;
    @FXML
    private RadioButton rbtall;
    @FXML
    private RadioButton rbtretail;
    @FXML
    private RadioButton rbtwhole;

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
        clear();
        populate_table();
        listner_boolean();
        listner_search();
        listner_adv_search_mob();
        listner_adv_search_id();
        listner_adv_search_name();
        listner_radio_selection();
        setLayout();
        tpTable.setText(tblView.getItems().size() + " Clients Found");
        tblView.itemsProperty().addListener(new ChangeListener<ObservableList<objtbl>>() {
            public void changed(ObservableValue<? extends ObservableList<objtbl>> ov, ObservableList<objtbl> t, ObservableList<objtbl> t1) {
                tpTable.setText(tblView.getItems().size() + " Clients  Found");
            }
        });
        doubleClickTableEdit();
        enterKeyEventOnTableView();
     
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
    
    private void ClientControllerActionIdentified(String type, String id) {
        if (!stage_add_client.isShowing()) {
            stage_add_client = new Stage(StageStyle.UTILITY);
            stage_add_client.initOwner(stage);
            stage_add_client.initModality(Modality.WINDOW_MODAL);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientCreation.fxml"));
                Parent root = (Parent) loader.load();
                ClientCreationController ccc = loader.getController();
                Scene scene = new Scene(root);
                stage_add_client.setScene(scene);
                stage_add_client.setResizable(false);
                stage_add_client.setTitle("Add Client");
                ccc.setStage(stage_add_client);
                stage_add_client.show();
                 if (type.equalsIgnoreCase("Add")) {
                }else if (type.equalsIgnoreCase("Edit")) {
                    ccc.fetch_for_update(id);
                    stage_add_client.setTitle("Edit Client");
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

    }
    
    private void set_table() {
        colid.setCellValueFactory(new PropertyValueFactory<objtbl, String>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<objtbl, String>("name"));
        colmob.setCellValueFactory(new PropertyValueFactory<objtbl, String>("mob"));
        colemail.setCellValueFactory(new PropertyValueFactory<objtbl, String>("email"));
        colplace.setCellValueFactory(new PropertyValueFactory<objtbl, String>("place"));
        coldis.setCellValueFactory(new PropertyValueFactory<objtbl, String>("dis"));
        coltype.setCellValueFactory(new PropertyValueFactory<objtbl, String>("type"));
        table_data.add(new objtbl("", "", "", "", "", "", ""));
        tblView.setItems(table_data);

    }

    public void populate_table() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,mob ,email, place, dis,type from " + db.schema + ".tbl_client where  status  = 1");
            ResultSet rs = st.executeQuery("select code, name,mob ,email, place, dis,type from " + db.schema + ".tbl_client where status  = 1");
            while (rs.next()) {
                
                table_data.add(new objtbl(rs.getString("code"), rs.getString("name"), rs.getString("mob"), rs.getString("email"), rs.getString("place"), rs.getString("dis"),rs.getString("type")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ClientMasterController.class.getName()).log(Level.SEVERE, null, ex);
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
        txtSearch.clear();
        txtMob.clear();
        txtname.clear();
        txtid.clear();
        rbtall.setSelected(true);
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
                    for (objtbl obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase()) || obj.getName().toLowerCase().contains(text.toLowerCase()) ) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Clients Found");
                }
            }
        });
    }

    private void listner_adv_search_id() {
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
                    tpTable.setText(tblView.getItems().size() + " Clients Found");
                }
            }
        });
    }

    private void listner_adv_search_name() {
        txtname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtname.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objtbl obj : table_data) {
                        if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Bill Found");
                }
            }
        });
    }

    private void listner_adv_search_mob() {
        txtMob.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtMob.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objtbl obj : table_data) {
                        if (obj.getMob().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Bill Found");
                }
            }
        });
    }
    
    private void populate_tbl_retail() {
        table_data.removeAll(table_data);
        String status = "";
        
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,mob ,email, place, dis,type from " + db.schema + ".tbl_client where type = 'Retailer' and status  = 1");
            ResultSet rs = st.executeQuery("select code, name,mob ,email, place, dis,type from " + db.schema + ".tbl_client where type = 'Retailer' and status  = 1");
            while (rs.next()) {
                
                table_data.add(new objtbl(rs.getString("code"), rs.getString("name"), rs.getString("mob"), rs.getString("email"), rs.getString("place"), rs.getString("dis"),rs.getString("type")));
            }
            st.close();
        }  catch (SQLException ex) {
            Logger.getLogger(ClientMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private void listner_radio_selection() {
        ToggleGroup grp =new ToggleGroup();
        rbtall.setToggleGroup(grp);
        rbtretail.setToggleGroup(grp);
        rbtwhole.setToggleGroup(grp);
        rbtall.setUserData("All");
        rbtretail.setUserData("Retailer");
        rbtretail.setUserData("Whole Saler");
        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                    if(rbtretail.isSelected()){
                        populate_tbl_retail();
                        tpTable.setText(table_data.size() + " Clients Found");
                    }else if(rbtwhole.isSelected()){
                        populate_tbl_whole();
                        tpTable.setText(table_data.size() + " Clients Found");
                    }else if (rbtall.isSelected()){
                        populate_table();
                        tpTable.setText(table_data.size() + " Clients Found");
                    }
                
            }
        });
    }

    private void populate_tbl_whole() {
        table_data.removeAll(table_data);
        String status = "";
        
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,mob ,email, place, dis,type from " + db.schema + ".tbl_client where type = 'Whole Saler' and status  = 1");
            ResultSet rs = st.executeQuery("select code, name,mob ,email, place, dis,type from " + db.schema + ".tbl_client where type = 'Whole Saler' and status  = 1");
            while (rs.next()) {
                
                table_data.add(new objtbl(rs.getString("code"), rs.getString("name"), rs.getString("mob"), rs.getString("email"), rs.getString("place"), rs.getString("dis"),rs.getString("type")));
            }
            st.close();
        }  catch (SQLException ex) {
            Logger.getLogger(ClientMasterController.class.getName()).log(Level.SEVERE, null, ex);
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

    private boolean delete(String id) {
        try {
                Statement st = db.con.createStatement();
                System.out.println("update " + db.schema + ".tbl_client set status= 0 where code =  '" + id + "'");
                int bool = st.executeUpdate("update " + db.schema + ".tbl_client set status= 0 where code = '" + id + "'");
                if (bool > 0) {
                    return true;  
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClientMasterController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void btnadd_onaction(ActionEvent event) {
        ClientControllerActionIdentified("Add", "");
    }

    @FXML
    private void btnedit_onaction(ActionEvent event) {
         if (tblView.getSelectionModel().isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Client from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {                  
                    tblView.requestFocus();
                    }    
        }
        
        else {
            ClientControllerActionIdentified("Edit", tblView.getSelectionModel().getSelectedItem().getId());
           
        }
    }

    @FXML
    private void btndelete_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Client from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {                  
                    tblView.requestFocus();
                    }           
        }
        else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to Delete this Client!", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                   if (delete(tblView.getSelectionModel().getSelectedItem().getId())) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Client Deleted Sucessfully", ButtonType.OK);
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
        txtid.requestFocus();

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

    public void setStage(Stage stage_client, Rectangle2D rectangle2D) {
        this.stage= stage_client;
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
