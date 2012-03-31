package si1.TesteLogica;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import si1.logica.Carona;

public class CaronaTest {
	
	Carona c1;
	Carona c2;
	Carona c3;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrigem() throws Exception {
		try{
			c1 = new Carona("091029", "Campina Grande", "Joao Pessoa", "10/02/12", "19:00", 3);	
		}catch(Exception e){
			
		}
		assertEquals("Campina Grande", c1.getOrigem());
		
		try{
			c2 = new Carona("091029", null, "Natal", "20/07/12", "14:00", 2);	
		}catch(Exception e){
			assertEquals("Origem nula ou invalida", e.getMessage());
			
		}
		
		try{
			c3 = new Carona("091029", "", "Fortaleza", "04/06/12", "09:00", 1);	
		}catch(Exception e){
			assertEquals("Origem nula ou invalida", e.getMessage());
		}
		
		
	}
	
	

}
