package drawing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("../views/Hello.fxml"));//路径由生成的class文件结构决定
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Easy Drawing Board");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static Stage getMainStage(){
        return mainStage;
    }
}