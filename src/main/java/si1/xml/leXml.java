package si1.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import si1.Excecoes.XMLNaoGeradaException;
import si1.logica.Carona;
import si1.logica.Review;
import si1.logica.Solicitacao;

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
	
	@SuppressWarnings({ "rawtypes", "null", "unchecked", "unused" })
	public List<Carona> getCaronas() throws Exception{
		Element caronas = this.elementCaronas();
		List elements = caronas.getChildren();
		Iterator i = elements.iterator();
		List<Carona> caronaList = new ArrayList();
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
			String vagasTotal = element.getChildText("vagasTotal");
			Carona carona = new Carona(origem, destino, data, hora, vagas, criador);
			carona.setVagasTotal(Integer.parseInt(vagasTotal));
			carona.setPonto(pontoDeEncontro);
			carona.setId(id);
			
			//Adicionando os pontos sugeridos em um mapa
			Map<String,String> pontosSugeridos = null;
			Element pontos = element.getChild("pontos");
			List elementsPontos = pontos.getChildren();
			Iterator i2 = elementsPontos.iterator();
			
			while(i2.hasNext()){
				Element element2 = (Element) i2.next();
				pontosSugeridos.put(element2.getAttributeValue("id"), element2.getChildText("pontos"));
			}
			carona.setPontosSugeridos(pontosSugeridos);
			
			//Adicionando as solicitacoes numa lista
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			
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
				
				Solicitacao s = new Solicitacao(idSoliciatacao,loginSolicitador,idCarona,ponto);
				s.setEstado(estado);
				solicitacoes.add(s);
			}
			
			//Adicionando os caroneiros confirmados
			List<Solicitacao> caroneirosConfirmados = new ArrayList<Solicitacao>();
			
			Element caroneirosElement = element.getChild("caroneirosConfirmados");
			
			List caroneirosList = caroneirosElement.getChildren();
			
			Iterator i4 = caroneirosList.iterator();
			while(i4.hasNext()){
				Element element4 = (Element) i4.next();
				
				String idSoliciatacao = element4.getChildText("idSolicitacao");
				String loginSolicitador = element4.getChildText("loginSolicitador");
				String idCarona = element4.getChildText("idCarona");
				String ponto = element4.getChildText("ponto");
				String estado = element4.getChildText("estado");
				
				Solicitacao s = new Solicitacao(idSoliciatacao,loginSolicitador,idCarona,ponto);
				s.setEstado(estado);
				caroneirosList.add(s);
			}
			
			carona.setCaroneirosConfirmados(caroneirosList);
			
			//Adiciona a lista de Reviews
			List<Review> reviews = new ArrayList<Review>();
			
			Element reviewsElement = element.getChild("reviews");
			
			List reviewList = reviewsElement.getChildren();
			
			Iterator i5 = reviewList.iterator();
			
			while(i5.hasNext()){
				Element element5 = (Element) i5.next();
				String reviewId = element5.getChildText("reviewId");
				String usuarioLogin = element5.getChildText("usuarioLogin");
				String idCarona = element5.getChildText("idCarona");
				String reviewTexto = element5.getChildText("reviewTexto");
				
				
				Review r = new Review(reviewId,usuarioLogin,idCarona,reviewTexto);
				reviews.add(r);
			}
			
			carona.setReviews(reviews);
			
			String isFinalizada = element.getChildText("isFinalizada");
			if(isFinalizada.equals("finalizada")){
				carona.setFinalizada(true);
			}else{
				carona.setFinalizada(false);
			}
		
			caronaList.add(carona);
		}

		
		return caronaList;
		
	}
	
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
