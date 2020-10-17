/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrintersThermal;

import database.DBMySQL;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miw101
 */
public class ThermalPrinterPageSetup {
  

      DBMySQL db = new DBMySQL();
      private PrintDataObject pdo = new PrintDataObject();

      public ThermalPrinterPageSetup(String bill_no) {


            Map<String, String> mapCoDetails = new HashMap<>();
            Map<String, String> staticStrings = new HashMap<>();
            List<String> itemsTableColHeads = new ArrayList<>();
            List<String[]> tableItems = new ArrayList<>();
            Map<String, Double> amounts = new HashMap<>();
            String billNo = bill_no + "";
             mapCoDetails.put("Co Name", "looms & weaves");
            mapCoDetails.put("Address", "PB No:6550, Parutthipara");
            mapCoDetails.put("Address2", "Thiruvanathapuram, Kerala");
            mapCoDetails.put("Phone", "Phone: 0471-2543222");
            mapCoDetails.put("Tin", "GSTIN:32BLZPG0278F1ZZ");
            pdo.setCoDetails(mapCoDetails);


            pdo.setBillNo(billNo);


            
            itemsTableColHeads.add("HSN");
            itemsTableColHeads.add("Rate");
            itemsTableColHeads.add("Qty");
            itemsTableColHeads.add("Total");

            pdo.setItemsTableColHeads(itemsTableColHeads);
            tableItems = getItems(billNo);
            pdo.setTableItems(tableItems);
            amounts = getTotals(bill_no);
            pdo.setAmounts(amounts);

//        staticStrings.put("Form", "Form 8B See Rule 58(10)");
            staticStrings.put("Cash Invoice", "CASH INVOICE");
//        staticStrings.put("Vat Rule", "The KVAT Rule 2005");
//        staticStrings.put("E & OE", "E & OE");
            staticStrings.put("Auth Sign", "Authorized Signatory");
            staticStrings.put("for co", "For looms & weaves ");
//        staticStrings.put("check", "Please check bill before leaving counter");
            staticStrings.put("thanks", "Thank you  Visit again");


            pdo.setStaticStrings(staticStrings);




      }

      public boolean print() {

            ThermalPrint tp = new ThermalPrint();
            try {
                  tp.printCard(pdo);
            }
            catch (Exception ex) {
                  Logger.getLogger(ThermalPrinterPageSetup.class.getName()).log(Level.SEVERE, null, ex);
                  return false;
            }

            return true;

      }

      private ArrayList<String[]> getItems(String billno) {


            ArrayList<String[]> tableItems = new ArrayList<>();
            Statement st_db = null;
            try {
                  st_db = db.con.createStatement();
                  System.out.println("select b.name, b.rate,b.qty,b.netamt,i.hsn FROM " + db.schema + ".tbl_bill_items b ," + db.schema + ".tbl_item i where billno ='" + billno + "' and b.name = i.name ");
                  String str_sql = "select b.name, b.rate,b.qty,b.netamt,i.hsn FROM " + db.schema + ".tbl_bill_items b ," + db.schema + ".tbl_item i where billno ='" + billno + "' and b.name = i.name ";
                  ResultSet rs_db = st_db.executeQuery(str_sql);
                  while (rs_db.next()) {
                        String item1[] = {rs_db.getString("name"),rs_db.getString("hsn"),
                                          rs_db.getString("rate"),rs_db.getString("qty"), 
                                          rs_db.getString("netamt")};
                        tableItems.add(item1);
                  }
                  st_db.close();
            }
            catch (SQLException ex) {
                  try {
                        st_db.close();
                  }
                  catch (SQLException ex1) {
//                        System.out.println("Inside DBTools class!!");
                        Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex1);
                  }
                  Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
            }


