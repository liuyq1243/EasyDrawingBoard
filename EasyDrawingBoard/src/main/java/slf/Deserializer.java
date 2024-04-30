package slf;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  反序列化存储在文件中的对象，加载到内存中
 */
public abstract class Deserializer {
    private Group group=new Group();
    private static int READLIMIT=500;

    public Deserializer(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        //循环读取对象文件中所有的对象，将其反序列化
        for(int row=0;;){
            String shape = bufferedReader.readLine();

            if(shape==null){
                break;
            }
            Matcher matcher = Pattern.compile("([a-zA-Z]+)").matcher(shape);
            row++;

            //regex自定义文件中的字段
            if(!matcher.matches()){
                if(shape.matches("[ ]*]"))  break;
                else {
                    throw new SyntaxError(row,"Invalid Declaration");
                }
            }

            String property;
            bufferedReader.mark(READLIMIT);
            property= bufferedReader.readLine();
            row++;
            if(property.equals("")) continue;
            Pattern matcher2 = Pattern.compile("[ ]+[a-zA-Z0-9]+:[\\-a-zA-Z0-9.]+");


            //对文件中存储的序列化对象进行反序列化
            //直线
            if(shape.equals("Line")){
                Line line = new Line();
                while (matcher.matches()){
                    String left=property.split(":")[0].replace("[ ]+","");
                    String right=property.split(":")[1].replace("[ ]+","");
                    switch (left){
                        case "layoutX":
                            assign(right,line.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,line.layoutYProperty());
                            break;
                        case "startX":
                            assign(right,line.startXProperty());
                            break;
                        case "startY":
                            assign(right,line.startYProperty());
                            break;
                        case "endX":
                            assign(right,line.endXProperty());
                            break;
                        case "endY":
                            assign(right,line.endYProperty());
                            break;
                        case "stroke":
                            assign(right,line.strokeProperty());
                            break;
                        case "rotate":
                            assign(right,line.rotateProperty());
                            break;
                        case "strokeWidth":
                            assign(right,line.strokeWidthProperty());
                            break;
                    }
                    bufferedReader.mark(READLIMIT);
                    property= bufferedReader.readLine();
                    row++;

                    if(property!=null){
                        matcher.reset(property);
                    }else {
                        break;
                    }
                }
                add(line);
            }
            //贝塞尔曲线
            else if(shape.equals("CubicCurve")){
                CubicCurve cubicCurve = new CubicCurve();

                while (matcher.matches()){
                    String left=property.split(":")[0].replace("[ ]+","");
                    String right=property.split(":")[1].replace("[ ]+","");

                    switch (left){
                        case "layoutX":
                            assign(right,cubicCurve.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,cubicCurve.layoutYProperty());
                            break;
                        case "startX":
                            assign(right,cubicCurve.startXProperty());
                            break;
                        case "startY":
                            assign(right,cubicCurve.startYProperty());
                            break;
                        case "endX":
                            assign(right,cubicCurve.endXProperty());
                            break;
                        case "endY":
                            assign(right,cubicCurve.endYProperty());
                            break;
                        case "controlX1":
                            assign(right,cubicCurve.controlX1Property());
                            break;
                        case "controlX2":
                            assign(right,cubicCurve.controlX2Property());
                            break;
                        case "controlY1":
                            assign(right,cubicCurve.controlY1Property());
                            break;
                        case "controlY2":
                            assign(right,cubicCurve.controlY2Property());
                            break;
                        case "stroke":
                            assign(right,cubicCurve.strokeProperty());
                            break;
                        case "rotate":
                            assign(right,cubicCurve.rotateProperty());
                            break;
                        case "fill":
                            assign(right,cubicCurve.fillProperty());
                            break;
                        case "strokeWidth":
                            assign(right,cubicCurve.strokeWidthProperty());
                            break;
                    }

                    bufferedReader.mark(READLIMIT);
                    property= bufferedReader.readLine();
                    row++;

                    if(property!=null){
                        matcher.reset(property);
                    }else {
                        break;
                    }
                }
            }
            //椭圆
            else if(shape.equals("Ellipse")){
                Ellipse ellipse = new Ellipse();

                while (matcher.matches()){
                    String left = property.split(":")[0].replaceAll("[ ]+","");
                    String right = property.split(":")[1].replaceAll("[ ]+","");

                    switch (left)
                    {
                        case "layoutX":
                            assign(right,ellipse.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,ellipse.layoutYProperty());
                            break;
                        case "centerX":
                            assign(right,ellipse.centerXProperty());
                            break;
                        case "centerY":
                            assign(right,ellipse.centerYProperty());
                            break;
                        case "radiusX":
                            assign(right,ellipse.radiusXProperty());
                            break;
                        case "radiusY":
                            assign(right,ellipse.radiusYProperty());
                            break;
                        case "stroke":
                            assign(right,ellipse.strokeProperty());
                            break;
                        case "fill":
                            assign(right,ellipse.fillProperty());
                            break;
                        case "rotate":
                            assign(right,ellipse.rotateProperty());
                            break;
                        case "strokeWidth":
                            assign(right, ellipse.strokeWidthProperty());
                    }
                    bufferedReader.mark(READLIMIT);
                    property = bufferedReader.readLine();
                    row++;

                    if(property!=null){
                        matcher.reset(property);
                    }else {
                        break;
                    }
                }
                add(ellipse);
            }
            //矩形
            else if(shape.equals("Rectangle")){
                Rectangle rectangle = new Rectangle();

                while (matcher.matches()){
                    String left = property.split(":")[0].replaceAll("[ ]+","");
                    String right = property.split(":")[1].replaceAll("[ ]+","");

                    switch (left)
                    {
                        case "layoutX":
                            assign(right,rectangle.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,rectangle.layoutYProperty());
                            break;
                        case "x":
                            assign(right,rectangle.xProperty());
                            break;
                        case "y":
                            assign(right,rectangle.yProperty());
                            break;
                        case "width":
                            assign(right,rectangle.widthProperty());
                            break;
                        case "height":
                            assign(right,rectangle.heightProperty());
                            break;
                        case "stroke":
                            assign(right,rectangle.strokeProperty());
                            break;
                        case "fill":
                            assign(right,rectangle.fillProperty());
                            break;
                        case "rotate":
                            assign(right,rectangle.rotateProperty());
                            break;
                        case "strokeWidth":
                            assign(right, rectangle.strokeWidthProperty());
                    }

                    bufferedReader.mark(READLIMIT);
                    property= bufferedReader.readLine();
                    row++;

                    if(property!=null){
                        matcher.reset(property);
                    }else {
                        break;
                    }
                }
                add(rectangle);
            }
            //折线
            else if (shape.equals("Polyline")) {
                Polyline polyline = new Polyline();

                while (matcher.matches()){
                    String left=property.split(":")[0].replace("[ ]+","");
                    String right=property.split(":")[1].replace("[ ]+","");

                    if(left.matches("[xy][0-9]{1,5}")){
                        polyline.getPoints().add(Double.parseDouble(right));
                    }else {
                        switch (left){
                                case "layoutX":
                                    assign(right,polyline.layoutXProperty());
                                    break;
                                case "layoutY":
                                    assign(right,polyline.layoutYProperty());
                                    break;
                                case "stroke":
                                    assign(right,polyline.strokeProperty());
                                    break;
                                case "fill":
                                    assign(right,polyline.fillProperty());
                                    break;
                                case "rotate":
                                    assign(right,polyline.rotateProperty());
                                    break;
                                case "strokeWidth":
                                    assign(right, polyline.strokeWidthProperty());
                            }
                    }
                    bufferedReader.mark(READLIMIT);
                    property = bufferedReader.readLine();
                    row++;
                    if(property != null){
                        matcher.reset(property);
                    }
                    else{
                        break;
                    }
                }
                add(polyline);
            }
            bufferedReader.reset();
            row--;
        }
    }

    public void setGroup(){
        this.group=group;
    }

    private void assign(String value, Property property){
        if(property instanceof DoubleProperty){
            property.setValue(Double.parseDouble(value));
        }else if(property.getName().equals("fill")||property.getName().equals("stroke")) {
            if(!value.equals("null"))
                property.setValue(Color.valueOf(value));
        }
    }

    abstract protected void add(Shape node);
}
