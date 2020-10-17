/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_StockUpdation;

/**
 *
 * @author RASHI
 */
public class objstock {
//    String master;
    String code;
    String name;
    String qty;
    String unit;
    String stock;
    String exp;
    String batch;
    String barcode;
    String whole;
    String retail;

    public objstock(String code, String name, String qty, String unit,String stock, String exp, String batch, String barcode, String whole, String retail) {
//        this.master = master;
        this.code = code;
        this.name = name;
        this.qty = qty;
        this.unit = unit;
        this.stock = stock;
        this.exp = exp;
        this.batch = batch;
        this.barcode = barcode;
        this.whole = whole;
        this.retail = retail;
    }

//    public String getMaster() {
//        return master;
//    }
//
//    public void setMaster(String master) {
//        this.master = master;
//    }

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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getWhole() {
        return whole;
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }

    public String getRetail() {
        return retail;
    }

    public void setRetail(String retail) {
        this.retail = retail;
    }
    
}
