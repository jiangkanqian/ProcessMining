package net.ProcessMining.base.dao.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class OrderBy {

	
	private List<Order> orders = new ArrayList<Order>();

	public OrderBy(){}
	
	public void add(Order order){
		this.orders.add(order);
	}
	
    public void build(Criteria criteria) {
        for(Order order : orders) {
            criteria.addOrder(order);
        }
    }


}
