package si1.Excecoes;

public class LoginExistenteException extends Exception {
	
	public LoginExistenteException(){
		super("J� existe um usu�rio com este login");
	}

}
