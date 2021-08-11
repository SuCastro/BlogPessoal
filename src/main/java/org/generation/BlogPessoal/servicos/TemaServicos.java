package org.generation.BlogPessoal.servicos;

import java.util.Optional;

import org.generation.BlogPessoal.Model.Tema;
import org.generation.BlogPessoal.Repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TemaServicos {

	private @Autowired TemaRepository repositorioT;

	/**
	 * Método utilizado para alterar um tema. O mesmo retorna um Optional com Tema
	 * caso correto ou um Optional.empyt() caso id do tema não exista.
	 * 
	 * @param temaParaAlterar do tipo Tema
	 * @return Optional com Tema alterado
	 * @since 1.0
	 * @author Turma 28
	 */
	public Optional<Tema> alterarTema(Tema temaParaAlterar) {
		return repositorioT.findById(temaParaAlterar.getId()).map(temaExistente -> {
			temaExistente.setPostagem(temaParaAlterar.getPostagem());
			return Optional.ofNullable(repositorioT.save(temaExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
}