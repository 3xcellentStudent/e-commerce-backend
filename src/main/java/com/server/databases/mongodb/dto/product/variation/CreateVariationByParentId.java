package com.server.databases.mongodb.dto.product.variation;

import java.util.List;

import com.google.firebase.database.annotations.NotNull;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.Description;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.Image;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.Options;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.StockInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateVariationByParentId(
  @NotBlank String parentId, 
  @NotBlank String productName, 
  @Valid @NotNull StockInfo stockInfo,
  @Valid @NotNull Options options,
  @Valid Image image,
  @Valid List<Description> descriptions
){}
