package excecoes;

public class EmailExistenteException extends Exception {
	
	public EmailExistenteException(){
		super("Já existe um usuário com este email");
	}

}
