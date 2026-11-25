package com.server.databases.mongodb.models.product.parent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.databases.mongodb.dto.product.CreateNewProductDto;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
// import com.server.databases.mongodb.models.reviews.components.ReviewsSnapshot;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@CompoundIndexes({
  @CompoundIndex(name = "category_rating_idx", def = "{'category': 1, 'rating': -1}"),
  @CompoundIndex(name = "category_created_idx", def = "{'category': 1, 'createdAt': -1}")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ProductParentModel {

  private @Id String id;
  // private Integer rating;
  private @NotBlank @TextIndexed String productName;
  private @NotEmpty Descriptions descriptions;
  private @Builder.Default List<ProductVariationModel> variations = List.of();
  private @NotEmpty Specifications specifications;
  private List<MediaContent> mediaContent;
  private RatingSummary ratingSummary;
  private @Indexed @NotBlank String category;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;

  @Data 
  @NoArgsConstructor 
  @AllArgsConstructor
  public static class RatingSummary {
    private @Min(0) @Max(5) Double averageRating = 0.0;
    private @Min(0) Long totalCount = 0L;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Descriptions {
    private @NotBlank String summary;
    private @NotEmpty List<String> presentable;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MediaContent {
    private boolean isImage;
    private ArrayList<MediaObject> mediaArray;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MediaObject {
      private String media;
      private String src;
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Specifications {
    private ArrayList<String> titles;
    private ArrayList<Properties> properties;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
      private String name;
      private ArrayList<PropertiesArrayObject> array;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertiesArrayObject {
      private String name;
      private String value;
    }
  }

  public ProductParentModel(CreateNewProductDto data){
    this.productName = data.productName();
    this.descriptions = data.descriptions();
    this.variations =  List.of();
    this.specifications = data.specifications();
    this.mediaContent = data.mediaContent();
    this.category = data.category();
    this.ratingSummary = new RatingSummary();
  }

}