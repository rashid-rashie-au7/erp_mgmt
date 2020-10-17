/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package M_Employ;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DBMySQL;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class EmployMasterController implements Initializable {

    @FXML
    private HBox hbx_main;
    @FXML
    private SplitPane sp_main;
    @FXML
    private AnchorPane anchr_sidebar;
    @FXML
    private Accordion accr_option;
    @FXML
    private TitledPane tp_options;
    @FXML
    private AnchorPane anchr_options;
    @FXML
    private VBox vbx_btn;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrint;
    @FXML
    private AnchorPane anchr_main;
    @FXML
    private VBox vbxMain;
    @FXML
    private Accordion accrSearch;
    @FXML
    private TitledPane tpSearch;
    @FXML
    private AnchorPane anchr_search;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnShow;
    @FXML
    private Button btnHide;
    @FXML
    private Button btnClear;
    @FXML
    private AnchorPane anchrAdvSearch;
    @FXML
    private Accordion accrtable;
    @FXML
    private TitledPane tpTable;
    @FXML
    private AnchorPane anchrTable;
    @FXML
    private TableView<objemp> tblView;
    @FXML
    private TableColumn colid;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtDept;
    @FXML
    private RadioButton rbtfemale;
    @FXML
    private RadioButton rbtMale;
    private Stage stage_add_emp = new Stage(StageStyle.UTILITY);
    private Stage stageEditemp = new Stage(StageStyle.UTILITY);
    private Stage stage = new Stage();
    public static BooleanProperty boolean_status = new SimpleBooleanProperty();
    public static ObservableList<objemp> table_data = FXCollections.observableArrayList();
    public static ObservableList<objemp> table_data_search = FXCollections.observableArrayList();
    private DBMySQL db = new DBMySQL();
    Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
    
    @FXML
    private TextField txtid;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colsex;
    @FXML
    private TableColumn colmob;
    @FXML
    private TableColumn colemail;
    @FXML
    private TableColumn coldept;
    @FXML
    private TableColumn colplace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        set_accordian(rectangle2D);
        load_titled_pane_icon();
        set_buttons();
        set_table();
        populate_table();
        listner_boolean();
        listner_radio_selection();

        listner_search();
        listner_adv_search_po_id();
        listner_adv_search_name();
        listner_adv_search_dept();
        setLayout();
        tpTable.setText(tblView.getItems().size() + " Employ Found");
        tblView.itemsProperty().addListener(new ChangeListener<ObservableList<objemp>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<objemp>> ov, ObservableList<objemp> t, ObservableList<objemp> t1) {
                tpTable.setText(tblView.getItems().size() + " Employ Found");
            }
        });
        doubleClickTableEdit();
        enterKeyEventOnTableView();
    }    
    
    private void set_accordian(Rectangle2D rectangle2D1) {
        accrSearch.setPrefWidth(rectangle2D1.getWidth() - anchr_options.getPrefWidth());
        accrtable.setPrefWidth(rectangle2D1.getWidth() - anchr_options.getPrefWidth());
        tpSearch.setGraphic(new ImageView(new Image("/res/1384536324_Search.png", 24, 24, true, true)));
        tp_options.setGraphic(new ImageView(new Image("/res/1385471310_order-1.png", 24, 24, true, true)));
    }

    private void set_buttons() {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
    }

    
    private void load_titled_pane_icon() {
//        tpSearch.setExpanded(true);
        accrSearch.setExpandedPane(tpSearch);
        tpSearch.setAnimated(true);
        tpTable.setAnimated(true);
        accr_option.setExpandedPane(tp_options);
        accrtable.setExpandedPane(tpTable);
        tpSearch.setAnimated(true);
        
    }

    private void set_table() {
        colid.setCellValueFactory(new PropertyValueFactory<objemp, String>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<objemp, String>("name"));
        colsex.setCellValueFactory(new PropertyValueFactory<objemp, String>("sex"));
        colmob.setCellValueFactory(new PropertyValueFactory<objemp, String>("mob"));
        colemail.setCellValueFactory(new PropertyValueFactory<objemp, String>("email"));
        coldept.setCellValueFactory(new PropertyValueFactory<objemp, String>("dept"));
        colplace.setCellValueFactory(new PropertyValueFactory<objemp, String>("place"));
        table_data.add(new objemp("", "", "", "", "", "", ""));
        tblView.setItems(table_data);
    }

    public void populate_table() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,gender ,mob, email, dept,city from " + db.schema + ".tbl_employ where status  = 1");
            ResultSet rs = st.executeQuery("select code, name,gender ,mob, email, dept,city from " + db.schema + ".tbl_employ where status  = 1");
            while (rs.next()) {
                
                table_data.add(new objemp(rs.getString("code"), rs.getString("name"), rs.getString("gender"), rs.getString("mob"), rs.getString("email"), rs.getString("dept"), rs.getString("city")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listner_boolean() {
        boolean_status.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                populate_table();
            }
        });
    }

    public void clear() {
        txtname.clear();
        txtid.clear();
        txtSearch.clear();
        txtDept.clear();
        rbtMale.setSelected(false);
        rbtfemale.setSelected(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtSearch.requestFocus();
            }
        });
        populate_table();
    }

    private void listner_radio_selection() {
        ToggleGroup grp =new ToggleGroup();
        rbtMale.setToggleGroup(grp);
        rbtfemale.setToggleGroup(grp);
        rbtMale.setUserData("Male");
        rbtfemale.setUserData("Female");

        grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                if (t1.getUserData().toString().equalsIgnoreCase(rbtMale.getUserData().toString())) {
                    populate_tbl_male ();
                    tpTable.setText(table_data.size() + " Employ Found");
                }
                else if (t1.getUserData().toString().equalsIgnoreCase(rbtfemale.getUserData().toString())) {
                    populate_tbl_female();
                 tpTable.setText(table_data.size() + " Employ Found");
                }
                
            }
        });
    }

    private void populate_tbl_male() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,gender ,mob, email, dept,city from " + db.schema + ".tbl_employ where gender = 'Male' and status  = 1");
            ResultSet rs = st.executeQuery("select code, name,gender ,mob, email, dept,city from " + db.schema + ".tbl_employ where gender = 'Male' and status  = 1");
            while (rs.next()) {
                
                table_data.add(new objemp(rs.getString("code"), rs.getString("name"), rs.getString("gender"), rs.getString("mob"), rs.getString("email"), rs.getString("dept"), rs.getString("city")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void EmployControllerActionIdentified(String type, String id) {
        if (!stage_add_emp.isShowing()) {
            stage_add_emp = new Stage(StageStyle.UTILITY);
            stage_add_emp.initOwner(stage);
            stage_add_emp.initModality(Modality.WINDOW_MODAL);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Employ.fxml"));
                Parent root = (Parent) loader.load();
                EmployController sc = loader.getController();
                Scene scene = new Scene(root);
                stage_add_emp.setScene(scene);
                stage_add_emp.setResizable(false);
                stage_add_emp.setTitle("Add Employ");
                sc.setStage(stage_add_emp);
                stage_add_emp.show();
                if (type.equalsIgnoreCase("Edit")) {
                    stage_add_emp.setTitle("Edit Employ");
                    sc.fetch_for_update( id);
                    } else if (type.equalsIgnoreCase("Add")) {
                  }

            } catch (IOException ex) {
                Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

    }
    
    private void populate_tbl_female() {
        table_data.removeAll(table_data);
        String status = "";
        try {
            Statement st = db.con.createStatement();
            System.out.println("select code, name,gender ,mob, email, dept,city from " + db.schema + ".tbl_employ where gender = 'Female' and status  = 1");
            ResultSet rs = st.executeQuery("select code, name,gender ,mob, email, dept,city from " + db.schema + ".tbl_employ where gender = 'Female' and status  = 1");
            while (rs.next()) {
                
                table_data.add(new objemp(rs.getString("code"), rs.getString("name"), rs.getString("gender"), rs.getString("mob"), rs.getString("email"), rs.getString("dept"), rs.getString("city")));
            }
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listner_search() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtSearch.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objemp obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase()) || obj.getName().toLowerCase().contains(text.toLowerCase()) ) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Employ Found");
                }
            }
        });
    }

    private void listner_adv_search_po_id() {
        txtid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtid.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objemp obj : table_data) {
                        if (obj.getId().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Employ Found");
                }
            }
        });
    }

    private void listner_adv_search_name(){
        txtname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtname.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objemp obj : table_data) {
                        if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Employ Found");
                }
            }
        });
    }
    
       private void listner_adv_search_dept(){
        txtDept.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                table_data_search.removeAll(table_data_search);
                String text = txtDept.getText();
                if (text.equals("")) {
                    tblView.setItems(table_data);
                }
                else {
                    for (objemp obj : table_data) {
                        if (obj.getDept().toLowerCase().contains(text.toLowerCase())) {
                            table_data_search.add(obj);
                        }
                    }
                    tblView.setItems(table_data_search);
                    tpTable.setText(tblView.getItems().size() + " Employ Found");
                }
            }
        });
    }
       
    private void setLayout() {
        tpSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!tpSearch.isExpanded()) {
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), accrtable);
                    translateTransition.setToY(-210);
                    translateTransition.play();

                    accrtable.setPrefHeight(rectangle2D.getHeight() - anchrAdvSearch.getHeight());
                    tblView.setPrefHeight(rectangle2D.getHeight() -  anchrAdvSearch.getHeight());
                }
                else if (tpSearch.isExpanded()) {
                    if (anchrAdvSearch.getOpacity() == 1) {
                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), accrtable);
                        translateTransition.setToY(0);
                        translateTransition.play();

                        accrtable.setPrefHeight(rectangle2D.getHeight() - 50);
                        tblView.setPrefHeight(rectangle2D.getHeight() - 50);
                    }
                    else if (anchrAdvSearch.getOpacity() == 0) {
                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), accrtable);
                        translateTransition.setToY(-210);
                        translateTransition.play();

                        accrtable.setPrefHeight(rectangle2D.getHeight() - 150);
                        tblView.setPrefHeight(rectangle2D.getHeight() -  150);
                    }
                }
            }
        });
    }

    
    private boolean delete(String code) {
        try {
            Statement st = db.con.createStatement();
            int bool = st.executeUpdate("delete from " + db.schema + ".tbl_employ where code = '" + code + "'");
            if (bool > 0) {
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void doubleClickTableEdit() {
        tblView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!tblView.getSelectionModel().isEmpty() && t.getClickCount() == 2) {
                    btnEdit.fire();
                }
            }
        });
    }

    private void enterKeyEventOnTableView() {
        tblView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER && !tblView.getSelectionModel().isEmpty()) {
                    btnEdit.fire();
                    t.consume();
                }
            }
        });
    }

    @FXML
    private void btnadd_onaction(ActionEvent event) {
        EmployControllerActionIdentified("Add", "");
    }

    @FXML
    private void btnedit_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Employee from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                   tblView.requestFocus();
                    }
        }     
        else {
            EmployControllerActionIdentified("Edit", tblView.getSelectionModel().getSelectedItem().getId());
        }
    }

    @FXML
    private void btndelete_onaction(ActionEvent event) {
        if (tblView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Employee from table!", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {                  
                    tblView.requestFocus();
                    }           
        }
        else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to Delete Employee!", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                   if (delete(tblView.getSelectionModel().getSelectedItem().getId())) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Employ Deleted Sucessfully", ButtonType.OK);
                    alert1.showAndWait();
                    boolean_status.setValue(!boolean_status.getValue());
                }           
                    }
        }
    }

    @FXML
    private void btnprint_onaction(ActionEvent event) throws FileNotFoundException, DocumentException {
        try {
            File file_temp = File.createTempFile("Employlist", ".pdf");
            Createpdf(file_temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void Createpdf(File file_temp) throws FileNotFoundException, DocumentException, IOException{
            Document doc =new Document(PageSize.A4, 30, 20, 20, 40);
            //////////////////////////ADDD HEADING IN PDF////////////////////////////////////////
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font head = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
            Chunk title= new Chunk("LOOMS & WEAVES",head);
            Phrase tit = new Phrase();
            tit.add(title);
            Paragraph paratit = new Paragraph();
            paratit.add(tit);
            paratit.setAlignment(Element.ALIGN_CENTER);
            Chunk reportTitle= new Chunk("Employ List",chapterFont);
            Phrase phrase = new Phrase();
            phrase.add(reportTitle);
            Paragraph para = new Paragraph();
            para.add(phrase);
            para.setAlignment(Element.ALIGN_CENTER);
            Paragraph pg = new Paragraph();
            pg.add(new Paragraph(" ", paragraphFont));
            pg.setAlignment(Element.ALIGN_CENTER);
            //////////////////ADDD TABLE IN PDF///////////////////////////    
            PdfPTable table = new PdfPTable(7);
		float widths[] = { 25, 50,25,25,30,35,30 };
		table.setWidths(widths);
                table.setWidthPercentage(100);
		table.setHeaderRows(1);
            Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font content = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
            /////////////// add cell of table - header cell///////////////////////
			PdfPCell cell = new PdfPCell(new Phrase("EMPOLY CODE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("EMPLOY NAME",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("GENDER",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("MOBILE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("EMAIL",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("DEPARTMENT",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("PLACE",heading));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new BaseColor(0, 173, 239));
			table.addCell(cell);
                        Phrase ph;
	    //////////////// looping the table cell for adding definition/////////////////////////
                    int row = table_data.size();
                    for (int i = 0; i < row; i++)
                    {
                        objemp sm = table_data.get(i);
				cell = new PdfPCell();
				ph = new Phrase(sm.getId(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getName(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getSex(),content);
				cell.addElement(ph);
				table.addCell(cell); 
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getMob(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getEmail(),content);
				cell.addElement(ph);
				table.addCell(cell);

				cell = new PdfPCell();
				ph = new Phrase(sm.getDept(),content);
				cell.addElement(ph);
				table.addCell(cell);
                                
                                cell = new PdfPCell();
				ph = new Phrase(sm.getPlace(),content);
				cell.addElement(ph);
				table.addCell(cell);
		    }                    
            
            Font printer = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            Chunk print= new Chunk("****Printed On :"+dateFormat.format(date)+"****",printer);
            Phrase pri = new Phrase();
            pri.add(print);
            Paragraph paraprint = new Paragraph();
            paraprint.add(pri);
            paraprint.setAlignment(Element.ALIGN_CENTER);
            try {
                PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream(file_temp));
                doc.open();   
                doc.add(paratit);
                doc.add(para);               
                doc.add(pg);
                doc.add(table);
                doc.add(paraprint);

                doc.close();
                Desktop.getDesktop().open(file_temp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EmployMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void btnshow_onaction(ActionEvent event) {
        btnHide.setVisible(true);
        btnShow.setVisible(false);
        txtid.requestFocus();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), anchrAdvSearch);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), accrtable);
        translateTransition.setFromY(accrtable.getTranslateY());
        translateTransition.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
        parallelTransition.play();

        accrtable.setPrefHeight(rectangle2D.getHeight() - 50);
        tblView.setPrefHeight(rectangle2D.getHeight() - 50);
    }

    @FXML
    private void btnhide_onaction(ActionEvent event) {
        btnHide.setVisible(false);
        btnShow.setVisible(true);
        txtSearch.requestFocus();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), anchrAdvSearch);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), accrtable);
        translateTransition.setFromY(accrtable.getTranslateY());
        translateTransition.setToY(-150);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
        parallelTransition.play();

        accrtable.setPrefHeight(rectangle2D.getHeight()  - 100);
        tblView.setPrefHeight(rectangle2D.getHeight() -  100);
    }

    @FXML
    private void btnClear_onaction(ActionEvent event) {
        clear();
    }

    public void setStage(Stage stage_emp, Rectangle2D rectangle2D) {
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent t) {
//                t.consume();
//                setFadeOutTransition();
//            }
//        });
this.stage= stage_emp;

        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    t.consume();
                    setFadeOutTransition();
                }
            }
        });
    
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F5) {
                    t.consume();
                    btnAdd.fire();
                }
            }
        });       
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F6) {
                    t.consume();
                   btnEdit.fire();
                }
            }
        });          
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F8) {
                    t.consume();
                   btnClear.fire();
                }
            }
        });       
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F12) {
                    t.consume();
                    btnDelete.fire();
                }
            }
        });   
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F9) {
                    t.consume();
                    btnPrint.fire();
                }
            }
        });   
    }
    
    private void setFadeOutTransition() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage.close();
            }
        });
    
    }
    
}
