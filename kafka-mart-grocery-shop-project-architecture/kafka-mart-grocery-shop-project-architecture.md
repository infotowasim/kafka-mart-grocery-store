## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***1. AUTH SERVICE PROJECT STRUCTURE:***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***









#### **auth-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.auth**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── AuthController.java**

#### **│       │   ├── VerificationController.java**

#### **│       │   └── PasswordController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── AuthService.java**

#### **│       │   │   ├── OtpService.java**

#### **│       │   │   ├── PasswordService.java**

#### **│       │   │   ├── JwtService.java**

#### **│       │   │   ├── EmailService.java**

#### **│       │   │   ├── RefreshTokenService.java**

#### **│       │   │   └── AccountLockService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── AuthServiceImpl.java**

#### **│       │       ├── OtpServiceImpl.java**

#### **│       │       ├── PasswordServiceImpl.java**

#### **│       │       ├── JwtServiceImpl.java**

#### **│       │       ├── EmailServiceImpl.java**

#### **│       │       ├── RefreshTokenServiceImpl.java**

#### **│       │       └── AccountLockServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── UserRepository.java**

#### **│       │   ├── RoleRepository.java**

#### **│       │   ├── RefreshTokenRepository.java**

#### **│       │   ├── OtpVerificationRepository.java**

#### **│       │   └── PasswordResetTokenRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── User.java**

#### **│       │   ├── Role.java**

#### **│       │   ├── RefreshToken.java**

#### **│       │   ├── OtpVerification.java**

#### **│       │   └── PasswordResetToken.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── RegisterRequest.java**

#### **│       │   │   ├── LoginRequest.java**

#### **│       │   │   ├── VerifyOtpRequest.java**

#### **│       │   │   ├── ResendOtpRequest.java**

#### **│       │   │   ├── ForgotPasswordRequest.java**

#### **│       │   │   ├── ResetPasswordRequest.java**

#### **│       │   │   ├── ChangePasswordRequest.java**

#### **│       │   │   └── RefreshTokenRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── AuthResponse.java**

#### **│       │       ├── JwtResponse.java**

#### **│       │       ├── UserResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaginationPayload.java**

#### **│       │**

#### **│       ├── security**

#### **│       │   │**

#### **│       │   ├── config**

#### **│       │   │   └── SecurityConfig.java**

#### **│       │   │**

#### **│       │   ├── filter**

#### **│       │   │   └── JwtAuthenticationFilter.java**

#### **│       │   │**

#### **│       │   ├── entrypoint**

#### **│       │   │   └── JwtAuthenticationEntryPoint.java**

#### **│       │   │**

#### **│       │   ├── service**

#### **│       │   │   └── CustomUserDetailsService.java**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── AccessDeniedHandlerImpl.java**

#### **│       │   │**

#### **│       │   └── util**

#### **│       │       └── JwtUtil.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── AuthEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── UserEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── UserRegisteredEvent.java**

#### **│       │   │   ├── UserVerifiedEvent.java**

#### **│       │   │   ├── PasswordResetEvent.java**

#### **│       │   │   └── AccountLockedEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── MailConfig.java**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   ├── AsyncConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── UserMapper.java**

#### **│       │   ├── RoleMapper.java**

#### **│       │   └── AuthMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── auth**

#### **│       │   │   ├── InvalidCredentialsException.java**

#### **│       │   │   ├── TokenExpiredException.java**

#### **│       │   │   └── RefreshTokenException.java**

#### **│       │   │**

#### **│       │   ├── otp**

#### **│       │   │   ├── InvalidOtpException.java**

#### **│       │   │   └── OtpExpiredException.java**

#### **│       │   │**

#### **│       │   └── user**

#### **│       │       ├── UserNotFoundException.java**

#### **│       │       ├── EmailAlreadyExistsException.java**

#### **│       │       └── PhoneAlreadyExistsException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── ApiConstants.java**

#### **│       │   ├── SecurityConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   ├── MailConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── RoleType.java**

#### **│       │   ├── AccountStatus.java**

#### **│       │   ├── OtpType.java**

#### **│       │   ├── TokenType.java**

#### **│       │   └── AuthProvider.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── OtpGeneratorUtil.java**

#### **│           ├── DateTimeUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           ├── PasswordGeneratorUtil.java**

