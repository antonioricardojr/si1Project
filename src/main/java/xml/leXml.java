package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import logica.Carona;
import logica.Review;
import logica.Solicitacao;
import logica.Usuario;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import excecoes.XMLNaoGeradaException;


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
	
	@SuppressWarnings({ "rawtypes", "null", "unchecked"})
	public List<Carona> getCaronas() throws Exception{
		Element caronas = this.elementCaronas();
		List elements = caronas.getChildren();
		Iterator i = elements.iterator();
		List<Carona> caronaList = new ArrayList<Carona>();
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
			Carona carona = new Carona(origem, destino, data, hora, Integer.parseInt(vagas), criador);
			carona.setVagasTotal(Integer.parseInt(vagasTotal));
			carona.setPonto(pontoDeEncontro);
			carona.setId(id);
			System.out.println();
			
			//Adicionando os pontos sugeridos em um mapa
			Map<String,String> pontosSugeridos = null;
			Element pontos = element.getChild("pontosSugeridos");
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
				caroneirosConfirmados.add(s);
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

	
	public List<Usuario> getUsuarios() throws Exception{
		Element usuarios = this.elementUsuarios();
		List elements = usuarios.getChildren();
		Iterator i = elements.iterator();
		List<Usuario> usuarioList = new ArrayList<Usuario>();
		while(i.hasNext()){
			Element element = (Element) i.next();
			//recupera os elementos
			String login = element.getChildText("login");
			String senha = element.getChildText("senha");
			String endereco = element.getChildText("endereco");
			String email = element.getChildText("email");
			String nome = element.getChildText("nome");
			
			//cria o objeto do tipo usuario
			Usuario usuario = new Usuario(login,senha,nome,endereco, email);
			
			//recupera as caronas
			
			Element caronas = element.getChild("caronas");
			
			List elementsCarona = caronas.getChildren();
			
			Iterator i2 = elementsCarona.iterator();
			
			List<String> caronasList = new ArrayList<String>();
			
			while(i2.hasNext()){
				Element idCarona = (Element) i2.next();
				String id = idCarona.getChildText("caronaId");
				caronasList.add(id);
			}
			
			usuario.setCaronas(caronasList);
			
			
			//recupera as caronas como caroneiro
			
			Element caronasComoCaroneiro = element.getChild("caronasComoCaroneiro");
			
			List elementsCaronaCC = caronasComoCaroneiro.getChildren();
			
			Iterator i3 = elementsCaronaCC.iterator();
			
			List<String> caronasCCList = new ArrayList<String>();
			
			while(i3.hasNext()){
				Element idCarona = (Element) i3.next();
				caronasCCList.add(idCarona.getChildText("caronaId"));
			}
			
			usuario.setCaronasComoCaroneiro(caronasCCList);
			
			//recupera as caronas oferecidas
			
			Element caronasOferecidas = element.getChild("caronasOferecidas");
			
			List elementsCaronaO = caronasOferecidas.getChildren();
			
			Iterator i4 = elementsCaronaO.iterator();
			
			List<String> caronasOList = new ArrayList<String>();
			
			while(i4.hasNext()){
				Element idCarona = (Element) i4.next();
				caronasOList.add(idCarona.getChildText("caronaId"));
			}
			
			usuario.setCaronasOferecidas(caronasOList);
			
			//recupera os amigos
			
			Element amigos = element.getChild("amigos");
			
			List elementsAmigos = amigos.getChildren();
			
			Iterator i5 = elementsAmigos.iterator();
			
			List<String> amigosList = new ArrayList<String>();
			
			while(i5.hasNext()){
				Element idAmigo = (Element) i5.next();
				amigosList.add(idAmigo.getChildText("amigoLogin"));
			}
			usuario.setAmigos(amigosList);
			
			usuarioList.add(usuario);
		}
		
		return usuarioList;
		
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
	
	private Element elementUsuarios() throws JDOMException, IOException, XMLNaoGeradaException{
		
		Element root = this.le();
		Element usuario = null;
		@SuppressWarnings("rawtypes")
		List elements = root.getChildren();
		@SuppressWarnings("rawtypes")
		Iterator i = elements.iterator();
		while(i.hasNext()){
			Element element = (Element) i.next();
			if(element.getName().equals("usuariosDoSistema")){
				usuario = element;
				break;
			}
			
		}
		if(usuario!=null){
			return usuario;
		}else{
			throw new XMLNaoGeradaException();
		}
		
	}
	
	
	public static void main(String[] args) throws Exception{
		leXml l = new leXml("arquivo.xml");
		List<Carona> c = l.getCaronas();
		List<Usuario> u = l.getUsuarios();
		for(Usuario o : u){
			System.out.println(o.getLogin());
			for(String ca: o.getCaronasOferecidas()){
				System.out.println(ca);
				
			}
		}
		
	}

}
