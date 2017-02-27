using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Belgrade.SqlClient;
using Microsoft.AspNetCore.Mvc;

namespace go.Controllers
{
    [Route("api/[controller]")]
    public class VenuesController : Controller
    {
        private readonly IQueryPipe SqlPipe;
        private readonly ICommand SqlCommand;

        public VenuesController(ICommand sqlCommand, IQueryPipe sqlPipe)
        {
            this.SqlCommand = sqlCommand;
            this.SqlPipe = sqlPipe;
        }

        // GET api/venues/byCoordinate?lat=-22.8498085&lng=-47.085443
        [HttpGet("byCoordinate")]
        public async Task Get(string lat, string lng)
        {
            string statament = String.Format("SELECT deal.Id, deal.Type, deal.Price, deal.Price/product.Quantity AS UnityPrice, deal.Comment, deal.DateTime, customer.FirstName AS [Customer.FirstName], customer.LastName AS [Customer.LastName], venue.Name AS [Venue.Name], venue.Address AS [Venue.Address], venue.City AS [Venue.City], venue.State	AS [Venue.State], venue.Country AS [Venue.Country], venue.Latitude AS [Venue.Latitude], venue.Longitude AS [Venue.Longitude], dbo.CalculateDistance({0}, {1}, venue.Latitude, venue.Longitude) AS [Venue.Distance], product.ProductName AS [Product.Name], product.Brand AS [Product.Brand], product.Size AS [Product.Size], product.Quantity AS [Product.Quantity] FROM Deal INNER JOIN Customer ON deal.CustomerId=customer.Id Inner JOIN Venue ON deal.VenueId=venue.Id Inner JOIN Product ON deal.ProductId=product.Id ORDER BY UnityPrice ASC, [Venue.Distance] ASC FOR JSON PATH, ROOT('Deals')", lat, lng);

            await SqlPipe.Stream(statament, Response.Body, "[]");
        }

        // GET api/venues/insert?VenueFoursquareId=555&VenueName=Carrefour&VenueAddress=Rod. D. Pedro I, Km 127&VenueCity=Campinas&VenueState=SP&VenueCountry=Brasil&VenueLatitude=-22.852876134654547&VenueLongitude=-47.02767489301763&CustomerFirstName=Patrick&CustomerLastName=Tedeschi&CustomerEmail=patrick@tedeschi.com.br&CustomerCountry=Brasil&ProductName=POM POM&ProductBrand=XX&ProductModel=Fofinha&ProductSize=G&ProductQuantity=128&DealPrice=60.&DealType=Consumer&DealComment=No comment
        [HttpGet("insert")]
        public async Task Get(string VenueFoursquareId, string VenueName, string VenueAddress, string VenueCity, string VenueState, string VenueCountry, string VenueLatitude, string VenueLongitude, string CustomerFirstName, string CustomerLastName, string CustomerEmail, string CustomerCountry, string ProductName, string ProductBrand, string ProductModel, string ProductSize, string ProductQuantity, string DealPrice, string DealType, string DealComment)
        {
            string statament = String.Format("EXEC insertDeal @VenueFoursquareId = '{0}', @VenueName = '{1}', @VenueAddress = '{2}', @VenueCity = '{3}', @VenueState = '{4}', @VenueCountry = '{5}', @VenueLatitude = '{6}', @VenueLongitude = '{7}', @CustomerFirstName = '{8}', @CustomerLastName = '{9}', @CustomerEmail = '{10}', @CustomerCountry = '{11}', @ProductName = '{12}', @ProductBrand = '{13}', @ProductModel = '{14}', @ProductSize = '{15}', @ProductQuantity = '{16}', @DealPrice = {17}, @DealType = {18}, @DealComment = '{19}'", VenueFoursquareId, VenueName, VenueAddress, VenueCity, VenueState, VenueCountry, VenueLatitude, VenueLongitude, CustomerFirstName, CustomerLastName, CustomerEmail, CustomerCountry, ProductName, ProductBrand, ProductModel, ProductSize, ProductQuantity, DealPrice, DealType, DealComment);

            await SqlPipe.Stream(statament, Response.Body, "[]");
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
