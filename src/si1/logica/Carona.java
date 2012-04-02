package si1.logica;

import java.util.Calendar;
import java.util.Date;
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
		if (origem == null || origem.equals("") || contemCharInvalidos(origem)) {
			throw new Exception("Origem nula ou invalida");
		} else {
			this.origem = origem;
		}
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) throws Exception {
		if (destino == null || destino.equals("") || contemCharInvalidos(destino)) {
			throw new Exception("Destino nulo ou invalido.");
		} else {
			this.destino = destino;
		}
	}

	private static boolean contemCharInvalidos(String nome) {
		String[] palavra = nome.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%¨¨¨&*()+¹²³£¢¬§=[{ª~^]}º;:>,</|´/`0123456789*-".contains(palavra[i])) {
				return true;
			}
		}

		return false;
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

	@SuppressWarnings("deprecation")
	private static boolean validaData(String data) throws Exception {
		Date date = new Date();
		int ano;
		int mes;
		int dia;
		if (data.length() != 10) {
			return false;
		}
		try {

			dia = Integer.parseInt(data.substring(0, 2));
			mes = Integer.parseInt(data.substring(3, 5));
			ano = Integer.parseInt(data.substring(6));

		} catch (Exception e) {
			throw new Exception("Data nula ou invalida.");
		}

		// Verifica ano >= que atual
		if (ano < date.getYear() + 1900) {
			return false;
		}

		// Verifica mes entre 01 e 12
		if (mes < 1 || mes > 12) {
			return false;
		}

		// Verifica para meses com 31 dias
		if ((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
				|| mes == 10 || mes == 12)
				&& (dia < 1 || dia > 31)) {
			return false;

		}

		// Verifica para meses com 30 dias
		if ((mes == 4 || mes == 6 || mes == 9 || mes == 11)
				&& (dia < 1 || dia > 30)) {
			return false;

		}

		// Verifica para fevereiro bissexto com 29 dias
		if ((mes == 2) && (ano % 4 == 0) && (dia < 1 || dia > 29)) {
			return false;
		}

		// Verifica para fevereiro não bissexto com 28 dias
		if ((mes == 2) && (ano % 4 != 0) && (dia < 1 || dia > 28)) {
			return false;
		}

		//Compara com a data atual
		if(comparaDataAtual(dia, mes, ano)<0){
			return false;
		}
		
		// Aceita a data
		return true;

	}
	
	private static int comparaDataAtual(int dia, int mes, int ano){
		Date date = new Date();
		if(ano==date.getYear()+1900){
			if(mes==date.getMonth()+1){
				if(dia==date.getDay()+1){
					return 0;
				}else{
					if(dia>date.getDay()+1){
						return 1;
					}else{
						return -1;
					}
				}
			}else{
				if(mes>date.getMonth()+1){
					return 1;
				}else{
					return -1;
				}
			}
		}else{
			if(ano>date.getYear()+1900){
				return 1;
			}else{
				return 0;
			}
		}
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
				if (i == 3) {
					if (!caracteres[3].equals(":")) {
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
		} else {
			String[] caracteres = idSessao.split("");
			for (int i = 1; i < caracteres.length; i++) {
				if (temInvalidoNosNumeros(caracteres[i])) {
					throw new Exception("Id de sessao invalido ou nulo.");
				}
			}
		}
		this.idSessao = idSessao;
	}

	public static boolean temInvalidoNosNumeros(String s) {
		if (!"0123456789".contains(s)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args){
		System.out.println(contemCharInvalidos("bucata oiE"));
	}
}
