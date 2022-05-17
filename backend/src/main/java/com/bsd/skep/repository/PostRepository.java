package com.bsd.skep.repository;

import com.bsd.skep.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PostRepository extends CrudRepository<Post, UUID> {
}
