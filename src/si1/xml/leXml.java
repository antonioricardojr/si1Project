package si1.xml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import si1.Excecoes.XMLNaoGeradaException;
import si1.logica.Carona;

public class leXml {
	
	private String endereco;
	
	public leXml(String endereco){
		this.endereco = endereco;
	}
	
	public Element le() throws JDOMException, IOException{
		
		File f = new File(this.endereco);
		
		SAXBuilder sb = new SAXBuilder();
		
		Document d = sb.build(f);
		
		Element xml = d.getRootElement();
		
		return xml;
	}
	
	@SuppressWarnings("rawtypes")
	public List getCaronas() throws JDOMException, IOException, XMLNaoGeradaException{
		Element caronas = this.elementCaronas();
		List elements = caronas.getChildren();
		Iterator i = elements.iterator();
		while(i.hasNext()){
			Element element = (Element) i.next();
			String id = element.getAttributeValue("id");
			String origem = element.getChildText("origem");
			String destino = element.getChildText("destino");
			String data = element.getChildText("data");
			String hora = element.getChildText("hora");
			String vagas = element.getChildText("vagas");
			String vagasTotal = element.getChildText("vagasTotal");
			
			//Carona carona = new Carona();
		}

		
		return null;
		
	}
	
	@SuppressWarnings("unused")
	private Element elementCaronas() throws JDOMException, IOException, XMLNaoGeradaException{
		Element root = this.le();
		Element carona = null;
		@SuppressWarnings("rawtypes")
		List elements = root.getChildren();
		@SuppressWarnings("rawtypes")
		Iterator i = elements.iterator();
		while(i.hasNext()){
			Element element = (Element) i.next();
			if(element.getName().equals("caronasDoSistema")){
				carona = element;
				break;
			}
			
		}
		if(carona!=null){
			return carona;
		}else{
			throw new XMLNaoGeradaException();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getUsuarios(){
		return null;
	}

}
