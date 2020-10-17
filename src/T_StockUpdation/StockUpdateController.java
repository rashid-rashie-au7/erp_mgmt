/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_StockUpdation;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.miw.control.sbox.SuggessionBox;
import database.DBMySQL;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class StockUpdateController implements Initializable {

    @FXML
    private TextField txtMcode;
    @FXML
    private TextField txtMqty;
    @FXML
    private TextField txtbatch;
    @FXML
    private TextField txtcode;
    @FXML
    private TextField txtqty;
    @FXML
    private DatePicker dpExpiry;
    @FXML
    private TextField txtwholesale;
    @FXML
    private TextField txtretail;
    @FXML
    private TextField txtbarcode;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Color x21;
    @FXML
    private Font x11;
    @FXML
    private Button btnBarcode;
    DBMySQL db = new DBMySQL();
    private Stage stage = new Stage(StageStyle.UTILITY);
    @FXML
    private TableView<objstock> tblView;
    @FXML
    private TableColumn colcode;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colqty;
    @FXML
    private TableColumn colunit;
    @FXML
    private TableColumn colexp;
    @FXML
    private TableColumn colbatch;
    @FXML
    private TableColumn colbarcode;
    @FXML
    private TableColumn colwhole;
    @FXML
    private TableColumn colretail;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
    @FXML
    private Hyperlink hpl_Remove;
    @FXML
    private Button btnclearItem;
    public SuggessionBox sbxunit;
    public SuggessionBox sbxitem;
    ObservableList<String> listunit = FXCollections.observableArrayList();
    ObservableList<String> listitem = FXCollections.observableArrayList();
    @FXML
    private HBox hbxUnit;
    @FXML
    private HBox hbxname;
     ObservableList<objstock> tblData = FXCollections.observableArrayList();
    @FXML
    private HBox hbxitem;
    public SuggessionBox sbxname;
    ObservableList<String> listname = FXCollections.observableArrayList();
    @FXML
    private TextField txtunits;
    @FXML
    private TextField txtuom;
    @FXML
    private TableColumn colstock;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
            setSbox();
            listner_txtbx();
            listner_sbxName();
            listner_sbxitem();
            listner_sbxUom();
            clear();
            setTable();
            lisnterTableClick();
          
    }    
    
    private void clear(){
       sbxname.clear();
       sbxunit.clear();
       txtqty.clear();
       dpExpiry.setValue(LocalDate.now());
       txtwholesale.setText("0.0");
       txtretail.setText("0.0");
       txtbarcode.clear();   
       txtcode.setText("");
       txtunits.clear();
       
    }
    
    private void clearall(){
        clear();
        txtMcode.clear();
        txtMqty.clear();
        txtbatch.clear();
        sbxitem.clear();
        txtuom.clear();
        tblData.removeAll(tblData);
    }
    
     private void setSbox(){
        sbxitem = new SuggessionBox();
        sbxitem.setPrefSize(180, 25);
        hbxname.getChildren().add(sbxitem);
        hbxname.setPrefSize(180, 25);
        sbxunit = new SuggessionBox();
        sbxunit.setPrefSize(192, 25);
        hbxUnit.getChildren().add(sbxunit);
        sbxname = new SuggessionBox();
        sbxname.setPrefSize(192, 25);
        hbxitem.getChildren().add(sbxname);
     }
   
    private void listner_sbxitem() {
        sbxitem.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listitem.removeAll(listitem);
                    try {
                        Statement st = db.con.createStatement();
                        System.out.println("select name from " + db.schema + ".tbl_item_master where status = 1 ");
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_item_master where status = 1 ");
                        while (rs.next()) {
                            listitem.add(rs.getString("name"));
                        }
                        sbxitem.setData(listitem);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        sbxitem.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    setvalue();
                    sbxname.requestFocus();
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
                        ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_item where status = 1");
                        while (rs.next()) {
                            listname.add(rs.getString("name"));
                        }
                        sbxname.setData(listname);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        sbxname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   txtunits.requestFocus();
                   setfields();
                }
            }
        });
    }
    
    private void setfields() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select code,unit,rate,mrp,qty from " + db.schema + ".tbl_item where name = '" + sbxname.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select code,unit,rate,mrp,qty from " + db.schema + ".tbl_item where name = '" + sbxname.getText() + "' ");
                while (rst.next()) {
                   txtcode.setText(rst.getString("code"));
                   sbxunit.setText(rst.getString("unit"));
                   txtwholesale.setText(rst.getString("rate"));
                   txtretail.setText(rst.getString("mrp"));
                   txtqty.setText(rst.getString("qty"));
                }
                txtqty.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setvalue() {
        System.out.println(" inside value");
        try {
            try (Statement stmt = db.con.createStatement()) {
                System.out.println("select code,qty,batch from " + db.schema + ".tbl_item_master where name = '" + sbxitem.getText() + "' ");
                ResultSet rst = stmt.executeQuery("select  code,qty,unit,batch from " + db.schema + ".tbl_item_master where name = '" + sbxitem.getText() + "' ");
                while (rst.next()) {
                   txtMcode.setText(rst.getString("code"));
                   txtMqty.setText(rst.getString("qty"));
                   txtbatch.setText(rst.getString("batch"));
                   txtuom.setText(rst.getString("unit"));
                   
                }
//                txtqty.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listner_sbxUom() {
        sbxunit.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    listunit.removeAll(listunit);
                    try {
                        Statement st = db.con.createStatement();
                        ResultSet rs = st.executeQuery("select shcode from " + db.schema + ".tbl_unit where status = 1");
                        while (rs.next()) {
                            listunit.add(rs.getString("shcode"));
                        }
                        sbxunit.setData(listunit);
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        sbxunit.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    dpExpiry.requestFocus();
                }
            }
        });
    }

     
    private void barcode(File file_temp) throws FileNotFoundException, IOException, BadElementException, DocumentException {
        Document doc =new Document(PageSize.A4, 5, 5, 5, 5);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        Code128Bean code128 = new Code128Bean();
        code128.setHeight(15f);
        code128.setModuleWidth(0.2);
        code128.setQuietZone(1);
        code128.doQuietZone(true);
        for (objstock obj : tblData) {
                if (obj.getCode().equals("") && obj.getName().equals("") && obj.getQty().equals("") && obj.getUnit().equals("") && obj.getExp().equals("") && obj.getBatch().equals("") && obj.getBarcode().equals("") && obj.getWhole().equals("") && obj.getRetail().equals("")) {
                    continue;
                }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 400, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        code128.generateBarcode(canvas, obj.getBarcode());
        canvas.finish();
        Image png = Image.getInstance(baos.toByteArray());
        png.setAbsolutePosition(0, 250);
        png.scalePercent(25);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.setTotalWidth(4);          
            Integer qty = Integer.parseInt(obj.getStock());
            Integer totqty =0;
            System.out.println("qty==="+qty); 
            if (qty % 4 == 0) {
               totqty= qty; 
            }else{
                Double qtyyyyy = Double.valueOf(qty)/4;
                double number = qtyyyyy;
                int integer = (int)number;
                double decimal = (10 * number - 10 * integer)/10;
                System.out.println(integer);
                totqty = (integer+1)*4;
                System.out.println(totqty);
                System.out.println(decimal);
            }        
        for (int aw = 0; aw <= totqty; aw++) {
            BaseFont baseFont5 = BaseFont.createFont(this.getClass().getResource("teko-semibold.ttf").toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font font4 = new com.itextpdf.text.Font(baseFont5, 9);
            com.itextpdf.text.Font printer = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font namefont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 7, com.itextpdf.text.Font.NORMAL);
            Paragraph p = new Paragraph("  looms & weaves (LAW)",printer);
            Paragraph p1 = new Paragraph("MRP: ₹"+obj.getRetail(),font4);
            Paragraph pp = new Paragraph("Special Price:  ₹"+obj.getWhole(),font4);
            Paragraph p2 = new Paragraph(obj.getName(),namefont);
            pp.setAlignment(Element.ALIGN_CENTER);
            PdfPTable intable = new PdfPTable(1);
            intable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            intable.addCell(p);
            intable.addCell(p2);
            intable.addCell(p1);
            intable.addCell(pp); 
            intable.addCell(png);
            intable.getDefaultCell().setBorder(2); 
            intable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED_ALL);
            table.addCell(intable);
        }
        }
        try {
                PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream(file_temp));
                doc.open();     
                doc.add(table);
                Desktop.getDesktop().open(file_temp);
