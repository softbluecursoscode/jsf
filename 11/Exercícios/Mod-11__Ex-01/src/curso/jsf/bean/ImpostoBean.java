package curso.jsf.bean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.Imposto;
import curso.jsf.model.Veiculo;
import curso.jsf.service.ImpostoService;
import curso.jsf.service.VeiculoService;

@Named
@SessionScoped
public class ImpostoBean extends AbstractBean {

	@Inject
	private VeiculoService veiculoService;
	
	@Inject ImpostoService impostoService;
	
	private String placa;
	
	private Veiculo veiculo;
	
	private Imposto imposto;
	
	/**
	 * Pesquisa um veículo
	 */
	public String pesquisarVeiculo() throws Exception {
		placa = placa.toUpperCase();
		veiculo = veiculoService.carregar(placa);
		
		if (veiculo == null) {
			addMessage("Veículo com a placa " + placa + " não foi encontrado");
		
		} else if (impostoService.existeImpostoVeiculo(placa)) {
			addMessage("Já existe imposto cadastrado para o veículo de placa " + placa);
			veiculo = null;
		}
		
		return null;
	}
	
	/**
	 * Cadastra um imposto para um veículo
	 */
	public String cadastrarImposto() throws Exception {
		impostoService.cadastrarImposto(imposto, placa);
		limpar();
		return "cadastrar_imposto?faces-redirect=true";
	}
	
	public String limpar() throws Exception {
		placa = null;
		imposto = null;
		veiculo = null;
		return null;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Imposto getImposto() {
		if (imposto == null) {
			 imposto = new Imposto();
		}
		return imposto;
	}

	public void setImposto(Imposto imposto) {
		this.imposto = imposto;
	}
}
