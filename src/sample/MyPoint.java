package sample;

import javafx.scene.shape.Sphere;

/**
 * Created by Oceanos on 02.11.2016.
 */
public class MyPoint extends Sphere {
    double z;
    double x;
    double y;

    public MyPoint(double radius, double x, double y, double z){
        super(radius);

        this.x = x;
        this.y = y;
        this.z = z;
        setTranslateX(x-200);
        setTranslateY(y+200);
        setTranslateZ(z+200);
    }
}