#### **│           └── RandomStringGeneratorUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **├── application-prod.yml**

#### &#x20;   **│**

#### &#x20;   **├── db**

#### &#x20;   **│   └── migration**

#### &#x20;   **│**

#### &#x20;   **├── templates**

#### &#x20;   **│   ├── otp-email.html**

#### &#x20;   **│   ├── welcome-email.html**

#### &#x20;   **│   └── reset-password-email.html**

#### &#x20;   **│**

#### &#x20;   **└── static**









## **\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~**

## **2. *USER SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***





#### **user-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.user**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── UserProfileController.java**

#### **│       │   ├── AddressController.java**

#### **│       │   └── ProfileImageController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── UserProfileService.java**

#### **│       │   │   ├── AddressService.java**

#### **│       │   │   ├── ProfileImageService.java**

#### **│       │   │   └── UserEventService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── UserProfileServiceImpl.java**

#### **│       │       ├── AddressServiceImpl.java**

#### **│       │       ├── ProfileImageServiceImpl.java**

#### **│       │       └── UserEventServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── UserProfileRepository.java**

#### **│       │   ├── AddressRepository.java**

#### **│       │   └── ProfileImageRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── UserProfile.java**

#### **│       │   ├── Address.java**

#### **│       │   └── ProfileImage.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── CreateUserProfileRequest.java**

#### **│       │   │   ├── UpdateUserProfileRequest.java**

#### **│       │   │   ├── CreateAddressRequest.java**

#### **│       │   │   ├── UpdateAddressRequest.java**

#### **│       │   │   ├── SetDefaultAddressRequest.java**

#### **│       │   │   └── UploadProfileImageRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── UserProfileResponse.java**

#### **│       │       ├── AddressResponse.java**

#### **│       │       ├── ProfileImageResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaginationPayload.java**

#### **│       │**

#### **│       ├── client**

#### **│       │   │**

#### **│       │   └── AuthServiceClient.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── UserEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── AuthEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── UserProfileCreatedEvent.java**

#### **│       │   │   ├── UserProfileUpdatedEvent.java**

#### **│       │   │   ├── AddressAddedEvent.java**

#### **│       │   │   └── ProfileImageUploadedEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   ├── AsyncConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── UserProfileMapper.java**

#### **│       │   ├── AddressMapper.java**

#### **│       │   └── ProfileImageMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── profile**

#### **│       │   │   ├── UserProfileNotFoundException.java**

#### **│       │   │   └── ProfileAlreadyExistsException.java**

#### **│       │   │**

#### **│       │   ├── address**

#### **│       │   │   ├── AddressNotFoundException.java**

#### **│       │   │   └── DefaultAddressNotFoundException.java**

#### **│       │   │**

#### **│       │   └── image**

#### **│       │       ├── InvalidImageException.java**

#### **│       │       └── ImageUploadFailedException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── ApiConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   ├── ImageConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── AddressType.java**

#### **│       │   ├── ProfileStatus.java**

#### **│       │   └── ImageType.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── FileUploadUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           ├── DateTimeUtil.java**

#### **│           └── ImageCompressionUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **├── application-prod.yml**

#### &#x20;   **│**

#### &#x20;   **└── static**











## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***3. PRODUCT SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***









#### **product-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.product**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── CategoryController.java**

#### **│       │   ├── BrandController.java**

#### **│       │   ├── ProductController.java**

#### **│       │   ├── ProductImageController.java**

#### **│       │   └── ProductSearchController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── CategoryService.java**

#### **│       │   │   ├── BrandService.java**

#### **│       │   │   ├── ProductService.java**

#### **│       │   │   ├── ProductImageService.java**

#### **│       │   │   └── ProductSearchService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── CategoryServiceImpl.java**

#### **│       │       ├── BrandServiceImpl.java**

#### **│       │       ├── ProductServiceImpl.java**

#### **│       │       ├── ProductImageServiceImpl.java**

#### **│       │       └── ProductSearchServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── CategoryRepository.java**

#### **│       │   ├── BrandRepository.java**

#### **│       │   ├── ProductRepository.java**

#### **│       │   └── ProductImageRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── Category.java**

#### **│       │   ├── Brand.java**

#### **│       │   ├── Product.java**