//                Desktop.getDesktop().print(file_temp);
                doc.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void createtxt() throws DocumentException, IOException, PrintException{
        File dir = new  File("D:/Barcode");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }        
         File file = new  File("D:/Barcode/inputFile.prn");
        try {
           
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
           
        }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        String content = "SIZE 103.1 mm, 30 mm\n" +
                         "DIRECTION 0,0\n" +
                         "REFERENCE 0,0\n" +
                         "OFFSET 0 mm\n" +
                         "SET PEEL OFF\n" +
                         "SET CUTTER OFF\n" +
                         "SET PARTIAL_CUTTER OFF\n" +
                         "SET TEAR ON\n" +
                         "CLS\n" +
                         "CODEPAGE 1252\n";  
         bw.write(content);
        for (objstock obj : tblData) {
                if (obj.getCode().equals("") && obj.getName().equals("") && obj.getQty().equals("") && obj.getUnit().equals("") && obj.getExp().equals("") && obj.getBatch().equals("") && obj.getBarcode().equals("") && obj.getWhole().equals("") && obj.getRetail().equals("")) {
                    continue;
                }        
            Integer qty = Integer.parseInt(obj.getStock());
            Integer totqty =0;
            System.out.println("qty==="+qty); 
            if (qty % 2 == 0) {
                System.out.println(qty%2);
               totqty= qty/2; 
            }else{
                Double qtyyyyy = Double.valueOf(qty)/4;
                double number = qtyyyyy;
                int integer = (int)number;
                double decimal = (10 * number - 10 * integer)/10;
                System.out.println(integer);
                totqty = (integer+1)*2;
                System.out.println(totqty);
                System.out.println(decimal);
            }        
        for (int aw = 0; aw <= totqty; aw+=2) {
            String barcode = "TEXT 1200,320,\"0\",180,11,11,\"looms & weaves (LAW)\"\n" +
                            "TEXT 1200,220,\"0\",180,8,11,\"MRP :" +obj.getRetail()+"\"\n" +
//                            "TEXT 1200,260,\"0\",180,8,6,\""+obj.getName()+"\"\n" +
                            "TEXT 1200,165,\"0\",180,11,11,\"Special Price:"+obj.getWhole()+"\"\n" +
                            "BARCODE 1200,110,\"93\",52,0,180,2,4,\""+obj.getBarcode()+"\"\n" +
                            "TEXT 1200,40,\"0\",180,9,9,\""+obj.getBarcode()+"\"\n" +
                            "TEXT 550,320,\"0\",180,11,11,\"looms & weaves (LAW)\"\n" +
                            "TEXT 550,220,\"0\",180,8,11,\"MRP :" +obj.getRetail()+"\"\n" +
//                            "TEXT 550,260,\"0\",180,6,6,\""+obj.getName()+"\"\n" +
                            "TEXT 550,165,\"0\",180,11,11,\"Special Price:"+obj.getWhole()+"\"\n" +
                            "BARCODE 550,110,\"93\",52,0,180,2,4,\""+obj.getBarcode()+"\"\n" +
                            "TEXT 550,40,\"0\",180,9,9,\""+obj.getBarcode()+"\"\n";        
        bw.write(barcode);
        System.out.println("Done");
        }
        }
        String print = "PRINT 1,1";
        bw.write(print);
        
         bw.close();
         
    } catch (IOException e) {
        e.printStackTrace();
    }
    PrinterJob job = PrinterJob.getPrinterJob();

            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            String choice = getDefaultPrinter();
            if (choice != null) {
                  for (PrintService p : printServices) {
                        if (choice.equalsIgnoreCase(p.getName())) {
                              try {
                                    System.out.println("..." + p);
                                    job.setPrintService(p);
                              }
                              catch (PrinterException ex) {
                                    Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                              }
                        }
                        else {
                              try {
                                  System.out.println("job.setPrintService(service)");
                                    job.setPrintService(service);
                              }
                              catch (PrinterException ex) {
                                    Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                              }
                        }
                  }
                  try {
                      FileInputStream fis = new FileInputStream("D:/Barcode/inputFile.prn");
    Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
    DocPrintJob printJob =service.createPrintJob();
    printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
    fis.close();      
//                      System.out.println((Printable) file);
//                         job.setPrintable((Printable) file);
//                         System.out.println("job.print");
//                                 job.print();
                                 
                  }
                  catch (Exception ex) {
                      System.out.println("print not sucessful"+ex);
//                Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
                /* The job did not successfully complete */
                  }
            }
            else {
                  Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select Defalut Printer in Printer Settings ", ButtonType.YES, ButtonType.NO);
                  alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    
                }
            }
    }
    
    public String getDefaultPrinter() {
            try {
                  Statement st = db.con.createStatement();
                  ResultSet rs = st.executeQuery("select defaultPrinter from " + db.schema + ".printerSettings where settingsId = 2");
                  if (rs.next()) {
                        return rs.getString("defaultPrinter");
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
      }
    
    
   private void addtable(){
       System.out.println("inside ");
//       String master = txtMqty.getText();
        String code = txtcode.getText();
        String name = sbxname.getText().toUpperCase();
        String qty = txtqty.getText();
        String unit = sbxunit.getText();
        String stock = txtunits.getText();
        String exp = dpExpiry.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        String batch = txtbatch.getText();
        String barcode = txtbarcode.getText().toUpperCase();
        String whole = txtwholesale.getText();
        String retail = txtretail.getText();    
        setqty(); 
        objstock obj = new objstock(code,name, qty, unit,stock,exp,batch,barcode,whole,retail);
        
        tblView.getItems().add(obj);      
        clear(); 
   }
   
   private void setqty(){
       Double Mqty= Double.parseDouble(txtMqty.getText());
       Double qty=Double.parseDouble(txtqty.getText());
       Double noofunits= Double.parseDouble(txtunits.getText());
       Double sum=0.0;
       Double calcunit=0.0;
       String Munit = txtuom.getText();
       String unit = sbxunit.getText();
       System.out.println("Master unit===  "+Munit);
     System.out.println(" unit===  "+unit);
       if(Munit.equals(unit)){
           sum = Mqty - (qty*noofunits);
           txtMqty.setText(sum.toString());
       }else if (Munit.equals("Kg") && unit.equals("Gm")){
           System.out.println("inside if");
           calcunit = (qty * noofunits)/1000;
           sum = Mqty - calcunit;
           txtMqty.setText(sum.toString());
       }else if (Munit.equals("L") && unit.equals("Ml")){
           System.out.println("inside if");
           calcunit = (qty * noofunits)/1000;
           sum = Mqty - calcunit;
           txtMqty.setText(sum.toString());
       }
           
//       sum = Mqty - qty;
//       txtMqty.setText(sum.toString());
   }
   
   private void listner_txtbx(){
        sbxname.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtqty.requestFocus();
                }
            }
        });
        
        txtqty.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtunits.requestFocus();
                }
            }
        });
        txtunits.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    sbxunit.requestFocus();
                }
            }
        });
        dpExpiry.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtwholesale.requestFocus();
                }
            }
        });
        txtwholesale.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtretail.requestFocus();
                }
            }
        });
        txtretail.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    txtbarcode.requestFocus();
                    txtbarcode.setText(txtcode.getText()+txtbatch.getText());
                }
            }
        });
        txtbarcode.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   if(validation()){
                       productExistInTbl(sbxname.getText());
                    sbxname.requestFocus();   
                   }
                    
                }
            }
        });      
    }
   
   private boolean table_invalid() {
        boolean flag = true;
        int count = 0;
        for (objstock obj : tblData) {
             count++;
            if (obj.getCode().equals("") || obj.getName().equals("") || obj.getQty().equals("") || obj.getUnit().equals("") || obj.getExp().equals("") || obj.getBatch().equals("") || obj.getBarcode().equals("") || obj.getWhole().equals("") || obj.getRetail().equals("")) {
                if (obj.getCode().equals("") && obj.getName().equals("") && obj.getQty().equals("") && obj.getUnit().equals("") && obj.getExp().equals("") && obj.getBatch().equals("") && obj.getBarcode().equals("") && obj.getWhole().equals("") && obj.getRetail().equals("")) {                  
                flag = false;
                } else {
                    return true;
                }
            } else {
                if (count == tblData.size()) {
                    return false;
                }
            }
        }
        return flag;
    }
   
   private boolean validation(){
        boolean flag = true;
        final String regExp = "[0-9]+([.][0-9]{1,2})?";
        final Pattern pattern = Pattern.compile(regExp);
        Matcher matchr_price = pattern.matcher(txtretail.getText());
        Matcher matchr_whole = pattern.matcher(txtwholesale.getText());
        
        if (sbxitem.getText().equalsIgnoreCase("")) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select Master Item ", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    sbxitem.requestFocus();
                    flag= false;
                    }  
        } else if (sbxname.getText().equalsIgnoreCase("")) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Item Name", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    sbxname.requestFocus();
                     flag= false;
                    }          
        }else if (sbxunit.getText().equalsIgnoreCase("")) {          
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select a Unit", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    sbxunit.requestFocus();
                     flag= false;
                    }          
        }  
        else if (txtqty.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Qty", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtqty.requestFocus();
                     flag= false;
                    }  
        }else if (!txtqty.getText().equals("")) {
           if (!matchr_price.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter  Valid Qty", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtqty.requestFocus();
                     flag= false;
                    }  
            }
        }  else if (txtwholesale.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Wholesale Rate", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtwholesale.requestFocus();
                     flag= false;
                    }  
        }else if (!txtwholesale.getText().equals("")) {
           if (!matchr_whole.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter  Valid Wholsesale Price", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtwholesale.requestFocus();
                     flag= false;
                    }  
            }
        }  else if (txtretail.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Retail Price", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtretail.requestFocus();
                     flag= false;
                    }  
        }else if (!txtretail.getText().equals("")) {
           if (!matchr_price.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter  Valid Retail Price", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    txtretail.requestFocus();
                     flag= false;
                    }  
            }
        }  
        return flag;
        
    }
   
   private void setTable() {
//       colmaster.setCellValueFactory(new PropertyValueFactory<objstock, String>("code"));
        colcode.setCellValueFactory(new PropertyValueFactory<objstock, String>("code"));
        colname.setCellValueFactory(new PropertyValueFactory<objstock, String>("name"));
        colqty.setCellValueFactory(new PropertyValueFactory<objstock, String>("qty"));
        colunit.setCellValueFactory(new PropertyValueFactory<objstock, String>("unit"));
        colstock.setCellValueFactory(new PropertyValueFactory<objstock, String>("stock"));
        colexp.setCellValueFactory(new PropertyValueFactory<objstock, String>("exp"));
        colbatch.setCellValueFactory(new PropertyValueFactory<objstock, String>("batch"));
        colbarcode.setCellValueFactory(new PropertyValueFactory<objstock, String>("barcode"));
        colwhole.setCellValueFactory(new PropertyValueFactory<objstock, String>("whole"));
        colretail.setCellValueFactory(new PropertyValueFactory<objstock, String>("retail"));
        tblView.setItems(tblData);
    }
   
   private void remove_row() {
        System.out.println("inside remove");
        int row = tblView.getSelectionModel().getSelectedIndex();
        if (tblData.size() > 0) {
            System.out.println("rooowwww  " + row);
            Double Mqty= Double.parseDouble(txtMqty.getText());
            Double qty= Double.parseDouble(tblView.getSelectionModel().getSelectedItem().getQty());
            Double stock=Double.parseDouble(tblView.getSelectionModel().getSelectedItem().getStock());
            String masterUoM = txtuom.getText();
            String uom = sbxunit.getText();
            if (masterUoM.equals(uom)) {       
            Double tot = Mqty + stock;
            txtMqty.setText(tot.toString()); 
            } else {
            Double tot = (qty*stock)/1000;
            Double totqty = Mqty + tot;
            txtMqty.setText(totqty.toString());
            }
            tblData.remove(row);
            tblView.getSelectionModel().clearSelection();
        } else {
            tblData.set(row, new objstock("", "", "", "","","","","","",""));
//            set_total();
            tblView.getSelectionModel().clearSelection();
        }
    }
   
   private boolean save() {
        boolean flag = false;
        int bool = 0;
        try {
            Statement st = db.con.createStatement();
            for (objstock obj : tblData) {
                if (obj.getCode().equals("") && obj.getName().equals("") && obj.getQty().equals("") && obj.getUnit().equals("") && obj.getExp().equals("") && obj.getBatch().equals("") && obj.getBarcode().equals("") && obj.getWhole().equals("") && obj.getRetail().equals("")) {
                    continue;
                }
                System.out.println("update " + db.schema + ".tbl_item set qty= "+obj.getQty()+", unit ='"+obj.getUnit()+"',rate ="+obj.getWhole()+",mrp ="+obj.getRetail()+", batch= '"+obj.getBatch()+"',barcode= '"+obj.getBarcode()+"', expdate= '"+obj.getExp()+"',stock=stock +"+obj.getStock()+" where code ='" +obj.getCode()+ "' and name ='"+obj.getName()+"' ");
                bool = st.executeUpdate("update " + db.schema + ".tbl_item set qty= "+obj.getQty()+", unit ='"+obj.getUnit()+"',rate ="+obj.getWhole()+",mrp ="+obj.getRetail()+", batch= '"+obj.getBatch()+"',barcode= '"+obj.getBarcode()+"', expdate= '"+obj.getExp()+"',stock=stock +"+obj.getStock()+" where code ='" +obj.getCode()+ "' and name ='"+obj.getName()+"' ");
       }
            if (bool > 0) {
                flag = true;
                updateStock();
                
                
            }
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Stock Sucessfully Updated", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                clearall();
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }


    
    @FXML
    private void barcode_onAction(ActionEvent event) throws FileNotFoundException, DocumentException, IOException, PrintException {
//        try {
//            File file_temp = File.createTempFile("Barcode", ".pdf");
//            barcode(file_temp);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        createtxt();

        
    }

    @FXML
    private void btnsave_onaction(ActionEvent event) {
        if (table_invalid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Table,Please add items", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                sbxname.requestFocus();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save this Stocks", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {

                save();


            }
        }
    }
    
    private void updateStock() {
        int bool = 0;
        try {
            Statement st = db.con.createStatement();

            System.out.println("update " + db.schema + ".tbl_item_master set qty='"+txtMqty.getText()+"'  where code ='" + txtMcode.getText() + "' ");
            bool = st.executeUpdate("update " + db.schema + ".tbl_item_master set qty= '"+txtMqty.getText()+"' where code ='" + txtMcode.getText() + "'");

            if (bool > 0) {

            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(StockUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void lisnterTableClick() {
        tblView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2 && !tblView.getSelectionModel().isEmpty()) {
                    fetch(tblView.getSelectionModel().getSelectedItem());
                }
            }

        });

    }

    private void fetch(objstock obj) {
        txtcode.setText(obj.getCode());
        sbxname.setText(obj.getName());
        txtqty.setText(obj.getQty());
        sbxunit.setText(obj.getUnit());
        txtwholesale.setText(obj.getWhole());
        txtretail.setText(obj.getRetail());
        txtbarcode.setText(obj.getBarcode());
        dpExpiry.setValue(LocalDate.parse(obj.getExp()));
        remove_row();

    }

    private void productExistInTbl(String item) {
         System.out.println("inside exixst function");
         System.out.println("inside exixst function===========  "+item);
        if(tblData.size()>0){
            for (objstock obj : tblData) {
            System.out.println("inside for function===========  "+item);  
            if (obj.getName().equalsIgnoreCase(item)) {
                System.out.println("inside exixst function  if");
                Alert alert = new Alert(Alert.AlertType.ERROR, " Item Already Exist In The Table ", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {  
                    sbxname.requestFocus();
                }
            } else
            {
                System.out.println("inside exixst function elseeee");
                addtable();
            }
            }     
        } else{
            addtable();
        }
   }
    
    @FXML
    private void clear_onAction(ActionEvent event) {
        clearall();
    }

    @FXML
    private void hpl_onAction(ActionEvent event) {
        if (tblView.getSelectionModel().getSelectedItem() != null) {
            remove_row();
        }
    }
    
     public void setStage(Stage stage_stock) {
              this.stage= stage_stock;
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

    @FXML
    private void clearItem_onAction(ActionEvent event) {
        clear();
    }
    
}
