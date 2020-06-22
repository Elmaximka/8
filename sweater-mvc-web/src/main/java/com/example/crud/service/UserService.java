package com.example.crud.service;

import com.example.crud.model.Role;
import com.example.crud.model.User;
import com.example.crud.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void addNewUser(User user){
        userRepository.save(user);
    }
    public void deleteUserByName(String name){
        User user = userRepository.findByName(name);
        userRepository.delete(user);
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try {
            user = getUserByName(s);
        } catch (NoResultException e) {
            throw new UsernameNotFoundException(s);
        }
        return new User(user.getUsername(),
                user.getPassword(),
                (Collection<Role>) user.getAuthorities());
    }

}
