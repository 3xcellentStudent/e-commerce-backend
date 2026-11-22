package com.server.databases.mongodb.models.product.variation;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;
// import com.server.databases.mongodb.models.product.variation.ProductVariationModel.Options;;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ProductVariationModel {
 
  private @Id String id;
  private @Indexed @NotBlank String parentId;
  private @Valid  StockInfo stockInfo;
  private @NotBlank String productName;
  private Options options;
  private Image image;
  private List<Description> descriptions;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Image {
    private String media = "";
    private String src = "";
    private String srcset = "";
    private String alt = "";
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Description {
    private String summary;
    private List<String> presentable;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StockInfo {
    private @Min(0) Integer amountMax = 0;
    private @Min(0) Integer amountAvailable = 0;
    private @Min(0) Integer amountReserved = 0;
    private @Min(0) @NotNull Long priceInCents = 0L;
    private @NotBlank(message = "Cannot be blank") String currency = "CAD";
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Options {
    private String attribute = "";
    private String unit = "";
    private String value = "";
  }

  public ProductVariationModel(CreateVariationByParentId body){
    this.parentId = body.parentId();
    this.stockInfo = body.stockInfo() != null ? body.stockInfo() : new StockInfo();
    this.options = body.options() != null ? body.options() : new Options();
    this.productName = body.productName();
    this.image = body.image() != null ? body.image() : new Image();
    this.descriptions = body.descriptions() != null ? body.descriptions() : new ArrayList<>();
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }
}
