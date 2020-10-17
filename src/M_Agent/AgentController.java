/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Agent;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DBMySQL;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class AgentController implements Initializable {

    @FXML
    private TextField txt_Code;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_Short;
    @FXML
    private TableView<objtbl> table_main;
    @FXML
    private TableColumn col_code;
    @FXML
    private TableColumn col_name;
    @FXML
    private TableColumn col_remark;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_Update;
    @FXML
    private Button btn_clr;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    ObservableList<objtbl> tblData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Setcode();
        Clear();
        SetButtons();
        setTable();
        lisnterTableClick();
        listner_txtbx();
    }    

    private void Setcode() throws NumberFormatException {
       Integer pr = Integer.parseInt(setId()) + 1;
       String no = String.format("%03d", pr);
       txt_Code.setText("AGN"+no);
       txt_name.requestFocus();
    }
        
    private void listner_txtbx(){
        txt_name.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txt_Short.requestFocus();
                }
            }
        });
        txt_Short.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    btn_save.requestFocus();
                }
            }
        });
        btn_save.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F10) {
                   savedb();
                }
                else if(t.getCode() == KeyCode.ENTER) {
                   btn_clr.requestFocus();
                }
            }
        });
    }

    private String setId() {
        String id = "0";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select count(code), max(code) FROM " + db.schema + ".tbl_agent");
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
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void Clear(){
        txt_name.requestFocus();
        txt_name.clear();
        txt_Short.clear();
        btn_Update.setVisible(false);
        btn_save.setVisible(true);
        populatetable();
        Setcode();
    }
    
    private void SetButtons(){
        btn_Update.setVisible(false);
    }

    private void setTable() {
        col_code.setCellValueFactory(new PropertyValueFactory<objtbl, String>("code"));
        col_name.setCellValueFactory(new PropertyValueFactory<objtbl, String>("name"));
        col_remark.setCellValueFactory(new PropertyValueFactory<objtbl, String>("remark"));
        table_main.setItems(tblData);
    }
    
    private void populatetable() {
        tblData.clear();
        try {

            Statement st = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_agent where status = 1 ");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_agent where status = 1 ");
            while (rs.next()) {
            tblData.add(new objtbl(rs.getString("code"), rs.getString("name"), rs.getString("remarks")));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private void lisnterTableClick() {
        table_main.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.PRIMARY && !table_main.getSelectionModel().isEmpty()) {
                    fetch(table_main.getSelectionModel().getSelectedItem());
                }
            }
        });
    }
   
    private void fetch(objtbl obj) {
        btn_save.setVisible(false);
        btn_Update.setVisible(true);
        txt_Code.setText(obj.getCode());
        txt_name.setText(obj.getName()); 
        txt_Short.setText(obj.getRemarks());
    }

    private boolean save() {
        boolean flag = false;
            try {
                Statement st = db.con.createStatement();
                System.out.println("insert into " + db.schema + ".tbl_agent values(null,'"+ txt_Code.getText()+"','"+txt_name.getText().toUpperCase().trim()+"','"+ txt_Short.getText()+"',0.0,0.0,1)");
                String query="insert into " + db.schema + ".tbl_agent values(null,'"+ txt_Code.getText()+"','"+txt_name.getText().toUpperCase().trim()+"','"+ txt_Short.getText()+"',0.0,0.0,1)";
                System.out.println(""+query);
                int bool = st.executeUpdate(query);
                if (bool > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Agent Saved Successfully!", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();          
                if (alert.getResult() == ButtonType.YES) {
                    Clear();
                    populatetable();
                        
                    }
                    flag = true;

                }
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Clear();        
        return flag;
    }
   
    private void updatedb() {        
        try {
            Statement st = db.con.createStatement();
            System.out.println("update " + db.schema + ".tbl_agent set name= '" + txt_name.getText().toUpperCase().trim() + "', remarks= '" + txt_Short.getText()+ "' where code = '" + table_main.getSelectionModel().getSelectedItem().getCode()+ "'");
            int bool = st.executeUpdate("update " + db.schema + ".tbl_agent set name= '" + txt_name.getText().toUpperCase().trim() + "', remarks= '" + txt_Short.getText()+ "' where code = '" + table_main.getSelectionModel().getSelectedItem().getCode()+ "'");
            if (bool > 0) {
                Clear();
                populatetable();
                btn_save.setVisible(true);
                btn_Update.setVisible(false);
               
            }
            } catch (SQLException ex) {
                Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
                }     
    }
    
    private void deletedb(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Do you Want Delete this Agent ?", ButtonType.YES, ButtonType.NO);
          alert.showAndWait();
          if (alert.getResult() == ButtonType.YES) {         
            try {
                Statement st = db.con.createStatement();
                System.out.println("update " + db.schema + ".tbl_agent set status= 0 where code = '" + table_main.getSelectionModel().getSelectedItem().getCode()+ "'");
                int bool = st.executeUpdate("update " + db.schema + ".tbl_agent set status= 0 where code = '" + table_main.getSelectionModel().getSelectedItem().getCode()+ "'");
                if (bool > 0) {
                    Clear();
                    populatetable();
                    btn_save.setVisible(true);
                    btn_Update.setVisible(false);  
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
    }

    private void savedb(){
            if (txt_name.getText().equalsIgnoreCase("")) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Agent", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    txt_name.requestFocus();
                    }  
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Save this Agent", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    save();
                    }  
            }  
    }
    
    private void Createpdf(File file_temp) throws FileNotFoundException, DocumentException, IOException{
            Document doc =new Document(PageSize.A4, 30, 20, 20, 40);
            //////////////////////////ADDD HEADING IN PDF////////////////////////////////////////
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font head = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
            Chunk title= new Chunk("LOOMS & WEAVES",head);
            Phrase tit = new Phrase();
            tit.add(title);
            Paragraph paratit = new Paragraph();
            paratit.add(tit);
            paratit.setAlignment(Element.ALIGN_CENTER);
            Chunk reportTitle= new Chunk("Agent List",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph(" ", paragraphFont));
            pg.setAlignment(Element.ALIGN_CENTER);
            //////////////////ADDD TABLE IN PDF///////////////////////////    
            PdfPTable table = new PdfPTable(3);
		float widths[] = { 16, 25, 25 };
		table.setWidths(widths);
		table.setHeaderRows(1);
            Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font content = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            /////////////// add cell of table - header cell///////////////////////
			PdfPCell cell = new PdfPCell(new Phrase("AGENT CODE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("AGENT NAME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("REMARKS",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        Phrase ph;
	    //////////////// looping the table cell for adding definition/////////////////////////
                    int row = tblData.size();
                    for (int i = 0; i < row; i++)
                    {
                        objtbl sm = tblData.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getCode(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getName(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getRemarks(),content);
				cell.addElement(ph);
				table.addCell(cell);                                  
		    }                    
            
            Font printer = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            Chunk print= new Chunk("****Printed On :"+dateFormat.format(date)+"****",printer);
            Phrase pri = new Phrase();
            pri.add(print);
            Paragraph paraprint = new Paragraph();
            paraprint.add(pri);
            paraprint.setAlignment(Element.ALIGN_CENTER);
            try {
                PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream(file_temp));
                doc.open();   
                doc.add(paratit);
                doc.add(para);               
                doc.add(pg);
                doc.add(table);
                doc.add(paraprint);

                doc.close();
                Desktop.getDesktop().open(file_temp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    @FXML
    private void btn_delete_onAction(ActionEvent event) {
        if (table_main.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Agent from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                   table_main.requestFocus();
                    }
        }     
        else {
           deletedb();
        }

    }

    @FXML
    private void btnPrint_onAction(ActionEvent event) throws FileNotFoundException, DocumentException {
           try {
            File file_temp = File.createTempFile("Agentlist", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void save_action(ActionEvent event) {
        savedb();
    }

    @FXML
    
    private void update_action(ActionEvent event) {
        if (table_main.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Agent from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                   table_main.requestFocus();
                    }
        }     
        else {         
         if (txt_name.getText().equalsIgnoreCase("")) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Agent", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    txt_name.requestFocus();
                    }  
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Update this Agent", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    updatedb();                 
                    }  
            }        
        }
    }
    

    @FXML
    private void clr_action(ActionEvent event) {
    Clear();
    }

    public void setStage(Stage stage_agent) {
        this.stage= stage_agent;
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
                    btn_clr.fire();
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
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F10) {
                    t.consume();
                   btn_save.fire();
                }
            }
        });        
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F11) {
                    t.consume();
                   btn_Update.fire();
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
