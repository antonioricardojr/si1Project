package logica;

public class ValidadorDeEmail {
	
	public boolean validaEmail(String email) throws Exception{
		
		if(email.endsWith("@") || email.endsWith(".") || email.endsWith("_")){
			return false;
		}
		
		String[] substrings = email.split("@");
		
		if(substrings.length != 2){
			return false;
		}
		
		String substring1 = substrings[0];
		String substring2 = substrings[1];
		
		if(substring1.contains("@") || substring2.contains("@")){
			return false;
		}
		
		if(substring1.equals(".") || substring1.equals("_")){
			return false;
		}
		
		
		
		String[] caracInvalidos = {"!","@", "#", "$", "%", "¨", "&", "*", "(", 
				")", "-", "+", "=", "¹", "²", "³", "£", "¢", "¬", "§", "/", "|", "?", ":", ";", ",", "<", 
				">",  "°"};
		
		for(String s : caracInvalidos){
			if(substring1.contains(s) || substring2.contains(s)){
				return false;
			}
		}

		
		String[] s1 = substring1.split("");
		
		String[] s2 = substring2.split("");
		
		
		for(int i = 1 ; i < s1.length - 1; i++){
			
			
			if(s1[i].equals(".") || s1.equals("_")){
				if(i == 1){
					return false;
				}else{
					int j = i + 1;
					if(j < s1.length){
						if(s1[j].equals(".") || s1[j].equals("_")){
							return false;
						}
					}
				}
				
			}
			
		}
		
		
		
		for(int i = 1 ; i < s2.length - 1 ; i++){
			
			
			if(s2[i].equals(".") || s2[i].equals("_")){
				if(i == 1){
					return false;
				}else{
					int j = i + 1;
					if(j < s2.length){
						if(s2[j].equals(".")){
							return false;
						}
					}
				}
			}
			
		}
		
		
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
