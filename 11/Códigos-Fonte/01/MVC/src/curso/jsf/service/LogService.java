package curso.jsf.service;

import java.util.Date;

import javax.inject.Inject;

import curso.jsf.dao.LogDAO;
import curso.jsf.model.Log;
import curso.jsf.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados ao logging
 */
public class LogService extends Service {

	@Inject
	private LogDAO logDAO;

	/**
	 * Insere uma nova mensagem de log no banco de dados
	 * @param mensagem Mensagem de log
	 * @param tipo Tipo da mensagem
	 * @throws ServiceException
	 */
	public void log(String mensagem, TipoMensagem tipo) {
		try {
			beginTransaction();
			
			Log log = new Log();
			log.setData(new Date());
			log.setTipo(tipo);
			log.setMensagem(mensagem);
			logDAO.salvar(log);
			
			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
}
