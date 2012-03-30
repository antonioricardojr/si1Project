package Facade;

import si1.logica.Sessao;
import si1.logica.Usuario;

/**
 * Classe Facade para usar os testes do easyAccept.
 * 
 * @author ANTONIOR
 *
 */
public class Si1ProjectFacade {
	
	private Usuario mark;
	private Sessao sessaoMark;
	
	
	public void zerarSistema(){
		mark = null;
	}
	
	public void abrirSessao(String login, String senha) throws Exception{
		sessaoMark = new Sessao(login, senha);
	}
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		mark = new Usuario(login, senha, nome, endereco, email);
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
