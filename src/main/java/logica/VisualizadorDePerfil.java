package logica;

import java.util.List;

public class VisualizadorDePerfil {

	private Usuario usuario;
	private List<CaronaAbstrata> caronas;

	public VisualizadorDePerfil(Usuario usuario, List<CaronaAbstrata> caronas) {
		setUsuario(usuario);
		setCaronas(caronas);
	}

	private void setCaronas(List<CaronaAbstrata> caronas) {
		this.caronas = caronas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {

		return usuario.getNome();
	}

	public String getEndereco() {
		return usuario.getEndereco();
	}

	public String getEmail() {
		return usuario.getEmail();
	}

	public String getHistoricoDeCaronas() {

		if (usuario.getCaronasOferecidas().size() == 0) {
			return "[]";
		}

		String historicoDeCaronas = "[";

		for (String c : usuario.getCaronasOferecidas()) {
			historicoDeCaronas += c + ",";
		}

		if (historicoDeCaronas.length() != 0) {
			historicoDeCaronas = historicoDeCaronas.substring(0,
					historicoDeCaronas.length() - 1);
		}
		historicoDeCaronas += "]";

		return historicoDeCaronas;
	}

	public String getHistoricoDeVagasEmCaronas() {

		String saida = "";
		if (usuario.getCaronasComoCaroneiro().size() == 0) {
			return "[]";
		} else {
			for (String c : usuario.getCaronasComoCaroneiro()) {
				saida += c + ",";
			}

			saida = "[" + saida.substring(0, saida.length() - 1) + "]";
			return saida;
		}
	}

	public String getCaronasSegurasETranquilas() {
		int saida = 0;
		if (usuario.getCaronasOferecidas().size() == 0) {
			return saida+"";
		} else {
			for (CaronaAbstrata c : caronas) {
				for(Review rev : c.getReviews()){
					if(rev.getUsuario().equals(usuario.getLogin())){
						if(rev.getReview().equals("segura e tranquila")){
							saida+=1;
						}
					}	
				}				
			}
		}
		return saida+"";
	}

	public String getCaronasQueNaoFuncionaram() {
		int saida = 0;
		if (usuario.getCaronasOferecidas().size() == 0) {
			return saida+"";
		} else {
			for (CaronaAbstrata c : caronas) {
				for(Review rev : c.getReviews()){
					if(rev.getUsuario().equals(usuario.getLogin())){
						if(rev.getReview().equals("não funcionou")){
							saida+=1;
						}
					}	
				}				
			}
		}
		return saida+"";
	}

	public String getFaltasEmVagasDeCaronas() {

		return usuario.getFaltas()+"";
	}

	public String getPresencasEmVagasDeCaronas() {

		return usuario.getPresencas()+"";
	}

	public List<String> getAmigos() {
		return usuario.getAmigos();
	}

}
