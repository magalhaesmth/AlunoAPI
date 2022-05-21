package com.deve.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dev.crud.domain.Aluno;
import com.dev.crud.exception.BadResourceException;
import com.dev.crud.exception.ResourceAlreadyExistsException;
import com.dev.crud.exception.ResourceNotFoundException;
import com.dev.crud.repository.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	private boolean existsById(Long id) {
		return alunoRepository.existsById(id);
	}
	public Aluno findById(Long id) throws ResourceNotFoundException {
	 Aluno aluno = alunoRepository.findById(id).orElse(null);
	 	if (aluno==null) {
	 		throw new ResourceNotFoundException("Aluno não encontrado com o id: " + id);
	 	}
	 	else return aluno;
	}
	public Page<Aluno> findAll(Pageable pegeable) {
		return alunoRepository.findAll(pegeable);
	}
	public Page<Aluno> findAllByNome(String nome, Pageable page) {
	 Page<Aluno> aluno = alunoRepository.findByNome(nome, page);
	 	return aluno;
	}
	public Aluno save(Aluno aluno) throws BadResourceException, ResourceAlreadyExistsException {
		if (!StringUtils.isEmpty(aluno.getNome())) { 
			if (aluno.getId() != null && existsById(aluno.getId())) {
				throw new ResourceAlreadyExistsException("Aluno com id: " + aluno.getId() + "já existe"); 
			}
			return alunoRepository.save(aluno);
		}
		else {
			BadResourceException exc = new BadResourceException("Erro ao salvar o aluno");
			exc.addErrorMessage("O aluno está vazio ou é nulo");
			throw exc;
		}
	}
	public void update (Aluno aluno)
		throws BadResourceException, ResourceNotFoundException {
	 if (!StringUtils.isEmpty(aluno.getNome())) {
		 if (!existsById(aluno.getId())) {
			 throw new ResourceNotFoundException("Aluno não foi encontrado com o Id: " + aluno.getId());
		 }
		 alunoRepository.save(aluno);
	 }
	 else {
		 BadResourceException exc = new BadResourceException("Falha ao salvar o aluno");
		 exc.addErrorMessage("Aluno está nulo ou está em branco");
		 throw exc;
	 }
	}
	 public void deleteById(Long id) throws ResourceNotFoundException {
		 if (!existsById(id)) {
			 throw new ResourceNotFoundException("Aluno não encontrado com o id: " + id);
		 }
		 else {
			 alunoRepository.deleteById(id);
		 }
	 }
	 public Long count() {
		 return alunoRepository.count();
	}
}
