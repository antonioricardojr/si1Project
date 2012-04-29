package si1.xml;

import org.jdom2.Element;

import si1.Excecoes.XMLNaoGeradaException;

public interface Xml {

	public void geraXML(Object o);

	public Element getRaiz() throws XMLNaoGeradaException;

}
