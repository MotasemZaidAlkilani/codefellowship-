package com.example.codeFellows.repositries;

import com.example.codeFellows.models.post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postRepositry extends JpaRepository<post,Integer> {
}
