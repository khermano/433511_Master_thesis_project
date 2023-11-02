package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.OrderItem;

public interface OrderItemDao {
	public void create(OrderItem orderItem);
	public OrderItem findById(Long id);
	public void removeById(Long id);
	public void delete(OrderItem orderItem);
}
