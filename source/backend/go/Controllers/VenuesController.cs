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

        // GET api/venues
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/venues/byCoordinate?lat=-22.8498085&lng=-47.085443
        [HttpGet("byCoordinate")]
        public async Task Get(string lat, string lng)
        {
            string statament = String.Format("SELECT deal.Id, deal.Type, deal.Price, deal.Price/product.Quantity AS UnityPrice, deal.Comment, deal.DateTime, customer.FirstName AS [Customer.FirstName], customer.LastName AS [Customer.LastName], venue.Name AS [Venue.Name], venue.Address AS [Venue.Address], venue.City AS [Venue.City], venue.State	AS [Venue.State], venue.Country AS [Venue.Country], venue.Latitude AS [Venue.Latitude], venue.Longitude AS [Venue.Longitude], dbo.CalculateDistance({0}, {1}, venue.Latitude, venue.Longitude) AS [Venue.Distance], product.ProductName AS [Product.Name], product.Brand AS [Product.Brand], product.Size AS [Product.Size], product.Quantity AS [Product.Quantity] FROM Deal INNER JOIN Customer ON deal.CustomerId=customer.Id Inner JOIN Venue ON deal.VenueId=venue.Id Inner JOIN Product ON deal.ProductId=product.Id FOR JSON PATH, ROOT('Deals')", lat, lng);

            await SqlPipe.Stream(statament, Response.Body, "[]");
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
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
