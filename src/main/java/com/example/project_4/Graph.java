// Name: Kamil Roginski
// Project: CMSC 315 Project 4
// Date: 5 MAY  2025
// Description: Class representing an undirected graph with methods for analysis and traversal.

package com.example.project_4;
import java.util.*;

public class Graph {
    final Map<String, Vertex> vertexMap = new LinkedHashMap<>();
    final Map<Vertex, List<Vertex>> adjList = new HashMap<>();

    public void addVertex(Vertex v) {
        vertexMap.put(v.getName(), v);
        adjList.put(v, new ArrayList<>());
    }

    public void addEdge(String name1, String name2) {
        if (!vertexMap.containsKey(name1) || !vertexMap.containsKey(name2)) {
            throw new IllegalArgumentException("Vertex not found");
        }
        Vertex v1 = vertexMap.get(name1);
        Vertex v2 = vertexMap.get(name2);
        adjList.get(v1).add(v2);
        adjList.get(v2).add(v1);
    }

    public boolean isConnected() {
        if (vertexMap.isEmpty()) return true;
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex start = vertexMap.values().iterator().next();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            for (Vertex nbr : adjList.get(v)) {
                if (!visited.contains(nbr)) {
                    visited.add(nbr);
                    queue.add(nbr);
                }
            }
        }
        return visited.size() == vertexMap.size();
    }

    public boolean hasCycles() {
        Set<Vertex> visited = new HashSet<>();
        for (Vertex v : vertexMap.values()) {
            if (!visited.contains(v)) {
                if (dfsCycle(v, null, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCycle(Vertex current, Vertex parent, Set<Vertex> visited) {
        visited.add(current);
        for (Vertex nbr : adjList.get(current)) {
            if (!visited.contains(nbr)) {
                if (dfsCycle(nbr, current, visited)) {
                    return true;
                }
            } else if (!nbr.equals(parent)) {
                return true;
            }
        }
        return false;
    }

    public List<Vertex> depthFirstSearch() {
        List<Vertex> result = new ArrayList<>();
        Vertex start = vertexMap.get("A");
        if (start == null) return result;
        Set<Vertex> visited = new HashSet<>();
        dfsHelper(start, visited, result);
        return result;
    }

    private void dfsHelper(Vertex v, Set<Vertex> visited, List<Vertex> result) {
        visited.add(v);
        result.add(v);
        for (Vertex nbr : adjList.get(v)) {
            if (!visited.contains(nbr)) {
                dfsHelper(nbr, visited, result);
            }
        }
    }

    public List<Vertex> breadthFirstSearch() {
        List<Vertex> result = new ArrayList<>();
        Vertex start = vertexMap.get("A");
        if (start == null) return result;
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            result.add(v);
            for (Vertex nbr : adjList.get(v)) {
                if (!visited.contains(nbr)) {
                    visited.add(nbr);
                    queue.add(nbr);
                }
            }
        }
        return result;
    }
}