#### **│       │   └── ProductImage.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── CreateCategoryRequest.java**

#### **│       │   │   ├── UpdateCategoryRequest.java**

#### **│       │   │   ├── CreateBrandRequest.java**

#### **│       │   │   ├── UpdateBrandRequest.java**

#### **│       │   │   ├── CreateProductRequest.java**

#### **│       │   │   ├── UpdateProductRequest.java**

#### **│       │   │   ├── UploadProductImageRequest.java**

#### **│       │   │   └── ProductSearchRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── CategoryResponse.java**

#### **│       │       ├── BrandResponse.java**

#### **│       │       ├── ProductResponse.java**

#### **│       │       ├── ProductImageResponse.java**

#### **│       │       ├── ProductSearchResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaginationPayload.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── ProductEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── InventoryEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── ProductCreatedEvent.java**

#### **│       │   │   ├── ProductUpdatedEvent.java**

#### **│       │   │   ├── ProductDeletedEvent.java**

#### **│       │   │   └── ProductImageUploadedEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   ├── AsyncConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── CategoryMapper.java**

#### **│       │   ├── BrandMapper.java**

#### **│       │   ├── ProductMapper.java**

#### **│       │   └── ProductImageMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── category**

#### **│       │   │   ├── CategoryNotFoundException.java**

#### **│       │   │   └── CategoryAlreadyExistsException.java**

#### **│       │   │**

#### **│       │   ├── brand**

#### **│       │   │   ├── BrandNotFoundException.java**

#### **│       │   │   └── BrandAlreadyExistsException.java**

#### **│       │   │**

#### **│       │   ├── product**

#### **│       │   │   ├── ProductNotFoundException.java**

#### **│       │   │   └── ProductAlreadyExistsException.java**

#### **│       │   │**

#### **│       │   └── image**

#### **│       │       ├── InvalidImageException.java**

#### **│       │       └── ProductImageUploadFailedException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── ApiConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   ├── ProductConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── ProductStatus.java**

#### **│       │   ├── ProductType.java**

#### **│       │   └── ImageType.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── FileUploadUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           ├── DateTimeUtil.java**

#### **│           └── SlugGeneratorUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **├── application-prod.yml**

#### &#x20;   **│**

#### &#x20;   **├── templates**

#### &#x20;   **└── static**













## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***4. INVENTORY SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***







#### **inventory-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.inventory**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── InventoryController.java**

#### **│       │   ├── StockController.java**

#### **│       │   └── StockMovementController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── InventoryService.java**

#### **│       │   │   ├── StockService.java**

#### **│       │   │   ├── StockMovementService.java**

#### **│       │   │   └── LowStockAlertService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── InventoryServiceImpl.java**

#### **│       │       ├── StockServiceImpl.java**

#### **│       │       ├── StockMovementServiceImpl.java**

#### **│       │       └── LowStockAlertServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── InventoryRepository.java**

#### **│       │   └── StockMovementRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── Inventory.java**

#### **│       │   └── StockMovement.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── AddStockRequest.java**

#### **│       │   │   ├── ReduceStockRequest.java**

#### **│       │   │   ├── UpdateStockRequest.java**

#### **│       │   │   └── StockAvailabilityRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── InventoryResponse.java**

#### **│       │       ├── StockMovementResponse.java**

#### **│       │       ├── StockAvailabilityResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaginationPayload.java**

#### **│       │**

#### **│       ├── client**

#### **│       │   │**

#### **│       │   └── ProductServiceClient.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── InventoryEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── ProductEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── StockAddedEvent.java**

#### **│       │   │   ├── StockReducedEvent.java**

#### **│       │   │   ├── LowStockEvent.java**

#### **│       │   │   └── OutOfStockEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   ├── AsyncConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── InventoryMapper.java**

#### **│       │   └── StockMovementMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── inventory**

#### **│       │   │   ├── InventoryNotFoundException.java**

#### **│       │   │   └── ProductInventoryNotFoundException.java**

#### **│       │   │**

#### **│       │   └── stock**

#### **│       │       ├── InsufficientStockException.java**

#### **│       │       ├── OutOfStockException.java**

#### **│       │       └── InvalidStockQuantityException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── InventoryConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── StockMovementType.java**

#### **│       │   ├── InventoryStatus.java**

