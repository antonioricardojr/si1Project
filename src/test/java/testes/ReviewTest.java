package testes;

import logica.Carona;
import logica.GeradorDeID;
import logica.Review;
import logica.Sistema;
import logica.Usuario;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excecoes.ReviewInvalidaException;


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
				"20/09/2012", "19:00", 3,usuario.getLogin());
		usuario2 = sis.criarUsuario("Jurema", "123456", "Jurema da Silva",
				"Cabedelo", "jurema@cidadelinda.com");
		c2 = new Carona("Campina Grande", "Joao Pessoa",
				"20/09/2012", "19:00", 3,usuario2.getLogin());
		
	}
	
	@Test 
	public void testaCriacaoReviewValida() throws Exception{
		
		Review rev = new Review(id,usuario.getLogin(),c1.getId(),"Boa");
		Assert.assertNotNull(rev);
	}
	
	@Test (expected=ReviewInvalidaException.class)
	public void testaCriacaoReviewInValida() throws Exception{
		
		Review rev1,rev2,rev3,rev4 = null;
		rev1 = new Review(null,usuario.getLogin(),c1.getId(),"Boa");
		rev2 = new Review(id,null,c1.getId(),"Boa");
		rev3 = new Review(id,usuario.getLogin(),null,"Boa");
		rev4 = new Review(id,usuario.getLogin(),c1.getId(),null);
		Assert.assertNull(rev1);
		Assert.assertNull(rev2);
		Assert.assertNull(rev3);
		Assert.assertNull(rev4);
	}
	
	
	@Test 
	public void testaGets() throws Exception{
		
		Review rev = new Review(id,usuario.getLogin(),c1.getId(),"Boa");
		Assert.assertEquals(id,rev.getId());
		Assert.assertEquals("Mark",rev.getUsuario());		
		Assert.assertEquals(c1.getId(),rev.getCarona());
		Assert.assertEquals(usuario,rev.getUsuario());		
		Assert.assertEquals(c1,rev.getCarona());
		Assert.assertEquals("Boa",rev.getReview());
	}
	
	@Test
	public void testaSets() throws Exception{
			
		Review rev = new Review(id,usuario.getLogin(),c1.getId(),"Boa");
		id = gerador.geraId();
		rev.setId(id);
		rev.setUsuario(usuario2.getLogin());
		rev.setCarona(c2.getId());
		rev.setReview("Ruim");
		Assert.assertEquals(id,rev.getId());		
		Assert.assertEquals(usuario2,rev.getUsuario());		
		Assert.assertEquals(c2,rev.getCarona());		
		Assert.assertEquals("Ruim",rev.getReview());
	}
	
}
