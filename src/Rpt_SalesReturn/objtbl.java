/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_SalesReturn;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String code; 
    String date;
    String time;
    String billno;  
    String client;
    String mob;
    String grand;
    String gst;
    String net;

    public objtbl(String code, String date, String time, String billno, String client, String mob, String grand, String gst, String net) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.billno = billno;
        this.client = client;
        this.mob = mob;
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

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
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
