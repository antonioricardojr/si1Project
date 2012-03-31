package Facade;


import si1.logica.Sistema;


/**
 * Classe Facade para usar os testes do easyAccept.
 * 
 * @author ANTONIOR
 *
 */
public class Si1ProjectFacade {
	
	
	
	private Sistema S1;	
	
	

	
	
	public void zerarSistema(){
		S1 = new Sistema();
	}
	
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		S1.criarUsuario("mark", "123456", "Mark Zuckerberg", "Palo Alto, California", "mark@facebook.com");
		S1.criarUsuario("steve", "5t3v3", "Steven Paul Jobs", "Palo Alto, California", "jobs@apple.com");
		S1.criarUsuario("bill", "severino", "William Henry Gates III", "Medina, Washington", "billzin@msn.com");
		
	}
	
	public String abrirSessao(String login, String senha) throws Exception{
		return S1.abrirSessao(login, senha);
		
	}
	
	
	public String getAtributoUsuario(String login, String atributo) throws Exception{
		return S1.getAtributoUsuario(login,atributo);
	}
	

}
