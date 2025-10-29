package com.dictionary.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ArabicKeyboard {
    private TextField targetTextField;
    
    // Arabic letters organized by rows
    private static final String[][] ARABIC_LETTERS = {
        {"ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج"},
        {"ش", "س", "ي", "ب", "ل", "ا", "ت", "ن", "م", "ك", "ط"},
        {"ئ", "ء", "ؤ", "ر", "ى", "ة", "و", "ز", "ظ", "-"}
    };

    public ArabicKeyboard(TextField textField) {
        this.targetTextField = textField;
    }

    public VBox createKeyboard() {
        VBox keyboardBox = new VBox(8);
        keyboardBox.setPadding(new Insets(10));
        keyboardBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

        for (String[] row : ARABIC_LETTERS) {
            // FIX 1: Changed new FlowPane(5) to new FlowPane()
            FlowPane rowPane = new FlowPane(); 
            rowPane.setAlignment(Pos.CENTER);
            rowPane.setHgap(3);
            rowPane.setVgap(3);

            for (String letter : row) {
                Button btn = createKeyButton(letter);
                rowPane.getChildren().add(btn);
            }
            keyboardBox.getChildren().add(rowPane);
        }

        // Add special buttons
        // FIX 2: Renamed 'myPane' to 'specialPane'
        FlowPane specialPane = new FlowPane(10, 10); // (hgap, vgap)
        specialPane.setAlignment(Pos.CENTER);
        specialPane.setHgap(3);

        Button btnSpace = createSpecialButton("Space", " ");
        Button btnBackspace = createSpecialButton("Backspace", "BACKSPACE");
        Button btnClear = createSpecialButton("Clear", "CLEAR");

        specialPane.getChildren().addAll(btnSpace, btnBackspace, btnClear);
        keyboardBox.getChildren().add(specialPane);

        return keyboardBox;
    }

    private Button createKeyButton(String letter) {
        Button btn = new Button(letter);
        btn.setFont(Font.font("Arial", 14));
        btn.setStyle("-fx-padding: 8px 12px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 3;");
        btn.setPrefWidth(40);
        btn.setPrefHeight(40);
        btn.setOnAction(e -> targetTextField.appendText(letter));
        return btn;
    }

    private Button createSpecialButton(String text, String action) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", 12));
        btn.setStyle("-fx-padding: 8px 12px; -fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 3;");
        btn.setPrefWidth(80);
        btn.setPrefHeight(40);

        btn.setOnAction(e -> {
            if ("BACKSPACE".equals(action)) {
                String text1 = targetTextField.getText();
                if (text1.length() > 0) {
                    targetTextField.setText(text1.substring(0, text1.length() - 1));
                }
            } else if ("CLEAR".equals(action)) {
                targetTextField.clear();
            } else {
                targetTextField.appendText(action);
            }
        });
        return btn;
    }
}
