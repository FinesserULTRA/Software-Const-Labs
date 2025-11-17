package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph();
	}

	/*
	 * Testing ConcreteVerticesGraph...
	 */

	// Testing strategy for ConcreteVerticesGraph.toString()
	// - empty graph
	// - graph with vertices only
	// - graph with vertices and edges

	@Test
	public void testToStringEmptyGraph() {
		Graph<String> graph = emptyInstance();
		String result = graph.toString();
		assertTrue("toString should mention graph info", result.contains("Graph") || result.contains("vertices"));
	}

	@Test
	public void testToStringWithVerticesAndEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		graph.set("B", "C", 3);
		String result = graph.toString();
		assertTrue("toString should contain A", result.contains("A"));
		assertTrue("toString should contain B", result.contains("B"));
	}

	/*
	 * Testing Vertex...
	 */

	// Testing strategy for Vertex
	// Constructor:
	// - valid label
	// - null label
	// getLabel():
	// - verify correct label returned
	// setEdge():
	// - add new edge
	// - update existing edge
	// - remove edge (weight = 0)
	// getTargets():
	// - no edges
	// - one edge
	// - multiple edges
	// removeEdge():
	// - remove existing edge
	// - remove nonexistent edge
	// toString():
	// - vertex with no edges
	// - vertex with edges

	@Test
	public void testVertexConstructorValid() {
		Vertex vertex = new Vertex("A");
		assertEquals("A", vertex.getLabel());
		assertEquals(0, vertex.getTargets().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVertexConstructorNullLabel() {
		new Vertex(null);
	}

	@Test
	public void testVertexSetEdgeAdd() {
		Vertex vertex = new Vertex("A");
		int prev = vertex.setEdge("B", 5);
		assertEquals("expected no previous edge", 0, prev);
		assertEquals("expected one target", 1, vertex.getTargets().size());
		assertEquals("expected weight 5", 5, (int) vertex.getTargets().get("B"));
	}

	@Test
	public void testVertexSetEdgeUpdate() {
		Vertex vertex = new Vertex("A");
		vertex.setEdge("B", 5);
		int prev = vertex.setEdge("B", 10);
		assertEquals("expected previous weight 5", 5, prev);
		assertEquals("expected weight 10", 10, (int) vertex.getTargets().get("B"));
	}

	@Test
	public void testVertexSetEdgeRemove() {
		Vertex vertex = new Vertex("A");
		vertex.setEdge("B", 5);
		int prev = vertex.setEdge("B", 0);
		assertEquals("expected previous weight 5", 5, prev);
		assertEquals("expected no targets", 0, vertex.getTargets().size());
	}

	@Test
	public void testVertexGetTargetsEmpty() {
		Vertex vertex = new Vertex("A");
		assertEquals(0, vertex.getTargets().size());
	}

	@Test
	public void testVertexGetTargetsMultiple() {
		Vertex vertex = new Vertex("A");
		vertex.setEdge("B", 5);
		vertex.setEdge("C", 3);
		assertEquals(2, vertex.getTargets().size());
		assertEquals(5, (int) vertex.getTargets().get("B"));
		assertEquals(3, (int) vertex.getTargets().get("C"));
	}

	@Test
	public void testVertexRemoveEdge() {
		Vertex vertex = new Vertex("A");
		vertex.setEdge("B", 5);
		int prev = vertex.removeEdge("B");
		assertEquals("expected previous weight 5", 5, prev);
		assertEquals("expected no targets", 0, vertex.getTargets().size());
	}

	@Test
	public void testVertexRemoveNonexistentEdge() {
		Vertex vertex = new Vertex("A");
		int prev = vertex.removeEdge("B");
		assertEquals("expected no previous edge", 0, prev);
	}

	@Test
	public void testVertexToString() {
		Vertex vertex = new Vertex("A");
		vertex.setEdge("B", 5);
		String result = vertex.toString();
		assertTrue("toString should contain vertex label", result.contains("A"));
		assertTrue("toString should contain target", result.contains("B"));
	}

}
