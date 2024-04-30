package controller;

import java.net.URL;
import java.util.ResourceBundle;

import drawing.MainGUI;
import drawing.MyCursor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnNew;

    @FXML
    private Button btn_quit;

    @FXML
    private Button btnOpen;

    private static MyCursor myCursor;
    private static Stage stage;
    private static Scene scene;
    private MainGUI mainGUI;

    @FXML
    void openFile(ActionEvent event) {

    }

    @FXML
    void newFile(ActionEvent event) {
        stage = (Stage)btnNew.getScene().getWindow();
        scene = new Scene(new MainGUI(),1200,800);
        stage.setScene(scene);

        myCursor=new MyCursor();
        mainGUI=new MainGUI();
        mainGUI.getToolBoard().initBind();
        mainGUI.getPropertyBoard().initBind();
        stage.setTitle("Easy Drawing Board");
        stage.show();
    }

    @FXML
    void quit(ActionEvent event) {
        System.out.println("程序正常退出");
        System.exit(0);
    }

    @FXML
    void initialize() {
        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'Hello.fxml'.";
        assert btn_quit != null : "fx:id=\"btn_quit\" was not injected: check your FXML file 'Hello.fxml'.";
        assert btnOpen != null : "fx:id=\"btnOpen\" was not injected: check your FXML file 'Hello.fxml'.";

    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        HelloController.stage = stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        HelloController.scene = scene;
    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public static MyCursor getMyCursor() {
        return myCursor;
    }
}
