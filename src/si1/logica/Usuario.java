package si1.logica;

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

	public Usuario(String login, String senha, String nome, String endereco,
			String email) throws Exception {

		setLogin(login);
		setSenha(senha);
		setNome(nome);
		setEndereco(endereco);
		setEmail(email);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws Exception {
		if (login == null || login.equals("")
				|| contemCharInvalidosLogin(login)) {
			throw new Exception("Login nulo ou invalido.");
		}
		this.login = login;
	}

	private boolean contemCharInvalidosLogin(String string) {

		String[] palavra = string.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%���&*()+�������=[{�~^]}�;:>,</|�/`���������������������������������� "
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
			throw new Exception("senha nula ou invalida.");
		}
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		if (nome == null || nome.equals("") || contemCharInvalidosNome(nome)) {
			throw new Exception("Nome nulo ou invalido.");
		}
		this.nome = nome;
	}

	private boolean contemCharInvalidosNome(String nome) {
		String[] palavra = nome.trim().split("");

		for (int i = 1; i < palavra.length; i++) {
			if ("'!@#$%���&*()+�������=[{�~^]}�;:>,</|�/`".contains(palavra[i])) {
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
			throw new Exception("Endereco nulo ou invalido.");
		}

		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		if (email == null || email.equals("")) {
			throw new Exception("E-mail nulo ou invalido.");
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
					throw new Exception("E-mail nulo ou invalido.");

				}
				break;
			}
		}

		String[] arraySubs2 = substring2.split("");

		for (int i = 1; i < arraySubs2.length; i++) {
			if (arraySubs2[i].equals("@")) {
				throw new Exception("E-mail nulo ou invalido.");
			}
			if (arraySubs2[i].equals(".")) {
				try {
					if ("qweasdzxcrtyfghvbnuiojklnmp"
							.contains(caracteres[i - 1])
							&& "qweasdzxcrtyfghvbnuiojklnmp"
									.contains(caracteres[i + 1])) {

					} else {
						throw new Exception("E-mail nulo ou invalido.");
					}
				} catch (Exception e) {
					throw new Exception("E-mail nulo ou invalido.");

				}
			}

		}

		if (substring1.equals(null) || substring1.equals("")) {
			throw new Exception("Email nulo ou invalido.");
		}

		if (substring2 == null || substring2.equals("")
				|| substring2.contains("@")) {
			throw new Exception("Email nulo ou invalido.");
		}

		this.email = email;

	}

	public static void main(String[] args) {
		String a = "login";
		String[] s = a.split("");
		for (int i = 0; i < s.length; i++) {
			System.out.println(i + s[i]);
		}
	}
}
