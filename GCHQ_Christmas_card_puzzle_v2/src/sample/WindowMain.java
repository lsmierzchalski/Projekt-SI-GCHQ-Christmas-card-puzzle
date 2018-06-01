package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;

public class WindowMain {

    private Stage primaryStage;
    private Text text;
    private Text textWidth;
    private Button buttonGeneratePuzzle;
    private Button buttonSolvePuzzle;
    private Button buttonSavePuzzle;
    private Button buttonLoadPuzzle;
    private Button buttonRefreshPuzzle;
    private Button buttonUserManual;
    private Button buttonPrevious;
    private TextArea textAreaPuzzle;

    private final static int WIDTH = 1300;
    private final static int HEIGHT = 1000;

    private static int WIDTH_BOARD;
    private static int HEIGHT_BOARD;

    Board b;
    Puzzle p;

    WindowMain(Stage stage, int widthBoard, int heightBoard){

        HEIGHT_BOARD = heightBoard;
        WIDTH_BOARD = widthBoard;

        Pane root = new Pane();
        //root.setPrefSize(WIDTH,HEIGHT);
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        //pierwsza kolumna tablca i okno
        VBox vBox1 = new VBox();

        textWidth = new Text("Tablica:");
        vBox1.getChildren().addAll(textWidth);

        b = new Board(widthBoard,heightBoard,vBox1);

        vBox1.setMargin(textWidth,new Insets(20,0,0,20));

        text = new Text("Wymiary łamigłówki: "+widthBoard + " x "+heightBoard);
        vBox1.getChildren().addAll(text);
        vBox1.setMargin(text,new Insets(10,0,0,20));

        HBox buttonBox = new HBox();

        buttonGeneratePuzzle = new Button("Generuj");
        buttonBox.getChildren().addAll(buttonGeneratePuzzle);
        buttonBox.setMargin(buttonGeneratePuzzle,new Insets(20,5,0,20));

        buttonSolvePuzzle = new Button("Rozwiaż");
        buttonBox.getChildren().addAll(buttonSolvePuzzle);
        buttonBox.setMargin(buttonSolvePuzzle,new Insets(20,5,0,10));

        buttonSavePuzzle = new Button("Zapisz");
        buttonBox.getChildren().addAll(buttonSavePuzzle);
        buttonBox.setMargin(buttonSavePuzzle,new Insets(20,5,0,10));

        buttonLoadPuzzle = new Button("Wczytaj");
        buttonBox.getChildren().addAll(buttonLoadPuzzle);
        buttonBox.setMargin(buttonLoadPuzzle,new Insets(20,5,0,10));

        buttonRefreshPuzzle= new Button("Odświerz");
        buttonBox.getChildren().addAll(buttonRefreshPuzzle);
        buttonBox.setMargin(buttonRefreshPuzzle,new Insets(20,5,0,10));

        buttonUserManual= new Button("Czyść");
        buttonBox.getChildren().addAll(buttonUserManual);
        buttonBox.setMargin(buttonUserManual,new Insets(20,5,0,10));

        buttonPrevious= new Button("Wstecz");
        buttonBox.getChildren().addAll(buttonPrevious);
        buttonBox.setMargin(buttonPrevious,new Insets(20,10,0,10));


        vBox1.getChildren().addAll(buttonBox);

        hBox.getChildren().addAll(vBox1);
        //druga kolumna łamigłówki i okno

        VBox vBox3 = new VBox();

        textWidth = new Text("Łamigłówka:");
        vBox3.getChildren().addAll(textWidth);

        p = new Puzzle(widthBoard,heightBoard,vBox3);

        hBox.getChildren().addAll(vBox3);
        hBox.setMargin(vBox3,new Insets(20,20,0,20));

        vBox.getChildren().addAll(hBox);

        textAreaPuzzle = new TextArea();
        textAreaPuzzle.setFont(Font.font("consolas",15));


        vBox.getChildren().addAll(textAreaPuzzle);
        vBox.setMargin(textAreaPuzzle,new Insets(0,20,20,20));
        textAreaPuzzle.resize(250,250);

        ///
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root);

