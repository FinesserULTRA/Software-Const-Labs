package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {

	private final List<Vertex> vertices = new ArrayList<>();

	// Abstraction function:
	// AF(vertices) = a weighted directed graph where
	// vertices = list of vertices, each containing its label and outgoing edges
	//
	// Representation invariant:
	// - vertices is not null
	// - all vertices in the list are not null
	// - all vertex labels are unique (no duplicates)
	// - all edge targets exist as vertices in the graph
	//
	// Safety from rep exposure:
	// - vertices is private and final
	// - Vertex is mutable, but not exposed outside this class
	// - vertices() returns an unmodifiable set of labels (not Vertex objects)
	// - sources() and targets() return new HashMap instances

	/**
	 * Create a new empty graph.
	 */
	public ConcreteVerticesGraph() {
		checkRep();
	}

	/**
	 * Check the representation invariant.
	 */
	private void checkRep() {
		assert vertices != null : "vertices list cannot be null";

		Set<String> labels = new HashSet<>();
		for (Vertex v : vertices) {
			assert v != null : "vertex cannot be null";
			assert !labels.contains(v.getLabel()) : "duplicate vertex labels not allowed";
			labels.add(v.getLabel());
		}

		// Check all edge targets exist
		for (Vertex v : vertices) {
			for (String target : v.getTargets().keySet()) {
				assert labels.contains(target) : "edge target must exist as vertex";
			}
		}
	}

	/**
	 * Find a vertex by its label.
	 * 
	 * @param label the vertex label to search for
	 * @return the Vertex object with the given label, or null if not found
	 */
	private Vertex findVertex(String label) {
		for (Vertex v : vertices) {
			if (v.getLabel().equals(label)) {
				return v;
			}
		}
		return null;
	}

	@Override
	public boolean add(String vertex) {
		if (findVertex(vertex) != null) {
			return false;
		}
		vertices.add(new Vertex(vertex));
		checkRep();
		return true;
	}

	@Override
	public int set(String source, String target, int weight) {
		// Ensure both vertices exist
		Vertex sourceVertex = findVertex(source);
		if (sourceVertex == null) {
			sourceVertex = new Vertex(source);
			vertices.add(sourceVertex);
		}

		Vertex targetVertex = findVertex(target);
		if (targetVertex == null) {
			targetVertex = new Vertex(target);
			vertices.add(targetVertex);
		}

		int previousWeight = sourceVertex.setEdge(target, weight);
		checkRep();
		return previousWeight;
	}

	@Override
	public boolean remove(String vertex) {
		Vertex v = findVertex(vertex);
		if (v == null) {
			return false;
		}

		// Remove the vertex from the list
		vertices.remove(v);

		// Remove all edges to this vertex from other vertices
		for (Vertex other : vertices) {
			other.removeEdge(vertex);
		}

		checkRep();
		return true;
	}

	@Override
	public Set<String> vertices() {
		Set<String> labels = new HashSet<>();
		for (Vertex v : vertices) {
			labels.add(v.getLabel());
		}
		return Collections.unmodifiableSet(labels);
	}

	@Override
	public Map<String, Integer> sources(String target) {
		Map<String, Integer> result = new HashMap<>();

		for (Vertex v : vertices) {
			Map<String, Integer> targets = v.getTargets();
			if (targets.containsKey(target)) {
				result.put(v.getLabel(), targets.get(target));
			}
		}

		return result;
	}

	@Override
	public Map<String, Integer> targets(String source) {
		Vertex v = findVertex(source);
		if (v == null) {
			return new HashMap<>();
		}
		return new HashMap<>(v.getTargets());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Graph with ").append(vertices.size()).append(" vertices:\n");
		for (Vertex v : vertices) {
			sb.append("  ").append(v.toString()).append("\n");
		}
		return sb.toString();
	}

}

/**
 * Represents a mutable vertex in a graph with outgoing edges. A vertex has a
 * label and maintains a map of outgoing edges to other vertices.
 * 
 * This class is internal to the rep of ConcreteVerticesGraph.
 */
class Vertex {

	private final String label;
	private final Map<String, Integer> targets;

	// Abstraction function:
	// AF(label, targets) = a vertex with the given label and outgoing edges
	// where targets maps target vertex labels to edge weights
	//
	// Representation invariant:
	// - label is not null
	// - targets is not null
	// - all target labels (keys) are not null
	// - all weights (values) are positive (> 0)
	//
	// Safety from rep exposure:
	// - label is private, final, and immutable (String)
	// - targets is private and final; getTargets() returns a new HashMap
	// - setEdge and removeEdge ensure only positive weights are stored

	/**
	 * Create a new vertex with the given label.
	 * 
	 * @param label the vertex label, must not be null
	 * @throws IllegalArgumentException if label is null
	 */
	public Vertex(String label) {
		if (label == null) {
			throw new IllegalArgumentException("label must not be null");
		}
		this.label = label;
		this.targets = new HashMap<>();
		checkRep();
	}

	/**
	 * Check the representation invariant.
	 */
	private void checkRep() {
		assert label != null : "label cannot be null";
		assert targets != null : "targets map cannot be null";

		for (Map.Entry<String, Integer> entry : targets.entrySet()) {
			assert entry.getKey() != null : "target label cannot be null";
			assert entry.getValue() > 0 : "edge weight must be positive";
		}
	}

	/**
	 * Get the label of this vertex.
	 * 
	 * @return the vertex label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Get the outgoing edges from this vertex.
	 * 
	 * @return a map from target vertex labels to edge weights
	 */
	public Map<String, Integer> getTargets() {
		return new HashMap<>(targets);
	}

	/**
	 * Add, update, or remove an outgoing edge from this vertex.
	 * 
	 * @param target the target vertex label
	 * @param weight the edge weight; if 0, removes the edge
	 * @return the previous weight of the edge, or 0 if there was no such edge
	 */
	public int setEdge(String target, int weight) {
		int previousWeight = targets.getOrDefault(target, 0);

		if (weight == 0) {
			targets.remove(target);
		} else {
			targets.put(target, weight);
		}

		checkRep();
		return previousWeight;
	}

	/**
	 * Remove an outgoing edge from this vertex.
	 * 
	 * @param target the target vertex label
	 * @return the previous weight of the edge, or 0 if there was no such edge
	 */
	public int removeEdge(String target) {
		int previousWeight = targets.getOrDefault(target, 0);
		targets.remove(target);
		checkRep();
		return previousWeight;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(label);
		if (!targets.isEmpty()) {
			sb.append(" -> {");
			boolean first = true;
			for (Map.Entry<String, Integer> entry : targets.entrySet()) {
				if (!first)
					sb.append(", ");
				sb.append(entry.getKey()).append("(").append(entry.getValue()).append(")");
				first = false;
			}
			sb.append("}");
		}
		return sb.toString();
	}

}
