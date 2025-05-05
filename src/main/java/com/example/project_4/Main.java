// Name: Kamil Roginski
// Project: CMSC 315 Project 4
// Date: 5 MAY  2025
// Description: Main application class setting up the JavaFX GUI and event handlers.

package com.example.project_4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
    private TextField messageField;

    @Override
    public void start(Stage primaryStage) {
        Graph graph = new Graph();
        GraphPane graphPane = new GraphPane(graph);

        TextField v1Field = new TextField();
        v1Field.setPromptText("Vertex 1");
        TextField v2Field = new TextField();
        v2Field.setPromptText("Vertex 2");
        Button addEdgeButton = new Button("Add Edge");

        addEdgeButton.setOnAction(e -> {
            // Automatically convert inputs to uppercase to match vertex names
            String name1 = v1Field.getText().trim().toUpperCase();
            String name2 = v2Field.getText().trim().toUpperCase();
            try {
                graph.addEdge(name1, name2);
                graphPane.drawGraph();
                messageField.setText("");
            } catch (IllegalArgumentException ex) {
                messageField.setText("Error: " + ex.getMessage());
            } finally {
                v1Field.clear();
                v2Field.clear();
            }
        });

        Button isConnButton = new Button("Is Connected");
        isConnButton.setOnAction(e -> {
            boolean conn = graph.isConnected();
            messageField.setText(conn
                    ? "Graph is connected"
                    : "Graph is not connected");
        });

        Button hasCycleButton = new Button("Has Cycles");
        hasCycleButton.setOnAction(e -> {
            boolean cycle = graph.hasCycles();
            messageField.setText(cycle
                    ? "Graph has cycles"
                    : "Graph has no cycles");
        });

        Button dfsButton = new Button("Depth First Search");
        dfsButton.setOnAction(e -> {
            List<Vertex> order = graph.depthFirstSearch();
            messageField.setText(order.stream()
                    .map(Vertex::getName)
                    .collect(Collectors.joining(" ")));
        });

        Button bfsButton = new Button("Breadth First Search");
        bfsButton.setOnAction(e -> {
            List<Vertex> order = graph.breadthFirstSearch();
            messageField.setText(order.stream()
                    .map(Vertex::getName)
                    .collect(Collectors.joining(" ")));
        });

        HBox edgeBox = new HBox(5,
                new Label("Vertex 1:"), v1Field,
                new Label("Vertex 2:"), v2Field,
                addEdgeButton
        );
        edgeBox.setPadding(new Insets(5));

        HBox buttonBox = new HBox(5,
                isConnButton, hasCycleButton, dfsButton, bfsButton
        );
        buttonBox.setPadding(new Insets(5));

        messageField = new TextField();
        messageField.setEditable(false);

        VBox bottomBox = new VBox(5, buttonBox, messageField);
        bottomBox.setPadding(new Insets(5));

        BorderPane root = new BorderPane();
        root.setCenter(graphPane);
        root.setTop(edgeBox);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Project 4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}