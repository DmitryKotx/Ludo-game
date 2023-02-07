package com.example.ludo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Base {
    @FXML
    private final Label label;
    @FXML
    private final ImageView chip1;
    @FXML
    private final ImageView chip2;
    @FXML
    private final ImageView chip3;
    @FXML
    private final ImageView chip4;
    private boolean bot;
    private int rough;
    private int countSix;


    public Base(Label label, ImageView chip1, ImageView chip2, ImageView chip3, ImageView chip4, boolean bot) {
        this.label = label;
        this.chip1 = chip1;
        this.chip2 = chip2;
        this.chip3 = chip3;
        this.chip4 = chip4;
        this.bot = bot;
    }

    public Label getLabel() {
        return label;
    }

    public ImageView getChip1() {
        return chip1;
    }

    public ImageView getChip2() {
        return chip2;
    }

    public ImageView getChip3() {
        return chip3;
    }

    public ImageView getChip4() {
        return chip4;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
    public int getRough() {
        return rough;
    }

    public void setRough(int rough) {
        this.rough = rough;
    }
    public int getCountSix() {
        return countSix;
    }

    public void setCountSix(int countSix) {
        this.countSix = countSix;
    }
}
