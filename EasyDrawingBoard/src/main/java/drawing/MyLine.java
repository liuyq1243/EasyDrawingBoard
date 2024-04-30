package drawing;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class MyLine implements FuntionTool {
    private Line currentLine = new Line();

    @Override
    public void MousePress(MouseEvent e, DrawBoard pane) {
        currentLine=new Line(e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2);
        currentLine.setStrokeWidth(pane.mainGui.getToolBoard().getStrokeWidth());
        currentLine.setStroke(pane.mainGui.getToolBoard().getColor());
        pane.add(currentLine);
    }

    @Override
    public void MouseDrag(MouseEvent e, DrawBoard pane) {
        currentLine.setEndX(e.getX()-pane.getWidth()/2);
        currentLine.setEndY(e.getY()-pane.getHeight()/2);
    }

    @Override
    public void MouseRelease(MouseEvent e, DrawBoard pane) {
        double lengthSquare = (currentLine.getStartX()-currentLine.getEndX())*(currentLine.getStartX()-currentLine.getEndX())+(currentLine.getStartY()-currentLine.getEndY())*(currentLine.getStartY()-currentLine.getEndY());
        if(lengthSquare<4) {
            pane.delete(currentLine);
        }
    }
}
