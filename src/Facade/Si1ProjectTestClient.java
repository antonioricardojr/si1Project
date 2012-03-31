package Facade;


import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class Si1ProjectTestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<String> files = new ArrayList<String>();
		// Put the us1.txt file into the "test scripts" list
		files.add("US/US01.txt");
		
		// Instantiate the Monopoly Game façade
		Si1ProjectFacade si1ProjectFacade = new Si1ProjectFacade();
		// Instantiate EasyAccept façade
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(si1ProjectFacade,
				files);

		eaFacade.executeTests();

		System.out.println(eaFacade.getCompleteResults());
	}

}
