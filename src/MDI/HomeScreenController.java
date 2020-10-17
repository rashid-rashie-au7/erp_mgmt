/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author RASHI
 */
public class HomeScreenController implements Initializable {

    @FXML
    private VBox vbxImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setimage();
    }  
    
    private void setimage(){
        vbxImage.setStyle("-fx-background-image: url(\"res/banner6-1903x950\");-fx-background-size: cover;");
//        vbxImage.prefWidthProperty().bind(rootpane.widthProperty());
//        vbxImage.prefHeightProperty().bind(rootpane.heightProperty());
//       roo .getChildren().setAll(vbxHome);
//        setFadeInTranstionMillis();
    }
    
}
