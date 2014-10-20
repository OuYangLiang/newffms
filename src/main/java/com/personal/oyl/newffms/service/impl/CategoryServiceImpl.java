package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.CategoryDao;
import com.personal.oyl.newffms.dao.ConsumptionItemDao;
import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryDao dao;
    @Autowired
    private ConsumptionItemDao consumptionItemDao;

    public List<Category> select(Category param) throws SQLException {
        return dao.select(param);
    }
    
    public Category selectByKey(BigDecimal categoryOid) throws SQLException {
        Category param = new Category();
        param.setCategoryOid(categoryOid);
        
        List<Category> list = this.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    
    public String selectFullDescByKey(BigDecimal categoryOid) throws SQLException {
        StringBuffer rlt = new StringBuffer();
        
        Category param = this.selectByKey(categoryOid);
        rlt.append(param.getCategoryDesc());
        
        while (param.getParentOid() != null) {
            param = this.selectByKey(param.getParentOid());
            rlt.insert(0, "-->").insert(0, param.getCategoryDesc());
        }
        
        return rlt.toString();
    }

    public List<Category> selectByLevel(Integer categoryLevel) throws SQLException {
        Category param = new Category();
        param.setCategoryLevel(categoryLevel);
        
        return this.select(param);
    }

    public List<Category> selectAllCategories() throws SQLException {
    	List<Category> list =  this.select(null);
        
        Map<BigDecimal, List<Category>> catMap = new HashMap<BigDecimal, List<Category>>();
        
        BigDecimal key = null;
        for (Category cat : list) {
        	if (null == cat.getParentOid()) {
        		key = BigDecimal.valueOf(-1);
        	} else {
        		key = cat.getParentOid();
        	}
        	
        	if (catMap.containsKey(key)) {
    			List<Category> cList = catMap.get(key);
    			cList.add(cat);
    		} else {
    			List<Category> cList = new ArrayList<Category>();
    			cList.add(cat);
    			
    			catMap.put(key, cList);
    		}
        }
        
        List<Category> rlt = new ArrayList<Category>();
        for (Category item : catMap.get(BigDecimal.valueOf(-1))) {
        	this.addItem(item, rlt, catMap);
        }
        
        return rlt;
    }
    
    private void addItem(Category item, List<Category> rlt, Map<BigDecimal, List<Category>> catMap) {
    	rlt.add(item);
    	
    	if (!item.getIsLeaf()) {
    		List<Category> list = catMap.get(item.getCategoryOid());
    		
    		for (Category cat : list) {
    			rlt.add(cat);
    			
    			addItem(cat, rlt, catMap);
    		}
    	}
    }

    public List<Category> selectAllCategoriesWithExclusion(Set<BigDecimal> excludingRootCategoryOids)
            throws SQLException {
        List<Category> allCategories = this.selectAllCategories();
        
        Map<BigDecimal, Category> catMap = new HashMap<BigDecimal, Category>();
        for (Category category : allCategories) {
            catMap.put(category.getCategoryOid(), category);
        }
        
        //第一步，先排除所有非root Cateogry
        Iterator<Category> it = allCategories.iterator();
        while (it.hasNext()) {
            Category item = it.next();
            
            if (item.getCategoryLevel() != 0) {
                
                Category pItem = catMap.get(item.getParentOid());
                
                while(pItem.getCategoryLevel() != 0) {
                    pItem = catMap.get(pItem.getParentOid());
                }
                
                if (excludingRootCategoryOids.contains(pItem.getCategoryOid())) {
                    it.remove();
                }
            } 
        }
        
        //第二步，排除所有root Category
        it = allCategories.iterator();
        while (it.hasNext()) {
            Category item = it.next();
            
            if (item.getCategoryLevel() == 0) {
                if (excludingRootCategoryOids.contains(item.getCategoryOid())) {
                    it.remove();
                }
            } 
        }
        
        return allCategories;
    }

	public void insert(Category param) throws SQLException {
		dao.insert(param);
	}

	public void updateByPrimaryKeySelective(Category param) throws SQLException {
		dao.updateByKeySelectively(param);
	}

	public void updateByPrimaryKey(Category param) throws SQLException {
		dao.updateByKey(param);
	}

	public void delete(Category param) throws SQLException {
		dao.delete(param);
	}

	public BigDecimal selectTotalBudgetByParent(BigDecimal parentOid)
			throws SQLException {
		return dao.selectTotalBudgetByParent(parentOid);
	}

	public boolean isCategoryUsedByIncoming(BigDecimal categoryOid)
			throws SQLException {
		Object obj = consumptionItemDao.selectOneByCategory(categoryOid);
		
		return null != obj;
	}

	public boolean isCategoryExist(BigDecimal parentOid, String categoryDesc)
			throws SQLException {
		return null != this.selectByParentAndDesc(parentOid, categoryDesc);
	}

	public Category selectByParentAndDesc(BigDecimal parentOid,
			String categoryDesc) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentOid", parentOid);
		param.put("categoryDesc", categoryDesc);
		
		return dao.selectByParentAndDesc(param);
	}

	public void deleteByKey(BigDecimal categoryOid) throws SQLException {
		Category param = new Category();
		param.setCategoryOid(categoryOid);
		
		dao.delete(param);
	}

	public List<Category> selectByParent(BigDecimal parentOid)
			throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentOid", parentOid);
		
		return dao.selectByParent(param);
	}

}
