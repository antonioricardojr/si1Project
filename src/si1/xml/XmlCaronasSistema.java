package si1.xml;

import org.jdom2.Element;

import si1.Excecoes.XMLNaoGeradaException;

public class XmlCaronasSistema implements Xml {

	private Element raiz;
	
	public XmlCaronasSistema(){
		this.raiz = new Element("caronasDoSistema");
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