#### **│       │   └── AlertType.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── ValidationUtil.java**

#### **│           ├── DateTimeUtil.java**

#### **│           └── StockCalculationUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **└── application-prod.yml**











## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***5. CART SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***







#### **cart-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.cart**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── CartController.java**

#### **│       │   ├── CartItemController.java**

#### **│       │   └── CartCheckoutController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── CartService.java**

#### **│       │   │   ├── CartItemService.java**

#### **│       │   │   ├── CartCalculationService.java**

#### **│       │   │   └── CheckoutValidationService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── CartServiceImpl.java**

#### **│       │       ├── CartItemServiceImpl.java**

#### **│       │       ├── CartCalculationServiceImpl.java**

#### **│       │       └── CheckoutValidationServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── CartRepository.java**

#### **│       │   └── CartItemRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── Cart.java**

#### **│       │   └── CartItem.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── AddToCartRequest.java**

#### **│       │   │   ├── UpdateCartItemRequest.java**

#### **│       │   │   ├── RemoveCartItemRequest.java**

#### **│       │   │   ├── ClearCartRequest.java**

#### **│       │   │   └── CheckoutRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── CartResponse.java**

#### **│       │       ├── CartItemResponse.java**

#### **│       │       ├── CheckoutSummaryResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaginationPayload.java**

#### **│       │**

#### **│       ├── client**

#### **│       │   │**

#### **│       │   ├── ProductServiceClient.java**

#### **│       │   ├── InventoryServiceClient.java**

#### **│       │   └── UserServiceClient.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── CartEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── InventoryEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── ProductAddedToCartEvent.java**

#### **│       │   │   ├── ProductRemovedFromCartEvent.java**

#### **│       │   │   ├── CartClearedEvent.java**

#### **│       │   │   └── CheckoutInitiatedEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   ├── AsyncConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── CartMapper.java**

#### **│       │   └── CartItemMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── cart**

#### **│       │   │   ├── CartNotFoundException.java**

#### **│       │   │   ├── CartEmptyException.java**

#### **│       │   │   └── CartAlreadyExistsException.java**

#### **│       │   │**

#### **│       │   └── item**

#### **│       │       ├── CartItemNotFoundException.java**

#### **│       │       ├── ProductOutOfStockException.java**

#### **│       │       └── InvalidQuantityException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── CartConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── CartStatus.java**

#### **│       │   └── CheckoutStatus.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── CartCalculationUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           └── DateTimeUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **└── application-prod.yml**















## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***6. ORDER SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***





#### **order-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.order**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── OrderController.java**

#### **│       │   ├── OrderTrackingController.java**

#### **│       │   ├── OrderHistoryController.java**

#### **│       │   └── OrderCancellationController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── OrderService.java**

#### **│       │   │   ├── OrderTrackingService.java**

#### **│       │   │   ├── OrderHistoryService.java**

#### **│       │   │   ├── OrderCancellationService.java**

#### **│       │   │   ├── OrderStatusService.java**

#### **│       │   │   └── OrderValidationService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── OrderServiceImpl.java**

#### **│       │       ├── OrderTrackingServiceImpl.java**

#### **│       │       ├── OrderHistoryServiceImpl.java**

#### **│       │       ├── OrderCancellationServiceImpl.java**

#### **│       │       ├── OrderStatusServiceImpl.java**

#### **│       │       └── OrderValidationServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── OrderRepository.java**

#### **│       │   ├── OrderItemRepository.java**

#### **│       │   └── OrderStatusHistoryRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── Order.java**

#### **│       │   ├── OrderItem.java**

#### **│       │   └── OrderStatusHistory.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── PlaceOrderRequest.java**

#### **│       │   │   ├── CancelOrderRequest.java**

#### **│       │   │   ├── UpdateOrderStatusRequest.java**

#### **│       │   │   └── TrackOrderRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── OrderResponse.java**

#### **│       │       ├── OrderItemResponse.java**

#### **│       │       ├── OrderTrackingResponse.java**

#### **│       │       ├── OrderHistoryResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaginationPayload.java**

#### **│       │**

#### **│       ├── client**

#### **│       │   │**

#### **│       │   ├── CartServiceClient.java**

#### **│       │   ├── InventoryServiceClient.java**

