package com.example.controller;
import com.example.entity.Building;
import com.example.repository.BuildingsRepository;
import com.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BuildingsController {

 @Autowired
 private BuildingsRepository buildingsRepository;

 @GetMapping("/buildings")
 public List<Building> getAllUsers() {
     return buildingsRepository.findAll();
 }

 @GetMapping("/buildings/{id}")
 public ResponseEntity<Building> getBuildingsById(@PathVariable(value = "id") Long buildingId)
     throws ResourceNotFoundException {
   Building building =
       buildingsRepository
           .findById(buildingId)
           .orElseThrow(() -> new ResourceNotFoundException("Building not found on :: " + buildingId));
   return ResponseEntity.ok().body(building);
 }

 @RequestMapping(value="/buildings", method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
 public Building createBuilding(@Valid @ModelAttribute Building building) {
   return buildingsRepository.save(building);
 }

 @PutMapping("/buildings/{id}")
 public ResponseEntity<Building> updateBuilding(
     @PathVariable(value = "id") Long buildingId, @Valid @RequestBody Building buildingDetails)
     throws ResourceNotFoundException {

   Building building =
       buildingsRepository
           .findById(buildingId)
           .orElseThrow(() -> new ResourceNotFoundException("Building not found on :: " + buildingId));

   building.setBuilding_name(buildingDetails.getBuilding_name());
   building.setArea(buildingDetails.getArea());
   final Building updatedBuilding = buildingsRepository.save(building);
   return ResponseEntity.ok(updatedBuilding);
 }

 @DeleteMapping("/building/{id}")
 public Map<String, Boolean> deleteBuilding(@PathVariable(value = "id") Long buildingId) throws Exception {
   Building building =
       buildingsRepository
           .findById(buildingId)
           .orElseThrow(() -> new ResourceNotFoundException("Building not found on :: " + buildingId));

   buildingsRepository.delete(building);
   Map<String, Boolean> response = new HashMap<>();
   response.put("deleted", Boolean.TRUE);
   return response;
 }
}