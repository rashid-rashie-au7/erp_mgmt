/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_StockRpt;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
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
public class StockRptController implements Initializable {
    private ObservableList<objStock> table_data_search = FXCollections.observableArrayList();
    private ObservableList<objtblMaster> table_master_search = FXCollections.observableArrayList();
    ObservableList<objStock> tblData = FXCollections.observableArrayList();
    ObservableList<objtblMaster> tblmaster = FXCollections.observableArrayList();
    private ObservableList<String> listGender = FXCollections.observableArrayList();
    private ObservableList<String> listwh = FXCollections.observableArrayList();
    @FXML
    private TableView<objStock> tableData;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colhsn;
    @FXML
    private TableColumn colcat;
    @FXML
    private TableColumn colbrand;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn coluom;
    @FXML
    private TableColumn colunits;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnexcel;
    @FXML
    private ComboBox<String> cmbCat;
    @FXML
    private TextField txtSearch;
    @FXML
    private CheckBox chkStock;
    DBMySQL db = new DBMySQL();
    Stage stage = new Stage();
    @FXML
    private ComboBox<String> cmbwh;
    @FXML
    private RadioButton rbtmaster;
    @FXML
    private RadioButton rbtsales;
    @FXML
    private AnchorPane anchrsales;
    @FXML
    private AnchorPane anchrmaster;
    @FXML
    private TableView<objtblMaster> tableData1;
    @FXML
    private TableColumn colid1;
    @FXML
    private TableColumn colname1;
    @FXML
    private TableColumn colhsn1;
    @FXML
    private TableColumn colcat1;
    @FXML
    private TableColumn colbrand1;
    @FXML
    private TableColumn colqty1;
    @FXML
    private TableColumn coluom1;
    @FXML
    private Button btnexcelmaster;
    @FXML
    private Button btnPrintmaster;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        setTable();
        setTablemaster();
        setsbox();
        populateCombo();
        populateCombowh();
        populate_table();
        populate_Master_table();
        listnerComboType();
        listner_radio_selection();
        listnerCombowh();
        listner_adv_name_search();
        listner_master_search();
        listener_checkbox();
       
    }  
    private void setTable() {
        colid.setCellValueFactory(new PropertyValueFactory<objStock, String>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<objStock, String>("name"));
        colhsn.setCellValueFactory(new PropertyValueFactory<objStock, String>("hsn"));
        colcat.setCellValueFactory(new PropertyValueFactory<objStock, String>("cat"));
        colbrand.setCellValueFactory(new PropertyValueFactory<objStock, String>("brand"));
        colqty.setCellValueFactory(new PropertyValueFactory<objStock, String>("qty"));
        coluom.setCellValueFactory(new PropertyValueFactory<objStock, String>("uom"));
        colunits.setCellValueFactory(new PropertyValueFactory<objStock, String>("units"));
        tableData.setItems(tblData);
    }
    
    private void setTablemaster() {
        colid1.setCellValueFactory(new PropertyValueFactory<objStock, String>("id"));
        colname1.setCellValueFactory(new PropertyValueFactory<objStock, String>("name"));
        colhsn1.setCellValueFactory(new PropertyValueFactory<objStock, String>("hsn"));
        colcat1.setCellValueFactory(new PropertyValueFactory<objStock, String>("cat"));
        colbrand1.setCellValueFactory(new PropertyValueFactory<objStock, String>("brand"));
        colqty1.setCellValueFactory(new PropertyValueFactory<objStock, String>("qty"));
        coluom1.setCellValueFactory(new PropertyValueFactory<objStock, String>("uom"));
        tableData1.setItems(tblmaster);
    }
    
    private void listner_radio_selection() {
        ToggleGroup grp =new ToggleGroup();
        rbtsales.setToggleGroup(grp);
        rbtmaster.setToggleGroup(grp);
        
        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                 if(rbtsales.isSelected()){
                     anchrmaster.setVisible(false);
                     anchrsales.setVisible(true);
                     tableData1.setVisible(false);
                     btnPrintmaster.setVisible(false);
                     btnexcelmaster.setVisible(false);
                     tableData.setVisible(true);
                     btnPrint.setVisible(true);
                     btnexcel.setVisible(true);
                    }else if(rbtmaster.isSelected()){
                    anchrmaster.setVisible(true);
                     anchrsales.setVisible(false);
                     tableData1.setVisible(true);
                     btnPrintmaster.setVisible(true);
                     btnexcelmaster.setVisible(true);
                     tableData.setVisible(false);
                     btnPrint.setVisible(false);
                     btnexcel.setVisible(false);
                    }
            }
        });
   
        
    }

    private void setsbox() { 
        cmbCat.setItems(listGender); 
        cmbCat.getSelectionModel().select(1);
        cmbwh.setItems(listwh);
        cmbwh.getSelectionModel().select(1);
        chkStock.setSelected(true);
       cmbCat.getSelectionModel().select("ALL");
       cmbwh.getSelectionModel().select("KERALA");
       rbtsales.setSelected(true);
       anchrmaster.setVisible(false);
       anchrsales.setVisible(true);
       tableData1.setVisible(false);
       btnPrintmaster.setVisible(false);
       btnexcelmaster.setVisible(false);
       tableData.setVisible(true);
       btnPrint.setVisible(true);
       btnexcel.setVisible(true);
    }
    private void listnerComboType() {
        cmbCat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                populate_table();
                populate_Master_table();
            }
        });
    }
    private void populateCombo(){
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_catgry ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_catgry ");
                        while (rs.next()) {
                            listGender.add(rs.getString("name"));
                        }
                       // sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
    private void listnerCombowh() {
        cmbwh.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                populate_table();
                populate_Master_table();
            }
        });
    }
    private void populateCombowh(){
      try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_wh ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_wh ");
                        while (rs.next()) {
                            listwh.add(rs.getString("name"));
                        }
                       // sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
    }
    
     private void listner_adv_name_search() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String txt = txtSearch.getText();
                if (txt.equals("")) {
                    tableData.setItems(tblData);
                } else {
                    for (objStock obj : tblData) {
                        if (obj.getName().toLowerCase().contains(txt.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tableData.setItems(table_data_search);
                   
                }
            }
        });
    }
     
    private void populate_table() {
         tblData.clear();
        try {
            String strsql1 = "";
            String strsql = "";
            String combo = "";
            String wh ="";
            String database="";
            if (!cmbwh.getSelectionModel().isEmpty()) {
                wh = cmbwh.getSelectionModel().getSelectedItem();
            }
            if (wh.equalsIgnoreCase("TAMILNADU")) {
                database = "tbl_item_tn";
            }
            else if (wh.equalsIgnoreCase("MAHARASHTRA")) {
                database = "tbl_item_mh";
            }else {
                database = "tbl_item";
            }
            if (!cmbCat.getSelectionModel().isEmpty()) {
                combo = cmbCat.getSelectionModel().getSelectedItem();
            }
            else{
                strsql1 = " and cat = '" + combo + "'";
            }
            
            if (combo.equalsIgnoreCase("ALL")) {
                strsql1 = "";
            }
            else {
                strsql1 = " and cat = '" + combo + "'";
            }
            if(chkStock.isSelected()){
                strsql="and stock > 0";
            } 
            else{
                strsql="";
            }

            Statement st = db.con.createStatement();
            System.out.println("select code,name,cat,hsn,brand,qty,unit,stock from " + db.schema + "."+database+" where status = 1 " + strsql1 + "" + strsql + "");
            ResultSet rs = st.executeQuery("select code,name,cat,hsn,brand,qty,unit,stock from " + db.schema + "."+database+" where status = 1 " + strsql1 + ""+strsql +"");

            while (rs.next()) {
                String id = rs.getString("code");
                String name = rs.getString("name");
                String cat = rs.getString("cat");
                String hsn = rs.getString("hsn");
                String brand = rs.getString("brand");
                String qty = rs.getString("qty");
                String uom = rs.getString("unit");
                String unit = rs.getString("stock");
                
                tblData.add(new objStock(id, name,hsn,cat,brand, qty, uom,unit));
                tableData.setItems(tblData);
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listner_master_search() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_master_search.removeAll(table_master_search);
                String txt = txtSearch.getText();
                if (txt.equals("")) {
                    tableData1.setItems(tblmaster);
                } else {
                    for (objtblMaster obj : tblmaster) {
                        if (obj.getName().toLowerCase().contains(txt.toLowerCase())) {
                            table_master_search.add(obj);
                        }
                    }
                    tableData1.setItems(table_master_search);
                   
                }
            }
        });
    }
     
    private void populate_Master_table() {
         tblmaster.clear();
        try {
            String strsql1 = "";
            String strsql = "";
            String combo = "";
            if (!cmbCat.getSelectionModel().isEmpty()) {
                combo = cmbCat.getSelectionModel().getSelectedItem();
            }
            
            if (combo.equalsIgnoreCase("All")) {
                strsql1 = "";
            }
            else {
                strsql1 = " and category = '" + combo + "'";
            }
            if(chkStock.isSelected()){
                strsql="and qty > 0";
            } 
            else{
                strsql="";
            }

            Statement st = db.con.createStatement();
            System.out.println("select code,name,category,hsn,brand,qty,unit from " + db.schema + ".tbl_item_master where status = 1 " + strsql1 + "" + strsql + "");
            ResultSet rs = st.executeQuery("select code,name,category,hsn,brand,qty,unit from " + db.schema + ".tbl_item_master where status = 1 " + strsql1 + ""+strsql +"");

            while (rs.next()) {
                String id = rs.getString("code");
                String name = rs.getString("name");
                String cat = rs.getString("category");
                String hsn = rs.getString("hsn");
                String brand = rs.getString("brand");
                String qty = rs.getString("qty");
                String uom = rs.getString("unit");
                
                tblmaster.add(new objtblMaster(id, name,hsn,cat,brand, qty, uom));
                tableData1.setItems(tblmaster);
            }
            st.close();
            
        }
        catch (SQLException ex) {
            Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listener_checkbox() {
        chkStock.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                populate_table();
                populate_Master_table();
                
            }
        });

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
            c12.setCellValue("Stock Report");
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
            c2.setCellValue("ITEM CODE");
            c2.setCellStyle(style1);
            sheet.setColumnWidth(0, 4000);

            HSSFCell c3 = rowhead.createCell(1);
            c3.setCellValue("ITEM NAME");
            c3.setCellStyle(style1);
            sheet.setColumnWidth(1, 8000);

            HSSFCell c4 = rowhead.createCell(2);
            c4.setCellValue("HSN");
            c4.setCellStyle(style1);
            sheet.setColumnWidth(2, 6500);

            HSSFCell c5 = rowhead.createCell(3);
            c5.setCellValue("CATEGORY");
            c5.setCellStyle(style1);
            sheet.setColumnWidth(3, 6500);

            HSSFCell c6 = rowhead.createCell(4);
            c6.setCellValue("BRAND");
            c6.setCellStyle(style1);
            sheet.setColumnWidth(4, 6500);
            
            HSSFCell c7 = rowhead.createCell(5);
            c7.setCellValue("QTY");
            c7.setCellStyle(style1);
            sheet.setColumnWidth(5, 5500);
            
             HSSFCell c8 = rowhead.createCell(6);
            c8.setCellValue("UoM");
            c8.setCellStyle(style1);
            sheet.setColumnWidth(6, 5500);

             HSSFCell c9 = rowhead.createCell(7);
            c9.setCellValue("No:of UNITS");
            c9.setCellStyle(style1);
            sheet.setColumnWidth(7, 6500);

            int row = tblData.size();

            for (int i = 0; i < row; i++) {

                HSSFRow exrow = sheet.createRow((short) i + 7);//starting from 4th row
                objStock sm = tblData.get(i);

                HSSFCell cell1 = exrow.createCell((int) 0);
                cell1.setCellValue(sm.getId());

                HSSFCell cell2 = exrow.createCell((int) 1);
                cell2.setCellValue(sm.getName());
                
                HSSFCell cell3 = exrow.createCell((int) 2);
                cell3.setCellValue(sm.getHsn());
                
                HSSFCell cell4 = exrow.createCell((int) 3);
                cell4.setCellValue(sm.getCat());
                
                HSSFCell cell5 = exrow.createCell((int) 4);
                cell5.setCellValue(sm.getBrand());
                
                HSSFCell cell6 = exrow.createCell((int) 5);
                cell6.setCellValue(sm.getQty());
                
                HSSFCell cell7 = exrow.createCell((int) 6);
                cell7.setCellValue(sm.getUom());
                
                HSSFCell cell8 = exrow.createCell((int) 7);
                cell8.setCellValue(sm.getUnits());

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
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
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
            Chunk reportTitle= new Chunk("STOCK REPORT",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph("Category : "+cmbCat.getSelectionModel().getSelectedItem(), paragraphFont));
            pg.add(new Paragraph("WAREHOUSE : "+cmbwh.getSelectionModel().getSelectedItem(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));

            pg.setAlignment(Element.ALIGN_CENTER);
//        doc.add(para);
//        doc.add(para1);
//        doc.add(pg);
/////////////////////////////ADDD TABLE IN PDF////////////////////////////////////////     

        PdfPTable table = new PdfPTable(8);
			float widths[] = { 20, 40, 16,20,18,12,10,20 };
			table.setWidths(widths);
                        table.setWidthPercentage(100);
			table.setHeaderRows(1);
        Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        Font content = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
        // add cell of table - header cell
			PdfPCell cell = new PdfPCell(new Phrase("ITEM CODE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("ITEM NAME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("HSN",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("CATEGORY",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("BRAND",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("QTY",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("UoM",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("No:of UNITS",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        Phrase ph;
			// looping the table cell for adding definition
                        int row = tblData.size();

                        for (int i = 0; i < row; i++)
                         {
                        objStock sm = tblData.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getId(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getName(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getHsn(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getCat(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getBrand(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getQty(),content);
				cell.addElement(ph);
				table.addCell(cell); 
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getUom(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getUnits(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
			}                     
            Paragraph pp1 = new Paragraph();
            pp1.add(new Paragraph("", paragraphFont));
            pp1.add(new Paragraph("", paragraphFont));
//            Chunk total= new Chunk("Total Amount :"+txttotal.getText()+"                 ",heading);
//            Phrase tot = new Phrase();
//            tot.add(total);
//            Paragraph paratot = new Paragraph();
//            paratot.add(tot);
//            paratot.setAlignment(Element.ALIGN_RIGHT);
            
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
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void printOnAction(ActionEvent event) throws FileNotFoundException, DocumentException {
           try {
            File file_temp = File.createTempFile("StockReport", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void excelOnAction(ActionEvent event) {
        try {
            File file_tmp = File.createTempFile("StockReport+", ".xls");
            exportToEXCEL(file_tmp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage_stockrpt) {
       this.stage= stage_stockrpt;
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
                   btnPrint.fire();
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

    @FXML
    private void excelmasterOnAction(ActionEvent event) {
           try {
            File file_tmp = File.createTempFile("StockMasterReport+", ".xls");
            exportToEXCEL(file_tmp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void printmasterOnAction(ActionEvent event) throws FileNotFoundException, DocumentException {
        try {
            File file_temp = File.createTempFile("StockMasterReport", ".pdf");
            Createpdfmaster(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void exportToEXCELMaster(File file) {
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
            c12.setCellValue("Stock Master Report");
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
            c2.setCellValue("ITEM CODE");
            c2.setCellStyle(style1);
            sheet.setColumnWidth(0, 4000);

            HSSFCell c3 = rowhead.createCell(1);
            c3.setCellValue("ITEM NAME");
            c3.setCellStyle(style1);
            sheet.setColumnWidth(1, 8000);

            HSSFCell c4 = rowhead.createCell(2);
            c4.setCellValue("HSN");
            c4.setCellStyle(style1);
            sheet.setColumnWidth(2, 6500);

            HSSFCell c5 = rowhead.createCell(3);
            c5.setCellValue("CATEGORY");
            c5.setCellStyle(style1);
            sheet.setColumnWidth(3, 6500);

            HSSFCell c6 = rowhead.createCell(4);
            c6.setCellValue("BRAND");
            c6.setCellStyle(style1);
            sheet.setColumnWidth(4, 6500);
            
            HSSFCell c7 = rowhead.createCell(5);
            c7.setCellValue("QTY");
            c7.setCellStyle(style1);
            sheet.setColumnWidth(5, 5500);
            
             HSSFCell c8 = rowhead.createCell(6);
            c8.setCellValue("UoM");
            c8.setCellStyle(style1);
            sheet.setColumnWidth(6, 5500);

            int row = tblmaster.size();

            for (int i = 0; i < row; i++) {

                HSSFRow exrow = sheet.createRow((short) i + 7);//starting from 4th row
                objtblMaster sm = tblmaster.get(i);

                HSSFCell cell1 = exrow.createCell((int) 0);
                cell1.setCellValue(sm.getId());

                HSSFCell cell2 = exrow.createCell((int) 1);
                cell2.setCellValue(sm.getName());
                
                HSSFCell cell3 = exrow.createCell((int) 2);
                cell3.setCellValue(sm.getHsn());
                
                HSSFCell cell4 = exrow.createCell((int) 3);
                cell4.setCellValue(sm.getCat());
                
                HSSFCell cell5 = exrow.createCell((int) 4);
                cell5.setCellValue(sm.getBrand());
                
                HSSFCell cell6 = exrow.createCell((int) 5);
                cell6.setCellValue(sm.getQty());
                
                HSSFCell cell7 = exrow.createCell((int) 6);
                cell7.setCellValue(sm.getUom());
  

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
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    
    private void Createpdfmaster(File file_temp) throws FileNotFoundException, DocumentException, IOException{
        
            Document doc =new Document(PageSize.A4, 20, 20, 20, 20);
            Font smallfont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
//////////////////////////////////ADDD HEADING IN PDF////////////////////////////////////////
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font head = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
            Chunk title= new Chunk("LOOMS & WEAVES",head);
            Phrase tit = new Phrase();
            tit.add(title);
            Paragraph paratit = new Paragraph();
            paratit.add(tit);
            paratit.setAlignment(Element.ALIGN_CENTER);
            Chunk reportTitle= new Chunk("STOCK MASTER REPORT",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph("Category : "+cmbCat.getSelectionModel().getSelectedItem(), paragraphFont));
            pg.add(new Paragraph(" ", paragraphFont));
//            pg.add(new Paragraph(" ", paragraphFont));

            pg.setAlignment(Element.ALIGN_CENTER);
//        doc.add(para);
//        doc.add(para1);
//        doc.add(pg);
/////////////////////////////ADDD TABLE IN PDF////////////////////////////////////////     

        PdfPTable table = new PdfPTable(7);
			float widths[] = { 20, 40, 16,20,18,16,16 };
			table.setWidths(widths);
                        table.setWidthPercentage(100);
			table.setHeaderRows(1);
        Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        Font content = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
        // add cell of table - header cell
			PdfPCell cell = new PdfPCell(new Phrase("ITEM CODE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("ITEM NAME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("HSN",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("CATEGORY",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("BRAND",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("QTY",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("UoM",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
     
                        Phrase ph;
			// looping the table cell for adding definition
                        int row = tblmaster.size();

                        for (int i = 0; i < row; i++)
                         {
                        objtblMaster sm = tblmaster.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getId(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getName(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getHsn(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getCat(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getBrand(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getQty(),content);
				cell.addElement(ph);
				table.addCell(cell); 
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getUom(),content);
				cell.addElement(ph);
				table.addCell(cell);

                                
			}                     
            Paragraph pp1 = new Paragraph();
            pp1.add(new Paragraph("", paragraphFont));
            pp1.add(new Paragraph("", paragraphFont));
//            Chunk total= new Chunk("Total Amount :"+txttotal.getText()+"                 ",heading);
//            Phrase tot = new Phrase();
//            tot.add(total);
//            Paragraph paratot = new Paragraph();
//            paratot.add(tot);
//            paratot.setAlignment(Element.ALIGN_RIGHT);
            
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
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StockRptController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    }
    

