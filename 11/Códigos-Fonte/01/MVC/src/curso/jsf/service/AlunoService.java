package curso.jsf.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.AlunoDAO;
import curso.jsf.dao.TurmaDAO;
import curso.jsf.model.Aluno;
import curso.jsf.model.Log.TipoMensagem;
import curso.jsf.model.Turma;

/**
 * M�todos de neg�cio relacionados � entidade Aluno
 */
public class AlunoService extends Service {
	
	@Inject
	private AlunoDAO alunoDAO;
	
	@Inject
	private TurmaDAO turmaDAO;

	@Inject
	private LogService logService;

	/**
	 * Insere um novo aluno no banco de dados
	 * @param aluno Aluno a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Aluno aluno) {
		try {
			beginTransaction();
			
			alunoDAO.salvar(aluno);
			logService.log("Aluno inserido: " + aluno, TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Alter um aluno cadastrado no banco de dados.
	 * @param aluno
	 * @throws ServiceException
	 */
	public void alterar(Aluno aluno) {
		try {
			beginTransaction();
			
			alunoDAO.alterar(aluno);
			logService.log("Aluno alterado: " + aluno, TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Exclui um aluno do banco de dados
	 * @param numMatricula N�mero de matr�cula do aluno a ser exclu�do
	 * @throws ServiceException
	 */
	public void excluir(String numMatricula) {
		try {
			beginTransaction();
			
			Aluno aluno = alunoDAO.carregar(numMatricula, Aluno.class);
			alunoDAO.excluir(aluno);
			logService.log("Aluno exclu�do: " + numMatricula, TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * L� todos os alunos cadastrados no banco de dados
	 * @return Lista de alunos cadastrados
	 * @throws ServiceException
	 */
	public List<Aluno> listarAlunos() {
		return alunoDAO.listarAlunos();
	}
	
	/**
	 * Obt�m todos os alunos associados a uma turma espec�fica
	 * @param turmaId ID da turma cujos alunos ser�o retornados
	 * @return Lista de alunos
	 * @throws ServiceException
	 */
	public List<Aluno> obterAlunosTurma(Integer turmaId) {
		return alunoDAO.obterAlunosTurma(turmaId);
	}
	
	/**
	 * Associa alunos a uma turma
	 * @param numMatriculas N�meros de matr�culas dos alunos
	 * @param turmaId ID da turma para associar os alunos
	 * @throws ServiceException
	 */
	public void associarAlunosTurma(String[] numMatriculas, Integer turmaId) {
		try {
			beginTransaction();

			// Remove todos os alunos da turma, para manter a consist�ncia			
			List<Aluno> alunos = alunoDAO.obterAlunosTurma(turmaId);
			for (Aluno aluno : alunos) {
				aluno.setTurma(null);
			}
			
			// Carrega a turma
			Turma turma = turmaDAO.carregar(turmaId, Turma.class);
						
			for (String numMatricula : numMatriculas) {
				// Atualiza a turma de cada aluno do array
				Aluno aluno = alunoDAO.carregar(numMatricula, Aluno.class);
				aluno.setTurma(turma);
			}
			
			logService.log("Alunos associados � turma " + turmaId + ":" + Arrays.toString(numMatriculas), TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
}
