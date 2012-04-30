package si1.xml;

import org.jdom2.Element;

import si1.Excecoes.XMLNaoGeradaException;

public class XmlSistema implements Xml {
	
private Element raiz;
	
	public XmlSistema(){
		this.raiz = new Element("Sistema");
	}

	@Override
	public void geraXML(Object o) {
		this.raiz.addContent((Element) o);
		
	}

	@Override
	public Element getRaiz() throws XMLNaoGeradaException {
		if(this.raiz == null){
			throw new XMLNaoGeradaException();
		}
		return this.raiz;
	}

}
