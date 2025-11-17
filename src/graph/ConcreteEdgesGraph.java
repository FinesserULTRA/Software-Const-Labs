package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    // AF(vertices, edges) = a weighted directed graph where
    // vertices = the set of vertex labels in the graph
    // edges = the set of directed edges, each with source, target, and positive
    // weight
    //
    // Representation invariant:
    // - vertices is not null
    // - edges is not null
    // - all edges have non-null source and target vertices
    // - all edges have positive weights
    // - all edge source and target vertices are in the vertices set
    // - no duplicate edges (same source and target pair)
    //
    // Safety from rep exposure:
    // - vertices and edges are private and final
    // - vertices() returns an unmodifiable set
    // - sources() and targets() return new HashMap instances
    // - Edge is immutable

    /**
     * Create a new empty graph.
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }

    /**
     * Check the representation invariant.
     */
    private void checkRep() {
        assert vertices != null : "vertices set cannot be null";
        assert edges != null : "edges list cannot be null";

        for (Edge edge : edges) {
            assert edge != null : "edge cannot be null";
            assert vertices.contains(edge.getSource()) : "edge source must be in vertices";
            assert vertices.contains(edge.getTarget()) : "edge target must be in vertices";
            assert edge.getWeight() > 0 : "edge weight must be positive";
        }

        // Check for duplicate edges
        for (int i = 0; i < edges.size(); i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                Edge e1 = edges.get(i);
                Edge e2 = edges.get(j);
                assert !(e1.getSource().equals(e2.getSource()) &&
                        e1.getTarget().equals(e2.getTarget())) : "duplicate edges not allowed";
            }
        }
    }

    @Override
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        // Find existing edge
        int previousWeight = 0;
        Edge existingEdge = null;

        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                existingEdge = edge;
                previousWeight = edge.getWeight();
                break;
            }
        }

        if (weight == 0) {
            // Remove edge if it exists
            if (existingEdge != null) {
                edges.remove(existingEdge);
            }
        } else {
            // Add or update edge
            // First ensure vertices exist
            vertices.add(source);
            vertices.add(target);

            if (existingEdge != null) {
                // Update: remove old edge and add new one
                edges.remove(existingEdge);
            }
            edges.add(new Edge(source, target, weight));
        }

        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }

        // Remove all edges involving this vertex
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
                iterator.remove();
            }
        }

        vertices.remove(vertex);
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(new HashSet<>(vertices));
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();

        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                result.put(edge.getSource(), edge.getWeight());
            }
        }

        return result;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> result = new HashMap<>();

        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                result.put(edge.getTarget(), edge.getWeight());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph with ").append(vertices.size()).append(" vertices and ")
                .append(edges.size()).append(" edges:\n");
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges:\n");
        for (Edge edge : edges) {
            sb.append("  ").append(edge.toString()).append("\n");
        }
        return sb.toString();
    }

}

/**
 * Represents an immutable weighted directed edge in a graph.
 * An edge connects a source vertex to a target vertex with a positive weight.
 * 
 * This class is internal to the rep of ConcreteEdgesGraph.
 */
class Edge {

    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function:
    // AF(source, target, weight) = a directed edge from source to target with given
    // weight
    //
    // Representation invariant:
    // - source is not null
    // - target is not null
    // - weight > 0
    //
    // Safety from rep exposure:
    // - all fields are private, final, and immutable (String is immutable, int is
    // primitive)
    // - getters return primitives or immutable objects

    /**
     * Create a new edge.
     * 
     * @param source the source vertex label, must not be null
     * @param target the target vertex label, must not be null
     * @param weight the edge weight, must be positive
     * @throws IllegalArgumentException if source or target is null, or weight is
     *                                  not positive
     */
    public Edge(String source, String target, int weight) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("source and target must not be null");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("weight must be positive");
        }

        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    /**
     * Check the representation invariant.
     */
    private void checkRep() {
        assert source != null : "source cannot be null";
        assert target != null : "target cannot be null";
        assert weight > 0 : "weight must be positive";
    }

    /**
     * Get the source vertex of this edge.
     * 
     * @return the source vertex label
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the target vertex of this edge.
     * 
     * @return the target vertex label
     */
    public String getTarget() {
        return target;
    }

    /**
     * Get the weight of this edge.
     * 
     * @return the edge weight
     */
    public int getWeight() {
        return weight;
    }

    @Override	 
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }

}
