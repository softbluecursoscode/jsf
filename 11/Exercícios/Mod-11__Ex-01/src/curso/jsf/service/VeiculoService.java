package curso.jsf.service;

import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.VeiculoDAO;
import curso.jsf.model.Veiculo;

public class VeiculoService extends Service {

	@Inject
	private VeiculoDAO veiculoDAO;

	public Veiculo carregar(String placa) {
		return veiculoDAO.carregar(placa, Veiculo.class);
	}
	
	public List<Veiculo> listarVeiculosPorCPF(String cpf) {
		return veiculoDAO.listarVeiculosPorCPF(cpf);
	}
}
