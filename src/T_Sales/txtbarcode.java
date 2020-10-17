/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;


import database.DBMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author RASHI
 */
public class txtbarcode extends TableCell<objsales, String> {
    
    private TextField textField;
//    public static String barcode = "";
    DBMySQL db = new DBMySQL();
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
      protected void updateItem(String t, boolean bln) {
            super.updateItem(t, bln); //To change body of generated methods, choose Tools | Templates.

            if (bln) {
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
            return getItem()== null ? "" : getItem().toString();
      }

      private TableColumn<objsales, ?> getNextColumn(boolean forward) {
            List<TableColumn<objsales, ?>> columns = new ArrayList<>();
            for (TableColumn<objsales, ?> column : getTableView().getColumns()) {
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

      private List<TableColumn<objsales, ?>> getLeaves(TableColumn<objsales, ?> root) {
            List<TableColumn<objsales, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                  //We only want the leaves that are editable.
                  if (root.isEditable()) {
                        columns.add(root);
                  }
                  return columns;
            }
            else {
                  for (TableColumn<objsales, ?> column : root.getColumns()) {
                        columns.addAll(getLeaves(column));
                  }
                  return columns;
            }
      }

private void createTextField() {
            textField = new TextField(getString());
            textField.selectAll();
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                  @Override
                  public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                              t.consume();
                              if (!textField.getText().equals("")) {
                                   commitEdit(textField.getText());
                                  try {
                                      setvalue();
                                  } catch (ParseException ex) {
                                      Logger.getLogger(txtbarcode.class.getName()).log(Level.SEVERE, null, ex);
                                  } 
                                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                                    if (nextColumn != null) {
                                          getTableView().edit(getTableRow().getIndex(),nextColumn);
                                          getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                                    }
                                    
                                 
                                                    }
                        }
                        else if (t.getCode() == KeyCode.ESCAPE) {
                              cancelEdit();
                        }
                        else if (t.getCode() == KeyCode.TAB) {
                              if (!textField.getText().equals("")) {
                                  try {
                                      //                                    commitEdit(textField.getText());
                                      setvalue();
                                  } catch (ParseException ex) {
                                      Logger.getLogger(txtbarcode.class.getName()).log(Level.SEVERE, null, ex);
                                  }
                                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                                    if (nextColumn != null) {
                                          getTableView().edit(getTableRow().getIndex(), nextColumn);
                                          getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                                    }
                              }else if(textField.getText().equals("")){
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
//                              if (item_name_price_exist()) {
                                    if (!textField.getText().equals("") && new Double(textField.getText()) > 0) {
                                          commitEdit(textField.getText());
                                        try {
                                            setvalue();
                                        } catch (ParseException ex) {
                                            Logger.getLogger(txtbarcode.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
//                              }
                              else {
                                    clear_column();

                              }
                        }
                        else {
                              if (textField.getText().equals("")) {
                                    textField.setText("");
                              }
                        }
                  }
            });
      }

     
      private void clear_column() {
            getTableView().getItems().get(getIndex()).setBarcode("");
            textField.clear();
      }


      private void setvalue() throws ParseException {
       System.out.println("item name in setvalue==="+ textField.getText());
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code,name,mrp,gst,expcheck,expdate from " + db.schema + ".tbl_item where barcode = '"+textField.getText()+"' and status = 1");
            ResultSet rs = st.executeQuery("select code,name,mrp,gst,expcheck,expdate from " + db.schema + ".tbl_item where barcode = '"+textField.getText()+"' and status = 1");
            while (rs.next()) {
                String code=rs.getString("code");
                String name=rs.getString("name");
                String rate = rs.getString("mrp");
                String gst = rs.getString("gst");
                String date = rs.getString("expdate");
                int chk = rs.getInt("expcheck");
              Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             Date date1 = sdf.parse(date);
//            date2 = sdf.format(now);
            System.out.println("exp date"+sdf.format(date1));
            System.out.println("today "+sdf.format(now));
            if(chk == 1 ){

            if(date1.after(now)){
                System.out.println("Date1 is after Date2");
                getTableView().getItems().get(getIndex()).setCode(code);
                getTableView().getItems().get(getIndex()).setName(name);
                getTableView().getItems().get(getIndex()).setRate(rate);
                getTableView().getItems().get(getIndex()).setGst(gst);
                getTableView().getItems().get(getIndex()).setQty("1");
                
            }
            // before() will return true if and only if date1 is before date2
            if(date1.before(now)){
                System.out.println("Date1 is before Date2");
                 Alert alert = new Alert(Alert.AlertType.WARNING,"This Product is Expired!",ButtonType.OK);
                alert.showAndWait();
                clear_column();
                textField.requestFocus();
            }

            //equals() returns true if both the dates are equal
            if(date1.equals(now)){
                System.out.println("Date1 is equal Date2");
                Alert alert = new Alert(Alert.AlertType.WARNING,"This Product will Expire today!",ButtonType.OK);
                alert.showAndWait();
                clear_column();
            }
            }else if (chk == 0){
                getTableView().getItems().get(getIndex()).setCode(code);
                getTableView().getItems().get(getIndex()).setName(name);
                getTableView().getItems().get(getIndex()).setRate(rate);
                getTableView().getItems().get(getIndex()).setGst(gst);
                getTableView().getItems().get(getIndex()).setQty("1");
            }
               
//                final int indexez = getIndex() + 3;
//                    getTableView().getSelectionModel().select(indexez, getTableView().getColumns().get(4));
//                        getTableView().getFocusModel().focus(indexez, getTableView().getColumns().get(4));

               }
            
            
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(txtbarcode.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      
    
}
