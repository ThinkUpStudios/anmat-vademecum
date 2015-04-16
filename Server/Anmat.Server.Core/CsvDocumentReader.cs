using System;
using System.Collections.Generic;
using System.Dynamic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{


    class CsvDocumentReader : IDocumentReader
    {
        public Document Read(string path, DocumentMetadata metadata)
        {
            var lines = File.ReadAllLines(path);

            var reader = new StreamReader(path);

            var line = default(string);

            while ((line = reader.ReadLine()) != null)
            {

            }

            reader.Close();

            dynamic test = new ExpandoObject();
            var i = new Random().Next();

            if (i > 10)
            {
                test.Names = new List<string>();
                test.Names.Add("hola");
                test.Names.Add("chau");

                foreach (var name in test.Names)
                {

                }
            }
            else
            {
                test = 3;
            }

            //TODO: Read from CSV
            var document = new Document();
            return document;
        }
    }
}
