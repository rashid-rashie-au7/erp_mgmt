/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Purchase;

/**
 *
 * @author RASHI
 */
public class objpo {
    String id;
    String date;
    String sup;
    String invoice;
    String items;
    String grand;
    String gst;
    String net;
    String status;

    public objpo(String id, String date, String sup, String invoice, String items, String grand, String gst, String net, String status) {
        this.id = id;
        this.date = date;
        this.sup = sup;
        this.invoice = invoice;
        this.items = items;
        this.grand = grand;
        this.gst = gst;
        this.net = net;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getGrand() {
        return grand;
    }

    public void setGrand(String grand) {
        this.grand = grand;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
