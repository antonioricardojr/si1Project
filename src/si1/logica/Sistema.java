package si1.logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

	public Usuario criarUsuario(String login, String senha, String nome,
			String endereco, String email) throws Exception {

		if (login == null || login.equals("") || senha == null) {
			throw new Exception("Login inválido");
		}

		if (nome == null || nome.equals("")) {
			throw new Exception("Nome inválido");
		}

		Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);

		usuarios.add(novoUsuario);

		return novoUsuario;
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

	public String encerrarSessao(String login) throws Exception {

		if (login == null || login.equals("")) {
			throw new Exception("Login inválido");
		}

		for (Sessao s : sessoes) {
			if (s.getLogin().equals(login)) {
				sessoes.remove(s);
				return login;
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

	public String localizarCarona(String idSessao, String origem, String destino)
			throws Exception {

		if (origem == null || contemCharInvalidos(origem)) {
			throw new Exception("Origem inválida");
		}
		if (destino == null || contemCharInvalidos(destino)) {
			throw new Exception("Destino inválido");
		}
		List<String> caronasLocalizadas = new LinkedList<String>();

		if (verificaSessao(idSessao)) {

			if (!origem.equals("") && !destino.equals("")) {
				for (Carona c : caronas) {
					if (c.getOrigem().equals(origem)
							&& c.getDestino().equals(destino)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			} else if (!origem.equals("") && destino.equals("")) {
				for (Carona c : caronas) {
					if (c.getOrigem().equals(origem)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}

			else if (origem.equals("") && !destino.equals("")) {
				for (Carona c : caronas) {
					if (c.getDestino().equals(destino)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}

			else if (origem.equals("") && destino.equals("")) {
				for (Carona c : caronas) {
					caronasLocalizadas.add(c.getId());
				}
			}

		}

		return caronasLocalizadas.toString().replace("[", "{")
				.replace("]", "}").replace(" ", "");
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
			String destino, String data, String hora, Object vagas)
			throws Exception {

		if (idSessao == null || idSessao.equals("")) {
			throw new Exception("Sessão inválida");
		}

		if (!verificaSessao(idSessao)) {
			throw new Exception("Sessão inexistente");
		}

		Sessao sessao = getSessao(idSessao);

		Usuario usuario = buscaUsuario(sessao.getLogin());
		Carona novaCarona = new Carona(origem, destino, data, hora, vagas,
				usuario);
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
			}
		}
		throw new Exception("Item inexistente");

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

	public Sessao getSessao(String idSessao) {

		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				return s;
			}
		}
		return null;

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

	public String sugerirPontoEncontro(String idSessao, String idCarona,
			String pontos) throws Exception {

		Carona carona = localizaCarona(idCarona);
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();
		carona.setPontos(id, pontos);

		return id;
	}

	public String responderSugestaoPontoEncontro(String idSessao,
			String idCarona, String idSugestao, String pontos) throws Exception {

		Carona carona = localizaCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (pontos.equals("") || pontos.equals(null)) {
			throw new Exception("Ponto Inválido");

		} else if (sessao.getLogin().equals(carona.getCriador().getLogin())) {
			carona.setPontos(idSugestao, pontos);
		}

		return pontos;
	}

	public String solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto) throws Exception {

		
		
		Carona carona = localizaCarona(idCarona);
		Sessao sessao = getSessao(idSessao);
		
	
		
		Usuario usuario = buscaUsuario(sessao.getLogin());
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();
		Solicitacao sol = new Solicitacao(id, usuario, carona, ponto);
		carona.addSolicitacao(sol);

		return id;

	}

	public String solicitarVaga(String idSessao, String idCarona)
			throws Exception {

		return solicitarVagaPontoEncontro(idSessao, idCarona, "Qualquer");

	}

	public String getAtributoSolicitacao(String idSolicitacao, String atributo)
			throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);

		if (atributo.equals("origem")) {
			return solicitacao.getCarona().getOrigem();
		} else if (atributo.equals("destino")) {
			return solicitacao.getCarona().getDestino();
		} else if (atributo.equals("Dono da carona")) {
			return solicitacao.getCarona().getCriador().getNome();
		} else if (atributo.equals("Dono da solicitacao")) {
			return solicitacao.getSolicitador().getNome();
		} else if (atributo.equals("Ponto de Encontro")) {
			return solicitacao.getPonto();
		}

		return solicitacao.toString();
	}

	private Solicitacao getSolicitacao(String idSolicitacao) throws Exception {

		for (Carona c : caronas) {
			List<Solicitacao> solicitacoes = c.getSolicitacoes();
			for (Solicitacao sol : solicitacoes) {
				if (idSolicitacao.equals(sol.getId())) {
					return sol;
				}
			}
		}

		throw new Exception("Solicitação inexistente");
	}

	public String aceitarSolicitacaoPontoEncontro(String idSessao,
			String idSolicitacao) throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		Sessao sessao = getSessao(idSessao);
		Carona carona = localizaCarona(solicitacao.getCarona().getId());

		if (sessao.getLogin().equals(
				solicitacao.getCarona().getCriador().getLogin())) {
			caronas.remove(carona);
			carona.addCaroneiro(solicitacao.getSolicitador());
			carona.setVagas(carona.getVagas() - 1);
			caronas.add(carona);
			carona.removeSolicitacao(solicitacao);
		}

		return solicitacao.getId();
	}

	public String aceitarSolicitacao(String idSessao, String idSolicitacao)
			throws Exception {

		return aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}

	public String desistirRequisicao(String idSessao, String idCarona,
			String idSugestao) throws Exception {

		Carona carona = localizaCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (sessao.getLogin().equals(carona.getCriador().getLogin())) {
			carona.removeSugestao(idSugestao);
		}

		return idSugestao;
	}

	public static void main(String[] args) throws Exception {
		Sistema sis = new Sistema();

		sis.criarUsuario("mark", "123456", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
		System.out.println(sis.buscaUsuario("mark").getNome());

		String sessaoMark = sis.abrirSessao("mark", "123456");
		System.out.println("sessao mark: " + sessaoMark);

		String id = sis.cadastrarCarona(sessaoMark, "Campina Grande",
				"Joao Pessoa", "06/04/2012", "04:50", 2);

		String idSol = sis.solicitarVagaPontoEncontro(sessaoMark, id,
				"Aqui mermo");
		System.out.println(sis.getAtributoSolicitacao(idSol, "origem"));
	}

}
