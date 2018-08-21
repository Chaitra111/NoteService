package com.bridgelabz.eurekaServerNoteService.note.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bridgelabz.eurekaServerNoteService.exceptionService.NoteExceptionHandler;
import com.bridgelabz.eurekaServerNoteService.exceptionService.ToDoException;
import com.bridgelabz.eurekaServerNoteService.model.Description;
import com.bridgelabz.eurekaServerNoteService.model.Link;
import com.bridgelabz.eurekaServerNoteService.model.Label;
import com.bridgelabz.eurekaServerNoteService.model.NoteDTO;
import com.bridgelabz.eurekaServerNoteService.model.NoteModel;
import com.bridgelabz.eurekaServerNoteService.repository.LabelElasticRepository;
import com.bridgelabz.eurekaServerNoteService.repository.LabelRepository;
import com.bridgelabz.eurekaServerNoteService.repository.NoteElasticRepository;
import com.bridgelabz.eurekaServerNoteService.repository.NoteMongoRepository;
import com.bridgelabz.eurekaServerNoteService.utility.JsoupImpl;
import com.bridgelabz.eurekaServerNoteService.utility.Messages;
import com.bridgelabz.eurekaServerNoteService.utility.PreConditions;
import com.bridgelabz.eurekaServerNoteService.utility.Utility;

/**
 * @author Chaitra Ankolekar
 * Purpose :Implementation for all the api's in services
 */
@Service
public class NoteServiceImplementation implements NoteServices {
	
	@Autowired
	NoteMongoRepository noteRepository;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LabelElasticRepository labelElasiticRepo;
	
	@Autowired
	private NoteElasticRepository noteElasiticRepo;
	
	@Autowired
	ModelMapper model;
	
	@Autowired
	Messages messages;
	
	@Autowired
	Utility utility;
	
