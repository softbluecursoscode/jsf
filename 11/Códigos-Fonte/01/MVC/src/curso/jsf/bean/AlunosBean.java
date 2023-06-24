package curso.jsf.bean;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.Aluno;
import curso.jsf.service.AlunoService;

/**
 * Bean da tela de cadastro de alunos
 */
@Named("alunos")
@SessionScoped
public class AlunosBean extends AbstractBean {

	@Inject
	private AlunoService alunoService;

	private List<Aluno> alunos;

	private Aluno aluno;

	private boolean alterar;

	/**
	 * Obtém a lista de alunos
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

	/**
	 * Abre a tela de edição de aluno
	 * @param aluno Aluno a editar
	 * @return
	 */
	public String alterar(Aluno aluno) {
		this.aluno = aluno;
		this.alterar = true;
		return "editar_aluno";
	}

	/**
	 * Abre a tela de cadastro de aluno
	 * @param aluno
	 * @return
	 */
	public String novoAluno() {
		aluno = new Aluno();
		alterar = false;
		return "editar_aluno";
	}
	
	
	/**
	 * Exclui um aluno
	 * @param aluno Aluno para excluir
	 * @return
	 */
	public String excluir(Aluno aluno) {
		try {
			alunoService.excluir(aluno.getNumMatricula());
			alunos = null;
			
			// Após a exclusão, faz um redirect
			return "listar_alunos?faces-redirect=true";
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	/**
	 * Cadastra ou atualiza um aluno (depende do estado da flag 'alterar')
	 * @return
	 */
	public String salvar() {
		
		System.out.println(aluno.getDataNascimento());
		try {
			if (alterar) {
				alunoService.alterar(aluno);
			} else {
				alunoService.inserir(aluno);
			}
			aluno = null;
			alunos = null;
			alterar = false;
			return "listar_alunos?faces-redirect=true";
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public Aluno getAluno() {
		if (aluno == null) {
			aluno = new Aluno();
		}
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public boolean isAlterar() {
		return alterar;
	}

	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

}
