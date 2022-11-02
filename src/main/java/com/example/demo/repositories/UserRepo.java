package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class UserRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void deleteById(Long id) {
        entityManager.remove(getById(id));
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public User findByUsername(String username) {
        return entityManager.createQuery("select u from User u where u.username = :username", User.class).setParameter("username", username).getSingleResult();
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    public void save(User user) {
        entityManager.persist(user);
    }
}
