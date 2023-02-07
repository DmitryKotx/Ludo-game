package com.example.ludo;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameController {
    Stage stage;
    Scene scene;
    Parent root;
    int cube;
    int cPlayers;
    int cBots;

    List<Color> colors = Arrays.asList(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
    @FXML
    ImageView bChip1;
    @FXML
    ImageView bChip2;

    @FXML
    ImageView bChip3;

    @FXML
    ImageView bChip4;
    @FXML
    ImageView yChip1;
    @FXML
    ImageView yChip2;

    @FXML
    ImageView yChip3;

    @FXML
    ImageView yChip4;
    @FXML
    ImageView rChip1;
    @FXML
    ImageView rChip2;

    @FXML
    ImageView rChip3;

    @FXML
    ImageView rChip4;
    @FXML
    ImageView gChip1;
    @FXML
    ImageView gChip2;

    @FXML
    ImageView gChip3;
    @FXML
    ImageView gChip4;
    @FXML
    ImageView bCube;
    @FXML
    ImageView rCube;
    @FXML
    ImageView gCube;
    @FXML
    ImageView yCube;
    @FXML
    ProgressBar bBar;
    @FXML
    ProgressBar rBar;
    @FXML
    ProgressBar gBar;
    @FXML
    ProgressBar yBar;
    @FXML
    Label bLabel;
    @FXML
    Label rLabel;
    @FXML
    Label gLabel;
    @FXML
    Label yLabel;
    @FXML
    Label bWin;
    @FXML
    Label rWin;
    @FXML
    Label gWin;
    @FXML
    Label yWin;

    Chip  cur;
    List<Integer> saveZone = Arrays.asList(10, 28, 46, 64);
    List<Image> images = Arrays.asList(
            new Image(new File("src/main/resources/com/example/ludo/cube/1.png").toURI().toString()),
            new Image(new File("src/main/resources/com/example/ludo/cube/2.png").toURI().toString()),
            new Image(new File("src/main/resources/com/example/ludo/cube/3.png").toURI().toString()),
            new Image(new File("src/main/resources/com/example/ludo/cube/4.png").toURI().toString()),
            new Image(new File("src/main/resources/com/example/ludo/cube/5.png").toURI().toString()),
            new Image(new File("src/main/resources/com/example/ludo/cube/6.png").toURI().toString()));
    Map<Color,double[]> paths = getMap();
    Map<Color, double[]> firstStep = getStep();
    public Map<Color, double[]> getMap () throws IOException{
        Map<Color, double[]> map = new HashMap<>();
        map.put(Color.BLUE, input("src/main/resources/com/example/ludo/coords/bCoords.txt"));
        map.put(Color.RED, input("src/main/resources/com/example/ludo/coords/rCoords.txt"));
        map.put(Color.GREEN, input("src/main/resources/com/example/ludo/coords/gCoords.txt"));
        map.put(Color.YELLOW, input("src/main/resources/com/example/ludo/coords/yCoords.txt"));
        return map;
    }

    public Map<Color, double[]> getStep () {
        Map<Color, double[]> map = new HashMap<>();
        map.put(Color.BLUE, new double[] {787, 430});
        map.put(Color.RED, new double[] {529.5, 584.5});
        map.put(Color.GREEN, new double[] {375, 327});
        map.put(Color.YELLOW, new double[] {632.5, 172.5});
        return map;
    }
    public GameController() throws IOException {
    }
    public List<Chip> create (ImageView image1, ImageView image2, ImageView image3, ImageView image4, Color color) {
        return Arrays.asList(new Chip(image1, color),
                new Chip(image2, color),
                new Chip(image3, color),
                new Chip(image4, color));
    }
    public Map<Color,List<Chip>> getChips () {
        Map<Color,List<Chip>> chips = new HashMap<>();
        chips.put(Color.BLUE, create(bChip1, bChip2, bChip3, bChip4, Color.BLUE));
        chips.put(Color.RED, create(rChip1, rChip2, rChip3, rChip4, Color.RED));
        chips.put(Color.GREEN, create(gChip1, gChip2, gChip3, gChip4, Color.GREEN));
        chips.put(Color.YELLOW, create(yChip1, yChip2, yChip3, yChip4, Color.YELLOW));
        return chips;
    }
    public int getMoves (Chip cur) {
        int moves;
        if ((cube == 1 || cube == 6) && cur.getPos() < 0) {
            moves = 1;
        } else {
            moves = cube;
        }
        return moves;
    }


    public int randomNumber () {
        return 1 + (int) (Math.random() * 6);
    }
    public int randomPlayer (int n) {
        return (int) (Math.random() * n);
    }

    public double[] input(String path) throws IOException {
        String strArr;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            strArr = reader.readLine();
        }
        return Arrays.stream(strArr.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }

    public ScaleTransition getScale (int n) {
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(cur.getImage());
        scale.setDuration(Duration.millis(250));
        scale.setCycleCount(n*2);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(0.3);
        scale.setByY(0.3);
        scale.setAutoReverse(true);
        return scale;
    }

    public void move(double[] arr, double[] firstStep) {
        int moves = getMoves(cur);
        ScaleTransition scale = getScale(moves);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(cur.getImage());
        int dur = 500;
        for (int i = cur.getPos(); i < cur.getPos() + moves*2; i+=2) {
            PauseTransition pause = new PauseTransition(Duration.millis(dur));
            int finalI = i;
            pause.setOnFinished(event -> {
                if (finalI < 0) {
                    if (cube == 1 || cube == 6) {
                        transition.setByX(firstStep[0] - cur.getImage().getLayoutX());
                        transition.setByY(firstStep[1] - cur.getImage().getLayoutY());
                    }
                }
                if (finalI >= 0) {
                    transition.setByX(arr[finalI]);
                    transition.setByY(arr[finalI + 1]);
                }

                transition.play();
                scale.play();
            });
            pause.play();
            dur += 500;
        }
    }

    public void selected() {
        if (cur != null) {
            if (cur.isMotion()) {
                return;
            }
        }
        for (Chip chip : chips.get(color)) {
            ColorAdjust adjust = new ColorAdjust();
            adjust.setContrast(0);
            adjust.setBrightness(0);
            chip.getImage().setEffect(adjust);
        }

        for (Chip chip : chips.get(color)) {
            if (chip.getImage().isHover()) {
                cur = chip;
                cur.setSelected(true);
                cur.getImage().setFocusTraversable(true);
                ColorAdjust adjust = new ColorAdjust();
                adjust.setContrast(1);
                adjust.setBrightness(0.7);
                cur.getImage().setEffect(adjust);
            }
        }
        coords = getFuturePos(cur,paths.get(color), firstStep.get(color));
        if (checkCell(cur, coords) || checkFinish(cur) || bases.get(color).isBot()) {
            cur.setSelected(false);
            cur.getImage().setFocusTraversable(false);
            ColorAdjust adjust = new ColorAdjust();
            adjust.setContrast(0);
            adjust.setBrightness(0);
            cur.getImage().setEffect(adjust);
            return;
        }
        assert cur != null;
        cur.getImage().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER && cur.isSelected() && cur.getColor() == color) {
                    cur.setMotion(true);
                    move(paths.get(color), firstStep.get(color));
                    cur.setX(coords[0]);
                    cur.setY(coords[1]);
                    ColorAdjust adjust = new ColorAdjust();
                    adjust.setContrast(0);
                    cur.getImage().setEffect(adjust);
                    cur.setSelected(false);
                    cur.getImage().setFocusTraversable(false);
                }
            }
        });
    }
    Chip curT = null;
    double[] coordsT;
    public boolean botChoice() {
        int maxPos = -2;
        List<Chip> potentialCur = new ArrayList<>();
        List<Chip> removeList = new ArrayList<>();
        int count = 0;
        for (Chip chip : chips.get(color)) {
            ColorAdjust adjust = new ColorAdjust();
            adjust.setContrast(0);
            adjust.setBrightness(0);
            chip.getImage().setEffect(adjust);
        }
        for (Chip chip : chips.get(color)) {
            if (chip.getX() == chip.getXHome() && chip.getY() == chip.getY()) {
                count++;
            }
            potentialCur.add(chip);
        }
        if (count == 4 && !(cube == 6 || cube == 1)) {
            return false;
        }
        if (count == 4) {
            curT = potentialCur.get(randomPlayer(count));
        } else {
            for (Chip chip : potentialCur) {
                coordsT = getFuturePos(chip,paths.get(color), firstStep.get(color));
                if (checkCell(chip, coordsT) || checkFinish(chip)) {
                    removeList.add(chip);
                }
            }
            for (Chip chip : removeList) {
                potentialCur.remove(chip);
            }
            if (potentialCur.size() == 0) {
                return false;
            }
            for (Chip chip : potentialCur) {
                if (chip.getPos() == 0 || chip.getPos() < 2 && (cube == 6 || cube == 1)) {
                    curT = chip;
                    coordsT = getFuturePos(curT, paths.get(color), firstStep.get(color));
                    return true;
                }
            }
            for (Chip chip : potentialCur) {
                if (chip.getPos() > maxPos) {
                    maxPos = chip.getPos();
                    curT = chip;
                }
            }
        }
        coordsT = getFuturePos(curT, paths.get(color), firstStep.get(color));
        return true;
    }
    public void botMove() {
        coords = coordsT;
        cur = curT;
        PauseTransition pause1 = new PauseTransition(Duration.millis(500));
        pause1.setOnFinished(event -> {
            cur.setSelected(true);
            cur.getImage().setFocusTraversable(true);
            final ColorAdjust[] adjust = {new ColorAdjust()};
            adjust[0].setContrast(1);
            adjust[0].setBrightness(0.7);
            cur.getImage().setEffect(adjust[0]);
        });
        pause1.play();
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(event -> {
            cur.setMotion(true);
            move(paths.get(color), firstStep.get(color));
            cur.setX(coords[0]);
            cur.setY(coords[1]);
            final ColorAdjust[] adjust = {new ColorAdjust()};
            adjust[0].setContrast(0);
            adjust[0].setBrightness(0);
            cur.getImage().setEffect(adjust[0]);
            cur.setSelected(false);
            cur.getImage().setFocusTraversable(false);
        });
        pause.play();
    }
    public double[] getFuturePos (Chip cur, double[] arr, double[] firstStep) {
        if (cube == 0) {
            return new double[] {0 ,0};
        }
        if (cur.getPos() + cube * 2 > paths.get(color).length) {
            return new double[] {0 ,0};
        }
        int moves = getMoves(cur);
        double x = cur.getX();
        double y = cur.getY();
        for (int i = cur.getPos(); i < cur.getPos() + moves*2; i+=2) {
            if (i < 0) {
                if (cube == 1 || cube == 6) {
                    x = firstStep[0];
                    y = firstStep[1];
                } else  {
                    return new double[] {x,y};
                }
            }
            if (i >= 0) {
                x += arr[i];
                y += arr[i+1];
            }
        }
        return new double[] {x, y};
    }
    public boolean checkCell (Chip cur, double[] coords) {
        int moves = getMoves(cur);
        for (Color color : chips.keySet()) {
            for (Chip chip : chips.get(color)) {
                if (color == cur.getColor()) {
                    if (coords[0] == chip.getX() && coords[1] == chip.getY() && !chip.isFinished()) {
                        return true;
                    }
                } else {
                    if (coords[0] == chip.getX() && coords[1] == chip.getY() &&
                            (saveZone.contains(cur.getPos() + moves * 2) || chip.getPos() == 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean checkFinish (Chip cur) {
        if (cur.getPos() == paths.get(color).length) {
            return true;
        }
        return cur.getPos() + cube * 2 > paths.get(color).length;
    }

    public boolean checkPos (double[] coords) {
        return (cur.getImage().getLocalToParentTransform().getTx() == coords[0] &&
                cur.getImage().getLocalToParentTransform().getTy() == coords[1]);
    }
    public void reload (List<Chip> chips) {
        for (Chip chip : chips) {
            if (chip.getImage() == cur.getImage()) {
                chip = cur;
                chip.setMotion(false);
            }
        }
    }
    public boolean finish () {
        if (cur == null) {
            return false;
        }
        for (Chip chip : chips.get(color)) {
            if (!chip.isFinished()) {
                return false;
            }
        }
        return true;
    }
    public void clean () {
        for (Color color : colors) {
            cubeImages.get(color).setVisible(false);
            bars.get(color).setVisible(false);
        }
        winner.get(color).setVisible(true);
        check.stop();
        timeline.stop();
        bar.stop();
    }

    public boolean murder () {
        for (Color color : chips.keySet()) {
            if (color != cur.getColor()) {
                for (Chip chip : chips.get(color)) {
                    if (cur.getX() == chip.getX() && cur.getY() == chip.getY()) {
                        goHome(chip);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void goHome (Chip chip) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(chip.getImage());
        transition.setByX(chip.getXHome() - chip.getX());
        transition.setByY(chip.getYHome() - chip.getY());
        transition.play();
        chip.setX(chip.getXHome());
        chip.setY(chip.getYHome());
        chip.setPos(-2);
    }
    public boolean allAtHome() {
        if (cur == null) {
            return true;
        }
        for (Chip chip : chips.get(color)) {
            if (chip.getX() != chip.getXHome() && chip.getY() != chip.getYHome()) {
                return false;
            }
        }
        return true;
    }
    public boolean skip () {
        List<Chip> list = new ArrayList<>(chips.get(color));
        List<Chip> remove = new ArrayList<>();
        double[] coords;
        for (Chip chip : list) {
            coords = getFuturePos(chip,paths.get(color), firstStep.get(color));
            if (checkCell(chip, coords) || checkFinish(chip)) {
                remove.add(chip);
            }
        }
        for (Chip chip : remove) {
            list.remove(chip);
        }
        return list.size() == 0;
    }


    Timeline timeline = new Timeline();
    Timeline check = new Timeline();
    Timeline bar = new Timeline();
    Timeline bot = new Timeline();
    double[] coords = new double[] {0,0};
    int i;
    Color color;
    Map<Color,List<Chip>> chips = new HashMap<>();
    Map<Color,ImageView> cubeImages;
    Map<Color, Label> winner;
    Map<Color, ProgressBar> bars;
    double progress = 1;
    boolean murder;
    public Map<Color,ImageView> getCubeImages () {
        Map<Color,ImageView> cubeImages = new HashMap<>();
        cubeImages.put(Color.BLUE, bCube);
        cubeImages.put(Color.RED, rCube);
        cubeImages.put(Color.GREEN, gCube);
        cubeImages.put(Color.YELLOW, yCube);
        return cubeImages;
    }
    public Map<Color, Label> getWinner() {
        Map<Color,Label> winner = new HashMap<>();
        winner.put(Color.BLUE, bWin);
        winner.put(Color.RED, rWin);
        winner.put(Color.GREEN, gWin);
        winner.put(Color.YELLOW, yWin);
        return winner;
    }
    public Map<Color, ProgressBar> getProgressBars () {
        Map<Color, ProgressBar> barMap = new HashMap<>();
        barMap.put(Color.BLUE, bBar);
        barMap.put(Color.RED, rBar);
        barMap.put(Color.GREEN, gBar);
        barMap.put(Color.YELLOW, yBar);
        return barMap;
    }

    public void run () {
        i = randomPlayer(colors.size());
        color = colors.get(i);
        while (bases.get(color).isBot() && cBots < cPlayers) {
            i--;
            color = colors.get(i);
        }
        cubeImages = getCubeImages();
        winner = getWinner();
        bars = getProgressBars();
        cube = randomNumber();
        if (allAtHome()) {
            cube = cube <= 3 ? 1 : 6;
        }
        chips = getChips();
        bars.get(color).setVisible(true);
        cubeImages.get(color).setImage(images.get(cube-1));
        cubeImages.get(color).setVisible(true);
        if (bases.get(color).isBot() && botChoice()) {
            botMove();
        }
        check.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> {
            int rough = bases.get(color).getRough();
            int sixCount  = bases.get(color).getCountSix();
            if ((cube == 6 && sixCount == 2) || rough == 3) {
                bases.get(color).setCountSix(0);
                bases.get(color).setRough(0);
                timeline.playFrom(Duration.millis(9700));
            }
            if ((!(cube == 1 || cube == 6) && allAtHome()) || skip()) {
                bases.get(color).setCountSix(0);
                bases.get(color).setRough(0);
                timeline.playFrom(Duration.millis(9700));
            }
            if (cur != null && checkPos(coords) && cur.isMotion()) {
                int moves = getMoves(cur);
                cur.setPos(cur.getPos() + moves * 2);
                coords = new double[]{0,0};
                if (cube == 6) {
                    bases.get(color).setCountSix(bases.get(color).getCountSix()+1);
                    bases.get(color).setRough(bases.get(color).getRough()+1);
                    i--;
                    timeline.playFrom(Duration.millis(1));
                }
                if (murder()) {
                    murder = true;
                    rough = bases.get(color).getRough() + 1;
                    rough = cube == 6 ? rough-1 : rough;
                    bases.get(color).setRough(rough);
                    i = cube == 6 ? i : i - 1;
                    timeline.playFrom(Duration.millis(1));
                } else {
                    murder = false;
                }
                if (cube != 6 && !murder) {
                    bases.get(color).setCountSix(0);
                    bases.get(color).setRough(0);
                }
                if (cur.getPos() == paths.get(color).length) {
                    cur.setFinished(true);
                    rough = bases.get(color).getRough() + 1;
                    rough = cube == 6 ? rough-1 : rough;
                    bases.get(color).setRough(rough);
                    i = cube == 6 ? i : i - 1;
                    timeline.playFrom(Duration.millis(1));
                }
                reload(chips.get(color));
                if (finish()) {
                    clean();
                    return;
                }
                timeline.playFrom(Duration.millis(9700));
            }
        }));
        bot.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> {
            if (progress < 0.855 && progress > 0.825) {
                if (bases.get(color).isBot() && botChoice() && !cur.isSelected() && !cur.isMotion()) {
                    botMove();
                }
            }
        }));
        bar.getKeyFrames().add(new KeyFrame(Duration.millis(1), event -> {
            bars.get(color).setVisible(!bars.get(color).isIndeterminate());
            progress -= 0.0001;
            bars.get(color).setProgress(progress);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10), event -> {
            if (cur == null || !cur.isMotion()) {
                i++;
                if (i == colors.size()) {
                    i = 0;
                }
                bars.get(color).setVisible(false);
                cubeImages.get(color).setVisible(false);
                color = colors.get(i);
                cube = randomNumber();
                if (allAtHome()) {
                    cube = cube <= 3 ? 1 : 6;
                }
            } else if (cur != null && cur.isMotion()) {
                timeline.playFrom(Duration.seconds(3));
            }

            cubeImages.get(color).setImage(images.get(cube-1));
            cubeImages.get(color).setVisible(true);
            if (cur == null || !cur.isMotion()) {
                bars.get(color).setVisible(true);
                progress = 1;
            }
        }));
        bar.setCycleCount(Animation.INDEFINITE);
        bar.play();
        bot.setCycleCount(Animation.INDEFINITE);
        bot.play();
        check.setCycleCount(Animation.INDEFINITE);
        check.play();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void fillField (Color color) {
        bases.get(color).getChip1().setVisible(true);
        bases.get(color).getChip2().setVisible(true);
        bases.get(color).getChip3().setVisible(true);
        bases.get(color).getChip4().setVisible(true);
        bases.get(color).getLabel().setVisible(true);
    }
    public void defaultGame () {
        bases = getBases();
        for (int i = 0; i < 4; i++) {
            Color color = colors.get(i);
            fillField(color);
            if (i == 0) {
                bases.get(color).getLabel().setText("Player_" + (1));
            } else {
                bases.get(color).getLabel().setText("Bot_" + (i));
            }
        }
    }
    Map<Color, Base> bases;
    public Map<Color, Base> getBases () {
        Map<Color, Base> bases = new HashMap<>();
        bases.put(Color.BLUE, new Base(bLabel, bChip1, bChip2, bChip3, bChip4, false));
        bases.put(Color.RED, new Base(rLabel, rChip1, rChip2, rChip3, rChip4, true));
        bases.put(Color.GREEN, new Base(gLabel, gChip1, gChip2, gChip3, gChip4, true));
        bases.put(Color.YELLOW, new Base(yLabel, yChip1, yChip2, yChip3, yChip4, true));
        return bases;
    }
    public void init() {
        if (cPlayers == 0) {
            defaultGame();
            return;
        }
        bases = getBases();
        for (int i = 0; i < cPlayers - cBots; i++) {
            Color color = colors.get(i);
            fillField(color);
            bases.get(color).setBot(false);
            bases.get(color).getLabel().setText("Player_" + (i + 1));
        }
        int k = 0;
        for (int i = cPlayers - cBots; i < cPlayers; i++) {
            Color color = colors.get(i);
            fillField(color);
            bases.get(color).setBot(true);
            bases.get(color).getLabel().setText("Bot_" + (++k));
        }
        List<Color> temp = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            if (j < cPlayers) {
                temp.add(colors.get(j));
            }
        }
        colors = temp;
    }
    public void exit(ActionEvent event) throws IOException {
        clean();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void showParams(ActionEvent event) throws IOException {
        clean();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Params.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}