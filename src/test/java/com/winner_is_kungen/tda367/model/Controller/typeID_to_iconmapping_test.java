package com.winner_is_kungen.tda367.model.Controller;

import com.winner_is_kungen.tda367.controller.ComponentControllerFactory;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class typeID_to_iconmapping_test {
	@Test
	public void testComponentMapping(){
		assertEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/gateIcons/test.png"));
		assertEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/gateIcons/test.png"));
		assertEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/gateIcons/test.png"));
		assertNotEquals(ComponentControllerFactory.getComponentIcon("NOT"), ("/fakeFilePath/test.png"));
		assertNull(ComponentControllerFactory.getComponentIcon("NULL"));
	}
}
