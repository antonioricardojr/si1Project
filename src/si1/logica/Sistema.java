package si1.logica;

import java.util.ArrayList;
import java.util.LinkedList;
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
	private List<Carona> caronas;

	public Sistema() {
		usuarios = new ArrayList<Usuario>();
		sessoes = new ArrayList<Sessao>();
		caronas = new ArrayList<Carona>();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void criarUsuario(String login, String senha, String nome,
			String endereco, String email) throws Exception {

		if (login == null || login.equals("")) {
			throw new Exception("Login inválido");
		}

		if (nome == null || nome.equals("")) {
			throw new Exception("Nome inválido");
		}

		Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);

		usuarios.add(novoUsuario);

	}

	public String abrirSessao(String login, String senha) throws Exception {
		if (login == null || login.equals("")) {
			throw new Exception("Login inválido");
		}

		Sessao novaSessao = null;

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				if (verificaSenha(senha)) {
					novaSessao = new Sessao(login);
					sessoes.add(novaSessao);
					return novaSessao.getId();
				} else {
					throw new Exception("Login inválido");
				}
			}
		}
		throw new Exception("Usuário inexistente");
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

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {

		if (atributo == null || atributo.equals("")) {
			throw new Exception("Atributo inválido");
		}

		if (login == null || login.equals("")) {
			throw new Exception("Login inválido");
		}

		Usuario u = buscaUsuario(login);

		if (u == null) {
			throw new Exception("Usuário inexistente");
		}

		if (atributo.equals("nome")) {
			return u.getNome();
		}
		if (atributo.equals("endereco")) {
			return u.getEndereco();
		}

		throw new Exception("Atributo inexistente");
	}

	private Usuario buscaUsuario(String login) {
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}

	public List<Integer> localizarCarona(String idSessao, String origem,
			String destino) throws Exception {

		if (origem == null || origem.equals("") || contemCharInvalidos(origem)) {
			throw new Exception("Origem inválida");
		}
		if (destino == null || destino.equals("")
				|| contemCharInvalidos(destino)) {
			throw new Exception("Destino inválido");
		}
		List<Integer> caronasLocalizadas = new LinkedList<Integer>();

		if (verificaSessao(idSessao)) {
			for (Carona c : caronas) {
				if (c.getOrigem().equals(origem)
						&& c.getDestino().equals(destino)) {
					// add em caronasLocalizadas
				}
			}
		}

		return caronasLocalizadas;
	}

	private static boolean contemCharInvalidos(String nome) {
		String[] palavra = nome.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%¨¨¨&*()+¹²³¢¬§=[{ª]}º;:>,</|/0123456789*-."
					.contains(palavra[i])) {
				return true;
			}
		}

		return false;
	}

	private boolean verificaSessao(String idSessao) {
		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				return true;
			}
		}

		return false;
	}

	public List<Carona> getCaronas() {
		return caronas;
	}

	public String cadastrarCarona(String idSessao, String origem,
			String destino, String data, String hora, int vagas)
			throws Exception {

		Carona novaCarona = new Carona(idSessao, origem, destino, data, hora,
				vagas);
		caronas.add(novaCarona);

		return novaCarona.getId();
	}

	public String getAtributoCarona(String idCarona, String atributo)
			throws Exception {

		if (idCarona == null || idCarona.equals("")) {
			throw new Exception("Identificador do carona é inválido");
		}

		if (atributo == null || atributo.equals("")) {
			throw new Exception("Atributo inválido");
		}

		for (Carona c : caronas) {
			if (c.getId().equals(idCarona)) {
				if (atributo.equals("origem")) {
					return c.getOrigem();
				}
				if (atributo.equals("destino")) {
					return c.getDestino();
				}
				if (atributo.equals("data")) {
					return c.getData();
				}
				if (atributo.equals("hora")) {
					return c.getHora();
				}
				if (atributo.equals("vagas")) {
					return "" + c.getVagas();
				} else {
					throw new Exception("Atributo inexistente");
				}
			} else {
				throw new Exception("Item inexistente");
			}
		}

		return null;

	}

	public String getTrajeto(String idCarona) throws Exception {

		if (idCarona == null) {
			throw new Exception("Trajeto Inválida");

		}

		String trajeto = null;

		try {
			Carona c = localizaCarona(idCarona);

			trajeto = c.getOrigem() + " - " + c.getDestino();

		} catch (Exception e) {
			throw new Exception("Trajeto Inexistente");
		}

		return trajeto;

	}

	public String getCarona(String idCarona) throws Exception {

		if (idCarona == null) {
			throw new Exception("Carona Inválida");
		}
		Carona c = localizaCarona(idCarona);

		String carona = c.getOrigem() + " para " + c.getDestino() + ", no dia "
				+ c.getData() + ", as " + c.getHora();

		return carona;
	}

	private Carona localizaCarona(String idCarona) throws Exception {

		if (idCarona == null || idCarona == "") {
			throw new Exception("Carona Inválida");
		}

		for (Carona c : caronas) {
			if (c.getId().equals(idCarona)) {
				return c;
			}
		}

		throw new Exception("Carona Inexistente");
	}

	public static void main(String[] args) throws Exception {
		Sistema sis = new Sistema();

		sis.criarUsuario("mark", "123456", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
		System.out.println(sis.buscaUsuario("mark").getNome());

		String sessaoMark = sis.abrirSessao("mark", "123456");
		System.out.println("sessao mark: " + sessaoMark);

		sis.cadastrarCarona(sessaoMark, "Campina Grande", "Joao Pessoa",
				"06/04/2012", "04h50", 2);

	}

}
