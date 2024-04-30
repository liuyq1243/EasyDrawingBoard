package drawing;

import controller.HelloController;
import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import slf.Deserializer;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  GUi界面的顶部，主要实现的是文件新建、打开、保存、导出等功能
 */
public class TopBoard extends MenuBar {
    MainGUI mainGUI;

    private Menu menu;
    private MenuItem newFile=new MenuItem("New");
    private MenuItem openFile=new MenuItem("Open");
    private MenuItem saveFile=new MenuItem("Save");
    private MenuItem exportFile=new MenuItem("Export");
    private MenuItem exit=new MenuItem("Exit");

    boolean saved=false;

    public TopBoard(MainGUI mainGui){
        this.mainGUI=mainGUI;

        Label menuLabel = new Label("File");
        //menuLabel.setStyle("-fx-background-color: #3872ba");
        menu=new Menu("",menuLabel);

//        InnerShadow innerShadow = new InnerShadow();
//        innerShadow.setOffsetY(8);
//        innerShadow.setOffsetY(8);
//        setEffect(innerShadow);

        menu.getItems().addAll(newFile,openFile,saveFile,exportFile,exportFile);

        //菜单功能实现
        newFile.setOnAction(e->{
            if(check()){
                saveFile.getOnAction().handle(e);
            }else{
                mainGui.getDrawBoard().clearDrawBoard();
            }
        });

        openFile.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File("./"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Shape List File","*.slf"),
                    new FileChooser.ExtensionFilter("Text(*.txt)","*.txt"),
                    new FileChooser.ExtensionFilter("All(*.*)","*.*"));
            File file = fileChooser.showOpenDialog(HelloController.getStage());

            if(file!=null){
                try {
                    MyDeserializer myDeserializer = new MyDeserializer(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        saveFile.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./"));

            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Shape List File(*.slf)","*.slf"),
                    new FileChooser.ExtensionFilter("Text(*.txt)","*.txt"),
                    new FileChooser.ExtensionFilter("All(*.*)","*.*"));
            File file = fileChooser.showOpenDialog(HelloController.getStage());

            if(file!=null){
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                    Pattern compile = Pattern.compile("shape\\.([a-zA-Z]+)");

                    for(ArrayList<Property> arrayList:mainGUI.getPropertyBoard().getObjectProperty()){
                        Matcher matcher = compile.matcher(mainGUI.getDrawBoard().getChildren().get(mainGUI.getPropertyBoard().getObjectProperty().indexOf(arrayList)).getClass().toString());

                        if(matcher.find()){
                            bufferedWriter.write(matcher.group(1));
                            bufferedWriter.newLine();
                        }

                        for(Property property:arrayList){
                            bufferedWriter.write(" "+property.getName()+":"+property.getValue());
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.flush();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exportFile.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./"));

            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Shape List File(*.slf)","*.slf"),
                    new FileChooser.ExtensionFilter("JPG(*.jpg)","*.jpg"),
                    new FileChooser.ExtensionFilter("All(*.*)","*.*"));
            File file = fileChooser.showOpenDialog(HelloController.getStage());
            WritableImage snapshot = mainGUI.getDrawBoard().getGroup().snapshot(new SnapshotParameters(), null);

            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),fileChooser.getSelectedExtensionFilter().getExtensions().get(0).replace("*.",""),file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exit.setOnAction(e->{
            newFile.getOnAction().handle(e);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to quit?");
            Optional<ButtonType> buttonType = alert.showAndWait();

//            System.out.println(buttonType.get());

            if(buttonType.get().equals(ButtonType.OK)){
                Start.getMainStage().close();
            }
        });

        //检测Stage是否为exit退出，如果不是则委托给exit，销毁原先的动作，这样防止退出时未保存文件
        HelloController.getStage().setOnCloseRequest(e->{
            exit.getOnAction().handle(new ActionEvent());
            e.consume();
        });

        this.getMenus().addAll(menu);
    }

    private class MyDeserializer extends Deserializer{
        public MyDeserializer(File file) throws IOException {
            super(file);
        }

        @Override
        protected void add(Shape shape) {
            mainGUI.getDrawBoard().load(shape);

            if(shape instanceof Polyline){
                mainGUI.getPropertyBoard().update(shape);
            }
        }
    }

    private boolean check(){
        if(!saved){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to save?");
            alert.setHeaderText(null);
            alert.setTitle("Attention");
            Optional<ButtonType> result = alert.showAndWait();
            return result.get() == ButtonType.OK;
        }

        return false;
    }

    public void setRecentSave(boolean recentSave)
    {
        this.saved = recentSave;
    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public MenuItem getNewFile() {
        return newFile;
    }

    public void setNewFile(MenuItem newFile) {
        this.newFile = newFile;
    }

    public MenuItem getOpenFile() {
        return openFile;
    }

    public void setOpenFile(MenuItem openFile) {
        this.openFile = openFile;
    }

    public MenuItem getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(MenuItem saveFile) {
        this.saveFile = saveFile;
    }

    public MenuItem getExportFile() {
        return exportFile;
    }

    public void setExportFile(MenuItem exportFile) {
        this.exportFile = exportFile;
    }

    public MenuItem getExit() {
        return exit;
    }

    public void setExit(MenuItem exit) {
        this.exit = exit;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
