// Name: Kamil Roginski
// Project: CMSC 315 Project 4
// Date: 5 MAY  2025
// Description: JavaFX Pane subclass to visually display and interact with the graph.

package com.example.project_4;

import javafx.scene.layout.Pane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import java.util.List;

public class GraphPane extends Pane {
    private final Graph graph;
    private char nextName = 'A';
    private static final double RADIUS = 5;

    public GraphPane(Graph graph) {
        this.graph = graph;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                if (event.getButton() == MouseButton.PRIMARY) {
                    // Add new vertex on left-click
                    String name = String.valueOf(nextName++);
                    Vertex v = new Vertex(name, x, y);
                    graph.addVertex(v);
                    drawGraph();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    // Remove vertex on right-click if clicked within node radius
                    Vertex toRemove = null;
                    for (Vertex v : graph.adjList.keySet()) {
                        double dx = v.getX() - x;
                        double dy = v.getY() - y;
                        if (Math.hypot(dx, dy) <= RADIUS) {
                            toRemove = v;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        graph.adjList.remove(toRemove);
                        graph.vertexMap.remove(toRemove.getName());
                        for (List<Vertex> nbrs : graph.adjList.values()) {
                            nbrs.remove(toRemove);
                        }
                        drawGraph();
                    }
                }
            }
        });
    }

    public void drawGraph() {
        this.getChildren().clear();

        // Draw edges
        for (Vertex v : graph.adjList.keySet()) {
            for (Vertex nbr : graph.adjList.get(v)) {
                if (v.getName().compareTo(nbr.getName()) < 0) {
                    Line line = new Line(v.getX(), v.getY(), nbr.getX(), nbr.getY());
                    this.getChildren().add(line);
                }
            }
        }

        // Draw vertices: filled black circle with label above
        for (Vertex v : graph.adjList.keySet()) {
            Circle circle = new Circle(v.getX(), v.getY(), RADIUS);
            circle.setFill(Color.BLACK);
            this.getChildren().add(circle);

            Text label = new Text(
                    v.getX() - 4,
                    v.getY() - 8,
                    v.getName()
            );
            label.setFill(Color.BLACK);
            this.getChildren().add(label);
        }
    }
}
