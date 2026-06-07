package com.kafkamart.productservice.dto.response;


public class CategoryResponse {


	

	private Long id;
    private String categoryName;
    private String description;

    public CategoryResponse(Long id, String categoryName, String description) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.description = description;
    }
		
		
    public CategoryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}