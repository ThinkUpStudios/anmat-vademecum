using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Dynamic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    public class CsvDocumentReader : IDocumentReader
    {
        public Document Read(string path, DocumentMetadata metadata)
        {
            if (!File.Exists(path)) {
                throw new ArgumentException(string.Format("El archivo {0} no existe",path));
            }

            if (Path.GetExtension(path) != ".csv")
            {
                throw new ArgumentException(string.Format("La extension {0} no soportada",Path.GetExtension(path)));
            }
            var document = new Document();
            var reader = new StreamReader(path);
            var line = default(string);
            var lineIndex = 0;
            if (metadata.HasHeader) 
            { 
                reader.ReadLine();
                lineIndex++;
            }
            
            while ((line = reader.ReadLine()) != null)
            {
                
                var lineParts = line.Split(';');
                lineIndex++;
                if (lineParts.Length != metadata.Columns.Count())
                {
                    throw new DocumentFormatException(string.Format("Se esperaban {0} columnas separadas por ';'. Se encontraron {1} en la linea {2}.", metadata.Columns.Count(),lineParts.Length, lineIndex));
                }
                var row = new Row();
                for (var i = 0; i < lineParts.Length; i++ )
                {
                    var columnMetadata = metadata.Columns.FirstOrDefault(x => x.ColumnNumber == i);

                    if (columnMetadata == null)
                    {
                        throw new FieldFormatException(string.Format("La columna {0} no esta definida en la metadata", i));
                    }

                    var value = lineParts[i];

                    if(!columnMetadata.IsNullable && (string.IsNullOrWhiteSpace(value) || value.ToLower() == "null")) {
                        throw new FieldFormatException(string.Format("El campo {0} tiene valor nulo.", columnMetadata.Name));
                    }
                    
                    try { 
                        TypeDescriptor.GetConverter(columnMetadata.Type).ConvertFromString(value);
                    }
                    catch (Exception e)
                    {
                        throw new FieldFormatException(string.Format("El campo {0} no tiene el formato esperado.", columnMetadata.Name), e);
                    }
                    
                    
                    row.Add(value.ToLower() == "null" ? string.Empty : value, columnMetadata.Type);    
                }
                                
                document.Add(row);
            }

            reader.Close();

            return document;
        }
    }
}
