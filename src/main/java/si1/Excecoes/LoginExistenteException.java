package si1.Excecoes;

public class LoginExistenteException extends Exception {
	
	public LoginExistenteException(){
		super("Já existe um usuário com este login");
	}

}
