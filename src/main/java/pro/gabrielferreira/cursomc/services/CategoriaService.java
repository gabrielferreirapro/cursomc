package pro.gabrielferreira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

//aqui é onde ficam as regras de negocio
@Service
public class CategoriaService {

	@Autowired // instancia automaticamente a dependencia
	private CategoriaRepository repo;

	// ou seja, essa é uma "regra" que quando der um buscar, vai retornar um item
	// por id
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		// reparar a separacao das responsabilidades, por exemplo aqui instanciei um
		// repo ali em cima, e uso o metodo dele de buscar no banco por id, cada classe
		// com sua responsabilidade
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	//salva obj no banco
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
