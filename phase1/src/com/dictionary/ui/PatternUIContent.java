package com.dictionary.ui;

import com.dictionary.model.PatternDTO;
import com.dictionary.service.PatternService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class PatternUIContent {

    private TextField txtPatternForm;
    private TableView<PatternDTO> table;
    private ObservableList<PatternDTO> data;
    private PatternService patternService;
    private Label lblId;
    private ArabicKeyboard arabicKeyboard;

    public PatternUIContent() {
        patternService = new PatternService();
    }

    public ScrollPane getContent() {
        Label lblTitle = new Label("Arabic Patterns Dictionary");
        lblTitle.setFont(Font.font("Segoe UI", 28));
        lblTitle.setTextFill(Color.web("#8E44AD"));

        lblId = new Label("Auto-generated");
        txtPatternForm = new TextField();
        txtPatternForm.setPromptText("Enter Pattern Form (e.g., فَعَلَ)");
        txtPatternForm.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));
        formPane.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-padding: 15;");
        formPane.add(new Label("ID:"), 0, 0);
        formPane.add(lblId, 1, 0);
        formPane.add(new Label("Pattern Form:"), 0, 1);
        formPane.add(txtPatternForm, 1, 1);

        arabicKeyboard = new ArabicKeyboard(txtPatternForm);
        VBox keyboardBox = arabicKeyboard.createKeyboard();

        Button btnAdd = createRoundedButton("Add", "#9B59B6");
        Button btnUpdate = createRoundedButton("Update", "#F39C12");
        Button btnDelete = createRoundedButton("Delete", "#E74C3C");
        Button btnClear = createRoundedButton("Clear", "#95A5A6");

        HBox buttonBox = new HBox(15, btnAdd, btnUpdate, btnDelete, btnClear);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        table = new TableView<>();
        TableColumn<PatternDTO, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);

        TableColumn<PatternDTO, String> colPatternForm = new TableColumn<>("Pattern Form");
        colPatternForm.setCellValueFactory(new PropertyValueFactory<>("patternForm"));
        colPatternForm.setPrefWidth(300);

        table.getColumns().addAll(colId, colPatternForm);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No data found"));
        table.setPrefHeight(300);

        // Table selection listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblId.setText(String.valueOf(newSel.getId()));
                txtPatternForm.setText(newSel.getPatternForm());
            }
        });

        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F5F7FA;");
        mainContent.getChildren().addAll(lblTitle, formPane, keyboardBox, buttonBox, new Separator(), table);
        mainContent.setVgrow(table, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);

        // Button Actions
        btnAdd.setOnAction(e -> addPattern());
        btnUpdate.setOnAction(e -> updatePattern());
        btnDelete.setOnAction(e -> deletePattern());
        btnClear.setOnAction(e -> clearFields());

        loadData();

        return scrollPane;
    }

    private Button createRoundedButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", 14));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: " + colorHex + "; -fx-background-radius: 20;");
        button.setPrefWidth(110);
        button.setPrefHeight(40);
        return button;
    }

    private void loadData() {
        try {
            List<PatternDTO> patterns = patternService.getAllPatterns();
            data = FXCollections.observableArrayList(patterns);
            table.setItems(data);
        } catch (Exception e) {
            System.err.println("Error loading patterns: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        lblId.setText("Auto-generated");
        txtPatternForm.clear();
        table.getSelectionModel().clearSelection();
    }

    private void addPattern() {
        String patternForm = txtPatternForm.getText().trim();
        if (patternForm.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter pattern form.");
            return;
        }
        List<PatternDTO> existingPatterns = patternService.getAllPatterns();
        boolean exists = existingPatterns.stream().anyMatch(p -> p.getPatternForm().equalsIgnoreCase(patternForm));
        if (exists) {
            showAlert(Alert.AlertType.WARNING, "Pattern already exists in database!");
            return;
        }
        patternService.addPattern(new PatternDTO(patternForm));
        showAlert(Alert.AlertType.INFORMATION, "Pattern added successfully!");
        loadData();
        clearFields();
    }

    private void updatePattern() {
        String idText = lblId.getText();
        String patternForm = txtPatternForm.getText().trim();
        if (idText.equals("Auto-generated")) {
            showAlert(Alert.AlertType.ERROR, "Please select a pattern to update.");
            return;
        }
        if (patternForm.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter new pattern form.");
            return;
        }
        try {
            int id = Integer.parseInt(idText);
            List<PatternDTO> existingPatterns = patternService.getAllPatterns();
            boolean exists = existingPatterns.stream().anyMatch(p -> p.getPatternForm().equalsIgnoreCase(patternForm) && p.getId() != id);
            if (exists) {
                showAlert(Alert.AlertType.WARNING, "Pattern already exists in database!");
                return;
            }
            patternService.updatePattern(id, patternForm);
            showAlert(Alert.AlertType.INFORMATION, "Pattern updated successfully!");
            loadData();
            clearFields();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error updating pattern: " + ex.getMessage());
        }
    }

    private void deletePattern() {
        String idText = lblId.getText();
        if (idText.equals("Auto-generated")) {
            showAlert(Alert.AlertType.ERROR, "Please select a pattern to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this pattern?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                int id = Integer.parseInt(idText);
                patternService.deletePattern(id);
                showAlert(Alert.AlertType.INFORMATION, "Pattern deleted successfully!");
                loadData();
                clearFields();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error deleting pattern: " + ex.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
