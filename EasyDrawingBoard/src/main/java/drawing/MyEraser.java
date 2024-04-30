package drawing;

import controller.HelloController;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class MyEraser implements FuntionTool {
    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        HelloController.getScene().setCursor(new ImageCursor(new Image("image/rectangle.png"),100, 100));
        delete(e.getX()-12.8-pane.getWidth()/2,e.getY()-12.8-pane.getHeight()/2,e.getX()+12.8-pane.getWidth()/2,e.getY()+12.8-pane.getHeight()/2,pane.getGroup(), pane);
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        delete(e.getX()-12.8-pane.getWidth()/2,e.getY()-12.8-pane.getHeight()/2,e.getX()+12.8-pane.getWidth()/2,e.getY()+12.8-pane.getHeight()/2,pane.getGroup(), pane);
    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
        HelloController.getScene().setCursor(Cursor.DEFAULT);
    }

    private void delete(double x1, double y1, double x2, double y2, Group object, DrawBoard pane)
    {
        ArrayList<Shape> deleteBuffer = new ArrayList<>();
        for(Node node:object.getChildren())
            if(Geometry.inRange(x1,y1,x2,y2,node))
            {
                deleteBuffer.add((Shape)node);
            }
        for(Shape shape:deleteBuffer)
        {
            pane.mainGui.getPropertyBoard().deleteSelected(shape);
            pane.delete(shape);
        }
    }
}
