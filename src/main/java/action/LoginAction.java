package action;

import java.util.List;

import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;

import domain.User;
import utils.UsefulUtils;

@Name("login")
@Scope(ScopeType.SESSION)
public class LoginAction implements Login {

	@In
	private EntityManager entityManager;

	@In
	User sessionUser;

	public String login() {
		User foundUser = findUser(sessionUser);
		if (foundUser == null) {
			FacesMessages.instance().add("Login or password is incorrect");
			return null;
		}
		injectUserToContext(foundUser);
		return "selectFilms.xhtml";
		
	}

	private void injectUserToContext(User user) {
		Contexts.getSessionContext().set("sessionUser", user);
	}

	private User findUser(User user) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> users = query.from(User.class);
		Predicate loginPredicate = criteriaBuilder.equal(users.get("login"), user.getLogin());
		Predicate passwordPredicate = criteriaBuilder.equal(users.get("password"), UsefulUtils.hashPassword(user.getPassword()));
		query.where(criteriaBuilder.and(loginPredicate, passwordPredicate));
		List<User> result = entityManager.createQuery(query).getResultList();
		return UsefulUtils.getFirst(result);
	}

	@Remove
	public void destroy() {

	}

	public String logout() {
		Contexts.getSessionContext().set("sessionUser", null);
		return "login.xhtml";
	}

}
