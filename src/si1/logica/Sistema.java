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

		try {
			Usuario novoUsuario = new Usuario(login, senha, nome, endereco,
					email);

			usuarios.add(novoUsuario);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

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

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {

		if (atributo == null || atributo.equals("")) {
			throw new Exception("atributo inexistente");
		}
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

	public String localizarCarona(String idSessao, String origem,
			String destino) {

		String caronasLocalizadas = "{";

		if (verificaSessao(idSessao)) {
			for (Carona c : caronas) {
				if (c.getOrigem().equals(origem)
						&& c.getDestino().equals(destino)) {
					caronasLocalizadas += c.getId() + ", ";
				}
			}
		}

		caronasLocalizadas = caronasLocalizadas.substring(0, caronasLocalizadas.length() - 3) + "}";
		
		return caronasLocalizadas;
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
				}
			}
		}

		return null;
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

	public String getTrajeto(String idCarona) throws Exception {

		Carona c = localizaCarona(idCarona);

		String trajeto = c.getOrigem() + " - " + c.getDestino();

		return trajeto;
	}

	public String getCarona(String idCarona) throws Exception {
		Carona c = localizaCarona(idCarona);
		
		String carona = c.getOrigem() + " para " +c.getDestino()+ ", no dia " +c.getData() + ", as " + c.getHora();
		
		return carona;
	}

	private Carona localizaCarona(String idCarona) throws Exception {
		for (Carona c : caronas) {
			if (c.getId().equals(idCarona)) {
				return c;
			}
		}

		throw new Exception("Carona nao encontrada");
	}
}
