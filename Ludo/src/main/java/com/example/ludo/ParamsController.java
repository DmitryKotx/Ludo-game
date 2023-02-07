package com.example.ludo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ParamsController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    int cPlayers;
    int cBots;
    @FXML
    private ChoiceBox<String> choicePlayers;
    @FXML
    private ChoiceBox<String> choiceBots;
    @FXML
    Label player1;
    @FXML
    Label player2;
    @FXML
    Label player3;
    @FXML
    Label player4;
    @FXML
    Label color1;
    @FXML
    Label color2;
    @FXML
    Label color3;
    @FXML
    Label color4;
    @FXML
    Button change;
    @FXML
    Button button;


    private final String[] maxCountPlayers = new String[] {"2", "3", "4"};
    private final String[] strColors = new String[] {"Blue","Red","Green", "Yellow"};
    Color[] colors = new Color[] {Color.BLUE, Color.RED, Color.GREEN,  Color.YELLOW};
    private List<Label> players;
    private List<Label> pColors;

    public void exit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        root = loader.load();
        GameController controller = loader.getController();
        controller.cPlayers = cPlayers;
        controller.cBots = cBots;
        controller.colors = Arrays.asList(colors);
        controller.init();
        controller.run();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choicePlayers.setStyle("-fx-font: 20px \"Arial\";");
        choiceBots.setStyle("-fx-font: 20px \"Arial\";");
        choicePlayers.getItems().addAll(maxCountPlayers);
        players = Arrays.asList(player1, player2, player3, player4);
        pColors = Arrays.asList(color1, color2, color3, color4);
        choicePlayers.setOnAction(this :: getCountBots);
    }
    private void getCountBots (ActionEvent event) {
        int n = Integer.parseInt(choicePlayers.getValue());
        String[] countBots = new String[n+1];
        for (int i = 0; i <= n; i++) {
            countBots[i] = String.valueOf(i);
        }
        choiceBots.getItems().removeAll("0","1","2","3", "4");
        choiceBots.getItems().addAll(countBots);
        delete();
        choiceBots.setOnAction(this::getColors);
    }
    private void getColors (ActionEvent event) {
        change.setVisible(true);
        cPlayers = Integer.parseInt(choicePlayers.getValue());
        cBots = choiceBots.getValue() == null ? 0 : Integer.parseInt(choiceBots.getValue());
        for (int i = 0; i < cPlayers-cBots; i++) {
            players.get(i).setText("Player_" + (i+1) + ":");
            players.get(i).setVisible(true);
            pColors.get(i).setText(strColors[i]);
            pColors.get(i).setVisible(true);
        }
        int j = 0;
        for (int i = cPlayers-cBots; i < cPlayers; i++) {
            players.get(i).setText("Bot_" + (++j) + ":");
            players.get(i).setVisible(true);
            pColors.get(i).setText(strColors[i]);
            pColors.get(i).setVisible(true);
        }
        for (int i = cPlayers; i < 4; i++) {
            players.get(i).setVisible(false);
            pColors.get(i).setVisible(false);
        }
    }
    public void change() {
        int p = Integer.parseInt(choicePlayers.getValue());
        String first = pColors.get(0).getText();
        for (int i = 0; i < p-1; i++) {
            pColors.get(i).setText(pColors.get(i+1).getText());
        }
        pColors.get(p-1).setText(first);
        changeColors();
    }
    public void changeColors() {
        int p = Integer.parseInt(choicePlayers.getValue());
        Color first = colors[0];
        for (int i = 0; i < p-1; i++) {
            colors[i] = colors[i+1];
        }
        colors[p-1] = first;
    }
    private void delete() {
        for (int i = 0; i < 4; i++) {
            players.get(i).setVisible(false);
            pColors.get(i).setVisible(false);
        }
    }
}
