package curso.jsf.service;

import javax.inject.Inject;

import curso.jsf.dao.ProprietarioDAO;
import curso.jsf.model.Proprietario;

public class ProprietarioService extends Service {

	@Inject
	private ProprietarioDAO proprietarioDAO;

	public Proprietario lerProprietario(String cpf) {
		return proprietarioDAO.lerProprietario(cpf);
	}
}
