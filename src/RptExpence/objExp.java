/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RptExpence;

/**
 *
 * @author rashi
 */
public class objExp {
     String id;
    String date;
    String name;
    String amt;
    String mode;
    String cmnt;

    public objExp(String id, String date, String name, String amt, String mode, String cmnt) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.amt = amt;
        this.mode = mode;
        this.cmnt = cmnt;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }
    
}
