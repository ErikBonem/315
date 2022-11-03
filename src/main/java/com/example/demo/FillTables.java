package com.example.demo;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Repository
@Transactional
public class FillTables {
    @PersistenceContext
    EntityManager entityManager;
    public void fillTables() {
            entityManager.createNativeQuery("INSERT INTO users (id, age, email, password, username) values (1, 24, 'max@mail.ru', 111, 'Max')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO users (id, age, email, password, username) values (2, 23, 'dima@mail.ru', 111, 'Dima')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO roles (id, name) value (1, 'ROLE_ADMIN')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO roles (id, name) value (2, 'ROLE_USER')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO users_roles (user_id, role_id) values (1,1)").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO users_roles (user_id, role_id) values (2,2)").executeUpdate();
    }
}
