//Mohammad Shafi (cssc0821) & Tanner Sutton (cssc0829)

package edu.sdsu.cs.datastructures;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;



public class DirectedGraph<V extends Comparable<V>> implements IGraph<V> {

    private class Vertex<V extends Comparable<V>> {
        V label;
        List<V> neighbors = new LinkedList<V>();

        private Vertex(V label) {
            this.label = label;
        }

    }

    private LinkedList<Vertex<V>> vertexList = new LinkedList<>();


    @Override
    public void add(V vertexName) {
        if (!(contains(vertexName))) {
            Vertex<V> newVert = new Vertex(vertexName);
            vertexList.add(newVert);
        }
    }

    @Override
    public void connect(V start, V destination) {
        if (!(contains(start))) {
            throw new NoSuchElementException();
        }
        if (!(contains(destination))) {
            throw new NoSuchElementException();
        }
        Vertex<V> findVert = findVertex(vertexList, start);
        findVert.neighbors.add(destination);

    }

    @Override
    public void clear() {
        for (Vertex vertex : vertexList) {
            vertex.label = null;
            vertex.neighbors.clear();
        }
        vertexList.clear();
    }

    @Override
    public boolean contains(V label) {
        if (vertexList.contains(findVertex(vertexList, label)))
            return true;

        return false;
    }

    @Override
    public void disconnect(V start, V destination) {
        if (!(contains(start))) {
            throw new NoSuchElementException();
        }
        if (!(contains(destination))) {
            throw new NoSuchElementException();
        }
        Vertex<V> node = findVertex(vertexList, start);
        node.neighbors.remove(destination);
    }

    @Override
    public boolean isConnected(V start, V destination) {
        if (!(contains(start))) {
            throw new NoSuchElementException();
        }
        if (!(contains(destination))) {
            throw new NoSuchElementException();
        }

        Vertex<V> node = findVertex(vertexList, start);
        for (V neighbor : node.neighbors) {
            if (node.neighbors.contains(neighbor)) {
                return true;
            } else {
                isConnected(neighbor, destination);
            }
        }
        return false;
    }




    @Override
    public Iterable<V> neighbors(V vertexName) {
        if (!(contains(vertexName))) {
            throw new NoSuchElementException();
        }
        Vertex node = findVertex(vertexList, vertexName);
        return node.neighbors;
    }

    @Override
    public void remove(V vertexName) {
        if (!(contains(vertexName))) {
            throw new NoSuchElementException();
        }
        for (Vertex vert : vertexList) {
            disconnect((V) vert.label, vertexName);
        }
        Vertex<V> removedVertex = findVertex(vertexList, vertexName);
        removedVertex.label = null;
        removedVertex.neighbors.clear();
        vertexList.remove(removedVertex);
    }


    @Override
    public List<V> shortestPath(V start, V destination) {
        if (!(contains(start))) {
            throw new NoSuchElementException();
        }
        if (!(contains(destination))) {
            throw new NoSuchElementException();
        }


        PriorityQueue<V> pq = new PriorityQueue<>();
        HashMap<V, Integer> distanceTable = new HashMap<>();
        HashMap<V, V> bread = new HashMap<>();
        List<V> unvisited = new LinkedList<>();

        distanceTable.put(start, 0);
        bread.put(start, null);
        pq.add(start);

        for (Vertex<V> vert : vertexList) {
            if (!vert.label.equals(start)) {
                distanceTable.put(vert.label, Integer.MAX_VALUE);
            }
            unvisited.add(vert.label);
        }


        while (!pq.isEmpty()) {

            V temp = pq.poll();

            Integer dist = distanceTable.get(temp);

            Vertex<V> vert = findVertex(vertexList, temp);
            if(unvisited.contains(temp)) {

                unvisited.remove(temp);

                for (V neighbor : vert.neighbors) {

                    if (distanceTable.get(neighbor) > dist + 1) {
                        distanceTable.replace(neighbor, dist + 1);
                        bread.put(neighbor, temp);
                    }
                    if(unvisited.contains(neighbor)) {
                        pq.add(neighbor);
                    }
                }
            }

        }


        LinkedList<V> shortestPath = new LinkedList<>();

        for (int i = distanceTable.get(destination); i > 0; i--) {
            shortestPath.add(null);
        }


        shortestPath.add(distanceTable.get(destination), destination);
        V temp = bread.get(destination);


        for (int i = distanceTable.get(destination) - 1; i >= 0; --i) {

            shortestPath.set(i, temp);

            temp = bread.get(temp);


        }


        return shortestPath;
    }

    @Override
    public int size() {
        return vertexList.size();
    }

    @Override
    public Iterable<V> vertices() {
        LinkedList<V> vertLabel = new LinkedList();

        for (Vertex<V> vert : vertexList) {

            vertLabel.add(vert.label);
        }
        return vertLabel;
    }

    @Override
    public IGraph<V> connectedGraph(V origin) {
        IGraph<V> origingraph = new DirectedGraph<V>();
        populateGraph(origingraph, origin, null);
        return origingraph;
    }

    private void populateGraph(IGraph anyGraph, V nodeLabel, V parent) {

        anyGraph.add(nodeLabel);
        if (findVertex(vertexList, nodeLabel).neighbors.isEmpty()) {
            return;
        }
        if (parent != null) {
            anyGraph.connect(parent, nodeLabel);
        }
        Vertex<V> vert = findVertex(vertexList, nodeLabel);
        for (V neighbor : vert.neighbors) {
            populateGraph(anyGraph, neighbor, nodeLabel);


        }

    }

    private Vertex findVertex(List<Vertex<V>> vertexList, V label) {
        for (Vertex<V> vertex : vertexList) {
            if (vertex.label.equals(label)) {
                return vertex;
            }
        }
        return null;
    }


}