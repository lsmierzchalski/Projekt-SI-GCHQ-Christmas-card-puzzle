package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WindowInputSize {

    private Text textQuestion;
    private Text textWidth;
    private Text textHeight;
    private TextField textFieldWidth;
    private TextField textFieldHeight;
    private Button buttonNext;
    private Stage primaryStage;

    private final static int WIDTH = 470;
    private final static int HEIGHT = 300;

    WindowInputSize(Stage stage){

        textQuestion = new Text("Podaj wymiary łamigłówki z zakresu od 1 do 25:");
        textHeight = new Text("Wysokość:");
        textFieldHeight = new TextField("25");
        textWidth = new Text("Szerokość:");
        textFieldWidth = new TextField("25");
        buttonNext = new javafx.scene.control.Button("Dalej");

        Pane root = new Pane();
        root.setPrefSize(WIDTH,HEIGHT);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(textQuestion);
        VBox.setMargin(textQuestion,new Insets(20,0,0,20));
        vBox.getChildren().addAll(textHeight);
        VBox.setMargin(textHeight,new Insets(20,0,0,20));
        vBox.getChildren().addAll(textFieldHeight);
        VBox.setMargin(textFieldHeight,new Insets(20,0,0,20));
        vBox.getChildren().addAll(textWidth);
        VBox.setMargin(textWidth,new Insets(20,0,0,20));
        vBox.getChildren().addAll(textFieldWidth);
        VBox.setMargin(textFieldWidth,new Insets(20,0,0,20));
        vBox.getChildren().addAll(buttonNext);
        VBox.setMargin(buttonNext,new Insets(20,0,0,20));

        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root);

        primaryStage = stage;

        primaryStage.setTitle("PROJEKT SI - Łamigłówka GCHQ Christmas card puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonNext.setOnAction(value -> {
            if(Main.tryParseInt(textFieldHeight.getText())&&Main.tryParseInt(textFieldWidth.getText())){
                int widthBoard = Integer.parseInt(textFieldWidth.getText());
                int heightBoard = Integer.parseInt(textFieldHeight.getText());
                if(widthBoard>0&&widthBoard<26&&heightBoard>0&&heightBoard<26){
                    primaryStage.close();

                    WindowMain windowMain = new WindowMain(stage, widthBoard, heightBoard);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BŁĄD");
                    alert.setHeaderText("Podane wartości nie należa do zadanego przedziąłu.");
                    alert.setContentText("Podaj poprawne wartości liczbowe, np: 25.");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            System.out.println("OK");
                        }
                    });
                }
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BŁĄD");
                alert.setHeaderText("Podane wartości nie są typu liczbami.");
                alert.setContentText("Podaj poprawne wartości liczbowe, np: 25.");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("OK");
                    }
                });
            }
        });
    }

}
