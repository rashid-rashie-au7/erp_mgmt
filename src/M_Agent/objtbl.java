/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Agent;

/**
 *
 * @author RASHI
 */
public class objtbl {
    
    String code;
    String name;
    String remarks;

    public objtbl(String code, String name, String remarks) {
        this.code = code;
        this.name = name;
        this.remarks = remarks;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
}
