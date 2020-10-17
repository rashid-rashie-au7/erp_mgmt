/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_Creditbill;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String bill;
    String client;
    String date;
    String biilamt;
    String paid;
    String amt;
    String bal;

    public objtbl(String bill, String client, String date, String biilamt, String paid, String amt, String bal) {
        this.bill = bill;
        this.client = client;
        this.date = date;
        this.biilamt = biilamt;
        this.paid = paid;
        this.amt = amt;
        this.bal = bal;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBiilamt() {
        return biilamt;
    }

    public void setBiilamt(String biilamt) {
        this.biilamt = biilamt;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }
    
}
