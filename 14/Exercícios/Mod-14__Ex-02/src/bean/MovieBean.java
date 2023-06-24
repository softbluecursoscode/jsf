package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.Movie;

@Named
@RequestScoped
public class MovieBean implements Serializable {
	
	/**
	 * Lista de filmes cadastrados. É definida como static para não perder os elementos entre as requisições
	 */
	private static List<Movie> movies = new ArrayList<Movie>();
	private Movie movie = new Movie();
	
	/**
	 * Indica o filme selecionado na tabela
	 */
	private Movie selectedMovie;
	
	/**
	 * Indica se está em modo de edição (true) ou cadastro (false)
	 */
	private boolean editMode;
	
	/**
	 * Grava um filme (cria ou altera)
	 */
	public void saveMovie() {
		if (!editMode) {
			// Se é criação, verifica se o nome do filme já não está cadastrado
			if (movies.contains(movie)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Filme já existente", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			
			} else {
				// Insere o filme na lista
				movies.add(movie);
			}
		
		} else {
			// Alteração de filme. Procura o filme na lista de filmes cadastados e copia os valores digitados pelo usuário
			// para o objeto
			for (Movie movie : movies) {
				if (movie.getName().equals(this.movie.getName())) {
					movie.setDescription(this.movie.getDescription());
					movie.setRating(this.movie.getRating());
					break;
				}
			}
		}
		
		movie = new Movie();
		editMode = false;
	}
	
	/**
	 * Exclui um filme
	 */
	public void deleteMovie() {
		movies.remove(selectedMovie);
	}
	
	/**
	 * Carrega os dados do filme nos campos de edição
	 */
	public void loadMovie() {
		movie = selectedMovie;
		editMode = true;
	}
	
	/**
	 * Retorna a label do botão de salvamento com base no editMode
	 */
	public String getButtonLabel() {
		return editMode ? "Alterar" : "Cadastrar";
	}
	
	/**
	 * Retorna o título do painel com base no editMode
	 */
	public String getPanelTitle() {
		return editMode ? "Alterar Filme" : "Cadastrar Filme";
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}
