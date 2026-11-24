package com.server.databases.mongodb.services.reviews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.product.parent.ProductParentModel.RatingSummary;
import com.server.databases.mongodb.models.reviews.ReviewsModel;

@Service
public class ReviewsService {

  private Logger logger = LoggerFactory.getLogger(ReviewsService.class);

  @Value("${databases.mongodb.collections.reviews}")
  private String collection;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ReviewsModel createOne(ReviewsModel body){
    ReviewsModel insertedDoc = mongoTemplate.insert(body, collection);

    return insertedDoc;
  }

  public RatingSummary getAverageRatingByParentId(String parentId){
    // Query query = Query.query(Criteria.where("parentId").is(parentId));
    try {
      Aggregation aggregation = Aggregation.newAggregation(
        Aggregation
        .match(Criteria.where("parentId")
        .is(parentId)),
        Aggregation
        .group("parentId")
        .avg("rating").as("averageRating")
        .count().as("totalCount"),
        Aggregation
        .project()
        // .and("_id").as("parentId")
        .and("averageRating").as("averageRating")
        .and("totalCount").as("totalCount")
      );
  
      AggregationResults<RatingSummary> results = mongoTemplate
      .aggregate(aggregation, collection, RatingSummary.class);
  
      return results.getUniqueMappedResult();
    } catch(NullPointerException err){
      String message = "Occured error while aggregating rating summary for product " 
      + parentId + " because of aggregation return Null ! Null will be replaced by default RatingSummary class.";
      logger.error(message);

      return new RatingSummary();
    }
  }

}
