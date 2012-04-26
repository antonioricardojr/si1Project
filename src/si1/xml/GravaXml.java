package si1.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class GravaXml {
	
	private Element raiz;
	
	public GravaXml(Element e){
		this.raiz = e;
	}
	
	public void gravar(String endereco) throws IOException{
		Document doc = new Document();
		doc.setRootElement(this.raiz);
		XMLOutputter xout = new XMLOutputter();
		FileWriter arquivo = new FileWriter(new File(endereco));
		xout.output(doc, arquivo);
		arquivo.close();
	}

}
