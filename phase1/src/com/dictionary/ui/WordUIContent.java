package com.dictionary.ui;

import com.dictionary.model.WordDTO;
import com.dictionary.model.RootDTO;
import com.dictionary.model.PatternDTO;
import com.dictionary.service.WordService;
import com.dictionary.service.RootService;
import com.dictionary.service.PatternService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WordUIContent {

    private TextField txtWord;
    private ComboBox<RootDTO> cmbRoot;
    private ComboBox<PatternDTO> cmbPattern;
    private TextArea txtMeaning;
    private TextArea txtExample;
    private TableView<WordDTO> table;
    private ObservableList<WordDTO> data;
    private WordService wordService;
    private RootService rootService;
    private PatternService patternService;
    private Label lblId;
    private ArabicKeyboard arabicKeyboard;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public WordUIContent() {
        wordService = new WordService();
        rootService = new RootService();
        patternService = new PatternService();
    }

    public ScrollPane  getContent() {
        Label lblTitle = new Label("Arabic Words Dictionary");
        lblTitle.setFont(Font.font("Segoe UI", 28));
        lblTitle.setTextFill(Color.web("#2C3E50"));

        lblId = new Label("Auto-generated");
        txtWord = new TextField();
        txtWord.setPromptText("Enter Word");
        txtWord.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        cmbRoot = new ComboBox<>();
        cmbRoot.setPromptText("Select Root");
        cmbRoot.setPrefWidth(200);
        loadRootsCombo();

        cmbPattern = new ComboBox<>();
        cmbPattern.setPromptText("Select Pattern");
        cmbPattern.setPrefWidth(200);
        loadPatternsCombo();

        txtMeaning = new TextArea();
        txtMeaning.setPromptText("Enter Meaning");
        txtMeaning.setWrapText(true);
        txtMeaning.setPrefRowCount(3);
        txtMeaning.setStyle("-fx-font-size: 12; -fx-padding: 8;");

        txtExample = new TextArea();
        txtExample.setPromptText("Enter Example");
        txtExample.setWrapText(true);
        txtExample.setPrefRowCount(3);
        txtExample.setStyle("-fx-font-size: 12; -fx-padding: 8;");

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));
        formPane.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-padding: 15;");
        
        formPane.add(new Label("ID:"), 0, 0);
        formPane.add(lblId, 1, 0);
        formPane.add(new Label("Word:"), 0, 1);
        formPane.add(txtWord, 1, 1);
        formPane.add(new Label("Root:"), 0, 2);
        formPane.add(cmbRoot, 1, 2);
        formPane.add(new Label("Pattern:"), 0, 3);
        formPane.add(cmbPattern, 1, 3);
        formPane.add(new Label("Meaning:"), 0, 4);
        formPane.add(txtMeaning, 1, 4);
        formPane.add(new Label("Example:"), 0, 5);
        formPane.add(txtExample, 1, 5);

        arabicKeyboard = new ArabicKeyboard(txtWord);
        VBox keyboardBox = arabicKeyboard.createKeyboard();

        Button btnAdd = createRoundedButton("Add", "#27AE60");
        Button btnUpdate = createRoundedButton("Update", "#3498DB");
        Button btnDelete = createRoundedButton("Delete", "#E74C3C");
        Button btnClear = createRoundedButton("Clear", "#95A5A6");

        HBox buttonBox = new HBox(15, btnAdd, btnUpdate, btnDelete, btnClear);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        table = new TableView<>();
        TableColumn<WordDTO, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<WordDTO, String> colWord = new TableColumn<>("Word");
        colWord.setCellValueFactory(new PropertyValueFactory<>("word"));
        colWord.setPrefWidth(100);

        TableColumn<WordDTO, Integer> colRootId = new TableColumn<>("Root ID");
        colRootId.setCellValueFactory(new PropertyValueFactory<>("rootId"));
        colRootId.setPrefWidth(70);

        TableColumn<WordDTO, Integer> colPatternId = new TableColumn<>("Pattern ID");
        colPatternId.setCellValueFactory(new PropertyValueFactory<>("patternId"));
        colPatternId.setPrefWidth(80);

        TableColumn<WordDTO, String> colMeaning = new TableColumn<>("Meaning");
        colMeaning.setCellValueFactory(new PropertyValueFactory<>("meaning"));
        colMeaning.setPrefWidth(150);

        TableColumn<WordDTO, String> colCreatedAt = new TableColumn<>("Created");
        colCreatedAt.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCreatedAt() != null ? 
                cellData.getValue().getCreatedAt().format(dateFormatter) : ""
            )
        );
        colCreatedAt.setPrefWidth(130);

        table.getColumns().addAll(colId, colWord, colRootId, colPatternId, colMeaning, colCreatedAt);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No data found"));
        table.setPrefHeight(300);

        // Table selection listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblId.setText(String.valueOf(newSel.getId()));
                txtWord.setText(newSel.getWord());
                selectComboValue(cmbRoot, newSel.getRootId());
                selectComboValue(cmbPattern, newSel.getPatternId());
                txtMeaning.setText(newSel.getMeaning());
                txtExample.setText(newSel.getExample());
            }
        });

        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #ECF0F1;");
        mainContent.getChildren().addAll(lblTitle, formPane, keyboardBox, buttonBox, new Separator(), table);
        mainContent.setVgrow(table, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);

        // Button Actions
        btnAdd.setOnAction(e -> addWord());
        btnUpdate.setOnAction(e -> updateWord());
        btnDelete.setOnAction(e -> deleteWord());
        btnClear.setOnAction(e -> clearFields());

        loadData();

        return scrollPane;
    }

    private void loadRootsCombo() {
        List<RootDTO> roots = rootService.getAllRoots();
        cmbRoot.setItems(FXCollections.observableArrayList(roots));
    }

    private void loadPatternsCombo() {
        List<PatternDTO> patterns = patternService.getAllPatterns();
        cmbPattern.setItems(FXCollections.observableArrayList(patterns));
    }

    private void selectComboValue(ComboBox<?> combo, int id) {
        for (int i = 0; i < combo.getItems().size(); i++) {
            Object item = combo.getItems().get(i);
            if (item instanceof RootDTO && ((RootDTO) item).getId() == id) {
                combo.getSelectionModel().select(i);
                return;
            } else if (item instanceof PatternDTO && ((PatternDTO) item).getId() == id) {
                combo.getSelectionModel().select(i);
                return;
            }
        }
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
            List<WordDTO> words = wordService.getAllWords();
            data = FXCollections.observableArrayList(words);
            table.setItems(data);
        } catch (Exception e) {
            System.err.println("Error loading words: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        lblId.setText("Auto-generated");
        txtWord.clear();
        txtMeaning.clear();
        txtExample.clear();
        cmbRoot.getSelectionModel().clearSelection();
        cmbPattern.getSelectionModel().clearSelection();
        table.getSelectionModel().clearSelection();
    }

    private void addWord() {
        String word = txtWord.getText().trim();
        String meaning = txtMeaning.getText().trim();
        String example = txtExample.getText().trim();
        RootDTO selectedRoot = cmbRoot.getSelectionModel().getSelectedItem();
        PatternDTO selectedPattern = cmbPattern.getSelectionModel().getSelectedItem();

        if (word.isEmpty() || selectedRoot == null || selectedPattern == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill all required fields (Word, Root, Pattern).");
            return;
        }

        WordDTO newWord = new WordDTO(word, selectedRoot.getId(), selectedPattern.getId(), meaning, example);
        wordService.addWord(newWord);
        showAlert(Alert.AlertType.INFORMATION, "Word added successfully!");
        loadData();
        clearFields();
    }

    private void updateWord() {
        String idText = lblId.getText();
        String word = txtWord.getText().trim();
        String meaning = txtMeaning.getText().trim();
        String example = txtExample.getText().trim();
        RootDTO selectedRoot = cmbRoot.getSelectionModel().getSelectedItem();
        PatternDTO selectedPattern = cmbPattern.getSelectionModel().getSelectedItem();

        if (idText.equals("Auto-generated")) {
            showAlert(Alert.AlertType.ERROR, "Please select a word to update.");
            return;
        }
        if (word.isEmpty() || selectedRoot == null || selectedPattern == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill all required fields.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            WordDTO updatedWord = new WordDTO(id, word, selectedRoot.getId(), selectedPattern.getId(), meaning, example, null, null);
            wordService.updateWord(updatedWord);
            showAlert(Alert.AlertType.INFORMATION, "Word updated successfully!");
            loadData();
            clearFields();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error updating word: " + ex.getMessage());
        }
    }

    private void deleteWord() {
        String idText = lblId.getText();
        if (idText.equals("Auto-generated")) {
            showAlert(Alert.AlertType.ERROR, "Please select a word to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this word?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                int id = Integer.parseInt(idText);
                wordService.deleteWord(id);
                showAlert(Alert.AlertType.INFORMATION, "Word deleted successfully!");
                loadData();
                clearFields();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error deleting word: " + ex.getMessage());
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
