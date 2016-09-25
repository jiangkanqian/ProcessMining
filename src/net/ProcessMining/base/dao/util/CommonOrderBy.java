package net.ProcessMining.base.dao.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.ProcessMining.util.JacksonUtil;

@SuppressWarnings("serial")
public class CommonOrderBy implements Serializable{

	
	private List<CommonOrder> orders = null;
	
	
	public CommonOrderBy(){
		orders = new ArrayList<CommonOrder>();
	}
	
	
	
	public List<CommonOrder> getOrders() {
		return orders;
	}



	public void setOrders(List<CommonOrder> orders) {
		this.orders = orders;
	}



	public void add(CommonOrder order){
		this.orders.add(order);
	}
	
	@Override
	public String toString(){
		return JacksonUtil.beanToString(this);
	}


}
