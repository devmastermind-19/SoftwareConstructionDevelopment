package com.dictionary.ui;

import com.dictionary.model.RootDTO;
import com.dictionary.service.RootService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class RootUI extends Application {

    private TextField txtRootLetters;
    private TableView<RootDTO> table;
    private ObservableList<RootDTO> data;
    private RootService rootService;
    private Label lblId;
    private ArabicKeyboard arabicKeyboard;

    @Override
    public void start(Stage primaryStage) {
        rootService = new RootService();
        initializeUI(primaryStage);
        loadData();
    }

    private void initializeUI(Stage stage) {
        stage.setTitle("Arabic Roots Dictionary");

        Label lblTitle = new Label("Arabic Roots Dictionary");
        lblTitle.setFont(Font.font("Segoe UI", 28));
        lblTitle.setTextFill(Color.web("#1E3C72"));

        lblId = new Label("Auto-generated");
        txtRootLetters = new TextField();
        txtRootLetters.setPromptText("Enter Root Letters (ك ت ب)");
        txtRootLetters.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));
        formPane.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-padding: 15;");
        formPane.add(new Label("ID:"), 0, 0);
        formPane.add(lblId, 1, 0);
        formPane.add(new Label("Root Letters:"), 0, 1);
        formPane.add(txtRootLetters, 1, 1);

        arabicKeyboard = new ArabicKeyboard(txtRootLetters);
        VBox keyboardBox = arabicKeyboard.createKeyboard();

        Button btnAdd = createRoundedButton("Add", "#48C9B0");
        Button btnUpdate = createRoundedButton("Update", "#FFC107");
        Button btnDelete = createRoundedButton("Delete", "#E74C3C");
        Button btnClear = createRoundedButton("Clear", "#95A5A6");

        HBox buttonBox = new HBox(15, btnAdd, btnUpdate, btnDelete, btnClear);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        table = new TableView<>();
        TableColumn<RootDTO, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);

        TableColumn<RootDTO, String> colRootLetters = new TableColumn<>("Root Letters");
        colRootLetters.setCellValueFactory(new PropertyValueFactory<>("rootLetters"));
        colRootLetters.setPrefWidth(200);

        table.getColumns().addAll(colId, colRootLetters);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No data found"));
        table.setPrefHeight(300);

        // Table selection listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblId.setText(String.valueOf(newSel.getId()));
                txtRootLetters.setText(newSel.getRootLetters());
            }
        });

        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F5F7FA;");
        mainContent.getChildren().addAll(lblTitle, formPane, keyboardBox, buttonBox, new Separator(), table);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 900, 800);
        stage.setScene(scene);
        stage.show();

        // Button Actions
        btnAdd.setOnAction(e -> addRoot());
        btnUpdate.setOnAction(e -> updateRoot());
        btnDelete.setOnAction(e -> deleteRoot());
        btnClear.setOnAction(e -> clearFields());
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
        List<RootDTO> roots = rootService.getAllRoots();
        data = FXCollections.observableArrayList(roots);
        table.setItems(data);
    }

    private void clearFields() {
        lblId.setText("Auto-generated");
        txtRootLetters.clear();
        table.getSelectionModel().clearSelection();
    }

    private void addRoot() {
        String rootLetters = txtRootLetters.getText().trim();
        if (rootLetters.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter root letters.");
            return;
        }
        List<RootDTO> existingRoots = rootService.getAllRoots();
        boolean exists = existingRoots.stream().anyMatch(r -> r.getRootLetters().equalsIgnoreCase(rootLetters));
        if (exists) {
            showAlert(Alert.AlertType.WARNING, "Root already exists in database!");
            return;
        }
        rootService.addRoot(rootLetters);
        showAlert(Alert.AlertType.INFORMATION, "Root added successfully!");
        loadData();
        clearFields();
    }

    private void updateRoot() {
        String idText = lblId.getText();
        String rootLetters = txtRootLetters.getText().trim();
        if (idText.equals("Auto-generated")) {
            showAlert(Alert.AlertType.ERROR, "Please select a root to update.");
            return;
        }
        if (rootLetters.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter new root letters.");
            return;
        }
        try {
            int id = Integer.parseInt(idText);
            List<RootDTO> existingRoots = rootService.getAllRoots();
            boolean exists = existingRoots.stream().anyMatch(r -> r.getRootLetters().equalsIgnoreCase(rootLetters) && r.getId() != id);
            if (exists) {
                showAlert(Alert.AlertType.WARNING, "Root already exists in database!");
                return;
            }
            rootService.updateRoot(id, rootLetters);
            showAlert(Alert.AlertType.INFORMATION, "Root updated successfully!");
            loadData();
            clearFields();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error updating root: " + ex.getMessage());
        }
    }

    private void deleteRoot() {
        String idText = lblId.getText();
        if (idText.equals("Auto-generated")) {
            showAlert(Alert.AlertType.ERROR, "Please select a root to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this root?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                int id = Integer.parseInt(idText);
                rootService.deleteRoot(id);
                showAlert(Alert.AlertType.INFORMATION, "Root deleted successfully!");
                loadData();
                clearFields();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error deleting root: " + ex.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
