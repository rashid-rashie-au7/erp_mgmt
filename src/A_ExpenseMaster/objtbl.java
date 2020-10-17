/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A_ExpenseMaster;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String code;
    String name;
    String remark;

    public objtbl(String code, String name, String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
