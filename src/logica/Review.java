package logica;

import excecoes.ReviewInvalidaException;

public class Review {
	
	private String id;
	private String loginUsuario;
	private String idCarona;
	private String review;
	
	public Review(String id, String loginUsuario, String idCarona, String review)throws Exception {
		
		if(id == null || loginUsuario == null || idCarona == null || review == null){
			throw new ReviewInvalidaException();
		}else{
			this.setId(id);
			this.setUsuario(loginUsuario);
			this.setCarona(idCarona);
			this.setReview(review);
		}	
		
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getCarona() {
		return this.idCarona;
	}

	public void setCarona(String idCarona) {
		this.idCarona = idCarona;
	}

	public String getUsuario() {
		return this.loginUsuario;
	}

	public void setUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
