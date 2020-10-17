/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Tax_Master;

/**
 *
 * @author RASHI
 */
public class objTax {
    String code;
    String name;
    String sgst;
    String cgst;
    String igst;
    String total;

    public objTax(String code, String name, String sgst, String cgst, String igst, String total) {
        this.code = code;
        this.name = name;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.total = total;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
}
