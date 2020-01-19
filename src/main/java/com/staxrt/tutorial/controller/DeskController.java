package com.staxrt.tutorial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	  
	  @GetMapping("/desks/{id}")
	  public ResponseEntity<Desks> getDeskById(@PathVariable(value = "id") Long deskId)
	      throws ResourceNotFoundException {
	    Desks desk =
	        deskRepository
	            .findById(deskId)
	            .orElseThrow(() -> new ResourceNotFoundException("Desk not found on :: " + deskId));
	    return ResponseEntity.ok().body(desk);
	  }

	  
	  
	  @PostMapping("/blocks/{id}/desks")
	  public Desks createDesk(@PathVariable(value = "id") Long blockId,@Valid @ModelAttribute Desks desk) throws ResourceNotFoundException{
		 blockRepository
			            .findById(blockId)
			            .orElseThrow(() -> new ResourceNotFoundException("Block not found on :: " + blockId));
		  desk.setBlockId(blockId); 
		  return deskRepository.save(desk);
	  }

	 
	  @PutMapping("/desks/{id}")
	  public ResponseEntity<Desks> updateDesk(
	      @PathVariable(value = "id") Long deskId, @Valid @ModelAttribute Desks deskDetails)
	      throws ResourceNotFoundException {

	    Desks desk =
	        deskRepository
	            .findById(deskId)
	            .orElseThrow(() -> new ResourceNotFoundException("Desk not found on :: " + deskId));

	    desk.setDeskName(deskDetails.getDeskName());
	    final Desks updatedDesk = deskRepository.save(desk);
	    return ResponseEntity.ok().body(updatedDesk);
	  }

	  
	  @DeleteMapping("/desks/{id}")
	  public Map<String, Boolean> deleteDesk(@PathVariable(value = "id") Long deskId) throws Exception {
	    Desks desk =
	        deskRepository
	            .findById(deskId)
	            .orElseThrow(() -> new ResourceNotFoundException("Desk not found on :: " + deskId));

	    deskRepository.delete(desk);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
	  
	  @GetMapping("/blocks/{id}/desks")
	  public ResponseEntity<List<Desks>> getDesksByBlock(@PathVariable(value = "id") Long blockId) throws ResourceNotFoundException{
			        blockRepository
			            .findById(blockId)
			            .orElseThrow(() -> new ResourceNotFoundException("Block not found on :: " + blockId)); 
		  List<Desks> desks=deskRepository.findAll();
		  List<Desks> filteredDesks=new ArrayList<>();
		  if(desks!=null)
	    filteredDesks =
	       desks.stream().filter(block -> block.getBlockId()==blockId).collect(Collectors.toList());
	    return ResponseEntity.ok().body(filteredDesks);

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