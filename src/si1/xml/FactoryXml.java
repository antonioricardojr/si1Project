package si1.xml;

import org.jdom2.Element;

import si1.Excecoes.XMLNaoGeradaException;

public class FactoryXml implements Xml{
	
	Xml factory;
	
	public FactoryXml(String tipo){
		if(tipo.equals("usuario")){
			this.factory = new XmlUsuario();
		}else if(tipo.equals("sistema")){
			this.factory = new XmlUsuariosSistema();
		}
			
	}

	@Override
	public void geraXML(Object o) {
		this.factory.geraXML(o);
	}

	@Override
	public Element getRaiz() throws XMLNaoGeradaException {
		return this.factory.getRaiz();
	}

}
