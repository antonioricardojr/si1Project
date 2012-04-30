package logica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import excecoes.AtributoInexistenteException;
import excecoes.AtributoInvalidoException;
import excecoes.CaronaInexistenteException;
import excecoes.CaronaInvalidaException;
import excecoes.DestinoInvalidoException;
import excecoes.EmailExistenteException;
import excecoes.IdentificadorCaronaInvalidoException;
import excecoes.ItemInexistenteException;
import excecoes.LoginExistenteException;
import excecoes.LoginInvalidoException;
import excecoes.NomeInvalidoException;
import excecoes.OrigemInvalidaException;
import excecoes.PontoInvalidoException;
import excecoes.SessaoInexistenteException;
import excecoes.SessaoInvalidaException;
import excecoes.SolicitacaoInexistenteException;
import excecoes.TrajetoInexistenteException;
import excecoes.TrajetoInvalidoException;
import excecoes.UsuarioInexistenteException;
import excecoes.VagaInvalidaException;
import excecoes.XMLNaoGeradaException;

import xml.FactoryXml;
import xml.GravaXml;
import xml.Xml;
import xml.leXml;

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
	private Xml xmlCreatorUsuarios;
	private Xml xmlCreatorCaronas;
	private Xml xmlCreatorSistema;

	public Sistema() {
		usuarios = new ArrayList<Usuario>();
		sessoes = new ArrayList<Sessao>();
		caronas = new ArrayList<Carona>();
		this.xmlCreatorUsuarios = new FactoryXml("Xml Usuarios do sistema");
		this.xmlCreatorCaronas = new FactoryXml("Xml Caronas do sistema");
		this.xmlCreatorSistema = new FactoryXml("Xml Sistema");
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public Usuario getUsuario(String login) {
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}

	public Usuario criarUsuario(String login, String senha, String nome,
			String endereco, String email) throws Exception {

		if (login == null || login.equals("") || senha == null) {
			throw new LoginInvalidoException();
		}

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				throw new LoginExistenteException();
			}
			if (u.getEmail().equals(email)) {
				throw new EmailExistenteException();
			}
		}

		if (nome == null || nome.equals("")) {
			throw new NomeInvalidoException();
		}

		Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);

		usuarios.add(novoUsuario);

		return novoUsuario;
	}

	public String abrirSessao(String login, String senha) throws Exception {
		if (login == null || login.equals("")) {
			throw new LoginInvalidoException();
		}

		Sessao novaSessao = null;

		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login)) {
				if (verificaSenha(senha)) {
					novaSessao = new Sessao(login);
					sessoes.add(novaSessao);
					return novaSessao.getId();
				} else {
					throw new LoginInvalidoException();
				}
			}
		}
		throw new UsuarioInexistenteException();
	}

	public String encerrarSessao(String login) throws Exception {

		if (login == null || login.equals("")) {
			throw new LoginInvalidoException();
		}

		for (Sessao s : sessoes) {
			if (s.getLogin().equals(login)) {
				sessoes.remove(s);
				return login;
			}
		}

		throw new UsuarioInexistenteException();

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
			throw new AtributoInvalidoException();
		}

		if (login == null || login.equals("")) {
			throw new LoginInvalidoException();
		}

		Usuario u = buscaUsuario(login);

		if (u == null) {
			throw new UsuarioInexistenteException();
		}

		if (atributo.equals("nome")) {
			return u.getNome();
		}
		if (atributo.equals("endereco")) {
			return u.getEndereco();
		}

		throw new AtributoInexistenteException();
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
			String destino, String data, String hora, String vagas)
			throws Exception {

		int intVagas;

		try {
			intVagas = Integer.parseInt(vagas);
		} catch (Exception e) {
			throw new VagaInvalidaException();
		}

		if (idSessao == null || idSessao.equals("")) {
			throw new SessaoInvalidaException();
		}

		if (!verificaSessao(idSessao)) {
			throw new SessaoInexistenteException();
		}

		Sessao sessao = getSessao(idSessao);

		Usuario usuario = buscaUsuario(sessao.getLogin());

		Carona novaCarona = new Carona(origem, destino, data, hora, intVagas,
				usuario.getLogin());
		caronas.add(novaCarona);
		usuario.adicionaCaronaOferecida(novaCarona.getId());
		usuario.adicionaCarona(novaCarona.getId());

		return novaCarona.getId();
	}

	public String getAtributoCarona(String idCarona, String atributo)
			throws Exception {

		if (idCarona == null || idCarona.equals("")) {
			throw new IdentificadorCaronaInvalidoException();
		}

		if (atributo == null || atributo.equals("")) {
			throw new AtributoInvalidoException();
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
					throw new AtributoInexistenteException();
				}
			}
		}
		throw new ItemInexistenteException();

	}

	public String getTrajeto(String idCarona) throws Exception {

		if (idCarona == null) {
			throw new TrajetoInvalidoException();

		}

		String trajeto = null;

		try {
			Carona c = localizaCarona(idCarona);

			trajeto = c.getOrigem() + " - " + c.getDestino();

		} catch (Exception e) {
			throw new TrajetoInexistenteException();
		}

		return trajeto;

	}

	public String getCarona(String idCarona) throws Exception {

		if (idCarona == null) {
			throw new CaronaInvalidaException();
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
			throw new CaronaInvalidaException();
		}

		for (Carona c : caronas) {
			if (c.getId().equals(idCarona)) {
				return c;
			}
		}

		throw new CaronaInexistenteException();
	}

	public String sugerirPontoEncontro(String idSessao, String idCarona,
			String pontos) throws Exception {

		Carona carona = localizaCarona(idCarona);
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();
		carona.setPontosSugeridos(id, pontos);

		return id;
	}

	public String responderSugestaoPontoEncontro(String idSessao,
			String idCarona, String idSugestao, String pontos) throws Exception {

		Carona carona = localizaCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		if (pontos.equals("") || pontos.equals(null)) {
			throw new PontoInvalidoException();

		} else if (sessao.getLogin().equals(carona.getCriador())) {
			carona.setPontosSugeridos(idSugestao, pontos);
		}

		return pontos;
	}

	public String solicitarVagaPontoEncontro(String idSessao, String idCarona,
			String ponto) throws Exception {

		if (idSessao == null || idCarona == null || ponto == null) {
			throw new PontoInvalidoException();
		}

		Carona carona = localizaCarona(idCarona);
		Sessao sessao = getSessao(idSessao);

		Usuario usuario = buscaUsuario(sessao.getLogin());
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();

		Solicitacao sol = new Solicitacao(id, usuario.getLogin(), carona.getId(), ponto);
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
			return localizaCarona(solicitacao.getCarona()).getOrigem();
		} else if (atributo.equals("destino")) {
			return localizaCarona(solicitacao.getCarona()).getDestino();
		} else if (atributo.equals("Dono da carona")) {
			return getUsuario(localizaCarona(solicitacao.getCarona()).getCriador()).getNome();
		} else if (atributo.equals("Dono da solicitacao")) {
			return getUsuario(solicitacao.getSolicitador()).getNome();
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

		throw new SolicitacaoInexistenteException();
	}

	public String aceitarSolicitacaoPontoEncontro(String idSessao,
			String idSolicitacao) throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		// Sessao sessao = getSessao(idSessao);
		Carona carona = localizaCarona(solicitacao.getCarona());

		// if (sessao.getLogin().equals(
		// solicitacao.getCarona().getCriador().getLogin())) {
		caronas.remove(carona);
		carona.addCaroneiro(solicitacao);
		carona.setVagas(carona.getVagas() - 1);
		carona.removeSolicitacao(solicitacao);
		carona.setPonto(solicitacao.getPonto());
		buscaUsuario(solicitacao.getSolicitador()).adicionaCaronaComoCaroneiro(carona.getId());
		caronas.add(carona);

		// }

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

		if (sessao.getLogin().equals(carona.getCriador())) {
			carona.removeSugestao(idSugestao);
		}

		return idSugestao;
	}

	public String rejeitarSolicitacao(String idSessao, String idSolicitacao)
			throws Exception {

		Solicitacao solicitacao = getSolicitacao(idSolicitacao);
		// Sessao sessao = getSessao(idSessao);
		Carona carona = localizaCarona(solicitacao.getCarona());

		// if (sessao.getLogin().equals(
		// solicitacao.getCarona().getCriador().getLogin())) {
		caronas.remove(carona);
		carona.removeSolicitacao(solicitacao);
		caronas.add(carona);
		// }

		return solicitacao.getId();
	}

	public String visualizarPerfil(String idSessao, String login)
			throws Exception {

		VisualizadorDePerfil vp = null;

		if (verificaSessao(idSessao)) {
			Usuario usuario = buscaUsuario(login);
			if (usuario == null) {
				throw new LoginInvalidoException();
			}
			List<Carona> caronasLista = new ArrayList<Carona>();
			for(String idCarona : usuario.getCaronas()){
				caronasLista.add(localizaCarona(idCarona));
			}
			
			vp = new VisualizadorDePerfil(usuario, caronasLista);
		}
		return vp.getNome();
	}

	public String getAtributoPerfil(String login, String atributo)
			throws Exception {

		Usuario usuario = buscaUsuario(login);

		VisualizadorDePerfil vp;
		if (usuario != null) {
			List<Carona> caronasLista = new ArrayList<Carona>();
			for(String idCarona : usuario.getCaronas()){
				caronasLista.add(localizaCarona(idCarona));
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

		return null;

	}

	public String getCaronaUsuario(String idSessao, int indexCarona) throws Exception {

		Usuario u = null;
		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = getUsuario(s.getLogin());
			}
		}

		Carona c = null;

		if (u != null) {

			int i = indexCarona - 1;
			if (i >= 0 && i < u.getCaronas().size()) {
				c = localizaCarona(u.getCaronas().get(i));
			}
		}

		String saida = null;

		if (c != null) {
			saida = c.getId();
		}

		return saida;
	}

	public String getTodasCaronasUsuario(String idSessao) throws Exception {

		Usuario u = null;

		List<String> todasAsCaronas = new ArrayList<String>();

		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = getUsuario(s.getLogin());
			}
		}

		for (String c : u.getCaronas()) {
			todasAsCaronas.add(localizaCarona(c).getId());
		}

		String saida = todasAsCaronas.toString().replace("[", "{")
				.replace("]", "}").replace(" ", "");

		return saida;

	}

	public String getSolicitacoesConfirmadas(String idSessao, String idCarona)
			throws Exception {

		Carona carona = localizaCarona(idCarona);
		String res = "{";
		for (Solicitacao sol : carona.getCaroneiros()) {
			res += sol.getId();

		}
		res += "}";

		return res;
	}

	public String getSolicitacoesPendentes(String idCarona) throws Exception {

		Carona carona = localizaCarona(idCarona);
		String res = "{";
		for (Solicitacao sol : carona.getSolicitacoes()) {
			res += sol.getId();

		}
		res += "}";

		return res;
	}

	public String getPontosSugeridos(String idSessao, String idCarona)
			throws Exception {

		Carona carona = localizaCarona(idCarona);
		String res = "";
		Collection<String> pontos = carona.getPontosSugeridosValues();
		for (String ponto : pontos) {
			res += ponto;

		}
		res += "";

		return res;
	}

	public boolean reviewCarona(String idSessao, String idCarona, String review) throws Exception {

		Usuario u;

		for (Sessao s : sessoes) {
			if (s.getId().equals(idSessao)) {
				u = buscaUsuario(s.getLogin());
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

	public String reviewVagaEmCarona(String idSessao, String idCarona,
			String loginCaroneiro, String review) throws Exception {

		Sessao sessao = getSessao(idSessao);
		Usuario user = buscaUsuario(sessao.getLogin());
		GeradorDeID gerador = new GeradorDeID();
		String id = gerador.geraId();

		Usuario caroneiro = buscaUsuario(loginCaroneiro);
		for (String c : caroneiro.getCaronasComoCaroneiro()) {
			if (c.equals(idCarona)) {
				Carona carona = localizaCarona(c);
				caronas.remove(carona);
				Review rev = new Review(id, user.getLogin(), c, review);
				carona.addReview(rev);
				caronas.add(carona);

				return rev.getId();
			}
		}

		return null;

	}
	
	public void encerrarSistema() throws IOException, XMLNaoGeradaException{
		
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

	public void zerarSistema() {
		File f = new File("arquivo.xml");
		if(f.exists()){	
			f.delete();	
		}
		
	}

	public void reiniciarSistema() throws Exception {
		leXml leitor = new leXml("arquivo.xml");
		this.usuarios = leitor.getUsuarios();	
		this.caronas = leitor.getCaronas();
	}
}
