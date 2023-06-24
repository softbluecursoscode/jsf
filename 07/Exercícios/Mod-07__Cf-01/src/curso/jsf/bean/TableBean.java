package curso.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import curso.jsf.model.Livro;

@Named("table")
@SessionScoped
public class TableBean implements Serializable {

	/**
	 * Lista de livros
	 */
	private List<Livro> livrosList = new ArrayList<Livro>();
	
	/**
	 * Table model para a lista de livros
	 */
	private ListDataModel<Livro> livros = new ListDataModel<Livro>(livrosList);
	
	/**
	 * Insere um novo registro de livro
	 * @return
	 */
	public String inserir() {
		Livro livro = new Livro();
		livro.setEdit(true);
		livrosList.add(livro);
		return null;
	}
	
	/**
	 * Exclui determinado livro
	 * @param livro
	 * @return
	 */
	public String excluir(Livro livro) {
		livrosList.remove(livro);
		return null;
	}
	
	/**
	 * Inicia o modo de edição de um livro
	 * @param livro
	 * @return
	 */
	public String editar(Livro livro) {
		livro.setEdit(true);
		return null;
	}
	
	/**
	 * Atualiza os dados de um livro sendo alterado
	 * @param livro
	 * @return
	 */
	public String atualizar(Livro livro) {
		livro.setEdit(false);
		return null;
	}

	public ListDataModel<Livro> getLivros() {
		return livros;
	}

	public void setlivros(ListDataModel<Livro> livros) {
		this.livros = livros;
	}
}
