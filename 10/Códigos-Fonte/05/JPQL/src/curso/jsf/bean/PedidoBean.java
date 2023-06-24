package curso.jsf.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import curso.jsf.model.Cliente;
import curso.jsf.model.Pedido;
import curso.jsf.model.Produto;

@Named
@RequestScoped
public class PedidoBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;

	private List<Cliente> clientes;
	private List<Produto> produtos;
	
	private Integer selectedClienteId;
	private Integer[] selectedProdutosIds;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		Query q = em.createQuery("SELECT c FROM Cliente c");
		clientes = q.getResultList();
		
		q = em.createQuery("SELECT p FROM Produto p");
		produtos = q.getResultList();
	}
	
	public String cadastrarPedido() throws Exception {
		try {
			ut.begin();
		
			Cliente cliente = em.find(Cliente.class, selectedClienteId);
			
			Pedido pedido = new Pedido();
			pedido.setData(new Date());
			pedido.setCliente(cliente);
			em.persist(pedido);
			
			double valorTotal = 0.0;
			for (Integer selectedProdutoId : selectedProdutosIds) {
				Produto produto = em.find(Produto.class, selectedProdutoId);
				pedido.getProdutos().add(produto);
				valorTotal += produto.getValor();
			}
			
			pedido.setValorTotal(valorTotal);
			
			ut.commit();
			
			return "pedidos?faces-redirect=true";
		
		} catch (Exception e) {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
			throw e;
		}
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public Integer getSelectedClienteId() {
		return selectedClienteId;
	}

	public void setSelectedClienteId(Integer selectedClienteId) {
		this.selectedClienteId = selectedClienteId;
	}

	public Integer[] getSelectedProdutosIds() {
		return selectedProdutosIds;
	}

	public void setSelectedProdutosIds(Integer[] selectedProdutosIds) {
		this.selectedProdutosIds = selectedProdutosIds;
	}
}
