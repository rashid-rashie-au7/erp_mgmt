/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Purchase;


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
import javafx.scene.control.TextField;
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
public class Po_rptController implements Initializable {

    @FXML
    private TableView<objpo> tableData;
    @FXML
    private TableColumn colno;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn colsup;
    @FXML
    private TableColumn colinvoice;
    @FXML
    private TableColumn colitems;
    @FXML
    private TableColumn colgrand;
    @FXML
    private TableColumn colgst;
    @FXML
    private TableColumn colnet;
    @FXML
    private TableColumn colstatus;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnexcel;
    @FXML
    private ComboBox<String> cmbsup;
    @FXML
    private DatePicker dpfrom;
    @FXML
    private DatePicker dpto;
    @FXML
    private TextField txtgrand;
    @FXML
    private TextField txtgst;
    @FXML
    private TextField txttot;
    ObservableList<String> listType = FXCollections.observableArrayList();
    DBMySQL db = new DBMySQL();
    Stage stage = new Stage();
     ObservableList<objpo> tblData = FXCollections.observableArrayList();

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
        colno.setCellValueFactory(new PropertyValueFactory<objpo, String>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<objpo, String>("date"));
        colsup.setCellValueFactory(new PropertyValueFactory<objpo, String>("sup"));
        colinvoice.setCellValueFactory(new PropertyValueFactory<objpo, String>("invoice"));
        colitems.setCellValueFactory(new PropertyValueFactory<objpo, String>("items"));
        colgrand.setCellValueFactory(new PropertyValueFactory<objpo, String>("grand"));
        colgst.setCellValueFactory(new PropertyValueFactory<objpo, String>("gst"));
        colnet.setCellValueFactory(new PropertyValueFactory<objpo, String>("net"));
        colstatus.setCellValueFactory(new PropertyValueFactory<objpo, String>("status"));
        tableData.setItems(tblData);
    }

    private void setsbox() { 
        dpfrom.setValue(LocalDate.now());
        dpto.setValue(LocalDate.now());
        cmbsup.setItems(listType); 
        cmbsup.getSelectionModel().select(0);
        
    }
    private void populateCombo(){
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_supplier ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_supplier ");
                        while (rs.next()) {
                            listType.add(rs.getString("name"));
                        }
                       // sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    private void listnerComboType() {
        cmbsup.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
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
         tblData.clear();
        try {
            String strsql1 = "";
            String combo = "";
            if (!cmbsup.getSelectionModel().isEmpty()) {
                combo = cmbsup.getSelectionModel().getSelectedItem();
            }
            
            if (combo.equalsIgnoreCase("")) {
                strsql1 = "";
            }
            else {
                strsql1 = " and p.suplier = '" + combo + "'";
            }

            Statement st = db.con.createStatement();
            System.out.println("select distinct p.code,p.date,p.suplier,p.invoice,p.grandtot,p.gst,p.total,p.po_status,(select count(code) from " + db.schema + ".tbl_po_items where code= p.code)  as items from " + db.schema + ".tbl_po p  where p.date between '"+dpfrom.getValue()+"' and '"+dpto.getValue()+"' "+strsql1+"");
            ResultSet rs = st.executeQuery("select distinct p.code,p.date,p.suplier,p.invoice,p.grandtot,p.gst,p.total,p.po_status,(select count(code) from " + db.schema + ".tbl_po_items where code= p.code) as items from " + db.schema + ".tbl_po p  where  p.date between '"+dpfrom.getValue()+"' and '"+dpto.getValue()+"' "+strsql1+"");

            while (rs.next()) {
                String no = rs.getString("code");
                String date = rs.getString("date");
                String sup = rs.getString("suplier");
                String item = rs.getString("items");
                String grand = rs.getString("grandtot");
                String invo = rs.getString("invoice");
                String gst = rs.getString("gst");
                String net = rs.getString("total");
                String status = rs.getString("po_status");
                String sts="";
                if(status.equals("0"))
                {
                   sts="Open" ;
                }else if(status.equals("1")){
                    sts="Closed" ;
                }
                tblData.add(new objpo(no,date,sup,invo,item,grand,gst, net,sts));
                tableData.setItems(tblData);
                settxtbx();
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void set_grand() {
        Double grand =0.0;
        for (int i = 0; i < tblData.size(); i++) {
            objpo sm = tblData.get(i);
            double amt = Double.parseDouble(sm.getGrand());
            grand = grand + amt;

        }
        if (tblData.size() > 0) {
            txtgrand.setText(grand.toString());

        } else {
            txtgrand.setText("");

        }
    }
    
    public void set_gst() {
        Double gst =0.0;
        for (int i = 0; i < tblData.size(); i++) {
            objpo sm = tblData.get(i);
            double amt = Double.parseDouble(sm.getGst());
            gst = gst + amt;

        }
        if (tblData.size() > 0) {
            txtgst.setText(gst.toString());

        } else {
            txtgst.setText("");

        }
    }
    
    public void set_net() {
        Double net =0.0;
        for (int i = 0; i < tblData.size(); i++) {
            objpo sm = tblData.get(i);
            double amt = Double.parseDouble(sm.getNet());
            net = net + amt;

        }
        if (tblData.size() > 0) {
            txttot.setText(net.toString());

        } else {
            txttot.setText("");

        }
    }

    private void settxtbx(){
        set_grand();
        set_gst();
        set_net();
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

            HSSFCell c12 = rowhead1.createCell(0);
            c12.setCellValue(db.schema.toUpperCase());
            c12.setCellStyle(style2);

            HSSFRow rowhead2 = sheet.createRow((short) 1);
            sheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    17 //last column  (0-based)
            ));

            HSSFCell c13 = rowhead2.createCell(0);
            c13.setCellValue("Purchase Report");
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
            c2.setCellValue("PURCHASE ID");
            c2.setCellStyle(style1);
            sheet.setColumnWidth(0, 3000);

            HSSFCell c3 = rowhead.createCell(1);
            c3.setCellValue("DATE");
            c3.setCellStyle(style1);
            sheet.setColumnWidth(1, 4000);

            HSSFCell c4 = rowhead.createCell(2);
            c4.setCellValue("SUPPLIER");
            c4.setCellStyle(style1);
            sheet.setColumnWidth(2, 8000);

            HSSFCell c5 = rowhead.createCell(3);
            c5.setCellValue("INVOICE");
            c5.setCellStyle(style1);
            sheet.setColumnWidth(3, 7000);

            HSSFCell c6 = rowhead.createCell(4);
            c6.setCellValue("ITEMS");
            c6.setCellStyle(style1);
            sheet.setColumnWidth(4, 7000);
            
            HSSFCell c7 = rowhead.createCell(5);
            c7.setCellValue("GRAND TOTAL");
            c7.setCellStyle(style1);
            sheet.setColumnWidth(5, 7000);
            
             HSSFCell c8 = rowhead.createCell(6);
            c8.setCellValue("GST AMOUNT");
            c8.setCellStyle(style1);
            sheet.setColumnWidth(6, 7000);

             HSSFCell c9 = rowhead.createCell(7);
            c9.setCellValue("NET AMOUNT");
            c9.setCellStyle(style1);
            sheet.setColumnWidth(7, 7000);
            
            HSSFCell c10 = rowhead.createCell(8);
            c10.setCellValue("STATUS");
            c10.setCellStyle(style1);
            sheet.setColumnWidth(8, 7000);
            
           int row = tblData.size();

            for (int i = 0; i < row; i++) {

                HSSFRow exrow = sheet.createRow((short) i + 7);//starting from 4th row
                objpo sm = tblData.get(i);

                HSSFCell cell1 = exrow.createCell((int) 0);
                cell1.setCellValue(sm.getId());

                HSSFCell cell2 = exrow.createCell((int) 1);
                cell2.setCellValue(sm.getDate());
                
                HSSFCell cell3 = exrow.createCell((int) 2);
                cell3.setCellValue(sm.getSup());
                
                HSSFCell cell4 = exrow.createCell((int) 3);
                cell4.setCellValue(sm.getInvoice());
                
                HSSFCell cell5 = exrow.createCell((int) 4);
                cell5.setCellValue(sm.getItems());
                
                HSSFCell cell6 = exrow.createCell((int) 5);
                cell6.setCellValue(sm.getGrand());
                
                HSSFCell cell7 = exrow.createCell((int) 6);
                cell7.setCellValue(sm.getGst());
                
                HSSFCell cell8 = exrow.createCell((int) 7);
                cell8.setCellValue(sm.getNet());
                
                HSSFCell cell9 = exrow.createCell((int) 8);
                cell9.setCellValue(sm.getStatus());

            }

            HSSFRow rowhead6 = sheet.createRow((short) row + 7);
//            sheet.addMergedRegion(new CellRangeAddress(
//                    row + 6, //first row (0-based)
//                    row + 6, //last row  (0-based)
//                    0, //first column (0-based)
//                    6 //last column  (0-based)
//                    ));
            HSSFCell cell1Row7 = rowhead6.createCell(4);
            cell1Row7.setCellValue(" Grand Total : " + txtgrand.getText());
            HSSFCell cell1Row8 = rowhead6.createCell(4);
            cell1Row8.setCellValue("Total GST : " + txtgst.getText());
            HSSFCell cell1Row9 = rowhead6.createCell(4);
            cell1Row9.setCellValue("Total Net Amount : " + txttot.getText());

            try {
                FileOutputStream fileOut = null;
                fileOut = new FileOutputStream(file);
                hwb.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
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
            Chunk reportTitle= new Chunk("PURCHASE REPORT",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph("From Date : "+dpfrom.getValue()+"     To Date :  "+dpto.getValue(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
            pg.add(new Paragraph("Payment Mode : "+cmbsup.getSelectionModel().getSelectedItem(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
            pg.setAlignment(Element.ALIGN_CENTER);
/////////////////////////////ADDD TABLE IN PDF////////////////////////////////////////     

        PdfPTable table = new PdfPTable(9);
			float widths[] = { 18,18,30,15,20,20,20,20,16 };
			table.setWidths(widths);
                        table.setWidthPercentage(100);
			table.setHeaderRows(1);
        Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font content = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
        // add cell of table - header cell
			PdfPCell cell = new PdfPCell(new Phrase("PO ID",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("DATE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("SUPPLIER",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("INVOICE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("ITEMS",heading));
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
                        
                        cell = new PdfPCell(new Phrase("STATUS",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        Phrase ph;
			// looping the table cell for adding definition
                        int row = tblData.size();

                        for (int i = 0; i < row; i++)
                         {
                        objpo sm = tblData.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getId(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getDate(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getSup(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getInvoice(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getItems(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getGrand(),content);
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
				ph = new Phrase(sm.getStatus(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
			}                     
            Paragraph pp1 = new Paragraph();
            pp1.add(new Paragraph("", paragraphFont));
            pp1.add(new Paragraph("", paragraphFont));
            Chunk grand= new Chunk("Grand Total :"+txtgrand.getText()+"                 ",heading);
            Phrase grant = new Phrase();
            grant.add(grand);
            Chunk gst= new Chunk("Total GST :"+txtgst.getText()+"                 ",heading);
            Phrase gstamt = new Phrase();
            gstamt.add(gst);
            Chunk net= new Chunk("Net Amount :"+txttot.getText()+"                 ",heading);
            Phrase netamt = new Phrase();
            netamt.add(net);
            
            Paragraph paratot = new Paragraph();
            paratot.add(grant);
            paratot.add(gstamt);
            paratot.add(netamt);
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
                Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void printOnAction(ActionEvent event) throws FileNotFoundException, DocumentException {
           try {
            File file_temp = File.createTempFile("Purchase Report", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void excelOnAction(ActionEvent event) {
         try {
            File file_tmp = File.createTempFile("Purchase Report+", ".xls");
            exportToEXCEL(file_tmp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Po_rptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage_porpt) {
       this.stage = stage_porpt;
    }
    
}
