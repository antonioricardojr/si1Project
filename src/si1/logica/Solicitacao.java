package si1.logica;

public class Solicitacao {

	private String id;
	private Usuario solicitador;
	private Carona carona;
	private String ponto;
	
	public Solicitacao(String id, Usuario usuario, Carona carona, String ponto) {
		
		this.id = id;
		this.solicitador = usuario;
		this.carona = carona;
		this.ponto = ponto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Usuario getSolicitador() {
		return solicitador;
	}

	public void setSolicitador(Usuario solicitador) {
		this.solicitador = solicitador;
	}

	public Carona getCarona() {
		return carona;
	}

	public void setCarona(Carona carona) {
		this.carona = carona;
	}

	public String getPonto() {
		return ponto;
	}

	public void setPonto(String ponto) {
		this.ponto = ponto;
	}

}
