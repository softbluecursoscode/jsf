package curso.jsf.bean;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.Parcela;
import curso.jsf.model.Proprietario;
import curso.jsf.model.Veiculo;
import curso.jsf.service.ImpostoService;
import curso.jsf.service.ProprietarioService;
import curso.jsf.service.VeiculoService;

@Named
@SessionScoped
public class PagamentoBean extends AbstractBean {

	@Inject
	private ProprietarioService proprietarioService;
	
	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private ImpostoService impostoService;
	
	private String cpf;
	
	private String placa;
	
	private Proprietario proprietario;
	
	private List<Veiculo> veiculos;
	
	private List<Parcela> parcelas;
	
	/**
	 * Pesquisa um propriet�rio pelo seu CPF
	 */
	public String pesquisarCPF() throws Exception {
		proprietario = proprietarioService.lerProprietario(cpf);
		
		if (proprietario == null) {
			addMessage("O CPF " + cpf + " n�o est� cadastrado");
			return null;
		}
		
		// obt�m os ve�culos associados ao propriet�rio
		veiculos = veiculoService.listarVeiculosPorCPF(cpf);
		
		return null;
	}
	
	/**
	 * Consulta as parcelas de um imposto
	 */
	public String consultarImposto() throws Exception {
		parcelas = impostoService.listarParcelasImposto(placa);
		
		if (parcelas.size() == 0) {
			addMessage("N�o existe imposto cadastrado para o ve�culo " + placa);
			return null;
		}
		
		return null;
	}
	
	/**
	 * Paga um parcela do imposto
	 */
	public String pagarParcela(Parcela parcela) throws Exception {
		impostoService.pagarParcela(parcela.getId());
		limpar();
		return "cadastrar_pagamento?faces-redirect=true";
	}
	
	public String limpar() {
		cpf = null;
		placa = null;
		proprietario = null;
		veiculos = null;
		parcelas = null;
		return null;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
}
