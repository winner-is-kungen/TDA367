package com.winner_is_kungen.tda367.controller;

import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class typeidIconMapTest {
	@Test
	public void testComponentMapping(){
		assertEquals(ComponentControllerFactory.getComponentContent("NOT"), "!");
		assertEquals(ComponentControllerFactory.getComponentContent("NOT"), "!");
		assertEquals(ComponentControllerFactory.getComponentContent("NOT"), "!");
		assertNotEquals(ComponentControllerFactory.getComponentContent("NOT"), "&");
		assertNull(ComponentControllerFactory.getComponentContent("NULL"));
	}
}
