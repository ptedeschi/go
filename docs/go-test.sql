SELECT
deal.Id,
deal.Type,
deal.Price,
deal.Price/product.Quantity AS UnityPrice,
deal.Comment,
deal.DateTime,
customer.FirstName AS [Customer.FirstName],
customer.LastName AS [Customer.LastName],
venue.Name AS [Venue.Name],
venue.Address AS [Venue.Address],
venue.City AS [Venue.City],
venue.State	AS [Venue.State],
venue.Country AS [Venue.Country],
venue.Latitude AS [Venue.Latitude],
venue.Longitude AS [Venue.Longitude],
dbo.CalculateDistance(-22.8498085, -47.085443, venue.Latitude, venue.Longitude) AS [Venue.Distance],
product.ProductName AS [Product.Name],
product.Brand AS [Product.Brand],
product.Size AS [Product.Size],
product.Quantity AS [Product.Quantity]
FROM Deal
INNER JOIN Customer ON deal.CustomerId=customer.Id
Inner JOIN Venue ON deal.VenueId=venue.Id
Inner JOIN Product ON deal.ProductId=product.Id
FOR JSON PATH, ROOT('Deals')
