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
public class objtbl {
    String id;
    String name;
    String cat;
    String brand;
    String qty;
    String uom;
    String stock;
    String gst;
    String retail;
    String whole;

    public objtbl(String id, String name, String cat, String brand, String qty, String uom, String stock, String gst, String retail, String whole) {
        this.id = id;
        this.name = name;
        this.cat = cat;
        this.brand = brand;
        this.qty = qty;
        this.uom = uom;
        this.stock = stock;
        this.gst = gst;
        this.retail = retail;
        this.whole = whole;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getRetail() {
        return retail;
    }

    public void setRetail(String retail) {
        this.retail = retail;
    }

    public String getWhole() {
        return whole;
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }
    
    
}
