package cz.fi.muni.pa165.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import cz.fi.muni.pa165.entity.OrderItem;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

	@PersistenceContext
	private EntityManager em;

    @Override
    public void create(OrderItem orderItem) {
        em.persist(orderItem);
    }

    @Override
	public void delete(OrderItem orderItem) {
		em.remove(orderItem);
	}

	@Override
	public OrderItem findById(Long id) {
		return em
				.createQuery("select o from OrderItem o WHERE o.id = :id",
						OrderItem.class).setParameter("id", id)
				.getSingleResult();
	}

	public void removeById(Long id) {
		em.remove(findById(id));
	}

}
