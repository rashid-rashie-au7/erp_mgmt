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
public class objtra {
    String id;
    String name ;
    String qty;

    public objtra(String id, String name, String qty) {
        this.id = id;
        this.name = name;
        this.qty = qty;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
    
}
