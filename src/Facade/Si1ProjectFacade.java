package Facade;


import si1.logica.Sistema;

/**
 * Classe Facade para usar os testes do easyAccept.
 * 
 * @author ANTONIOR
 * 
 */
public class Si1ProjectFacade {

	Sistema S1 = new Sistema();

	public void zerarSistema() {
		S1 = new Sistema();
	}

	public void criarUsuario(String login, String senha, String nome,
			String endereco, String email) throws Exception {
		S1.criarUsuario(login, senha, nome, endereco, email);

	}

	public String abrirSessao(String login, String senha) throws Exception {
		return S1.abrirSessao(login, senha);

	}

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		return S1.getAtributoUsuario(login, atributo);
	}

	public static void main(String[] args) {
		System.out.println("oiola");
	}

}