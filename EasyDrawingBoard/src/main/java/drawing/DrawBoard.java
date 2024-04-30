package drawing;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DrawBoard extends Pane {
    private  PosHandler posHandler;
    MainGUI mainGui;

    private SimpleStringProperty x=new SimpleStringProperty("X: ");
    private SimpleStringProperty y=new SimpleStringProperty("Y: ");
    private Group group=new Group();

    public DrawBoard(MainGUI mainGui){
        this.mainGui = mainGui;

        setStyle("-fx-background-color: White");
        setStyle("-fx-border-color: BLACk");
//        InnerShadow innerShadow = new InnerShadow();
//        innerShadow.setOffsetX(5);
//        innerShadow.setOffsetY(5);
//        setEffect(innerShadow);

        getChildren().add(group);
        group.layoutXProperty().bind(widthProperty().divide(2));
        group.layoutYProperty().bind(heightProperty().divide(2));
        PosHandler posHandler = new PosHandler();
        group.setOnMouseEntered(posHandler);
        group.setOnMouseMoved(posHandler);
//      group.setOnMouseExited(posHandler);

        //GUI增加监听器，用两种方法实现（匿名内部类和lambda表达式）
        mainGui.heightProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setPrefWidth(mainGui.getPrefHeight()*0.7);
            }
        });
        mainGui.widthProperty().addListener((width, pre, now)->{
            setPrefWidth(mainGui.getPrefWidth()*0.7);
        });

        //方法引用(Java8)
        setOnMousePressed(this::press);
        setOnMouseDragged(this::drag);
        setOnMouseReleased(this::release);
    }

    public void press(MouseEvent e){
        mainGui.getToolBoard().getNowTool().MousePress(e,this);
    }

    public void drag(MouseEvent e){
        mainGui.getToolBoard().getNowTool().MouseDrag(e,this);
    }

    public void release(MouseEvent e){
        mainGui.getToolBoard().getNowTool().MouseRelease(e,this);
    }

    public void delete(Shape node) {
        mainGui.getPropertyBoard().delete(node);
        group.getChildren().remove(node);
    }

    class PosHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
                x.setValue("X: "+mouseEvent.getX());
                y.setValue("Y: "+mouseEvent.getY());
            }else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_MOVED)){
                x.setValue("X: "+mouseEvent.getX());
                y.setValue("Y: "+mouseEvent.getY());
            }
        }
    }

    public void clearDrawBoard(){
        mainGui.getPropertyBoard().getSelected().clear();
        mainGui.getPropertyBoard().clearProperty();
        group.getChildren().clear();
    }

    public void load(Shape node){
        mainGui.getChildren().add(node);
        mainGui.getPropertyBoard().add(node);
        node.setOnMouseEntered(event ->
        {
            mainGui.getStatusBoard().setSerial(String.valueOf(group.getChildren().indexOf(node)));
        });
        node.setOnMouseExited(event ->
        {
            mainGui.getStatusBoard().setSerial("");
        });
        MoveProcessor processor = new MoveProcessor();
        node.setOnMousePressed(processor);
        node.setOnMouseDragged(processor);
    }

    public void add(Shape node)
    {
        mainGui.getTopBoard().setSaved(false);
        load(node);
    }

    private class MoveProcessor implements EventHandler<MouseEvent>
    {
        private double originalX = 0, originalY = 0;
        @Override
        public void handle(MouseEvent event)
        {
            if(mainGui.getToolBoard().getNowTool() instanceof MyChooser)
            {

                if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED))
                {
                    if(event.getButton().equals(MouseButton.SECONDARY))
                    {
                        originalX = event.getX();//+((Node)event.getSource()).getLayoutX();
                        originalY = event.getY();//+((Node)event.getSource()).getLayoutY();
                    }
                }
                if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
                {
                    if(event.getButton().equals(MouseButton.SECONDARY))
                    {
                        ((Node)event.getSource()).setLayoutX(((Node)event.getSource()).getLayoutX()+event.getX()-originalX);
                        ((Node)event.getSource()).setLayoutY(((Node)event.getSource()).getLayoutY()+event.getY()- originalY);
                    }
                }
            }
        }
    }

    public PosHandler getPosHandler() {
        return posHandler;
    }

    public void setPosHandler(PosHandler posHandler) {
        this.posHandler = posHandler;
    }

    public MainGUI getMainGui() {
        return mainGui;
    }

    public void setMainGui(MainGUI mainGui) {
        this.mainGui = mainGui;
    }

    public String getX() {
        return x.get();
    }

    public SimpleStringProperty xProperty() {
        return x;
    }

    public void setX(String x) {
        this.x.set(x);
    }

    public String getY() {
        return y.get();
    }

    public SimpleStringProperty yProperty() {
        return y;
    }

    public void setY(String y) {
        this.y.set(y);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
