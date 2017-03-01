
IF EXISTS(SELECT * FROM sys.databases WHERE name='go-sql-database')
	DROP DATABASE [go-sql-database]
GO

CREATE DATABASE [go-sql-database]
GO

ALTER DATABASE [go-sql-database] SET COMPATIBILITY_LEVEL = 130
GO

USE [go-sql-database]  
GO

/*==============================================================*/
/* Stored Procedure to insert Deal: EXEC insertDeal @City = 'Calgary', @AddressLine = 'Rua Heitor Ernesto'                               */
/* EXEC insertDeal @VenueSku = '555', @VenueName = 'Carrefour', @VenueAddress = 'Rod. D. Pedro I, Km 127', @VenueCity = 'Campinas', @VenueState = 'SP', @VenueCountry = 'Brasil', @VenueLatitude = -22.852876134654547, @VenueLongitude = -47.02767489301763, @CustomerFirstName = 'Patrick', @CustomerLastName = 'Tedeschi', @CustomerEmail = 'patrick@tedeschi.com.br', @CustomerCountry =  'Brasil', @ProductName = 'Premium Care', @ProductBrand = 'Pampers', @ProductModel = '', @ProductSize = 'M', @ProductQuantity = 48, @DealPrice = 51.89, @DealType = 'Consumer', @DealComment = '' */
/*==============================================================*/


CREATE PROCEDURE insertDeal @VenueSku nvarchar(40) = NULL, @VenueName nvarchar(40) = NULL, @VenueAddress nvarchar(40) = NULL, @VenueCity nvarchar(40) = NULL, @VenueState nvarchar(40) = NULL, @VenueCountry nvarchar(40) = NULL, @VenueLatitude float, @VenueLongitude float, @CustomerFirstName nvarchar(40) = NULL, @CustomerLastName nvarchar(40) = NULL, @CustomerEmail nvarchar(40) = NULL, @CustomerCountry nvarchar(40) = NULL, @ProductSku nvarchar(40) = NULL, @ProductName nvarchar(50) = NULL, @ProductBrand nvarchar(50) = NULL, @ProductModel nvarchar(50) = NULL, @ProductSize nvarchar(50) = NULL, @ProductQuantity int, @DealPrice decimal(12,2), @DealType nvarchar(50) = NULL, @DealComment nvarchar(50) = NULL
AS
BEGIN
	DECLARE @ID_Deal_Venue int = 0
	DECLARE @ID_Deal_Customer int= 0
	DECLARE @ID_Deal_Product int = 0

	BEGIN TRANSACTION 
		SELECT @ID_Deal_Venue = Id FROM Venue WHERE Sku = @VenueSku
		
		IF @ID_Deal_Venue = 0
		BEGIN
			INSERT INTO Venue (Sku, Name, Address, City, State, Country, Latitude, Longitude) VALUES (@VenueSku, @VenueName, @VenueAddress, @VenueCity, @VenueState, @VenueCountry, @VenueLatitude, @VenueLongitude);
			SET @ID_Deal_Venue = SCOPE_IDENTITY()    
			
			IF @@ERROR <> 0
				BEGIN      
					ROLLBACK     
					RETURN 
				END   
		END

		INSERT INTO Customer (FirstName, LastName, Email, Country) VALUES (@CustomerFirstName, @CustomerLastName, @CustomerEmail, @CustomerCountry);
		SET @ID_Deal_Customer = SCOPE_IDENTITY()    
		
		IF @@ERROR <> 0
			BEGIN
				ROLLBACK
				RETURN
			END
			
		SELECT @ID_Deal_Product = Id FROM Product WHERE Sku = @ProductSku
		
		IF @ID_Deal_Product = 0
		BEGIN
			INSERT INTO Product (Sku, ProductName, Brand, Model, Size, Quantity) VALUES (@ProductSku, @ProductName, @ProductBrand, @ProductModel, @ProductSize,  @ProductQuantity);
			SET @ID_Deal_Product = SCOPE_IDENTITY()
			
			IF @@ERROR <> 0
				BEGIN
					ROLLBACK
					RETURN
				END
		END
			
		INSERT INTO Deal (CustomerId, VenueId, ProductId, Price, Type, Comment) VALUES (@ID_Deal_Customer, @ID_Deal_Venue, @ID_Deal_Product, @DealPrice, @DealType, @DealComment);
		
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK
				RETURN
			END
		
	COMMIT
