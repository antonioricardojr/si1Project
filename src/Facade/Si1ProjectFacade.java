package Facade;

import java.util.List;

import si1.logica.Carona;
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
		try {
			S1.criarUsuario(login, senha, nome, endereco, email);

		} catch (Exception e) {

		}

	}

	public String abrirSessao(String login, String senha) throws Exception {
		return S1.abrirSessao(login, senha);

	}

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		return S1.getAtributoUsuario(login, atributo);
	}

	public List<Carona> localizarCaronas(String idSessao, String origem,
			String destino) {

		return S1.localizarCarona(idSessao, origem, destino);
	}
	
	public String cadastrarCarona(String idSessao,String origem,String destino,String data,String hora,int vagas) throws Exception{
		return S1.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
	}
	
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		
		return S1.getAtributoCarona(idCarona, atributo);
	}
	
	public String getTrajeto(String idCarona) throws Exception{
		
		return S1.getTrajeto(idCarona);
		
	}
	
	public String getCarona(String idCarona) throws Exception{
		return S1.getCarona(idCarona);
	}

}