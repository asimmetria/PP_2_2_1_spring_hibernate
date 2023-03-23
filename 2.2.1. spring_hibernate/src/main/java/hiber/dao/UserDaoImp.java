package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.openSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
        Query query = sessionFactory.openSession().createQuery(hql, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return (User) query.getSingleResult();
    }
}
