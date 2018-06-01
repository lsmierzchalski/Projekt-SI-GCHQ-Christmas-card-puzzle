package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Puzzle {

    public Tile[][] tile;
    public Text[] textHeightNr;
    public Label[] testWidthNr;

    Puzzle(int w, int h, VBox root){

        tile = new Tile[h][w];
        testWidthNr = new Label[w];
        textHeightNr = new Text[h];

        VBox vBox = new VBox();
        for(int i=0; i<h; i++){
            HBox hBox = new HBox();
            for(int j=0; j<w; j++){
                tile[i][j] = new Tile(false);
                hBox.getChildren().addAll(tile[i][j]);
            }
            textHeightNr[i] = new Text("                 ");
            textHeightNr[i].setFont(Font.font("consolas",14));
            hBox.getChildren().addAll(textHeightNr[i]);
            vBox.getChildren().addAll(hBox);
            vBox.setMargin(hBox,new Insets(0,0,0,0));
        }

        HBox hBox = new HBox();
        for(int i=0; i<w; i++){
            testWidthNr[i] = new Label("  \n  \n  \n  \n  \n  \n  \n  \n");
            //testWidthNr[i].resize(Main.TILE_SIZE,100);
            testWidthNr[i].setFont(Font.font("consolas",10.5));
            hBox.getChildren().addAll(testWidthNr[i]);
            hBox.setMargin(testWidthNr[i],new Insets(5,4,0,5));
        }

        root.getChildren().addAll(vBox);
        root.getChildren().addAll(hBox);

    }

}
