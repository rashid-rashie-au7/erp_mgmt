/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Purchase;

/**
 *
 * @author RASHI
 */
public class objPO {
    String id;
    String date;
    String invoice;
    String sup;
    String tot;
    String gst;
    String net;
    String status;

    public objPO(String id, String date, String invoice, String sup, String tot, String gst, String net, String status) {
        this.id = id;
        this.date = date;
        this.invoice = invoice;
        this.sup = sup;
        this.tot = tot;
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

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
