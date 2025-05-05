// Name: Kamil Roginski
// Project: CMSC 315 Project 4
// Date: 5 MAY  2025
// Description: Immutable class representing a graph vertex with coordinates and name.

package com.example.project_4;

public class Vertex {
    private final String name;
    private final double x;
    private final double y;

    public Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex other = (Vertex) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
