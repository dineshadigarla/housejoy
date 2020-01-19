package com.staxrt.tutorial.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.staxrt.tutorial.model.Building;
import com.staxrt.tutorial.model.Desks;
import com.staxrt.tutorial.model.Floor;
import com.staxrt.tutorial.repository.BlockRepository;
import com.staxrt.tutorial.repository.DeskRepository;
import com.staxrt.tutorial.repository.FloorRepository;

@RestController
@RequestMapping("/api/v1")
public class BlockController {

	@Autowired
	private BlockRepository blockRepository;
	
	@Autowired
	private FloorRepository floorRepository;
	
	@Autowired
	private DeskRepository deskRepository;
	
	  @GetMapping("/blocks")
	  public List<Block> getAllBuidlings() {
	    return blockRepository.findAll();
	  }

	  
	  @GetMapping("/blocks/{id}")
	  public ResponseEntity<Block> getBlocksById(@PathVariable(value = "id") Long blockId)
	      throws ResourceNotFoundException {
	    Block block =
	        blockRepository
	            .findById(blockId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + blockId));
	    return ResponseEntity.ok().body(block);
	  }
	  
	  @GetMapping("/blocks/{id}/nonassociations")
	  public ResponseEntity<List<Desks>> getNonAssociatedDesks(@PathVariable(value = "id") Long blockId)
	      throws ResourceNotFoundException {
	    List<Desks> block =
	        deskRepository
	            .findNonAssociatedDesksForBlock(blockId);
	    return ResponseEntity.ok().body(block);
	  }

	  @GetMapping("/blocks/{id}/associations")
	  public ResponseEntity<List<Desks>> getAssociatedDesks(@PathVariable(value = "id") Long blockId)
	      throws ResourceNotFoundException {
	    List<Desks> block =
	        deskRepository
	            .findAssociatedDesksForBlock(blockId);
	    return ResponseEntity.ok().body(block);
	  }
	  
	  
	  @PostMapping("/floors/{id}/blocks")
	  public Block createBlock(@PathVariable(value = "id") Long floorId,@Valid @ModelAttribute Block block) {
	      block.setFloorId(floorId); 
		  return blockRepository.save(block);
	  }
	  
	  @GetMapping("/floors/{id}/blocks")
	  public ResponseEntity<List<Block>> getBlocksByFloor(@PathVariable(value = "id") Long floorId) {
		  List<Block> blocks=blockRepository.findAll();
		  List<Block> filteredBlocks=new ArrayList<>();
		  if(blocks!=null)
	    filteredBlocks =
	       blocks.stream().filter(block -> block.getFloorId()==floorId).collect(Collectors.toList());
	    return ResponseEntity.ok().body(filteredBlocks);

	  }

	 
	  @PutMapping("/blocks/{id}")
	  public ResponseEntity<Block> updateBlock(
	      @PathVariable(value = "id") Long blockId, @Valid @ModelAttribute Block blockDetails)
	      throws ResourceNotFoundException {

	    Block block =
	        blockRepository
	            .findById(blockId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + blockId));

	    block.setBlockName(blockDetails.getBlockName());
	    final Block updatedBlock = blockRepository.save(block);
	    return ResponseEntity.ok().body(updatedBlock);
	  }

	  
	  @DeleteMapping("/blocks/{id}")
	  public Map<String, Boolean> deleteBlocks(@PathVariable(value = "id") Long blockId) throws Exception {
	    Block block =
	        blockRepository
	            .findById(blockId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + blockId));

	    blockRepository.delete(block);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
}
