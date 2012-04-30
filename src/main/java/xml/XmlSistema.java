package xml;

import org.jdom2.Element;

import excecoes.XMLNaoGeradaException;


public class XmlSistema implements Xml {
	
private Element raiz;
	
	public XmlSistema(){
		this.raiz = new Element("Sistema");
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
