package com.bridgelabz.eurekaServerNoteService.note.service; 

import java.io.IOException;
import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.bridgelabz.eurekaServerNoteService.exceptionService.ToDoException;
import com.bridgelabz.eurekaServerNoteService.exceptionService.NoteExceptionHandler;
import com.bridgelabz.eurekaServerNoteService.model.Label;
import com.bridgelabz.eurekaServerNoteService.model.NoteDTO;
import com.bridgelabz.eurekaServerNoteService.model.NoteModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :NoteService to perform CRUD operation on note
 */
public interface NoteServices {
	
	List<NoteModel> viewNotes(String noteId) throws ToDoException, NoteExceptionHandler;

	void deleteNote(String id, String token) throws ToDoException, NoteExceptionHandler;

	void setPin(String id) throws ToDoException;

	void setArchive(String id);

	NoteModel setReminder(String id, String reminderTime) throws Exception;
	
	public void createlabel(Label label, String token) throws NoteExceptionHandler, ToDoException;

	void addlabel(String noteid, String labelname)throws NoteExceptionHandler, ToDoException ;

	void deleteLabel(String id) throws NoteExceptionHandler, ToDoException;

	void trashNote(String id) throws ToDoException;

	void doCreateNote(NoteDTO note, String tpken) throws IOException, NoteExceptionHandler;

	List<NoteModel> sortbyTitle(String noteId) throws ToDoException;

	List<NoteModel> displayNotes(String id);

	void updateNote(NoteDTO n, String id) throws ToDoException, IOException;

	void updateLabel(Label l) throws NoteExceptionHandler, ToDoException;

	List<Label> displayLabels(String userId) throws ToDoException;
}