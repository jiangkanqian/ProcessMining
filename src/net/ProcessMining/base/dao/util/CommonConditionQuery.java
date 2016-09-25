package net.ProcessMining.base.dao.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.ProcessMining.util.JacksonUtil;

@SuppressWarnings("serial")
public class CommonConditionQuery implements Serializable{


    private List<CommonRestrictions> restrictions = null;
    
    
    
    public CommonConditionQuery(){
    	restrictions = new ArrayList<CommonRestrictions>();
    }
    
    
    
    public List<CommonRestrictions> getRestrictions() {
		return restrictions;
	}



	public void setRestrictions(List<CommonRestrictions> restrictions) {
		this.restrictions = restrictions;
	}



	public void add(CommonRestrictions restrictions){
    	this.restrictions.add(restrictions);
    }
	
	@Override
	public String toString(){
		return JacksonUtil.beanToString(this);
	}

}
