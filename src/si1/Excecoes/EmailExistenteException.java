package si1.Excecoes;

public class EmailExistenteException extends Exception {
	
	public EmailExistenteException(){
		super("J� existe um usu�rio com este email");
	}

}
