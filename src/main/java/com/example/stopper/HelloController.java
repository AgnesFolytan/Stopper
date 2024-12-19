package com.example.stopper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController {
    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label timesSavedLabel;
    @FXML
    private Button startStopButton;
    @FXML
    private Button resetSaveButton;
    private List<String> savedTimes = new ArrayList<>();
    private Timer myTimer;
    private long startTime;

    @FXML
    protected void startStopCilck() {
        if (Objects.equals(startStopButton.getText(), "Start")) {
            startStopButton.setText("Stop");
            resetSaveButton.setText("Save");
            timer();
        }
        else {
            startStopButton.setText("Start");
            resetSaveButton.setText("Reset");
            timer();
        }
    }

    @FXML
    protected void resetSaveCilck() {
        if (Objects.equals(resetSaveButton.getText(), "Save")) {
            savedTimes.add(currentTimeLabel.getText());
        }
        else {
            savedTimes.clear();
        }
        String saved = savedTimes.stream().map(e -> e.split("\n")).flatMap(Arrays::stream).collect(Collectors.joining("\n"));
        timesSavedLabel.setText(saved);
    }

    private void timer() {
        if (Objects.equals(startStopButton.getText(), "Stop")) {
            String timeText = currentTimeLabel.getText();
            long accumulatedTime = parseTimeToMillis(timeText);
            startTime = System.currentTimeMillis() - accumulatedTime;
            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(HelloController.this::updateCurrentTimeLabel);
                }
            }, 0, 1);
        }
        else {
            if (myTimer != null) {
                myTimer.cancel();
            }
        }
    }

    private long parseTimeToMillis(String timeText) {
        String[] timeParts = timeText.split(":");
        if (timeParts.length == 3) {
            String[] secondsParts = timeParts[2].split("\\.");
            long hours = Long.parseLong(timeParts[0]);
            long minutes = Long.parseLong(timeParts[1]);
            long seconds = Long.parseLong(secondsParts[0]);
            long milliseconds = (secondsParts.length > 1) ? Long.parseLong(secondsParts[1]) : 0;

            return (hours * 3600000) + (minutes * 60000) + (seconds * 1000) + milliseconds;
        } else {
            return 0;
        }
    }

    private void updateCurrentTimeLabel() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long hours = elapsedMillis / 3600000;
        long minutes = (elapsedMillis % 3600000) / 60000;
        long seconds = (elapsedMillis % 60000) / 1000;
        long milliseconds = elapsedMillis % 1000;
        currentTimeLabel.setText(String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds));
    }

















}