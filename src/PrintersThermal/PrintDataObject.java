/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrintersThermal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author miw101
 */
public class PrintDataObject {

    private Map<String, String> coDetails = new HashMap<>();
    private Map<String, String> staticStrings = new HashMap<>();     // CASH INVOICE, Form 8B, KVAT Rules,
    // Auth-signatory, for Ajantha, DElcarations etc.
    private List<String> itemsTableColHeads = new ArrayList<>();
    private List<String[]> tableItems = new ArrayList<>();
    private String date = "";
    private String user = "";
    private String time = "";
    private String words = "";
    private String mode = "";
    private String rcamt = "";
    private String balamt = "";
    private String adv = "";
    private String credit = "";
    private String billamt = "";
    private String card = "";

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBillamt() {
        return billamt;
    }

    public void setBillamt(String billamt) {
        this.billamt = billamt;
    }
    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRcamt() {
        return rcamt;
    }

    public void setRcamt(String rcamt) {
        this.rcamt = rcamt;
    }

    public String getBalamt() {
        return balamt;
    }

    public void setBalamt(String balamt) {
        this.balamt = balamt;
    }

    public String getAdv() {
        return adv;
    }

    public void setAdv(String adv) {
        this.adv = adv;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    private String billNo = "";
    private Map<String, Double> amounts = new HashMap<>();

    public Map<String, String> getCoDetails() {
        return coDetails;
    }

    public void setCoDetails(Map<String, String> coDetails) {
        this.coDetails = coDetails;
    }

    public Map<String, String> getStaticStrings() {
        return staticStrings;
    }

    public void setStaticStrings(Map<String, String> staticStrings) {
        this.staticStrings = staticStrings;
    }

    public List<String> getItemsTableColHeads() {
        return itemsTableColHeads;
    }

    public void setItemsTableColHeads(List<String> itemsTableColHeads) {
        this.itemsTableColHeads = itemsTableColHeads;
    }

    public List<String[]> getTableItems() {
        return tableItems;
    }

    public void setTableItems(List<String[]> tableItems) {
        this.tableItems = tableItems;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Map<String, Double> getAmounts() {
        return amounts;
    }

    public void setAmounts(Map<String, Double> amounts) {
        this.amounts = amounts;
    }

    public static void main(String[] args) {

        PrintDataObject dataobj = new PrintDataObject();
        dataobj.coDetails.put("co name", "MIW");



    }
}
