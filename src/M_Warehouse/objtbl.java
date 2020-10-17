/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Warehouse;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String code;
    String name;
    String gst;

    public objtbl(String code, String name, String gst) {
        this.code = code;
        this.name = name;
        this.gst = gst;
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

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
    
    
}
