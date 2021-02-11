package action.impl;

import javax.ejb.Remove;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;

import action.Login;
import domain.User;
import service.LoginService;

@Name("login")
@Scope(ScopeType.SESSION)
public class LoginAction implements Login {

	@In
	private LoginService loginService;

	@In(required = false)
	User sessionUser;

	public String login() {
		User foundUser = loginService.findUser(sessionUser);
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

	public String logout() {
		Contexts.getSessionContext().set("sessionUser", null);
		return "login.xhtml";
	}
	
	@Remove
	public void destroy() {
	}

}
