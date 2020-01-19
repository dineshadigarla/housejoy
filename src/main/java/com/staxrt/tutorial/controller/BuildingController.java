
package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Building;
import com.staxrt.tutorial.model.Desks;
import com.staxrt.tutorial.repository.BuildingRepository;
import com.staxrt.tutorial.repository.DeskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BuildingController {

  @Autowired
  private BuildingRepository buildingRepository;
  
  @Autowired
  private DeskRepository deskRepository;

 
  @GetMapping("/buildings")
  public List<Building> getAllBuidlings() {
    return buildingRepository.findAll();
  }

  
  @GetMapping("/buildings/{id}")
  public ResponseEntity<Building> getBuildingsById(@PathVariable(value = "id") Long buildingId)
      throws ResourceNotFoundException {
    Building user =
        buildingRepository
            .findById(buildingId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + buildingId));
    return ResponseEntity.ok().body(user);
  }

  
  
  @PostMapping("/buildings")
  public Building createBuilding(@Valid @ModelAttribute Building building) {
    return buildingRepository.save(building);
  }

 
  @PutMapping("/buildings/{id}")
  public ResponseEntity<Building> updateBuilding(
      @PathVariable(value = "id") Long buildingId, @Valid @ModelAttribute Building userDetails)
      throws ResourceNotFoundException {

    Building building =
        buildingRepository
            .findById(buildingId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + buildingId));

    building.setBuildingName(userDetails.getBuildingName());
    building.setArea(userDetails.getArea());
    building.setUpdatedAt(new Date());
    final Building updatedUser = buildingRepository.save(building);
    return ResponseEntity.ok(updatedUser);
  }

  
  @DeleteMapping("/buildings/{id}")
  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long buildingId) throws Exception {
    Building building =
        buildingRepository
            .findById(buildingId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + buildingId));

    buildingRepository.delete(building);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
  
  @GetMapping("/buildings/{id}/nonassociations")
  public ResponseEntity<List<Desks>> getNonAssociatedDesks(@PathVariable(value = "id") Long buildingId)
      throws ResourceNotFoundException {
    List<Desks> building =
        deskRepository
            .findNonAssociatedDesksForBuilding(buildingId);
    return ResponseEntity.ok().body(building);
  }
  
  @GetMapping("/buildings/{id}/associations")
  public ResponseEntity<List<Desks>> getAssociatedDesks(@PathVariable(value = "id") Long buildingId)
      throws ResourceNotFoundException {
    List<Desks> building =
        deskRepository
            .findAssociatedDesksForBuilding(buildingId);
    return ResponseEntity.ok().body(building);
  }
}
