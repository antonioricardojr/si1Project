package si1.TesteLogica;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import si1.logica.Carona;

public class CaronaTest {
	
	Carona c1;
	Carona c2;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrigem() throws Exception {
		try{
			c1 = new Carona("091029", "Campina Grande", "Joao Pessoa", "12/12/12", "12:00", 3);	
		}catch(Exception e){
			
		}
		
		assertEquals("Campina Grande", c1.getOrigem());
	}

}
