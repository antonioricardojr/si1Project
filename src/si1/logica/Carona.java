package si1.logica;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe que representa uma carona no sistema.
 * 
 * @author ANTONIOR
 * 
 */

public class Carona {

	private String idSessao;
	private String id;
	private String origem;
	private String destino;
	private String data;
	private String hora;
	private int vagas;

	public Carona(String idSessao, String origem, String destino, String data,
			String hora, int vagas) throws Exception {

		this.setIdSessao(idSessao);
		this.setOrigem(origem);
		this.setDestino(destino);
		this.setData(data);
		this.setHora(hora);
		this.setVagas(vagas);

		GeradorDeID gerador = new GeradorDeID();
		id = gerador.geraId();
	}

	public String getId() {
		return id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) throws Exception {
		if (origem == null || origem.equals("")) {
			throw new Exception("Origem nula ou invalida");
		} else {
			this.origem = origem;
		}
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) throws Exception {
		if (destino == null || destino.equals("")) {
			throw new Exception("Destino nulo ou invalido.");
		} else {
			this.destino = destino;
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) throws Exception {
		if (data == null || data.equals("") || !validaData(data)) {
			throw new Exception("Data nula ou invalida.");
		} else {
			this.data = data;
		}
	}
	
	private static boolean validaData(String data){
		if(data.length()!=10){
			return false;
		}
		String dia = data.substring(0, 2);
		String mes = data.substring(3, 5);
		String ano = data.substring(6);
		
		
		return true;
		
	}

	public String getHora() throws Exception {
		return this.hora;
	}

	public void setHora(String hora) throws Exception {
		if (hora == null || hora.equals("") || !validaHora(hora)) {
			throw new Exception("Hora nula ou invalida.");
		} else {
			this.hora = hora;
		}
	}

	private boolean validaHora(String hora) {
		if (hora.length() != 5) {
			return false;
		} else {
			String[] caracteres = hora.split("");
			for (int i = 1; i < caracteres.length; i++) {
				if (i == 3){
					if(!caracteres[3].equals(":")) {
						return false;					
					}
				} else {
					if (temInvalidoNosNumeros((caracteres[i]))) {
						return false;
					}
				}
			}

		}
		return true;

	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) throws Exception {
		if (vagas <= 0) {
			throw new Exception("Numero de vagas nula ou invalida.");
		}
		this.vagas = vagas;
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String idSessao) throws Exception {
		if (idSessao == null || idSessao.equals("")) {
			throw new Exception("Id de sessao nulo ou invalido.");
		}else{
			String[] caracteres = idSessao.split("");
			for(int i = 1; i<caracteres.length;i++){
				if(temInvalidoNosNumeros(caracteres[i])){
					throw new Exception("Id de sessao invalido ou nulo.");
				}
			}
		}
		this.idSessao = idSessao;
	}

	public static boolean temInvalidoNosNumeros(String s){
		if(!"0123456789".contains(s)){
			return true;
		}else{
			return false;
		}
	}
}
