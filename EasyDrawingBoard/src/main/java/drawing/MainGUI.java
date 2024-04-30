package drawing;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;

/**
 *  GUI组件集合类，主要负责布置各个部分位置的
 */
public class MainGUI extends BorderPane {
    private DrawBoard center;
    private StatusBoard bottom;
    private ToolBoard left;
    private PropertyBoard right;
    private TopBoard top;

    public MainGUI(){
        center=new DrawBoard(this);
        left=new ToolBoard(this);
        right=new PropertyBoard(this);
        bottom=new StatusBoard(this);
        top=new TopBoard(this);

        setRight(right);
        setLeft(left);
        setTop(top);
        setBottom(bottom);
        setCenter(center);

        setOnKeyPressed(event ->
        {
            if(event.getCode().equals(KeyCode.DELETE)&&left.getNowTool() instanceof MyChooser)
            {
                for (Shape shape : right.getSelected())
                    center.delete(shape);
                right.getSelected().clear();
                right.getMainGUI().getChildren().remove(1,right.getMainGUI().getChildren().size());
            }
        });
    }

    public TopBoard getTopBoard(){
        return this.top;
    }

    public StatusBoard getStatusBoard(){
        return this.bottom;
    }

    public ToolBoard getToolBoard(){
        return this.left;
    }

    public PropertyBoard getPropertyBoard(){
        return this.right;
    }

    public DrawBoard getDrawBoard(){
        return this.center;
    }
}
