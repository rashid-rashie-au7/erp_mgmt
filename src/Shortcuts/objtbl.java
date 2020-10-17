/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shortcuts;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String key;
    String act;

    public objtbl(String key, String act) {
        this.key = key;
        this.act = act;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
    
}
