package logica;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Classe que representa uma Carona relâmpago.
 * 
 * @author ANTONIOR
 *
 */
public class Escapada extends Carona {

	private int minCaroneiros; // número mínimo de caroneiros para realizar a carona relâmpago.

	public Escapada(String origem, String destino, String data, String hora,
			Object vagas, String criador) throws Exception {
		super(origem, destino, data, hora, vagas, criador);
		setMinCaroneiros(minCaroneiros);
	}

	public int getMinCaroneiros() {
		return minCaroneiros;
	}

	public void setMinCaroneiros(int minCaroneiros) {

		if (minCaroneiros <= vagasTotal) {
			this.minCaroneiros = minCaroneiros;
		}
	}

	
	/**
	 * Verifica e faz a confirmação da Carona 'Escapada'.
	 * 
	 * 
	 * @return boolean
	 */
	public boolean confirmaCarona() {

		Date date = new Date();
		date.setDate(Integer.parseInt(data.substring(0, 1)));
		date.setMonth(Integer.parseInt(data.substring(3, 4)) - 1);
		date.setYear(Integer.parseInt(data.substring(6)) - 1900);

		date.setHours(Integer.parseInt(hora.substring(0, 1)));
		date.setMinutes(Integer.parseInt(hora.substring(3, 4)));
		date.setSeconds(Integer.parseInt(hora.substring(6, 7)));
		
		GregorianCalendar calendar = new GregorianCalendar();
		Date dataDeHoje = calendar.getTime();
		Date horaAtual = calendar.getTime();

		if (date.compareTo(dataDeHoje) == 1 && (date.getHours() - horaAtual.getHours()) >= 48) {
			

				if (caroneirosConfirmados.size() >= minCaroneiros
						&& caroneirosConfirmados.size() <= vagasTotal) {
					return true;
				}
		}else{
			enviaEmailParaCaroneiros();
		}

		return false;
	}

	
	/**
	 * Envia e-mail para caroneiros avisando que a carona não será realizada.
	 * 
	 */
	private void enviaEmailParaCaroneiros() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		Date date = new Date();
		date.setDate(30);
		date.setMonth(05);
		date.setYear(112);

		System.out.println(date);

		GregorianCalendar calendar = new GregorianCalendar();
		Date dataDeHoje = calendar.getTime();
		System.out.println(dataDeHoje);

		System.out.println(date.compareTo(dataDeHoje));

		System.out.println(date.getHours());
		System.out.println(date.getMinutes());

	}
}
