package com.dev.crud.controller;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dev.crud.domain.Aluno;
import com.dev.crud.exception.BadResourceException;
import com.dev.crud.exception.ResourceAlreadyExistsException;
import com.dev.crud.exception.ResourceNotFoundException;
import com.dev.crud.service.AlunoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="aluno", description = "API para CRUD de Aluno")

public class AlunoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AlunoService alunoService;
	
	@GetMapping(value = "/alunos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Aluno>> findAll(
			@RequestBody(required=false) String nome, Pageable pageable) {
		
		if (StringUtils.isEmpty(nome)) {
			return ResponseEntity.ok(alunoService.findAll(pageable));
		}
		else {
			return ResponseEntity.ok(alunoService.findAllByNome(nome, pageable));
		}
	}
	
	@GetMapping(value = "/aluno/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Aluno> findAlunoById(@PathVariable long id) {
		
		try {
			Aluno aluno = alunoService.findById(id);
			return ResponseEntity.ok(aluno);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
	@PostMapping(value = "/aluno")
	public ResponseEntity<Aluno> addAluno(@RequestBody Aluno aluno)
			throws URISyntaxException {
				
		try {
			Aluno novoAluno = alunoService.save(aluno);
			return ResponseEntity.created(new URI("/api/aluno" + novoAluno.getId())).body(aluno);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping(value = "/aluno/{id}")
	public ResponseEntity<Aluno> updateAluno(@Valid @RequestBody Aluno aluno, @PathVariable long id) {
	
		try {
			aluno.setId(id);
			alunoService.update(aluno);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping(path="/aluno/{id}")
	public ResponseEntity<Void> deleteAlunoById(@PathVariable long id) {
		try {
			alunoService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
}
