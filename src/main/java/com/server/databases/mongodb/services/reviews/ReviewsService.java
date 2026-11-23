package com.server.databases.mongodb.services.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.reviews.ReviewsModel;

@Service
public class ReviewsService {

  @Value("${databases.mongodb.collections.reviews}")
  private String collection;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ReviewsModel createOne(ReviewsModel body){
    ReviewsModel insertedDoc = mongoTemplate.insert(body, collection);

    return insertedDoc;
  }

  public Double getAverageRating(String id){
    Query query = Query.query(Criteria.where("parentId").is(id));
    return mongoTemplate.
  }

}
