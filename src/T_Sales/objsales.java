/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author RASHI
 */
public class objsales {

      private final StringProperty barcode = new SimpleStringProperty();
      private final StringProperty code = new SimpleStringProperty();
      private final StringProperty name = new SimpleStringProperty();
      private final StringProperty rate = new SimpleStringProperty();
      private final StringProperty qty = new SimpleStringProperty();
      private final StringProperty gst = new SimpleStringProperty();
      private final StringProperty gstamt = new SimpleStringProperty();
      private final StringProperty tot = new SimpleStringProperty();
      
    public objsales(String barcode, String code, String name, String rate, String qty, String gst,String gstamt, String tot) {

        this.barcode.setValue(barcode);
        this.code.setValue(code);
        this.name.setValue(name);
        this.rate.setValue(rate);
        this.qty.setValue(qty);
        this.gst.setValue(gst);
        this.gstamt.setValue(gstamt);
        this.tot.setValue(tot);
    }

    

    public String getBarcode() {
        return barcode.get();
    }

    public void setBarcode(String value) {
       barcode.set(value);
    }

     public StringProperty barcodeProperty() {
            return barcode;
      }

    
    public String getCode() {
        return code.get();
    }

    public void setCode(String value) {
        code.set(value);
    }

    public StringProperty codeProperty() {
            return code;
      }

    
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
       name.set(value);
    }
    
     public StringProperty nameProperty() {
            return name;
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

    public String getQty() {
        return qty.get();
    }

    public void setQty(String value) {
        qty.set(value);
    }
    
     public StringProperty qtyProperty() {
            return qty;
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

    public String getGstamt() {
        return gstamt.get();
    }

    public void setGstamt(String value) {
        gstamt.set(value);
    }

     public StringProperty gstamtProperty() {
            return gstamt;
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

}
