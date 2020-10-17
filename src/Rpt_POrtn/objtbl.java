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
public class objtbl {
     String code; 
    String date;
    String time;
    String poid;  
    String sup;
    String invoice;
    String grand;
    String gst;
    String net;

    public objtbl(String code, String date, String time, String poid, String sup, String invoice, String grand, String gst, String net) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.poid = poid;
        this.sup = sup;
        this.invoice = invoice;
        this.grand = grand;
        this.gst = gst;
        this.net = net;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
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
    
}
