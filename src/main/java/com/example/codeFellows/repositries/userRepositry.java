package com.example.codeFellows.repositries;

import com.example.codeFellows.models.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface userRepositry extends CrudRepository<ApplicationUser,Integer> {
    ApplicationUser findByUsername(String username);
    ApplicationUser findById(int id);

}
