package io.kauri.dbt.service.repository;

import io.kauri.dbt.model.dto.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMongoRepository extends MongoRepository<Blog, String> {

    Blog findOneByUser(String user);
}
