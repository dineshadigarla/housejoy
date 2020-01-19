
package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Building;
import com.staxrt.tutorial.model.Desks;
import com.staxrt.tutorial.model.Floor;
import com.staxrt.tutorial.repository.BuildingRepository;
import com.staxrt.tutorial.repository.DeskRepository;
import com.staxrt.tutorial.repository.FloorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class FloorController {

  @Autowired
  private FloorRepository floorRepository;
  
  @Autowired
  private BuildingRepository buildingRepository;
  
  @Autowired
  DeskRepository deskRepository;

 
  @GetMapping("/floors")
  public List<Floor> getAllFloors() {
    return floorRepository.findAll();
  }

  
  @GetMapping("/buildings/{id}/floors")
  public ResponseEntity<List<Floor>> getFloorsByBuildingId(@PathVariable(value = "id") Long buildingId)
      throws ResourceNotFoundException {
	  List<Floor> floors=floorRepository.findAll();
	  List<Floor> filteredFloors=new ArrayList<>();
	  if(floors!=null)
    filteredFloors =
       floors.stream().filter(floor -> floor.getBuildingId()==buildingId).collect(Collectors.toList());
    return ResponseEntity.ok().body(filteredFloors);
  }

  
  
  @PostMapping("/buildings/{id}/floors")
  public Floor createFloor(@PathVariable(value = "id") Long buildingId,@Valid @ModelAttribute Floor floor) throws ResourceNotFoundException {
	  buildingRepository
      .findById(buildingId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + buildingId));
      floor.setBuildingId(buildingId);
	  return floorRepository.save(floor);
  }

 
  @PutMapping("floors/{id}")
  public ResponseEntity<Floor> updateFloor(
      @PathVariable(value = "id") Long floorId, @Valid @ModelAttribute Floor floorDetails)
      throws ResourceNotFoundException {

    Floor floor =
        floorRepository
            .findById(floorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + floorId));

    floor.setFloorName(floorDetails.getFloorName());
    final Floor updatedFloor = floorRepository.save(floor);
    return ResponseEntity.ok().body(updatedFloor);
  }

  @GetMapping("floors/{id}")
  public ResponseEntity<Floor> getFloorById(
      @PathVariable(value = "id") Long floorId)
      throws ResourceNotFoundException {

    Floor floor =
        floorRepository
            .findById(floorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + floorId));
    return ResponseEntity.ok().body(floor);
  }

  
  @DeleteMapping("/floors/{id}")
  public Map<String, Boolean> deleteFloor(@PathVariable(value = "id") Long floorId) throws Exception {
    Floor floor =
        floorRepository
            .findById(floorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + floorId));

    floorRepository.delete(floor);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
  
  @GetMapping("/floors/{id}/nonassociations")
  public ResponseEntity<List<Desks>> getNonAssociatedDesks(@PathVariable(value = "id") Long floorId)
      throws ResourceNotFoundException {
    List<Desks> floor =
        deskRepository
            .findNonAssociatedDesksForFloor(floorId);
    return ResponseEntity.ok().body(floor);
  }
}

