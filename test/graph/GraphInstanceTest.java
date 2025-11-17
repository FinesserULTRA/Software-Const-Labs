	/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	// Testing strategy
	// TODO

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		// TODO you may use, change, or remove this test
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// Testing strategy
	//
	// Partition for add(vertex):
	// - adding to empty graph
	// - adding new vertex to non-empty graph
	// - adding duplicate vertex
	//
	// Partition for set(source, target, weight):
	// - weight = 0 (remove edge): edge exists, edge doesn't exist
	// - weight > 0 (add/update): new edge, update existing edge, vertices don't
	// exist
	// - source and target: same vertex (self-loop), different vertices
	//
	// Partition for remove(vertex):
	// - vertex exists with no edges
	// - vertex exists with outgoing edges only
	// - vertex exists with incoming edges only
	// - vertex exists with both incoming and outgoing edges
	// - vertex doesn't exist
	//
	// Partition for vertices():
	// - empty graph
	// - graph with one vertex
	// - graph with multiple vertices
	//
	// Partition for sources(target):
	// - target has no incoming edges
	// - target has one incoming edge
	// - target has multiple incoming edges
	// - target doesn't exist in graph
	//
	// Partition for targets(source):
	// - source has no outgoing edges
	// - source has one outgoing edge
	// - source has multiple outgoing edges
	// - source doesn't exist in graph

	// Tests for add()

	@Test
	public void testAddSingleVertex() {
		Graph<String> graph = emptyInstance();
		assertTrue("expected add to return true for new vertex", graph.add("A"));
		assertTrue("expected graph to contain added vertex", graph.vertices().contains("A"));
		assertEquals("expected graph to have one vertex", 1, graph.vertices().size());
	}

	@Test
	public void testAddDuplicateVertex() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		assertFalse("expected add to return false for duplicate vertex", graph.add("A"));
		assertEquals("expected graph to still have one vertex", 1, graph.vertices().size());
	}

	@Test
	public void testAddMultipleVertices() {
		Graph<String> graph = emptyInstance();
		assertTrue(graph.add("A"));
		assertTrue(graph.add("B"));
		assertTrue(graph.add("C"));
		assertEquals("expected graph to have three vertices", 3, graph.vertices().size());
		assertTrue(graph.vertices().contains("A"));
		assertTrue(graph.vertices().contains("B"));
		assertTrue(graph.vertices().contains("C"));
	}

	// Tests for set()

	@Test
	public void testSetAddNewEdge() {
		Graph<String> graph = emptyInstance();
		int prevWeight = graph.set("A", "B", 5);
		assertEquals("expected previous weight to be 0 for new edge", 0, prevWeight);
		assertTrue("expected source vertex to be added", graph.vertices().contains("A"));
		assertTrue("expected target vertex to be added", graph.vertices().contains("B"));
	}

	@Test
	public void testSetUpdateExistingEdge() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		int prevWeight = graph.set("A", "B", 10);
		assertEquals("expected previous weight to be 5", 5, prevWeight);
		assertEquals("expected updated weight to be 10", 10, (int) graph.targets("A").get("B"));
	}

	@Test
	public void testSetRemoveEdgeWithZeroWeight() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		int prevWeight = graph.set("A", "B", 0);
		assertEquals("expected previous weight to be 5", 5, prevWeight);
		assertFalse("expected edge to be removed", graph.targets("A").containsKey("B"));
	}

	@Test
	public void testSetRemoveNonexistentEdge() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		graph.add("B");
		int prevWeight = graph.set("A", "B", 0);
		assertEquals("expected previous weight to be 0 for nonexistent edge", 0, prevWeight);
	}

	@Test
	public void testSetSelfLoop() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "A", 3);
		assertTrue("expected vertex A to exist", graph.vertices().contains("A"));
		assertEquals("expected self-loop weight to be 3", 3, (int) graph.targets("A").get("A"));
	}

	// Tests for remove()

	@Test
	public void testRemoveVertexNoEdges() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		assertTrue("expected remove to return true for existing vertex", graph.remove("A"));
		assertFalse("expected vertex to be removed", graph.vertices().contains("A"));
	}

	@Test
	public void testRemoveNonexistentVertex() {
		Graph<String> graph = emptyInstance();
		assertFalse("expected remove to return false for nonexistent vertex", graph.remove("A"));
	}

	@Test
	public void testRemoveVertexWithOutgoingEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		graph.set("A", "C", 3);
		assertTrue(graph.remove("A"));
		assertFalse("expected vertex A to be removed", graph.vertices().contains("A"));
		assertEquals("expected no sources for B", 0, graph.sources("B").size());
		assertEquals("expected no sources for C", 0, graph.sources("C").size());
	}

	@Test
	public void testRemoveVertexWithIncomingEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "C", 5);
		graph.set("B", "C", 3);
		assertTrue(graph.remove("C"));
		assertFalse("expected vertex C to be removed", graph.vertices().contains("C"));
		assertEquals("expected no targets for A", 0, graph.targets("A").size());
		assertEquals("expected no targets for B", 0, graph.targets("B").size());
	}

	@Test
	public void testRemoveVertexWithBothEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		graph.set("B", "C", 3);
		graph.set("C", "B", 2);
		assertTrue(graph.remove("B"));
		assertFalse(graph.vertices().contains("B"));
		assertEquals("expected no targets for A", 0, graph.targets("A").size());
		assertEquals("expected no sources for C", 0, graph.sources("C").size());
	}

	// Tests for vertices()

	@Test
	public void testVerticesEmptyGraph() {
		Graph<String> graph = emptyInstance();
		assertEquals("expected empty set for empty graph", Collections.emptySet(), graph.vertices());
	}

	@Test
	public void testVerticesSingleVertex() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		Set<String> vertices = graph.vertices();
		assertEquals("expected one vertex", 1, vertices.size());
		assertTrue("expected vertex A", vertices.contains("A"));
	}

	@Test
	public void testVerticesMultipleVertices() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		graph.add("B");
		graph.add("C");
		Set<String> vertices = graph.vertices();
		assertEquals("expected three vertices", 3, vertices.size());
		assertTrue(vertices.contains("A"));
		assertTrue(vertices.contains("B"));
		assertTrue(vertices.contains("C"));
	}

	// Tests for sources()

	@Test
	public void testSourcesNoIncomingEdges() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		Map<String, Integer> sources = graph.sources("A");
		assertEquals("expected no sources", 0, sources.size());
	}

	@Test
	public void testSourcesSingleIncomingEdge() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		Map<String, Integer> sources = graph.sources("B");
		assertEquals("expected one source", 1, sources.size());
		assertEquals("expected source A with weight 5", 5, (int) sources.get("A"));
	}

	@Test
	public void testSourcesMultipleIncomingEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "C", 5);
		graph.set("B", "C", 3);
		Map<String, Integer> sources = graph.sources("C");
		assertEquals("expected two sources", 2, sources.size());
		assertEquals("expected source A with weight 5", 5, (int) sources.get("A"));
		assertEquals("expected source B with weight 3", 3, (int) sources.get("B"));
	}

	@Test
	public void testSourcesNonexistentVertex() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		Map<String, Integer> sources = graph.sources("B");
		assertEquals("expected no sources for nonexistent vertex", 0, sources.size());
	}

	// Tests for targets()

	@Test
	public void testTargetsNoOutgoingEdges() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		Map<String, Integer> targets = graph.targets("A");
		assertEquals("expected no targets", 0, targets.size());
	}

	@Test
	public void testTargetsSingleOutgoingEdge() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		Map<String, Integer> targets = graph.targets("A");
		assertEquals("expected one target", 1, targets.size());
		assertEquals("expected target B with weight 5", 5, (int) targets.get("B"));
	}

	@Test
	public void testTargetsMultipleOutgoingEdges() {
		Graph<String> graph = emptyInstance();
		graph.set("A", "B", 5);
		graph.set("A", "C", 3);
		Map<String, Integer> targets = graph.targets("A");
		assertEquals("expected two targets", 2, targets.size());
		assertEquals("expected target B with weight 5", 5, (int) targets.get("B"));
		assertEquals("expected target C with weight 3", 3, (int) targets.get("C"));
	}

	@Test
	public void testTargetsNonexistentVertex() {
		Graph<String> graph = emptyInstance();
		graph.add("A");
		Map<String, Integer> targets = graph.targets("B");
		assertEquals("expected no targets for nonexistent vertex", 0, targets.size());
	}

}
