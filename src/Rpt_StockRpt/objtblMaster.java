/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rpt_StockRpt;

/**
 *
 * @author RASHI
 */
public class objtblMaster {
    String id;
    String name;
    String hsn;
    String cat;
    String brand;
    String qty;
    String uom;

    public objtblMaster(String id, String name, String hsn, String cat, String brand, String qty, String uom) {
        this.id = id;
        this.name = name;
        this.hsn = hsn;
        this.cat = cat;
        this.brand = brand;
        this.qty = qty;
        this.uom = uom;
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

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
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
    
}
