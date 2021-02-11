package service;

import javax.ejb.Local;

import domain.User;

@Local
public interface LoginService {
	
	User findUser(User user);
	
}
