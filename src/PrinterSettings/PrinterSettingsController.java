/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrinterSettings;

import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import static miw.Tools.filter;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class PrinterSettingsController implements Initializable {

    @FXML
    private Font x3;
    @FXML
    private Font x1;
    @FXML
    private ComboBox<String> comboBoxPrinters;
    @FXML
    private Button btnSave;
    @FXML
    private Font x2;
    @FXML
    private Button btnRefresh;
    ObservableList<String> deviceList = FXCollections.observableArrayList();
    private Stage stage;
    DBMySQL db = new DBMySQL();
    String defaultPrinter = "";
    @FXML
    private ComboBox<String> cmbtypes;
    ObservableList<String> type = FXCollections.observableArrayList("Bill Printer","Barcode Printer","Other Printers");
    @FXML
    private Font x11;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbtypes.setItems(type);
        cmbtypes.getSelectionModel().select(0);
        fetchPrinters();
        listnerComboType();
    }    
     private void listnerComboType() {
        cmbtypes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
               settingsExist();
            }
        });
    }
    
    private void fetchPrinters() {
        deviceList.clear();
        PrintService[] pserv = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService p : pserv) {
            deviceList.add(p.getName());
        }
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        comboBoxPrinters.setItems(deviceList);
        if (settingsExist()) {
            comboBoxPrinters.getSelectionModel().select(defaultPrinter);
        }
        else {
            comboBoxPrinters.getSelectionModel().select(service.getName());
        }
    }
    
    private void savePrinterSettings() {
        try {
            int id=0;
            if(cmbtypes.getSelectionModel().getSelectedItem()=="Bill Printer"){
               id=1; 
            } else if(cmbtypes.getSelectionModel().getSelectedItem()=="Barcode Printer"){
                id = 2;
            } else if (cmbtypes.getSelectionModel().getSelectedItem()=="Other Printers") {
               id=3; 
            }
            Statement st = db.con.createStatement();
            System.out.println("insert into " + db.schema + ".printersettings values(null, "+id+" , '" + filter(comboBoxPrinters.getSelectionModel().getSelectedItem()) + "')");
            int bool = st.executeUpdate("insert into " + db.schema + ".printersettings values(null, "+id+" , '" + filter(comboBoxPrinters.getSelectionModel().getSelectedItem()) + "')");
            if (bool > 0) {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Printer Saved Successfully", ButtonType.OK);
               alert.showAndWait();
                stage.close();
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PrinterSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean settingsExist() {
        try {
            Statement st = db.con.createStatement();
             int id=0;
            if(cmbtypes.getSelectionModel().getSelectedItem()=="Bill Printer"){
               id=1; 
            } else if(cmbtypes.getSelectionModel().getSelectedItem()=="Barcode Printer"){
                id = 2;
            } else if (cmbtypes.getSelectionModel().getSelectedItem()=="Other Printers") {
               id=3; 
            }
            System.out.println("select * from " + db.schema + ".printersettings where settingsId = "+id+"");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".printersettings where settingsId = "+id+"");
            if (rs.next()) {
                defaultPrinter = rs.getString("defaultPrinter");
                comboBoxPrinters.getSelectionModel().select(defaultPrinter);
                return true;
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PrinterSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void updatePrinterSettings() {
        try {
            Statement st = db.con.createStatement();
            int id=0;
            if(cmbtypes.getSelectionModel().getSelectedItem()=="Bill Printer"){
               id=1; 
            } else if(cmbtypes.getSelectionModel().getSelectedItem()=="Barcode Printer"){
                id = 2;
            } else if (cmbtypes.getSelectionModel().getSelectedItem()=="Other Printers") {
               id=3; 
            }
            System.out.println("update " + db.schema + ".printersettings set defaultPrinter = '" + filter(comboBoxPrinters.getSelectionModel().getSelectedItem()) + "' where settingsId = "+id+"");
            int bool = st.executeUpdate("update " + db.schema + ".printersettings set defaultPrinter = '" + filter(comboBoxPrinters.getSelectionModel().getSelectedItem()) + "' where settingsId = "+id+" ");
            if (bool > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Printer Updated Successfully",ButtonType.OK);
                alert.showAndWait();
                stage.close();
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PrinterSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
             if (!comboBoxPrinters.getSelectionModel().isEmpty()) {
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Save this Printer as Default Printer", ButtonType.YES,ButtonType.NO);
                 alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (settingsExist()) {
                    updatePrinterSettings();
                }
                else {
                    savePrinterSettings();
                }
            }
        }
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        deviceList.clear();
        PrintService[] pserv = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService p : pserv) {
            deviceList.add(p.getName());
        }
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        comboBoxPrinters.setItems(deviceList);
        comboBoxPrinters.getSelectionModel().select(service.getName());
    }
    
       public void setStage(Stage stage_pri) {
        this.stage = stage_pri;

        stage.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    setFadeOutTransition();
                    t.consume();
                }
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                setFadeOutTransition();
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
