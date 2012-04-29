package si1.TesteLogica;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import si1.Excecoes.ReviewInvalidaException;
import si1.logica.Carona;
import si1.logica.GeradorDeID;
import si1.logica.Review;
import si1.logica.Sistema;
import si1.logica.Usuario;

public class ReviewTest {
	
	Carona c1,c2;
	Usuario usuario,usuario2;
	String id;
	GeradorDeID gerador  = new GeradorDeID();
	
	@Before
	public void setUp() throws Exception{
		Sistema sis = new Sistema();
		
		id = gerador.geraId();
		usuario = sis.criarUsuario("Mark", "123456", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
		c1 = new Carona("Campina Grande", "Joao Pessoa",
				"20/09/2012", "19:00", 3,usuario);
		usuario2 = sis.criarUsuario("Jurema", "123456", "Jurema da Silva",
				"Cabedelo", "jurema@cidadelinda.com");
		c2 = new Carona("Campina Grande", "Joao Pessoa",
				"20/09/2012", "19:00", 3,usuario2);
		
	}
	
	@Test 
	public void testaCriacaoReviewValida() throws Exception{
		
		Review rev = new Review(id,usuario,c1,"Boa");
		Assert.assertNotNull(rev);
	}
	
	@Test (expected=ReviewInvalidaException.class)
	public void testaCriacaoReviewInValida() throws Exception{
		
		Review rev1,rev2,rev3,rev4 = null;
		rev1 = new Review(null,usuario,c1,"Boa");
		rev2 = new Review(id,null,c1,"Boa");
		rev3 = new Review(id,usuario,null,"Boa");
		rev4 = new Review(id,usuario,c1,null);
		Assert.assertNull(rev1);
		Assert.assertNull(rev2);
		Assert.assertNull(rev3);
		Assert.assertNull(rev4);
	}
	
	
	@Test 
	public void testaGets() throws Exception{
		
		Review rev = new Review(id,usuario,c1,"Boa");
		Assert.assertEquals(id,rev.getId());
		Assert.assertEquals(usuario,rev.getUsuario());		
		Assert.assertEquals(c1,rev.getCarona());
		Assert.assertEquals("Boa",rev.getReview());
	}
	
	@Test
	public void testaSets() throws Exception{
			
		Review rev = new Review(id,usuario,c1,"Boa");
		id = gerador.geraId();
		rev.setId(id);
		rev.setUsuario(usuario2);
		rev.setCarona(c2);
		rev.setReview("Ruim");
		Assert.assertEquals(id,rev.getId());		
		Assert.assertEquals(usuario2,rev.getUsuario());		
		Assert.assertEquals(c2,rev.getCarona());		
		Assert.assertEquals("Ruim",rev.getReview());
	}
	
}
