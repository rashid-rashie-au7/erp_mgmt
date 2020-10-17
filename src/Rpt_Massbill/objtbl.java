/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Massbill;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String date;
    String time;
    String bill;
    String sgst;
    String cgst;
    String igst;
    String tot;
    String gst;
    String net;
    String wh;
    String agent;

    public objtbl(String date, String time, String bill, String sgst, String cgst, String igst, String tot, String gst, String net, String wh, String agent) {
        this.date = date;
        this.time = time;
        this.bill = bill;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.tot = tot;
        this.gst = gst;
        this.net = net;
        this.wh = wh;
        this.agent = agent;
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

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
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

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    
}
