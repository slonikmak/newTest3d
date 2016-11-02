package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.jar.Attributes;

public class Controller implements Initializable{
    Timeline timeLine = null;

    @FXML
    private Pane pane;

    @FXML
    private Slider sliderX;

    @FXML
    private Slider sliderY;

    @FXML
    private Slider sliderZ;

    @FXML
    private Slider sliderZoom;

    @FXML
    private Label labelX;

    @FXML
    private Label labelY;

    @FXML
    private Label labelZ;

    @FXML
    private Label labelZoom;

    Group boxGroup;

    private int count;

    @FXML
    void startTimer(ActionEvent event) {
        if (timeLine==null) setTimeLine();
        timeLine.play();
    }

    @FXML
    void stopTimer(ActionEvent event) {

    }



    private ObservableList<MyPoint> array = FXCollections.observableArrayList();


    public void setTimeLine(){
        count = 0;
        timeLine = new Timeline(new KeyFrame(Duration.millis(100), ae->{
            boxGroup.getChildren().add(array.get(count));
            count++;
        }));
        timeLine.setCycleCount(array.size());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        initPoints();



        Group lightGroup = new Group();
        boxGroup = new Group();
        Group subGroup = new Group();

        SubScene subScene = new SubScene(subGroup, 750, 600, true, SceneAntialiasing.BALANCED);

        pane.getChildren().add(subScene);

        Box box = new Box(400, 400, 400);
        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.web("#ffff0080"));
        greyMaterial.setSpecularColor(Color.web("#ffff0080"));
        box.setMaterial(greyMaterial);




        //Create lines
        Line lineX = new Line(-700,0,700,0);
        Line lineY = new Line(0,-700,0,700);
        Line lineZ = new Line(-700,0,700,0);
        lineZ.setRotate(90);
        lineZ.setRotationAxis(Rotate.Y_AXIS);

        lineX.setStrokeWidth(3);
        lineY.setStrokeWidth(3);
        lineZ.setStrokeWidth(3);


        ///Create axis
        Cylinder cylinderX = new Cylinder(5, 600);
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);
        cylinderX.setMaterial(redMaterial);
        cylinderX.setTranslateX(-200);
        cylinderX.setTranslateZ(200);
        cylinderX.setTranslateY(-100);

        Cylinder cylinderY = new Cylinder(5, 600);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setSpecularColor(Color.GREEN);
        greenMaterial.setDiffuseColor(Color.GREENYELLOW);
        cylinderY.setMaterial(greenMaterial);
        cylinderY.setTranslateX(100);
        cylinderY.setTranslateZ(200);
        cylinderY.setTranslateY(200);
        cylinderY.setRotate(90);

        Cylinder cylinderZ = new Cylinder(5, 600);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setSpecularColor(Color.BLUE);
        blueMaterial.setDiffuseColor(Color.AQUA);
        cylinderZ.setMaterial(blueMaterial);
        cylinderZ.setTranslateX(-200);
        cylinderZ.setTranslateZ(-100);
        cylinderZ.setTranslateY(200);
        cylinderZ.setRotationAxis(Rotate.X_AXIS);
        cylinderZ.setRotate(90);

        //Lights

        PointLight light = new PointLight();
        light.setTranslateX(350);
        light.setTranslateY(100);
        light.setTranslateZ(-500);

        //Camera

        PerspectiveCamera camera = new PerspectiveCamera(false);// создание камеры
        camera.setTranslateX(-400);
        camera.setTranslateY(-300);
        camera.setTranslateZ(-400);
        subScene.setCamera(camera);

        boxGroup.getChildren().addAll(cylinderX, cylinderY,cylinderZ);
        /*array.forEach(myPoint -> {
            boxGroup.getChildren().add(myPoint);
        });*/
        lightGroup.getChildren().add(light);
        subGroup.getChildren().addAll(boxGroup, lightGroup);

        sliderX.setMin(0);
        sliderX.setMax(360);
        sliderY.setMin(0);
        sliderY.setMax(360);
        sliderZ.setMin(0);
        sliderZ.setMax(360);

        sliderZoom.setMin(0);
        sliderZoom.setMax(2);
        sliderZoom.setValue(1);


        Rotate rotateX = new Rotate(0,0, 0, -0,  Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, 0, 0, -0,Rotate.Y_AXIS);
        Rotate rotateZ = new Rotate(0,  0, 0, -0,Rotate.Z_AXIS);

        Scale scale = new Scale();


        //box.getTransforms().addAll(rotateX, rotateY, rotateZ);
        boxGroup.getTransforms().addAll(rotateX, rotateY, rotateZ, scale);

        rotateX.angleProperty().bind(sliderX.valueProperty());
        rotateY.angleProperty().bind(sliderY.valueProperty());
        rotateZ.angleProperty().bind(sliderZ.valueProperty());

        scale.xProperty().bind(sliderZoom.valueProperty());
        scale.yProperty().bind(sliderZoom.valueProperty());
        scale.zProperty().bind(sliderZoom.valueProperty());

        labelX.textProperty().bindBidirectional(sliderX.valueProperty(), new MyConverter());
        labelY.textProperty().bindBidirectional(sliderY.valueProperty(), new MyConverter());
        labelZ.textProperty().bindBidirectional(sliderZ.valueProperty(), new MyConverter());
        labelZoom.textProperty().bindBidirectional(sliderZoom.valueProperty(), new MyConverter());

    }

    private class MyConverter extends StringConverter<Number>{

        @Override
        public String toString(Number object) {
            return ""+object.intValue();
        }

        @Override
        public Number fromString(String string) {
            return null;
        }
    }

    public void initPoints(){

        for (int i = 0; i < 100; i++) {

            array.add(new MyPoint(2, 6*i, 100*Math.sin(i*(2*Math.PI/100))*2, -3*i));
        }

    }


}
