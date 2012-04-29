package si1.xml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	@SuppressWarnings({ "rawtypes", "null" })
	public List getCaronas() throws JDOMException, IOException, XMLNaoGeradaException{
		Element caronas = this.elementCaronas();
		List elements = caronas.getChildren();
		Iterator i = elements.iterator();
		while(i.hasNext()){
			
			//Pegando os dados do XML
			Element element = (Element) i.next();
			String id = element.getAttributeValue("id");
			String origem = element.getChildText("origem");
			String destino = element.getChildText("destino");
			String data = element.getChildText("data");
			String hora = element.getChildText("hora");
			String vagas = element.getChildText("vagas");
			String pontoDeEncontro = element.getChildText("pontoDeEncontro");
			String criador = element.getChildText("criador");
			//String vagasTotal = element.getChildText("vagasTotal");
			//Carona carona = new Carona(origem, destino, data, hora, vagas, criador);
			
			//Adicionando os pontos sugeridos em um mapa
			Map<String,String> pontosSugeridos = null;
			Element pontos = element.getChild("pontos");
			List elementsPontos = pontos.getChildren();
			Iterator i2 = elementsPontos.iterator();
			
			while(i2.hasNext()){
				Element element2 = (Element) i2.next();
				pontosSugeridos.put(element2.getAttributeValue("id"), element2.getChildText("pontos"));
			}
			
			//Adicionando as solicitacoes numa lista
			List<String> solicitacoes;
			
			Element solicitacoesElement = element.getChild("solicitacoes");
			
			List solicitacoesList = solicitacoesElement.getChildren();
			
			Iterator i3 = solicitacoesList.iterator();
			
			while(i3.hasNext()){
				Element element3 = (Element) i3.next();
				
				String idSoliciatacao = element3.getChildText("idSolicitacao");
				String loginSolicitador = element3.getChildText("loginSolicitador");
				String idCarona = element3.getChildText("idCarona");
				String ponto = element3.getChildText("ponto");
				String estado = element3.getChildText("estado");
				
				//Solicitacao s = new Solicitacao(idSoliciacao,)
				//solicitacoes.add(s);
			}
			
			
			
			
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
