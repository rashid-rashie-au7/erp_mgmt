/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class MDIController implements Initializable {

    @FXML
    public static MenuBar MenuBar;
    @FXML
    private MenuItem MI_cat;
    @FXML
    private MenuItem MItax;
    @FXML
    private MenuItem MIitem;
    @FXML
    private MenuItem MIsup;
    @FXML
    private MenuItem MIunit;
    @FXML
    private MenuItem MI_brand;
    @FXML
    private MenuItem MI_dept;
    @FXML
    private MenuItem MI_employ;
     Stage stage_Category = new Stage(StageStyle.DECORATED);
    Scene scene_Category=null;
    Stage stage_Tax = new Stage(StageStyle.DECORATED);
    Scene scene_Tax=null;
    Stage stage_stockrpt = new Stage(StageStyle.DECORATED);
    Scene scene_stockrpt=null;
    Stage stage_salerpt = new Stage(StageStyle.DECORATED);
    Scene scene_salerpt=null;
    Stage stage_porpt = new Stage(StageStyle.DECORATED);
    Scene scene_porpt=null;
    Stage stage_creditrpt = new Stage(StageStyle.DECORATED);
    Scene scene_creditrpt=null;
    Stage stage_Unit = new Stage(StageStyle.DECORATED);
    Scene scene_Unit=null;
    Stage stage_Item = new Stage(StageStyle.DECORATED);
    Scene scene_Item=null;
    Stage stage_brand = new Stage(StageStyle.DECORATED);
    Scene scene_brand=null;
    Stage stage_dept = new Stage(StageStyle.DECORATED);
    Scene scene_dept=null;
    Stage stage_emp = new Stage(StageStyle.DECORATED);
    Scene scene_emp=null;
    Stage stage_sup = new Stage(StageStyle.DECORATED);
    Scene scene_sup=null;
    private Stage main_stage = new Stage();
    @FXML
    private MenuItem mi_po;
    Stage stage_po = new Stage(StageStyle.DECORATED);
    Scene scene_po=null;
    @FXML
    private MenuItem mi_stock;
    Stage stage_stock = new Stage(StageStyle.DECORATED);
    Scene scene_stock=null;
    @FXML
    private MenuItem mi_sales;
    Stage stage_sales = new Stage(StageStyle.DECORATED);
    Scene scene_sales=null;
    @FXML
    private MenuItem mi_client;
    Stage stage_client = new Stage(StageStyle.DECORATED);
    Scene scene_client=null;
    @FXML
    private MenuItem mi_short;
    Stage stage_short = new Stage(StageStyle.DECORATED);
    Scene scene_short=null;
    @FXML
    private AnchorPane anchrbtn;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnSales;
    @FXML
    private Button btnPurchase;
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnPOReport; 
    @FXML
    private Button btnItemRPT;
    @FXML
    private Button btnStockRpt;
    @FXML
    private Button btnSalesRpt;
    @FXML
    private Button btnDaily;
    Stage stage_daily= new Stage(StageStyle.DECORATED);
    Scene scene_daily=null;
    @FXML
    private Button btnCreditRpt;
    @FXML
    private Button btnItem;
    @FXML
    private Button btnClient;   
    @FXML
    private VBox vbxHome;
    T_Sales.SalesMasterController sm = null;
    FXMLLoader loader;
    Parent rootSales;
    public static String loaderType = "";
    @FXML
    private MenuItem mi_agent;
    Stage stage_agent= new Stage(StageStyle.DECORATED);
    Scene scene_agent=null;
    @FXML
    private MenuItem mi_port;
    Stage stage_portn= new Stage(StageStyle.DECORATED);
    Scene scene_portn=null;
    @FXML
    private MenuItem mi_wh;
    Stage stage_wh= new Stage(StageStyle.DECORATED);
    Scene scene_wh=null;
    @FXML
    private MenuItem mi_password;
    Stage stage_pass= new Stage(StageStyle.DECORATED);
    Scene scene_pass=null;
    @FXML
    private MenuItem mi_printer;
    Stage stage_pri= new Stage(StageStyle.DECORATED);
    Scene scene_pri=null;
    @FXML
    private MenuItem mi_stocktransfer;
    Stage stage_stktra= new Stage(StageStyle.DECORATED);
    Scene scene_stktra=null;
    @FXML
    private MenuItem mi_Exp_Master;
    Stage stage_expm= new Stage(StageStyle.DECORATED);
    Scene scene_expm=null;
    @FXML
    private MenuItem mi_Exp_reg;
    Stage stage_expreg= new Stage(StageStyle.DECORATED);
    Scene scene_expreg=null;
    @FXML
    private MenuItem mi_bank;
    Stage stage_bank= new Stage(StageStyle.DECORATED);
    Scene scene_bank=null;
    @FXML
    private MenuItem mi_Sup_coll;
    Stage stage_supcoll= new Stage(StageStyle.DECORATED);
    Scene scene_supcoll=null;
    @FXML
    private MenuItem mi_salesrpt;
    @FXML
    private MenuItem mi_salesRTrpt;
    Stage stage_salesrtnRpt= new Stage(StageStyle.DECORATED);
    Scene scene_salesrtnRpt=null;
    @FXML
    private MenuItem mi_porpt;
    @FXML
    private MenuItem mi_poRTrpt;
    Stage stage_portnRpt= new Stage(StageStyle.DECORATED);
    Scene scene_portnRpt=null;
    @FXML
    private MenuItem mi_stockrpt;
    @FXML
    private MenuItem mi_exprpt;
    @FXML
    private MenuItem mi_dailyrpt;
    @FXML
    private MenuItem mi_creditrpt;
    @FXML
    private MenuItem mi_salesRtn;
    Stage stage_salesrtn= new Stage(StageStyle.DECORATED);
    Scene scene_salesrtn=null;
    @FXML
    private MenuItem mi_mass;
     Stage stage_mass= new Stage(StageStyle.DECORATED);
    Scene scene_mass=null;
    @FXML
    private MenuItem mi_agentCol;
    Stage stage_agentcol= new Stage(StageStyle.DECORATED);
    Scene scene_agentcol=null; 
    @FXML
    private MenuItem mi_about;
    Stage stage_about= new Stage(StageStyle.DECORATED);
    Scene scene_about=null; 
    @FXML
    private MenuItem mi_massrpt;
    Stage stage_massrpt= new Stage(StageStyle.DECORATED);
    Scene scene_massrpt=null; 
    @FXML
    private MenuItem mi_Exprpt;
    Stage stage_exprpt= new Stage(StageStyle.DECORATED);
    Scene scene_exprpt=null; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnHome.fire();
//        set_components(Rectangle2D.EMPTY);
    }    

    @FXML
    private void mi_cat_onaction(ActionEvent event) {
        if (!stage_Category.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Category/Category.fxml"));
                Parent root = (Parent) loader.load();
                M_Category.CategoryController cc = loader.getController();
                scene_Category = new Scene(root);
                stage_Category.setScene(scene_Category);
                stage_Category.setResizable(false);
                stage_Category.setTitle("Category");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                cc.setStage(stage_Category);
                stage_Category.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void mi_tax_onaction(ActionEvent event) {
        if (!stage_Tax.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Tax_Master/TaxMaster.fxml"));
                Parent root = (Parent) loader.load();
                M_Tax_Master.TaxMasterController tc = loader.getController();
                scene_Tax = new Scene(root);
                stage_Tax.setScene(scene_Tax);
                stage_Tax.setResizable(false);
                stage_Tax.setTitle("Tax Creation");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                tc.setStage(stage_Tax);
                stage_Tax.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }

    @FXML
    private void mi_Item_onaction(ActionEvent event) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_Item.setX(primaryScreenBounds.getMinX());
        stage_Item.setY(primaryScreenBounds.getMinY());
        stage_Item.setWidth(primaryScreenBounds.getWidth());
        stage_Item.setHeight(primaryScreenBounds.getHeight());
        stage_Item.setResizable(false);
        stage_Item.setTitle("Item Master");
//        stage_Item.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Item/ItemMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_item = new Scene(root);
            stage_Item.setScene(scene_item);
            M_Item.ItemMasterController imc = loader.getController();
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_Item.setX(rectangle2D.getMinX());
            stage_Item.setY(rectangle2D.getMinY());
            stage_Item.setWidth(rectangle2D.getWidth());
            stage_Item.setHeight(rectangle2D.getHeight());
            imc.setStage(stage_Item, rectangle2D);
            stage_Item.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mi_Sup_onaction(ActionEvent event) {
        
      btnSupplier.fire();
    }
    
    public void setFadeInTransition(Parent root) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    private void mi_unit_onaction(ActionEvent event) {
        if (!stage_Unit.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Unit/Unit.fxml"));
                Parent root = (Parent) loader.load();
                M_Unit.UnitController uc = loader.getController();
                scene_Unit = new Scene(root);
                stage_Unit.setScene(scene_Unit);
                stage_Unit.setResizable(false);
                stage_Unit.setTitle("Unit Creation");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                uc.setStage(stage_Unit);
                stage_Unit.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_brand_onaction(ActionEvent event) {
        if (!stage_brand.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Brand/Brand.fxml"));
                Parent root = (Parent) loader.load();
                M_Brand.BrandController bc = loader.getController();
                scene_brand = new Scene(root);
                stage_brand.setScene(scene_brand);
                stage_brand.setResizable(false);
                stage_brand.setTitle("Brand Creation");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                bc.setStage(stage_brand);
                stage_brand.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_dept_onaction(ActionEvent event) {
        if (!stage_dept.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Department/Depart.fxml"));
                Parent root = (Parent) loader.load();
                M_Department.DepartController dc = loader.getController();
                scene_dept = new Scene(root);
                stage_dept.setScene(scene_dept);
                stage_dept.setResizable(false);
                stage_dept.setTitle("Department Creation");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                dc.setStage(stage_dept);
                stage_dept.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_emp_onaction(ActionEvent event) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_emp.setX(primaryScreenBounds.getMinX());
        stage_emp.setY(primaryScreenBounds.getMinY());
        stage_emp.setWidth(primaryScreenBounds.getWidth());
        stage_emp.setHeight(primaryScreenBounds.getHeight());
        stage_emp.setResizable(false);
        stage_emp.setTitle("Employ Master");
//        stage_emp.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Employ/EmployMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_emp = new Scene(root);
            stage_emp.setScene(scene_emp);
            M_Employ.EmployMasterController emc = loader.getController();
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_emp.setX(rectangle2D.getMinX());
            stage_emp.setY(rectangle2D.getMinY());
            stage_emp.setWidth(rectangle2D.getWidth());
            stage_emp.setHeight(rectangle2D.getHeight());
            emc.setStage(stage_emp, rectangle2D);
            stage_emp.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mi_po_onaction(ActionEvent event) {
        btnPurchase.fire();
    }

    @FXML
    private void mi_stock_onaction(ActionEvent event) {
        btnItem.fire();
        
    }

    @FXML
    private void mi_sales_onaction(ActionEvent event) {
       btnSales.fire();
    }

    @FXML
    private void mi_client_onaction(ActionEvent event) {
       btnClient.fire();
    }

    @FXML
    private void mi_short_onaction(ActionEvent event) {
        if (!stage_short.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Shortcuts/short.fxml"));
                Parent root = (Parent) loader.load();
                Shortcuts.ShortController sc = loader.getController();
                scene_short = new Scene(root);
                stage_short.setScene(scene_short);
                stage_short.setResizable(false);
                stage_short.setTitle("Shortcuts");
//                stage_short.initModality(Modality.WINDOW_MODAL);
                sc.setStage(stage_short);
                stage_short.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void btnHomeOnAction(ActionEvent event) {
//        remove_nodes();
        System.out.println("homepage");
        vbxHome.setStyle("-fx-background-image: url(\"res/banner.jpg\");-fx-background-size: 100% 100%;");
        
        setFadeInTranstionMillis();
    }
    private void remove_nodes() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), vbxHome);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        vbxHome.getChildren().removeAll(vbxHome.getChildren());
    }
    public void setFadeInTranstionMillis() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700), vbxHome);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    private void btnSalesOnAction(ActionEvent event) throws IOException {
       Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_sales.setX(primaryScreenBounds.getMinX());
        stage_sales.setY(primaryScreenBounds.getMinY());
        stage_sales.setWidth(primaryScreenBounds.getWidth());
        stage_sales.setHeight(primaryScreenBounds.getHeight());
        stage_sales.setResizable(false);
        stage_sales.setTitle("Sales");
//        stage_sales.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_Sales/SalesMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_sales = new Scene(root);
            stage_sales.setScene(scene_sales);
            T_Sales.SalesMasterController smc = loader.getController();
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_sales.setX(rectangle2D.getMinX());
            stage_sales.setY(rectangle2D.getMinY());
            stage_sales.setWidth(rectangle2D.getWidth());
            stage_sales.setHeight(rectangle2D.getHeight());
            smc.setStage(stage_sales, rectangle2D);
            stage_sales.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnPuchaseOnAction(ActionEvent event) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_po.setX(primaryScreenBounds.getMinX());
        stage_po.setY(primaryScreenBounds.getMinY());
        stage_po.setWidth(primaryScreenBounds.getWidth());
        stage_po.setHeight(primaryScreenBounds.getHeight());
        stage_po.setResizable(false);
        stage_po.setTitle("PURCHASE");
//        stage_po.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_Purchase/PurchaseMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_po = new Scene(root);
            stage_po.setScene(scene_po);
            T_Purchase.PurchaseMasterController pmc = loader.getController();
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_po.setX(rectangle2D.getMinX());
            stage_po.setY(rectangle2D.getMinY());
            stage_po.setWidth(rectangle2D.getWidth());
            stage_po.setHeight(rectangle2D.getHeight());
            pmc.setStage(stage_po, rectangle2D);
            stage_po.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSupplierOnAction(ActionEvent event) {
         Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_sup.setX(primaryScreenBounds.getMinX());
        stage_sup.setY(primaryScreenBounds.getMinY());
        stage_sup.setWidth(primaryScreenBounds.getWidth());
        stage_sup.setHeight(primaryScreenBounds.getHeight());
        stage_sup.setResizable(false);
        stage_sup.setTitle("Supplier Master");
//        stage_sup.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Supplier/SuppilerMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_sup = new Scene(root);
            stage_sup.setScene(scene_sup);
            M_Supplier.SuppilerMasterController smc = loader.getController();
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_sup.setX(rectangle2D.getMinX());
            stage_sup.setY(rectangle2D.getMinY());
            stage_sup.setWidth(rectangle2D.getWidth());
            stage_sup.setHeight(rectangle2D.getHeight());
            smc.setStage(stage_sup, rectangle2D);
            stage_sup.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnPoRPTonAction(ActionEvent event) {
        if (!stage_porpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_Purchase/po_rpt.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_Purchase.Po_rptController prc = loader.getController();
                scene_porpt = new Scene(root);
                stage_porpt.setScene(scene_porpt);
                stage_porpt.setResizable(false);
                stage_porpt.setTitle("Purchase Reports");
//                stage_porpt.initModality(Modality.WINDOW_MODAL);
                prc.setStage(stage_porpt);
                stage_porpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnItemRPTonAction(ActionEvent event) {
        mi_salesRTrpt.fire();
    }

    @FXML
    private void btnStockRPTonAction(ActionEvent event) {
        if (!stage_stockrpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_StockRpt/StockRpt.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_StockRpt.StockRptController src = loader.getController();
                scene_stockrpt = new Scene(root);
                stage_stockrpt.setScene(scene_stockrpt);
                stage_stockrpt.setResizable(false);
                stage_stockrpt.setTitle("Stock Reports");
//                stage_stockrpt.initModality(Modality.WINDOW_MODAL);
                src.setStage(stage_stockrpt);
                stage_stockrpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnSalesRPTonAction(ActionEvent event) {
        if (!stage_salerpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_SalesReport/salesrpt.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_SalesReport.SalesrptController src = loader.getController();
                scene_salerpt = new Scene(root);
                stage_salerpt.setScene(scene_salerpt);
                stage_salerpt.setResizable(false);
                stage_salerpt.setTitle("Sales Report");
//                stage_salerpt.initModality(Modality.WINDOW_MODAL);
                src.setStage(stage_salerpt);
                stage_salerpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnDailyonAction(ActionEvent event) {
        if (!stage_daily.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_Daily/DailyRpt.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_Daily.DailyRptController drc = loader.getController();
                scene_daily = new Scene(root);
                stage_daily.setScene(scene_daily);
                stage_daily.setResizable(false);
                stage_daily.setTitle("Daily Report");
//                stage_creditrpt.initModality(Modality.WINDOW_MODAL);
                drc.setStage(stage_daily);
                stage_daily.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnCreditRptonAction(ActionEvent event) {
        if (!stage_creditrpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_Creditbill/CreditRpt.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_Creditbill.CreditRptController crc = loader.getController();
                scene_creditrpt = new Scene(root);
                stage_creditrpt.setScene(scene_creditrpt);
                stage_creditrpt.setResizable(false);
                stage_creditrpt.setTitle("Credit Bill Report");
//                stage_creditrpt.initModality(Modality.WINDOW_MODAL);
                crc.setStage(stage_creditrpt);
                stage_creditrpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnitemOnAction(ActionEvent event) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_stock.setX(primaryScreenBounds.getMinX());
        stage_stock.setY(primaryScreenBounds.getMinY());
        stage_stock.setWidth(primaryScreenBounds.getWidth());
        stage_stock.setHeight(primaryScreenBounds.getHeight());
        stage_stock.setResizable(false);
        stage_stock.setTitle("Stock Master");
//        stage_stock.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_StockUpdation/StockMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_stock = new Scene(root);
            stage_stock.setScene(scene_stock);         
            T_StockUpdation.StockMasterController smc = loader.getController();
             Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_stock.setX(rectangle2D.getMinX());
            stage_stock.setY(rectangle2D.getMinY());
            stage_stock.setWidth(rectangle2D.getWidth());
            stage_stock.setHeight(rectangle2D.getHeight());
            smc.setStage(stage_stock, rectangle2D);
            stage_stock.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        
        
        
    }

    @FXML
    private void btnClientOnAction(ActionEvent event) {
         Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage_client.setX(primaryScreenBounds.getMinX());
        stage_client.setY(primaryScreenBounds.getMinY());
        stage_client.setWidth(primaryScreenBounds.getWidth());
        stage_client.setHeight(primaryScreenBounds.getHeight());
        stage_client.setResizable(false);
        stage_client.setTitle("Clients");
//        stage_client.getIcons().add(new javafx.scene.image.Image("/images/logo.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_Client/ClientMaster.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            Scene scene_clients = new Scene(root);
            stage_client.setScene(scene_clients);         
            T_Client.ClientMasterController cmc = loader.getController();
             Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            stage_client.setX(rectangle2D.getMinX());
            stage_client.setY(rectangle2D.getMinY());
            stage_client.setWidth(rectangle2D.getWidth());
            stage_client.setHeight(rectangle2D.getHeight());
            cmc.setStage(stage_client, rectangle2D);
            stage_client.show();
        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStage(Stage stage, Rectangle2D rectangle2D) {
       this.main_stage = stage;
//        set_components(rectangle2D);

        main_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
              event.consume();
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
              alert.showAndWait();
              if (alert.getResult() == ButtonType.YES) {  
                System.exit(0);  
              }  
            }
        });
    }

    @FXML
    private void mi_agent_onaction(ActionEvent event) {
        if (!stage_agent.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Agent/Agent.fxml"));
                Parent root = (Parent) loader.load();
                M_Agent.AgentController ac = loader.getController();
                scene_agent = new Scene(root);
                stage_agent.setScene(scene_agent);
                stage_agent.setResizable(false);
                stage_agent.setTitle("Agent Creation");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                ac.setStage(stage_agent);
                stage_agent.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }

    @FXML
    private void mi_port_onaction(ActionEvent event) {
      if (!stage_portn.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_PurchasReturn/ReturnMaster.fxml"));
                Parent root = (Parent) loader.load();
                T_PurchasReturn.ReturnMasterController pmc = loader.getController();
                scene_portn = new Scene(root);
                stage_portn.setScene(scene_portn);
                stage_portn.setResizable(false);
                stage_portn.setTitle("Purchase Return");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                pmc.setStage(stage_portn);
                stage_portn.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    @FXML
    private void mi_wh_onaction(ActionEvent event) {
        if (!stage_wh.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/M_Warehouse/Warehouse.fxml"));
                Parent root = (Parent) loader.load();
                M_Warehouse.WarehouseController wc = loader.getController();
                scene_wh = new Scene(root);
                stage_wh.setScene(scene_wh);
                stage_wh.setResizable(false);
                stage_wh.setTitle("Ware House Creation");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                wc.setStage(stage_wh);
                stage_wh.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_pass_onaction(ActionEvent event) {
        if (!stage_pass.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Password/password.fxml"));
                Parent root = (Parent) loader.load();
               Password.PasswordController pc = loader.getController();
                scene_pass = new Scene(root);
                stage_pass.setScene(scene_pass);
                stage_pass.setResizable(false);
                stage_pass.setTitle("Password Settings");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                pc.setStage(stage_pass);
                stage_pass.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_printer_onaction(ActionEvent event) {
        if (!stage_pri.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PrinterSettings/PrinterSettings.fxml"));
                Parent root = (Parent) loader.load();
                PrinterSettings.PrinterSettingsController psc = loader.getController();
                scene_pri = new Scene(root);
                stage_pri.setScene(scene_pri);
                stage_pri.setResizable(false);
                stage_pri.setTitle("Printer Settings");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                psc.setStage(stage_pri);
                stage_pri.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_stocktransfer_onaction(ActionEvent event) {
        if (!stage_stktra.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_StockTransfer/TransferMaster.fxml"));
                Parent root = (Parent) loader.load();
                T_StockTransfer.TransferMasterController tmc = loader.getController();
                scene_stktra = new Scene(root);
                stage_stktra.setScene(scene_stktra);
                stage_stktra.setResizable(false);
                stage_stktra.setTitle(" Stock Transfer");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                tmc.setStage(stage_stktra);
                stage_stktra.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_expmaster_onaction(ActionEvent event) {
        if (!stage_expm.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/A_ExpenseMaster/ExpenseMaster.fxml"));
                Parent root = (Parent) loader.load();
                A_ExpenseMaster.ExpenseMasterController emc = loader.getController();
                scene_expm = new Scene(root);
                stage_expm.setScene(scene_expm);
                stage_expm.setResizable(false);
                stage_expm.setTitle("Expense Master");
//              stage_Category.initModality(Modality.WINDOW_MODAL);
                emc.setStage(stage_expm);
                stage_expm.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_expreg_onaction(ActionEvent event) {
        if (!stage_expreg.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/A_ExpenseRegister/Expense.fxml"));
                Parent root = (Parent) loader.load();
                A_ExpenseRegister.ExpenseController ec = loader.getController();
                scene_expreg = new Scene(root);
                stage_expreg.setScene(scene_expreg);
                stage_expreg.setResizable(false);
                stage_expreg.setTitle("Expense Register");
//              stage_Category.initModality(Modality.WINDOW_MODAL);
                ec.setStage(stage_expreg);
                stage_expreg.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_bank_onaction(ActionEvent event) {
    }

    @FXML
    private void mi_supcol_onaction(ActionEvent event) {
    }

    @FXML
    private void mi_salesrpt_onaction(ActionEvent event) {
        btnSalesRpt.fire();
    }

    @FXML
    private void mi_salesRTrpt_onaction(ActionEvent event) {
       if (!stage_salesrtnRpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_SalesReturn/SalesReturn.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_SalesReturn.SalesReturnController src = loader.getController();
                scene_salesrtnRpt = new Scene(root);
                stage_salesrtnRpt.setScene(scene_salesrtnRpt);
                stage_salesrtnRpt.setResizable(false);
                stage_salesrtnRpt.setTitle("Sales Return Report");
//              stage_Category.initModality(Modality.WINDOW_MODAL);
                src.setStage(stage_salesrtnRpt);
                stage_salesrtnRpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_porpt_onaction(ActionEvent event) {
        btnPOReport.fire();
    }

    @FXML
    private void mi_poRTrpt_onaction(ActionEvent event) {
        if (!stage_portnRpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_POrtn/portn.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_POrtn.PortnController prc = loader.getController();
                scene_portnRpt = new Scene(root);
                stage_portnRpt.setScene(scene_portnRpt);
                stage_portnRpt.setResizable(false);
                stage_portnRpt.setTitle("Purchase Return Report");
//              stage_Category.initModality(Modality.WINDOW_MODAL);
                prc.setStage(stage_portnRpt);
                stage_portnRpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void mi_stockrpt_onaction(ActionEvent event) {
        btnStockRpt.fire();
    }

    @FXML
    private void mi_exprpt_onaction(ActionEvent event) {
        
    }

    @FXML
    private void mi_dailyrpt_onaction(ActionEvent event) {
        btnDaily.fire();
    }


    @FXML
    private void mi_creditrpt_onaction(ActionEvent event) {
        btnCreditRpt.fire();
    }

    @FXML
    private void mi_salesRtn_onaction(ActionEvent event) {
        if (!stage_salesrtn.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/T_SalesReturn/SalesReturnMaster.fxml"));
                Parent root = (Parent) loader.load();
                T_SalesReturn.SalesReturnMasterController pmc = loader.getController();
                scene_salesrtn = new Scene(root);
                stage_salesrtn.setScene(scene_salesrtn);
                stage_salesrtn.setResizable(false);
                stage_salesrtn.setTitle("Sales Return");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                pmc.setStage(stage_salesrtn);
                stage_salesrtn.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    @FXML
    private void mi_mass_onaction(ActionEvent event) {
         if (!stage_mass.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/excelbill/ExcelBill.fxml"));
                Parent root = (Parent) loader.load();
                excelbill.ExcelBillController ebc = loader.getController();
                scene_mass = new Scene(root);
                stage_mass.setScene(scene_mass);
                stage_mass.setResizable(false);
                stage_mass.setTitle("Sales Return");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                ebc.setStage(stage_mass);
                stage_mass.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    @FXML
    private void mi_agentcol_onaction(ActionEvent event) {
         if (!stage_agentcol.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/A_AgentCollection/AgentCollection.fxml"));
                Parent root = (Parent) loader.load();
                A_AgentCollection.AgentCollectionController acc = loader.getController();
                scene_agentcol = new Scene(root);
                stage_agentcol.setScene(scene_agentcol);
                stage_agentcol.setResizable(false);
                stage_agentcol.setTitle("Agent Collection");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                acc.setStage(stage_agentcol);
                stage_agentcol.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    @FXML
    private void about_onaction(ActionEvent event) {
            if (!stage_about.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/about/about.fxml"));
                Parent root = (Parent) loader.load();
                about.AboutController ac = loader.getController();
                scene_about = new Scene(root);
                stage_about.setScene(scene_about);
                stage_about.setResizable(false);
                stage_about.setTitle("Agent Collection");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                ac.setStage(stage_about);
                stage_about.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    @FXML
    private void mi_massrpt_onaction(ActionEvent event) {
        if (!stage_massrpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rpt_Massbill/Massbill.fxml"));
                Parent root = (Parent) loader.load();
                Rpt_Massbill.MassbillController mbc = loader.getController();
                scene_massrpt = new Scene(root);
                stage_massrpt.setScene(scene_massrpt);
                stage_massrpt.setResizable(false);
                stage_massrpt.setTitle("Mass Bill Report");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                mbc.setStage(stage_massrpt);
                stage_massrpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }

    @FXML
    private void mi_Exprpt_onaction(ActionEvent event) {
        if (!stage_exprpt.isShowing()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RptExpence/RptExpence.fxml"));
                Parent root = (Parent) loader.load();
                RptExpence.RptExpenceController rec = loader.getController();
                scene_exprpt = new Scene(root);
                stage_exprpt.setScene(scene_exprpt);
                stage_exprpt.setResizable(false);
                stage_exprpt.setTitle("Expence Report");
//                stage_Category.initModality(Modality.WINDOW_MODAL);
                rec.setStage(stage_exprpt);
                stage_exprpt.show();
                setFadeInTransition(root);
            } catch (IOException ex) {
                Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
}
