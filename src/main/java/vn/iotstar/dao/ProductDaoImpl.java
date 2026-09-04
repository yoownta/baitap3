package vn.iotstar.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.entity.Product;

public class ProductDaoImpl implements IProductDao {

    @Override
    public void insert(Product product) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(product);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Product product) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(product);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void delete(int productId) throws Exception {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Product product = enma.find(Product.class, productId);
            if (product != null) {
                enma.remove(product);
            } else {
                throw new Exception("Không tìm thấy sản phẩm!");
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Product findById(int productId) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(Product.class, productId);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findAll() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> query = enma.createNamedQuery("Product.findAll", Product.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findTop10Latest() {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p ORDER BY p.productId DESC";
        try {
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            query.setMaxResults(10);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findAll(int page, int pageSize) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p ORDER BY p.productId DESC";
        try {
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            int firstResult = Math.max(0, (page - 1) * pageSize);
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int count() {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT count(p) FROM Product p";
        try {
            Query query = enma.createQuery(jpql);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> findByCategoryId(int categoryId, int page, int pageSize) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p WHERE p.category.categoryid = :catId ORDER BY p.productId DESC";
        try {
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            query.setParameter("catId", categoryId);
            int firstResult = Math.max(0, (page - 1) * pageSize);
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int countByCategoryId(int categoryId) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT count(p) FROM Product p WHERE p.category.categoryid = :catId";
        try {
            Query query = enma.createQuery(jpql);
            query.setParameter("catId", categoryId);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Product> searchByName(String keyword) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p WHERE p.productName like :kw ORDER BY p.productId DESC";
        try {
            TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            enma.close();
        }
    }
}
