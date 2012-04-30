package si1.logica;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import si1.Excecoes.LoginInvalidoException;
import si1.Excecoes.XMLNaoGeradaException;
import si1.xml.FactoryXml;
import si1.xml.Xml;


/**
 * Classe que representa um usuario do sistema.
 * 
 * @author ANTONIOR
 * 
 */
public class Usuario {

	private String login;
	private String senha;
	private String nome;
	private String endereco;
	private String email;

	private List<String> caronasOferecidas;
	private List<String> caronasComoCaroneiro;

	private List<String> caronas;

	private List<String> amigos;
	
	private Xml xmlCreator;

	public Usuario(String login, String senha, String nome, String endereco,
			String email) throws Exception {

		setLogin(login);
		setSenha(senha);
		setNome(nome);
		setEndereco(endereco);
		setEmail(email);

		caronasOferecidas = new ArrayList<String>();
		caronasComoCaroneiro = new ArrayList<String>();

		setCaronas(new ArrayList<String>());

		setAmigos(new ArrayList<String>());
		this.xmlCreator = new FactoryXml("Xml usuario");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws Exception {

		if (login == null || login.equals("")
				|| contemCharInvalidosLogin(login)) {
			throw new LoginInvalidoException();
		}
		this.login = login;
	}

	private boolean contemCharInvalidosLogin(String string) {

		String[] palavra = string.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%¨¨¨&*()+¹²³£¢¬§=[{ª~^]}º;:>,</|´/`áéíóúàèìòùÁÉÍÓÚÀÈÌÒÙãõÃÕâêîôûÂÊÎÔÛ "
					.contains(palavra[i])) {
				return true;
			}
		}

		return false;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws Exception {
		if (senha == null || senha == "") {
			throw new Exception("Senha inválida");
		}
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		if (nome == null || nome.equals("") || contemCharInvalidosNome(nome)) {
			throw new Exception("Nome inválido");
		}
		this.nome = nome;
	}

	private boolean contemCharInvalidosNome(String nome) {
		String[] palavra = nome.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%¨¨¨&*()+¹²³£¢¬§=[{ª~^]}º;:>,</|´/`".contains(palavra[i])) {
				return true;
			}
		}

		return false;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) throws Exception {
		if (endereco == null || endereco.equals("")) {
			throw new Exception("Endereço inválido");
		}

		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		if (email == null || email.equals("")) {
			throw new Exception("Email inválido");
		}
		String[] caracteres = email.split("");
		String substring1 = "";
		String substring2 = "";

		for (int i = 1; i < caracteres.length; i++) {
			if (!caracteres[i].equals("@")) {
				substring1 += caracteres[i];
			} else {

				try {
					substring2 = email.substring(i + 1);
				} catch (Exception e) {
					throw new Exception("Email inválido");

				}
				break;
			}
		}

		String[] arraySubs2 = substring2.split("");

		for (int i = 1; i < arraySubs2.length; i++) {
			if (arraySubs2[i].equals("@")) {
				throw new Exception("Email inválido");
			}
			if (arraySubs2[i].equals(".")) {
				try {
					if ("qweasdzxcrtyfghvbnuiojklnmp"
							.contains(caracteres[i - 1])
							&& "qweasdzxcrtyfghvbnuiojklnmp"
									.contains(caracteres[i + 1])) {

					} else {
						throw new Exception("Email inválido.");
					}
				} catch (Exception e) {
					throw new Exception("Email inválido");

				}
			}

		}

		if (substring1.equals(null) || substring1.equals("")) {
			throw new Exception("Email inválido");
		}

		if (substring2 == null || substring2.equals("")
				|| substring2.contains("@")) {
			throw new Exception("Email inválido");
		}

		this.email = email;

	}

	public List<String> getCaronasOferecidas() {
		return caronasOferecidas;
	}

	public void adicionaCaronaOferecida(String novaCarona) {
		caronasOferecidas.add(novaCarona);

	}

	public void removeCaronaOferecida(String caronaARemover) {
		caronasOferecidas.remove(caronaARemover);
	}

	public List<String> getCaronasComoCaroneiro() {
		return caronasComoCaroneiro;
	}

	public void adicionaCaronaComoCaroneiro(String novaCarona) {
		caronasComoCaroneiro.add(novaCarona);
	}

	public void removeCaronaComoCaroneiro(Carona caronaARemover) {
		caronasComoCaroneiro.remove(caronaARemover);
	}

	public List<String> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<String> amigos) {
		this.amigos = amigos;
	}

	public void adicionaAmigo(String u) throws Exception {
		if (u != null) {
			amigos.add(u);
		} else {
			throw new Exception("Amigo inválido");
		}
	}

	public List<String> getCaronas() {
		return caronas;
	}

	public void setCaronas(List<String> caronas) {
		this.caronas = caronas;
	}

	public void adicionaCarona(String c) {
		if (c != null) {
			caronas.add(c);
		}
	}

	public void removeCarona(Carona c) {
		if (c != null) {
			caronas.remove(c);
		}
	}
	
	public void geraXml(){
		this.xmlCreator.geraXML(this);
	}
	
	public Element getXml() throws XMLNaoGeradaException{
		return this.xmlCreator.getRaiz();
	}
	
	public void setCaronasComoCaroneiro(List<String> lista){
		caronasComoCaroneiro = lista;
	}
	
	public void setCaronasOferecidas(List<String> lista){
		caronasOferecidas = lista;
	}
}
