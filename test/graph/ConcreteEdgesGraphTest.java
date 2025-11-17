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
	// - valid inputs: single char labels, multi-char labels, numeric strings,
	// special chars
	// - boundary values: weight = 1, large weights, Integer.MAX_VALUE
	// - null source
	// - null target
	// - both null
	// - zero weight
	// - negative weight (various negative values)
	// - empty string labels
	// - same source and target (self-loop)
	// getSource(), getTarget(), getWeight():
	// - verify correct values returned for various inputs
	// - immutability check (returned values can't affect internal state)
	// toString():
	// - verify format includes source, target, and weight
	// - check with different label types
	// - check arrow and parentheses format

	// Constructor tests - valid cases
	@Test
	public void testEdgeConstructorSingleCharLabels() {
		Edge edge = new Edge("A", "B", 5);
		assertEquals("A", edge.getSource());
		assertEquals("B", edge.getTarget());
		assertEquals(5, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorMultiCharLabels() {
		Edge edge = new Edge("vertex1", "vertex2", 10);
		assertEquals("vertex1", edge.getSource());
		assertEquals("vertex2", edge.getTarget());
		assertEquals(10, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorNumericStringLabels() {
		Edge edge = new Edge("123", "456", 7);
		assertEquals("123", edge.getSource());
		assertEquals("456", edge.getTarget());
		assertEquals(7, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorSpecialCharLabels() {
		Edge edge = new Edge("node_1", "node-2", 3);
		assertEquals("node_1", edge.getSource());
		assertEquals("node-2", edge.getTarget());
		assertEquals(3, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorMinWeight() {
		Edge edge = new Edge("A", "B", 1);
		assertEquals(1, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorLargeWeight() {
		Edge edge = new Edge("A", "B", 999999);
		assertEquals(999999, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorMaxIntWeight() {
		Edge edge = new Edge("A", "B", Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorSelfLoop() {
		Edge edge = new Edge("A", "A", 5);
		assertEquals("A", edge.getSource());
		assertEquals("A", edge.getTarget());
		assertEquals(5, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorEmptyStringLabels() {
		Edge edge = new Edge("", "", 5);
		assertEquals("", edge.getSource());
		assertEquals("", edge.getTarget());
		assertEquals(5, edge.getWeight());
	}

	@Test
	public void testEdgeConstructorWhitespaceLabels() {
		Edge edge = new Edge(" ", "  ", 5);
		assertEquals(" ", edge.getSource());
		assertEquals("  ", edge.getTarget());
	}

	@Test
	public void testEdgeConstructorLongLabels() {
		String longLabel1 = "verylongvertexlabelwithalotofcharacters";
		String longLabel2 = "anotherlongvertexlabelwithmanychars";
		Edge edge = new Edge(longLabel1, longLabel2, 42);
		assertEquals(longLabel1, edge.getSource());
		assertEquals(longLabel2, edge.getTarget());
		assertEquals(42, edge.getWeight());
	}

	// Constructor tests - invalid cases
	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNullSource() {
		new Edge(null, "B", 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNullTarget() {
		new Edge("A", null, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorBothNull() {
		new Edge(null, null, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorZeroWeight() {
		new Edge("A", "B", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNegativeWeight() {
		new Edge("A", "B", -5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorNegativeWeightMinusOne() {
		new Edge("A", "B", -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorLargeNegativeWeight() {
		new Edge("A", "B", -999999);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeConstructorMinIntWeight() {
		new Edge("A", "B", Integer.MIN_VALUE);
	}

	// Getter tests
	@Test
	public void testEdgeGetSource() {
		Edge edge = new Edge("source", "target", 10);
		assertEquals("source", edge.getSource());
	}

	@Test
	public void testEdgeGetTarget() {
		Edge edge = new Edge("source", "target", 10);
		assertEquals("target", edge.getTarget());
	}

	@Test
	public void testEdgeGetWeight() {
		Edge edge = new Edge("source", "target", 10);
		assertEquals(10, edge.getWeight());
	}

	@Test
	public void testEdgeGettersMultipleCalls() {
		Edge edge = new Edge("X", "Y", 15);
		// Call getters multiple times to ensure consistency
		assertEquals("X", edge.getSource());
		assertEquals("X", edge.getSource());
		assertEquals("Y", edge.getTarget());
		assertEquals("Y", edge.getTarget());
		assertEquals(15, edge.getWeight());
		assertEquals(15, edge.getWeight());
	}

	@Test
	public void testEdgeImmutability() {
		Edge edge = new Edge("A", "B", 5);
		String source = edge.getSource();
		String target = edge.getTarget();
		// Verify original edge unchanged
		assertEquals("A", edge.getSource());
		assertEquals("B", edge.getTarget());
		assertEquals(5, edge.getWeight());
	}

	// toString tests
	@Test
	public void testEdgeToStringFormat() {
		Edge edge = new Edge("A", "B", 7);
		String result = edge.toString();
		assertTrue("toString should contain source", result.contains("A"));
		assertTrue("toString should contain target", result.contains("B"));
		assertTrue("toString should contain weight", result.contains("7"));
		assertTrue("toString should contain arrow", result.contains("->"));
	}

	@Test
	public void testEdgeToStringWithLongLabels() {
		Edge edge = new Edge("LongSourceVertex", "LongTargetVertex", 100);
		String result = edge.toString();
		assertTrue(result.contains("LongSourceVertex"));
		assertTrue(result.contains("LongTargetVertex"));
		assertTrue(result.contains("100"));
	}

	@Test
	public void testEdgeToStringSelfLoop() {
		Edge edge = new Edge("X", "X", 3);
		String result = edge.toString();
		assertTrue(result.contains("X"));
		assertTrue(result.contains("3"));
	}

	@Test
	public void testEdgeToStringMultipleCalls() {
		Edge edge = new Edge("A", "B", 5);
		String result1 = edge.toString();
		String result2 = edge.toString();
		assertEquals("toString should be consistent", result1, result2);
	}

	@Test
	public void testEdgeToStringWithSpecialChars() {
		Edge edge = new Edge("node_1", "node-2", 8);
		String result = edge.toString();
		assertTrue(result.contains("node_1"));
		assertTrue(result.contains("node-2"));
		assertTrue(result.contains("8"));
	}

	@Test
	public void testEdgeToStringWithEmptyLabels() {
		Edge edge = new Edge("", "B", 5);
		String result = edge.toString();
		assertTrue(result.contains("B"));
		assertTrue(result.contains("5"));
	}

	@Test
	public void testEdgeToStringWithLargeWeight() {
		Edge edge = new Edge("A", "B", 1000000);
		String result = edge.toString();
		assertTrue(result.contains("1000000"));
	}

	// Additional edge equality and comparison tests
	@Test
	public void testEdgeDifferentInstances() {
		Edge edge1 = new Edge("A", "B", 5);
		Edge edge2 = new Edge("A", "B", 5);
		// These are different objects
		assertNotSame(edge1, edge2);
		// But have same values
		assertEquals(edge1.getSource(), edge2.getSource());
		assertEquals(edge1.getTarget(), edge2.getTarget());
		assertEquals(edge1.getWeight(), edge2.getWeight());
	}

	@Test
	public void testEdgeDifferentWeights() {
		Edge edge1 = new Edge("A", "B", 5);
		Edge edge2 = new Edge("A", "B", 10);
		assertEquals(edge1.getSource(), edge2.getSource());
		assertEquals(edge1.getTarget(), edge2.getTarget());
		assertNotEquals(edge1.getWeight(), edge2.getWeight());
	}

	@Test
	public void testEdgeDifferentSourceSameTarget() {
		Edge edge1 = new Edge("A", "C", 5);
		Edge edge2 = new Edge("B", "C", 5);
		assertNotEquals(edge1.getSource(), edge2.getSource());
		assertEquals(edge1.getTarget(), edge2.getTarget());
	}

	@Test
	public void testEdgeSameSourceDifferentTarget() {
		Edge edge1 = new Edge("A", "B", 5);
		Edge edge2 = new Edge("A", "C", 5);
		assertEquals(edge1.getSource(), edge2.getSource());
		assertNotEquals(edge1.getTarget(), edge2.getTarget());
	}

}
