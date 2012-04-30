package testes;

import junit.framework.Assert;
import logica.Carona;
import logica.GeradorDeID;
import logica.Sessao;
import logica.Sistema;
import logica.Usuario;
import logica.VisualizadorDePerfil;

import org.junit.Before;
import org.junit.Test;

public class TestaSessao {
	Carona c1,c2;
	Usuario usuario,usuario2;
	String id;
	GeradorDeID gerador  = new GeradorDeID();
	Sessao sessao;
	
	@Before
	public void setUp() throws Exception{
		sessao = new Sessao(usuario.getLogin());
	}
	
	@Test
	public void testaSetsGets() throws Exception{
		
		sessao.setId(id);
		Assert.assertEquals(id,sessao.getId());
		sessao.setLogin(usuario2.getLogin());
		Assert.assertEquals(usuario2.getLogin(),sessao.getLogin());
	}
}
