/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Daily;


import database.DBMySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class DailyRptController implements Initializable {

    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField txtsales;
    @FXML
    private TextField txttotal;
    @FXML
    private TextField txtcredit;
    @FXML
    private TextField txtexp;
    @FXML
    private TextField txtbank;
    @FXML
    private TextField txtcash;
    @FXML
    private Button btnprint;
    DBMySQL db = new DBMySQL();
    Stage stage = new Stage();
    Double Cashbal=0.0;
    Double creditbal = 0.0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dpdate.setValue(LocalDate.now());
        setvalue();
        listner_adv_search_date();
    }    

    private void sales(){
        Double net=0.0;
            try {
            Statement st = db.con.createStatement();
            System.out.println("select sum(netamt)as netamt from " + db.schema + ".tbl_bill where date = '"+dpdate.getValue()+"' ");
            ResultSet rs = st.executeQuery("select sum(netamt) as netamt from " + db.schema + ".tbl_bill where date = '"+dpdate.getValue()+"'");
            while (rs.next()) {
                net += rs.getDouble("netamt");
                System.out.println("nettt"+ net);
            txtsales.setText(String.valueOf(net));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void credit(){
        Double cr=0.0;
            try {
            Statement st = db.con.createStatement();
            System.out.println("select sum(creditbal)as cb from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' ");
            ResultSet rs = st.executeQuery("select sum(creditbal)as cb from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"'");
            while (rs.next()) {
                cr += rs.getDouble("cb");
                System.out.println("cb   "+ cr);
                txtcredit.setText(String.valueOf(cr));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void expense(){
        Double exp=0.0;
            try {
            Statement st = db.con.createStatement();
            System.out.println("select sum(amt)as amt from " + db.schema + ".tbl_exp_register where date = '"+dpdate.getValue()+"' and status = 1 ");
            ResultSet rs = st.executeQuery("select sum(amt)as amt from " + db.schema + ".tbl_exp_register where date = '"+dpdate.getValue()+"' and status = 1");
            while (rs.next()) {
                exp += rs.getDouble("amt");
                System.out.println("exp   "+ exp);
                
            txtexp.setText(String.valueOf(exp));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void bank(){
        Double bnk =0.0;
            try {
            Statement st = db.con.createStatement();
            System.out.println("select sum(billamt)as ba from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' and type ='Card'  ");
            ResultSet rs = st.executeQuery("select sum(billamt)as ba from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' and type ='Card' ");
            while (rs.next()) {
                bnk += rs.getDouble("ba");
                System.out.println("bank   "+ bnk);
            txtbank.setText(String.valueOf(bnk));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void cash(){
        Double cash =0.0;
            try {
            Statement st = db.con.createStatement();
            System.out.println("select sum(billamt)as ba from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' and  type ='Cash'");
            ResultSet rs = st.executeQuery("select sum(billamt)as ca from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' and type ='Cash'");
            while (rs.next()) {
                cash += rs.getDouble("ca");
                System.out.println("cash   "+ cash);
            txtcash.setText(String.valueOf(cash));
            
            
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void creditamt(){
        Double cra =0.0;
            try {
            Statement st = db.con.createStatement();
            System.out.println("select sum(adv)as ba from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' and  type ='Credit'");
            ResultSet rs = st.executeQuery("select sum(adv)as cr from " + db.schema + ".tbl_payment where date = '"+dpdate.getValue()+"' and type ='Credit'");
            while (rs.next()) {
                cra += rs.getDouble("cr");
                System.out.println("creditamot   "+ cra);
            txtbank.setText(String.valueOf(cra));
            creditbal = cra;
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void listner_adv_search_date() {
        dpdate.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
             setvalue();

            }
        });
        
       
    }
    private void setvalue(){
        sales();
        expense();
        credit();
        Double sale = Double.parseDouble(txtsales.getText());
        if (sale==null) {
           sale=0.0; 
        }
        Double credit = Double.parseDouble(txtcredit.getText());
        Double exp = Double.parseDouble(txtexp.getText());
        Double total = 0.0;
        Double tot = 0.0;
        total = sale - credit;
        tot = total - exp;
        txttotal.setText(String.valueOf(tot));
        cash();
        bank();
        creditamt();
        txtcash.setText(String.valueOf(Cashbal+creditbal));
    }
    
    @FXML
    private void print_onaction(ActionEvent event) {
        
    }

    public void setStage(Stage stage_daily) {
         this. stage =stage_daily;
    }
}