#### **│       │   ├── ProductServiceClient.java**

#### **│       │   ├── UserServiceClient.java**

#### **│       │   └── PaymentServiceClient.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── OrderEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── PaymentEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── OrderCreatedEvent.java**

#### **│       │   │   ├── OrderCancelledEvent.java**

#### **│       │   │   ├── OrderConfirmedEvent.java**

#### **│       │   │   ├── OrderShippedEvent.java**

#### **│       │   │   └── OrderDeliveredEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   ├── AsyncConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── OrderMapper.java**

#### **│       │   ├── OrderItemMapper.java**

#### **│       │   └── OrderStatusHistoryMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── order**

#### **│       │   │   ├── OrderNotFoundException.java**

#### **│       │   │   ├── OrderAlreadyCancelledException.java**

#### **│       │   │   └── InvalidOrderStatusException.java**

#### **│       │   │**

#### **│       │   └── item**

#### **│       │       ├── OrderItemNotFoundException.java**

#### **│       │       └── ProductUnavailableException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── OrderConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── OrderStatus.java**

#### **│       │   ├── PaymentStatus.java**

#### **│       │   └── DeliveryStatus.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── OrderNumberGeneratorUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           ├── DateTimeUtil.java**

#### **│           └── OrderCalculationUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **└── application-prod.yml**















## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***7. PAYMENT SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***







#### **payment-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.payment**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── PaymentController.java**

#### **│       │   ├── SSLCommerzController.java**

#### **│       │   ├── CODController.java**

#### **│       │   └── PaymentCallbackController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── PaymentService.java**

#### **│       │   │   ├── SSLCommerzService.java**

#### **│       │   │   ├── CODPaymentService.java**

#### **│       │   │   ├── PaymentVerificationService.java**

#### **│       │   │   └── PaymentCallbackService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── PaymentServiceImpl.java**

#### **│       │       ├── SSLCommerzServiceImpl.java**

#### **│       │       ├── CODPaymentServiceImpl.java**

#### **│       │       ├── PaymentVerificationServiceImpl.java**

#### **│       │       └── PaymentCallbackServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── PaymentRepository.java**

#### **│       │   └── PaymentTransactionRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── Payment.java**

#### **│       │   └── PaymentTransaction.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── CreatePaymentRequest.java**

#### **│       │   │   ├── VerifyPaymentRequest.java**

#### **│       │   │   ├── SSLCommerzPaymentRequest.java**

#### **│       │   │   └── CODPaymentRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── PaymentResponse.java**

#### **│       │       ├── PaymentTransactionResponse.java**

#### **│       │       ├── SSLCommerzResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── ApiSuccessPayload.java**

#### **│       │   ├── ApiErrorPayload.java**

#### **│       │   └── PaymentCallbackPayload.java**

#### **│       │**

#### **│       ├── client**

#### **│       │   │**

#### **│       │   └── OrderServiceClient.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── PaymentEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   └── OrderEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── PaymentInitiatedEvent.java**

#### **│       │   │   ├── PaymentSuccessEvent.java**

#### **│       │   │   ├── PaymentFailedEvent.java**

#### **│       │   │   └── PaymentCancelledEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── SSLCommerzConfig.java**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   ├── ModelMapperConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── PaymentMapper.java**

#### **│       │   └── PaymentTransactionMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── payment**

#### **│       │   │   ├── PaymentNotFoundException.java**

#### **│       │   │   ├── PaymentFailedException.java**

#### **│       │   │   └── InvalidPaymentException.java**

#### **│       │   │**

#### **│       │   └── transaction**

#### **│       │       └── TransactionNotFoundException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── PaymentConstants.java**

#### **│       │   ├── SSLCommerzConstants.java**

#### **│       │   └── KafkaTopicConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── PaymentMethod.java**

#### **│       │   ├── PaymentStatus.java**

#### **│       │   └── TransactionStatus.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── SignatureUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           └── DateTimeUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **└── application-prod.yml**













## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***8. NOTIFICATION SERVICE PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***







#### **notification-service**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.notification**

#### **│**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   ├── EmailController.java**

#### **│       │   ├── NotificationController.java**

#### **│       │   └── TemplateController.java**

#### **│       │**

#### **│       ├── service**

#### **│       │   │**

