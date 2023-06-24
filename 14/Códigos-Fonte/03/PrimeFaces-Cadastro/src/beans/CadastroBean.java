package beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entity.Cliente;
import entity.Transferencia;

@Named
@RequestScoped
public class CadastroBean implements Serializable {

	private Cliente cliente = new Cliente();
	private Transferencia transf = new Transferencia();

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Transferencia getTransf() {
		return transf;
	}

	public void setTransf(Transferencia transf) {
		this.transf = transf;
	}
	
	public List<String> listarBancos(String text) {
		List<String> bancos = Arrays.asList(new String[] {
				"Banco do Brasil",
				"Bradesco",
				"Caixa Econômica Federal",
				"Itaú"
		});
		
		List<String> bancosExibidos = new ArrayList<String>();
		
		for (String banco : bancos) {
			if (banco.toUpperCase().startsWith(text.toUpperCase())) {
				bancosExibidos.add(banco);
			}
		}
		
		return bancosExibidos;
	}
	
	public String cadastrar() {
		System.out.println(cliente);
		System.out.println(transf);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Transferência cadastrada com sucesso");
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, msg);
		
		return null;
	}
}
