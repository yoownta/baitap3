package vn.iotstar.services;

import java.util.List;

import vn.iotstar.dao.CategoryDaoImpl;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryDao cateDao = new CategoryDaoImpl();

    @Override
    public void insert(Category category) {
        cateDao.insert(category);
    }

    @Override
    public void update(Category category) {
        cateDao.update(category);
    }

    @Override
    public void delete(int cateid) throws Exception {
        cateDao.delete(cateid);
    }

    @Override
    public Category findById(int cateid) {
        return cateDao.findById(cateid);
    }

    @Override
    public Category findByCategoryname(String name) {
        try {
            return cateDao.findByCategoryname(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Category> findAll() {
        return cateDao.findAll();
    }

    @Override
    public List<Category> searchByName(String catname) {
        return cateDao.searchByName(catname);
    }

    @Override
    public List<Category> findAll(int page, int pagesize) {
        return cateDao.findAll(page, pagesize);
    }

    @Override
    public int count() {
        return cateDao.count();
    }
}
