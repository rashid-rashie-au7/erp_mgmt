/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_SalesReturn;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String bill;
    String date;
    String time;
    String name; 
    String items;
    String qty;
    String tot;
    String gst;
    String net;
    String type;

    public objtbl(String bill, String date, String time, String name, String items, String qty, String tot, String gst, String net, String type) {
        this.bill = bill;
        this.date = date;
        this.time = time;
        this.name = name;
        this.items = items;
        this.qty = qty;
        this.tot = tot;
        this.gst = gst;
        this.net = net;
        this.type = type;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTot() {
        return tot;
    }

    public void setTot(String tot) {
        this.tot = tot;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
