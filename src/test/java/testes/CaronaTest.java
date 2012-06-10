package testes;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;
import logica.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CaronaTest {

	CaronaAbstrata c1;
	CaronaAbstrata c2;
	CaronaAbstrata c3;
	CaronaAbstrata c4;
	CaronaAbstrata c5;
	Usuario usuario;
	
	
	GeradorDeID gerador = new GeradorDeID();
	
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
			c1 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"20/09/2012", "19:00", 3,usuario.getLogin());
		} catch (Exception e) {

		}
		assertEquals("Campina Grande", c1.getOrigem());

		try {
			c2 = new CaronaAbstrata(null, "Natal", "20/07/2012", "14:00", 2,usuario.getLogin());
		} catch (Exception e) {
			assertEquals("Origem inválida", e.getMessage());

		}

		try {
			c3 = new CaronaAbstrata("", "Fortaleza", "04/08/2012", "09:00", 1,usuario.getLogin());
		} catch (Exception e) {
			assertEquals("Origem inválida", e.getMessage());
		}
		
		try {
			c4 = new CaronaAbstrata("", "Fort@l&z@", "04/08/2012", "09:00", 1,usuario.getLogin());
		} catch (Exception e) {
			assertEquals("Origem inválida", e.getMessage());
		}
		
		

	}

	@Test
	public void testHora() throws Exception {

		try {
			c1 = new CaronaAbstrata("Campina Grande", "Fortaleza",
					"04/08/2012", "09:00", 1,usuario.getLogin());
		} catch (Exception e) {
		}
		assertEquals("09:00", c1.getHora());

		try {
			c2 = new CaronaAbstrata("Campina Grande", "Fortaleza",
					"04/08/2012", "seis", 1,usuario.getLogin());
		} catch (Exception e) {
			assertEquals("Hora inválida", e.getMessage());
		}
		
		try{
			c3 = new CaronaAbstrata("Campina Grande", "Fortaleza", "04/08/2012", "09000", 1,usuario.getLogin());	
		}catch(Exception e){
			assertEquals("Hora inválida", e.getMessage());
		}
		
		try{
			c4 = new CaronaAbstrata("Campina Grande", "Fortaleza", "04/08/2012", "009:0", 1,usuario.getLogin());	
		}catch(Exception e){
			assertEquals("Hora inválida", e.getMessage());
		}
	}

	

	@Test
	public void testData(){
		try {
			c1 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"02/08/2012", "19:00", 3,usuario.getLogin());
		} catch (Exception e) {

		}
		assertEquals("02/08/2012", c1.getData());
		
		try {
			c2 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"30/02/2013", "19:00", 3,usuario.getLogin());
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
		
		try {
			c3 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"29/02/2013", "19:00", 3,usuario.getLogin());
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
		
		try {
			c4 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"32/02/2013", "19:00", 3,usuario.getLogin());
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
		
		try {
			c5 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"32/02/2010", "19:00", 3,usuario.getLogin());
		}catch (Exception e){
			assertEquals("Data inválida", e.getMessage());
		}
	}
	
	@Test
	public void testVagas(){
		try {
			c1 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"01/02/2013", "19:00", 3,usuario.getLogin());
		}catch (Exception e){
		}
		assertEquals(3, c1.getVagas());
		
		try {
			c2 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
					"01/02/2013", "19:00", -1,usuario.getLogin());
		}catch (Exception e){
			assertEquals("Vaga inválida", e.getMessage());
		}
	}
	
	@Test
	public void testVagasTotal() throws Exception{
		c1 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
				"01/02/2013", "19:00", 3,usuario.getLogin());
		
		c2 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
				"01/02/2013", "19:00", 2,usuario.getLogin());
		
		Usuario u1 = new Usuario("random", "123456", "Homem Random", "Palo Alto, California", "random@random.com");
		
		Usuario u2 = new Usuario("Steve", "123456", "Steve Paul Jobs", "Palo Alto, California", "steve@apple.com");
		
		Solicitacao s1 = new Solicitacao(gerador.geraId(), u1.getLogin(), c1.getId(), "Açude Velho");
		Solicitacao s2 = new Solicitacao(gerador.geraId(), u2.getLogin(), c1.getId(), "Praça da Bandeira");
		
		c1.addCaroneiro(s1);
		c1.addCaroneiro(s2);
		
		Assert.assertEquals(s1, c1.getCaroneiros().get(0));
		Assert.assertEquals(s2, c1.getCaroneiros().get(1));
		
		
		c1.removeSolicitacao(s1);
		
		Assert.assertEquals(s1, c1.getCaroneiros().get(0));
				
	}
	
	@Test
	public void testeReview(){
		
	}

}
