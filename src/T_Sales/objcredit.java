/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;

/**
 *
 * @author RASHI
 */
public class objcredit {
    String no;
    String name;
    String billamt;
    String paid;
    String bal;

    public objcredit(String no, String name, String billamt, String paid, String bal) {
        this.no = no;
        this.name = name;
        this.billamt = billamt;
        this.paid = paid;
        this.bal = bal;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillamt() {
        return billamt;
    }

    public void setBillamt(String billamt) {
        this.billamt = billamt;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }
    
    
}
