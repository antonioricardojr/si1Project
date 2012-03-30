package si1.logica;
/**
 * Classe que representa um sessao do sistema.
 * 
 * @author ANTONIOR
 * 
 */
public class Sessao {

	private Usuario usuario;
	private String id;

	public Sessao(Usuario usuario, String id) {
		super();
		this.usuario = usuario;
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
