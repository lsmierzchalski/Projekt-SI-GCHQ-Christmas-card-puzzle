package sample;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    public enum State{
        WHITE,BLACK,RED
    }

    public State state;

    boolean isClicked;

    Tile(boolean isClicked){

        setWidth(Main.TILE_SIZE);
        setHeight(Main.TILE_SIZE);

        setStroke(Color.BLACK);

        setFill(Color.WHITE);

        this.state = State.WHITE;
        this.isClicked = isClicked;

        if(isClicked){
            setOnMouseClicked(this::handleMouseClick);
        }
    }

    public void handleMouseClick(MouseEvent event){

        if(state == State.RED){
            state = State.WHITE;
        }
        else if (state == State.WHITE){
            state = State.BLACK;
        }
        else {
            state = State.RED;
        }

        changeColor();

    }

    public void changeColor(){
        if(state == State.BLACK){
            this.state = State.BLACK;
            setFill(Color.BLACK);
        }
        else if (state == State.WHITE){
            this.state = State.WHITE;
            setFill(Color.WHITE);
        }
        else {
            this.state = State.RED;
            setFill(Color.RED);
        }
    }

}
