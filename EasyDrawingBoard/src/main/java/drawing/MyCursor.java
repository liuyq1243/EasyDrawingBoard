package drawing;

import controller.HelloController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

/**
 *  实现当鼠标放在所选工具上时鼠标游标变为手，离开为默认箭头
 */
public class MyCursor implements EventHandler<MouseEvent> {
    Cursor pre= HelloController.getScene().getCursor();
    Cursor now=Cursor.DEFAULT;

    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            pre=HelloController.getScene().getCursor();
            HelloController.getScene().setCursor(Cursor.HAND);
        }else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED)){
            HelloController.getScene().setCursor(now);
        }
    }
}
