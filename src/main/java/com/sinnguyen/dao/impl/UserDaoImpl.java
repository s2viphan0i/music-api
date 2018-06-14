package com.sinnguyen.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinnguyen.dao.UserDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.SearchDTO;

public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public void add(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}

	public void edit(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	public void delete(User user) {
		sessionFactory.getCurrentSession().delete(user);
	}

	public User getById(int id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	public List<User> search(SearchDTO searchDTO) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(User.class);
		if(searchDTO.getKeyword()!=null) {
			cr.add(Restrictions.ilike("name", searchDTO.getKeyword()));
		}
		return cr.list();
	}

}
