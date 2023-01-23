package org.demo.hibernate.service;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService{
    @Override
    public void createUser(User user1) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(user1);
        t.commit();
        factory.close();
        session.close();
        System.out.println("successfully created user "+user1.getFirstName()+" "+user1.getLastName());
    }

    @Override
    public void updateUser(User user, String phoneNumber) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        user.setPhoneNumber(phoneNumber);
        session.merge(user);
        session.getTransaction().commit();
        factory.close();
        session.close();
        System.out.println("successfully updated user "+user.getFirstName()+" "+user.getLastName()+ " phone number to "+user.getPhoneNumber());
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "FROM User";
        TypedQuery query = session.createQuery(hql);
        List<User> results = query.getResultList();
        factory.close();
        session.close();
        return results;
    }

    @Override
    public User findUserByEmail(String email) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "FROM User u WHERE u.email =:email";
        TypedQuery query = session.createQuery(hql, User.class);
        query.setParameter("email",email);
        User user = (User) query.getSingleResult();
        factory.close();
        session.close();
        return user;
    }

    @Override
    public void delete(int id) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        User u = new User();
        u.setId(id);
        session.delete(u);
        t.commit();
        factory.close();
        session.close();
    }
}
