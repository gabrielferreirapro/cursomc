package pro.gabrielferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.domain.Produto;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;
import pro.gabrielferreira.cursomc.repositories.ProdutoRepository;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepo;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids); // pega as categorias pelo id
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);// retorna a consulta
																								// efetivamente
	}
}
