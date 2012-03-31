package si1.logica;

/**
 * Classe que representa uma carona no sistema.
 * 
 * @author ANTONIOR
 * 
 */

public class Carona {

	private String idDaSessao;
	private String id;
	private String origem;
	private String destino;
	private String data;
	private String hora;
	private int vagas;

	public Carona(String idDaSessao, String origem, String destino,
			String data, String hora, int vagas) throws Exception {
		super();
		this.setIdDaSessao(idDaSessao);
		this.setOrigem(origem);
		this.setDestino(destino);
		this.setData(data);
		this.setHora(hora);
		this.setVagas(vagas);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getOrigem() {
		return origem;
	}

	
	public void setOrigem(String origem) throws Exception {
		if(origem == null || origem.equals("")){
			throw new Exception("Origem nula ou invalida");
		}else{
			this.origem = origem;			
		}
	}

	
	public String getDestino() {
		return destino;
	}

	
	public void setDestino(String destino) throws Exception {
		if(destino == null || destino.equals("")){
			throw new Exception("Data nula ou invalida.");
		}else{
		    this.destino = destino;
		}
	}

	
	public String getData() {
		return data;
	}
	
	
	public void setData(String data) throws Exception {
		if (data == null || data.equals("")) {
			throw new Exception("Data nula ou invalida.");
		} else {
			this.data = data;
		}
	}

	
	public String getHora() throws Exception {
		return this.hora;
	}

	public void setHora(String hora) throws Exception {
		if(hora == null || hora.equals("")){
			throw new Exception("Hora nula ou invalida.");
		}else{
			this.hora = hora;
		}
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) throws Exception {
		if(vagas <= 0){
			throw new Exception("Numero de vagas nula ou invalida.");
		}
		this.vagas = vagas;
	}

	public String getIdDaSessao() {
		return idDaSessao;
	}

	public void setIdDaSessao(String idDaSessao) {
		this.idDaSessao = idDaSessao;
	}

}
