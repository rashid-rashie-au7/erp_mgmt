/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author miw101
 */
public class Login extends Application {

      @Override
      public void start(Stage stage) throws Exception {
            final File file = new File(FileSystemView.getFileSystemView().getHomeDirectory().getParent(), "Lock");
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock lock = randomAccessFile.getChannel().tryLock();

            if (lock == null) {
                  Platform.exit();
            }

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                  @Override
                  public void handle(WindowEvent t) {
                        try {
                              lock.release();
                              randomAccessFile.close();
                        }
                        catch (IOException ex) {
                              Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                  }
            });


            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            final Parent root = (Parent) loader.load();
            LoginController controller = (LoginController) loader.getController();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            stage.getIcons().add(new javafx.scene.image.Image("/login/loginN.png"));

            stage.setScene(scene);
            stage.setTitle("Login");
            root.setOpacity(0);
            stage.show();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
            fadeTransition.setToValue(1);
            fadeTransition.play();



            controller.setStageAndSetupListeners(stage); // or what you want to do

      }

      /**
       * The main() method is ignored in correctly deployed JavaFX application.
       * main() serves only as fallback in case the application can not be
       * launched through deployment artifacts, e.g., in IDEs with limited FX
       * support. NetBeans ignores main().
       *
       * @param args the command line arguments
       */
      public static void main(String[] args) {
            launch(args);
      }
}
