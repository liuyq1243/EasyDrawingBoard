package drawing;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polyline;

public class MyPolyline implements FuntionTool {
    private Polyline currentPolyline =new Polyline();

    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        if(!pane.getGroup().getChildren().contains(currentPolyline))
        {
            currentPolyline =new Polyline();
            currentPolyline.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
            currentPolyline.setFill(pane.mainGui.getToolBoard().getColor());
            currentPolyline.setStroke(pane.mainGui.getToolBoard().getColor());
            if(currentPolyline.getPoints().isEmpty())
            {
                pane.add(currentPolyline);
                currentPolyline.getPoints().addAll(e.getX()-pane.getWidth()/2, e.getY()-pane.getHeight()/2);
            }
            currentPolyline.getPoints().addAll(e.getX()-pane.getWidth()/2, e.getY()-pane.getHeight()/2);
            return;
        }
        if(e.getButton().equals(MouseButton.SECONDARY))
        {
            if(currentPolyline.getPoints().isEmpty()) return;

            currentPolyline.getPoints().addAll(currentPolyline.getPoints().get(0), currentPolyline.getPoints().get(1));
            currentPolyline.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
            currentPolyline.setStroke(pane.mainGui.getToolBoard().getColor());
            currentPolyline.setFill(pane.mainGui.getToolBoard().getColor());
            pane.mainGui.getPropertyBoard().update(currentPolyline);
            currentPolyline =new Polyline();
            return;
        }
        currentPolyline.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
        currentPolyline.setFill(pane.mainGui.getToolBoard().getColor());
        currentPolyline.setStroke(pane.mainGui.getToolBoard().getColor());
        if(currentPolyline.getPoints().isEmpty())
        {
            pane.add(currentPolyline);
            currentPolyline.getPoints().addAll(e.getX()-pane.getWidth()/2, e.getY()-pane.getHeight()/2);
        }
        currentPolyline.getPoints().addAll(e.getX()-pane.getWidth()/2, e.getY()-pane.getHeight()/2);
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        if(e.getButton().equals(MouseButton.SECONDARY)) return;
        currentPolyline.getPoints().remove(currentPolyline.getPoints().size()-1);
        currentPolyline.getPoints().remove(currentPolyline.getPoints().size()-1);
        currentPolyline.getPoints().addAll(e.getX()-pane.getWidth()/2, e.getY()-pane.getHeight()/2);

    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
    }
}
