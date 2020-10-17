/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_SalesReturn;


import database.DBMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import static miw.Tools.filter;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class SalesReturnMasterController implements Initializable {

    @FXML
    private HBox hbx_main;
    @FXML
    private SplitPane sp_main;
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
    private TextField txtbillno;
    @FXML
    private TextField txtclient;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Accordion accrtable;
    @FXML
    private TitledPane tpTable;
    @FXML
    private AnchorPane anchrTable;
    @FXML
    private TableView<objtbl> tblView;
    @FXML
    private TableColumn colbill;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn coltime;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colitem;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn coltot;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colnet;
    @FXML
    private TableColumn coltype;
    private Stage stage = new Stage();
    private Stage stage_add_sales = new Stage(StageStyle.DECORATED);
    public static BooleanProperty boolean_status = new SimpleBooleanProperty();
    public static ObservableList<objtbl> table_data = FXCollections.observableArrayList();
    public static ObservableList<objtbl> table_data_search = FXCollections.observableArrayList();
    private DBMySQL db = new DBMySQL();
    public ObservableList<objtbl> table_delete_data = FXCollections.observableArrayList();
    String password = "";
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
        load_titled_pane_icon();
        set_buttons();
        set_table();
        populate_table();
        listner_boolean();
        listner_search();
        listner_adv_search_Bill();
        listner_adv_search_name();
        listner_adv_search_date();;
        tpTable.setText(tblView.getItems().size() + " Bills Found");
        tblView.itemsProperty().addListener(new ChangeListener<ObservableList<objtbl>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<objtbl>> ov, ObservableList<objtbl> t, ObservableList<objtbl> t1) {
                tpTable.setText(tblView.getItems().size() + " Bills  Found");
            }
        });
        doubleClickTableEdit();
        enterKeyEventOnTableView();
        setLayout();
    }    
    
    private void load_titled_pane_icon() {
        accrSearch.setExpandedPane(tpSearch);
        tpSearch.setAnimated(true);
        tpTable.setAnimated(true);
        accrtable.setExpandedPane(tpTable);
        tpSearch.setAnimated(true);
        
    }
    
    

    private void set_buttons() {
        dpDate.setValue(LocalDate.now());
        btnHide.setVisible(true);
        btnShow.setVisible(false);
    }
    
    private void set_table() {
        colbill.setCellValueFactory(new PropertyValueFactory<objtbl, String>("bill"));
        coldate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<objtbl, String>("time"));
        colname.setCellValueFactory(new PropertyValueFactory<objtbl, String>("name"));
        colitem.setCellValueFactory(new PropertyValueFactory<objtbl, String>("items"));
        colqty.setCellValueFactory(new PropertyValueFactory<objtbl, String>("qty"));
        coltot.setCellValueFactory(new PropertyValueFactory<objtbl, String>("tot"));
        colgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("gst"));
        colnet.setCellValueFactory(new PropertyValueFactory<objtbl, String>("net"));
        coltype.setCellValueFactory(new PropertyValueFactory<objtbl, String>("type"));
        table_data.add(new objtbl("", "", "", "", "", "", "","","",""));
        tblView.setItems(table_data);

    }

    public void populate_table() {
        table_data.removeAll(table_data);
//        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select billno, date,time , client, items,qty,grand,gst,netamt,wh from " + db.schema + ".tbl_bill where date='"+dpDate.getValue()+"' and status  = 1");
            ResultSet rs = st.executeQuery("select billno, date,time , client, items,qty,grand,gst,netamt,wh from " + db.schema + ".tbl_bill where date='"+dpDate.getValue()+"' and status  = 1");
            while (rs.next()) {
                
                table_data.add(new objtbl(rs.getString("billno"), rs.getString("date"), rs.getString("time"), rs.getString("client"), rs.getString("items"), rs.getString("qty"),rs.getString("grand"), rs.getString("gst"),rs.getString("netamt") ,rs.getString("wh")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
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
        txtbillno.clear();
        txtclient.clear();
        dpDate.setValue(LocalDate.now());
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
                        if (obj.getBill().toLowerCase().contains(text.toLowerCase()) || obj.getName().toLowerCase().contains(text.toLowerCase()) ) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Bills Found");
                }
            }
        });
    }

    private void listner_adv_search_Bill() {
        txtbillno.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtbillno.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objtbl obj : table_data) {
                        if (obj.getBill().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Bill Found");
                }
            }
        });
    }

    private void listner_adv_search_date() {
        dpDate.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
               populate_table();                  

            }
        });
        
       
    }
    
    private void listner_adv_search_name() {
        txtclient.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtclient.getText();
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


    public void delete_bill_data(String billno) {
//            table_delete_data.removeAll(table_delete_data);
//            try {
//                  Statement st1 = db.con.createStatement();
//                  ResultSet rs = st1.executeQuery("select  * from " + db.schema + ".tbl_bill_items  where  billno = '" + billno + "'");
//                  while (rs.next()) {
//                        table_delete_data.add(new objtbl(rs.getString("barcode"), rs.getString("code"), rs.getString("name"), rs.getString("rate"), rs.getString("qty"), rs.getString("gst"), rs.getString("gstamt"), rs.getString("netamt")));
//                  }
//                  st1.close();
//            }
//            catch (SQLException ex) {
//                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
//            }
      }
    
    private boolean Delete_bill(String billno) {
            boolean transaction_status = false;
            delete_bill_data(billno);
            try {
                  db.con.setAutoCommit(false);
                  if (deleteBillItems(billno)) {
                        if (delete_tbl_bill(billno)) {

                              deletepayment(billno);
                              stockUpdateDeleteBill(billno);

                              db.con.commit();
                              transaction_status = true;
                        }
                  }
                  if (transaction_status = true) {
//                        db.con.rollback();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bill Delete Sucessfully", ButtonType.OK);
                        alert.showAndWait();
                        populate_table();
                      
                  }
                  if (transaction_status = false) {
                        db.con.rollback();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Database Error Please Contact Service Provider", ButtonType.OK);
                        alert.showAndWait();
                      
                  }
                  db.con.setAutoCommit(true);
            }
            catch (SQLException ex) {
                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;

      }
    
         private boolean deleteBillItems(String billno) {
            boolean flag = false;
            try {
                  Statement st = db.con.createStatement();
                  int bool = st.executeUpdate("delete from " + db.schema + ".tbl_bill_items where billno = '" + billno + "'");
                  if (bool > 0) {
                        flag = true;
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return flag;
      }

      private boolean delete_tbl_bill(String billno) {
            boolean flag = false;
            try {
                  Statement st = db.con.createStatement();
                  int bool = st.executeUpdate("delete from " + db.schema + ".tbl_bill where billno = '" + billno + "'");
                  if (bool > 0) {
                        flag = true;
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return flag;
      }
      
      private boolean deletepayment(String billno) {
            boolean flag = false;
            try {
                  Statement st = db.con.createStatement();
                  int bool = st.executeUpdate("delete from " + db.schema + ".tbl_payment where billno = '" + billno + "'");
                  if (bool > 0) {
                        flag = true;
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return flag;
      }
    
    private int stockUpdateDeleteBill(String text) {
            int no_of_items_updated = 0;
            try {
                  Statement st = db.con.createStatement();
                  for (int i = 0; i < table_delete_data.size(); i++) {
                        objtbl obj = table_delete_data.get(i);
                
                              double qty_out = Double.parseDouble(obj.getQty());
                              String str_sql = "update " + db.schema + ".tbl_item set stock = stock + " + qty_out + " where name = '" + obj.getName() + "'";
                              int bool = st.executeUpdate(str_sql);
                              if (bool > 0) {
                                    no_of_items_updated++;
                              }
                        
                  }
                  st.close();
            }
            catch (SQLException e) {
                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, e);
            }
            return no_of_items_updated;
      }
    
    private void doubleClickTableEdit() {
        tblView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!tblView.getSelectionModel().isEmpty() && t.getClickCount() == 2) {
                    load_fxml();
                }
            }
        });
    }
    private void load_fxml() {
        try {
            if (stage_add_sales.isIconified()) {
                stage_add_sales.setIconified(false);
            }
            else if (!stage_add_sales.isShowing()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SalesReturn.fxml"));
                Parent root = (Parent) loader.load();
                SalesReturnController cpc = loader.getController();
                Scene scene = new Scene(root);
                stage_add_sales.setScene(scene);
                stage_add_sales.setTitle("Return Purchase " + tblView.getSelectionModel().getSelectedItem().getBill());
                stage_add_sales.setResizable(false);
                cpc.setStage(stage_add_sales);
                cpc.fetch_for_update(tblView.getSelectionModel().getSelectedItem().getBill());
                stage_add_sales.show();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(SalesReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enterKeyEventOnTableView() {
        tblView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER && !tblView.getSelectionModel().isEmpty()) {
                    load_fxml();
                    t.consume();
                }
            }
        });
    }

    private void btndelete_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Bill from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {                  
                    tblView.requestFocus();
                    }           
        }  
        else {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete Bill!", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
          
                if (alert.getResult() == ButtonType.YES) {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Verify");
                    dialog.setHeaderText("Verify Admin Password");
                    dialog.setContentText("Please enter Admin Password");
                    // Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            System.out.println("Your name: " + result.get());
                            if (passwordCorrect(result.get())) {
                                if (Delete_bill(tblView.getSelectionModel().getSelectedItem().getBill())){
                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Bill Deleted Sucessfully", ButtonType.OK);
                                    alert1.showAndWait();
                                    boolean_status.setValue(!boolean_status.getValue());
                                }     
                            }else{
                                 Alert alert2 = new Alert(Alert.AlertType.ERROR, "Incorrect Password. Please Enter Correct Password ", ButtonType.YES,ButtonType.NO);
                                  alert2.showAndWait();
                                    if (alert.getResult() == ButtonType.YES) {
//                                       dialog.showAndWait();
                                    }
                            }
                        }       
                }
        }
    }
    
    private GridPane gridPassword() {
            GridPane pane = new GridPane();
            PasswordField field = new PasswordField();
            Label label = new Label("Please Enter Admin Password : ");
            field.textProperty().addListener(new ChangeListener<String>() {
                  @Override
                  public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                        password = filter(t1);
                  }
            });
            pane.add(label, 1, 1);
            pane.add(field, 2, 1);

            return pane;
      }

      private boolean passwordCorrect(String password) {
            try {
                  Statement st = db.con.createStatement();
                  ResultSet rs = st.executeQuery("select password from " + db.schema + ".login");
                  if (rs.next()) {
                        if (rs.getString("password").equals(password)) {
                              return true;
                        }
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(SalesReturnMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
      }


    @FXML
    private void btnshow_onaction(ActionEvent event) {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
        txtbillno.requestFocus();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), anchrAdvSearch);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), accrtable);
        translateTransition.setFromY(accrtable.getTranslateY());
        translateTransition.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
        parallelTransition.play();

       
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

        
    }

    @FXML
    private void btnClear_onaction(ActionEvent event) {
        clear();
    }

    public void setStage(Stage stage_salesrtn) {
      this.stage=stage_salesrtn;
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
                if (t.getCode() == KeyCode.F8) {
                    t.consume();
                   btnClear.fire();
                }
            }
        });       
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
    
    
}
