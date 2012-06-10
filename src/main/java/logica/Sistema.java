package logica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import excecoes.AtributoInvalidoException;
import excecoes.CaronaInvalidaException;
import excecoes.DataInvalidaException;
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
 * Classe que representará as chamadas do sistema.
 * 
 */

public class Sistema {

	private List<Usuario> usuarios;
	private List<Sessao> sessoes;
	private List<CaronaAbstrata> caronas;
	private Xml xmlCreatorUsuarios;
	private Xml xmlCreatorCaronas;
	private Xml xmlCreatorSistema;
	private List<Interesse> interesses;
	
	

	/**
	 * Construtor da classe Sistema
	 */
	public Sistema() {
		this.usuarios = new ArrayList<Usuario>();
		this.sessoes = new ArrayList<Sessao>();
		this.caronas = new ArrayList<CaronaAbstrata>();
		this.setInteresses(new ArrayList<Interesse>());
		this.xmlCreatorUsuarios = new FactoryXml("Xml Usuarios do sistema");
		this.xmlCreatorCaronas = new FactoryXml("Xml Caronas do sistema");
		this.xmlCreatorSistema = new FactoryXml("Xml Sistema");
	}

	/**
	 * Método que retorna lista com usuários
	 * @return usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * Método que retorna um usuário a partir de seu login
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
	 * Método que cria usuário e o adiciona a lista de usuários
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
			throw new AtributoInvalidoException("Login inválido");
		}

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				throw new AtributoInvalidoException("Já existe um usuário com este login");
			}
			if (u.getEmail().equals(email)) {
				throw new AtributoInvalidoException("Já existe um usuário com este email");
			}
		}

		if (nome == null || nome.equals("")) {
			throw new AtributoInvalidoException("Nome inválido");
		}

		Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);
		usuarios.add(novoUsuario);

		return novoUsuario;
	}

	/**
	 * Método para criar sessão e adiciona-la a lista de sessões
	 * @param login
	 * @param senha
	 * @return sessao
	 * @throws Exception
	 */
	public Sessao abrirSessao(String login, String senha) throws Exception {
		if (login == null || login.equals("")) {
			throw new AtributoInvalidoException("Login inválido");
		}

		Sessao novaSessao = null;

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				if (verificaSenha(u,senha)) {
					novaSessao = new Sessao(login);
					sessoes.add(novaSessao);
					return novaSessao;
				} else {
					throw new AtributoInvalidoException("Login inválido");
				}
			}
		}
		throw new ItemInexistenteException("Usuário inexistente");
	}

	/**
	 * Método que remove a sessão com o login passado por parâmetro da lista de sessões
	 * @param login
	 * @return sessaoRemovida
	 * @throws Exception
	 */
	public Sessao encerrarSessao(String login) throws Exception {

		if (login == null || login.equals("")) {
			throw new AtributoInvalidoException("Login inválido");
		}

		for (Sessao s : sessoes) {
			if (s.getLogin().equals(login)) {
				sessoes.remove(s);
				return s;
			}
		}
		throw new ItemInexistenteException("Usuário inexistente");
	}
	/**
	 * Método interno para verificar se a senha é valida para
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
	 * Método que retorna lista com as sessões abertas
	 * @return sessoes
	 */
	public List<Sessao> getSessoes() {
		return sessoes;
	}

	/**
	 * Método que retorna o atributo requerido pelo usuário se for válido
	 * @param login
	 * @param atributo
	 * @return atributo
	 * @throws Exception
	 */
	public String getAtributoUsuario(String login, String atributo)
			throws Exception {

		if (atributo == null || atributo.equals("")) {
			throw new AtributoInvalidoException("Atributo inválido");
		}

		if (login == null || login.equals("")) {
			throw new AtributoInvalidoException("Login inválido");
		}

		Usuario u = getUsuario(login);

		if (u == null) {
			throw new AtributoInvalidoException("Usuário inexistente");
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
	 * Método que retorna carona se esta existir
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
				for (CaronaAbstrata c : caronas) {
					if (c.getOrigem().equals(origem)
							&& c.getDestino().equals(destino)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			} else if (!origem.equals("") && destino.equals("")) {
				for (CaronaAbstrata c : caronas) {
					if (c.getOrigem().equals(origem)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}

			else if (origem.equals("") && !destino.equals("")) {
				for (CaronaAbstrata c : caronas) {
					if (c.getDestino().equals(destino)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}

			else if (origem.equals("") && destino.equals("")) {
				for (CaronaAbstrata c : caronas) {
					caronasLocalizadas.add(c.getId());
				}
			}

		}

		return caronasLocalizadas.toString().replace("[", "{")
				.replace("]", "}").replace(" ", "");
	}

	/**
	 * Método que retorna carona municipal se esta existir
	 * @param idSessao
	 * @param origem
	 * @param destino
	 * @return caronasLocalizadas
	 * @throws Exception
	 */public String localizarCaronaMunicipal(String idSessao, String cidade, String origem, String destino)
			throws Exception {

		if (origem == null || contemCharInvalidos(origem)) {
			throw new OrigemInvalidaException();
		}
		if (destino == null || contemCharInvalidos(destino)) {
			throw new DestinoInvalidoException();
		}
		
		if (cidade == null || cidade.equals("")) {
			throw new ItemInexistenteException("Cidade inexistente");
		}
		
		List<String> caronasLocalizadas = new LinkedList<String>();

		if (verificaSessao(idSessao)) {
			List<CaronaMunicipal> caronasMunicipais = new ArrayList<CaronaMunicipal>();
			for (CaronaAbstrata c : caronas) {
				if(c instanceof CaronaMunicipal){
					caronasMunicipais.add((CaronaMunicipal)c);
				}
			}
			if (!origem.equals("") && !destino.equals("")) {
				for (CaronaMunicipal c : caronasMunicipais) {
					if (c.getOrigem().equals(origem)
							&& c.getDestino().equals(destino)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			} else if (!origem.equals("") && destino.equals("")) {
				for (CaronaMunicipal c : caronasMunicipais) {
					if (c.getOrigem().equals(origem)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}
			
			else if (origem.equals("") && destino.equals("") && !cidade.equals("")) {;
				for (CaronaMunicipal c : caronasMunicipais) {
					if (c.getCidade().equals(cidade)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}

			else if (origem.equals("") && !destino.equals("")) {
				for (CaronaAbstrata c : caronasMunicipais) {
					if (c.getDestino().equals(destino)) {
						caronasLocalizadas.add(c.getId());
					}
				}
			}

			else if (origem.equals("") && destino.equals("") && cidade.equals("")) {
				for (CaronaAbstrata c : caronasMunicipais) {
					caronasLocalizadas.add(c.getId());
				}
			}

		}

		return caronasLocalizadas.toString().replace("[", "{")
				.replace("]", "}").replace(" ", "");
	}
	
	/**
	 * Método interno que verifica se nome contém caracteres inválidos
	 * @param nome
	 * @return true se conter, false se não
	 */
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

	/**
	 * Método interno que verifica se sessão está aberta
	 * @param idSessao
	 * @return true se estiver, false se não
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
	 * Método que retorna lista com caronas
	 * @return caronas
	 */
	public List<CaronaAbstrata> getCaronas() {
		return caronas;
	}

	/**
	 * Método que cadastra e adiciona caronas a lista de caronas
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
			throw new SessaoInvalidaException("Sessão inválida");
		}

		if (!verificaSessao(idSessao)) {
			throw new SessaoInvalidaException("Sessão inexistente");
		}

		Sessao sessao = getSessao(idSessao);

		Usuario usuario = getUsuario(sessao.getLogin());

		Carona caronaCadastrada = new Carona(origem, destino, data, hora, intVagas,
				usuario.getLogin());
		caronas.add(caronaCadastrada);
		usuario.adicionaCaronaOferecida(caronaCadastrada.getId());
		usuario.adicionaCarona(caronaCadastrada.getId());
		this.avisaAosInteressados(caronaCadastrada);
		return caronaCadastrada;
	}
	
	/**
	 * Método que cadastra e adiciona caronas a lista de caronas
	 * @param idSessao
	 * @param origem
	 * @param destino
	 * @param data
	 * @param hora
	 * @param vagas
	 * @return caronaCadastrada
	 * @throws Exception
	 */
	public CaronaMunicipal cadastrarCaronaMunicipal(String idSessao, String origem,
			String destino, String cidade, String data, String hora, String vagas)
			throws Exception {
		int intVagas;

		try {
			intVagas = Integer.parseInt(vagas);
		} catch (Exception e) {
			throw new VagaInvalidaException();
		}

		if (idSessao == null || idSessao.equals("")) {
			throw new SessaoInvalidaException("Sessão inválida");
		}

		if (!verificaSessao(idSessao)) {
			throw new SessaoInvalidaException("Sessão inexistente");
		}

		Sessao sessao = getSessao(idSessao);

		Usuario usuario = getUsuario(sessao.getLogin());

		CaronaMunicipal caronaCadastrada = new CaronaMunicipal(origem, destino, cidade, data, hora, intVagas,
				usuario.getLogin());
		caronas.add(caronaCadastrada);
		usuario.adicionaCaronaOferecida(caronaCadastrada.getId());
		usuario.adicionaCarona(caronaCadastrada.getId());
		this.avisaAosInteressados(caronaCadastrada);
		return caronaCadastrada;
	}
	
	/**
	 * Método que avisa aos usuários sobre caronas de interesse
	 * @param carona
	 */
	private void avisaAosInteressados(CaronaAbstrata carona){
		
		for(Usuario u: usuarios){
			avisaSobreCaronaInteressada(u, carona);
		}
		
	}
	
	
	/**
	 * Método que retorna string de atributo de carona se esta existir
	 * @param idCarona
	 * @param atributo
	 * @return atributo
	 * @throws Exception
	 */
	public String getAtributoCarona(String idCarona, String atributo)
			throws Exception {

		if (idCarona == null || idCarona.equals("")) {
			throw new AtributoInvalidoException("Identificador do carona é inválido");
		}

		if (atributo == null || atributo.equals("")) {
			throw new AtributoInvalidoException("Atributo inválido");
		}

		for (CaronaAbstrata c : caronas) {
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

				}if (atributo.equals("ehMunicipal")) {
					if(c instanceof CaronaMunicipal){
						return ""+true;
					}
					return "" + false;

				} else {
					throw new AtributoInvalidoException("Atributo inexistente");
				}
			}
		}
		throw new ItemInexistenteException("Item inexistente");

	}

	/**
	 * Método que retorna string com trajeto a partir da id da carona
	 * @param idCarona
	 * @return trajeto
	 * @throws Exception
	 */
	public String getTrajeto(String idCarona) throws Exception {

		if (idCarona == null) {
			throw new AtributoInvalidoException("Trajeto Inválida");

		}

		String trajeto = null;

		try {
			CaronaAbstrata c = getCarona(idCarona);

			trajeto = c.getOrigem() + " - " + c.getDestino();

		} catch (Exception e) {
			throw new ItemInexistenteException("Trajeto Inexistente");
		}

		return trajeto;

	}

	/**
	 * Método que retorna carona a partir da id
	 * @param idCarona
	 * @return carona
	 * @throws Exception
	 */
	public CaronaAbstrata getCarona(String idCarona) throws Exception {

		if (idCarona == null || idCarona == "") {
			throw new CaronaInvalidaException("Carona Inválida");
		}

		for (CaronaAbstrata c : caronas) {
			if (c.getId().equals(idCarona)) {
				return c;
			}
		}

		throw new CaronaInvalidaException("Carona Inexistente");
	}

	/**
	 * Método que retorna sessão a partir de sua id
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
	 * Método que cadastra sugestao de ponto de encontro
	 * @param idSessao
	 * @param idCarona
	 * @param pontos
	 * @return id
	 * @throws Exception
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona,
			String pontos) throws Exception {
		
		if(pontos.equals("")){
			throw new OpcaoInvalidaException("Ponto Inválido");
		}
		CaronaAbstrata carona = getCarona(idCarona);
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();
		carona.setPontosSugeridos(id, pontos);

		return id;
	}

	/**
	 * Método para responder sugestao de encontro para a carona de tal id passado por parâmetro
	 * @param idSessao
	 * @param idCarona
	 * @param idSugestao
	 * @param pontos
	 * @return pontos
	 * @throws Exception
	 */
	public String responderSugestaoPontoEncontro(String idSessao,
			String idCarona, String idSugestao, String pontos) throws Exception {

		CaronaAbstrata carona = getCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (pontos.equals("") || pontos.equals(null)) {
			throw new AtributoInvalidoException("Ponto Inválido");

		} else if (sessao.getLogin().equals(carona.getCriador())) {
			carona.setPontosSugeridos(idSugestao, pontos);
		}

		return pontos;
	}

	/**
	 * Método para fazer solicitação de vaga passando ponto de encontro
	 * @param idSessao
	 * @param idCarona
	 * @param ponto
	 * @return solicitacao
	 * @throws Exception
	 */
	public Solicitacao solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto) throws Exception {
		
		CaronaAbstrata carona = getCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (idSessao == null || idCarona == null || ponto == null || carona == null || sessao == null) {
			throw new AtributoInvalidoException("Ponto Inválido");
		}

		
		Usuario usuario = getUsuario(sessao.getLogin());
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();

		Solicitacao solicitacao = new Solicitacao(id, usuario.getLogin(), carona.getId(), ponto);
		carona.addSolicitacao(solicitacao);
		return solicitacao;

	}

	/**
	 * Método para fazer solicitação de vaga sem passar ponto de encontro
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
	 * Método para retornar atributo de solicitação
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
	 * Método que retorna solicitação a partir de sua id
	 * @param idSolicitacao
	 * @return solicitacao
	 * @throws Exception
	 */
	private Solicitacao getSolicitacao(String idSolicitacao) throws Exception {

		for (CaronaAbstrata c : this.caronas) {
			List<Solicitacao> solicitacoes = c.getSolicitacoes();
			for (Solicitacao solicitacao : solicitacoes) {
				if (idSolicitacao.equals(solicitacao.getId())) {
					return solicitacao;
				}
			}
		}

		throw new ItemInexistenteException("Solicitação inexistente");
	}

	/**
	 * Método para aceitar solicitação dado um ponto de encontro
	 * @param idSessao
	 * @param idSolicitacao
	 * @return solicitacao
	 * @throws Exception
	 */
	public Solicitacao aceitarSolicitacaoPontoEncontro(String idSessao,
			String idSolicitacao) throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		// Sessao sessao = getSessao(idSessao);
		CaronaAbstrata carona = getCarona(solicitacao.getCarona());

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
	 * Método para aceitar solicitação 
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
	 * Método para desistir de sugestão
	 * @param idSessao
	 * @param idCarona
	 * @param idSugestao
	 * @return idSugestao
	 * @throws Exception
	 */
	public String desistirRequisicao(String idSessao, String idCarona,
			String idSugestao) throws Exception {

		CaronaAbstrata carona = getCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (sessao.getLogin().equals(carona.getCriador())) {
			carona.removeSugestao(idSugestao);
		}

		return idSugestao;
	}

	/**
	 * Método para rejeitar solicitação
	 * @param idSessao
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Solicitacao rejeitarSolicitacao(String idSessao, String idSolicitacao)
			throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		// Sessao sessao = getSessao(idSessao);
		CaronaAbstrata carona = getCarona(solicitacao.getCarona());

		// if (sessao.getLogin().equals(
		// solicitacao.getCarona().getCriador().getLogin())) {
		caronas.remove(carona);
		carona.removeSolicitacao(solicitacao);
		caronas.add(carona);
		// }

		return solicitacao;
	}

	/**
	 * Método para visualizar perfil de um usuário a partir de seu login
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
				throw new AtributoInvalidoException("Login inválido");
			}
			List<CaronaAbstrata> caronasLista = new ArrayList<CaronaAbstrata>();
			for(String idCarona : usuario.getCaronas()){
				caronasLista.add(getCarona(idCarona));
			}
			
			visualizadorDePerfil = new VisualizadorDePerfil(usuario, caronasLista);
		}
		return visualizadorDePerfil;
	}

	/**
	 * Método para retornar atributo de perfil de um usuario a partir de seu login
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
			List<CaronaAbstrata> caronasLista = new ArrayList<CaronaAbstrata>();
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
		if (atributo.equals("caronas que não funcionaram")) {
			return vp.getCaronasQueNaoFuncionaram();
		}

		if (atributo.equals("faltas em vagas de caronas")) {
			return vp.getFaltasEmVagasDeCaronas();
		}

		if (atributo.equals("presenças em vagas de caronas")) {
			return vp.getPresencasEmVagasDeCaronas();
		}
		throw new AtributoInvalidoException("Atributo inexistente");

	}

	/**
	 * Método que retorna carona de usuário a partir do index
	 * @param idSessao
	 * @param indexCarona
	 * @return carona
	 * @throws Exception
	 */
	public CaronaAbstrata getCaronaUsuario(String idSessao, int indexCarona) throws Exception {

		Usuario u = null;
		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = getUsuario(s.getLogin());
			}
		}

		CaronaAbstrata carona = null;

		if (u != null) {

			int i = indexCarona - 1;
			if (i >= 0 && i < u.getCaronas().size()) {
				carona = getCarona(u.getCaronas().get(i));
			}
		}

		return carona;
	}

	/**
	 * Método que retorna string com todas as caronas de um usuário a partir de sue login
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
	 * Método que retorna ids das solicitações confirmadas
	 * @param idSessao
	 * @param idCarona
	 * @return ids
	 * @throws Exception
	 */
	public String getSolicitacoesConfirmadas(String idSessao, String idCarona)
			throws Exception {

		CaronaAbstrata carona = getCarona(idCarona);
		String res = "{";
		for (Solicitacao sol : carona.getCaroneiros()) {
			res += sol.getId();

		}
		res += "}";

		return res;
	}
	/**
	 * Método que retorna as ids das solicitações pendentes
	 * @param idCarona
	 * @return ids
 	* @throws Exception
 	*/
	public String getSolicitacoesPendentes(String idSessao,String idCarona) throws Exception {

		CaronaAbstrata carona = getCarona(idCarona);
		String res = "{";
		for (Solicitacao sol : carona.getSolicitacoes()) {
			res += sol.getId();

		}
		res += "}";

		return res;
	}

	/**
	 * Método que retorna pontos sugeridos
	 * @param idSessao
	 * @param idCarona
	 * @return
	 * @throws Exception
	 */
	public String getPontosSugeridos(String idSessao, String idCarona)
			throws Exception {

		CaronaAbstrata carona = getCarona(idCarona);
		String res = "[";
		Collection<String> pontos = carona.getPontosSugeridosValues();
		for (String ponto : pontos) {
			res += ponto;

		}
		res += "]";

		return res;
	}

	/**
	 * Método que recupera pontos sugeridos
	 * @param idSessao
	 * @param idCarona
	 * @return
	 * @throws Exception
	 */
	public String getPontosEncontro(String idSessao, String idCarona)
			throws Exception {

		return getPontosSugeridos(idSessao, idCarona);
	}
	
	
	/**
	 * Método que adiciona review a carona
	 * @param idSessao
	 * @param idCarona
	 * @param review
	 * @return true se foi feita a review corretamente, false se nao
	 * @throws Exception
	 */
	public boolean reviewCarona(String idSessao, String idCarona, String review) throws Exception {

		Sessao sessao = getSessao(idSessao);
		String loginCaroneiro = getUsuario(sessao.getLogin()).getLogin();
		
		if(!review.equals("segura e tranquila") && !review.equals("não funcionou")){
			throw new OpcaoInvalidaException("Opção inválida.");
			
		}else if(!isCaroneiro(loginCaroneiro, idCarona)){
			throw new OpcaoInvalidaException("Usuário não possui vaga na carona.");
		}
		
		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				for (CaronaAbstrata c : caronas) {
					if (c.getId().equals(idCarona)) {

						GeradorDeID gerador = new GeradorDeID();
						String id = gerador.geraId();

						Review novoReview = new Review(id, c.getCriador(), c.getId(), review);
						c.addReview(novoReview);
						return true;

					}
				}

			}
		}

		return false;

	}
	
	/**
	 * Metodo para verificar se caroneiro esta mesmo na carona
	 * @param loginCaroneiro
	 * @param idCarona
	 * @return true se estiver, false se nao
	 */
	private boolean isCaroneiro(String loginCaroneiro, String idCarona){
		
		Usuario caroneiro = getUsuario(loginCaroneiro);
		boolean isCaroneiroDaCarona = false;
		
		for(String caronaID: caroneiro.getCaronasComoCaroneiro()){
			if(caronaID.equals(idCarona)){
				isCaroneiroDaCarona = true;
			}
		}
		
		return isCaroneiroDaCarona;		
	}

	/**
	 * Método que adiciona review sobre vaga em carona
	 * @param idSessao
	 * @param idCarona
	 * @param loginCaroneiro
	 * @param review1
	 * @return
	 * @throws Exception
	 */
	public String reviewVagaEmCarona(String idSessao, String idCarona,
			String loginCaroneiro, String review1) throws Exception {
		
		
		if(!isCaroneiro(loginCaroneiro,idCarona)){
			throw new OpcaoInvalidaException("Usuário não possui vaga na carona.");
		}
		
		Usuario caroneiro = getUsuario(loginCaroneiro);
		usuarios.remove(caroneiro);		
		
		if(review1.equals("faltou")){
			caroneiro.addFalta();
			usuarios.add(caroneiro);
			return caroneiro.getFaltas()+"";
			
		}else if(review1.equals("não faltou")){
			caroneiro.addPresenca();
			usuarios.add(caroneiro);
			return caroneiro.getPresencas()+"";
		}
		throw new OpcaoInvalidaException("Opção inválida.");
	}
	
	/**
	 * étodo que fecha o sistema
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
		
		for(CaronaAbstrata c : caronas){
			c.geraXml();
			this.xmlCreatorCaronas.geraXML(c.getXml());
		}
		
		this.xmlCreatorSistema.geraXML(this.xmlCreatorUsuarios.getRaiz());
		this.xmlCreatorSistema.geraXML(this.xmlCreatorCaronas.getRaiz());
		GravaXml gravador = new GravaXml(this.xmlCreatorSistema.getRaiz());
		gravador.gravar("arquivo.xml");
		
		
		
	}

	/**
	 * Método para apagar informações gravadas do sistema
	 */
	public void zerarSistema() {
		File f = new File("arquivo.xml");
		if(f.exists()){	
			
			f.delete();	
		}
		
	}

	/**
	 * Método para reiniciar o sistema
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
		
		return true;
	}
	
	/**
	 * Cadastra interesse em carona
	 * @param origem
	 * @param destino
	 * @param data
	 * @param horarioInicial
	 * @param horarioFinal
	 * @param interessado
	 * @return
	 * @throws OrigemInvalidaException 
	 * @throws DestinoInvalidoException 
	 * @throws DataInvalidaException 
	 */
	public Interesse cadastrarInteresse(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws OrigemInvalidaException, DestinoInvalidoException, DataInvalidaException{

		if (origem == null || contemCharInvalidos(origem)) {
			throw new OrigemInvalidaException();
		}else if (destino == null || contemCharInvalidos(destino)) {
			throw new DestinoInvalidoException();
		}else if(data == null){
			throw new DataInvalidaException();
		}
		
		Sessao sessao = getSessao(idSessao);
		String interessado = getUsuario(sessao.getLogin()).toString();
		Interesse interesse = new Interesse(interessado);
		interesse.setData(data);
		interesse.setDestino(destino);
		interesse.setHorarioFinal(horaFim);
		interesse.setHorarioInicial(horaInicio);
		interesse.setOrigem(origem);
		this.interesses.add(interesse);
		
		return interesse;
	}
	
	public String verificarMensagensPerfil(String idSessao){
		
		Sessao sessao = getSessao(idSessao);
		Usuario user = getUsuario(sessao.getLogin());
		
		return user.getMensagens();			
	}
	
	/**
	 * Procura nas caronas alguma que satisfaça os requesitos ditos no interesse
	 * @param interesse
	 */
	public void avisaSobreCaronaInteressada(Usuario u, CaronaAbstrata carona){
		
		for(Interesse i: interesses){
			if(i.getInteressado().equals(u.toString())){
				Usuario criador = getUsuario(carona.criador);
				if(i.getOrigem().equals(carona.getOrigem()) && i.getDestino().equals(carona.getDestino()) && i.getData().equals(carona.getData())){
					if(estaNoRangeDoTempo(i.getHorarioInicial(), i.getHorarioFinal(), carona.getHora())){
						
						String mensagem = "Carona cadastrada no dia " +carona.getData() +", às " + carona.getHora() + " de acordo com os seus interesses registrados. Entrar em contato com " + criador.getEmail();
						u.adicionaMensagem(mensagem);
						this.interesses.remove(i);
					}
				}else if(i.getOrigem().equals(carona.getOrigem()) && i.getDestino().equals(carona.getDestino()) && i.getData().equals("") && i.getHorarioInicial().equals("")){
					if(estaNoRangeDoTempo("00:00", i.getHorarioFinal(), carona.getHora())){
						
						String mensagem = "Carona cadastrada no dia " +carona.getData() +", às " + carona.getHora() + " de acordo com os seus interesses registrados. Entrar em contato com " + criador.getEmail();
						u.adicionaMensagem(mensagem);
						this.interesses.remove(i);
					}
				}
			}			
		}	
		
	}
	
	private boolean estaNoRangeDoTempo(String tempoInicial, String tempoFinal, String tempoCarona){
		
		if(tempoInicial.equals("")){
			tempoInicial = "00:00";
		}
		if(tempoFinal.equals("")){
			tempoFinal = "00:00";
		}
		
		int horaInicial = Integer.parseInt(tempoInicial.substring(0, 2));
		int horaFinal = Integer.parseInt(tempoFinal.substring(0, 2));
		
		int minutoInicial = Integer.parseInt(tempoInicial.substring(3, 5));
		int minutoFinal = Integer.parseInt(tempoFinal.substring(3, 5));
		
		int horaCarona = Integer.parseInt(tempoInicial.substring(0, 2));
		int minutoCarona = Integer.parseInt(tempoFinal.substring(3, 5));

		if((horaInicial <= horaCarona) && (horaFinal >= horaCarona)){
			return true;
			
		}else if(horaInicial == horaCarona && horaFinal == horaCarona){
			if(minutoInicial <= minutoCarona && minutoFinal >= minutoCarona){
				
				return true;
			}else{
				return false;
			}
			 
		}else{
			return false;
		}
	}

	public List<Interesse> getInteresses() {
		return interesses;
	}

	public void setInteresses(List<Interesse> interesses) {
		this.interesses = interesses;
	}

}
