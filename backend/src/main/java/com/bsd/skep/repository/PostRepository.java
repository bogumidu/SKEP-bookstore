package com.bsd.skep.repository;

import com.bsd.skep.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
