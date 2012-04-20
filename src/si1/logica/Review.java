package si1.logica;

public class Review {
	
	private String id;
	private Usuario usuario;
	private Carona carona;
	private String review;
	
	public Review(String id, Usuario usuario, Carona carona, String review){
		this.setId(id);
		this.setUsuario(usuario);
		this.setCarona(carona);
		this.setReview(review);
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Carona getCarona() {
		return carona;
	}

	public void setCarona(Carona carona) {
		this.carona = carona;
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
