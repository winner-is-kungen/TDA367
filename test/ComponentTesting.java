import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.ComponentFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentTesting {
	@Test
	public void testTrueOrTrue() {
		Component or = ComponentFactory.create2OR(0, 0);
		or.setInPort(0, true);
		or.setInPort(1, true);
		or.calculate();
		assertTrue(or.getOutPort(0));
	}

	@Test
	public void testTrueOrFalse() {
		Component or = ComponentFactory.create2OR(0, 0);
		or.setInPort(0, true);
		or.setInPort(1, false);
		or.calculate();
		assertTrue(or.getOutPort(0));
	}

	@Test
	public void testFalseOrTrue() {
		Component or = ComponentFactory.create2OR(0, 0);
		or.setInPort(0, false);
		or.setInPort(1, true);
		or.calculate();
		assertTrue(or.getOutPort(0));
	}

	@Test
	public void testFalseOrFalse() {
		Component or = ComponentFactory.create2OR(0, 0);
		or.setInPort(0, false);
		or.setInPort(1, false);
		or.calculate();
		assertFalse(or.getOutPort(0));
	}
}
