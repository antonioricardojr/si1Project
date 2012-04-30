package xml;

import org.jdom2.Element;

import excecoes.XMLNaoGeradaException;


public interface Xml {

	public void geraXML(Object o);

	public Element getRaiz() throws XMLNaoGeradaException;

}
