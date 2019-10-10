package com.winner_is_kungen.tda367.controller;

import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class typeidIconMapTest {
	@Test
	public void testComponentMapping(){
		assertEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/gateIcons/test.png"));
		assertEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/gateIcons/test.png"));
		assertEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/gateIcons/test.png"));
		assertNotEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/fakeFilePath/test.png"));
		assertNull(ComponentControllerFactory.getComponentIcon("NULL"));
	}
}
