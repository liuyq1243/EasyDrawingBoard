package drawing;


import javafx.scene.input.MouseEvent;

public interface FuntionTool {
    void MousePress(MouseEvent e, DrawBoard pane);
    void MouseDrag(MouseEvent e, DrawBoard pane);
    void MouseRelease(MouseEvent e,DrawBoard pane);
}
