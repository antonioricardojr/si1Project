package Facade;

import java.util.List;

import si1.logica.Carona;
import si1.logica.Sessao;
import si1.logica.Sistema;
import si1.logica.Usuario;

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
	
	public String encerrarSessao(String login) throws Exception{
		return S1.encerrarSessao(login);
	}

	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontos) throws Exception{
		return S1.sugerirPontoEncontro(idSessao, idCarona, pontos);
	}
	
	public String responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao,String pontos) throws Exception{
		
		return S1.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, pontos);
	}
	
	public String solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
	
		return S1.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
	}
	
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws Exception{
		return S1.getAtributoSolicitacao(idSolicitacao, atributo);
	}
	
	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		return S1.getAtributoUsuario(login, atributo);
	}

	public String aceitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{
		return S1.aceitarSolicitacao(idSessao, idSolicitacao);
	}
	
	public String desistirRequisicao(String idSessao, String idCarona, String idSugestao) throws Exception{
		return S1.desistirRequisicao(idSessao, idCarona, idSugestao);
	}
	
	public String localizarCarona(String idSessao, String origem,
			String destino) throws Exception {

		return S1.localizarCarona(idSessao, origem, destino);
	}

	public String cadastrarCarona(String idSessao, String origem,
			String destino, String data, String hora, int vagas)
			throws Exception {
		return S1.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
	}

	public String getAtributoCarona(String idCarona, String atributo)
			throws Exception {

		return S1.getAtributoCarona(idCarona, atributo);
	}

	public String getTrajeto(String idCarona) throws Exception {

		return S1.getTrajeto(idCarona);

	}
	
	public String aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		return S1.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}

	public String solicitarVaga(String idSessao, String idCarona) throws Exception{
		return S1.solicitarVaga(idSessao, idCarona);
	}
	
	public String getCarona(String idCarona) throws Exception {
		return S1.getCarona(idCarona);
	}
	
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao){
		S1.rejeitarSolicitacao(idSessao, idSolicitacao);
	}

	public void encerrarSistema() {

	}

}