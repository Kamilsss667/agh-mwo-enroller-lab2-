package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		{
			return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findByID(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
		if (meetingService.findByID(meeting.getID()) != null) {
			return new ResponseEntity<String>(
					"Unable to create. A meeting with id " + meeting.getID() + " already exist.",
					HttpStatus.CONFLICT);
		}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<?> delete(@PathVariable("id") String id) {
//		Meeting meeting = meetingService.findByID(id);
//		if (meeting == null) {
//			return new ResponseEntity(HttpStatus.NOT_FOUND);
//		}
//		meetingService.delete(meeting);
//		return new ResponseEntity<Participant>(HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Meeting updatedMeeting) {
//		Meeting meeting = meetingService.findByID(id);
//		if (meeting == null) {
//			return new ResponseEntity(HttpStatus.NOT_FOUND);
//		}
//		meeting.setPassword(updatedMeeting.getPassword());
//		meetingService.update(meeting);
//		return new ResponseEntity<Participant>(HttpStatus.OK);
//	}
//
}
