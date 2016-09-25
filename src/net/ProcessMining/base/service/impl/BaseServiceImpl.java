package net.ProcessMining.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import net.ProcessMining.base.dao.BaseDao;
import net.ProcessMining.base.dao.impl.BaseDaoImpl;
import net.ProcessMining.base.dao.util.CommonConditionQuery;
import net.ProcessMining.base.dao.util.CommonOrderBy;
import net.ProcessMining.base.dao.util.ConditionQuery;
import net.ProcessMining.base.dao.util.OrderBy;
import net.ProcessMining.base.service.BaseService;

public abstract class BaseServiceImpl<E extends java.io.Serializable, PK extends java.io.Serializable>
implements BaseService<E, PK>{
	
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	protected BaseDao<E, PK> baseDao;
	
	public abstract void setBaseDao(BaseDao<E, PK> baseDao);
	
	public PK save(E entity){
		return this.baseDao.save(entity);
	}
	
    public void saveOrUpdate(E entity){
    	this.baseDao.saveOrUpdate(entity);
    }
    
    public void deleteByPK(PK pk){
    	this.baseDao.deleteByPK(pk);
    }
    
    public void delete(E entity){
    	this.baseDao.delete(entity);
    }
    
    public void update(E entity){
    	this.baseDao.update(entity);
    }
    
    public E getByPK(PK pk){
    	return this.baseDao.getByPK(pk);
    }
    
    public E loadByPK(PK pk){
    	return this.baseDao.loadByPK(pk);
    }
    
    public boolean exists(PK pk){
    	return this.baseDao.exists(pk);
    }
    
    public List<E> list(){
    	return this.baseDao.list();
    }
    
    public List<E> list(boolean cacheable){
    	return this.baseDao.list(cacheable);
    }
    
    public <T> T count(){
    	return this.baseDao.count();
    }
    
    public <T> T count(boolean cacheable){
    	return this.baseDao.count(cacheable);
    }
    
    public <T> T count (ConditionQuery query){
    	return this.baseDao.count(query);
    }
    
    public <T> T count (ConditionQuery query,boolean cacheable){
    	return this.baseDao.count(query,cacheable);
    }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy){
    	return this.baseDao.list(query, orderBy);
    }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy,boolean cacheable){
    	return this.baseDao.list(query, orderBy,cacheable);
    }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy,final int pageNum, final int pageSize){
    	return this.baseDao.list(query, orderBy, pageNum, pageSize);
    }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable){
    	return this.baseDao.list(query, orderBy, pageNum, pageSize,cacheable);
    }
    
    public <T> T count(CommonConditionQuery query){
    	return this.baseDao.count(query);
    }
    
    public <T> T count(CommonConditionQuery query,boolean cacheable){
    	return this.baseDao.count(query);
    }

    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy){
    	return this.baseDao.list(query, orderBy);
    }
    
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,boolean cacheable){
    	return this.baseDao.list(query, orderBy,cacheable);
    }
    
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize){
    	return this.baseDao.list(query, orderBy, pageNum, pageSize);
    }
    
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable){
    	return this.baseDao.list(query, orderBy, pageNum, pageSize,cacheable);
    }

}
