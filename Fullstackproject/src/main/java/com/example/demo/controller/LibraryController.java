package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptionHandling.ResourceNotFoundException;
import com.example.demo.model.Library;
import com.example.demo.repository.LibraryRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController//@ResponseBody and @Controller
@RequestMapping("/api/v1/")
public class LibraryController {

	@Autowired
	private LibraryRepository libraryRepository;
	
	@GetMapping("/libraryRecords")
	public List<Library> getRecordsFromDb(){
		return libraryRepository.findAll();
	}		

	// create library api vi
	@PostMapping("/saveLibraryRecords")
	public Library createLibraryRecords(@RequestBody Library library) {
		return libraryRepository.save(library);
	}

	// get library by id api vi
	@GetMapping("/libraryRecords/{BookId}")
	public ResponseEntity<Library> searchById(@PathVariable int BookId) {
		Library library = libraryRepository.findById(BookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not exist with id :" + BookId));
		return ResponseEntity.ok(library);
	}

	// update library  api vi
	@PutMapping("/updateRecords/{BookId}")
	public ResponseEntity<Library> updateBookDetails(@PathVariable int BookId, @RequestBody Library libraryRecordDetails){
		Library library = libraryRepository.findById(BookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not exist with id :" + BookId));

		library.setNoOfCopies(libraryRecordDetails.getNoOfCopies());

		Library updatedRecords = libraryRepository.save(library);
		return ResponseEntity.ok(updatedRecords);
	}

	// delete library  api vi
	@DeleteMapping("/deleteRecords/{BookId}")
	public ResponseEntity<Map<String, Boolean>> deleteRecord(@PathVariable int BookId){
		 Library library = libraryRepository.findById(BookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not exist with id :" + BookId));

		 libraryRepository.delete(library);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}


}
 