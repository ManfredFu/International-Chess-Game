package controller;

import ExceptionHandle.IncorrectFileTypeException;
import view.Chessboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;
    int stepCounter = 0;

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

    public void loadGameFromFile(File saveFile) {
        try {
            String path = saveFile.getPath();
            StringBuilder fileType = new StringBuilder();
            for (int i = path.length() - 4; i < path.length(); i++) {
                fileType.append(path.charAt(i));
            }
            if (!fileType.toString().equals(".txt")) {
                throw new IncorrectFileTypeException("The save file type is not .txt, Please Check");
            }
            List<String> chessData = Files.readAllLines(Path.of(saveFile.getPath()));
            chessboard.loadGame(chessData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectFileTypeException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveGame(String path) {
        try {
            FileWriter saveWriter = new FileWriter(path + ".txt", false);
            for (int i = 0; i < chessboard.saveGame().size(); i++) {
                saveWriter.write(chessboard.saveGame().get(i) + "\r\n");
            }
            saveWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void undo(int stepCounter) {
        try {
            List<String> chessData = Files.readAllLines(Path.of("ChessCache/movement" + (stepCounter - 2) + ".txt"));
            chessboard.loadGame(chessData);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void replay() {
        ActionListener fileLoader = e -> {
            List<String> chessData;
            try {
                chessData = Files.readAllLines(Path.of("ChessCache/movement" + stepCounter + ".txt"));
                chessboard.loadGame(chessData);
                stepCounter++;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Replay End!", "INFORM", JOptionPane.INFORMATION_MESSAGE);
                stepCounter = 0;
                return;
            }
        };
        Timer timer = new Timer(1000, fileLoader);
        timer.start();
    }

    public void initialGame() {
        chessboard.initializeGame();
    }
}
