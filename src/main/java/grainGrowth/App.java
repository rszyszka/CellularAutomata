package grainGrowth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("/MainView.fxml"));
        Parent mainNode = mainLoader.load();
        Scene scene = new Scene(mainNode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Multi Scale Modelling");
        primaryStage.getIcons().add(new Image("/isim.png"));
        primaryStage.show();
    }

}
