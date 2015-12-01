using System.Collections.Generic;
using System.Linq;

namespace Anmat.Server.Core.Data
{
	public class DocumentMetadataInitializer : IInitializer<DocumentMetadata>
	{
		private readonly AnmatConfiguration configuration;

		public DocumentMetadataInitializer (AnmatConfiguration configuration)
		{
			this.configuration = configuration;
		}

		public void Initialize (IRepository<DocumentMetadata> repository, bool force = false)
		{
			if (!force && repository.GetAll ().Any ()) {
				return;
			}

			if (force) {
				this.CleanExistingData (repository);
			}

			this.InitializeMedicinesMetadata (repository);
			this.InitializeActiveComponentsMetadata (repository);
			this.InitializeGroupsMetadata (repository);
			this.InitializePregnancyComponentsMetadata (repository);
		}

		private void CleanExistingData (IRepository<DocumentMetadata> repository)
		{
			(repository as SqlRepository<DocumentMetadata>).DeleteAll ();
		}

		private void InitializeMedicinesMetadata(IRepository<DocumentMetadata> repository)
		{
			var columns = new List<DocumentColumnMetadata>();

            columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 0,
                Name = "id",
                Type = typeof(string).ToString(),
                IsNullable = true, 
                UpperCase = false
            });
            columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 1,
                Name = "certificado",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
            columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 2,
                Name = "cuit",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 3,
                Name = "laboratorio",
				Type = typeof(string).ToString(),
                IsNullable = true,
                RemovableAccents = true,
                UpperCase = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 4,
                Name = "gtin",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 5,
                Name = "troquel",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 6,
                Name = "comercial",
				Type = typeof(string).ToString(),
                IsNullable = true,
                RemovableAccents = true,
                UpperCase = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 7,
                Name = "forma",
				Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 8,
                Name = "generico",
				Type = typeof(string).ToString(),
                IsNullable = true,
                RemovableAccents = true,
                UpperCase = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 9,
                Name = "pais",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 10,
                Name = "expendio",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 11,
                Name = "trazabilidad",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 12,
                Name = "presentacion",
				Type = typeof(string).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 13,
                Name = "precio",
				Type = typeof(double).ToString(),
                IsNullable = true,
                UpperCase = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 14,
                Name = "es_hospitalario",
				Type = typeof(int).ToString(),
                IsNullable = true,
                UpperCase = false
            });
            columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 15,
                Name = "es_remediar",
                Type = typeof(int).ToString(),
                IsNullable = true,
                UpperCase = false
            });

            var metadata = new DocumentMetadata
            {
                DocumentName = this.configuration.TargetMedicinesTableName,
				HasHeader = true,
                Columns = columns,
            };

            repository.Create(metadata);
		}

		private void InitializeActiveComponentsMetadata(IRepository<DocumentMetadata> repository)
		{
			var columns = new List<DocumentColumnMetadata>();

            columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 0,
                Name = "ifa",
                Type = typeof(string).ToString(),
                IsNullable = false
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 1,
                Name = "clasificacion_terapeutica",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 2,
                Name = "farma_descripcion",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 3,
                Name = "farma_mecanismo_accion",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 4,
                Name = "farma_farmacocinetica",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 5,
                Name = "iyp_indicaciones",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 6,
                Name = "iyp_posologia",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 7,
                Name = "contraindicaciones",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add(new DocumentColumnMetadata()
            {
                ColumnNumber = 8,
                Name = "interacciones",
                Type = typeof(string).ToString(),
                IsNullable = true
            });
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 9,
				Name = "reacciones_adversas",
				Type = typeof (string).ToString (),
				IsNullable = true
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 10,
				Name = "referencias",
				Type = typeof (string).ToString (),
				IsNullable = true
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 11,
				Name = "informacion_adicional",
				Type = typeof (string).ToString (),
				IsNullable = true
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 12,
				Name = "bibliografia",
				Type = typeof (string).ToString (),
				IsNullable = true
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 13,
				Name = "otros_nombres",
				Type = typeof (string).ToString (),
				IsNullable = true
			});

			var metadata = new DocumentMetadata
            {
                DocumentName = this.configuration.TargetActiveComponentsTableName,
				HasHeader = true,
                Columns = columns
            };

            repository.Create(metadata);
		}

		private void InitializeGroupsMetadata (IRepository<DocumentMetadata> repository)
		{
			var columns = new List<DocumentColumnMetadata>();

			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 0,
				Name = "id",
				Type = typeof (string).ToString (),
				IsNullable = true,
				UpperCase = false
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 1,
				Name = "descripcion",
				Type = typeof (string).ToString (),
				IsNullable = true,
				UpperCase = false
			});

			var metadata = new DocumentMetadata
			{
				DocumentName = this.configuration.TargetGroupsTableName,
				HasHeader = true,
				Columns = columns,
			};

			repository.Create (metadata);
		}

		private void InitializePregnancyComponentsMetadata (IRepository<DocumentMetadata> repository)
		{
			var columns = new List<DocumentColumnMetadata>();

			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 0,
				Name = "grupo_id",
				Type = typeof (string).ToString (),
				IsNullable = true,
				UpperCase = false
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 1,
				Name = "principio",
				Type = typeof (string).ToString (),
				IsNullable = true,
				UpperCase = false
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 2,
				Name = "medicamentos",
				Type = typeof (string).ToString (),
				IsNullable = true,
				UpperCase = false
			});
			columns.Add (new DocumentColumnMetadata () {
				ColumnNumber = 3,
				Name = "clasificacion",
				Type = typeof (string).ToString (),
				IsNullable = true,
				UpperCase = false
			});

			var metadata = new DocumentMetadata
			{
				DocumentName = this.configuration.TargetPregnancyComponentsTableName,
				HasHeader = true,
				Columns = columns,
			};

			repository.Create (metadata);
		}
	}
}
