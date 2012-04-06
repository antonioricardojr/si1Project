package si1.TesteLogica;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import si1.logica.Carona;
import si1.logica.Sistema;
import si1.logica.Usuario;

public class CaronaTest {

	Carona c1;
	Carona c2;
	Carona c3;
	Carona c4;
	Carona c5;
	Usuario usuario;
	@Before
	public void setUp() throws Exception {
		Sistema sis = new Sistema();
		
		usuario = sis.criarUsuario("Mark", "123456", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrigem() throws Exception {
		
		try {
			c1 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"20/09/2012", "19:00", 3,usuario);
		} catch (Exception e) {

		}
		assertEquals("Campina Grande", c1.getOrigem());

		try {
			c2 = new Carona("123456", null, "Natal", "20/07/2012", "14:00", 2,usuario);
		} catch (Exception e) {
			assertEquals("Origem inválida", e.getMessage());

		}

		try {
			c3 = new Carona("1020301", "", "Fortaleza", "04/08/2012", "09:00", 1,usuario);
		} catch (Exception e) {
			assertEquals("Origem inválida", e.getMessage());
		}
		
		try {
			c4 = new Carona("1020301", "", "Fort@l&z@", "04/08/2012", "09:00", 1,usuario);
		} catch (Exception e) {
			assertEquals("Origem inválida", e.getMessage());
		}
		
		

	}

	@Test
	public void testHora() throws Exception {

		try {
			c1 = new Carona("7894561", "Campina Grande", "Fortaleza",
					"04/08/2012", "09:00", 1,usuario);
		} catch (Exception e) {
		}
		assertEquals("09:00", c1.getHora());

		try {
			c2 = new Carona("7894561", "Campina Grande", "Fortaleza",
					"04/08/2012", "seis", 1,usuario);
		} catch (Exception e) {
			assertEquals("Hora inválida", e.getMessage());
		}
		
		try{
			c3 = new Carona("7894561", "Campina Grande", "Fortaleza", "04/08/2012", "09000", 1,usuario);	
		}catch(Exception e){
			assertEquals("Hora inválida", e.getMessage());
		}
		
		try{
			c4 = new Carona("7894561", "Campina Grande", "Fortaleza", "04/08/2012", "009:0", 1,usuario);	
		}catch(Exception e){
			assertEquals("Hora inválida", e.getMessage());
		}
	}

	@Test
	public void testIdSessao(){
		try {
			c1 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"10/08/2012", "19:00", 3,usuario);
		} catch (Exception e) {

		}
		assertEquals("091029", c1.getIdSessao());
		
		try{
			c2 = new Carona("091029A", "Campina Grande", "Joao Pessoa",
					"10/08/2012", "19:00", 3,usuario);
		}catch(Exception e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
		
		try{
			c3 = new Carona("teste", "Campina Grande", "Joao Pessoa",
					"10/08/2012", "19:00", 3,usuario);
		}catch(Exception e){
			assertEquals("Sessão inexistente", e.getMessage());
		}
	}

	@Test
	public void testData(){
		try {
			c1 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"02/08/2012", "19:00", 3,usuario);
		} catch (Exception e) {

		}
		assertEquals("02/08/2012", c1.getData());
		
		try {
			c2 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"30/02/2013", "19:00", 3,usuario);
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
		
		try {
			c3 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"29/02/2013", "19:00", 3,usuario);
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
		
		try {
			c4 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"32/02/2013", "19:00", 3,usuario);
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
		
		try {
			c5 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"32/02/2010", "19:00", 3,usuario);
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
	}
	
	@Test
	public void testVagas(){
		try {
			c1 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"01/02/2013", "19:00", 3,usuario);
		}catch (Exception e){
		}
		assertEquals(3, c1.getVagas());
		
		try {
			c2 = new Carona("091029", "Campina Grande", "Joao Pessoa",
					"01/02/2013", "19:00", -1,usuario);
		}catch (Exception e){
			assertEquals("Vaga inválida", e.getMessage());
		}
	}

}
