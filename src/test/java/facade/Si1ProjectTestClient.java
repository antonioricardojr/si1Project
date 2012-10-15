package facade;


import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class Si1ProjectTestClient {
	
	/**
	 * @param args 
	 */
	public static void main(String[] args) {

		List<String> files = new ArrayList<String>();
		// Scripts de testes do EasyAccept
		files.add("scripts/US01.txt");
		files.add("scripts/US02.txt");
		files.add("scripts/US03.txt");
		files.add("scripts/US04.txt");
		files.add("scripts/US05.txt");
		files.add("scripts/US06.txt");
		files.add("scripts/US07.txt");
		files.add("scripts/US08.txt");
		files.add("scripts/US09.txt");
		files.add("scripts/US10.txt");
		files.add("scripts/US11.txt");
		files.add("scripts/US12.txt");
		files.add("scripts/US15.txt");
		files.add("scripts/US16.txt");
	
		Si1ProjectFacade si1ProjectFacade = new Si1ProjectFacade();
		// Instantiate EasyAccept fa�ade
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(si1ProjectFacade,
				files);

		eaFacade.executeTests();

		System.out.println(eaFacade.getCompleteResults());
	}

}
