package logica;

import java.util.ArrayList;
import java.util.HashMap;

import xml.FactoryXml;

public class CaronaMunicipal extends CaronaAbstrata{

	private String cidade;

	public CaronaMunicipal(String origem, String destino, String cidade,String data, String hora,
			Object vagas, String criador) throws Exception {

		super.setOrigem(origem);
		super.setDestino(destino);
		super.setData(data);
		super.setHora(hora);
		super.setVagas(vagas);
		super.setCriador(criador);
		super.pontosSugeridos = new HashMap<String, String>();
		super.solicitacoes = new ArrayList<Solicitacao>();
		super.caroneirosConfirmados = new ArrayList<Solicitacao>();
		super.reviews = new ArrayList<Review>();
		GeradorDeID gerador = new GeradorDeID();
		id = gerador.geraId();
		this.cidade = cidade;
		setVagasTotal((Integer) vagas);
		
		super.setFinalizada(false);
		
		super.xmlCreator = new FactoryXml("Xml carona");
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

}
