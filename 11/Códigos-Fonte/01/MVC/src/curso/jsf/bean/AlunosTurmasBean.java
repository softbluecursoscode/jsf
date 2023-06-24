package curso.jsf.bean;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.Aluno;
import curso.jsf.model.Turma;
import curso.jsf.service.AlunoService;
import curso.jsf.service.TurmaService;

/**
 * Bean da tela de associação entre alunos e turmas
 */
@Named("alunosTurmas")
@SessionScoped
public class AlunosTurmasBean extends AbstractBean {

	@Inject
	private AlunoService alunoService;
	
	@Inject
	private TurmaService turmaService;

	private List<Turma> turmas;
	
	private List<Aluno> alunos;

	private Integer turmaId;
	
	private String[] alunosSelecionados;
	
	/**
	 * Evento disparado quando a caixa de seleção de turma tem seu valor alterado
	 * @param event
	 */
	public void mudouTurma(ValueChangeEvent event) {
		try {
			Integer turmaId = (Integer) event.getNewValue();
			
			// Obtém a lista de alunos cadastrados para a turma selecionada
			List<Aluno> alunosTurma = alunoService.obterAlunosTurma(turmaId);
			
			// Popula o array 'alunosSelecionados' com os números de matrículas, para que os checkboxes
			// apareçam marcados na tela
			alunosSelecionados = new String[alunosTurma.size()];
			int i = 0;
			for (Aluno aluno : alunosTurma) {
				alunosSelecionados[i++] = aluno.getNumMatricula();
			}
			
			this.turmaId = turmaId;
			
			// O código abaixo força a recriação da view, fazendo com o JSF navegue para a tela representada
			// pelo outcome 'alunos_turmas'. Sem isto, os dados na tela não serão atualizados
			FacesContext context = FacesContext.getCurrentInstance();
			NavigationHandler navHandler = context.getApplication().getNavigationHandler();
			navHandler.handleNavigation(context, null, "alunos_turmas");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	/**
	 * Associa alunos a uma turma
	 * @return
	 */
	public String associarAlunos() {
		try {
			alunoService.associarAlunosTurma(alunosSelecionados, turmaId);
			turmas = null;
			alunos = null;
			turmaId = null;
			alunosSelecionados = null;
			return "index";
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	/**
	 * Obtém todas as turmas cadastradas no banco de dados
	 * @return Lista de turmas
	 */
	public List<Turma> getTurmas() {
		try {
			if (turmas == null) {
				turmas = turmaService.listarTurmas();
			}
			return turmas;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	/**
	 * Obtém todos os alunos cadastrados no banco de dados
	 * @return Lista de alunos
	 */
	public List<Aluno> getAlunos() {
		try {
			if (alunos == null) {
				alunos = alunoService.listarAlunos();
			}
			return alunos;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Integer getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(Integer turmaId) {
		this.turmaId = turmaId;
	}

	public String[] getAlunosSelecionados() {
		return alunosSelecionados;
	}

	public void setAlunosSelecionados(String[] alunosSelecionados) {
		this.alunosSelecionados = alunosSelecionados;
	}
}
