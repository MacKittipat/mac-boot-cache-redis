package com.mackittipat.bootcacheredis.web;

import com.mackittipat.bootcacheredis.domain.User;
import com.mackittipat.bootcacheredis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        log.debug("Saving user : {}", user.toString());
        userRepository.save(user);
    }

    @CachePut(value = "user", key = "#user.id")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public User updateById(@RequestBody User user) {
        log.debug("Updating user : {}", user.toString());
        return userRepository.save(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> findAll() {
        log.debug("Finding all user");
        return (List<User>) userRepository.findAll();
    }

    @Cacheable(value = "user", key = "#userId", unless = "#result.id == null") // Does not cache if result.id is null
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User findById(@PathVariable Long userId) {
        log.debug("Finding user id : {}", userId);
        return userRepository.findById(userId).orElseGet(User::new);
    }

    @CacheEvict(value = "user")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long userId) {
        userRepository.deleteById(userId);
    }

}
