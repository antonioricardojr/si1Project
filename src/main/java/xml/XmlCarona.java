package xml;

import logica.CaronaAbstrata;
import logica.Review;
import logica.Solicitacao;

import org.jdom2.Element;

import excecoes.XMLNaoGeradaException;


public class XmlCarona implements Xml{
	
	private Element raiz;
	
	public XmlCarona(){
		this.raiz = null;
	}

	public void geraXML(Object o) {
		//Cria a raiz da carona
		this.raiz = new Element("carona");
		
		//Cast do objeto para carona
		CaronaAbstrata carona = (CaronaAbstrata) o;
		
		//Adiciona um atributo id a carona no xml
		this.raiz.setAttribute("id", carona.getId());
		
		//Cria os campos do XML
		Element origem = new Element("origem");
		Element destino = new Element("destino");
		Element data = new Element("data");
		Element hora = new Element("hora");
		Element vagas = new Element("vagas");
		Element vagasTotal = new Element("vagasTotal");
		Element pontosSugeridos = new Element("pontosSugeridos");
		Element pontoDeEncontro = new Element("pontoDeEncontro");
		Element solicitacoes = new Element("solicitacoes");
		Element caroneirosConfirmados = new Element("caroneirosConfirmados");
		Element criador = new Element("criador");
		Element reviews = new Element("reviews");
		Element isFinalizada = new Element("isFinalizada");
		
		//Adicionando conteudo aos elementos
		origem.addContent(carona.getOrigem());
		destino.addContent(carona.getDestino());
		data.addContent(carona.getData());
		hora.addContent(carona.getHora());
		vagas.addContent(""+carona.getVagas());
		vagasTotal.addContent(""+carona.getVagasTotal());
		pontoDeEncontro.addContent(carona.getPontoDeEncontro());
		
		this.raiz.addContent(origem);
		this.raiz.addContent(destino);
		this.raiz.addContent(data);
		this.raiz.addContent(hora);
		this.raiz.addContent(vagas);
		this.raiz.addContent(vagasTotal);
		
		for(String id : carona.getPontosSugeridos().keySet()){
			Element pontos = new Element("pontos");
			pontos.setAttribute("id",id);
			pontos.addContent(carona.getPontosSugeridos().get(id));
			pontosSugeridos.addContent(pontos);
		}
		
		this.raiz.addContent(pontosSugeridos);
		
		this.raiz.addContent(pontoDeEncontro);
		
		//Adicionando as solicitacoes
		for(Solicitacao s : carona.getSolicitacoes()){
			//Cria um elemento solicitacao com todos os campos da solicitacao
			Element solicitacao = new Element("solicitacao");
			Element idSoliciatacao = new Element("idSolicitacao");
			Element loginSolicitador = new Element("loginSolicitador");
			Element idCarona = new Element("idCarona");
			Element ponto = new Element("ponto");
			Element estado = new Element("estado");
			
			//adiciona conteudo aos elementos
			idSoliciatacao.addContent(s.getId());
			loginSolicitador.addContent(s.getSolicitador());
			idCarona.addContent(s.getCarona());
			ponto.addContent(s.getPonto());
			estado.addContent(s.getEstado());
			
			//adiciona os elementos a solicitacao
			solicitacao.addContent(idSoliciatacao);
			solicitacao.addContent(loginSolicitador);
			solicitacao.addContent(idCarona);
			solicitacao.addContent(ponto);
			solicitacao.addContent(estado);
			
			//adiciona a solicitacao nas solicitacoes
			solicitacoes.addContent(solicitacao);			
		}
		
		this.raiz.addContent(solicitacoes);
		
		//Adicionando os caroneiros
		for(Solicitacao s : carona.getCaroneiros()){
			//Cria um elemento solicitacao com todos os campos da solicitacao
			Element solicitacao = new Element("solicitacao");
			Element idSoliciatacao = new Element("idSolicitacao");
			Element loginSolicitador = new Element("loginSolicitador");
			Element idCarona = new Element("idCarona");
			Element ponto = new Element("ponto");
			Element estado = new Element("estado");
			
			//adiciona conteudo aos elementos
			idSoliciatacao.addContent(s.getId());
			loginSolicitador.addContent(s.getSolicitador());
			idCarona.addContent(s.getCarona());
			ponto.addContent(s.getPonto());
			estado.addContent(s.getEstado());
			
			//adiciona os elementos a solicitacao
			solicitacao.addContent(idSoliciatacao);
			solicitacao.addContent(loginSolicitador);
			solicitacao.addContent(idCarona);
			solicitacao.addContent(ponto);
			solicitacao.addContent(estado);
			
			//adiciona a solicitacao nas solicitacoes
			caroneirosConfirmados.addContent(solicitacao);			
		}
		
		this.raiz.addContent(caroneirosConfirmados);
		
		criador.addContent(carona.getCriador());
		
		this.raiz.addContent(criador);

		
		for(Review r : carona.getReviews()){
			Element review = new Element("review");
			Element reviewId = new Element("reviewId");
			Element usuarioLogin = new Element("usuarioLogin");
			Element idCarona = new Element("idCarona");
			Element reviewTexto = new Element("reviewTexto");
			
			
			reviewId.addContent(r.getId());
			usuarioLogin.addContent(r.getUsuario());
			idCarona.addContent(r.getCarona());
			reviewTexto.addContent(r.getReview());
			
			review.addContent(reviewId);
			review.addContent(usuarioLogin);
			review.addContent(idCarona);
			review.addContent(reviewTexto);
			
			
			reviews.addContent(review);
		}
		
		this.raiz.addContent(reviews);

		
		if(carona.isFinalizada()){
			isFinalizada.addContent("finalizada");
		}else{
			isFinalizada.addContent("nao finalizada");
		}
		
		this.raiz.addContent(isFinalizada);
		
	}

	public Element getRaiz() throws XMLNaoGeradaException {
		if(this.raiz == null){
			throw new XMLNaoGeradaException();
		}
		return this.raiz;
	}
	
	

}
