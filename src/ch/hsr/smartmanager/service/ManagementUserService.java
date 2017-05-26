package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.data.repository.ManagementUserRepository;

@Component
public class ManagementUserService implements UserDetailsService{

	@Autowired
	private ManagementUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ManagementUser user = userRepository.findByUsername(username);
		if(user == null){
            throw new UsernameNotFoundException(username);
        }else{
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        }
	}
	
	public ManagementUser findUserByName(String username) {
		return userRepository.findByUsername(username);
	}
	
	public boolean deleteUser(String username) {
		if(username.equals("admin")) {
			System.out.println("Hier");
			return false;
		}
		else {
			userRepository.removeByUsername(username);
			return true;			
		}
	}
	
	public boolean checkOldPassword(String id, String password) {
		ManagementUser user = userRepository.findOne(id);
		if(user != null && user.getPassword().equals(password)) {
			return true;
		}
		else return false;
	}
	
	public boolean updateUser(String id, String firstPassword, String secondPassword) {
		if(firstPassword.equals(secondPassword)) {
			ManagementUser user = userRepository.findOne(id);
			user.setPassword(passwordEncoder.encode(firstPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	public boolean addUser(String username, String firstPassword, String secondPassword) {
		System.out.println(username + " : " + firstPassword + " : " + secondPassword);
		if(firstPassword.equals(secondPassword) && !userRepository.existsByUsername(username)) {
			ManagementUser user = new ManagementUser(username, passwordEncoder.encode(firstPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public List<ManagementUser> findAll() {
		return userRepository.findAll();
	}

	public boolean checkUser(String username) {
		return userRepository.existsByUsername(username);
	}
	

	
	
}
