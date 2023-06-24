package curso.jsf.service;

import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.TurmaDAO;
import curso.jsf.model.Log.TipoMensagem;
import curso.jsf.model.Turma;

/**
 * Métodos de negócio relacionados à entidade Turma
 */
public class TurmaService extends Service {

	@Inject
	private TurmaDAO turmaDAO;

	@Inject
	private LogService logService;

	/**
	 * Insere uma nova turma no banco de dados
	 * @param turma Turma a ser inserida
	 * @throws ServiceException
	 */
	public void inserir(Turma turma) {
		try {
			beginTransaction();
			
			turmaDAO.salvar(turma);
			logService.log("Turma inserida: " + turma, TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Altera uma turma cadastrada no banco de dados
	 * @param turma Turma a ser alterada
	 * @throws ServiceException
	 */
	public void alterar(Turma turma) {
		try {
			beginTransaction();
			
			turmaDAO.alterar(turma);
			logService.log("Turma alterada: " + turma, TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Exclui uma turma cadastrada no banco de dados
	 * @param id ID da turma a ser excluída
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();
			
			Turma turma = turmaDAO.carregar(id, Turma.class);
			turmaDAO.excluir(turma);
			logService.log("Turma excluída: " + id, TipoMensagem.INFO);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Obtém todas as turmas cadastradas no banco de dados
	 * @return Lista de turmas cadastradas
	 * @throws ServiceException
	 */
	public List<Turma> listarTurmas() {
		return turmaDAO.listarTurmas();
	}
}
