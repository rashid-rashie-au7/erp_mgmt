/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RptExpence;


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
import com.miw.control.sbox.SuggessionBox;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author rashi
 */
public class RptExpenceController implements Initializable {

    @FXML
    private TableView<objExp> tblview;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colamt;
    @FXML
    private TableColumn colmode;
    @FXML
    private TableColumn colcmnt;
    @FXML
    private DatePicker dpfrom;
    @FXML
    private HBox hbxname;
    @FXML
    private ComboBox<String> cmbmode;
    @FXML
    private DatePicker dpto;
    @FXML
    private TextField txttot;
    @FXML
    private Button btn_print;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.DECORATED);
    ObservableList<objExp> tblData = FXCollections.observableArrayList();
    ObservableList<String> combo_list = FXCollections.observableArrayList("All","Cash", "Bank");
    public SuggessionBox sbxname;
    ObservableList<String> listname = FXCollections.observableArrayList();
    private static DecimalFormat df = new DecimalFormat(".##");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setSbox();
        setTable();
        listnerComboType();
        listner_FromDate();
        listner_ToDate();
        listner_sbxName();
        populatetable();
    }    
    
    private void listner_FromDate() {
        dpfrom.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
             populatetable();

            }
        });
    }
  
    private void listner_ToDate() {
        dpto.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
             populatetable();

            }
        });
    }
    
    private void listnerComboType() {
        cmbmode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                populatetable();
            }
        });
    }

    private void setSbox(){        
        sbxname = new SuggessionBox();
        hbxname.getChildren().add(sbxname);
        sbxname.setPrefSize(200, 25);
        sbxname.setStrict(true);
        cmbmode.setItems(combo_list);
        dpfrom.setValue(LocalDate.now().minusDays(1));
        dpto.setValue(LocalDate.now());
    }
    
    private void setTable() {
        colid.setCellValueFactory(new PropertyValueFactory<objExp, String>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<objExp, String>("date"));
        colname.setCellValueFactory(new PropertyValueFactory<objExp, String>("name"));
        colamt.setCellValueFactory(new PropertyValueFactory<objExp, String>("amt"));
        colmode.setCellValueFactory(new PropertyValueFactory<objExp, String>("mode"));
        colcmnt.setCellValueFactory(new PropertyValueFactory<objExp, String>("cmnt"));
        tblview.setItems(tblData);
    }
    
    private void populatetable() {
        tblData.clear();
        try {
            String strsql1 = "";
            String strsql2 = "";
            String combo = "";
            String name ="";
            if (!cmbmode.getSelectionModel().isEmpty()) {
                combo = cmbmode.getSelectionModel().getSelectedItem();
            }
            
            if (combo.equalsIgnoreCase("") || combo.equalsIgnoreCase("All")) {
                strsql1 = "";
            }
            else {
                strsql1 = " and p.suplier = '" + combo + "'";
            }
            
            if (!sbxname.getText().isEmpty()) {
                name = sbxname.getText();
            }
            
            if (sbxname.getText().equalsIgnoreCase("")) {
                strsql1 = "";
            }
            else {
                strsql2 = " and name = '" + name + "'";
            }
            Statement st = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_exp_register where status = 1 and date between '"+dpfrom.getValue()+"' and '"+dpto.getValue()+"' "+strsql2+" "+strsql1+"");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_exp_register where status = 1 and date between '"+dpfrom.getValue()+"' and '"+dpto.getValue()+"' "+strsql2+" "+strsql1+"");
            while (rs.next()) {
            tblData.add(new objExp(rs.getString("code"), rs.getString("date"), rs.getString("name"),rs.getString("amt"), rs.getString("mode"), rs.getString("cmnt")));
            }
            set_total();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(RptExpenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void listner_sbxName() {
        sbxname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listname.removeAll(listname);
                    try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_exp_master where status = 1 ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_exp_master where status = 1 ");
                        while (rs.next()) {
                            listname.add(rs.getString("name"));
                        }
                        sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(RptExpenceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    populatetable();
                }
            }
        });
    }
     
     public void set_total() {
        Double sum = 0.0;
        for (int i = 0; i < tblData.size(); i++) {
            objExp sm = tblData.get(i);
            Double amt = Double.parseDouble(sm.getAmt());
            sum = sum + amt;
        }
        if (tblData.size() > 0) {
            txttot.setText(df.format(sum));  
        } else {
            txttot.setText("0.0");
        }
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
            Chunk reportTitle= new Chunk("Expense Report",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph(" ", paragraphFont));
            pg.add(new Paragraph("Date From:  "+dpfrom.getValue().toString()+"   To :  "+dpto.getValue().toString(), head));
            pg.add(new Paragraph(" ", paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
            pg.setAlignment(Element.ALIGN_CENTER);
            //////////////////ADDD TABLE IN PDF///////////////////////////    
            PdfPTable table = new PdfPTable(6);
		float widths[] = { 20, 20, 30,25,16,40 };
		table.setWidths(widths);
		table.setHeaderRows(1);
            Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font content = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            /////////////// add cell of table - header cell///////////////////////
			PdfPCell cell = new PdfPCell(new Phrase("EXPENSE ID",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("DATE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
   
			cell = new PdfPCell(new Phrase("EXPENSE NAME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("AMOUNT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("MODE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("COMMENTS",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        Phrase ph;
	    //////////////// looping the table cell for adding definition/////////////////////////
                    int row = tblData.size();
                    for (int i = 0; i < row; i++)
                    {
                        objExp sm = tblData.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getId(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getDate(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getName(),content);
				cell.addElement(ph);
				table.addCell(cell);  

                                cell = new PdfPCell();
				ph = new Phrase(sm.getAmt(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getMode(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getCmnt(),content);
				cell.addElement(ph);
				table.addCell(cell);  
		    }                    
            Paragraph pp1 = new Paragraph();
            pp1.add(new Paragraph("", paragraphFont));
            pp1.add(new Paragraph("", paragraphFont));
            Chunk total= new Chunk("Total Amount :"+txttot.getText()+"                 ",heading);
            Phrase tot = new Phrase();
            tot.add(total);
            Paragraph paratot = new Paragraph();
            paratot.add(tot);
            paratot.setAlignment(Element.ALIGN_RIGHT);
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
                doc.add(paratot);
                doc.add(paraprint);

                doc.close();
                Desktop.getDesktop().open(file_temp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RptExpenceController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RptExpenceController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void Print_onaction(ActionEvent event) throws FileNotFoundException, DocumentException {
              try {
            File file_temp = File.createTempFile("ExpenseReport", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(RptExpenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage_exprpt) {
   this.stage= stage_exprpt;
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
                if (t.getCode() == KeyCode.F9) {
                    t.consume();
                   btn_print.fire();
                }
            }
        });       
 
}
}
