package curso.jsf.bean;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.Turma;
import curso.jsf.service.TurmaService;

/**
 * Bean da tela de cadastro de turmas
 */
/**
 * @author Carlos
 *
 */
@Named("turmas")
@SessionScoped
public class TurmasBean extends AbstractBean {

	@Inject
	private TurmaService turmaService;

	private List<Turma> turmas;

	private Turma turma;

	private boolean alterar;

	/**
	 * Obtém a lista de turmas
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
	 * Abre a tela de edição de turma
	 * @param turma Turma a editar
	 * @return
	 */
	public String alterar(Turma turma) {
		this.turma = turma;
		this.alterar = true;
		return "editar_turma";
	}
	
	/**
	 * Abre a tela de cadastro de turma
	 * @return
	 */
	public String novaTurma() {
		turma = new Turma();
		alterar = false;
		return "editar_turma";
	}

	/**
	 * Exclui uma turma
	 * @param turma Turma para excluir
	 * @return
	 */
	public String excluir(Turma turma) {
		try {
			turmaService.excluir(turma.getId());
			turmas = null;
			
			// Após a exclusão, faz um redirect
			return "listar_turmas?faces-redirect=true";
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	/**
	 * Cadastra ou atualiza uma turma (depende do estado da flag 'alterar')
	 * @return
	 */
	public String salvar() {
		try {
			if (alterar) {
				turmaService.alterar(turma);
			} else {
				turmaService.inserir(turma);
			}
			turma = null;
			turmas = null;
			alterar = false;
			return "listar_turmas?faces-redirect=true";
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public Turma getTurma() {
		if (turma == null) {
			turma = new Turma();
		}
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public boolean isAlterar() {
		return alterar;
	}

	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

}
