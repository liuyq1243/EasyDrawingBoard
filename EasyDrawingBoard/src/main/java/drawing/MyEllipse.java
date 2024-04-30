package drawing;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class MyEllipse implements FuntionTool {
    private double leftx,lefty;
    private Ellipse currentEllipse = new Ellipse();

    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        currentEllipse=new Ellipse(e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,0,0);
        currentEllipse.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
        currentEllipse.setFill(pane.mainGui.getToolBoard().getColor());
        currentEllipse.setStroke(pane.mainGui.getToolBoard().getColor());
        leftx=e.getX();lefty=e.getY();
        pane.add(currentEllipse);
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        if(e.isShiftDown())
        {
            currentEllipse.setRadiusX(Math.max(Math.abs(e.getX()-leftx)/2,Math.abs(e.getY()-lefty)/2));
            currentEllipse.setRadiusY(Math.max(Math.abs(e.getX()-leftx)/2,Math.abs(e.getY()-lefty)/2));
        }
        else
        {
            currentEllipse.setRadiusX(Math.abs(e.getX()-leftx)/2);
            currentEllipse.setRadiusY(Math.abs(e.getY()-lefty)/2);
        }
        currentEllipse.setCenterX((e.getX()+leftx)/2-pane.getWidth()/2);
        currentEllipse.setCenterY((e.getY()+lefty)/2-pane.getHeight()/2);
    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
        if(currentEllipse.getRadiusX()<1&&currentEllipse.getRadiusY()<1) {
            pane.delete(currentEllipse);
        }
    }
}
