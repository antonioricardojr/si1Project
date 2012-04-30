package excecoes;

public class LoginExistenteException extends Exception {
	
	public LoginExistenteException(){
		super("Já existe um usuário com este login");
	}

}
