package si1.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que representa uma carona no sistema.
 * 
 * @author ANTONIOR
 * 
 */

public class Carona {

	private String id;
	private String origem;
	private String destino;
	private String data;
	private String hora;
	private Object vagas;
	private HashMap<String, String> pontosSugeridos;
	private List<Solicitacao> solicitacoes;
	private List<Usuario> caroneiros;
	private Usuario criador;

	public Carona(String origem, String destino, String data, String hora,
			Object vagas, Usuario criador) throws Exception {

		this.setOrigem(origem);
		this.setDestino(destino);
		this.setData(data);
		this.setHora(hora);
		this.setVagas(vagas);
		this.setCriador(criador);
		this.pontosSugeridos = new HashMap<String, String>();
		this.solicitacoes = new ArrayList<Solicitacao>();
		this.caroneiros = new ArrayList<Usuario>();

		GeradorDeID gerador = new GeradorDeID();
		id = gerador.geraId();
	}

	public HashMap<String, String> getPontosSugeridos() {
		return pontosSugeridos;
	}

	public void setPontosSugeridos(HashMap<String, String> pontosSugeridos) {
		this.pontosSugeridos = pontosSugeridos;
	}

	public List<Usuario> getCaroneiros() {
		return caroneiros;
	}

	public void setCaroneiros(List<Usuario> caroneiros) {
		this.caroneiros = caroneiros;
	}

	public void addCaroneiro(Usuario caroneiro) {
		this.caroneiros.add(caroneiro);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	private void setCriador(Usuario criador) {
		this.criador = criador;
	}

	public String getId() {
		return id;
	}

	public String getOrigem() {
		return origem;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public Usuario getCriador() {
		return criador;
	}

	public void addSolicitacao(Solicitacao solicitacao) {
		solicitacoes.add(solicitacao);
	}

	public void setOrigem(String origem) throws Exception {
		if (origem == null || origem.equals("") || contemCharInvalidos(origem)) {
			throw new Exception("Origem inválida");
		} else {
			this.origem = origem;
		}
	}

	public String getDestino() {
		return destino;
	}

	public HashMap<String, String> getPontos() {
		return pontosSugeridos;
	}

	public void setPontos(String id, String pontos) {

		if (pontos != null && !pontos.equals("")) {
			pontosSugeridos.put(id, pontos);
		}

	}

	public void setDestino(String destino) throws Exception {
		if (destino == null || destino.equals("")
				|| contemCharInvalidos(destino)) {
			throw new Exception("Destino inválido");
		} else {
			this.destino = destino;
		}
	}

	private static boolean contemCharInvalidos(String nome) {
		String[] palavra = nome.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%¨¨¨&*()+¹²³¢¬§=[{ª]}º;:>,</|/0123456789*-"
					.contains(palavra[i])) {
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
			throw new Exception("Data inválida");
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
			throw new Exception("Data inválida.");
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

		// Compara com a data atual
		if (comparaDataAtual(dia, mes, ano) < 0) {
			return false;
		}

		// Aceita a data
		return true;

	}

	private static int comparaDataAtual(int dia, int mes, int ano) {
		Date date = new Date();
		if (ano == date.getYear() + 1900) {
			if (mes == date.getMonth() + 1) {
				if (dia == date.getDay() + 1) {
					return 0;
				} else {
					if (dia > date.getDay() + 1) {
						return 1;
					} else {
						return -1;
					}
				}
			} else {
				if (mes > date.getMonth() + 1) {
					return 1;
				} else {
					return -1;
				}
			}
		} else {
			if (ano > date.getYear() + 1900) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public String getHora() throws Exception {
		return this.hora;
	}

	public void setHora(String hora) throws Exception {
		if (hora == null || hora.equals("") || !validaHora(hora)) {
			throw new Exception("Hora inválida");
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
		int intVagas = (Integer) vagas;

		return intVagas;
	}

	// verificar
	public void setVagas(Object vagas) throws Exception {

		if (vagas == null) {
			throw new Exception("Vaga inválida");
		}

		int intVagas;

		try {
			intVagas = (Integer) vagas;

		} catch (Exception e) {
			throw new Exception("Vaga inválida");
		}

		String stringVagas = "" + vagas;

		if (intVagas <= 0 || stringVagas == null) {
			throw new Exception("Vaga inválida");
		}
		this.vagas = intVagas;
	}

	public static boolean temInvalidoNosNumeros(String s) {
		if (!"0123456789".contains(s)) {
			return true;
		} else {
			return false;
		}
	}

	public void removeSolicitacao(Solicitacao solicitacao) {

		solicitacoes.remove(solicitacao);

	}

	public void removeSugestao(String idSugestao) {
		pontosSugeridos.remove(idSugestao);

	}

	@Override
	public String toString() {

		return "origem=" + origem + " destino=" + destino + " data=" + data
				+ " hora=" + hora + " vagas=" + vagas;
	}

}
