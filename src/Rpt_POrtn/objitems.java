/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_POrtn;


/**
 *
 * @author RASHI
 */
public class objitems {
    String item;
    String qty;
    String uom;
    String rate;
    String sgst;
    String cgst;
    String igst;
    String tot;
    String gst;
    String net;

    public objitems(String item, String qty, String uom, String rate, String sgst, String cgst, String igst, String tot, String gst, String net) {
        this.item = item;
        this.qty = qty;
        this.uom = uom;
        this.rate = rate;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.tot = tot;
        this.gst = gst;
        this.net = net;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
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
    

    
}
