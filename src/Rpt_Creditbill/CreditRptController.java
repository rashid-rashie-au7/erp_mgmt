/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Creditbill;


import M_Item.ItemCreationController;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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
public class CreditRptController implements Initializable {

    @FXML
    private TableView<objtbl> tableData;
    @FXML
    private TableColumn colno;
    @FXML
    private TableColumn colclient;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn colpaid;
    @FXML
    private TableColumn colbal;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnexcel;
    @FXML
    private HBox hbxname;
    ObservableList<objtbl> tblData = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    Stage stage = new Stage();
    @FXML
    private TableColumn colbillamt;
    @FXML
    private TableColumn colamt;
    public SuggessionBox sbxbillno;
    public SuggessionBox sbxname;
    ObservableList<String> listbill = FXCollections.observableArrayList();
    ObservableList<String> listname = FXCollections.observableArrayList();
    @FXML
    private HBox hbxbill;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        setTable();
        setSbox();
        populate_table();
        listner_sbxbill();
        listner_sbxName();
    }  
    
    private void setSbox(){
        
        sbxbillno = new SuggessionBox();
        hbxbill.getChildren().add(sbxbillno);
        sbxname = new SuggessionBox();
        hbxname.getChildren().add(sbxname);
        sbxbillno.setPrefSize(175, 25);
        sbxname.setPrefSize(175, 25);
    }
    
    private void setTable() {
        colno.setCellValueFactory(new PropertyValueFactory<objtbl, String>("bill"));
        colclient.setCellValueFactory(new PropertyValueFactory<objtbl, String>("client"));
        coldate.setCellValueFactory(new PropertyValueFactory<objtbl, String>("date"));
        colbillamt.setCellValueFactory(new PropertyValueFactory<objtbl, String>("biilamt"));
        colpaid.setCellValueFactory(new PropertyValueFactory<objtbl, String>("paid"));
        colamt.setCellValueFactory(new PropertyValueFactory<objtbl, String>("amt"));
        colbal.setCellValueFactory(new PropertyValueFactory<objtbl, String>("bal"));
       
        tableData.setItems(tblData);
    }
    
    private void listner_sbxbill() {
        sbxbillno.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listbill.removeAll(listbill);
                    try {
                        Statement st = db.con.createStatement();
                        System.out.println("select billno from " + db.schema + ".tbl_credit ");
                        ResultSet rs = st.executeQuery("select billno from " + db.schema + ".tbl_credit ");
                        while (rs.next()) {
                            listbill.add(rs.getString("billno"));
                        }
                        sbxbillno.setData(listbill);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ItemCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxbillno.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   populate_tableBill();
                }
            }
        });
    }

    private void listner_sbxName() {
        sbxname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listname.removeAll(listname);
                    try {
                        Statement st = db.con.createStatement();
                        System.out.println("select client from " + db.schema + ".tbl_credit");
                        ResultSet rs = st.executeQuery("select client from " + db.schema + ".tbl_credit");
                        while (rs.next()) {
                            listname.add(rs.getString("client"));
                        }
                        sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ItemCreationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        sbxname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    populate_tableName();
                    
                }
            }
        });
    }
        private void populate_tableBill() {
         tblData.clear();
        try {
            Statement st = db.con.createStatement();
            System.out.println("select billno,client,date,billamt,paid,amt,bal from " + db.schema + ".tbl_credit where billno ='"+sbxbillno.getText()+"'");
            ResultSet rs = st.executeQuery("select billno,client,date,billamt,paid,amt,bal from " + db.schema + ".tbl_credit  where billno ='"+sbxbillno.getText()+"' ");

            while (rs.next()) {
                String billno = rs.getString("billno");
                String name = rs.getString("client");
                String date = rs.getString("date");
                String billamt = rs.getString("billamt");
                String paid = rs.getString("paid");
                String amt = rs.getString("amt");
                String bal = rs.getString("bal");
                
                tblData.add(new objtbl(billno,name,date,billamt,paid,amt,bal));
                tableData.setItems(tblData);
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            private void populate_tableName() {
         tblData.clear();
        try {
            Statement st = db.con.createStatement();
            System.out.println("select billno,client,date,billamt,paid,amt,bal from " + db.schema + ".tbl_credit where client = '"+sbxname.getText()+"' ");
            ResultSet rs = st.executeQuery("select billno,client,date,billamt,paid,amt,bal from " + db.schema + ".tbl_credit where client = '"+sbxname.getText()+"' ");

            while (rs.next()) {
                String billno = rs.getString("billno");
                String name = rs.getString("client");
                String date = rs.getString("date");
                String billamt = rs.getString("billamt");
                String paid = rs.getString("paid");
                String amt = rs.getString("amt");
                String bal = rs.getString("bal");
                
                tblData.add(new objtbl(billno,name,date,billamt,paid,amt,bal));
                tableData.setItems(tblData);
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    private void populate_table() {
         tblData.clear();
        try {
            Statement st = db.con.createStatement();
            System.out.println("select billno,client,date,billamt,paid,amt,bal from " + db.schema + ".tbl_credit ");
            ResultSet rs = st.executeQuery("select billno,client,date,billamt,paid,amt,bal from " + db.schema + ".tbl_credit ");

            while (rs.next()) {
                String billno = rs.getString("billno");
                String name = rs.getString("client");
                String date = rs.getString("date");
                String billamt = rs.getString("billamt");
                String paid = rs.getString("paid");
                String amt = rs.getString("amt");
                String bal = rs.getString("bal");
                
                tblData.add(new objtbl(billno,name,date,billamt,paid,amt,bal));
                tableData.setItems(tblData);
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
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

            HSSFCell c11 = rowhead1.createCell(0);
            c11.setCellValue(db.schema.toUpperCase());
            c11.setCellStyle(style2);

            HSSFRow rowhead2 = sheet.createRow((short) 1);
            sheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    17 //last column  (0-based)
            ));

            HSSFCell c12 = rowhead2.createCell(0);
            c12.setCellValue("Credit Report");
            c12.setCellStyle(style2);

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
            HSSFCell c13 = rowhead3.createCell(0);
            c13.setCellValue("");
            c13.setCellStyle(style1);

            HSSFRow rowhead = sheet.createRow((short) 5);

            HSSFCell c2 = rowhead.createCell(0);
            c2.setCellValue("BILL NO ");
            c2.setCellStyle(style1);
            sheet.setColumnWidth(0, 4000);

            HSSFCell c3 = rowhead.createCell(1);
            c3.setCellValue("CLIENT");
            c3.setCellStyle(style1);
            sheet.setColumnWidth(1, 8000);

            HSSFCell c4 = rowhead.createCell(2);
            c4.setCellValue("DATE");
            c4.setCellStyle(style1);
            sheet.setColumnWidth(2, 6500);

            HSSFCell c5 = rowhead.createCell(3);
            c5.setCellValue("BILL AMOUNT");
            c5.setCellStyle(style1);
            sheet.setColumnWidth(3, 6500);

            HSSFCell c6 = rowhead.createCell(4);
            c6.setCellValue("PAIDED AMOUNT ");
            c6.setCellStyle(style1);
            sheet.setColumnWidth(4, 6500);
            
            HSSFCell c7 = rowhead.createCell(5);
            c7.setCellValue("AMOUNT PAID");
            c7.setCellStyle(style1);
            sheet.setColumnWidth(5, 5500);
            
             HSSFCell c8 = rowhead.createCell(6);
            c8.setCellValue("BALANCE AMOUNT");
            c8.setCellStyle(style1);
            sheet.setColumnWidth(6, 5500);

            int row = tblData.size();

            for (int i = 0; i < row; i++) {

                HSSFRow exrow = sheet.createRow((short) i + 7);//starting from 4th row
                objtbl sm = tblData.get(i);

                HSSFCell cell1 = exrow.createCell((int) 0);
                cell1.setCellValue(sm.getBill());

                HSSFCell cell2 = exrow.createCell((int) 1);
                cell2.setCellValue(sm.getClient());
                
                HSSFCell cell3 = exrow.createCell((int) 2);
                cell3.setCellValue(sm.getDate());
                
                HSSFCell cell4 = exrow.createCell((int) 3);
                cell4.setCellValue(sm.getBiilamt());
                
                HSSFCell cell5 = exrow.createCell((int) 4);
                cell5.setCellValue(sm.getPaid());
                
                HSSFCell cell6 = exrow.createCell((int) 5);
                cell6.setCellValue(sm.getAmt());
                
                HSSFCell cell7 = exrow.createCell((int) 6);
                cell7.setCellValue(sm.getBal());
                

            }

            HSSFRow rowhead6 = sheet.createRow((short) row + 7);
//            sheet.addMergedRegion(new CellRangeAddress(
//                    row + 6, //first row (0-based)
//                    row + 6, //last row  (0-based)
//                    0, //first column (0-based)
//                    6 //last column  (0-based)
//                    ));
//            HSSFCell cell1Row7 = rowhead6.createCell(4);
//            cell1Row7.setCellValue("Total Grand Total : " + txttotal.getText());

            try {
                FileOutputStream fileOut = null;
                fileOut = new FileOutputStream(file);
                hwb.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
    private void Createpdf(File file_temp) throws FileNotFoundException, DocumentException, IOException{
        
            Document doc =new Document(PageSize.A4, 20, 20, 20, 20);
            Font smallfont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
//////////////////////////////////ADDD HEADING IN PDF////////////////////////////////////////
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font head = new Font(Font.FontFamily.COURIER, 24, Font.BOLD);
            Chunk title= new Chunk("LOOMS & WEAVES",head);
            Phrase tit = new Phrase();
            tit.add(title);
            Paragraph paratit = new Paragraph();
            paratit.add(tit);
            paratit.setAlignment(Element.ALIGN_CENTER);
            Chunk reportTitle= new Chunk("CREDIT BILL REPORT",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
//            pg.add(new Paragraph("Category : "+cmbCat.getSelectionModel().getSelectedItem(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
//            pg.add(new Paragraph(" ", paragraphFont));

            pg.setAlignment(Element.ALIGN_CENTER);
//        doc.add(para);
//        doc.add(para1);
//        doc.add(pg);
/////////////////////////////ADDD TABLE IN PDF////////////////////////////////////////     

        PdfPTable table = new PdfPTable(7);
			float widths[] = { 25, 35, 25,25,25,25,25 };
			table.setWidths(widths);
                        table.setWidthPercentage(100);
			table.setHeaderRows(1);
        Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font content = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
        // add cell of table - header cell
			PdfPCell cell = new PdfPCell(new Phrase("BILL NO",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("CLIENT NAME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("DATE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("BILL AMOUNT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("TOTAL PAID AMOUNT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("AMOUNT PAID",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("BALANCE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        Phrase ph;
			// looping the table cell for adding definition
                        int row = tblData.size();

                        for (int i = 0; i < row; i++)
                         {
                        objtbl sm = tblData.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getBill(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getClient(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getDate(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getBiilamt(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getPaid(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getAmt(),content);
				cell.addElement(ph);
				table.addCell(cell); 
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getBal(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                

			}                     
            Paragraph pp1 = new Paragraph();
            pp1.add(new Paragraph("", paragraphFont));
            pp1.add(new Paragraph("", paragraphFont));

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
                //doc.add(para1);
                doc.add(pg);
                doc.add(table);
                doc.add(pp1);
//                doc.add(paratot);
                doc.add(paraprint);
//                doc.addCreator("Durga Softech");
//                doc.bottom(print);
                doc.close();
                Desktop.getDesktop().open(file_temp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void printOnAction(ActionEvent event) throws FileNotFoundException, DocumentException {
          try {
            File file_temp = File.createTempFile("CreditReport", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void excelOnAction(ActionEvent event) {
        try {
            File file_tmp = File.createTempFile("Credit report+", ".xls");
            exportToEXCEL(file_tmp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(CreditRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage_creditrpt) {
      this. stage =stage_creditrpt;
    }
    
}
