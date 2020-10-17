/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Unit;

/**
 *
 * @author RASHI
 */
public class obj_unit {
    String code;
    String name;
    String shortcode;

    public obj_unit(String code, String name, String shortcode) {
        this.code = code;
        this.name = name;
        this.shortcode = shortcode;
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

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }
    
    
    
}
