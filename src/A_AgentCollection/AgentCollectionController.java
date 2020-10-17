/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A_AgentCollection;

import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class AgentCollectionController implements Initializable {

    @FXML
    private DatePicker dpdate;
    @FXML
    private ComboBox<String> cmbagent;
    @FXML
    private TextField txttot;
    @FXML
    private TextField txtamtrec;
    @FXML
    private TextField txtbal;
    @FXML
    private TextField txtamtdue;
    @FXML
    private TextField txttime;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnclear;
    private ObservableList<String> listagent = FXCollections.observableArrayList();
    @FXML
    private TextField txtcode;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.DECORATED);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clear();
        Setcode();
        listner_txtbx();
        populateCombo_agent();
    }  
    
    private void clear(){
        dpdate.setValue(LocalDate.now());
        initClock();
        cmbagent.getSelectionModel().select("");
        txttot.setText("0.0");
        txtamtdue.setText("0.0");
        txtamtrec.setText("0.0");
        txtbal.setText("0.0");
    }
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        txttime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    private void Setcode() throws NumberFormatException {
       Integer pr = Integer.parseInt(setId()) + 1;
       String no = String.format("%03d", pr);
       txtcode.setText("AGCLN-"+no);
       dpdate.requestFocus();
    }
        
    private void listner_txtbx(){
        dpdate.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    cmbagent.requestFocus();
                }
            }
        });
        txtamtrec.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   Double amtdue = Double.parseDouble(txtamtdue.getText());
                   Double amtrec = Double.parseDouble(txtamtrec.getText());
                   Double bal = amtdue - amtrec;
                   txtbal.setText(String.valueOf(bal));
                   btnUpdate.requestFocus();
                }
            }
        });
        cmbagent.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    setvalue();
                    txtamtrec.requestFocus();
                }
            }
        });
    }
    
         private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select tot,bal from " + db.schema + ".tbl_agent where name = '" + cmbagent.getSelectionModel().getSelectedItem() + "' ");
                ResultSet rst = stmt.executeQuery("select tot,bal from " + db.schema + ".tbl_agent where name = '" + cmbagent.getSelectionModel().getSelectedItem() + "' ");
                while (rst.next()) {
                    txttot.setText(rst.getString("tot"));
                    txtamtdue.setText(rst.getString("bal"));
                    
                }
               }
        } catch (SQLException ex) {
            Logger.getLogger(AgentCollectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_agent_col");
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
            Logger.getLogger(AgentCollectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
     
     private void populateCombo_agent(){
        listagent.clear();
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_agent ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_agent ");
                        while (rs.next()) {
                            listagent.add(rs.getString("name"));
                        }
                        cmbagent.setItems(listagent);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AgentCollectionController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    

    @FXML
    private void UpdateOnAction(ActionEvent event) {
    }

    @FXML
    private void ClearOnAction(ActionEvent event) {
    }

    public void setStage(Stage stage_agentcol) {
        this.stage= stage_agentcol;
        
    }
    
}
