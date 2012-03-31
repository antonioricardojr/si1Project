package si1.logica;

/**
 * Classe que representa um sessao do sistema.
 * 
 * @author ANTONIOR
 * 
 */
public class Sessao {

	private String login;
	private String id;

	public Sessao(String login) throws Exception {

		setLogin(login);		
		setId(new GeradorDeID().geraId());
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login)throws Exception {
		if(login == null || login.equals("")){
			throw new Exception("Login nulo ou invalido.");
		}
		this.login = login;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) throws Exception {
		if (id == null || id.equals("")) {
			throw new Exception("ID nulo ou invalido");
		}
		this.id = id;
	}


	
	
	

}
