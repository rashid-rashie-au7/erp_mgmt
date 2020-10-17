/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shortcuts;

import M_Unit.obj_unit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class ShortController implements Initializable {

    @FXML
    private TableView<objtbl> tbldata;
    @FXML
    private TableColumn colkey;
    @FXML
    private TableColumn colact;
    private Stage stage = new Stage(StageStyle.UTILITY);
    ObservableList<objtbl> tbllist = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        settable();
    }    
    private void settable(){
        colkey.setCellValueFactory(new PropertyValueFactory<objtbl, String>("key"));
        colact.setCellValueFactory(new PropertyValueFactory<objtbl, String>("act"));
        tbllist.add(new objtbl("Esc", "Close Form"));
        tbllist.add(new objtbl("F1", ""));
        tbllist.add(new objtbl("F2", ""));
        tbllist.add(new objtbl("F3", ""));
        tbllist.add(new objtbl("F4", ""));
        tbllist.add(new objtbl("F5", "ADD NEW "));
        tbllist.add(new objtbl("F6", "EDIT FORMS"));
        tbllist.add(new objtbl("F7", "OPEN RETURN FORMS OR SUB FORMS"));
        tbllist.add(new objtbl("F8", "CLEAR ALL FIELDS IN A FORM"));
        tbllist.add(new objtbl("F9", "PRINT DETAILS"));
        tbllist.add(new objtbl("F10", "SAVE THE FORMS"));
        tbllist.add(new objtbl("F11", "UPDATE THE FORMS"));
        tbllist.add(new objtbl("F12", "DELETE DATA"));
        tbldata.setItems(tbllist);
    }

    public void setStage(Stage stage_short) {
        this.stage= stage_short;
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
}
