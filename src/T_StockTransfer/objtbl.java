/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_StockTransfer;

/**
 *
 * @author RASHI
 */
public class objtbl {
    String id;
    String date;
    String time;
    String wh;
   

    public objtbl(String id, String date, String time, String wh) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.wh = wh;
       
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }


    
}
