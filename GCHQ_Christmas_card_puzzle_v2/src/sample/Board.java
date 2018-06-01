package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Board {

    public Tile[][] tile;

    Board(int w, int h, VBox root){

        tile = new Tile[h][w];

        VBox vBox = new VBox();
        for(int i=0; i<h; i++){
            HBox hBox = new HBox();
            for(int j=0; j<w; j++){
                tile[i][j] = new Tile(true);
                hBox.getChildren().addAll(tile[i][j]);
            }
            vBox.getChildren().addAll(hBox);
            vBox.setMargin(hBox,new Insets(0,0,0,20));
        }
        root.getChildren().addAll(vBox);

    }



}
