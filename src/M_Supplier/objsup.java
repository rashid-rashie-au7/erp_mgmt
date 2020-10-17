/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Supplier;

/**
 *
 * @author RASHI
 */
public class objsup {
    String id;
    String name;
    String place;
    String mob;
    String office;
    String email;
    String gst;

    public objsup(String id, String name, String place, String mob, String office, String email, String gst) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.mob = mob;
        this.office = office;
        this.email = email;
        this.gst = gst;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
    
    
}