            return tableItems;
      }

      private Map<String, Double> getTotals(String billno) {


            Map<String, Double> amnts_fet = new HashMap<>();

            Statement st_db = null;
            try {
                  st_db = db.con.createStatement();
                  System.out.println("SELECT DATE_FORMAT(CAST(date AS DATE),'%d/%m/%Y')  AS bill_date, items, qty, grand, discount, netamt, gst,sgst,cgst,igst,agent,time,kfc FROM " + db.schema + ".tbl_bill where billno = '" + billno+"' ");
                  String str_sql = "SELECT DATE_FORMAT(CAST(date AS DATE),'%d/%m/%Y')  AS bill_date, items, qty, grand, discount, netamt, gst,sgst,cgst,igst,agent,time,kfc FROM " + db.schema + ".tbl_bill where billno = '" + billno+"' ";
                  ResultSet rs_db = st_db.executeQuery(str_sql);
                  while (rs_db.next()) {
                        amnts_fet.put("Qty", Double.parseDouble(rs_db.getString("qty")));
                        amnts_fet.put("Items", Double.parseDouble(rs_db.getString("items")));
                        amnts_fet.put("Total", Double.parseDouble(rs_db.getString("grand")));
                        amnts_fet.put("gst", Double.parseDouble(rs_db.getString("gst")));
                        amnts_fet.put("discount", Double.parseDouble(rs_db.getString("discount")));
                        amnts_fet.put("Net", Double.parseDouble(rs_db.getString("netamt")));         
                        amnts_fet.put("sgst", Double.parseDouble(rs_db.getString("sgst")));
                        amnts_fet.put("cgst", Double.parseDouble(rs_db.getString("cgst")));
                        amnts_fet.put("igst", Double.parseDouble(rs_db.getString("igst")));
                        amnts_fet.put("kfc", Double.parseDouble(rs_db.getString("kfc")));
                        pdo.setDate(rs_db.getString("bill_date"));
                        pdo.setTime(rs_db.getString("time"));
                        pdo.setUser(rs_db.getString("agent"));
                        pdo.setWords(convertToIndianCurrency(rs_db.getString("netamt")));
                        payment(billno);
                  }
                  st_db.close();
            }
            catch (SQLException ex) {
                  try {
                        st_db.close();
                  }
                  catch (SQLException ex1) {
                        Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex1);
                  }
                  Logger.getLogger(ThermalPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
            return amnts_fet;
      }
      
      private void payment(String billno){
          try {
            Statement st = db.con.createStatement();
            System.out.println("select type, rcamt,balamt , adv, creditbal,billamt,card from " + db.schema + ".tbl_payment where billno ='"+billno+"'");
            ResultSet rs = st.executeQuery("select type, rcamt,balamt,adv,creditbal,billamt,card from " + db.schema + ".tbl_payment where billno ='"+billno+"'");
            while (rs.next()) {
                        pdo.setMode(rs.getString("type"));
                        pdo.setRcamt(rs.getString("rcamt"));
                        pdo.setBalamt(rs.getString("balamt"));
                        pdo.setAdv(rs.getString("adv"));
                        pdo.setCredit(rs.getString("creditbal"));
                        pdo.setBillamt(rs.getString("billamt"));
                        pdo.setCard(rs.getString("card"));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(ThermalPrinterPageSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
      }

    public  String convertToIndianCurrency(String num) {
        BigDecimal bd = new BigDecimal(num);
        long number = bd.longValue();
        long no = bd.longValue();
        int decimal = (int) (bd.remainder(BigDecimal.ONE).doubleValue() * 100);
        int digits_length = String.valueOf(no).length();
        int i = 0;
        ArrayList<String> str = new ArrayList<>();
        HashMap<Integer, String> words = new HashMap<>();
        words.put(0, "");
        words.put(1, "One");
        words.put(2, "Two");
        words.put(3, "Three");
        words.put(4, "Four");
        words.put(5, "Five");
        words.put(6, "Six");
        words.put(7, "Seven");
        words.put(8, "Eight");
        words.put(9, "Nine");
        words.put(10, "Ten");
        words.put(11, "Eleven");
        words.put(12, "Twelve");
        words.put(13, "Thirteen");
        words.put(14, "Fourteen");
        words.put(15, "Fifteen");
        words.put(16, "Sixteen");
        words.put(17, "Seventeen");
        words.put(18, "Eighteen");
        words.put(19, "Nineteen");
        words.put(20, "Twenty");
        words.put(30, "Thirty");
        words.put(40, "Forty");
        words.put(50, "Fifty");
        words.put(60, "Sixty");
        words.put(70, "Seventy");
        words.put(80, "Eighty");
        words.put(90, "Ninety");
        String digits[] = {"", "Hundred", "Thousand", "Lakh", "Crore"};
        while (i < digits_length) {
            int divider = (i == 2) ? 10 : 100;
            number = no % divider;
            no = no / divider;
            i += divider == 10 ? 1 : 2;
            if (number > 0) {
                int counter = str.size();
                String plural = (counter > 0 && number > 9) ? "s" : "";
                String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural : words.get(Integer.valueOf((int) Math.floor(number / 10) * 10)) + " " + words.get(Integer.valueOf((int) (number % 10))) + " " + digits[counter] + plural;                
                str.add(tmp);
            } else {
                str.add("");
            }
        }
 
        Collections.reverse(str);
        String Rupees = String.join(" ", str).trim();
 
        String paise = (decimal) > 0 ? " And " + words.get(Integer.valueOf((int) (decimal - decimal % 10))) + " " + words.get(Integer.valueOf((int) (decimal % 10))) : "";
        return "Rupees " + Rupees + paise + " Only";
    }
     
}


