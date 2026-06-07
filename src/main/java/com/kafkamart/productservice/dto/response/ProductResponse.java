package com.kafkamart.productservice.dto.response;

public class ProductResponse {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	private Long id;
    private String productName;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String skuCode;
    private String brand;
    private String imageUrl;
    private String categoryId;
    private String categoryName;

    public ProductResponse(Long id, String productName, String description, Double price, Integer stockQuantity,
			String skuCode, String brand, String imageUrl, String categoryId, String categoryName) {
		super();
		this.id = id;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.skuCode = skuCode;
		this.brand = brand;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ProductResponse(Long id, String productName, String description, Double price, Integer stockQuantity,
			String skuCode, String brand, String imageUrl, String categoryId) {
		super();
		this.id = id;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.skuCode = skuCode;
		this.brand = brand;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
	}

	public ProductResponse() {
    }

    // Generate Getter Setter for all fields
}
