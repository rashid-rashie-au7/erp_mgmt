/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T_Purchase;

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
public class TextfieldUom extends TableCell<objitem, String>{
   
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
            textField = new TextField(getString());
//            numbers_only();
            textField.setEditable(false);
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                  @Override
                  public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                              t.consume();
                              if (!textField.getText().equals("")) {
//                                    commitEdit(textField.getText());
                                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                                    if (nextColumn != null) {
                                          getTableView().edit(getTableRow().getIndex(), nextColumn);
                                          getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                                    }
//                                    calcTotal();
                              }
                        }
                        else if (t.getCode() == KeyCode.ESCAPE) {
                              cancelEdit();
                        }
                        else if (t.getCode() == KeyCode.TAB) {
                              if (!textField.getText().equals("")) {
//                                    commitEdit(textField.getText());
//                                    calcTotal();
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
//                                          commitEdit(textField.getText());
//                                          calcTotal();
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
}
