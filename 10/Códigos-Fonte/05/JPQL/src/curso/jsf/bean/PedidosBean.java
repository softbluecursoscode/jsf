package curso.jsf.bean;

import java.io.Serializable;
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

import curso.jsf.model.Pagamento;
import curso.jsf.model.Pagamento.TipoPagamento;
import curso.jsf.model.Pedido;

@Named
@RequestScoped
public class PedidosBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;

	private List<Pedido> pedidos;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		Query q = em.createQuery("SELECT p FROM Pedido p");
		pedidos = q.getResultList();
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public String pagar(Integer pedidoId, String tipo) throws Exception  {
		try {
			ut.begin();
		
			TipoPagamento tipoPagamento = TipoPagamento.valueOf(tipo);
			
			Pedido pedido = em.find(Pedido.class, pedidoId);
			
			Pagamento pagamento = new Pagamento();
			pagamento.setPedido(pedido);
			pagamento.setTipoPagto(tipoPagamento);
			em.persist(pagamento);
			
			pedido.setPagamento(pagamento);
			
			ut.commit();
			
			return "pedidos?faces-redirect=true";
		
		} catch (Exception e) {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
			throw e;
		}
	}
	
	public String excluir(Integer pedidoId) throws Exception {
		try {
			ut.begin();
			
			Pedido pedido = em.find(Pedido.class, pedidoId);
			em.remove(pedido);
			
			ut.commit();
			
			return "pedidos?faces-redirect=true";
			
		} catch (Exception e) {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
			throw e;
		}
	}
}
