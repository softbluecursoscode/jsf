package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@Named
@RequestScoped
public class ColorBean implements Serializable {

	private DualListModel<String> colors;
	
	public ColorBean() {
		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();
		
		source.add("Amarelo");
		source.add("Verde");
		source.add("Azul");
		source.add("Branco");
		source.add("Preto");
		source.add("Cinza");
		
		colors = new DualListModel<String>(source, target);
	}
	
	public void onTransfer(TransferEvent event) {
		List<?> itens = event.getItems();
		String txt = event.isAdd() ? "adicionados" : "removidos";
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Itens " + txt + ": " + itens.toString(), null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public DualListModel<String> getColors() {
		return colors;
	}

	public void setColors(DualListModel<String> colors) {
		this.colors = colors;
	}
}
