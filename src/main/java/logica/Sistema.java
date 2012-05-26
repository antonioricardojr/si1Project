package logica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import excecoes.AtributoInvalidoException;
import excecoes.CaronaInvalidaException;
import excecoes.DestinoInvalidoException;
import excecoes.ItemInexistenteException;
import excecoes.OpcaoInvalidaException;
import excecoes.OrigemInvalidaException;
import excecoes.SessaoInvalidaException;
import excecoes.VagaInvalidaException;
import excecoes.XMLNaoGeradaException;

import xml.FactoryXml;
import xml.GravaXml;
import xml.Xml;
import xml.leXml;

/**
 * Classe que representar� as chamadas do sistema.
 * 
 */

public class Sistema {

	private List<Usuario> usuarios;
	private List<Sessao> sessoes;
	private List<Carona> caronas;
	private Xml xmlCreatorUsuarios;
	private Xml xmlCreatorCaronas;
	private Xml xmlCreatorSistema;
	
	

	/**
	 * Construtor da classe Sistema
	 */
	public Sistema() {
		this.usuarios = new ArrayList<Usuario>();
		this.sessoes = new ArrayList<Sessao>();
		this.caronas = new ArrayList<Carona>();
		this.xmlCreatorUsuarios = new FactoryXml("Xml Usuarios do sistema");
		this.xmlCreatorCaronas = new FactoryXml("Xml Caronas do sistema");
		this.xmlCreatorSistema = new FactoryXml("Xml Sistema");
	}

	/**
	 * M�todo que retorna lista com usu�rios
	 * @return usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * M�todo que retorna um usu�rio a partir de seu login
	 * @param login
	 * @return usuario
	 */
	public Usuario getUsuario(String login) {
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}
	
