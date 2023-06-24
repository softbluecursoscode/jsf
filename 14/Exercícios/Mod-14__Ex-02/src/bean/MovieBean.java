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
	 * Lista de filmes cadastrados. � definida como static para n�o perder os elementos entre as requisi��es
	 */
	private static List<Movie> movies = new ArrayList<Movie>();
	private Movie movie = new Movie();
	
	/**
	 * Indica o filme selecionado na tabela
	 */
	private Movie selectedMovie;
	
	/**
	 * Indica se est� em modo de edi��o (true) ou cadastro (false)
	 */
	private boolean editMode;
	
	/**
	 * Grava um filme (cria ou altera)
	 */
	public void saveMovie() {
		if (!editMode) {
			// Se � cria��o, verifica se o nome do filme j� n�o est� cadastrado
			if (movies.contains(movie)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Filme j� existente", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			
			} else {
				// Insere o filme na lista
				movies.add(movie);
			}
		
		} else {
			// Altera��o de filme. Procura o filme na lista de filmes cadastados e copia os valores digitados pelo usu�rio
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
	 * Carrega os dados do filme nos campos de edi��o
	 */
	public void loadMovie() {
		movie = selectedMovie;
		editMode = true;
	}
	
	/**
	 * Retorna a label do bot�o de salvamento com base no editMode
	 */
	public String getButtonLabel() {
		return editMode ? "Alterar" : "Cadastrar";
	}
	
	/**
	 * Retorna o t�tulo do painel com base no editMode
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
