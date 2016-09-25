package net.ProcessMining.base.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import net.ProcessMining.base.dao.BaseDao;
import net.ProcessMining.base.dao.impl.BaseDaoImpl;
import net.ProcessMining.base.dao.util.CommonConditionQuery;
import net.ProcessMining.base.dao.util.CommonOrder;
import net.ProcessMining.base.dao.util.CommonOrderBy;
import net.ProcessMining.base.dao.util.CommonRestrictions;
import net.ProcessMining.base.dao.util.ConditionQuery;
import net.ProcessMining.base.dao.util.OrderBy;

public abstract class BaseDaoImpl <E extends java.io.Serializable, PK extends java.io.Serializable> 
implements BaseDao<E, PK>{


	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	private static final String SPACE=" ";
    @Autowired
    private SessionFactory sessionFactory;
    
    private Class<E> entityClass = null;
    private String pkName = null;
    private String HQL_ALL = null;
    private String HQL_COUNT_ALL=null;


    public BaseDaoImpl() {
        this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] fields = this.entityClass.getDeclaredFields();
        for(Field f : fields) {
            if(f.isAnnotationPresent(Id.class)) {
                this.pkName = f.getName();
            }
        }
        HQL_ALL = " select "+entityClass.getSimpleName()+" from "+entityClass.getSimpleName()+" "+entityClass.getSimpleName();
        HQL_COUNT_ALL = " select count(*) from  "+entityClass.getSimpleName()+" "+entityClass.getSimpleName();
    }
    

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public PK save(E entity) {
        return (PK) getSession().save(entity);
    }
    
    public void saveOrUpdate(E entity){
    	getSession().saveOrUpdate(entity);
    }
    public void deleteByPK(PK pk){
    	getSession().delete(this.getByPK(pk));
    }
    public void delete(E entity){
    	getSession().delete(entity);
    }
    public void update(E entity){
    	getSession().update(entity);
    }
    
    public E getByPK(PK pk){
    	return (E)getSession().get(entityClass, pk);
    }
    
    public E loadByPK(PK pk){
    	return (E)getSession().load(entityClass, pk);
    }
    
    public void evict(E entity){
    	getSession().evict(entity);
    }
    
    public void evict(){
    	this.sessionFactory.evict(entityClass);
    }
    
    
    public boolean exists(PK pk){
    	return (this.getByPK(pk)!=null);
    }
    
    private void setParameters(Query query,CommonConditionQuery condition) {
    	List<CommonRestrictions> restrictions = condition.getRestrictions();
        for (CommonRestrictions restriction :restrictions) {
            if(restriction.getValue() instanceof Date) {
                query.setTimestamp(restriction.getName(), (Date)restriction.getValue());
            } else if(restriction.getValue()  instanceof Collection) {
                query.setParameterList(restriction.getName(), (Collection)restriction.getValue());
            } else{
            	query.setParameter(restriction.getName(), restriction.getValue());
            }
        }
    }
    
    private void appendcondition(StringBuffer sqlsb,CommonConditionQuery condition) {
    	List<CommonRestrictions> restrictions = condition.getRestrictions();
    	int i = 0;
    	for (CommonRestrictions restriction :restrictions) {
			if(restriction.getCondition()==null 
					|| restriction.getCondition().equals("")==true){
				continue;
			}
			if(i == 0){
				Pattern pattern = Pattern.compile("\\s+where\\s+");
				Matcher matcher = pattern.matcher(sqlsb.toString().toLowerCase());
				if(matcher.find()==false){
					sqlsb.append(SPACE).append("where").append(SPACE);
				}
				sqlsb.append(restriction.getCondition());
			}else{
				//sqlsb.append(" and ");//不默认增加运算关系，由condition指明
				sqlsb.append(SPACE).append(restriction.getLogic()).append(SPACE);
				sqlsb.append(SPACE).append(restriction.getCondition()).append(SPACE);
			}
			i++;
		}
		if(logger.isDebugEnabled()){
			logger.info("condition sql = "+sqlsb);
		}
    }
    
    private void appendOrderBys(StringBuffer sqlsb,CommonOrderBy orderBy) {
		List<CommonOrder> orders = orderBy.getOrders();
    	int i = 0;
		for(CommonOrder order : orders){
			if(i == 0){
				sqlsb.append(SPACE).append("order by");
				sqlsb.append(SPACE).append(order.getOrderColumn()).append(SPACE);
				sqlsb.append(order.getLogic());
			}else{
				sqlsb.append(",");
				sqlsb.append(order.getOrderColumn());
				sqlsb.append(SPACE).append(order.getLogic()).append(SPACE);
			}
			i++;
		}
		if(logger.isDebugEnabled()){
			logger.info("order by  sql = "+sqlsb);
		}
    }
    
    public int executeBulk(final String hql,CommonConditionQuery condition) {
    	StringBuffer hqlBuffer = new StringBuffer(hql);
    	if(condition!=null){
    		this.appendcondition(hqlBuffer, condition);
    	}
        Query query = getSession().createQuery(hqlBuffer.toString());
        if(condition!=null){
        	this.setParameters(query, condition);
        }
        Object result = query.executeUpdate();
        return result == null ? 0 : ((Integer) result).intValue();
    }
    
    public int executeNativeBulk(final String nativeSQL, CommonConditionQuery condition) {
    	StringBuffer nativeSQLBuffer = new StringBuffer(nativeSQL);
    	if(condition!=null){
    		this.appendcondition(nativeSQLBuffer, condition);
    	}
        Query query = getSession().createSQLQuery(nativeSQLBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
        Object result = query.executeUpdate();
        return result == null ? 0 : ((Integer) result).intValue();
    }
 
    
    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }
    
    
    public List<E> list(){
    	return this.list(false);
    }
    
    public List<E> list(boolean cacheable){
    	Criteria criteria = getSession().createCriteria(this.entityClass);
    	return criteria.setCacheable(cacheable).list();
    }
    
    public <T> T count(){
      	 return count(false);
    }
    
    public <T> T count(boolean cacheable){
     	 Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.setProjection(Projections.rowCount());
       return (T) criteria.setCacheable(cacheable).uniqueResult();
   }
    
    public <T> T count (ConditionQuery query){
   	 return this.count(query, false);
   }
    
    public <T> T count (ConditionQuery query,boolean cacheable){
      	 Criteria criteria = getSession().createCriteria(this.entityClass);
           query.build(criteria);
           criteria.setProjection(Projections.rowCount());
          return (T) criteria.setCacheable(cacheable).uniqueResult();
      }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy) {
       return this.list(query, orderBy,false);
    }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy,boolean cacheable) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        query.build(criteria);
        orderBy.build(criteria);
        return criteria.setCacheable(cacheable).list();
    }
    
    public List<E> list(ConditionQuery query, OrderBy orderBy,final int pageNum, final int pageSize) {
    	return this.list(query, orderBy, pageNum, pageSize, false);
    }
    
    
    public List<E> list(ConditionQuery query, OrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable) {
    	Criteria criteria = getSession().createCriteria(this.entityClass);
        query.build(criteria);
        orderBy.build(criteria);
        return criteria.setFirstResult(pageSize * (pageNum - 1)).setMaxResults(pageSize).setCacheable(cacheable).list();
    }
    
    public <T> List<T> list(Criteria criteria) {
        return criteria.setCacheable(true).list();
    }

    public <T> T unique(Criteria criteria) {
        return (T) criteria.setCacheable(true).uniqueResult();
    }

    public <T> List<T> list(DetachedCriteria criteria) {
        return list(criteria.getExecutableCriteria(getSession()));
    }

    public <T> T unique(DetachedCriteria criteria) {
        return (T) unique(criteria.getExecutableCriteria(getSession()));
    }
    
    public <T> T count(CommonConditionQuery condition){
    	return this.count(this.HQL_COUNT_ALL, condition);
    }
    
    public <T> T count(CommonConditionQuery condition,boolean cacheable){
    	return this.count(this.HQL_COUNT_ALL, condition,cacheable);
    }
    
    public <T> T count(String hql,CommonConditionQuery condition){
    	return this.count(this.HQL_COUNT_ALL, condition,false);
    }
    public <T> T count(String hql,CommonConditionQuery condition,boolean cacheable){
    	StringBuffer hqlBuffer = new StringBuffer(hql);
    	if(condition!=null ){
    		this.appendcondition(hqlBuffer, condition);
    	}
        Query query = getSession().createQuery(hqlBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
       return (T) query.setCacheable(cacheable).uniqueResult();
    }
    
    
    public List<E> list(CommonConditionQuery condition,CommonOrderBy orderBy){
    	return this.list(this.HQL_ALL, condition, orderBy);
    }
    
    public List<E> list(CommonConditionQuery condition,CommonOrderBy orderBy,boolean cacheable){
    	return this.list(this.HQL_ALL, condition, orderBy,cacheable);
    }
    
    public List<E> list(String hql,CommonConditionQuery condition,CommonOrderBy orderBy){
    	return this.list(hql, condition, orderBy, false);
    }
    public List<E> list(String hql,CommonConditionQuery condition,CommonOrderBy orderBy,boolean cacheable){
    	StringBuffer hqlBuffer = new StringBuffer(hql);
    	if(condition!=null){
    		this.appendcondition(hqlBuffer, condition);
    	}
    	if(orderBy!=null){
        	this.appendOrderBys(hqlBuffer, orderBy);
    	}
        Query query = getSession().createQuery(hqlBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
       return query.setCacheable(cacheable).list();
    }
    
    public List<E> list(CommonConditionQuery condition,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize){
    	return this.list(this.HQL_ALL, condition, orderBy, pageNum, pageSize);
    }
    
    public List<E> list(CommonConditionQuery condition,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable){
    	return this.list(this.HQL_ALL, condition, orderBy, pageNum, pageSize,cacheable);
    }
    
    public List<E> list(String hql,CommonConditionQuery condition,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize){
    	return this.list(hql, condition, orderBy, pageNum, pageSize, false);
    }
    
    public List<E> list(String hql,CommonConditionQuery condition,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable){
    	StringBuffer hqlBuffer = new StringBuffer(hql);
    	if(condition!=null){
    		this.appendcondition(hqlBuffer, condition);
    	}
    	if(orderBy!=null ){
        	this.appendOrderBys(hqlBuffer, orderBy);
    	}
        Query query = getSession().createQuery(hqlBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
        if(pageNum<1 || pageSize<1){
        	return query.setCacheable(cacheable).list();
        }else{
        	return query.setCacheable(cacheable).setFirstResult(pageSize * (pageNum - 1)).setMaxResults(pageSize).list();
        }
    }
    
    public <T> List<T> find(String hql,CommonConditionQuery condition,CommonOrderBy orderBy){
    	StringBuffer hqlBuffer = new StringBuffer(hql);
    	if(condition!=null){
    		this.appendcondition(hqlBuffer, condition);
    	}
    	if(orderBy!=null ){
        	this.appendOrderBys(hqlBuffer, orderBy);
    	}
        Query query = getSession().createQuery(hqlBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
       return query.setCacheable(false).list();
    }
    
    public <T> List<T> find(String hql,CommonConditionQuery condition,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize){
    	StringBuffer hqlBuffer = new StringBuffer(hql);
    	if(condition!=null){
    		this.appendcondition(hqlBuffer, condition);
    	}
    	if(orderBy!=null ){
        	this.appendOrderBys(hqlBuffer, orderBy);
    	}
        Query query = getSession().createQuery(hqlBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
        if(pageNum<1 || pageSize<1){
        	return query.setCacheable(false).list();
        }else{
        	return query.setCacheable(false).setFirstResult(pageSize * (pageNum - 1)).setMaxResults(pageSize).list();
        }
    }
    
    
    
    public <T> T nativeCount(String sql,CommonConditionQuery condition){
    	StringBuffer sqlBuffer = new StringBuffer(sql);
    	if(condition!=null ){
    		this.appendcondition(sqlBuffer, condition);
    	}
        Query query = getSession().createSQLQuery(sqlBuffer.toString());
        if(condition!=null ){
        	this.setParameters(query, condition);
        }
       return (T) query.setCacheable(false).uniqueResult();
    }
   
    public <T> List<T> nativeList(String sql,CommonConditionQuery condition,CommonOrderBy orderBy){
    	StringBuffer sqlBuffer = new StringBuffer(sql);
    	if(condition!=null ){
    		this.appendcondition(sqlBuffer, condition);
    	}
    	if(orderBy!=null ){
        	this.appendOrderBys(sqlBuffer, orderBy);
    	}
        Query query = getSession().createSQLQuery(sqlBuffer.toString());
        if(condition!=null){
        	this.setParameters(query, condition);
        }
       return query.setCacheable(false).list();
    }

    public <T> List<T> nativeList(String sql,CommonConditionQuery condition,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize){
    	StringBuffer sqlBuffer = new StringBuffer(sql);
    	if(condition!=null){
    		this.appendcondition(sqlBuffer, condition);
    	}
    	if(orderBy!=null ){
        	this.appendOrderBys(sqlBuffer, orderBy);
    	}
        Query query = getSession().createSQLQuery(sqlBuffer.toString());
        if(condition!=null){
        	this.setParameters(query, condition);
        }
        if(pageNum<1 || pageSize<1){
        	return query.setCacheable(false).list();
        }else{
        	return query.setCacheable(false).setFirstResult(pageSize * (pageNum - 1)).setMaxResults(pageSize).list();
        }
    }
    
    
   

}
