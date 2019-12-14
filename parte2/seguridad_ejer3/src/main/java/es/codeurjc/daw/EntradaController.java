package es.codeurjc.daw;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EntradaController {

	@Autowired
	private EntradaRepository repository;

	@PostConstruct
	public void init() {

		Entrada e1 = new Entrada("Michel", "Mike", "Entrada 1", "Mi contenido");
		Entrada e2 = new Entrada("Nicolás", "Nico", "Entrada 2", "Mi contenido");
		Entrada e3 = new Entrada("Michel", "Mike", "Entrada 3", "Mi contenido");

		repository.save(e1);
		repository.save(e2);
		repository.save(e3);
	}

	@GetMapping
	public List<Entrada> entradas() {
		return repository.findAll();
	}

	@Autowired
	private EntityManager entityManager;

	@GetMapping("{name}")
	public List<Entrada> getEntrada(@PathVariable String name) {
		List<Entrada> result = repository.findByName(name);

		return result;
	}

	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Entrada addEntrada(@RequestBody Entrada entrada) {
		return repository.save(entrada);
	}

	@PutMapping("{id}")
	public ResponseEntity<Entrada> updateEntrada(@RequestBody Entrada entrada, @PathVariable long id) {
		Optional<Entrada> saved = repository.findById(id);
		if(saved.isPresent()) {
			Entrada updated = repository.save(entrada);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Entrada> deleteEntrada(@PathVariable long id) {
		Optional<Entrada> found = repository.findById(id);

		if(found.isPresent()) {
			repository.deleteById(id);
			return new ResponseEntity<>(found.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("{id}/comments/")
	public ResponseEntity<Entrada> addComment(@PathVariable long id, @RequestBody Comment comment) {
		Optional<Entrada> found = repository.findById(id);

		if(found.isPresent()) {
			found.get().getComments().add(comment);
			Entrada saved = repository.save(found.get());
			return new ResponseEntity<>(saved, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}


	}

}
