package com.staxrt.tutorial.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Block;
import com.staxrt.tutorial.model.Desks;
import com.staxrt.tutorial.repository.BlockRepository;
import com.staxrt.tutorial.repository.DeskRepository;
import com.staxrt.tutorial.repository.FloorRepository;

@RestController
@RequestMapping("/api/v1")
public class DeskController {

	@Autowired
	private DeskRepository deskRepository;
	
	@Autowired
	private BlockRepository blockRepository;
	
	  @GetMapping("/desks")
	  public List<Desks> getAllDesks() {
	    return deskRepository.findAll();
	  }

	  
	  @GetMapping("/desk/{id}")
	  public ResponseEntity<Desks> getDeskById(@PathVariable(value = "id") Long deskId)
	      throws ResourceNotFoundException {
	    Desks desk =
	        deskRepository
	            .findById(deskId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + deskId));
	    return ResponseEntity.ok().body(desk);
	  }

	  
	  
	  @PostMapping("/blocks/{id}/desks")
	  public Desks createBlock(@PathVariable(value = "id") Long blockId,@Valid @ModelAttribute Desks desk) {
	      desk.setBlockId(blockId); 
		  return deskRepository.save(desk);
	  }

	 
	  @PutMapping("/desk/{id}")
	  public ResponseEntity<Desks> updateBlock(
	      @PathVariable(value = "id") Long deskId, @Valid @ModelAttribute Desks deskDetails)
	      throws ResourceNotFoundException {

	    Desks desk =
	        deskRepository
	            .findById(deskId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + deskId));

	    desk.setDeskName(deskDetails.getDeskName());
	    final Desks updatedDesk = deskRepository.save(desk);
	    return ResponseEntity.ok().body(updatedDesk);
	  }

	  
	  @DeleteMapping("/desk/{id}")
	  public Map<String, Boolean> deleteDesk(@PathVariable(value = "id") Long deskId) throws Exception {
	    Desks desk =
	        deskRepository
	            .findById(deskId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + deskId));

	    deskRepository.delete(desk);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
	  
	  @GetMapping("/desks/associations")
	  public ResponseEntity<List<Desks>> findAssociatedDesks(){
		  List<Desks> desks=deskRepository.findAssociatedDesks();
		  return ResponseEntity.ok().body(desks);
		  
	  }
	  
	  @GetMapping("/desks/nonassociations")
	  public ResponseEntity<List<Desks>> findNonAssociatedDesks(){
		 List<Desks> desks= deskRepository.findNonAssociatedDesks();
		  return ResponseEntity.ok().body(desks);
		  
	  }
}