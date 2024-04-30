package drawing;

import controller.HelloController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class PropertyBoard extends Pane {
    MainGUI mainGUI;

    private Label name=new Label("");
    private LinkedList<Shape> selected=new LinkedList<>();
    private ArrayList<ArrayList<Property>> objectProperty=new ArrayList<>();
    private Group group=new Group();
    private Shape nowShape;
    private ScrollBar scrollBar=new ScrollBar();

    public PropertyBoard(MainGUI mainGui) {

    }

    public class DragToSuit implements EventHandler<MouseEvent>
    {
        private MyCursor changeCursor;
        private boolean pressed = false;
        public DragToSuit(MyCursor changeCursor)
        {
            this.changeCursor = changeCursor;
        }
        @Override
        public void handle(MouseEvent event)
        {
            if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED))
            {
                if(event.getX()<10)
                {
                    HelloController.getScene().setCursor(Cursor.E_RESIZE);
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED))
            {
                HelloController.getScene().setCursor(changeCursor.now);
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_MOVED))
            {
                if(!pressed)
                {
                    if (event.getX() >= 10)
                    {
                        HelloController.getScene().setCursor(changeCursor.now);
                    } else
                    {
                        HelloController.getScene().setCursor(Cursor.E_RESIZE);
                    }
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED))
            {
                if(event.getX()<10)
                {
                    pressed = true;
                    setPrefWidth(getWidth()-event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
            {
                if(pressed)
                {
                    setPrefWidth(getWidth()-event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED))
            {
                pressed = false;
            }
        }
    }

    public LinkedList<Shape> getSelected(){
        return this.selected;
    }

    public void clearProperty(){
        objectProperty.clear();
    }

    public void add(Shape shape){
        add(shape,mainGUI.getDrawBoard().getChildren().indexOf(shape));
    }

    public void add(Shape shape,int index){
        objectProperty.add(new ArrayList<>());
        ArrayList<Property> properties = objectProperty.get(index);

        if(shape instanceof Line){
            Line line=(Line) shape;

            properties.add(shape.layoutXProperty());
            properties.add(shape.layoutXProperty());
            properties.add(line.startXProperty());
            properties.add(line.startYProperty());
            properties.add(line.endXProperty());
            properties.add(line.endYProperty());
            properties.add(line.strokeProperty());
            properties.add(line.rotateProperty());
            properties.add(line.strokeWidthProperty());
        }else if(shape instanceof CubicCurve){
            CubicCurve cubicCurve=(CubicCurve) shape;

            properties.add(shape.layoutXProperty());
            properties.add(shape.layoutYProperty());
            properties.add(cubicCurve.startXProperty());
            properties.add(cubicCurve.startYProperty());
            properties.add(cubicCurve.endXProperty());
            properties.add(cubicCurve.endYProperty());
            properties.add(cubicCurve.controlX1Property());
            properties.add(cubicCurve.controlY1Property());
            properties.add(cubicCurve.controlX2Property());
            properties.add(cubicCurve.controlY2Property());
            properties.add(cubicCurve.strokeProperty());
            properties.add(cubicCurve.fillProperty());
            properties.add(cubicCurve.rotateProperty());
            properties.add(cubicCurve.strokeWidthProperty());
        }else if(shape instanceof Ellipse){
            Ellipse ellipse=(Ellipse) shape;

            properties.add(shape.layoutXProperty());
            properties.add(shape.layoutYProperty());
            properties.add(ellipse.centerXProperty());
            properties.add(ellipse.centerYProperty());
            properties.add(ellipse.radiusXProperty());
            properties.add(ellipse.radiusYProperty());
            properties.add(ellipse.strokeProperty());
            properties.add(ellipse.fillProperty());
            properties.add(ellipse.rotateProperty());
            properties.add(ellipse.strokeWidthProperty());
        }else if(shape instanceof Rectangle){
            Rectangle rectangle=(Rectangle) shape;

            properties.add(shape.layoutXProperty());
            properties.add(shape.layoutYProperty());
            properties.add(rectangle.xProperty());
            properties.add(rectangle.yProperty());
            properties.add(rectangle.widthProperty());
            properties.add(rectangle.heightProperty());
            properties.add(rectangle.strokeProperty());
            properties.add(rectangle.fillProperty());
            properties.add(rectangle.rotateProperty());
            properties.add(rectangle.strokeWidthProperty());
        }else if(shape instanceof Polyline){
            Polyline polyline=(Polyline) shape;

            PointsProperty pointsProperty=new PointsProperty();
            for (int i=0;i<polyline.getPoints().size();i+=2){
                PointsProperty x,y;

                properties.add(x = new PointsProperty(polyline, "x"+i/2, i));
                properties.add(y = new PointsProperty(polyline, "y"+i/2, i+1));
                x.setPair(y);
                y.setPair(x);
                x.setNext(y);

                if(pointsProperty.getIndex()!=-1){
                    pointsProperty.setNext(x);
                }
                pointsProperty=y;
            }

            properties.add(shape.layoutXProperty());
            properties.add(shape.layoutYProperty());
            properties.add(polyline.strokeProperty());
            properties.add(polyline.fillProperty());
            properties.add(polyline.rotateProperty());
            properties.add(polyline.strokeWidthProperty());
        }
    }

    /**
     *  主要解决折线顶点问题
     */
    public class PointsProperty extends SimpleDoubleProperty {
        private String name;
        private int index=-1;
        private PointsProperty pair,next;
        private TextField textField;

        public PointsProperty(){}

        public PointsProperty(Polyline polyline,String name,int index){
            super(polyline.getPoints().get(index));
            this.name=name;
            this.index=index;

            addListener((observable,pre,now)->{
                polyline.getPoints().set(index,(Double) now);
            });
        }

        public void update() {
            if(next != null)
            {
                next.index--;
                next.update();
            }
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public PointsProperty getPair() {
            return pair;
        }

        public PointsProperty pairProperty() {
            return pair;
        }

        public void setPair(PointsProperty pair) {
            this.pair = pair;
        }

        public void setNext(PointsProperty next) {
            this.next=next;
        }

        public TextField getTextField() {
            return textField;
        }

        public void setTextField(TextField textField) {
            this.textField = textField;
        }
    }

    public void update(Shape shape){
        deleteShape(shape);
        group.getChildren().remove(shape);
    }

    public void deleteShape(Shape shape){
        int index=mainGUI.getDrawBoard().getChildren().indexOf(shape);

        if(index!=-1){
            delete(index);
        }
    }

    public void delete(Shape shape) {
        int i = mainGUI.getDrawBoard().getGroup().getChildren().indexOf(shape);
        if(i != -1)
        {
            delete(i);
        }
    }

    public void delete(int index){
        objectProperty.remove(index);
    }

    public void addSelected(Shape shape)
    {
        if(!selected.contains(shape))
            selected.add(shape);
        if(selected.size() == 1)    nowShape = shape;
        else nowShape = null;
    }

    public void deleteSelected(Shape shape)
    {
        selected.remove(shape);
        if(selected.size() == 1)nowShape = selected.get(0);
        else nowShape = null;
    }

    public void change()
    {
        if(selected.size() == 0)
        {
            setName("");
            group.getChildren().remove(1,group.getChildren().size());
        }
        if(selected.size() == 1)
            changeItem(nowShape);
        else if(selected.size() > 1)
            changeGroup();
    }

    private void changeGroup()
    {
        setName("Group");
        TreeMap<String,ArrayList<Property>> treeMap = check();
        group.getChildren().remove(1, group.getChildren().size());
        double y = name.getLayoutY() + 40;
        for(String key:treeMap.keySet())
        {
            Label lKey = new Label(key);
            lKey.setStyle("-fx-text-fill: #dedede");
            TextField tValue = new TextField("");
            lKey.setLayoutY(y+=40);
            tValue.setLayoutY(y);
            lKey.setLayoutX(5);
            tValue.setLayoutX(getWidth()*2/5);
            tValue.prefWidthProperty().bind(widthProperty().divide(2));
            group.getChildren().addAll(lKey, tValue);

            changeValue(tValue, treeMap.get(key));
        }
        scrollBar.setMax(y);
    }

    private TreeMap<String, ArrayList<Property>> check()
    {
        TreeMap<String, ArrayList<Property>> temp = new TreeMap<>();
        for(Shape shape:selected)
        {
            int index = mainGUI.getDrawBoard().getGroup().getChildren().indexOf(shape);
            if(temp.size()!=0)
            {
                for(Property property:objectProperty.get(index))//当前各个属性
                {
                    if(temp.containsKey(property.getName()))
                    {
                        temp.get(property.getName()).add(property);
                    }
                }
            }
            else
            {
                for(Property property:objectProperty.get(index))
                {
                    temp.put(property.getName(),new ArrayList<>());
                    temp.get(property.getName()).add(property);
                }
            }
        }
        ArrayList<String> removeList = new ArrayList<>();
        for(String name:temp.keySet())
        {
            if(temp.get(name).size() < selected.size())
            {
                removeList.add(name);
            }
        }
        for(String name: removeList)
        {
            temp.remove(name);
        }
        return temp;
    }

    public void setName(String name)
    {
        this.name.setText(name);
    }

    public void changeItem(Shape shape)
    {
        if(shape instanceof Line)setName("Line");
        else if(shape instanceof CubicCurve)setName("Curve");
        else if(shape instanceof Ellipse)setName("Ellipse");
        else if(shape instanceof Rectangle)setName("Rectangle");
        else if(shape instanceof Polyline)setName("Polyline");
        changeItem(shape, mainGUI.getDrawBoard().getGroup().getChildren().indexOf(shape));
    }

    private void changeItem(Shape shape, int i)
    {
        group.getChildren().remove(1,group.getChildren().size());
        double y = name.getLayoutY()+40;

        for(Property property: objectProperty.get(i))
        {
            Label key = new Label(property.getName());
            key.setStyle("-fx-text-fill: #dedede");
            TextField value = new TextField(property.getValue() != null?property.getValue().toString():"");
            property.addListener((val, pre, now)->
            {
                value.setText(property.getValue() != null?property.getValue().toString():"");
            });
            key.setLayoutY(y+=40);
            value.setLayoutY(y);
            key.setLayoutX(5);
            value.setLayoutX(getWidth()*2/5);
            value.prefWidthProperty().bind(widthProperty().divide(2));
            group.getChildren().addAll(key, value);

            changeValue(value,property);
            if(property instanceof PointsProperty)
            {
                if(((PointsProperty)property).getIndex()%2 == 0)
                {
                    Button button = new Button("x");
                    button.setStyle("-fx-text-fill: Red");
                    ((PointsProperty) property).setTextField(value);
                    button.setOnAction(event ->
                    {
                        objectProperty.get(i).remove(property);
                        objectProperty.get(i).remove(((PointsProperty) property).getPair());
                        ((PointsProperty) property).update();
                        ((PointsProperty) property).getPair().update();
                        value.disableProperty().set(true);
                        button.setDisable(true);
                        ((Polyline)shape).getPoints().remove(((PointsProperty)property).getIndex()+1);
                        ((Polyline)shape).getPoints().remove(((PointsProperty)property).getIndex());
                        if(((Polyline)shape).getPoints().isEmpty())
                        {
                            selected.clear();
                            mainGUI.getDrawBoard().delete(shape);
                            group.getChildren().remove(1,group.getChildren().size());
                        }
                    });
                    button.setPrefWidth(30);
                    button.setLayoutX(scrollBar.getLayoutX()-button.getPrefWidth());
//                    button.setLayoutX(value.getLayoutX()+value.getPrefWidth());
                    button.setLayoutY(y);
                    group.getChildren().add(button);
                }
                else
                {
                    value.disableProperty().bind(((PointsProperty)property).getPair().getTextField().disabledProperty());
                }
            }
        }
        scrollBar.setMax(y);
    }

    private void changeValue(TextField value, ArrayList<Property> properties)
    {
        for(Property property:properties)
            changeValue(value, property);
    }

    private void changeValue(TextField value, Property property)
    {
        value.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if(event.getCode().equals(KeyCode.ENTER))
            {
                try
                {
                    if (property instanceof DoubleProperty && !value.getText().equals("")) property.setValue(Double.parseDouble(value.getText()));
                    else if (!value.getText().equals("") && (property.getName().equals("fill") || property.getName().equals("stroke")))
                    {
                        property.setValue(Color.valueOf(value.getText()));
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                name.requestFocus();
            }
        });

        value.focusedProperty().addListener((focused, pre, now) ->
        {
            if(!now)
            {
                try
                {
                    if(property instanceof DoubleProperty && !value.getText().equals(""))property.setValue(Double.parseDouble(value.getText()));
                    else if(!value.getText().equals("") && (property.getName().equals("fill")||property.getName().equals("stroke")))
                    {
                        property.setValue(Color.valueOf(value.getText()));
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initBind()
    {
        DragToSuit dragToSuit = new DragToSuit(HelloController.getMyCursor());
        this.setOnMouseEntered(dragToSuit);
        this.setOnMouseExited(dragToSuit);
        this.setOnMouseMoved(dragToSuit);
        this.setOnMouseDragged(dragToSuit);
        this.setOnMousePressed(dragToSuit);
        this.setOnMouseReleased(dragToSuit);
    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void setSelected(LinkedList<Shape> selected) {
        this.selected = selected;
    }

    public ArrayList<ArrayList<Property>> getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(ArrayList<ArrayList<Property>> objectProperty) {
        this.objectProperty = objectProperty;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Shape getNowShape() {
        return nowShape;
    }

    public void setNowShape(Shape nowShape) {
        this.nowShape = nowShape;
    }
}
