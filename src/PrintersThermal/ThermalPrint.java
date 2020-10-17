/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrintersThermal;

import database.DBMySQL;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialogs;
import javafx.stage.Stage;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 *
 * @author miw101
 */
public class ThermalPrint {
    
    

      private Stage stage;
      Font monospaced7 = new Font("Monospaced", Font.BOLD, 7);
      Font monospaced9 = new Font("Monospaced", Font.PLAIN, 9);
      Font monospaced9B = new Font("Monospaced", Font.BOLD, 9);
      Font times11 = new Font("Times New Roman", Font.PLAIN, 11);
       Font times8 = new Font("Times New Roman", Font.PLAIN, 8);
      Font times16 = new Font("Times New Roman", Font.BOLD, 16);
       Font times13 = new Font("Times New Roman", Font.BOLD,9 );
       Font times9 = new Font("Times New Roman", Font.PLAIN,9 );

      Font times14 = new Font("Times New Roman", Font.BOLD, 14);
      PrintDataObject pdo;
      DBMySQL db = new DBMySQL();

      public void printCard(PrintDataObject pdo_fet) throws PrinterException {

            pdo = pdo_fet;




            Printable contentToPrint = new Printable() {
                  @Override
                  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {


                        Graphics2D g2d = (Graphics2D) graphics;

                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                        g2d.setFont(monospaced7);


                        if (pageIndex > 0) {
                              return NO_SUCH_PAGE;
                        } //Only one page



                        int y = 30;
                        g2d.setFont(times14);
                        g2d.drawString(pdo.getCoDetails().get("Co Name"), 60, y);
                        y += 12;
                        g2d.setFont(times13);
                        g2d.drawString(pdo.getCoDetails().get("Address"), 55, y);
                        y += 11;
                        g2d.setFont(times11);
                        g2d.drawString(pdo.getCoDetails().get("Address2"), 43, y);
                        y += 11;
                        g2d.drawString(pdo.getCoDetails().get("Phone"), 60, y);
                        y += 11;
                        g2d.setFont(times8);
                        g2d.drawString(pdo.getCoDetails().get("Tin"), 60, y);
                        g2d.setFont(monospaced9);
                        g2d.setFont(times11);
//                y += 11;
//                g2d.drawString(pdo.getStaticStrings().get("Vat Rule"), 45, y);
//                y += 11;
//                g2d.drawString(pdo.getStaticStrings().get("Form"), 35, y);
//                y += 11;
//                g2d.drawString(pdo.getStaticStrings().get("Cash Invoice"), 58, y);
                        y += 15;
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        y += 25;
                        g2d.drawString("Invoice No :", 20, y);
                        g2d.drawString(pdo.getBillNo(), 90, y);
                        y += 15;
                        g2d.drawString("Date & Time : ", 20, y);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                        Date input = null;
                        try {
                              input = sdf1.parse(pdo.getDate());
                        }
                        catch (ParseException ex) {
                              Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
                        g2d.drawString(sdf2.format(input), 90, y);
                        g2d.drawString(pdo.getTime(), 160, y);
                        y += 15;
                        g2d.drawString("Agent :", 20, y);
                        g2d.drawString(pdo.getUser(), 90, y);
                        y += 15;
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        y += 15;
                        g2d.drawString("Description", 20, y);
                        y += 13;
                        g2d.drawString(pdo.getItemsTableColHeads().get(0), 20, y);
                        g2d.drawString(pdo.getItemsTableColHeads().get(1), 80, y);
                        g2d.drawString(pdo.getItemsTableColHeads().get(2), 130, y);
                        g2d.drawString(pdo.getItemsTableColHeads().get(3), 165, y);
                          y += 11;
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        g2d.setFont(monospaced9);
//                        y += 12;

                        Set<String> emps = new HashSet<>();
                        for (int i = 0; i < pdo.getTableItems().size(); i++) {
                              y += 17;
                              String[] item = pdo.getTableItems().get(i);
                              g2d.drawString(item[0], 20, y);
                              y+=15;
                              g2d.drawString(item[1], 20, y);
                              g2d.drawString(item[2], 75, y);
                              g2d.drawString(item[3], 125, y);
                              g2d.drawString(item[4], 160, y);
                        }   
                        y += 20;
                        g2d.setFont(times11);
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        y += 11;
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        y += 20;
                        g2d.setFont(times13);
                        g2d.drawString("Total Qty", 20, y);
                        Double tot_qty = new Double(pdo.getAmounts().get("Qty"));
                        placeAmount(g2d, new Integer(tot_qty.intValue()).toString(), 175, y, 5);
                        
                        y += 15;
                         g2d.setFont(times13);
                        g2d.drawString("Total Items", 20, y);

                        Double items = new Double(pdo.getAmounts().get("Items"));
                        placeAmount(g2d, new Integer(items.intValue()).toString(), 175, y, 5);
                        
                        
                        y += 15;
                         g2d.setFont(times13);
                        g2d.drawString("Grand Total", 20, y);
                        Double amt_totals = new Double(pdo.getAmounts().get("Total"));
                        BigDecimal amt_totls_dbl = new BigDecimal(amt_totals).setScale(2, RoundingMode.HALF_EVEN);

                        placeAmount(g2d, amt_totls_dbl.toString(), 200, y, 5);

                        
                        y += 15;
                        g2d.drawString("Discount", 20, y);
                        Double disc = new Double(new Double(pdo.getAmounts().get("discount")));
                        BigDecimal disc_amt = new BigDecimal(disc).setScale(2, RoundingMode.HALF_EVEN);
                        placeAmount(g2d, disc_amt.toString(), 200, y, 5);
                        
                        y += 15;
                        g2d.drawString("SGST", 20, y);
                        g2d.setFont(times13);
                        Double sgst = new Double(new Double(pdo.getAmounts().get("sgst")));
                        BigDecimal sgst_amt = new BigDecimal(sgst).setScale(2, RoundingMode.HALF_EVEN);
                        placeAmount(g2d, sgst_amt.toString(), 200, y, 5);
                        y += 15;
                        g2d.drawString("CGST", 20, y);
                        g2d.setFont(times13);
                        Double cgst = new Double(new Double(pdo.getAmounts().get("cgst")));
                        BigDecimal cgst_amt = new BigDecimal(cgst).setScale(2, RoundingMode.HALF_EVEN);
                        placeAmount(g2d, cgst_amt.toString(), 200, y, 5);
                        y += 15;
                        g2d.drawString("IGST", 20, y);
                        g2d.setFont(times13);
                        Double igst = new Double(new Double(pdo.getAmounts().get("igst")));
                        BigDecimal igst_amt = new BigDecimal(igst).setScale(2, RoundingMode.HALF_EVEN);
                        placeAmount(g2d, igst_amt.toString(), 200, y, 5);
                        
                        
                        y += 15;
                        g2d.setFont(times13);
                        g2d.drawString("GST ", 20, y);
                        g2d.setFont(times13);
                        Double gst = new Double(new Double(pdo.getAmounts().get("gst")));
                        BigDecimal gst_amt = new BigDecimal(gst).setScale(2, RoundingMode.HALF_EVEN);
                        placeAmount(g2d, gst_amt.toString(), 200, y, 5);
                        
                        y += 15;
                        g2d.setFont(times13);
                        g2d.drawString("KFC 1% ", 20, y); 
                        g2d.setFont(times13);
                        Double kfc = new Double(new Double(pdo.getAmounts().get("kfc")));
                        BigDecimal kfc_amt = new BigDecimal(kfc).setScale(2, RoundingMode.HALF_EVEN);
                        placeAmount(g2d, kfc_amt.toString(), 200, y, 5);

                        y += 20;

                        g2d.setFont(times14);
                        g2d.drawString("Net Bill Amount: ", 35, y);
                        g2d.setFont(times16);
                        BigDecimal net_bill_amt = new BigDecimal(new Double(pdo.getAmounts().get("Net"))).setScale(2, RoundingMode.HALF_EVEN);
                        g2d.drawString(net_bill_amt.toString(), 145, y);
                        y += 11;
                        g2d.setFont(times11);
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        y += 10;                                        
                        g2d.setFont(monospaced9);
//                        g2d.drawString(pdo.getWords(), 20, y);
                        int length = pdo.getWords().length();
                        System.out.println("lengthhhhhh   = "+length);
                        String text = pdo.getWords();
                        String part1 = "";
                        String part2 = "";
                        String[] words = text.split(" ");
                        int k;
                        part1 = words[0];
                        for (k = 1; k < words.length; k++) {
                           if (part1.length() >= 35 - words[k].length()) {
                              break;
                           }
                           part1 += " " + words[k];
                        }
                        if (k < words.length) {
                           part2 = words[k++];
                           while (k < words.length) {
                              part2 += " " + words[k++];
                           }
                        }
                        g2d.drawString(part1, 20, y);
                        y += 10;
                        g2d.drawString(part2, 20, y);
                        System.out.println(part1);
                        System.out.println(part2);   
                         y += 10;
                         g2d.setFont(times11);
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        y += 15;
                        g2d.setFont(times13);
                        g2d.drawString(pdo.getMode(), 20, y);
                        
                      switch (pdo.getMode()) {
                          case "Cash":
                              g2d.drawString(pdo.getRcamt(), 160, y);
                              y += 15;
                              g2d.setFont(times13);
                              g2d.drawString("Change Back", 20, y);
                              g2d.drawString(pdo.getBalamt(), 160, y);
                              break;
                          case "Card":
                              g2d.drawString(pdo.getBillamt(), 160, y);
                              y += 15;
                              g2d.setFont(times13);
                              g2d.drawString("Card Type", 20, y);
                              g2d.drawString(pdo.getCard(), 150, y);
                              break;
                          case "Credit":
                              g2d.drawString(pdo.getBillamt(), 160, y);
                              y += 15;
                              g2d.setFont(times13);
                              g2d.drawString("Balance Amount", 20, y);
                              g2d.drawString(pdo.getCredit(), 160, y);
                              break;
                          default:
                              break;
                      }
                        
//                        y += 15;
//                        g2d.setFont(monospaced9);
//                        g2d.drawString(pdo.getWords(), 20, y);
                        y += 11;
                        g2d.setFont(times11);
                        g2d.drawString("-----------------------------------------------------", 20, y);
                        g2d.setFont(monospaced9);

                        y += 15;
                        g2d.drawString(pdo.getStaticStrings().get("Auth Sign"), 57, y);

                        g2d.setFont(times11);
                        y += 30;
                        g2d.drawString(pdo.getStaticStrings().get("for co"), 65, y);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");     //"dd/MM/yyyy"
                        String printDate = sdf.format(new Date());

                        g2d.setFont(monospaced9);
                        y += 13;
                        g2d.drawString("Print @ ", 37, y);
                        g2d.drawString(printDate, 77, y);
                        g2d.setFont(monospaced9B);
                        y += 13;
                        g2d.drawString(pdo.getStaticStrings().get("thanks"), 48, y);
                        
                        y += 15;
                        g2d.drawString("For Online Shopping ", 57, y);

                        y += 13;
                        g2d.drawString("Visit our website ", 60, y);
                        y += 13;
                        g2d.drawString("www.weaveskart.com ", 58, y);
                        return PAGE_EXISTS;

                  }
            };


            System.out.println("111111");
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
                                    Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
                              }
                        }
                        else {
                              try {
                                    job.setPrintService(service);
                              }
                              catch (PrinterException ex) {
                                    Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
                              }
                        }
                  }
                  try {

                        /////////////////////////////////////////

                        PageFormat pageFormat = new PageFormat();
                        pageFormat.setOrientation(PageFormat.PORTRAIT);
                        Paper pPaper = pageFormat.getPaper();



                        pPaper.setImageableArea(0, 0, pPaper.getWidth(), pPaper.getHeight() - 2);
                        pageFormat.setPaper(pPaper);

                        job.setPrintable(contentToPrint, pageFormat);


                        ////////////////////////////////////////

                        job.print();
                  }
                  catch (Exception ex) {
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
                  ResultSet rs = st.executeQuery("select defaultPrinter from " + db.schema + ".printerSettings where settingsId = 1");
                  if (rs.next()) {
                        return rs.getString("defaultPrinter");
                  }
                  st.close();
            }
            catch (SQLException ex) {
                  Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
      }

      private void placeAmount(Graphics g, String value_amount, int x_value, int y, double px) {

            int x = x_value;
            int offset = value_amount.length() - 1;
            for (int i = 0; i < value_amount.length(); i++) {

                  char[] amt_chars = value_amount.toCharArray();

                  if (true) {

                        g.drawChars(amt_chars, offset, 1, x, y);

                        try {
                              if ((amt_chars[offset - 1] == ',') || (amt_chars[offset - 1] == '.')) {
                                    x -= (px / 2);
                              }
                              else {
                                    x -= px;
                              }

                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                              return;
                        }



//                x -= px;
                        offset--;

                  }
            }
      }
}