END
GO

/*==============================================================*/
/* Table: Venues                                                */
/*==============================================================*/
CREATE TABLE Venue (
	Id				int					IDENTITY(1,1),
	Sku				nvarchar(40) 		NOT NULL,
	Name			nvarchar(max) 		NOT NULL,
	Address			nvarchar(max) 		NOT NULL,
	City			nvarchar(40) 		NOT NULL,
	State			nvarchar(2) 		NOT NULL,
	Country			nvarchar(40) 		NOT NULL,
	Latitude		float 				NOT NULL,
	Longitude		float 				NOT NULL,
	constraint PK_VENUE primary key (Id)
)
GO

/*==============================================================*/
/* Table: Customer                                              */
/*==============================================================*/
CREATE TABLE Customer (
   Id                   int                  IDENTITY(1,1),
   FirstName            nvarchar(40)         NOT NULL,
   LastName             nvarchar(40)         NOT NULL,
   Email                nvarchar(40)         NULL,
   Country              nvarchar(40)         NULL,
   constraint PK_CUSTOMER primary key (Id)
)
GO

/*==============================================================*/
/* Table: Product                                               */
/*==============================================================*/
CREATE TABLE Product (
   Id                   int                  IDENTITY(1,1),
   Sku					nvarchar(40) 		 NOT NULL,
   ProductName          nvarchar(50)         NOT NULL,
   Brand				nvarchar(50)         NOT NULL,
   Model				nvarchar(50)         NOT NULL,
   Size					nvarchar(50)         NOT NULL,
   Quantity				int			         NOT NULL,
   IsDiscontinued       bit                  NOT NULL DEFAULT 0,
   constraint PK_PRODUCT primary key (Id)
)
GO

/*==============================================================*/
/* Table: Deal                                                  */
/*==============================================================*/
create table Deal (
   Id                   int                  IDENTITY(1,1),
   CustomerId           int                  NOT NULL,
   VenueId            	int                  NOT NULL,
   ProductId            int                  NOT NULL,
   Price            	decimal(12,2)        NOT NULL DEFAULT 0,
   Type					nvarchar(50)         NOT NULL,
   Comment				nvarchar(50)         NULL,
   DateTime 			datetime 			 NOT NULL DEFAULT GETDATE()
   CONSTRAINT FK_Deal_Customer FOREIGN KEY (CustomerId) REFERENCES Customer (Id),
   CONSTRAINT FK_Deal_Venue FOREIGN KEY (VenueId) REFERENCES Venue (Id),
   CONSTRAINT FK_Deal_Product FOREIGN KEY (ProductId) REFERENCES Product (Id)
	    ON DELETE CASCADE    
		ON UPDATE CASCADE  
)
GO

/*==============================================================*/
/* Transact-SQL Scalar Function: Calculate geolocation distance */
/*==============================================================*/
CREATE FUNCTION calculatedistance(@latStart float, @lngStart float, @latDest float, @lngDest float) 
returns float AS 
BEGIN 
  /** For Geography the default of SRID is 4326 to indicate that the data conforms to the WGS 84 standard for curved earth data **/
  DECLARE @SRID int = 4326 
  
  /** Creating GEOGRAPHY data based on float **/
  DECLARE @LocStart GEOGRAPHY = GEOGRAPHY::Point(@latStart, @lngStart, @SRID) 
  DECLARE @LocDest GEOGRAPHY = GEOGRAPHY::Point(@latDest, @lngDest, @SRID) 
  
  /** Returing distance **/
  RETURN (@LocStart.STDistance(@LocDest)/1000) 
END
