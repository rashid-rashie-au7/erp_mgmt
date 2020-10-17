/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class AboutController implements Initializable {
    private Stage stage = new Stage(StageStyle.DECORATED);
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private ImageView imgview;
    @FXML
    private Label lbl3;
    @FXML
    private Label lbl5;
    @FXML
    private Label lbl4;
    @FXML
    private Label lbl6;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setStage(Stage stage_about) {
    this.stage= stage_about;   
    }
    
}
