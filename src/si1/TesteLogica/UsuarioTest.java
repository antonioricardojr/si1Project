package si1.TesteLogica;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import si1.logica.Usuario;

public class UsuarioTest {

	Usuario u1;
	Usuario u2;
	Usuario u3;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testLogin() throws Exception {

		try {
			u1 = new Usuario(null, "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login nulo ou invalido.", e.getMessage());
		}

		try {
			u1 = new Usuario("", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login nulo ou invalido.", e.getMessage());
		}

		try {
			u1 = new Usuario("!", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login nulo ou invalido.", e.getMessage());
		}

		try {
			u1 = new Usuario("mark*", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login nulo ou invalido.", e.getMessage());
		}

		try {
			u1 = new Usuario("ma&rk", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login nulo ou invalido.", e.getMessage());
		}
		
		u2 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		Assert.assertEquals("mark", u2.getLogin());

		
	}

	@Test
	public void testSenha() throws Exception {
		try {
			u1 = new Usuario("mark", null, "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("senha nula ou invalida.", e.getMessage());
		}

		try {
			u2 = new Usuario("mark", "", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("senha nula ou invalida.", e.getMessage());
		}
		
		
		u2 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		Assert.assertEquals("123456", u2.getSenha());

		
	}

	@Test
	public void testNome() throws Exception {
		try {
			u1 = new Usuario("mark", "123456", null, "Palo Alto, California",
					"mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome nulo ou invalido.", e.getMessage());

		}

		try {
			u1 = new Usuario("mark", "123456", "", "Palo Alto, California",
					"mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome nulo ou invalido.", e.getMessage());

		}

		try {
			u1 = new Usuario("mark", "123456", "M@rk", "Palo Alto, California",
					"mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome nulo ou invalido.", e.getMessage());

		}

		try {
			u1 = new Usuario("mark", "123456", "Mark-Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome nulo ou invalido.", e.getMessage());

		}

		u2 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		Assert.assertEquals("Mark Zuckerberg", u2.getNome());

	}
	
	
	
	@Test
	public void testEndereco(){
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg",
					null, "mark@facebook.com");
		}catch(Exception e){
			Assert.assertEquals("Endereco nulo ou invalido.", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg",
					"", "mark@facebook.com");
		}catch(Exception e){
			Assert.assertEquals("Endereco nulo ou invalido.", e.getMessage());
		}
		
		try {
			u3 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			
		}
		
		
		Assert.assertEquals("Palo Alto, California", u3.getEndereco());
	}
	
	
	@Test
	public void testEmail() throws Exception{
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", null);
		}catch(Exception e){
			Assert.assertEquals("E-mail nulo ou invalido.", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "");
		}catch(Exception e){
			Assert.assertEquals("E-mail nulo ou invalido.", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@@facebook.com");
		}catch(Exception e){
			Assert.assertEquals("E-mail nulo ou invalido.", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@.facebook.com");
		}catch(Exception e){
			Assert.assertEquals("E-mail nulo ou invalido.", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook...com");
		}catch(Exception e){
			Assert.assertEquals("E-mail nulo ou invalido.", e.getMessage());
		}
		
		u2 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		Assert.assertEquals("mark@facebook.com.br", u2.getEmail());
		
		u3 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		Assert.assertEquals("mark@facebook.com", u3.getEmail());
		
		
	}

}
