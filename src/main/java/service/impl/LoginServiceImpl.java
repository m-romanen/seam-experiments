package service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import domain.User;
import service.LoginService;
import utils.UsefulUtils;

@AutoCreate
@Name("loginService")
@Scope(ScopeType.APPLICATION)
public class LoginServiceImpl implements LoginService {
	
	@In
	private EntityManager entityManager;

	public User findUser(User user) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> users = query.from(User.class);
		Predicate loginPredicate = criteriaBuilder.equal(users.get("login"), user.getLogin());
		Predicate passwordPredicate = criteriaBuilder.equal(users.get("password"), UsefulUtils.hashPassword(user.getPassword()));
		query.where(criteriaBuilder.and(loginPredicate, passwordPredicate));
		List<User> result = entityManager.createQuery(query).getResultList();
		return UsefulUtils.getFirst(result);
	}

}
