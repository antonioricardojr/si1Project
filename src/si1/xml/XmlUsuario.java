package si1.xml;

import org.jdom2.Element;

import si1.Excecoes.XMLNaoGeradaException;
import si1.logica.Carona;
import si1.logica.Usuario;

public class XmlUsuario implements Xml{
	
	private Element raiz;
	
	public XmlUsuario(){
		this.raiz = null;
	}

	public void geraXML(Object o) {
		this.raiz = new Element("usuario");
		
		//Cria os campos do XML
		Usuario usuario = (Usuario) o;
		Element login = new Element("login");
		Element senha = new Element("senha");
		Element nome = new Element("nome");
		Element endereco = new Element("endereco");
		Element caronasOferecidas = new Element("caronasOferecidas");
		Element caronasComoCaroneiro = new Element("caronasComoCaroneiro");
		Element caronas = new Element("caronas");
		Element amigos = new Element("amigos");
		
		//Adiciona o login, senha, nome e endereco do usuario no XML
		login.addContent(usuario.getLogin());
		senha.addContent(usuario.getSenha());
		nome.addContent(usuario.getNome());
		endereco.addContent(usuario.getEndereco());
		
		this.raiz.addContent(login);
		this.raiz.addContent(senha);
		this.raiz.addContent(nome);
		this.raiz.addContent(endereco);
		
		
		//Pega os ids das caronas oferecidas e coloca no xml
		for(Carona c : usuario.getCaronasOferecidas()){
			Element caronaId = new Element("caronaId");
			caronaId.addContent(c.getId());
			caronasOferecidas.addContent(caronaId);
		}
		
		this.raiz.addContent(caronasOferecidas);
		
		//Pega os ids das caronas como caroneiro e coloca no XML
		for(Carona c : usuario.getCaronasComoCaroneiro()){
			Element caronaId = new Element("caronaId");
			caronaId.addContent(c.getId());
			caronasComoCaroneiro.addContent(caronaId);
		}
		
		this.raiz.addContent(caronasComoCaroneiro);
		
		//Pega os ids das caronas e coloca no xml
		for(Carona c : usuario.getCaronas()){
			Element caronaId = new Element("caronaId");
			caronaId.addContent(c.getId());
			caronas.addContent(caronaId);
		}
		
		this.raiz.addContent(caronas);
		
		//Pega o login dos amigos e coloca no xml
		for(Usuario a : usuario.getAmigos()){
			Element amigoLogin = new Element("amigoLogin");
			amigoLogin.addContent(a.getLogin());
			amigos.addContent(amigoLogin);
		}
		
		this.raiz.addContent(amigos);
		
	}

	@Override
	public Element getRaiz() throws XMLNaoGeradaException {
		if(this.raiz == null){
			throw new XMLNaoGeradaException();
		}
		return this.raiz;
	}

	

}
