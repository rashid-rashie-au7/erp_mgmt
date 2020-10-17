/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_SalesReturn;

import javafx.scene.control.CheckBox;

/**
 *
 * @author RASHI
 */
public class objSalesRT {
    String bar;
    String code;
    String name;
    String rate;
    String qty;
    String gst;
    String gstamt;
    String tot;
    private CheckBox select;

    public objSalesRT(String bar, String code, String name, String rate, String qty, String gst, String gstamt, String tot, String value) {
        this.bar = bar;
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.qty = qty;
        this.gst = gst;
        this.gstamt = gstamt;
        this.tot = tot;
        this.select = new CheckBox(); 
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getGstamt() {
        return gstamt;
    }

    public void setGstamt(String gstamt) {
        this.gstamt = gstamt;
    }

    public String getTot() {
        return tot;
    }

    public void setTot(String tot) {
        this.tot = tot;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
}
