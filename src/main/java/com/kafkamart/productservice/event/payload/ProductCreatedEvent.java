package com.kafkamart.productservice.event.payload;


public class ProductCreatedEvent {


	    private Long productId;
	    private String productName;
	    private Integer stockQuantity;
	    private Double price;
	    private Long categoryId;
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public Integer getStockQuantity() {
			return stockQuantity;
		}
		public void setStockQuantity(Integer stockQuantity) {
			this.stockQuantity = stockQuantity;
		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public Long getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}
		public ProductCreatedEvent(Long productId, String productName, Integer stockQuantity, Double price,
				Long categoryId) {
			super();
			this.productId = productId;
			this.productName = productName;
			this.stockQuantity = stockQuantity;
			this.price = price;
			this.categoryId = categoryId;
		}
		public ProductCreatedEvent() {
			super();
			// TODO Auto-generated constructor stub
		}

	    
	


}