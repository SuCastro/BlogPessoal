package org.generation.BlogPessoal.Controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.BlogPessoal.Model.Postagem;
import org.generation.BlogPessoal.Repository.PostagemRepository;
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

@RestController
@RequestMapping("/Postagem")
@CrossOrigin("*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository repository;
	
	@GetMapping
	public ResponseEntity <List<Postagem>> GetAll() {
		return ResponseEntity.status(200).body(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Postagem> GetById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());	
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity <List<Postagem>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity <Postagem> post(@Valid @RequestBody Postagem NovaPostagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(NovaPostagem));
	}

	@PutMapping
	public ResponseEntity <Postagem> put(@Valid @RequestBody Postagem NovaPostagem) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(NovaPostagem));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
		}	
}