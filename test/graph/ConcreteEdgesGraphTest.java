package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph();
	}

	/*
	 * Testing ConcreteEdgesGraph...
	 */

	// Testing strategy for ConcreteEdgesGraph.toString()
	// - empty graph
	// - graph with vertices only
	// - graph with vertices and edges

	@Test
	public void testToStringEmptyGraph() {
		Graph<String> graph = emptyInstance();
		String result = graph.toString();
		assertTrue("toString should mention vertices", result.contains("vertices"));
		assertTrue("toString should mention edges", result.contains("edges"));
	}

	@Test
	public void testToStringWithVerticesAndEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		graph.set("B", "C", 3);
		String result = graph.toString();
		assertTrue("toString should contain vertex info", result.contains("2") || result.contains("vertices"));
		assertTrue("toString should contain A", result.contains("A"));
		assertTrue("toString should contain B", result.contains("B"));
	}

	/*
	 * Testing Edge...
	 */

	// Testing strategy for Edge
	// Constructor:
	// - valid inputs
	// - null source
	// - null target
	// - zero weight
	// - negative weight
	// getSource(), getTarget(), getWeight():
	// - verify correct values returned
	// toString():
	// - verify format includes source, target, and weight

	@Test
	public void testEdgeConstructorValid() {
		Edge edge = new Edge("A", "B", 5);
		assertEquals("A", edge.getSource());
		assertEquals("B", edge.getTarget());
		assertEquals(5, edge.getWeight());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNullSource() {
		new Edge(null, "B", 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNullTarget() {
		new Edge("A", null, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorZeroWeight() {
		new Edge("A", "B", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNegativeWeight() {
		new Edge("A", "B", -5);
	}

	@Test
	public void testEdgeGetters() {
		Edge edge = new Edge("source", "target", 10);
		assertEquals("source", edge.getSource());
		assertEquals("target", edge.getTarget());
		assertEquals(10, edge.getWeight());
	}

	@Test
	public void testEdgeToString() {
		Edge edge = new Edge("A", "B", 7);
		String result = edge.toString();
		assertTrue("toString should contain source", result.contains("A"));
		assertTrue("toString should contain target", result.contains("B"));
		assertTrue("toString should contain weight", result.contains("7"));
	}

}
