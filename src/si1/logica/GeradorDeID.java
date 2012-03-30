package si1.logica;
/**
 * Gera um id aleatório de tamanho descrito
 * 
 * @author ANTONIOR
 *
 */

public class GeradorDeID {

	static int SIZE = 15;

	public String geraId(){
		String id = "";
		for (int i = 0; i < SIZE; i++) {
			id += (int) (Math.random()*10);
		}

		return id;
	}
	
	
	public static void main(String[] args) {
		GeradorDeID gerador = new GeradorDeID();
		
		System.out.println(gerador.geraId());
	}

}
