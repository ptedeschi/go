/*==============================================================*/
/* Table: Venue	                                                */
/*==============================================================*/
SET IDENTITY_INSERT Venue ON
INSERT INTO Venue (Id, FoursquareId, Name, Address, City, State, Country, Latitude, Longitude) VALUES (1, '', 'Carrefour', 'Rod. D. Pedro I, Km 127', 'Campinas', 'SP', 'Brasil', -22.852876134654547, -47.02767489301763);
INSERT INTO Venue (Id, FoursquareId, Name, Address, City, State, Country, Latitude, Longitude) VALUES (2, '', 'Makro Atacadista', 'Rod. Dom Pedro I, Km 139', 'Campinas', 'SP', 'Brasil', -22.847792643535193, -47.088747800217334);
INSERT INTO Venue (Id, FoursquareId, Name, Address, City, State, Country, Latitude, Longitude) VALUES (3, '', 'Alô Bebê', 'Av. José de Souza Campos, 1445', 'Campinas', 'SP', 'Brasil', -22.89319195681192, -47.047162952385);
INSERT INTO Venue (Id, FoursquareId, Name, Address, City, State, Country, Latitude, Longitude) VALUES (4, '', 'Sam''s Club', 'Rod. D. Pedro I, km 132', 'Campinas', 'SP', 'Brasil', -22.861005801518594, -47.02353885476915);
INSERT INTO Venue (Id, FoursquareId, Name, Address, City, State, Country, Latitude, Longitude) VALUES (5, '', 'Farmácia São Paulo', 'Praça 14 Bis', 'São Paulo', 'SP', 'Brasil', -23.553045764428443, -46.647328338206094);
SET IDENTITY_INSERT Venue OFF
GO

/*==============================================================*/
/* Table: Customer                                              */
/*==============================================================*/
SET IDENTITY_INSERT Customer ON
INSERT INTO Customer (Id, FirstName, LastName, Email, Country) VALUES (1, 'Patrick', 'Tedeschi', 'patrick@tedeschi.com.br', 'Brasil');
INSERT INTO Customer (Id, FirstName, LastName, Email, Country) VALUES (2, 'Ticiana', 'Simmelink', 'ticiana.simmelink@gmail.com', 'Brasil');
SET IDENTITY_INSERT Customer OFF
GO

/*==============================================================*/
/* Table: Product                                              */
/*==============================================================*/
SET IDENTITY_INSERT Product ON
INSERT INTO Product (Id, ProductName, Brand, Model, Size, Quantity, IsDiscontinued) VALUES (1, 'Premium Care', 'Pampers', '', 'M', '48', 0); /* 51,89 */
INSERT INTO Product (Id, ProductName, Brand, Model, Size, Quantity, IsDiscontinued) VALUES (2, 'Turma da Mônica Tripla Proteção', 'Huggies', '', 'XG', '24', 0); /* 25,90 */
INSERT INTO Product (Id, ProductName, Brand, Model, Size, Quantity, IsDiscontinued) VALUES (3, '', 'Capricho Bummis', '', 'EG', '16', 0); /* 19,50 */
INSERT INTO Product (Id, ProductName, Brand, Model, Size, Quantity, IsDiscontinued) VALUES (4, 'Premium Care', 'Pampers', '', 'XG', '32', 0); /* 50,85 */
SET IDENTITY_INSERT Product OFF
GO

/*==============================================================*/
/* Table: Deal                                              */
/*==============================================================*/
SET IDENTITY_INSERT Deal ON
INSERT INTO Deal (Id, CustomerId, VenueId, ProductId, Price, Type, Comment, DateTime) VALUES (1, 2, 4, 1, 51.89, 'Consumer', '', '2017-02-11T14:25:10');
SET IDENTITY_INSERT Deal OFF
GO