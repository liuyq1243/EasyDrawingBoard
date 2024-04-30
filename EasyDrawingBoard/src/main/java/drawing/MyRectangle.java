package drawing;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MyRectangle implements FuntionTool {
    private double originalX,originalY;
    private Rectangle currentRectangle = new Rectangle();

    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        currentRectangle=new Rectangle(e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,0,0);
        currentRectangle.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
        currentRectangle.setStroke(pane.mainGui.getToolBoard().getColor());
        currentRectangle.setFill(pane.mainGui.getToolBoard().getColor());
        originalX=e.getX()-pane.getWidth()/2;originalY=e.getY()-pane.getHeight()/2;pane.add(currentRectangle);
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        double x = e.getX()-pane.getWidth()/2, y = e.getY()-pane.getHeight()/2;
        if(e.isShiftDown())
        {
            double a = Math.max(Math.abs(originalX - x),Math.abs(originalY - y));
            currentRectangle.setWidth(a);
            currentRectangle.setHeight(a);
            currentRectangle.setX(x<originalX?originalX-a:originalX);
            currentRectangle.setY(y<originalY?originalY-a:originalY);
        }
        else
        {
            currentRectangle.setWidth(Math.abs(originalX - x));
            currentRectangle.setHeight(Math.abs(originalY - y));
            currentRectangle.setX(Math.min(originalX,x));
            currentRectangle.setY(Math.min(originalY, y));
        }
    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
        if(currentRectangle.getWidth()<3&&currentRectangle.getHeight()<3) {
            pane.delete(currentRectangle);
        }
    }
}
