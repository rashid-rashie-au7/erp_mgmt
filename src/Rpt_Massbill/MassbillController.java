/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Massbill;

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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class MassbillController implements Initializable {

    @FXML
    private DatePicker dpfrom;
    @FXML
    private ComboBox<String> cmbagent;
    @FXML
    private DatePicker dpto;
    @FXML
    private TableView<objtbl> tbldata;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn coltime;
    @FXML
    private TableColumn colbill;
    @FXML
    private TableColumn colsgst;
    @FXML
    private TableColumn colcgst;
    @FXML
    private TableColumn coligst;
    @FXML
    private TableColumn coltot;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colnet;
    @FXML
    private TableColumn colwh;
    @FXML
    private TableColumn colagent;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnexcel;
    ObservableList<String> listType = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    Stage stage = new Stage();
     ObservableList<objtbl> tablelData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setsbox();
       setTable();
       populateCombo();
       populate_table();
       listner_fromdate();
       listner_todate();
       listnerComboType();
    }    
    
    private void setTable() {
        coldate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<objtbl, String>("time"));
        colbill.setCellValueFactory(new PropertyValueFactory<objtbl, String>("bill"));
        colsgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("sgst"));
        colcgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("cgst"));
        coligst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("igst"));
        coltot.setCellValueFactory(new PropertyValueFactory<objtbl, String>("tot"));
        colgst.setCellValueFactory(new PropertyValueFactory<objtbl, String>("gst"));
        colnet.setCellValueFactory(new PropertyValueFactory<objtbl, String>("net"));
        colwh.setCellValueFactory(new PropertyValueFactory<objtbl, String>("wh"));
        colagent.setCellValueFactory(new PropertyValueFactory<objtbl, String>("agent"));
        tbldata.setItems(tablelData);
    }

    private void setsbox() { 
        dpfrom.setValue(LocalDate.now());
        dpto.setValue(LocalDate.now());
        cmbagent.setItems(listType); 
        cmbagent.getSelectionModel().select(null);
        
    }

    private void populateCombo(){
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_agent ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_agent ");
                        while (rs.next()) {
                            listType.add(rs.getString("name"));
                        }
                       // sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    private void listnerComboType() {
        cmbagent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                populate_table();
            }
        });
    }
    
        private void listner_fromdate() {
        dpfrom.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
               populate_table();
            }
        });
    }
        
        private void listner_todate() {
        dpto.valueProperty().addListener(new ChangeListener<LocalDate>()  {
            @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
               populate_table();
            }
        });
    }
    
    private void populate_table() {
         tablelData.clear();
        try {
            String strsql1 = "";
            String combo = "";
            if (!cmbagent.getSelectionModel().isEmpty()) {
                combo = cmbagent.getSelectionModel().getSelectedItem();
            }
            
            if (combo.equalsIgnoreCase("")) {
                strsql1 = "";
            }
            else {
                strsql1 = " and agent = '" + combo + "'";
            }

            Statement st = db.con.createStatement();
            System.out.println("select * from " + db.schema + ".tbl_mass_bill where date between '"+dpfrom.getValue()+"' and '"+dpto.getValue()+"' "+strsql1+" ");
            ResultSet rs = st.executeQuery("select * from " + db.schema + ".tbl_mass_bill where date between '"+dpfrom.getValue()+"' and '"+dpto.getValue()+"' "+strsql1+" ");
            while (rs.next()) {
                String date = rs.getString("date");
                String time = rs.getString("time");
                String bill = rs.getString("tot_row");
                String sgst = rs.getString("sgst");
                String cgst = rs.getString("cgst");
                String igst = rs.getString("igst");
                String grand = rs.getString("grand_tot");
                String gst = rs.getString("gst");
                String net = rs.getString("netamt");
                String wh = rs.getString("wh");
                String agent = rs.getString("agent");
                tablelData.add(new objtbl(date,time,bill,sgst,cgst,igst,grand, gst,net,wh,agent));
                tbldata.setItems(tablelData);
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void exportToEXCEL(File file) {
        if (file != null) {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFCellStyle style1 = hwb.createCellStyle();
            HSSFCellStyle style2 = hwb.createCellStyle();
            HSSFSheet sheet = hwb.createSheet(db.schema);

            HSSFFont font = hwb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFFont font1 = hwb.createFont();
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font1.setFontHeightInPoints((short) 14);

            style1.setFont(font);
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            style2.setFont(font1);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            HSSFRow rowhead1 = sheet.createRow((short) 0);
            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    17 //last column  (0-based)
            ));

            HSSFCell c1l3 = rowhead1.createCell(0);
            c1l3.setCellValue(db.schema.toUpperCase());
            c1l3.setCellStyle(style2);

            HSSFRow rowhead2 = sheet.createRow((short) 1);
            sheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    17 //last column  (0-based)
            ));

            HSSFCell c13 = rowhead2.createCell(0);
            c13.setCellValue("Mass Billing Report");
            c13.setCellStyle(style2);

            HSSFRow rowhead3 = sheet.createRow((short) 2);
            sheet.addMergedRegion(new CellRangeAddress(
                    2, //first row (0-based)
                    2, //last row  (0-based)
                    0, //first column (0-based)
                    17 //last column  (0-based)
            ));

            HSSFRow rowhead4 = sheet.createRow((short) 3);
            sheet.addMergedRegion(new CellRangeAddress(
                    3, //first row (0-based)
                    3, //last row  (0-based)
                    0, //first column (0-based)
                    1 //last column  (0-based)
            ));

            HSSFCell c16 = rowhead4.createCell(0);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            c16.setCellValue("From Date : " +date);
            c16.setCellStyle(style1);

            HSSFRow rowhead5 = sheet.createRow((short) 4);
            sheet.addMergedRegion(new CellRangeAddress(
                    4, //first row (0-based)
                    4, //last row  (0-based)
                    0, //first column (0-based)
                    17 //last column  (0-based)
            ));
            HSSFCell c14 = rowhead3.createCell(0);
            c14.setCellValue("");
            c14.setCellStyle(style1);

            HSSFRow rowhead = sheet.createRow((short) 5);

            HSSFCell c2 = rowhead.createCell(0);
            c2.setCellValue("DATE");
            c2.setCellStyle(style1);
            sheet.setColumnWidth(0, 5000);

            HSSFCell c3 = rowhead.createCell(1);
            c3.setCellValue("TIME");
            c3.setCellStyle(style1);
            sheet.setColumnWidth(1, 4000);

            HSSFCell c4 = rowhead.createCell(2);
            c4.setCellValue("NO:OF BILLS");
            c4.setCellStyle(style1);
            sheet.setColumnWidth(2, 8000);

            HSSFCell c5 = rowhead.createCell(3);
            c5.setCellValue("SGST");
            c5.setCellStyle(style1);
            sheet.setColumnWidth(3, 7000);

            HSSFCell c6 = rowhead.createCell(4);
            c6.setCellValue("CGST");
            c6.setCellStyle(style1);
            sheet.setColumnWidth(4, 7000);
            
            HSSFCell c7 = rowhead.createCell(5);
            c7.setCellValue("IGST");
            c7.setCellStyle(style1);
            sheet.setColumnWidth(5, 7000);
            
             HSSFCell c8 = rowhead.createCell(6);
            c8.setCellValue("GRAND TOTAL");
            c8.setCellStyle(style1);
            sheet.setColumnWidth(6, 7000);

             HSSFCell c9 = rowhead.createCell(7);
            c9.setCellValue("GST AMOUNT");
            c9.setCellStyle(style1);
            sheet.setColumnWidth(7, 7000);
            
            HSSFCell c10 = rowhead.createCell(8);
            c10.setCellValue("NET AMOUNT");
            c10.setCellStyle(style1);
            sheet.setColumnWidth(8, 7000);
            
            HSSFCell c11 = rowhead.createCell(9);
            c11.setCellValue("WARE HOUSE");
            c11.setCellStyle(style1);
            sheet.setColumnWidth(9, 8000);
            
            HSSFCell c12 = rowhead.createCell(10);
            c12.setCellValue("AGENT");
            c12.setCellStyle(style1);
            sheet.setColumnWidth(10, 9000);
            
           int row = tablelData.size();

            for (int i = 0; i < row; i++) {

                HSSFRow exrow = sheet.createRow((short) i + 7);//starting from 4th row
                objtbl sm = tablelData.get(i);

                HSSFCell cell1 = exrow.createCell((int) 0);
                cell1.setCellValue(sm.getDate());

                HSSFCell cell2 = exrow.createCell((int) 1);
                cell2.setCellValue(sm.getTime());
                
                HSSFCell cell3 = exrow.createCell((int) 2);
                cell3.setCellValue(sm.getBill());
                
                HSSFCell cell4 = exrow.createCell((int) 3);
                cell4.setCellValue(sm.getSgst());
                
                HSSFCell cell5 = exrow.createCell((int) 4);
                cell5.setCellValue(sm.getCgst());
                
                HSSFCell cell6 = exrow.createCell((int) 5);
                cell6.setCellValue(sm.getIgst());
                
                HSSFCell cell7 = exrow.createCell((int) 6);
                cell7.setCellValue(sm.getTot());
                
                HSSFCell cell8 = exrow.createCell((int) 7);
                cell8.setCellValue(sm.getGst());
                
                HSSFCell cell9 = exrow.createCell((int) 8);
                cell9.setCellValue(sm.getNet());
                
                HSSFCell cell10 = exrow.createCell((int) 9);
                cell10.setCellValue(sm.getWh());
                
                HSSFCell cell11 = exrow.createCell((int) 10);
                cell11.setCellValue(sm.getAgent());
            }

            HSSFRow rowhead6 = sheet.createRow((short) row + 7);
//            sheet.addMergedRegion(new CellRangeAddress(
//                    row + 6, //first row (0-based)
//                    row + 6, //last row  (0-based)
//                    0, //first column (0-based)
//                    6 //last column  (0-based)
//                    ));
//            HSSFCell cell1Row7 = rowhead6.createCell(4);
//            cell1Row7.setCellValue(" Grand Total : " + txtgrand.getText());
//            HSSFCell cell1Row8 = rowhead6.createCell(4);
//            cell1Row8.setCellValue("Total GST : " + txtgst.getText());
//            HSSFCell cell1Row9 = rowhead6.createCell(4);
//            cell1Row9.setCellValue("Total Net Amount : " + txttot.getText());

            try {
                FileOutputStream fileOut = null;
                fileOut = new FileOutputStream(file);
                hwb.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
    private void Createpdf(File file_temp) throws FileNotFoundException, DocumentException, IOException{
        
            Document doc =new Document(PageSize.A4, 20, 20, 20, 20);
            Font smallfont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
//////////////////////////////////ADDD HEADING IN PDF////////////////////////////////////////
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
//     Image img = Image.getInstance("src/images/logo.png");
//     img.setAlignment(Element.ALIGN_CENTER);
            Font head = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
            Chunk title= new Chunk("LOOMS & WEAVES",head);
            Phrase tit = new Phrase();
            tit.add(title);
            Paragraph paratit = new Paragraph();
            paratit.add(tit);
            paratit.setAlignment(Element.ALIGN_CENTER);
            Chunk reportTitle= new Chunk("MASS BILL REPORT",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph("From Date : "+dpfrom.getValue()+"     To Date :  "+dpto.getValue(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
            pg.add(new Paragraph("Payment Mode : "+cmbagent.getSelectionModel().getSelectedItem(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
            pg.setAlignment(Element.ALIGN_CENTER);
/////////////////////////////ADDD TABLE IN PDF////////////////////////////////////////     

        PdfPTable table = new PdfPTable(11);
			float widths[] = { 18,18,25,20,20,20,20,20,25,25 };
			table.setWidths(widths);
                        table.setWidthPercentage(100);
			table.setHeaderRows(1);
        Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font content = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
        // add cell of table - header cell
			PdfPCell cell = new PdfPCell(new Phrase("DATE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("TIME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("NO:OF BILLS",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("SGST",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("CGST",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("IGST",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("GRAND TOTAL",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("GST AMOUNT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("NET AMOUNT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("WARE HOUSE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        cell = new PdfPCell(new Phrase("AGENT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        Phrase ph;
			// looping the table cell for adding definition
                        int row = tablelData.size();

                        for (int i = 0; i < row; i++)
                         {
                        objtbl sm = tablelData.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getDate(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getTime(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getBill(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getSgst(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getCgst(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getIgst(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getTot(),content);
				cell.addElement(ph);
				table.addCell(cell); 
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getGst(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getNet(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getWh(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getAgent(),content);
				cell.addElement(ph);
				table.addCell(cell);
			}                     
            Paragraph pp1 = new Paragraph();
            pp1.add(new Paragraph("", paragraphFont));
            pp1.add(new Paragraph("", paragraphFont));
            Paragraph paratot = new Paragraph();
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
                doc.add(pp1);
                doc.add(paratot);
                doc.add(paraprint);
                doc.close();
                Desktop.getDesktop().open(file_temp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @FXML
    private void printOnAction(ActionEvent event) throws FileNotFoundException, DocumentException {
         try {
            File file_temp = File.createTempFile("Purchase Report", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void excelOnAction(ActionEvent event) {
         try {
            File file_tmp = File.createTempFile("Purchase Report+", ".xls");
            exportToEXCEL(file_tmp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MassbillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage_massrpt) {
        this.stage = stage_massrpt;
    }
    
}
