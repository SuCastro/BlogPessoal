package org.generation.BlogPessoal.servicos;

import java.util.Optional;
import org.generation.BlogPessoal.Model.Postagem;
import org.generation.BlogPessoal.Model.Tema;
import org.generation.BlogPessoal.Repository.PostagemRepository;
import org.generation.BlogPessoal.Repository.TemaRepository;
import org.generation.BlogPessoal.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostagemServicos {
	
	private @Autowired PostagemRepository repositorioP;
	private @Autowired UsuarioRepository repositorioU;
	private @Autowired TemaRepository repositorioT;

	/**
	 * Método utilizado para cadastrar uma postagem nova no banco validando se o
	 * usuario criador é existente. O id do usuario criador e o id do tema deve ser
	 * passado dentro do objeto postagem para que a criação seja efetuada. Caso id
	 * do usuario não for passado ou não existir no banco retorna um
	 * Optional.empty()
	 * 
	 * @param novaPostagem do tipo Postagem
	 * @return Optional com Postagem
	 * @since 1.5
	 * @author Turma 28
	 */
	public Optional<?> cadastrarPostagem(Postagem novaPostagem) {
		Optional<Tema> objetoExistente = repositorioT.findById(novaPostagem.getTema().getId());
		return repositorioU.findById(novaPostagem.getCriadore().getId()).map(usuarioExistente -> {
			if (objetoExistente.isPresent()) {
				novaPostagem.setCriadore(usuarioExistente);
				novaPostagem.setTema(objetoExistente.get());
				return Optional.ofNullable(repositorioP.save(novaPostagem));
			} else {
				return Optional.empty();
			}
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	/**
	 * Método utilizado para alterar uma postagem. O mesmo retorna um Optional com
	 * Postagem caso correto ou um Optional.empyt() caso id da postagem não exista.
	 * 
	 * @param postagemParaAlterar do tipo Postagem
	 * @return Optional com Postagem alterada
	 * @since 1.0
	 * @author Turma 28
	 */
	public Optional<Postagem> alterarPostagem(Postagem postagemParaAlterar) {
		return repositorioP.findById(postagemParaAlterar.getId()).map(postagemExistente -> {
			postagemExistente.setTitulo(postagemParaAlterar.getTitulo());
			postagemExistente.setTexto(postagemParaAlterar.getTexto());
			return Optional.ofNullable(repositorioP.save(postagemExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
}