package com.bridgelabz.eurekaServerNoteService.utility;

import org.springframework.lang.Nullable;

import com.bridgelabz.eurekaServerNoteService.exceptionService.NoteExceptionHandler;
import com.bridgelabz.eurekaServerNoteService.exceptionService.ToDoException;

/**
 * @author Chaitra Ankolekar
 * Purpose :PreConditions class to check the fields
 */
public class PreConditions {

	/**
	 * @param reference
	 * @param errorMessage
	 * @return
	 * @throws NoteExceptionHandler 
	 */
	public static <T> T checkNull(T reference,@Nullable Object errorMessage) throws NoteExceptionHandler {
		if(reference=="") {
			throw new NoteExceptionHandler("Note already created");
		}
		return reference;
	}
	public static <T> T checkNotNull(T reference,@Nullable Object errorMessage) throws NoteExceptionHandler{
		if(reference=="") {
			throw new NoteExceptionHandler("Note id doesnot  exist");
		}
		return reference;
	}
	public  <T> T CheckPassword(T resource) throws NoteExceptionHandler  {
        if (resource == null) {
            throw new NoteExceptionHandler(("invalid password"));
        }
        return resource;
}
	public static boolean isPresentInDb(boolean reference,@Nullable Object errorMessage) throws ToDoException {
        if (!reference) {
            throw new ToDoException(String.valueOf(errorMessage));
        }
        return reference;
}
}


