package com.example.ludo;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Chip {
    @FXML
    private ImageView image;
    private double xHome;
    private double yHome;
    private double x;
    private double y;
    private boolean selected;
    private boolean motion;
    private int pos = -2;
    private Color color;
    private boolean finished;

    public Chip() {
    }
    public Chip(ImageView image, Color color) {
        this.image = image;
        this.color = color;
        x = image.getLayoutX();
        y = image.getLayoutY();
        xHome = image.getLayoutX();
        yHome = image.getLayoutY();
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public double getXHome() {
        return xHome;
    }


    public double getYHome() {
        return yHome;
    }

    public ImageView getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }
    public Color getColor() {
        return color;
    }

}
