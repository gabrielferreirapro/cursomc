package pro.gabrielferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import pro.gabrielferreira.cursomc.domain.Categoria;
import pro.gabrielferreira.cursomc.dto.CategoriaDTO;
import pro.gabrielferreira.cursomc.repositories.CategoriaRepository;
import pro.gabrielferreira.cursomc.services.exceptions.DataIntegrityException;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

//aqui é onde ficam as regras de negocio
@Service
public class CategoriaService {

	@Autowired // instancia automaticamente a dependencia
	private CategoriaRepository repo;

	// ou seja, essa é uma "regra" que quando der um buscar, vai retornar um item
	// por id
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		// reparar a separacao das responsabilidades, por exemplo aqui instanciei um
		// repo ali em cima, e uso o metodo dele de buscar no banco por id, cada classe
		// com sua responsabilidade
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	// salva obj no banco
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	// atualiza obj no banco, igual salva
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId()); // procuro o obj antes de salvar, por garantia
		updateData(newObj, obj);
		return repo.save(newObj); // salva
	}
	


	//deleta obj do banco pelo id
	public void delete(Integer id) {
		find(id); //procura obj antes de deletar
		try {
			repo.deleteById(id); //deleta por id
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos"); //msg personalizada caso de exeption
		}
	}

	// retorna tudo do banco, joao mostrou como fazer uma query e pegar só o que eu
	// quero.
	public List<Categoria> findAll() {
		return repo.findAll();
	}

	// metodo para paginacao das categorias
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// instancio o objeto pagereq com a forma que eu quero que o repo traga do banco
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		// passo pro repo a forma que eu quero que ele traga do banco pra mim.
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
