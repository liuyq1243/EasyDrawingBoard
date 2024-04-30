package drawing;


import controller.HelloController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ToolBoard extends VBox {
    MainGUI mainGUI;

    private Color color;
    private double strokeWidth;
    private ColorPicker colorPicker;
    private TextField strokeWidthPicker;
    private FuntionTool nowTool,myLine,myEllipse,myRectangle, myPolyline,myEraser,myChooser,myCubicCurve;
    private ToggleButton line,circle,rectangle,polygon,eraser,chooser,cubicCurve;
    private ArrayList<FuntionTool> allTool=new ArrayList<>();
    private MyCursor myCursor;

    public ToolBoard(MainGUI mainGUI) {
        super(20);
        this.mainGUI=mainGUI;

        Color none = Color.TRANSPARENT;
        BorderStrokeStyle solid = BorderStrokeStyle.SOLID;
        setBorder(new Border(new BorderStroke(none,Color.GRAY,none,none, solid,solid,solid,solid, CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        setPrefWidth(100);

        ImageView imageView;

        myChooser = new MyChooser();
        this.add(myChooser);
        imageView = new ImageView("image/chooser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        chooser = new ToggleButton("Select", imageView);
        getChildren().add(chooser);

        myLine = new MyLine();
        this.add(myLine);
        imageView = new ImageView("image/line.png");
        line = new ToggleButton("Line", imageView);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        getChildren().add(line);

        myCubicCurve = new MyCubicCurve();
        this.add(myCubicCurve);
        imageView = new ImageView("image/cubicCurve.png");
        cubicCurve = new ToggleButton("Curve", imageView);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        getChildren().add(cubicCurve);

        myEllipse =new MyEllipse();
        this.add(myEllipse);
        imageView = new ImageView("image/ellipse.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        circle = new ToggleButton("Ellipse", imageView);
        getChildren().add(circle);


        myRectangle=new MyRectangle();
        this.add(myRectangle);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        rectangle = new ToggleButton("Rec", imageView);
        getChildren().add(rectangle);

        myPolyline = new MyPolyline();
        this.add(myPolyline);
        imageView = new ImageView("image/polyline.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        polygon = new ToggleButton("Poly", imageView);
        getChildren().add(polygon);

        myEraser = new MyEraser();
        this.add(myEraser);
        imageView = new ImageView("image/eraser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        eraser = new ToggleButton("Eraser",imageView);
        getChildren().add(eraser);

        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        getChildren().add(colorPicker);
        colorPicker.setOnAction(event -> color = colorPicker.getValue());

        strokeWidthPicker = new TextField();
        strokeWidthPicker.setFocusTraversable(false);
        strokeWidthPicker.setText("2");
        getChildren().add(strokeWidthPicker);
        strokeWidthPicker.setOnKeyPressed(event ->
        {
            if(event.getCode().equals(KeyCode.ENTER)&&!strokeWidthPicker.getText().equals(""))try
            {
                if(strokeWidthPicker.getText().equals(""))
                    strokeWidthPicker.setText(""+strokeWidth);
                else
                    strokeWidth = Double.parseDouble(strokeWidthPicker.getText());
                mainGUI.getPropertyBoard().getName().requestFocus();
            }catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        });

        color = Color.BLACK;
        strokeWidth = 2;
        nowTool=allTool.get(0);
    }

    public void initBind()
    {
        myCursor = HelloController.getMyCursor();
        DragToSuit dragToSuit = new DragToSuit(myCursor);
        this.setOnMouseEntered(dragToSuit);
        this.setOnMouseExited(dragToSuit);
        this.setOnMouseMoved(dragToSuit);
        this.setOnMouseDragged(dragToSuit);
        this.setOnMousePressed(dragToSuit);
        this.setOnMouseReleased(dragToSuit);

        ToggleGroup toggleGroup = new ToggleGroup();
        chooser.setToggleGroup(toggleGroup);
        line.setToggleGroup(toggleGroup);
        cubicCurve.setToggleGroup(toggleGroup);
        circle.setToggleGroup(toggleGroup);
        rectangle.setToggleGroup(toggleGroup);
        polygon.setToggleGroup(toggleGroup);
        eraser.setToggleGroup(toggleGroup);

        //Tools的动作驱动
        chooser.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myChooser);
            myCursor.now = Cursor.DEFAULT;
        });

        line.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myLine);
            myCursor.now = Cursor.CROSSHAIR;
        });

        cubicCurve.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myCubicCurve);
            myCursor.now = Cursor.CROSSHAIR;
        });

        circle.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myEllipse);
            myCursor.now = Cursor.CROSSHAIR;
        });

        rectangle.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myRectangle);
            myCursor.now = Cursor.CROSSHAIR;
        });

        polygon.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myPolyline);
            myCursor.now = Cursor.CROSSHAIR;
        });

        eraser.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchtool(myEraser);
            myCursor.now = Cursor.DEFAULT;
        });

        colorPicker.setOnAction(event ->
        {
            color = colorPicker.getValue();
        });


        Effect effect = new DropShadow();
        DropShadow dropShadow = (DropShadow)effect;
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        for(Node node: getChildren())
        {
            Control button = (Control) node;
            button.setStyle("-fx-base: #c5cacd");
            button.setEffect(effect);
            button.prefWidthProperty().bind(prefWidthProperty());
            button.setOnMouseEntered(myCursor);
            button.setOnMouseExited(myCursor);
        }
    }

    public class DragToSuit implements EventHandler<MouseEvent>
    {
        private MyCursor myCursor;
        private boolean pressed = false;
        public DragToSuit(MyCursor myCursor)
        {
            this.myCursor = DragToSuit.this.myCursor;
        }
        @Override
        public void handle(MouseEvent event)
        {
            if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED))
            {
                if(getWidth()-event.getX()<10)
                {
                    HelloController.getScene().setCursor(Cursor.E_RESIZE);
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED))
            {
                HelloController.getScene().setCursor(myCursor.now);
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_MOVED))
            {
                if(!pressed)
                {
                    if (getWidth() - event.getX() >= 10)
                    {
                        HelloController.getScene().setCursor(myCursor.now);
                    } else
                    {
                        HelloController.getScene().setCursor(Cursor.E_RESIZE);
                    }
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED))
            {
                if(getWidth()-event.getX()<10)
                {
                    pressed = true;
                    setPrefWidth(event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
            {
                if(pressed)
                {
                    setPrefWidth(event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED))
            {
                pressed = false;
            }
        }
    }

    private void switchtool(FuntionTool tool){
        nowTool=tool;
    }

    private void add(FuntionTool tool)
    {
        if(nowTool==null) nowTool=tool;
        allTool.add(tool);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public FuntionTool getNowTool() {
        return nowTool;
    }

    public void setNowTool(FuntionTool nowTool) {
        this.nowTool = nowTool;
    }
}
