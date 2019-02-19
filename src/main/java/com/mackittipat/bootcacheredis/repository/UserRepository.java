package com.mackittipat.bootcacheredis.repository;

import com.mackittipat.bootcacheredis.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
