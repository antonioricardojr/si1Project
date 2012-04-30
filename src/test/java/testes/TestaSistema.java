package testes;

import logica.Carona;
import logica.GeradorDeID;
import logica.Sistema;
import logica.Usuario;
import logica.VisualizadorDePerfil;

import org.junit.Before;
import org.junit.Test;

public class TestaSistema {
	Carona c1,c2;
	Usuario usuario,usuario2;
	String id;
	GeradorDeID gerador  = new GeradorDeID();
	VisualizadorDePerfil vdp;
	Sistema sis;
	
	@Before
	public void setUp() throws Exception{
		sis = new Sistema();
		
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
	public void testa() throws Exception{
		
		sis.abrirSessao(usuario.getLogin(), usuario.getSenha());
		sis.encerrarSessao(usuario.getLogin());
		
		
	}
	
	@Test
	public void testaGets() throws Exception{
		c1 = new Carona("Campina Grande", "Joao Pessoa",
				 3,usuario.getLogin());
	
		sis.abrirSessao(usuario.getLogin(), usuario.getSenha());
		
		sis.cadastrarCarona(idSessao,"Campina Grande","Joao Pessoa","20/09/2012","19:00",3)
		sis
	}
}
