/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Purchase;

import com.miw.control.sbox.SuggessionBox;
import database.DBMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author RASHI
 */
public class SboxCellItem extends TableCell<objitem, String> {
    
 private SuggessionBox textField;
    public static ObservableList item_suggest_data = FXCollections.observableArrayList();
    public static String name = "";
    public static DBMySQL db= new DBMySQL();
    private String item="";

    @Override
    public void startEdit() {
        super.startEdit(); //To change body of generated methods, choose Tools | Templates.

        if (textField == null) {
            createTextField();
        }
        else {
            textField.setText(getString());
            textField.selectAll();
        }
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                textField.requestFocus();
                textField.selectAll();
            }
        });
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit(); //To change body of generated methods, choose Tools | Templates.
        setText((String) getItem());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
            else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private TableColumn<objitem, ?> getNextColumn(boolean forward) {
        List<TableColumn<objitem, ?>> columns = new ArrayList<>();
        for (TableColumn<objitem, ?> column : getTableView().getColumns()) {
            columns.addAll(getLeaves(column));
        }
        //There is no other column that supports editing.
        if (columns.size() < 2) {
            return null;
        }
        int currentIndex = columns.indexOf(getTableColumn());
        int nextIndex = currentIndex;
        if (forward) {
            nextIndex++;
            if (nextIndex > columns.size() - 1) {
                nextIndex = 0;
            }
        }
        else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
            }
        }
        return columns.get(nextIndex);
    }

    private List<TableColumn<objitem, ?>> getLeaves(TableColumn<objitem, ?> root) {
        List<TableColumn<objitem, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            //We only want the leaves that are editable.
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        }
        else {
            for (TableColumn<objitem, ?> column : root.getColumns()) {
                columns.addAll(getLeaves(column));
            }
            return columns;
        }
    }

    private void createTextField() {
        textField = new SuggessionBox(item_suggest_data);
        textField.setStrict(true);
        textField.setSelect_first(true);
        textField.setText(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    if (!textField.getText().equals("")) {
                        commitEdit(textField.getText());
                        setvalue();
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                            getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                        }
                            
                    }else{
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                            getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                        }
                    }
                }
                else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
                else if (t.getCode() == KeyCode.TAB) {
                    if (!textField.getText().equals("")) {
//                        commitEdit(textField.getText());
                            setvalue();
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                            getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                        }
                        
                    }
                }
            }
        });

        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && textField != null) {
//                    if (type_exist()) {
                        
                             if (!textField.getText().equals("")) {
                              if (!name.equalsIgnoreCase(textField.getText())) {
                                 setvalue();
//                                        BigDecimal calamount = new BigDecimal(TextfieldRate.fetch_price( getTableView().getItems().get(getIndex()).getItem())).setScale(2, RoundingMode.HALF_EVEN);
//                                                getTableView().getItems().get(getIndex()).setRate(String.valueOf(calamount));
//                                   Settbl(textField.getText());
                                                 
                                          }
                                          commitEdit(textField.getText());
                        }
//                        if (!textField.getText().equals("")) {
//                            if (!name.equalsIgnoreCase(textField.getText())) {
//                                getTableView().getItems().get(getIndex()).setPrice(TextFieldCellFactoryPrice.fetch_price(getTableView().getItems().get(getIndex()).getType(), getTableView().getItems().get(getIndex()).getName()));
//                            }
                            commitEdit(textField.getText());
//                        }
//                    }
//                    else {
//                        clear_column();
//                    }
                }
                else if (newValue) {
                    if (textField.getText().length() > 0) {
                        name = textField.getText();
                        item=textField.getText();
//                        Settbl(textField.getText());
                    }
                    fetch_names();
                }
            }
        });
    }

    
    private String fetch_names() {
        item_suggest_data.removeAll(item_suggest_data);
        String names = "";
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery("select name from " + db.schema + ".tbl_item_master where status = 1");
            while (rs.next()) {
                item_suggest_data.add(rs.getString("name"));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(SboxCellItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return names;
    }

    private void clear_column() {
        getTableView().getItems().get(getIndex()).setItem("");
        textField.clear();
    }

    private void setvalue() {
    System.out.println("item name in setvalue==="+ textField.getText());
        try {
            Statement st = db.con.createStatement();
            System.out.println("select unit,sgst,cgst,igst,mrp from " + db.schema + ".tbl_item_master where name = '"+textField.getText()+"' and status = 1");
            ResultSet rs = st.executeQuery("select unit,sgst,cgst,igst,mrp from " + db.schema + ".tbl_item_master where name = '"+textField.getText()+"' and status = 1");
            while (rs.next()) {
                String unit=rs.getString("unit");
                String sgst=rs.getString("sgst");
                String cgst = rs.getString("cgst");
                String igst = rs.getString("igst");
                String rate = rs.getString("mrp");
                
                getTableView().getItems().get(getIndex()).setQty("1");
                getTableView().getItems().get(getIndex()).setUom(unit);
                getTableView().getItems().get(getIndex()).setRate(rate);
                getTableView().getItems().get(getIndex()).setSgst(sgst);
                getTableView().getItems().get(getIndex()).setCgst(cgst);
                getTableView().getItems().get(getIndex()).setIgst(igst);
                
               }
            
            
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(SboxCellItem.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    
}