        primaryStage = stage;

        primaryStage.setTitle("PROJEKT SI - Łamigłówka GCHQ Christmas card puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();

        //generowanie łamigłówki
        buttonGeneratePuzzle.setOnAction(value ->  {

            ArrayList<Integer> tmp;
            ArrayList<ArrayList<Integer>> columnNr = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> rowsNr = new ArrayList<ArrayList<Integer>>();

            for(int i=0; i<heightBoard; i++){
                tmp = new ArrayList<Integer>();
                boolean count = false;
                for(int j=0; j<widthBoard; j++){
                    if(b.tile[i][j].state == Tile.State.RED){
                        p.tile[i][j].state = Tile.State.BLACK;
                    } else {
                        p.tile[i][j].state = Tile.State.WHITE;
                    }
                    p.tile[i][j].changeColor();

                    if(b.tile[i][j].state == Tile.State.RED || b.tile[i][j].state == Tile.State.BLACK){
                        if(count == false){
                            tmp.add(1);
                            count = true;
                        }
                        else{
                            tmp.set(tmp.size()-1,tmp.get(tmp.size()-1)+1);
                        }
                    }
                    else {
                        count = false;
                    }
                }
                rowsNr.add(tmp);
            }

            for(int i=0; i<widthBoard; i++){
                tmp = new ArrayList<Integer>();
                boolean count = false;
                for(int j=0; j<heightBoard; j++){
                    if(b.tile[j][i].state == Tile.State.RED || b.tile[j][i].state == Tile.State.BLACK){
                        if(count == false){
                            tmp.add(1);
                            count = true;
                        }
                        else{
                            tmp.set(tmp.size()-1,tmp.get(tmp.size()-1)+1);
                        }
                    }
                    else {
                        count = false;
                    }
                }
                columnNr.add(tmp);
                //System.out.println(tmp);
            }

            //generowanie łamigłówki w formie tekstu wejsciowego
            textAreaPuzzle.setText("");
            for(int i=0; i<widthBoard; i++){
                textAreaPuzzle.appendText(columnNr.get(i).toString()+",");
            }
            textAreaPuzzle.appendText("\n");

            for(int i=0; i<heightBoard; i++){
                String stmp = "";
                textAreaPuzzle.appendText(rowsNr.get(i).toString()+",\t[");

                for(int j=0;j<widthBoard;j++){
                    if(p.tile[i][j].state == Tile.State.BLACK){
                        textAreaPuzzle.appendText("1");
                    } else {
                        textAreaPuzzle.appendText("_");
                    }

                    if(j!=widthBoard-1) textAreaPuzzle.appendText(",");
                }
                if(i!=heightBoard-1) textAreaPuzzle.appendText("],\n");
            }
            textAreaPuzzle.appendText("].");

            DrawPuzzleFromText(textAreaPuzzle.getText());
        });

        //rozwiazywanie łamigłóki
        buttonSolvePuzzle.setOnAction(value -> {

            PuzzleData pd = ParsePuzzleToData(textAreaPuzzle.getText());
            SolverPuzzle sp = new SolverPuzzle();
            int[][] result = sp.solvePuzzle(widthBoard,heightBoard,pd.knownPosition,pd.columnConstraints,pd.rowConstraints);
            clr();
            if(result!=null){
                for(int i=0; i<heightBoard; i++){
                    for(int j=0; j<widthBoard; j++){
                        if(result[j][i]==1){
                            b.tile[i][j].state = Tile.State.BLACK;
                            b.tile[i][j].changeColor();
                        }
                    }
                }
                for(int i=0; i<pd.knownPosition.size(); i++){
                    b.tile[pd.knownPosition.get(i).get(1)][pd.knownPosition.get(i).get(0)].state = Tile.State.RED;
                    b.tile[pd.knownPosition.get(i).get(1)][pd.knownPosition.get(i).get(0)].changeColor();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BŁĄD");
                alert.setHeaderText("Nieudało się rozwiązać łamigłówki.");
                alert.setContentText("Niepoprawne dane lub zbyt mało znanych pukntów.");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("OK");
                    }
                });
            }
        });

        //zapisz tekst łąmigłówki do pliku
        buttonSavePuzzle.setOnAction(value ->  {

            if(textAreaPuzzle.getText().length()!=0){
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if(file != null){
                    SaveFile(textAreaPuzzle.getText(), file);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BŁĄD");
                alert.setHeaderText("Brak tekstu do zapisania.");
                alert.setContentText("Wygeneruj łamigłówkę.");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("OK");
                    }
                });
            }
        });

        //wczytaj tekst łąmigłówki z pliku txt
        buttonLoadPuzzle.setOnAction(value ->  {
            textAreaPuzzle.setText("");

            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            if(f!=null){
                String filename = f.getAbsolutePath();
                String text = "";
                boolean first = true;
                try{
                    FileReader reader = new FileReader(filename);
                    BufferedReader br = new BufferedReader(reader);
                    do{
                        text = br.readLine();
                        if(text != null){
                            if(first){
                                textAreaPuzzle.appendText(text);
                                first = false;
                            }else {
                                textAreaPuzzle.appendText("\n"+text);
                            }
                        }
                    }while (text != null);
                    br.close();
                }
                catch (Exception e){
                    AlertBadFile();
                }
                DrawPuzzleFromText(textAreaPuzzle.getText());
            }else{
                //AlertBadFile();
            }
        });

        //odświerza łamigłówkę po zmianach w tekście
        buttonRefreshPuzzle.setOnAction(value ->  {

            DrawPuzzleFromText(textAreaPuzzle.getText());

        });

        //otwieranie okna z instrukcją
        //a jednak nie
        buttonUserManual.setOnAction(value ->  {

            clr();

        });

        buttonPrevious.setOnAction(value ->  {

            primaryStage.close();
            Main.startApp(primaryStage);

        });

    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            //Logger.getLogger(JavaFXSaveText.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //parsowanie tekstu łamigłówki do struktur potrzebnych solverowi
    //
    //tablica zananych pozycji int[][] i koordynaty znanych puktów
    //list kolumn z listami int[][]
    //list wirszy z listami int[][]

    private PuzzleData ParsePuzzleToData(String text){
        try{
            ArrayList<ArrayList<Integer>> columnArray = new ArrayList<ArrayList<Integer>>();

            //lista kolumn
            int n = 0;
            for(int i=0; i<WIDTH_BOARD; i++){
                String tmp = "";
                for(int j=n; text.charAt(j) != ']'; j++, n++){
                    tmp+=text.charAt(j);
                }
                //System.out.println(tmp);
                String[] items = tmp.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

                columnArray.add(new ArrayList<Integer>());

                for (int j = 0; j < items.length; j++) {
                    try {
                        columnArray.get(i).add(Integer.parseInt(items[j]));
                    } catch (NumberFormatException nfe) {
                        //NOTE: write something here if you need to recover from formatting errors
                    };
                }
                n+=2;
            }

            n++;
            System.out.println(columnArray);

            //lista wierszy
            //tablica znanych
            ArrayList<ArrayList<Integer>> rowsArray = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> knowPositionAL = new ArrayList<ArrayList<Integer>>();

            for(int i=0; i<HEIGHT_BOARD; i++){
                String tmp = "";
                for(int j=n; text.charAt(j) != ']'; j++, n++){
                    tmp+=text.charAt(j);
                }
                //System.out.println("<"+tmp+">");
                String[] items = tmp.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

                rowsArray.add(new ArrayList<Integer>());

                for (int j = 0; j < items.length; j++) {
                    try {
                        rowsArray.get(i).add(Integer.parseInt(items[j]));
                    } catch (NumberFormatException nfe) {
                        //NOTE: write something here if you need to recover from formatting errors
                    };
                }

                n+=4;

                for(int j=0; j<WIDTH_BOARD; j++, n+=2){

                    if(text.charAt(n) == '1'){
                        ArrayList<Integer> pare = new ArrayList<>();
                        pare.add(j);
                        pare.add(i);
                        knowPositionAL.add(pare);
                    }

                    //System.out.println(text.charAt(n));
                }
                n+=3; // , \t [ ] , \n
            }
            n-=2;

            System.out.println(rowsArray);

            System.out.println(knowPositionAL+" "+n+" "+text.length());

            if(n!=text.length()){
                AlertBadData();
            }else{
                System.out.println("GOOD");
                return new PuzzleData(rowsArray,columnArray,knowPositionAL);
            }

        } catch (Exception e){
            AlertBadData();
        }

        return null;
    }

    private void DrawPuzzleFromText(String text){
        int SPECIAL_NR = 9;
        PuzzleData pD;
        pD = ParsePuzzleToData(text);
        if(pD != null) {
            for(int i=0; i<HEIGHT_BOARD; i++){
                int xd = 0;
                String stmp = "";
                for(int j=0;j<pD.rowConstraints.get(i).size();j++){
                    stmp+=" "+pD.rowConstraints.get(i).get(j).toString();
                    xd++;
                }
                for(int k=xd; k<SPECIAL_NR; k++){
                    if(k<SPECIAL_NR-1)stmp+="  ";
                    else if(k<SPECIAL_NR-1)stmp+=" ";
                }
                p.textHeightNr[i].setText(stmp);
            }

            for(int i=0; i<WIDTH_BOARD; i++){
                int xd = 0;
                String stmp = "";
                for(int j=0;j<pD.columnConstraints.get(i).size();j++){
                    stmp+=pD.columnConstraints.get(i).get(j).toString();
                    if(j!=pD.columnConstraints.get(i).size()-1)stmp+="\n";
                    xd++;
                }
                for(int k=xd; k<SPECIAL_NR; k++){
                    if(k<SPECIAL_NR-2)stmp+="\n ";
                    //else if(k<SPECIAL_NR-1)stmp+="  ";
                }
                if(pD.columnConstraints.get(i).size()==0) stmp+="  ";
                else if(pD.columnConstraints.get(i).get(pD.columnConstraints.get(i).size()-1)<10)stmp+=" ";
                p.testWidthNr[i].setText(stmp);
            }

            for(int i=0; i<pD.knownPosition.size(); i++){
                p.tile[pD.knownPosition.get(i).get(1)][pD.knownPosition.get(i).get(0)].state = Tile.State.BLACK;
                p.tile[pD.knownPosition.get(i).get(1)][pD.knownPosition.get(i).get(0)].changeColor();
            }

        }else{
            AlertBadData();
        }

    }

    private void AlertBadData(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BŁĄD");
        alert.setHeaderText("Podany tekst łamigłówki jest niepoprwany.");
        alert.setContentText("Sparwdź czy tekst nie jest pusty lub rozmiar łamigłówki jest poprawny lub tekst nie zawiera innych błędów.");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("OK");
            }
        });
    }

    private void AlertBadFile(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BŁĄD");
        alert.setHeaderText("Nieudało się wczytać pliku.");
        alert.setContentText("Sprawdź czy plik nie zawiera błędów lub podana ścieżka jest niepoprawna.");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("OK");
            }
        });
    }

    private void clr(){
        for(int i=0; i<HEIGHT_BOARD; i++){
            for(int j=0; j<WIDTH_BOARD; j++){
                b.tile[i][j].state = Tile.State.WHITE;
                b.tile[i][j].changeColor();
            }
        }
    }

}