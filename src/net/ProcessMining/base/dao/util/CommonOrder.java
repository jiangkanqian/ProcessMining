package net.ProcessMining.base.dao.util;

import java.io.Serializable;

import net.ProcessMining.util.JacksonUtil;

@SuppressWarnings("serial")
public class CommonOrder implements Serializable{


	public static final String ASC="asc";
	public static final String DESC="desc";
	
	private String logic;
	private String orderColumn;
	
	private CommonOrder(){
		
	}
	
	public String getOrderColumn() {
		return orderColumn;
	}


	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	
	public String getLogic() {
		return logic;
	}

	private void setLogic(String logic) {
		this.logic = logic;
	}

	private static CommonOrder build(String logic ,String orderColumn){
		CommonOrder order = new CommonOrder();
		order.setOrderColumn(orderColumn);
		order.setLogic(logic);
		return order;
	}
	
	public static CommonOrder asc(String orderColumn){
		return build(ASC,orderColumn);
	}
	
	public static CommonOrder desc(String orderColumn){
		return build(DESC,orderColumn);
	}
	
	@Override
	public String toString(){
		return JacksonUtil.beanToString(this);
	}


}
