/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

/*
 * Mustafa Hamad
 * 455095
 * SE14A
 */
package twitter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */
	private static final Instant T1 = Instant.parse("2025-10-17T10:26:26Z");
	private static final Instant T2 = Instant.parse("2025-10-17T11:00:00Z");
	private static final Instant T3 = Instant.parse("2025-10-17T12:00:00Z");

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertTrue("expected empty list", influencers.isEmpty());
	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * SocialNetwork class that follows the spec. It will be run against several
	 * staff implementations of SocialNetwork, which will be done by overwriting
	 * (temporarily) your version of SocialNetwork with the staff's version. DO NOT
	 * strengthen the spec of SocialNetwork or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in SocialNetwork, because that means you're testing a stronger
	 * spec than SocialNetwork says. If you need such helper methods, define them in
	 * a different class. If you only need them in this test class, then keep them
	 * in this test class.
	 */

	private static Tweet tweet(long id, String author, String text, Instant time) {
		return new Tweet(id, author, text, time);
	}

	// 1. Empty list of tweets -> empty graph
	@Test
	public void testGuessEmptyTweets() {
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(Collections.emptyList());
		assertTrue(g.isEmpty());
	}

	// 2. Tweets without mentions -> no keys required
	@Test
	public void testGuessNoMentions() {
		List<Tweet> ts = Arrays.asList(tweet(1, "alice", "hello world", T1), tweet(2, "bob", "just text no ats", T2));
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(ts);
		assertTrue(g.isEmpty());
	}

	// 3. Single mention
	@Test
	public void testGuessSingleMention() {
		List<Tweet> ts = Collections.singletonList(tweet(1, "Alice", "hi @Bob", T1));
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(ts);

		assertTrue(g.containsKey("alice"));
		assertEquals(new HashSet<>(Arrays.asList("bob")), g.get("alice"));
	}

	// 4. Multiple distinct mentions in one tweet
	@Test
	public void testGuessMultipleMentionsOneTweet() {
		List<Tweet> ts = Collections.singletonList(tweet(1, "carol", "to @Dave and @EVE and @dave again", T1));
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(ts);

		assertEquals(1, g.size());
		assertEquals(new HashSet<>(Arrays.asList("dave", "eve")), g.get("carol"));
	}

	// 5. Multiple tweets from one user (accumulates)
	@Test
	public void testGuessAccumulatesAcrossTweets() {
		List<Tweet> ts = Arrays.asList(tweet(1, "ernie", "@bert hi", T1),
				tweet(2, "Ernie", "ping @Oscar and @Bert", T2));
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(ts);

		assertEquals(new HashSet<>(Arrays.asList("bert", "oscar")), g.get("ernie"));
	}

	// 6. Self-mention should not create self-follow
	@Test
	public void testGuessIgnoresSelfFollow() {
		List<Tweet> ts = Arrays.asList(tweet(1, "frank", "I am @Frank!", T1), tweet(2, "frank", "and @GRACE too", T2));
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(ts);

		assertTrue(g.get("frank").contains("grace"));
		assertFalse(g.get("frank").contains("frank"));
	}

	// 7. Case-insensitive usernames (author & mentions)
	@Test
	public void testGuessCaseInsensitive() {
		List<Tweet> ts = Arrays.asList(tweet(1, "HeLen", "hello @ALIce", T1),
				tweet(2, "helen", "and @alice again", T2));
		Map<String, Set<String>> g = SocialNetwork.guessFollowsGraph(ts);

		assertTrue(g.containsKey("helen"));
		assertEquals(new HashSet<>(Arrays.asList("alice")), g.get("helen"));
	}

	// 8. influencers(): empty graph -> empty list
	@Test
	public void testInfluencersEmptyGraph() {
		List<String> infl = SocialNetwork.influencers(Collections.emptyMap());
		assertTrue(infl.isEmpty());
	}

	// 9. influencers(): single influencer
	@Test
	public void testInfluencersSingle() {
		Map<String, Set<String>> g = new HashMap<>();
		g.put("alice", new HashSet<>(Arrays.asList("bob")));
		// bob has 1 follower; alice has 0
		List<String> infl = SocialNetwork.influencers(g);

		assertFalse(infl.isEmpty());
		assertEquals("bob", infl.get(0));
	}

	// 10. influencers(): multiple influencers and a tie
	@Test
	public void testInfluencersMultipleAndTies() {
		Map<String, Set<String>> g = new HashMap<>();
		g.put("a", new HashSet<>(Arrays.asList("x", "y"))); // x:1, y:1
		g.put("b", new HashSet<>(Arrays.asList("x"))); // x:2
		g.put("c", new HashSet<>(Arrays.asList("y"))); // y:2
		g.put("d", new HashSet<>(Arrays.asList("z"))); // z:1

		List<String> infl = SocialNetwork.influencers(g);

		// The top two should be x and y (both with 2). Order among them is
		// underdetermined.
		Set<String> topTwo = new HashSet<>(infl.subList(0, 2));
		assertEquals(new HashSet<>(Arrays.asList("x", "y")), topTwo);

		// Next should include z (1) and also followers like a/b/c/d may appear with 0.
		int idxZ = infl.indexOf("z");
		assertTrue(idxZ >= 2); // appears after the top two
	}
}
