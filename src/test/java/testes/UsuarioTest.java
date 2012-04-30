package testes;

import junit.framework.Assert;
import logica.Carona;
import logica.Usuario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


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
			Assert.assertEquals("Login inválido", e.getMessage());
		}

		try {
			u1 = new Usuario("", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		}

		try {
			u1 = new Usuario("!", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		}

		try {
			u1 = new Usuario("mark*", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		}

		try {
			u1 = new Usuario("ma&rk", "123456", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Login inválido", e.getMessage());
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
			Assert.assertEquals("Senha inválida", e.getMessage());
		}

		try {
			u2 = new Usuario("mark", "", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			Assert.assertEquals("Senha inválida", e.getMessage());
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
			Assert.assertEquals("Nome inválido", e.getMessage());

		}

		try {
			u1 = new Usuario("mark", "123456", "", "Palo Alto, California",
					"mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome inválido", e.getMessage());

		}

		try {
			u1 = new Usuario("mark", "123456", "M@rk", "Palo Alto, California",
					"mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome inválido", e.getMessage());

		}

		try {
			u1 = new Usuario("mark", "123456", "Mark-Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");

		} catch (Exception e) {
			Assert.assertEquals("Nome inválido", e.getMessage());

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
			Assert.assertEquals("Endereço inválido", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg",
					"", "mark@facebook.com");
		}catch(Exception e){
			Assert.assertEquals("Endereço inválido", e.getMessage());
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
			Assert.assertEquals("Email inválido", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "");
		}catch(Exception e){
			Assert.assertEquals("Email inválido", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@@facebook.com");
		}catch(Exception e){
			Assert.assertEquals("Email inválido", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@.facebook.com");
		}catch(Exception e){
			Assert.assertEquals("Email inválido", e.getMessage());
		}
		
		try{
			u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook...com");
		}catch(Exception e){
			Assert.assertEquals("Email inválido", e.getMessage());
		}
		
		u2 = new Usuario("steve", "654321", "Steve Pal Jobs", "Palo Alto, California", "steve@apple.com");
		Assert.assertEquals("steve@apple.com", u2.getEmail());
		
		u3 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		Assert.assertEquals("mark@facebook.com", u3.getEmail());
		
		
	}
	
	@Test
	public void testeAdicionarAmigo() throws Exception{
		
		u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		
		u2 = new Usuario("steve", "654321", "Steve Pal Jobs", "Palo Alto, California", "steve@apple.com");
		
		u3 = new Usuario("bill", "456789", "William Henry Gates III", "Palo Alto, California", "bill@microsoft.com");
		
		u1.adicionaAmigo(u2.getLogin());
		
		Assert.assertEquals(u2.getLogin(), u1.getAmigos().get(0));
		
				
	}
	
	@Test
	public void testeAdicionarERemoverCarona() throws Exception{
		
		u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		
		u2 = new Usuario("steve", "654321", "Steve Pal Jobs", "Palo Alto, California", "steve@apple.com");
		
		u3 = new Usuario("bill", "456789", "William Henry Gates III", "Palo Alto, California", "bill@microsoft.com");
		
		
		Carona c1 = new Carona("Campina Grande", "João Pessoa", "01/10/2012", "17:50", 3, u1.getLogin());
		u1.adicionaCarona(c1.getId());
		Assert.assertEquals(c1.getId(), u1.getCaronas().get(0));		
		
		Carona c2 = new Carona("Campina Grande", "João Pessoa", "07/10/2012", "14:40", 2, u2.getLogin());
		u2.adicionaCarona(c2.getId());
		Assert.assertEquals(c2.getId(), u2.getCaronas().get(0));
		
		
		Carona c3 = new Carona("Recife", "João Pessoa", "16/10/2012", "20:10", 5, u3.getLogin());
		
		u3.adicionaCarona(c3.getId());
		Assert.assertEquals(c3.getId(), u3.getCaronas().get(0));
		
		
		Carona c4 = new Carona("Recife", "Nata", "28/11/2012", "07:20", 1, u1.getLogin());
		u1.adicionaCarona(c4.getId());
		Assert.assertEquals(c4.getId(), u1.getCaronas().get(1));
		
	}
	
	@Test
	public void testeAdicionarERemoverCaronaOferecida() throws Exception{
		
		u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		
		u2 = new Usuario("steve", "654321", "Steve Pal Jobs", "Palo Alto, California", "steve@apple.com");
		
		u3 = new Usuario("bill", "456789", "William Henry Gates III", "Palo Alto, California", "bill@microsoft.com");
		
		
		Carona c1 = new Carona("Campina Grande", "João Pessoa", "01/10/2012", "17:50", 3, u1.getLogin());
		u1.adicionaCaronaOferecida(c1.getId());
		Assert.assertEquals(c1.getId(), u1.getCaronasOferecidas().get(0));
		
		
		Carona c2 = new Carona("Campina Grande", "João Pessoa", "07/10/2012", "14:40", 2, u2.getLogin());
		u2.adicionaCaronaOferecida(c2.getId());
		Assert.assertEquals(c2.getId(), u2.getCaronasOferecidas().get(0));
		
		
		Carona c3 = new Carona("Recife", "João Pessoa", "16/10/2012", "20:10", 5, u3.getLogin());
		u3.adicionaCaronaOferecida(c3.getId());
		Assert.assertEquals(c3.getId(), u3.getCaronasOferecidas().get(0));
		
		
		Carona c4 = new Carona("Recife", "Nata", "28/11/2012", "07:20", 1, u1.getLogin());
		u1.adicionaCaronaOferecida(c4.getId());
		Assert.assertEquals(c4.getId(), u1.getCaronasOferecidas().get(1));
		
		
		
		Assert.assertEquals(2, u1.getCaronasOferecidas().size());
		u1.removeCaronaOferecida(c1.getId());
		Assert.assertEquals(1, u1.getCaronasOferecidas().size());
		
		u1.removeCaronaOferecida(c4.getId());
		Assert.assertEquals(0, u1.getCaronasOferecidas().size());

	}
	
	@Test
	public void testeAdicionarERemoverCaronaComoCaroneiro() throws Exception{
		
		u1 = new Usuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com.br");
		
		u2 = new Usuario("steve", "654321", "Steve Pal Jobs", "Palo Alto, California", "steve@apple.com");
		
		u3 = new Usuario("bill", "456789", "William Henry Gates III", "Palo Alto, California", "bill@microsoft.com");
		
		
		Carona c1 = new Carona("Campina Grande", "João Pessoa", "01/10/2012", "17:50", 3, u1.getLogin());
		u1.adicionaCaronaComoCaroneiro(c1.getId());
		Assert.assertEquals(c1.getId(), u1.getCaronasComoCaroneiro().get(0));
		
		
		Carona c2 = new Carona("Campina Grande", "João Pessoa", "07/10/2012", "14:40", 2, u2.getLogin());
		u2.adicionaCaronaComoCaroneiro(c2.getId());
		Assert.assertEquals(c2.getId(), u2.getCaronasComoCaroneiro().get(0));
		
		
		Carona c3 = new Carona("Recife", "João Pessoa", "16/10/2012", "20:10", 5, u3.getLogin());
		u3.adicionaCaronaComoCaroneiro(c3.getId());
		Assert.assertEquals(c3.getId(), u3.getCaronasComoCaroneiro().get(0));
		
		
		Carona c4 = new Carona("Recife", "Nata", "28/11/2012", "07:20", 1, u1.getLogin());
		u1.adicionaCaronaComoCaroneiro(c4.getId());
		Assert.assertEquals(c4.getId(), u1.getCaronasComoCaroneiro().get(1));
		
		
		
		Assert.assertEquals(2, u1.getCaronasComoCaroneiro().size());
		u1.removeCaronaComoCaroneiro(c1.getId());
		Assert.assertEquals(1, u1.getCaronasComoCaroneiro().size());
		
		u1.removeCaronaComoCaroneiro(c4.getId());
		Assert.assertEquals(0, u1.getCaronasComoCaroneiro().size());

	}

}
