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
		String historicoDeCaronas = "";

		for (Carona c : usuario.getCaronasOferecidas()) {
			historicoDeCaronas += c.toString() + ",";
		}

		if (historicoDeCaronas.length() != 0) {
			historicoDeCaronas = historicoDeCaronas.substring(0,
					historicoDeCaronas.length() - 1);
		}

		return historicoDeCaronas;
	}

	public String getHistoricoDeVagasEmCaronas() {
		
		String saida = "";
		
		if(usuario.getCaronas().size() == 0){
			return "";
		}
		
		for(Carona c : usuario.getCaronas()){
			saida += c.getId() + ",";
		}
		
		saida = saida.substring(0, saida.length() - 2);

		saida = "[" + saida + "]";
		
		return saida;
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
		// TODO Auto-generated method stub
		return "0";
	}

	public String getPresencasEmVagasDeCaronas() {
		// TODO Auto-generated method stub
		return "0";
	}

	public List<Usuario> getAmigos() {
		return usuario.getAmigos();
	}

}
