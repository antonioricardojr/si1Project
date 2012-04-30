package testes;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

import logica.Carona;
import logica.GeradorDeID;
import logica.Sistema;
import logica.Usuario;
import logica.VisualizadorDePerfil;

import org.junit.Before;
import org.junit.Test;

public class TestaVisualizadorDePerfil {
	
	Carona c1,c2;
	Usuario usuario,usuario2;
	String id;
	GeradorDeID gerador  = new GeradorDeID();
	VisualizadorDePerfil vdp;
	
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
		List<Carona> caronas = new ArrayList<Carona>();
		caronas.add(c1);
		caronas.add(c2);
		vdp = new VisualizadorDePerfil(usuario, caronas);
	}
	
	@Test
	public void testaGets(){
		Assert.assertEquals("", vdp.getHistoricoDeCaronas());
		Assert.assertEquals("",vdp.getCaronasQueNaoFuncionaram());
		Assert.assertEquals("",vdp.getCaronasSegurasETranquilas());
		Assert.assertEquals("",vdp.getFaltasEmVagasDeCaronas());
		Assert.assertEquals("",vdp.getPresencasEmVagasDeCaronas());
		
		//Assert.assertEquals(expected,vdp.g );
		//Assert.assertEquals(expected, );
	}
	
	@Test
	public void testaSets(){
		
	}
	
	
}