	/**
	 * M�todo que cria usu�rio e o adiciona a lista de usu�rios
	 * @param login
	 * @param senha
	 * @param nome
	 * @param endereco
	 * @param email
	 * @return novoUsuario
	 * @throws Exception
	 */
	public Usuario criarUsuario(String login, String senha, String nome,
			String endereco, String email) throws Exception {

		if (login == null || login.equals("") || senha == null) {
			throw new AtributoInvalidoException("Login inv�lido");
		}

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				throw new AtributoInvalidoException("J� existe um usu�rio com este login");
			}
			if (u.getEmail().equals(email)) {
				throw new AtributoInvalidoException("J� existe um usu�rio com este email");
			}
		}

		if (nome == null || nome.equals("")) {
			throw new AtributoInvalidoException("Nome inv�lido");
		}

		Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);

		usuarios.add(novoUsuario);

		return novoUsuario;
	}

	/**
	 * M�todo para criar sess�o e adiciona-la a lista de sess�es
	 * @param login
	 * @param senha
	 * @return sessao
	 * @throws Exception
	 */
	public Sessao abrirSessao(String login, String senha) throws Exception {
		if (login == null || login.equals("")) {
			throw new AtributoInvalidoException("Login inv�lido");
		}

		Sessao novaSessao = null;

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				if (verificaSenha(u,senha)) {
					novaSessao = new Sessao(login);
					sessoes.add(novaSessao);
					return novaSessao;
				} else {
					throw new AtributoInvalidoException("Login inv�lido");
				}
			}
		}
		throw new ItemInexistenteException("Usu�rio inexistente");
	}

	/**
	 * M�todo que remove a sess�o com o login passado por par�metro da lista de sess�es
	 * @param login
	 * @return sessaoRemovida
	 * @throws Exception
	 */
	public Sessao encerrarSessao(String login) throws Exception {

		if (login == null || login.equals("")) {
			throw new AtributoInvalidoException("Login inv�lido");
		}

		for (Sessao s : sessoes) {
			if (s.getLogin().equals(login)) {
				sessoes.remove(s);
				return s;
			}
		}
		throw new ItemInexistenteException("Usu�rio inexistente");
	}
	/**
	 * M�todo interno para verificar se a senha � valida para
	 * @param senha
	 * @return
	 */
	private boolean verificaSenha(Usuario usuario, String senha) {

		for (Usuario u : usuarios) {
			if (u.equals(usuario)) {
				if(u.getSenha().equals(senha)){
					return true;
				}				
			}
		}

		return false;
	}
	
	/**
	 * M�todo que retorna lista com as sess�es abertas
	 * @return sessoes
	 */
	public List<Sessao> getSessoes() {
		return sessoes;
	}

	/**
	 * M�todo que retorna o atributo requerido pelo usu�rio se for v�lido
	 * @param login
	 * @param atributo
	 * @return atributo
	 * @throws Exception
	 */
	public String getAtributoUsuario(String login, String atributo)
			throws Exception {

		if (atributo == null || atributo.equals("")) {
			throw new AtributoInvalidoException("Atributo inv�lido");
		}

		if (login == null || login.equals("")) {
			throw new AtributoInvalidoException("Login inv�lido");
		}

		Usuario u = getUsuario(login);

		if (u == null) {
			throw new AtributoInvalidoException("Usu�rio inexistente");
		}

		if (atributo.equals("nome")) {
			return u.getNome();
		}
		if (atributo.equals("endereco")) {
			return u.getEndereco();
		}

		throw new AtributoInvalidoException("Atributo inexistente");
	}

	/**
	 * M�todo que retorna carona se esta existir
	 * @param idSessao
	 * @param origem
	 * @param destino
	 * @return caronasLocalizadas
	 * @throws Exception
	 */
	public String localizarCarona(String idSessao, String origem, String destino)
			throws Exception {

		if (origem == null || contemCharInvalidos(origem)) {
			throw new OrigemInvalidaException();
		}
		if (destino == null || contemCharInvalidos(destino)) {
			throw new DestinoInvalidoException();
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

	/**
	 * M�todo interno que verifica se nome cont�m caracteres inv�lidos
	 * @param nome
	 * @return true se conter, false se n�o
	 */
	private static boolean contemCharInvalidos(String nome) {
		String[] palavra = nome.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%���&*()+������=[{�]}�;:>,</|/0123456789*-."
					.contains(palavra[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * M�todo interno que verifica se sess�o est� aberta
	 * @param idSessao
	 * @return true se estiver, false se n�o
	 */
	private boolean verificaSessao(String idSessao) {
		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * M�todo que retorna lista com caronas
	 * @return caronas
	 */
	public List<Carona> getCaronas() {
		return caronas;
	}

	/**
	 * M�todo que cadastra e adiciona caronas a lista de caronas
	 * @param idSessao
	 * @param origem
	 * @param destino
	 * @param data
	 * @param hora
	 * @param vagas
	 * @return caronaCadastrada
	 * @throws Exception
	 */
	public Carona cadastrarCarona(String idSessao, String origem,
			String destino, String data, String hora, String vagas)
			throws Exception {

		int intVagas;

		try {
			intVagas = Integer.parseInt(vagas);
		} catch (Exception e) {
			throw new VagaInvalidaException();
		}

		if (idSessao == null || idSessao.equals("")) {
			throw new SessaoInvalidaException("Sess�o inv�lida");
		}

		if (!verificaSessao(idSessao)) {
			throw new SessaoInvalidaException("Sess�o inexistente");
		}

		Sessao sessao = getSessao(idSessao);

		Usuario usuario = getUsuario(sessao.getLogin());

		Carona caronaCadastrada = new Carona(origem, destino, data, hora, intVagas,
				usuario.getLogin());
		caronas.add(caronaCadastrada);
		usuario.adicionaCaronaOferecida(caronaCadastrada.getId());
		usuario.adicionaCarona(caronaCadastrada.getId());

		return caronaCadastrada;
	}

	/**
	 * M�todo que retorna string de atributo de carona se esta existir
	 * @param idCarona
	 * @param atributo
	 * @return atributo
	 * @throws Exception
	 */
	public String getAtributoCarona(String idCarona, String atributo)
			throws Exception {

		if (idCarona == null || idCarona.equals("")) {
			throw new AtributoInvalidoException("Identificador do carona � inv�lido");
		}

		if (atributo == null || atributo.equals("")) {
			throw new AtributoInvalidoException("Atributo inv�lido");
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
				}
				if (atributo.equals("Ponto de Encontro")) {
					return "" + c.getPontoDeEncontro();

				} else {
					throw new AtributoInvalidoException("Atributo inexistente");
				}
			}
		}
		throw new ItemInexistenteException("Item inexistente");

	}

	/**
	 * M�todo que retorna string com trajeto a partir da id da carona
	 * @param idCarona
	 * @return trajeto
	 * @throws Exception
	 */
	public String getTrajeto(String idCarona) throws Exception {

		if (idCarona == null) {
			throw new AtributoInvalidoException("Trajeto Inv�lido");

		}

		String trajeto = null;

		try {
			Carona c = getCarona(idCarona);

			trajeto = c.getOrigem() + " - " + c.getDestino();

		} catch (Exception e) {
			throw new ItemInexistenteException("Trajeto Inexistente");
		}

		return trajeto;

	}

	/**
	 * M�todo que retorna carona a partir da id
	 * @param idCarona
	 * @return carona
	 * @throws Exception
	 */
	public Carona getCarona(String idCarona) throws Exception {

		if (idCarona == null || idCarona == "") {
			throw new CaronaInvalidaException("Carona Inv�lida");
		}

		for (Carona c : caronas) {
			if (c.getId().equals(idCarona)) {
				return c;
			}
		}

		throw new CaronaInvalidaException("Carona Inexistente");
	}

	/**
	 * M�todo que retorna sess�o a partir de sua id
	 * @param idSessao
	 * @return sessao
	 */
	public Sessao getSessao(String idSessao) {

		for (Sessao sessao : sessoes) {
			if (sessao.getId().equals(idSessao)) {
				return sessao;
			}
		}
		return null;

	}

	/**
	 * M�todo que cadastra sugestao de ponto de encontro
	 * @param idSessao
	 * @param idCarona
	 * @param pontos
	 * @return id
	 * @throws Exception
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona,
			String pontos) throws Exception {

		Carona carona = getCarona(idCarona);
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();
		carona.setPontosSugeridos(id, pontos);

		return id;
	}

	/**
	 * M�todo para responder sugestao de encontro para a carona de tal id passado por par�metro
	 * @param idSessao
	 * @param idCarona
	 * @param idSugestao
	 * @param pontos
	 * @return pontos
	 * @throws Exception
	 */
	public String responderSugestaoPontoEncontro(String idSessao,
			String idCarona, String idSugestao, String pontos) throws Exception {

		Carona carona = getCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (pontos.equals("") || pontos.equals(null)) {
			throw new AtributoInvalidoException("Ponto Inv�lido");

		} else if (sessao.getLogin().equals(carona.getCriador())) {
			carona.setPontosSugeridos(idSugestao, pontos);
		}

		return pontos;
	}

	/**
	 * M�todo para fazer solicita��o de vaga passando ponto de encontro
	 * @param idSessao
	 * @param idCarona
	 * @param ponto
	 * @return solicitacao
	 * @throws Exception
	 */
	public Solicitacao solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto) throws Exception {

		if (idSessao == null || idCarona == null || ponto == null) {
			throw new AtributoInvalidoException("Ponto Inv�lido");
		}

		Carona carona = getCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		Usuario usuario = getUsuario(sessao.getLogin());
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();

		Solicitacao solicitacao = new Solicitacao(id, usuario.getLogin(), carona.getId(), ponto);
		carona.addSolicitacao(solicitacao);
		return solicitacao;

	}

	/**
	 * M�todo para fazer solicita��o de vaga sem passar ponto de encontro
	 * @param idSessao
	 * @param idCarona
	 * @return solicitacao
	 * @throws Exception
	 */
	public Solicitacao solicitarVaga(String idSessao, String idCarona)
			throws Exception {

		return solicitarVagaPontoEncontro(idSessao, idCarona, "Qualquer");

	}

	/**
	 * M�todo para retornar atributo de solicita��o
	 * @param idSolicitacao
	 * @param atributo
	 * @return atributo
	 * @throws Exception
	 */
	public String getAtributoSolicitacao(String idSolicitacao, String atributo)
			throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);

		if (atributo.equals("origem")) {
			return getCarona(solicitacao.getCarona()).getOrigem();
		} else if (atributo.equals("destino")) {
			return getCarona(solicitacao.getCarona()).getDestino();
		} else if (atributo.equals("Dono da carona")) {
			return getUsuario(getCarona(solicitacao.getCarona()).getCriador()).getNome();
		} else if (atributo.equals("Dono da solicitacao")) {
			return getUsuario(solicitacao.getSolicitador()).getNome();
		} else if (atributo.equals("Ponto de Encontro")) {
			return solicitacao.getPonto();
		}

		return solicitacao.toString();
	}

	/**
	 * M�todo que retorna solicita��o a partir de sua id
	 * @param idSolicitacao
	 * @return solicitacao
	 * @throws Exception
	 */
	private Solicitacao getSolicitacao(String idSolicitacao) throws Exception {

		for (Carona c : this.caronas) {
			List<Solicitacao> solicitacoes = c.getSolicitacoes();
			for (Solicitacao solicitacao : solicitacoes) {
				if (idSolicitacao.equals(solicitacao.getId())) {
					return solicitacao;
				}
			}
		}

		throw new ItemInexistenteException("Solicita��o inexistente");
	}

	/**
	 * M�todo para aceitar solicita��o dado um ponto de encontro
	 * @param idSessao
	 * @param idSolicitacao
	 * @return solicitacao
	 * @throws Exception
	 */
	public Solicitacao aceitarSolicitacaoPontoEncontro(String idSessao,
			String idSolicitacao) throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		// Sessao sessao = getSessao(idSessao);
		Carona carona = getCarona(solicitacao.getCarona());

		// if (sessao.getLogin().equals(
		// solicitacao.getCarona().getCriador().getLogin())) {
		//caronas.remove(carona);
		carona.addCaroneiro(solicitacao);
		carona.setVagas(carona.getVagas() - 1);
		carona.removeSolicitacao(solicitacao);
		carona.setPonto(solicitacao.getPonto());
		getUsuario(solicitacao.getSolicitador()).adicionaCaronaComoCaroneiro(carona.getId());
		caronas.add(carona);

		// }

		return solicitacao;
	}

	/**
	 * M�todo para aceitar solicita��o 
	 * @param idSessao
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Solicitacao aceitarSolicitacao(String idSessao, String idSolicitacao)
			throws Exception {

		return aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}

	/**
	 * M�todo para desistir de sugest�o
	 * @param idSessao
	 * @param idCarona
	 * @param idSugestao
	 * @return idSugestao
	 * @throws Exception
	 */
	public String desistirRequisicao(String idSessao, String idCarona,
			String idSugestao) throws Exception {

		Carona carona = getCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (sessao.getLogin().equals(carona.getCriador())) {
			carona.removeSugestao(idSugestao);
		}

		return idSugestao;
	}

	/**
	 * M�todo para rejeitar solicita��o
	 * @param idSessao
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Solicitacao rejeitarSolicitacao(String idSessao, String idSolicitacao)
			throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		// Sessao sessao = getSessao(idSessao);
		Carona carona = getCarona(solicitacao.getCarona());

		// if (sessao.getLogin().equals(
		// solicitacao.getCarona().getCriador().getLogin())) {
		caronas.remove(carona);
		carona.removeSolicitacao(solicitacao);
		caronas.add(carona);
		// }

		return solicitacao;
	}

	/**
	 * M�todo para visualizar perfil de um usu�rio a partir de seu login
	 * @param idSessao
	 * @param login
	 * @return visualizadorDePerfil
	 * @throws Exception
	 */
	public VisualizadorDePerfil visualizarPerfil(String idSessao, String login)
			throws Exception {

		VisualizadorDePerfil visualizadorDePerfil = null;

		if (verificaSessao(idSessao)) {
			Usuario usuario = getUsuario(login);
			if (usuario == null) {
				throw new AtributoInvalidoException("Login inv�lido");
			}
			List<Carona> caronasLista = new ArrayList<Carona>();
			for(String idCarona : usuario.getCaronas()){
				caronasLista.add(getCarona(idCarona));
			}
			
			visualizadorDePerfil = new VisualizadorDePerfil(usuario, caronasLista);
		}
		return visualizadorDePerfil;
	}

	/**
	 * M�todo para retornar atributo de perfil de um usuario a partir de seu login
	 * @param login
	 * @param atributo
	 * @return atributo
	 * @throws Exception
	 */
	public String getAtributoPerfil(String login, String atributo)
			throws Exception {

		Usuario usuario = getUsuario(login);

		VisualizadorDePerfil vp;
		if (usuario != null) {
			List<Carona> caronasLista = new ArrayList<Carona>();
			for(String idCarona : usuario.getCaronas()){
				caronasLista.add(getCarona(idCarona));
			}
			
			vp = new VisualizadorDePerfil(usuario, caronasLista);
		} else {
			throw new Exception();
		}

		if (atributo.equals("nome")) {
			return vp.getNome();
		}

		if (atributo.equals("endereco")) {
			return vp.getEndereco();
		}

		if (atributo.equals("email")) {
			return vp.getEmail();
		}

		if (atributo.equals("historico de caronas")) {
			return vp.getHistoricoDeCaronas();
		}

		if (atributo.equals("historico de vagas em caronas")) {
			return vp.getHistoricoDeVagasEmCaronas();
		}

		if (atributo.equals("caronas seguras e tranquilas")) {
			return vp.getCaronasSegurasETranquilas();
		}
		if (atributo.equals("caronas que n�o funcionaram")) {
			return vp.getCaronasQueNaoFuncionaram();
		}

		if (atributo.equals("faltas em vagas de caronas")) {
			return vp.getFaltasEmVagasDeCaronas();
		}

		if (atributo.equals("presen�as em vagas de caronas")) {
			return vp.getPresencasEmVagasDeCaronas();
		}
		throw new AtributoInvalidoException("Atributo inexistente");

	}

	/**
	 * M�todo que retorna carona de usu�rio a partir do index
	 * @param idSessao
	 * @param indexCarona
	 * @return carona
	 * @throws Exception
	 */
	public Carona getCaronaUsuario(String idSessao, int indexCarona) throws Exception {

		Usuario u = null;
		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = getUsuario(s.getLogin());
			}
		}

		Carona carona = null;

		if (u != null) {

			int i = indexCarona - 1;
			if (i >= 0 && i < u.getCaronas().size()) {
				carona = getCarona(u.getCaronas().get(i));
			}
		}

		return carona;
	}

	/**
	 * M�todo que retorna string com todas as caronas de um usu�rio a partir de sue login
	 * @param idSessao
	 * @return todasAsCaronas
	 * @throws Exception
	 */
	public String getTodasCaronasUsuario(String idSessao) throws Exception {

		Usuario u = null;

		List<String> todasAsCaronas = new ArrayList<String>();

		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = getUsuario(s.getLogin());
			}
		}

		for (String c : u.getCaronas()) {
			todasAsCaronas.add(getCarona(c).getId());
		}

		String saida = todasAsCaronas.toString().replace("[", "{")
				.replace("]", "}").replace(" ", "");

		return saida;

	}

	/**
	 * M�todo que retorna ids das solicita��es confirmadas
	 * @param idSessao
	 * @param idCarona
	 * @return ids
	 * @throws Exception
	 */
	public String getSolicitacoesConfirmadas(String idSessao, String idCarona)
			throws Exception {

		Carona carona = getCarona(idCarona);
		String res = "{";
		for (Solicitacao sol : carona.getCaroneiros()) {
			res += sol.getId();

		}
		res += "}";

		return res;
	}
	/**
	 * M�todo que retorna as ids das solicita��es pendentes
	 * @param idCarona
	 * @return ids
 	* @throws Exception
 	*/
	public String getSolicitacoesPendentes(String idCarona) throws Exception {

		Carona carona = getCarona(idCarona);
		String res = "{";
		for (Solicitacao sol : carona.getSolicitacoes()) {
			res += sol.getId();

		}
		res += "}";

		return res;
	}

	/**
	 * M�todo que retorna pontos sugeridos
	 * @param idSessao
	 * @param idCarona
	 * @return
	 * @throws Exception
	 */
	public String getPontosSugeridos(String idSessao, String idCarona)
			throws Exception {

		Carona carona = getCarona(idCarona);
		String res = "";
		Collection<String> pontos = carona.getPontosSugeridosValues();
		for (String ponto : pontos) {
			res += ponto;

		}
		res += "";

		return res;
	}

	/**
	 * M�todo que adiciona review a carona
	 * @param idSessao
	 * @param idCarona
	 * @param review
	 * @return true se foi feita a review corretamente, false se nao
	 * @throws Exception
	 */
	public boolean reviewCarona(String idSessao, String idCarona, String review) throws Exception {

		Usuario u;

		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = getUsuario(s.getLogin());
				for (Carona c : caronas) {
					if (c.getId().equals(idCarona)) {

						GeradorDeID gerador = new GeradorDeID();
						String id = gerador.geraId();

						Review novoReview = new Review(id, u.getLogin(), c.getId(), review);
						c.addReview(novoReview);
						return true;

					}
				}

			}
		}

		return false;

	}

	/**
	 * M�todo que adiciona review sobre vaga em carona
	 * @param idSessao
	 * @param idCarona
	 * @param loginCaroneiro
	 * @param review1
	 * @return
	 * @throws Exception
	 */
	public Review reviewVagaEmCarona(String idSessao, String idCarona,
			String loginCaroneiro, String review1) throws Exception {
		
		if(review1.equals("faltou") || review1.equals("n�o faltou")){
			Sessao sessao = getSessao(idSessao);
			Usuario user = getUsuario(sessao.getLogin());
			GeradorDeID gerador = new GeradorDeID();
			String id = gerador.geraId();
			Usuario caroneiro = getUsuario(loginCaroneiro);
			for (String c : caroneiro.getCaronasComoCaroneiro()) {
				if (c.equals(idCarona)) {
					Carona carona = this.getCarona(c);
					this.caronas.remove(carona);
					Review review = new Review(id, caroneiro.getLogin(), c, review1);
					carona.addReview(review);
					this.caronas.add(carona);
					return review;
				}
			}
		}
			throw new OpcaoInvalidaException();
	}
	
	/**
	 * �todo que fecha o sistema
	 * @throws IOException
	 * @throws XMLNaoGeradaException
	 */
	public void encerrarSistema() throws IOException, XMLNaoGeradaException{
		this.xmlCreatorUsuarios = new FactoryXml("Xml Usuarios do sistema");
		this.xmlCreatorCaronas = new FactoryXml("Xml Caronas do sistema");
		// Gera XML de todos os usuarios do sistema
		for(Usuario u : usuarios){
			u.geraXml();
			this.xmlCreatorUsuarios.geraXML(u.getXml());
		}
		
		for(Carona c : caronas){
			c.geraXml();
			this.xmlCreatorCaronas.geraXML(c.getXml());
		}
		
		this.xmlCreatorSistema.geraXML(this.xmlCreatorUsuarios.getRaiz());
		this.xmlCreatorSistema.geraXML(this.xmlCreatorCaronas.getRaiz());
		GravaXml gravador = new GravaXml(this.xmlCreatorSistema.getRaiz());
		gravador.gravar("arquivo.xml");
		
		
		
	}

	/**
	 * M�todo para apagar informa��es gravadas do sistema
	 */
	public void zerarSistema() {
		File f = new File("arquivo.xml");
		if(f.exists()){	
			
			f.delete();	
		}
		
	}

	/**
	 * M�todo para reiniciar o sistema
	 * @throws Exception
	 */
	public void reiniciarSistema() throws Exception {
		leXml leitor = new leXml("arquivo.xml");
		this.usuarios = leitor.getUsuarios();	
		this.caronas = leitor.getCaronas();
	}
	
	
	public boolean enviarEmail(String idSessao, String destino, String message){
		Sessao sessao = getSessao(idSessao);

		
		return enviaEmail(sessao.getLogin(), destino, message);
	}
	
	
	
	private boolean enviaEmail(String remetente, String destinatario, String mensagem) {
		//TODO: fazer a mensagem ser enviada ao destinat�rio.
		
		return false;
	}

	public static void main(String[] args) throws Exception {
		//criarUsuario login="mark" senha="m@rk" nome="Mark Zuckerberg" endereco="Palo Alto, California" email="mark@facebook.com"
		//criarUsuario login="bill" senha="bilz@o" nome="Bill Clinton" endereco="Hollywood, California" email="bill@gmail.com"
		
		System.out.println("Cria Sistema");
		Sistema Si1 = new Sistema();
		System.out.println("--------------------------------------------");
		System.out.println("Cria dois Usuarios, mark e bill");
		System.out.println(Si1.criarUsuario("mark", "m@rk", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com").getNome());
		System.out.println(Si1.criarUsuario("bill", "bilz@o", "Bill Clinton", "Hollywood, California", "bill@gmail.com").getNome());;
		
		System.out.println("--------------------------------------------");
		System.out.println("Verificar se existe os usu�rios adicionados.");
		System.out.println(Si1.getUsuario("mark").getEmail());
		System.out.println(Si1.getUsuario("bill").getEmail());
		
		//#Iniciar sess�o.
		//sessaoMark=abrirSessao login="mark" senha="m@rk"
		System.out.println("Inicia sessao para mark");
		
		Sessao sessaoMark = Si1.abrirSessao("mark", "m@rk");
		System.out.println(sessaoMark.getId());
		
		//#Cadastrar caronas.
		//carona4ID=cadastrarCarona idSessao=${sessaoMark} origem="Campina Grande" destino="Jo�o Pessoa" data="02/06/2012" hora="12:00" vagas=1
		//carona5ID=cadastrarCarona idSessao=${sessaoMark} origem="Campina Grande" destino="Jo�o Pessoa" data="04/06/2012" hora="16:00" vagas=2
		System.out.println("---------------------------------------------------------");
		System.out.println("cadastra duas caronas na sessaoMark");
		
		Carona carona4 = Si1.cadastrarCarona(sessaoMark.getId(), "Campina Grande", "Jo�o Pessoa", "02/06/2012" ,"12:00", "1");
		Carona carona5 = Si1.cadastrarCarona(sessaoMark.getId(), "Campina Grande", "Jo�o Pessoa", "04/06/2012" ,"16:00", "2");
		System.out.println(carona4.getCriador() + ", " + carona4.getId());
		System.out.println(carona5.getCriador() + ", " + carona5.getId());
		
		//Verifica se as caronas existem na lista de caronas do Sistema.
		System.out.println("---------------------------------------------------------");
		System.out.println("Verifica se as caronas existem na lista de caronas do Sistema");
		System.out.println(Si1.getCarona(carona4.getId()).getCriador());
		System.out.println(Si1.getCarona(carona5.getId()).getCriador());
		
		
		
		//abrir uma sessao para bill
		System.out.println("---------------------------");
		System.out.println("Abrir uma sessao para Bill");
		Sessao sessaoBill = Si1.abrirSessao("bill", "bilz@o");
		System.out.println(sessaoBill.getId());
		
		//#Requisitar vaga na carona.
		//solicitacao1ID=solicitarVaga idSessao=${sessaoBill} idCarona=${carona4ID}
		//expect "Campina Grande" getAtributoSolicitacao idSolicitacao={solicitacao1ID} atributo="origem"
		System.out.println("------------------------");
		System.out.println("Requisitar vaga em uma carona oferecida por Mark");
		Solicitacao solicitacao4 = Si1.solicitarVaga(sessaoBill.getId(), carona4.getId());
		System.out.println("---------------------------------------");
		System.out.println("Buscar a origem da carona");
		System.out.println(Si1.getAtributoSolicitacao(solicitacao4.getId(), "origem"));;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