#### **│       │   ├── interfaces**

#### **│       │   │   │**

#### **│       │   │   ├── EmailService.java**

#### **│       │   │   ├── NotificationService.java**

#### **│       │   │   ├── TemplateService.java**

#### **│       │   │   └── SmsService.java**

#### **│       │   │**

#### **│       │   └── impl**

#### **│       │       │**

#### **│       │       ├── EmailServiceImpl.java**

#### **│       │       ├── NotificationServiceImpl.java**

#### **│       │       ├── TemplateServiceImpl.java**

#### **│       │       └── SmsServiceImpl.java**

#### **│       │**

#### **│       ├── repository**

#### **│       │   │**

#### **│       │   ├── NotificationRepository.java**

#### **│       │   └── EmailTemplateRepository.java**

#### **│       │**

#### **│       ├── entity**

#### **│       │   │**

#### **│       │   ├── Notification.java**

#### **│       │   └── EmailTemplate.java**

#### **│       │**

#### **│       ├── dto**

#### **│       │   │**

#### **│       │   ├── request**

#### **│       │   │   │**

#### **│       │   │   ├── SendEmailRequest.java**

#### **│       │   │   ├── SendOtpRequest.java**

#### **│       │   │   ├── SendOrderNotificationRequest.java**

#### **│       │   │   └── SendPaymentNotificationRequest.java**

#### **│       │   │**

#### **│       │   └── response**

#### **│       │       │**

#### **│       │       ├── NotificationResponse.java**

#### **│       │       ├── EmailResponse.java**

#### **│       │       ├── TemplateResponse.java**

#### **│       │       ├── ApiResponse.java**

#### **│       │       └── ErrorResponse.java**

#### **│       │**

#### **│       ├── payload**

#### **│       │   │**

#### **│       │   ├── EmailPayload.java**

#### **│       │   ├── NotificationPayload.java**

#### **│       │   └── ApiErrorPayload.java**

#### **│       │**

#### **│       ├── kafka**

#### **│       │   │**

#### **│       │   ├── producer**

#### **│       │   │   └── NotificationEventProducer.java**

#### **│       │   │**

#### **│       │   ├── consumer**

#### **│       │   │   ├── AuthEventConsumer.java**

#### **│       │   │   ├── OrderEventConsumer.java**

#### **│       │   │   └── PaymentEventConsumer.java**

#### **│       │   │**

#### **│       │   ├── event**

#### **│       │   │   ├── EmailSentEvent.java**

#### **│       │   │   ├── NotificationSentEvent.java**

#### **│       │   │   └── SmsSentEvent.java**

#### **│       │   │**

#### **│       │   └── config**

#### **│       │       └── KafkaConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── MailConfig.java**

#### **│       │   ├── ThymeleafConfig.java**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   ├── OpenApiConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── mapper**

#### **│       │   │**

#### **│       │   ├── NotificationMapper.java**

#### **│       │   └── EmailTemplateMapper.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── email**

#### **│       │   │   ├── EmailSendFailedException.java**

#### **│       │   │   └── InvalidEmailTemplateException.java**

#### **│       │   │**

#### **│       │   └── notification**

#### **│       │       ├── NotificationNotFoundException.java**

#### **│       │       └── NotificationFailedException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── EmailConstants.java**

#### **│       │   ├── NotificationConstants.java**

#### **│       │   ├── KafkaTopicConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── NotificationType.java**

#### **│       │   ├── NotificationStatus.java**

#### **│       │   └── TemplateType.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── EmailTemplateUtil.java**

#### **│           ├── ValidationUtil.java**

#### **│           └── DateTimeUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **├── application-prod.yml**

#### &#x20;   **│**

#### &#x20;   **├── templates**

#### &#x20;   **│   ├── otp-email.html**

#### &#x20;   **│   ├── welcome-email.html**

#### &#x20;   **│   ├── order-confirmation.html**

#### &#x20;   **│   ├── payment-success.html**

#### &#x20;   **│   └── forgot-password.html**

#### &#x20;   **│**

#### &#x20;   **└── static**













## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***9. API GATEWAY PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***









#### **api-gateway**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.gateway**

#### **│**

#### **│       ├── filter**

#### **│       │   │**

#### **│       │   ├── JwtAuthenticationFilter.java**

