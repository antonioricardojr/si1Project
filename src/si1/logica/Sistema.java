package si1.logica;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representará as chamadas do sistema.
 * 
 * @author ANTONIOR
 * 
 */

public class Sistema {

	private List<Usuario> usuarios;
	private List<Sessao> sessoes;

	public Sistema() {
		usuarios = new ArrayList<Usuario>();
		sessoes = new ArrayList<Sessao>();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	

	public Usuario criarUsuario(String login, String senha, String nome,
			String endereco, String email) throws Exception {

		Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);
		usuarios.add(novoUsuario);
		return novoUsuario;
	}

	public String abrirSessao(String login, String senha) throws Exception {
		Sessao novaSessao = null;

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				if (verificaSenha(senha)) {
					novaSessao = new Sessao(login);
					sessoes.add(novaSessao);
				}
			}
		}
		return novaSessao.getId();
	}

	private boolean verificaSenha(String senha) {

		for (Usuario u : usuarios) {
			if (u.getSenha().equals(senha)) {
				return true;
			}
		}

		return false;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public String getAtributoUsuario(String login, String atributo) throws Exception {

		Usuario u = buscaUsuario(login);

		if (u == null) {
			throw new Exception("Login nao encontrado.");
		}

		if (atributo.equals("nome")) {
			return u.getNome();
		}
		if (atributo.equals("endereco")) {
			return u.getEndereco();
		}

		return null;
	}

	private Usuario buscaUsuario(String login) {
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}

}