	NoteModel notes = new NoteModel();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImplementation.class);
	
	@Override
	public void doCreateNote(NoteDTO note, String id) throws IOException, NoteExceptionHandler {

		PreConditions.checkNotNull(note.getTitle(), messages.get("13"));
		PreConditions.checkNotNull(note.getDescription(), messages.get("14"));
		NoteModel noteModel = model.map(note, NoteModel.class);
		if (!note.getDescription().equals("")) {
			String noteDescription = note.getDescription();
			noteModel.setDescription(makeDescription(noteDescription));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		noteModel.setId(sdf.format(new Date()));
		noteModel.setEditedAt(formatter.format(new Date()));
		noteRepository.save(noteModel);
		noteElasiticRepo.save(noteModel);
	}

	/**
	 * @param noteDescription
	 * @return
	 * @throws IOException
	 */
	public static Description makeDescription(String noteDescription) throws IOException {
		Description desc = new Description();
		List<Link> linkList = new ArrayList<>();
		List<String> simpleList = new ArrayList<>();
		String[] descriptionArray = noteDescription.split(" ");
		for (int i = 0; i < descriptionArray.length; i++) {
			if (descriptionArray[i].startsWith("http://") || descriptionArray[i].startsWith("https://")) {
				Link link = new Link();
				link.setLinkTitle(JsoupImpl.getTitle(descriptionArray[i]));
				link.setLinkDomainName(JsoupImpl.getDomain(descriptionArray[i]));
				link.setLinkImage(JsoupImpl.getImage(descriptionArray[i]));
				System.out.println(link);
				linkList.add(link);
			} else if (!descriptionArray[i].equals("")
					&& (!descriptionArray[i].startsWith("http://") || !descriptionArray[i].startsWith("https://"))) {
				simpleList.add(descriptionArray[i]);
			}
		}
		desc.setSimpleDescription(simpleList);
		desc.setLinkDescription(linkList);
		return desc;
	}

	
	 @Override public void updateNote(NoteDTO n, String id) throws ToDoException,
	 IOException {
	  
	  logger.info("updating the note"); 
	  Optional<NoteModel> note = noteElasiticRepo.findById(id);//noteId);
	  PreConditions.isPresentInDb(note.isPresent(), messages.get("1")); 
	  notes = note.get(); 
	  if (!notes.getDescription().equals("")) { 
	  notes.setDescription(makeDescription(n.getDescription()));
	  } 
	  notes.setTitle(n.getTitle());//title);
	  notes.setCreatedAt(formatter.format(new Date()));
	  notes.setEditedAt(formatter.format(new Date())); 
	  noteRepository.save(notes);
	  noteElasiticRepo.save(notes); }

	@Override
	public void trashNote(String id) throws ToDoException {

		Optional<NoteModel> note = noteRepository.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));
		NoteModel notes = note.get();
		notes.setTrash(true);
		noteRepository.save(notes);
		noteElasiticRepo.save(notes);
	}

	@Override
	public void deleteNote(String noteId, String id) throws ToDoException, NoteExceptionHandler {

		logger.info("deleting the note");
		Optional<NoteModel> note = noteElasiticRepo.findById(noteId);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));
		noteRepository.deleteById(noteId);
		noteElasiticRepo.deleteById(noteId);
	}

	@Override
	public List<NoteModel> displayNotes(String id) {

		List<NoteModel> note = noteRepository.findNoteById(id);
		List<NoteModel> stream = note.stream().filter(pair -> pair.getId().equals(id)).collect(Collectors.toList());
		return stream;
	}

	@Override
	public List<NoteModel> viewNotes(String emailId) throws ToDoException {

		logger.info("view notes");
		System.out.println(emailId);
		// Optional<User> users = userRepository.findByemailId(mailId);
		// System.out.println(mailId);
		// PreConditions.isPresentInDb(users.isPresent(), messages.get("2"));
		List<NoteModel> note = noteRepository.findNoteByemailId(emailId);
		/*
		 * System.out.println(note); if(note==null) {
		 * System.out.println(messages.get("1")); }
		 */

		return note;
	}

	@Override
	public void setArchive(String id) {

		logger.info("setting archive");
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		if (note.isPresent()) {
			notes = note.get();
			System.out.println(notes.getCreatedAt());
			notes.setArchive(true);
			noteRepository.save(notes);
			noteElasiticRepo.save(notes);
			logger.info("note archived");
		}
	}

	@Override
	public void setPin(String id) throws ToDoException {

		logger.info("setting pin");
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));
		notes = note.get();
		notes.setpin(true);
		noteRepository.save(notes);
		noteElasiticRepo.save(notes);
		logger.info("note pinned");
	}

	@Override
	public NoteModel setReminder(String id, String reminderTime) throws Exception {

		logger.info("setting reminder");
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		Timer timer = null;
		if (note.isPresent()) {
			Date reminder = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(reminderTime);
			long timeDifference = reminder.getTime() - new Date().getTime();
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					System.out.println("Reminder");// + note.toString());

				}
			}, timeDifference);
		}
		return note.get();
	}

	@Override
	public List<NoteModel> sortbyTitle(String id) throws ToDoException {

		List<NoteModel> list = noteRepository.findNoteByemailId(id);
		System.out.println(list);
		// Preconditions.checkNotNull(list,"List empty");
		if (list == null) {
			throw new ToDoException(messages.get("1"));
		}
		List<NoteModel> stream = list.stream().sorted((l1, l2) -> l1.getEmailId().compareToIgnoreCase(l2.getEmailId()))
				.collect(Collectors.toList());
		return stream;

	}

	@Override
	public void createlabel(Label label, String userId) throws NoteExceptionHandler, ToDoException {

		logger.info("creating label");
		if (label == null)
			throw new ToDoException("Note cannot be created with empty title and description");
		List<Label> list = labelElasiticRepo.findAllByUserId(userId);
		for (Label l : list) {
			if (l.getLabelName().equals(label.getLabelName())) {
				throw new NoteExceptionHandler(messages.get("3"));
			}
		}
		label.setUserId(userId);
		label.setLabelName(label.getLabelName());
		try {
			labelRepository.save(label);
			labelElasiticRepo.save(label);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new NoteExceptionHandler(messages.get("4"));
		}
		logger.info("label created");
	}

	@Override
	public void addlabel(String noteid, String labelname) throws ToDoException {
		Label label = new Label();
		Optional<NoteModel> note = noteRepository.findById(noteid);
		if (note.get().getLabelName() == null) {
			List<Label> newLabelList = new ArrayList<>();
			note.get().setLabelName(newLabelList);
		}
		List<Label> list = labelRepository.findAll();
		for (Label l : list) {

			if (l.getLabelName().equals(labelname)) {
				Optional<NoteModel> optionalnote = noteRepository.findById(noteid);
				PreConditions.isPresentInDb(optionalnote.isPresent(), messages.get("1"));
				//label.setUserId(notes.getId());
				label.setLabelName(labelname);
				label.setLabelId(l.getLabelId());
				note.get().getLabelName().add(label);
				noteRepository.save(note.get());
				noteElasiticRepo.save(note.get());
			}
		}
	}

	@Override
	public void updateLabel(Label l) throws NoteExceptionHandler, ToDoException {

		logger.info("updating label");
		Optional<Label> label = labelRepository.findById(l.getLabelId());
		PreConditions.isPresentInDb(label.isPresent(), messages.get("5"));
		Label labels = label.get();
		labels.setLabelName(l.getLabelName());
		//Optional<NoteModel> note = noteRepository.findById(notes.getId());
		//notes.getLabelName().add(l);
		labelRepository.save(label.get());
		labelElasiticRepo.save(label.get());
		logger.info("label updated");
	}

	@Override
	public void deleteLabel(String id) throws NoteExceptionHandler, ToDoException {

		logger.info("deleting label");
		Optional<Label> optionallabel = labelElasiticRepo.findById(id);
		PreConditions.isPresentInDb(optionallabel.isPresent(), messages.get("5"));
		labelRepository.deleteById(id);
		labelElasiticRepo.deleteById(id);
	}

	/*@Override
	public List<Label> displayLabels() throws NoteExceptionHandler {

		List<Label> label = (List<Label>) labelElasiticRepo.findAll();
		//List<Label> stream = label.stream().filter(p -> p.getLabelId().equals(id)).collect(Collectors.toList());
		return label;
	}*/
	@Override
	public List<Label> displayLabels(String labelId) throws ToDoException {

		List<Label> labelList = labelRepository.findAllByLabelId(labelId);
		return labelList;

	}
}