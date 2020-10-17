/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Sales;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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
public class txtrate extends TableCell<objsales, String>{
 private TextField textField;
      private String checker = null;

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
            return getItem() == null ? "" : getItem().toString();
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
            numbers_only();
            textField.selectAll();
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                  @Override
                  public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                              t.consume();
                              if (!textField.getText().equals("") && new Double(textField.getText()) > 0) {
                                    commitEdit(textField.getText());
                                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                                    if (nextColumn != null) {
                                          getTableView().edit(getTableRow().getIndex(), nextColumn);
                                          getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                                    }
                                    calcTotal();
                              }
                        }
                        else if (t.getCode() == KeyCode.ESCAPE) {
                              cancelEdit();
                        }
                        else if (t.getCode() == KeyCode.TAB) {
                              if (!textField.getText().equals("") && new Double(textField.getText()) > 0) {
//                                    commitEdit(textField.getText());
                                    calcTotal();
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
                                    if (!textField.getText().equals("") && new Double(textField.getText()) > 0) {
                                          commitEdit(textField.getText());
                                          calcTotal();
                                    }
                        }
                        else {
                              if (textField.getText().equals("")) {
//                                    set_price();
                              }
                        }
                  }
            });
      }

      private void numbers_only() {
            textField.textProperty().addListener(new ChangeListener<String>() {
                  @Override
                  public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                        try {
                              if (t1.endsWith("f") || t1.endsWith("F") || t1.endsWith("d") || t1.endsWith("D")) {
                                    textField.setText(t);
                              }
                              else if (t1.equals("")) {
                                    textField.setText("");
                                    t1 = "0.0";
                              }
                              Double.parseDouble(t1);
                        }
                        catch (NumberFormatException e) {
                              textField.setText(t);
                        }
                  }
            });
      }

      private void clear_column() {
            getTableView().getItems().get(getIndex()).setRate("");
            textField.clear();
      }

      private void calcTotal() {
           Double total=0.0;
           Double gstamt=0.0;
           Double gst= Double.parseDouble(getTableView().getItems().get(getIndex()).getGst());
           System.out.println("gst per===="+gst);
           Double qty= Double.parseDouble(getTableView().getItems().get(getIndex()).getQty());
           System.out.println("qty======="+qty);
           Double rate =Double.parseDouble(getTableView().getItems().get(getIndex()).getRate());
           System.out.println("rate===="+rate);
            Double tot = qty*rate;
            System.out.println("totlllllllllllllll"  +tot);
            gstamt=tot*gst/100;
            System.out.println("gst amt==== "+gstamt);
           total=tot+gstamt;
           System.out.println("grand total=== "+total);
           getTableView().getItems().get(getIndex()).setGstamt(gstamt.toString());
            getTableView().getItems().get(getIndex()).setTot(total.toString());
          
      }  
}
