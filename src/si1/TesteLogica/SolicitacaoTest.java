package testes;

import org.junit.Before;
import org.junit.Test;

import si1.logica.Carona;
import si1.logica.GeradorDeID;
import si1.logica.Sistema;
import si1.logica.Solicitacao;
import si1.logica.Usuario;

public class SolicitacaoTest {
	
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
	public void testaCriacao(){
		
		Solicitacao sol = new Solicitacao(id, usuario.getLogin(), c1.getId(), "Campina Grande");
		
		
	}
}
