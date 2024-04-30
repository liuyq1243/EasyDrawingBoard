package drawing;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurve;

public class MyCubicCurve implements FuntionTool {
    private int nowId=2;
    private CubicCurve currentCubicCurve;

    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        if(!pane.getGroup().getChildren().contains(currentCubicCurve))
        {
            nowId = 0;
            double x=e.getX()-pane.getWidth()/2,y=e.getY()-pane.getHeight()/2;
            currentCubicCurve = new CubicCurve(x, y, x, y, x, y, x, y);
            currentCubicCurve.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
            currentCubicCurve.setFill(pane.mainGui.getToolBoard().getColor());
            currentCubicCurve.setStroke(pane.mainGui.getToolBoard().getColor());
            pane.add(currentCubicCurve);
            return;
        }
        if(e.getButton().equals(MouseButton.SECONDARY))
        {
            nowId=2;return;
        }
        double x=e.getX()-pane.getWidth()/2,y=e.getY()-pane.getHeight()/2;
        nowId=(nowId+1)%3;
        switch (nowId)
        {
            case 0:
                currentCubicCurve = new CubicCurve(x, y, x, y, x, y, x, y);
                currentCubicCurve.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
                currentCubicCurve.setFill(pane.mainGui.getToolBoard().getColor());
                currentCubicCurve.setStroke(pane.mainGui.getToolBoard().getColor());
                pane.add(currentCubicCurve);
                break;
            case 1:
                currentCubicCurve.setControlX1(x);
                currentCubicCurve.setControlY1(y);
                break;
            case 2:
                currentCubicCurve.setControlX2(x);
                currentCubicCurve.setControlY2(y);
                break;
        }
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        if(e.getButton().equals(MouseButton.SECONDARY)) return;

        double x=e.getX()-pane.getWidth()/2,y=e.getY()-pane.getHeight()/2;
        switch (nowId)
        {
            case 0:
                currentCubicCurve.setEndX(x);
                currentCubicCurve.setEndY(y);
                break;
            case 1:
                currentCubicCurve.setControlX1(x);
                currentCubicCurve.setControlY1(y);
                break;
            case 2:
                currentCubicCurve.setControlX2(x);
                currentCubicCurve.setControlY2(y);
                break;
        }
    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
        if(e.getButton().equals(MouseButton.SECONDARY)) return;

        if(nowId != 0) return;
        double lengthSquare = (currentCubicCurve.getStartX()-currentCubicCurve.getEndX())*(currentCubicCurve.getStartX()-currentCubicCurve.getEndX())+(currentCubicCurve.getStartY()-currentCubicCurve.getEndY())*(currentCubicCurve.getStartY()-currentCubicCurve.getEndY());
        if(lengthSquare<4) {
            pane.delete(currentCubicCurve);
        }
    }
}
