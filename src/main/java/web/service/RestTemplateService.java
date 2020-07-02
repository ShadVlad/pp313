package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class RestTemplateService {
    private final String SERVER_URL = "http://localhost:8000/api/admin/";
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void deleteUser(Long id) {
        //final String uri = "http://localhost:8000/api/admin/";
        //RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(SERVER_URL + "delete/" + id);;
    }

    public List<User> getUsersList() {
        //RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<User>> response =
                    restTemplate.exchange(SERVER_URL + "users",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<User>>(){});
            return response.getBody();
        }

    public UserDetails getUserByName(String username) {
        //RestTemplate restTemplate = new RestTemplate();
        UserDetails userDetails =restTemplate.getForObject("http://localhost:8000/api/" +
                        username, User.class);

        return userDetails;
    }

    public User getUserById(Long id) {
        String uri = "http://localhost:8000/api/user/" + id;
        //RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(uri, User.class, id);
        return user;
    }

//    public User getUserAfterLogin(String username) {
//        final String uri = "http://localhost:8000/user/{username}";
//        RestTemplate restTemplate = new RestTemplate();
//        User user = restTemplate.getForObject(uri, User.class, username);
//        return user;
//    }

    public void createUser(User user) {
        //RestTemplate restTemplate = new RestTemplate();
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        restTemplate.postForObject(SERVER_URL + "add", user, User.class);
    }

    public void updateUser(User user) {
        if (!user.getPassword().isEmpty()) {
            String hashPassword = user.getPassword();
            user.setPassword(hashPassword);
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response =
                restTemplate.exchange(SERVER_URL + "edit",
                        HttpMethod.PUT,
                        new HttpEntity<>(user),
                        User.class);
    }

    public Role getRoleByName(String role) {
        //RestTemplate restTemplate = new RestTemplate();
        Optional<Role> roleOptional =
                Optional.ofNullable(restTemplate.getForObject("http://localhost:8000/api/roles/" +
                        role, Role.class));
        return roleOptional.get();
    }
    public List<Role> getAllRoles(){
        String uri = "http://localhost:8000/api/roles";
        //RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Role>> response =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Role>>(){});
        return response.getBody();
    }

}
