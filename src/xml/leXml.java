package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
			//System.out.println(id);
			
			//Adicionando os pontos sugeridos em um mapa
			Map<String,String> pontosSugeridos = new HashMap<String, String>();
			Element pontos = element.getChild("pontosSugeridos");
			List elementsPontos = pontos.getChildren();
			Iterator i2 = elementsPontos.iterator();
			
			while(i2.hasNext()){
				Element element2 = (Element) i2.next();
				pontosSugeridos.put(element2.getAttributeValue("id"), element2.getText());
			}
			carona.setPontosSugeridos(pontosSugeridos);
			
			//Adicionando as solicitacoes numa lista
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			
			
			
			Element solicitacoesElement = this.buscaElement("solicitacoes", element);
			
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
			
			carona.setSolicitacoes(solicitacoes);
			
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
			
			
			carona.setCaroneirosConfirmados(caroneirosConfirmados);
			
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
			
			//recupera as caronas oferecidas
			
			Element caronasOferecidas = element.getChild("caronasOferecidas");
			
			List elementsCaronaO = caronasOferecidas.getChildren();
		
			
			Iterator i4 = elementsCaronaO.iterator();
			
			List<String> caronasOList = new ArrayList<String>();
			
			while(i4.hasNext()){
			
				Element idCarona = (Element) i4.next();
				String iddacarona = idCarona.getText();
				caronasOList.add(iddacarona);
			}
			
			usuario.setCaronasOferecidas(caronasOList);
			
			//recupera as caronas como caroneiro
				
			List elementsCaronaCC = element.getChildren("caronasComoCaroneiro");
			
			Iterator i3 = elementsCaronaCC.iterator();
			
			List<String> caronasCCList = new ArrayList<String>();
			
			while(i3.hasNext()){
				Element idCarona = (Element) i3.next();
				caronasCCList.add(idCarona.getText());
			}
			
			usuario.setCaronasComoCaroneiro(caronasCCList);
			//recupera as caronas
			
			//Element elementCarona  = element.getChild("caronas");
			
			List<Element> elementsCarona = element.getChildren("caronas");
			
			Iterator i2 = elementsCarona.iterator();
			
			List<String> caronasList = new ArrayList<String>();
			
			while(i2.hasNext()){
				Element idCarona = (Element) i2.next();
				String id = idCarona.getText();
				caronasList.add(id);
			}
			
			usuario.setCaronas(caronasList);
			
			//recupera os amigos
			
			Element amigos = element.getChild("amigos");
			
			List elementsAmigos = amigos.getChildren();
			
			Iterator i5 = elementsAmigos.iterator();
			
			List<String> amigosList = new ArrayList<String>();
			
			while(i5.hasNext()){
				Element idAmigo = (Element) i5.next();
				amigosList.add(idAmigo.getText());
			}
			usuario.setAmigos(amigosList);
			
			usuarioList.add(usuario);
		}
		
		return usuarioList;
		
	}

	
	@SuppressWarnings("unused")
	private Element buscaElement(String nome, Element e) throws JDOMException, IOException, XMLNaoGeradaException{
		
		Element elemento = null;
		@SuppressWarnings("rawtypes")
		List elements = e.getChildren();
		@SuppressWarnings("rawtypes")
		Iterator i = elements.iterator();
		while(i.hasNext()){
			Element element = (Element) i.next();
			if(element.getName().equals(nome)){
				elemento = element;
				break;
			}
			
		}
		if(elemento!=null){
			return elemento;
		}else{
			throw new XMLNaoGeradaException();
		}
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
		System.out.println(u);
		for(Usuario o : u){
			
			System.out.println(o.getLogin() + " " + o.getEndereco() + " " + o.getSenha());
			for(String ca: o.getCaronasOferecidas()){
				System.out.println(ca);
				
			}
		}
		
		for(Carona i: c){
			List<Solicitacao> o = i.getCaroneiros();
			System.out.println(i + i.getId() + " " + i.getCriador());
		}
		
	}

}
