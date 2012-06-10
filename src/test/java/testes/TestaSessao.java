package testes;

import junit.framework.Assert;
import logica.CaronaAbstrata;
import logica.GeradorDeID;
import logica.Sessao;
import logica.Sistema;
import logica.Usuario;
import logica.VisualizadorDePerfil;

import org.junit.Before;
import org.junit.Test;

public class TestaSessao {
	CaronaAbstrata c1,c2;
	Usuario usuario,usuario2;
	String id;
	GeradorDeID gerador  = new GeradorDeID();
	Sessao sessao;
	
	@Before
	public void setUp() throws Exception{
		Sistema sis = new Sistema();
		id = gerador.geraId();
		usuario = sis.criarUsuario("Mark", "123456", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
		usuario2 = sis.criarUsuario("Jurema", "123456", "Jurema da Silva",
				"Cabedelo", "jurema@cidadelinda.com");
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
