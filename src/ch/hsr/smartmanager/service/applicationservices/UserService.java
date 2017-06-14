package ch.hsr.smartmanager.service.applicationservices;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.data.repositories.ManagementUserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private ManagementUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ManagementUser user = userRepository.findByUsername(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		}
	}

	public ManagementUser findUserByName(String username) {
		return userRepository.findByUsername(username);
	}

	public boolean deleteUser(String username) {
		if (username.equals("admin")) {
			return false;
		} else {
			userRepository.removeByUsername(username);
			return true;
		}
	}

	private boolean checkOldPassword(String id, String password) {
		ManagementUser user = userRepository.findOne(id);
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return true;
		} else
			return false;
	}

	public JSONObject updateUser(String id, String oldPassword, String firstPassword, String secondPassword) {
		JSONObject result = new JSONObject();
		try {
			if (!checkOldPassword(id, oldPassword)) {
				result.put("oldPasswordError", true);
			} else if (firstPassword.length() < 8 || firstPassword.length() > 50) {
				result.put("passwordLength", true);
			} else if (!firstPassword.equals(secondPassword)) {
				result.put("matchError", true);
			} else {
				ManagementUser user = userRepository.findOne(id);
				user.setPassword(passwordEncoder.encode(firstPassword));
				userRepository.save(user);
				result.put("Changed", true);
				return result;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	private boolean validateUsername(String username) {
		final String regex = "(?=^.{4,20}$)[a-zA-Z0-9]*$";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(username);
		while (matcher.find()) {
			return false;
		}
		return true;
	}

	public JSONObject addUser(String username, String firstPassword, String secondPassword) {

		JSONObject result = new JSONObject();

		try {
			if (username.length() < 4 || username.length() > 20) {
				result.put("usernameLength", true);
			} else if (validateUsername(username)) {
				result.put("invalidCharError", true);
			} else if (checkUser(username)) {
				result.put("existsError", true);
			} else if (!firstPassword.equals(secondPassword)) {
				result.put("matchError", true);
			} else if (firstPassword.length() < 8 || firstPassword.length() > 50) {
				result.put("passwordLength", true);

			} else {
				ManagementUser user = new ManagementUser(username, passwordEncoder.encode(firstPassword));
				userRepository.save(user);
				result.put("username", username);
			}
		} catch (JSONException e) {
			e.printStackTrace();
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
