import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.CurrentTempUtil;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
        Scene scene = loader.load(); 
        stage.getIcons().add(new javafx.scene.image.Image(CurrentTempUtil.getResourcePath("src/resources/images/logo.png")));        
        stage.setTitle("TIC TAC TOE");
        stage.setScene(scene);

        stage.show();
    }

}
