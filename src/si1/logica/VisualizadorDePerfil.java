package si1.logica;

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
		
		String vagasOcupadasEmCaronas = "";
		
		int vagas = 0;
		
		for(Carona c : usuario.getCaronasComoCaroneiro()){
			vagas++;
		}
		
		return vagasOcupadasEmCaronas;
	}

	public String getCaronasSegurasETranquilas() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCaronasQueNaoFuncionaram() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFaltasEmVagasDeCaronas() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPresencasEmVagasDeCaronas() {
		// TODO Auto-generated method stub
		return null;
	}

}
