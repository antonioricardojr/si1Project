package xml;

import logica.Usuario;

import org.jdom2.Element;

import excecoes.XMLNaoGeradaException;


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
		Element email = new Element("email");
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
		email.addContent(usuario.getEmail());
		
		this.raiz.addContent(login);
		this.raiz.addContent(senha);
		this.raiz.addContent(nome);
		this.raiz.addContent(endereco);
		this.raiz.addContent(email);
		
		
		//Pega os ids das caronas oferecidas e coloca no xml
		for(String c : usuario.getCaronasOferecidas()){
			Element caronaId = new Element("caronaId1");
			caronaId.addContent(c);
			caronasOferecidas.addContent(caronaId);
		}
		
		this.raiz.addContent(caronasOferecidas);
		
		//Pega os ids das caronas como caroneiro e coloca no XML
		for(String c : usuario.getCaronasComoCaroneiro()){
			Element caronaId = new Element("caronaId2");
			caronaId.addContent(c);
			caronasComoCaroneiro.addContent(caronaId);
		}
		
		this.raiz.addContent(caronasComoCaroneiro);
		
		//Pega os ids das caronas e coloca no xml
		for(String c : usuario.getCaronas()){
			Element caronaId = new Element("caronaId3");
			caronaId.addContent(c);
			caronas.addContent(caronaId);
		}
		
		this.raiz.addContent(caronas);
		
		//Pega o login dos amigos e coloca no xml
		for(String a : usuario.getAmigos()){
			Element amigoLogin = new Element("amigoLogin");
			amigoLogin.addContent(a);
			amigos.addContent(amigoLogin);
		}
		
		this.raiz.addContent(amigos);
		
	}

	public Element getRaiz() throws XMLNaoGeradaException {
		if(this.raiz == null){
			throw new XMLNaoGeradaException();
		}
		return this.raiz;
	}

	

}
