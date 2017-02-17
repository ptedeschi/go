/*
USE master
IF EXISTS(select * from sys.databases where name='go-sql-database')
DROP DATABASE go-sql-database
GO

CREATE DATABASE [go-sql-database]
GO

ALTER DATABASE [go-sql-database] SET COMPATIBILITY_LEVEL = 130
GO
*/

/*==============================================================*/
/* Table: Venues                                                */
/*==============================================================*/
CREATE TABLE Venue (
	Id				int					IDENTITY(1,1),
	FoursquareId	nvarchar(40) 		NOT NULL,
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
   DateTime				datetime			 NOT NULL,
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
