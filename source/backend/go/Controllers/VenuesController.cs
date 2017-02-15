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
            string statament = String.Format("SELECT [id], [name], [address], [city], [state], [country], [lat], [lng], dbo.CalculateDistance({0},{1}, [lat], [lng])[distance] FROM [dbo].[venues] ORDER BY [distance] FOR JSON PATH", lat, lng);

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
