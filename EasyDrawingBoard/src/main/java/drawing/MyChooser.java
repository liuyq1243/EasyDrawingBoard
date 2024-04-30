package drawing;

import controller.HelloController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 *  实现鼠标托拉出来的选择框
 */
public class MyChooser implements FuntionTool{
    private double originalX,originalY;
    private Rectangle rectangle = new Rectangle();
    private DrawBoard pane;
    private static Bloom bloom = new Bloom(0.3);

    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        if(e.getButton().equals(MouseButton.PRIMARY)){
            this.pane=pane;
            originalX=e.getX();originalY=e.getY();
            rectangle = new Rectangle(originalX,originalY,0,0);
            rectangle.setFill(Color.BLUE);
            rectangle.setOpacity(0.3);
            pane.getChildren().add(rectangle);
        }
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        HelloController.getScene().setCursor(Cursor.CLOSED_HAND);

        if(e.getButton().equals(MouseButton.PRIMARY)){
            if(e.getX()>=originalX && e.getY()>=originalY)
            {
                rectangle.setWidth(e.getX()-originalX);
                rectangle.setHeight(e.getY()-originalY);
            }else {
                rectangle.setWidth(Math.abs(originalX-e.getX()));
                rectangle.setHeight(Math.abs(originalY-e.getY()));
                rectangle.setX(Math.min(originalX,e.getX()));
                rectangle.setY(Math.min(originalY,e.getY()));
            }
            choose(originalX-pane.getWidth()/2,originalY-pane.getHeight()/2,e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,pane.getGroup());
        }
    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
        HelloController.getScene().setCursor(Cursor.DEFAULT);
        if(e.getButton().equals(MouseButton.PRIMARY))
        {
            choose(originalX - pane.getWidth() / 2, originalY - pane.getHeight() / 2, e.getX() - pane.getWidth() / 2, e.getY() - pane.getHeight() / 2, pane.getGroup());
            pane.getChildren().remove(rectangle);
            new Timeline(new KeyFrame(Duration.millis(1000), event ->
            {
                for (Node node : pane.getGroup().getChildren())
                {
                    node.setStyle(null);
                    node.setEffect(null);
                }
            })).play();
            pane.mainGui.getPropertyBoard().change();
        }
    }

    //Shape被选中之后fill与stroke变化
    private void choose(double x1, double y1, double x2, double y2, Group group){
        for(Node node:group.getChildren()){
            if(Geometry.inRange(x1,x2,y1,y2,node)){
                pane.mainGui.getPropertyBoard().addSelected((Shape) node);
                node.setStyle("-fx-fill: #4387de; -fx-stroke:#3872ba");
                node.setEffect(bloom);
            }else {
                pane.mainGui.getPropertyBoard().deleteSelected((Shape) node);
                node.setStyle(null);
                node.setEffect(null);
            }
        }
    }
}
