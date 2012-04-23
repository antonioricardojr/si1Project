package si1.logica;

import java.util.List;

public class VisualizadorDePerfil {

	private Usuario usuario;

	public VisualizadorDePerfil(Usuario usuario) {
		setUsuario(usuario);
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

		if(usuario.getCaronasOferecidas().size()==0){
			return "";
		}
		
		String historicoDeCaronas = "[";
		
		for (Carona c : usuario.getCaronasOferecidas()) {
			historicoDeCaronas += c.getId() + ",";
		}

		if (historicoDeCaronas.length() != 0) {
			historicoDeCaronas = historicoDeCaronas.substring(0,
					historicoDeCaronas.length() - 1);
		}
		historicoDeCaronas += "]";
		
		return historicoDeCaronas;
	}

	public String getHistoricoDeVagasEmCaronas() {
		
		int saida = 0;
		
		if(usuario.getCaronas().size() == 0){
			return "";
		}
		
		for(Carona c : usuario.getCaronas()){
			saida += c.getVagas();
		}
		
		return saida+"";
	}

	public String getCaronasSegurasETranquilas() {
		// TODO Auto-generated method stub
		return "0";
	}

	public String getCaronasQueNaoFuncionaram() {
		// TODO Auto-generated method stub
		return "0";
	}

	public String getFaltasEmVagasDeCaronas() {
		
		int faltas = 0;
		for(Carona c : usuario.getCaronas()){
			for(Review r : c.getReviews()){
				
				if(r.getReview() == "faltou"){
					
					faltas++;
				}
			}
		}
		
		
		return "" + faltas;
	}

	public String getPresencasEmVagasDeCaronas() {
		// TODO Auto-generated method stub
		return "0";
	}

	public List<Usuario> getAmigos() {
		return usuario.getAmigos();
	}

}
