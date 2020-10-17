/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_SalesReport;

/**
 *
 * @author RASHI
 */
public class objsale {
    String billno;
    String date;
    String client;
    String item;
    String grand;
    String dis;
    String sgst;
    String cgst;
    String igst;
    String kfc;
    String gst;
    String bal;
    String net;
    String pay;

    public objsale(String billno, String date, String client, String item, String grand, String dis, String sgst, String cgst, String igst, String kfc, String gst, String bal, String net, String pay) {
        this.billno = billno;
        this.date = date;
        this.client = client;
        this.item = item;
        this.grand = grand;
        this.dis = dis;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.kfc = kfc;
        this.gst = gst;
        this.bal = bal;
        this.net = net;
        this.pay = pay;
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

    public String getKfc() {
        return kfc;
    }

    public void setKfc(String kfc) {
        this.kfc = kfc;
    }

    

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getGrand() {
        return grand;
    }

    public void setGrand(String grand) {
        this.grand = grand;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
    
}
