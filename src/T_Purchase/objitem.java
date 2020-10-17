/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Purchase;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author RASHI
 */
public class objitem {
      
      private final StringProperty item = new SimpleStringProperty();
      private final StringProperty qty = new SimpleStringProperty();
      private final StringProperty uom = new SimpleStringProperty();
      private final StringProperty rate = new SimpleStringProperty();
      private final StringProperty sgst = new SimpleStringProperty();
      private final StringProperty cgst = new SimpleStringProperty();
      private final StringProperty igst = new SimpleStringProperty();
      private final StringProperty tot = new SimpleStringProperty();
      private final StringProperty gst = new SimpleStringProperty();
      private final StringProperty total = new SimpleStringProperty();
      
    public objitem( String item, String qty, String uom, String rate, String sgst, String cgst, String igst,String tot, String gst, String total) {
       
        this.item.setValue(item);
        this.qty.setValue(qty);
        this.uom.setValue(uom);
        this.rate.setValue(rate);
        this.sgst.setValue(sgst);
        this.cgst.setValue(cgst);
        this.igst.setValue(igst);
        this.tot.setValue(tot);
        this.gst.setValue(gst);
        this.total.setValue(total);
    }


    public String getItem() {
        return item.get();
    }
    public void setItem(String value) {
      item.set(value);
    }

     public StringProperty itemProperty() {
            return item;
      }

    

    public String getQty() {
        return qty.get();
    }
    public void setQty(String value) {
       qty.set(value);
    }

     public StringProperty qtyProperty() {
            return qty;
      }

    public String getUom() {
        return uom.get();
    }
    public void setUom(String value) {
       uom.set(value);
    }

     public StringProperty uomProperty() {
            return uom;
      }

    public String getRate() {
        return rate.get();
    }
    public void setRate(String value) {
       rate.set(value);
    }

     public StringProperty rateProperty() {
            return rate;
      }

    public String getSgst() {
        return sgst.get();
    }

    public void setSgst(String value) {
       sgst.set(value);
    }

     public StringProperty sgstProperty() {
            return sgst;
      }
    public String getCgst() {
        return cgst.get();
    }
    public void setCgst(String value) {
       cgst.set(value);
    }

     public StringProperty cgstProperty() {
            return cgst;
      }

    public String getIgst() {
        return igst.get();
    }
    public void setIgst(String value) {
       igst.set(value);
    }

     public StringProperty igstProperty() {
            return igst;
      }
     
     public String getTot() {
        return tot.get();
    }
    public void setTot(String value) {
       tot.set(value);
    }

     public StringProperty totProperty() {
            return tot;
      }

    public String getGst() {
        return gst.get();
    }
    public void setGst(String value) {
       gst.set(value);
    }

     public StringProperty gstProperty() {
            return gst;
      }

    public String getTotal() {
        return total.get();
    }
    public void setTotal(String value) {
       total.set(value);
    }

     public StringProperty totalProperty() {
            return total;
      }

    
    
}
