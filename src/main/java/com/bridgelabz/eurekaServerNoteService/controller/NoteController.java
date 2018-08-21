package com.bridgelabz.eurekaServerNoteService.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.eurekaServerNoteService.exceptionService.ToDoException;
import com.bridgelabz.eurekaServerNoteService.exceptionService.NoteExceptionHandler;
import com.bridgelabz.eurekaServerNoteService.model.Label;
import com.bridgelabz.eurekaServerNoteService.model.NoteDTO;
import com.bridgelabz.eurekaServerNoteService.model.NoteModel;
import com.bridgelabz.eurekaServerNoteService.model.Response;
import com.bridgelabz.eurekaServerNoteService.note.service.FeignService;
import com.bridgelabz.eurekaServerNoteService.note.service.NoteServiceImplementation;

/**
 * @author Chaitra Ankolekar
 * Purpose :NoteController with API
 */

@RefreshScope
@RestController
@RequestMapping(value="/notes")
public class NoteController {

	@Autowired
	NoteServiceImplementation services;
	
	@Autowired
	FeignService service;
	
	/**
	 * @param note
	 * @param request
	 * @return response
	 * @throws IOException
	 * @throws NoteExceptionHandler
	 */
	@RequestMapping(value = "/createnoteLink", method = RequestMethod.POST)
	public ResponseEntity<Response> createLinkNote(@RequestBody NoteDTO note, HttpServletRequest request )
			throws IOException, NoteExceptionHandler {
		//String email=request.getHeader("Authorization");
		String email = (String) request.getAttribute("Authorization");
		System.out.println(email);
		//String email=red.getFromRedis("userId");
		services. doCreateNote(note,email);
		Response response = new Response();
		response.setMessage("created note link Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Method to update the note
	 * @param jwtToken
	 * @param title
	 * @param content
	 * @param noteId
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 * @throws NoteExceptionHandler 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/updatenote/{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO note,@PathVariable String noteId, HttpServletRequest request ) throws ToDoException, NoteExceptionHandler, IOException {
		String email = request.getHeader("userId");
		//System.out.println(email);
		//String email=request.getHeader("Token");
		System.out.println(email);
		services.updateNote(note,noteId);
		Response response = new Response();
		response.setMessage("updated note successfull!!");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	/**
	 * Method to delete the note
	 * @param title
	 * @param token
	 * @param note
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 * @throws NoteExceptionHandler 
	 */
	@RequestMapping(value = "/deletenote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable String id, HttpServletRequest request )
			throws ToDoException, NoteExceptionHandler {
		//String email = (String) request.getAttribute("Authorization");
		String email=request.getHeader("Token");
		services.deleteNote(id,email);
		Response response = new Response();
		response.setMessage("note deleted successfull!!");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	/**
	 * Method to display all the notes
	 * @param token
	 * @return
	 * @throws ToDoException
	 * @throws NoteExceptionHandler 
	 * @throws LoginExceptionHandler 
	 */
	@RequestMapping(value = "/viewnotes", method = RequestMethod.GET)
	public List<NoteModel> viewNotes(HttpServletRequest request) throws ToDoException {
		String jwtToken=request.getHeader("userId");
		//System.out.println("id:"+jwtToken);
		List<NoteModel> note=services.viewNotes(jwtToken);
		return note;
	}
	/**
	 * @param id
	 * @param request
	 * @return
	 * @throws NoteExceptionHandler
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/displayNote/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<NoteModel>> displayNotes(@PathVariable String id, HttpServletRequest request) throws NoteExceptionHandler, ToDoException {
		//String jwtToken = request.getHeader("Authorization");
		List<NoteModel> note=services.displayNotes(id);
		return new ResponseEntity<List<NoteModel>>(note,HttpStatus.CREATED);
	}	
	
	/**
	 * @param request
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/sortbytitle", method = RequestMethod.GET)
	public List<NoteModel> sortTitle(HttpServletRequest request) throws ToDoException {
		String jwtToken=request.getHeader("Token");
		//System.out.println(jwtToken);
		List<NoteModel> note=services.sortbyTitle(jwtToken);
		return note;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/setpin/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> setPin(@PathVariable String id, HttpServletRequest request) throws ToDoException{
		String email = (String) request.getAttribute("Token");
		services.setPin(id);
		Response response = new Response();
		response.setMessage("note pinned");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/setarchive/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> setArchive(@PathVariable String id, HttpServletRequest request) throws ToDoException{
		String email = (String) request.getAttribute("Token");
		services.setArchive(id);
		Response response = new Response();
		response.setMessage("note archived");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/trashnote{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> trashNote(@PathVariable String noteId) throws ToDoException {
		services.trashNote(noteId);
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("note sent totrash");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
}
	/**
	 * @param tokenbridgeit
	 * @param id
	 * @param reminderTime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/setreminder/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> setReminder(@PathVariable String id,@RequestParam String reminderTime, HttpServletRequest request) throws Exception {
	
		String email = (String) request.getAttribute("Token");
		NoteModel note=services.setReminder(id,reminderTime);
		return new ResponseEntity<>(new Response("Response:" +note.toString(),200),HttpStatus.OK);
	}
		
	/**
	 * @param label
	 * @param jwtToken
	 * @param request
	 * @return response
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 * @throws NoteExceptionHandler
	 */
	@RequestMapping(value = "/createlabel", method = RequestMethod.POST)
	public ResponseEntity<Response> createlabel(@RequestBody Label label, HttpServletRequest request) throws ToDoException, NoteExceptionHandler{
		String email = (String) request.getAttribute("Token");
		System.out.println("create note:"+email);
		services.createlabel(label, email);
		Response response = new Response();
		response.setMessage("created label Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	/**
	 * @param noteId
	 * @param labelName
	 * @param jwtToken
	 * @return response
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 * @throws NoteExceptionHandler
	 */
	@RequestMapping(value = "/addlabel/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<Response> addLabel(@PathVariable String noteId ,@RequestParam String labelName, HttpServletRequest request) throws ToDoException, NoteExceptionHandler{
		//String email = (String) request.getAttribute("Authorization");
		services.addlabel(noteId,labelName);
		Response response = new Response();
		response.setMessage("adding label successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param labelName
	 * @param noteId
	 * @param httpServletRequest
	 * @return
	 * @throws ToDoException 
	 * @throws NoteExceptionHandler 
	 */
	@RequestMapping(value = "/updatelabel/{labelId}", method = RequestMethod.PUT)
    public ResponseEntity<Response> updateLabel(@RequestBody Label label, HttpServletRequest request) throws NoteExceptionHandler, ToDoException {
		//String email = (String) request.getAttribute("Authorization");
        services.updateLabel(label);
        Response response = new Response();
		response.setMessage("Label Updated");
		response.setStatus(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	/**
	 * @param id
	 * @return
	 * @throws NoteExceptionHandler 
	 * @throws ToDoException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/deleteLabel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Response> deletelabel(@PathVariable String id, HttpServletRequest request) throws NoteExceptionHandler, ToDoException {
		String jwtToken = request.getHeader("Token");
		services.deleteLabel(id);
		Response response = new Response();
		response.setMessage("Label  Deleted");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param id
	 * @param request
	 * @return
	 * @throws NoteExceptionHandler
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/displayLabel/{labelId}", method = RequestMethod.GET)
    public List<Label> displaylabel(@PathVariable String labelId) throws NoteExceptionHandler, ToDoException {
		//String jwtToken = request.getHeader("Authorization");
		List<Label> label=services.displayLabels(labelId);
		return label;
	}	
	
	/**
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public List<?> getAllUser()throws ToDoException {
		List<?> user=service.getUsers();
		return user;
	}
}