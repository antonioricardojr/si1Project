package testes;

import logica.CaronaAbstrata;
import logica.GeradorDeID;
import logica.Sistema;
import logica.Solicitacao;
import logica.Usuario;

import org.junit.Before;
import org.junit.Test;


public class SolicitacaoTest {
	
	CaronaAbstrata c1,c2;
	Usuario usuario,usuario2;
	String id;
	GeradorDeID gerador  = new GeradorDeID();
	
	@Before
	public void setUp() throws Exception{
		
		Sistema sis = new Sistema();		
		id = gerador.geraId();
		usuario = sis.criarUsuario("Mark", "123456", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
		c1 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
				"20/09/2012", "19:00", 3,usuario.getLogin());
		usuario2 = sis.criarUsuario("Jurema", "123456", "Jurema da Silva",
				"Cabedelo", "jurema@cidadelinda.com");
		c2 = new CaronaAbstrata("Campina Grande", "Joao Pessoa",
				"20/09/2012", "19:00", 3,usuario2.getLogin());
		
	}
	
	@Test
	public void testaCriacao(){
		
		Solicitacao sol = new Solicitacao(id, usuario.getLogin(), c1.getId(), "Campina Grande");
		
		
	}
}
