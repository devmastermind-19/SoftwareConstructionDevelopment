package com.dictionary.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arabic Dictionary Management System");
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-font-size: 12;");

        // Create tabs for each module
        Tab wordTab = createWordTab();
        Tab rootTab = createRootTab();
        Tab patternTab = createPatternTab();

        tabPane.getTabs().addAll(wordTab, rootTab, patternTab);

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1400);
        primaryStage.setHeight(900);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private Tab createWordTab() {
        Tab tab = new Tab();
        tab.setText("Words");
        tab.setStyle("-fx-text-base-color: #2C3E50;");
        
        // Create a container for WordUI content
        WordUIContent wordContent = new WordUIContent();
        tab.setContent(wordContent.getContent());
        
        return tab;
    }

    private Tab createRootTab() {
        Tab tab = new Tab();
        tab.setText("Roots");
        tab.setStyle("-fx-text-base-color: #1E3C72;");
        
        // Create a container for RootUI content
        RootUIContent rootContent = new RootUIContent();
        tab.setContent(rootContent.getContent());
        
        return tab;
    }

    private Tab createPatternTab() {
        Tab tab = new Tab();
        tab.setText("Patterns");
        tab.setStyle("-fx-text-base-color: #8E44AD;");
        
        // Create a container for PatternUI content
        PatternUIContent patternContent = new PatternUIContent();
        tab.setContent(patternContent.getContent());
        
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
