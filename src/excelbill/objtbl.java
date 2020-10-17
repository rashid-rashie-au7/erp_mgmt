/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelbill;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String odid;
    String date;
    String time;
    String code;
    String desc;
    String rate; 
    String qty;
    String tot;
    String sgst;
    String cgst;
    String igst;
    String gst;
    String net;
    String client; 
    String mob;
    String add;
    String gstin;
    String zip;
    String st;
    String pay;

    public objtbl(String odid, String date,String time, String code, String desc, String rate, String qty, String tot, String sgst, String cgst, String igst, String gst, String net, String client, String mob, String add, String gstin, String zip, String st, String pay) {
        this.odid = odid;
        this.date = date;
        this.time= time;
        this.code = code;
        this.desc = desc;
        this.rate = rate;
        this.qty = qty;
        this.tot = tot;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.gst = gst;
        this.net = net;
        this.client = client;
        this.mob = mob;
        this.add = add;
        this.gstin = gstin;
        this.zip = zip;
        this.st = st;
        this.pay = pay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOdid() {
        return odid;
    }

    public void setOdid(String odid) {
        this.odid = odid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
    
}
