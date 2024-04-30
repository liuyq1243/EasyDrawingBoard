package drawing;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class StatusBoard extends HBox {
    private MainGUI fa;
    private Label x = new Label("x: "), y = new Label("y: "), serial = new Label("serial number: ");

    public StatusBoard(MainGUI mainGui) {
        super(50);
        this.fa=mainGui;

        getChildren().addAll(x,y,serial);
    }

    public void setSerial(String serial)
    {
        this.serial.setText("serial number: "+serial);
    }
}
