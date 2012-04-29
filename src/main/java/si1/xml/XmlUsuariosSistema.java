package si1.xml;

import org.jdom2.Element;

import si1.Excecoes.XMLNaoGeradaException;

public class XmlUsuariosSistema implements Xml {
	
	private Element raiz;
	
	public XmlUsuariosSistema(){
		this.raiz = new Element("usuariosDoSistema");
	}

	public void geraXML(Object o) {
		this.raiz.addContent((Element) o);
		
	}

	public Element getRaiz() throws XMLNaoGeradaException {
		if(this.raiz == null){
			throw new XMLNaoGeradaException();
		}
		return this.raiz;
	}
	

	
	

}
