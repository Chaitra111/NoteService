package com.bridgelabz.eurekaServerNoteService.exceptionService;

/**
 * @author Chaitra Ankolekar
 * Purpose :NoteExceptionHandler
 */
public class NoteExceptionHandler extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public NoteExceptionHandler(String message) {

		super(message);

	}

}