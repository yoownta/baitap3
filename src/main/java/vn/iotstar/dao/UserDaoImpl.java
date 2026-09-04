package vn.iotstar.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.entity.User;

public class UserDaoImpl implements IUserDao {

    @Override
    public void insert(User user) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
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
    public void update(User user) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
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
    public void delete(int id) throws Exception {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            User user = enma.find(User.class, id);
            if (user != null) {
                enma.remove(user);
            } else {
                throw new Exception("Không tìm thấy người dùng!");
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
    public User findById(int id) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(User.class, id);
        } finally {
            enma.close();
        }
    }

    @Override
    public User findByUsername(String username) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT u FROM User u WHERE u.userName = :username";
        try {
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("username", username);
            List<User> list = query.getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public User findByEmail(String email) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        try {
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("email", email);
            List<User> list = query.getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public User findByUsernameOrEmail(String usernameOrEmail) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT u FROM User u WHERE u.userName = :val OR u.email = :val";
        try {
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("val", usernameOrEmail);
            List<User> list = query.getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } finally {
            enma.close();
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT count(u) FROM User u WHERE u.email = :email";
        try {
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } finally {
            enma.close();
        }
    }

    @Override
    public boolean checkExistUsername(String username) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT count(u) FROM User u WHERE u.userName = :username";
        try {
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("username", username);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } finally {
            enma.close();
        }
    }

    @Override
    public boolean checkExistPhone(String phone) {
        EntityManager enma = JpaConfig.getEntityManager();
        String jpql = "SELECT count(u) FROM User u WHERE u.phone = :phone";
        try {
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("phone", phone);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            TypedQuery<User> query = enma.createNamedQuery("User.findAll", User.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }
}
