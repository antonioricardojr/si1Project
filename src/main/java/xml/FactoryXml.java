package xml;

import org.jdom2.Element;

import excecoes.XMLNaoGeradaException;


public class FactoryXml implements Xml{
	
	Xml factory;
	
	public FactoryXml(String tipo){
		if(tipo.equals("Xml usuario")){
			this.factory = new XmlUsuario();
		}else if(tipo.equals("Xml Usuarios do sistema")){
			this.factory = new XmlUsuariosSistema();
		}else if(tipo.equals("Xml carona")){
			this.factory = new XmlCarona();
		}else if(tipo.equals("Xml Caronas do sistema")){
			this.factory = new XmlCaronasSistema();
		}else if(tipo.equals("Xml Sistema")){
			this.factory= new XmlSistema();
		}
		
			
	}

	public void geraXML(Object o) {
		this.factory.geraXML(o);
	}

	public Element getRaiz() throws XMLNaoGeradaException {
		return this.factory.getRaiz();
	}

}