#### **│       │   ├── RequestLoggingFilter.java**

#### **│       │   ├── RateLimitingFilter.java**

#### **│       │   ├── CorrelationIdFilter.java**

#### **│       │   └── GlobalFilter.java**

#### **│       │**

#### **│       ├── security**

#### **│       │   │**

#### **│       │   ├── SecurityConfig.java**

#### **│       │   ├── JwtValidator.java**

#### **│       │   └── RouteSecurityValidator.java**

#### **│       │**

#### **│       ├── route**

#### **│       │   │**

#### **│       │   ├── AuthRouteConfig.java**

#### **│       │   ├── UserRouteConfig.java**

#### **│       │   ├── ProductRouteConfig.java**

#### **│       │   ├── InventoryRouteConfig.java**

#### **│       │   ├── CartRouteConfig.java**

#### **│       │   ├── OrderRouteConfig.java**

#### **│       │   ├── PaymentRouteConfig.java**

#### **│       │   └── NotificationRouteConfig.java**

#### **│       │**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── CorsConfig.java**

#### **│       │   ├── GatewayConfig.java**

#### **│       │   ├── SwaggerConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   ├── UnauthorizedException.java**

#### **│       │   ├── ForbiddenException.java**

#### **│       │   ├── InvalidTokenException.java**

#### **│       │   └── RateLimitExceededException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── GatewayConstants.java**

#### **│       │   ├── SecurityConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   ├── RouteType.java**

#### **│       │   └── SecurityLevel.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           ├── JwtUtil.java**

#### **│           ├── RouteUtil.java**

#### **│           └── HeaderUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **└── application-prod.yml**











## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***10. EUREKA SERVER PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***









#### **service-registry**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.registry**

#### **│**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── EurekaServerConfig.java**

#### **│       │   ├── SecurityConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   └── RegistryHealthController.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   └── RegistryException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── RegistryConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   └── ServiceStatus.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           └── HealthCheckUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── application-dev.yml**

#### &#x20;   **└── application-prod.yml**











## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***11. CONFIG SERVER PROJECT STRUCTURE***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***







#### **config-server**

#### **│**

#### **├── src**

#### **│   ├── main**

#### **│   │**

#### **│   ├── java**

#### **│   │**

#### **│   └── com.kafkamart.configserver**

#### **│**

#### **│       ├── config**

#### **│       │   │**

#### **│       │   ├── ConfigServerConfig.java**

#### **│       │   ├── SecurityConfig.java**

#### **│       │   └── BeanConfig.java**

#### **│       │**

#### **│       ├── controller**

#### **│       │   │**

#### **│       │   └── ConfigHealthController.java**

#### **│       │**

#### **│       ├── exception**

#### **│       │   │**

#### **│       │   ├── handler**

#### **│       │   │   └── GlobalExceptionHandler.java**

#### **│       │   │**

#### **│       │   └── ConfigServerException.java**

#### **│       │**

#### **│       ├── constants**

#### **│       │   │**

#### **│       │   ├── ConfigConstants.java**

#### **│       │   └── ErrorMessageConstants.java**

#### **│       │**

#### **│       ├── enums**

#### **│       │   │**

#### **│       │   └── EnvironmentType.java**

#### **│       │**

#### **│       └── util**

#### **│           │**

#### **│           └── ConfigValidationUtil.java**

#### **│**

#### **└── resources**

#### &#x20;   **│**

#### &#x20;   **├── application.yml**

#### &#x20;   **├── bootstrap.yml**

#### &#x20;   **└── banner.txt**













## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

## ***12. Config Repository Structure (Git)***

## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***









#### **kafka-mart-config-repo**

#### **│**

#### **├── auth-service.yml**

#### **├── user-service.yml**

#### **├── product-service.yml**

#### **├── inventory-service.yml**

#### **├── cart-service.yml**

#### **├── order-service.yml**

#### **├── payment-service.yml**

#### **├── notification-service.yml**

#### **├── api-gateway.yml**

#### **│**

#### **├── auth-service-dev.yml**

#### **├── auth-service-prod.yml**

#### **│**

#### **├── user-service-dev.yml**

#### **├── user-service-prod.yml**

#### **│**

#### **└── common.yml**











## ***\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~\~***

# 

