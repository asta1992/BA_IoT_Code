package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
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
		ManagementUser user = userRepository.findByUsername(username.toLowerCase());
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
	
	private boolean checkOldPassword(String id, String password) {
		ManagementUser user = userRepository.findOne(id);
		if(user != null && passwordEncoder.matches(password, user.getPassword())) {
			return true;
		}
		else return false;
	}
	
	public JSONObject updateUser(String id, String oldPassword, String firstPassword, String secondPassword) throws JSONException {
		JSONObject result = new JSONObject();
		if(!checkOldPassword(id, oldPassword)) {
			result.put("oldPasswordError", true);
		}
		else if(firstPassword.length() < 8) {
			result.put("passwordLength", true);
		}
		else if(!firstPassword.equals(secondPassword)) {
			result.put("matchError", true);
		}
		else {
			ManagementUser user = userRepository.findOne(id);
			user.setPassword(passwordEncoder.encode(firstPassword));
			userRepository.save(user);
			result.put("Changed", true);
			return result;
		}
		return result;
	}
	
	public JSONObject addUser(String username, String firstPassword, String secondPassword) throws JSONException {
		JSONObject result = new JSONObject();
		System.out.println(username + " : " + firstPassword + " : " + secondPassword);
		if(userRepository.existsByUsername(username)) {
			result.put("existsError", true);
		}
		
		else if(!firstPassword.equals(secondPassword)) {
			result.put("matchError", true);

		}
		else if(firstPassword.length() < 8) {
			result.put("passwordLength", true);
		}
		else if(username.length() < 4) {
			result.put("usernameLength", true);
		}
		else {
			ManagementUser user = new ManagementUser(username, passwordEncoder.encode(firstPassword));
			userRepository.save(user);
			result.put("username", username);
		}
		return result;
	}

	public List<ManagementUser> findAll() {
		return userRepository.findAll();
	}

	public boolean checkUser(String username) {
		return userRepository.existsByUsername(username);
	}
	

	
	
}
