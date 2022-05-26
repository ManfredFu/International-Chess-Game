package controller;

import model.ChessComponent;
import view.Chessboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> loadGameFromFile(File saveFile) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(saveFile.getPath()));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGame(String path) {
        try {
            FileWriter saveWriter = new FileWriter(path + ".txt", false);
            for (int i = 0; i < chessboard.saveGame().size(); i++) {
                saveWriter.write(chessboard.saveGame().get(i)+"\r\n");
            }
            saveWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialGame() {
        chessboard.initializeGame();
    }

}
