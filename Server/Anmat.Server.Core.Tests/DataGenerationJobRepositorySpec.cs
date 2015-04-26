using System;
using Anmat.Server.Core.Data;
using Xunit;
using System.Linq;

namespace Anmat.Server.Core.Tests
{
	public class DataGenerationJobRepositorySpec : RepositorySpec<DataGenerationJob>
	{
		[Fact]
        public void when_creating_job_then_succeeds()
        {
            var job = new DataGenerationJob
            {
				Status = DataGenerationJobStatus.InProgress,
				Version = new Random().Next(),
				Message = "Job In Progress",
				DateStarted = DateTime.Now.AddDays(-1).ToString()
            };
			var jobId = job.Id;

            this.testRepository.Create(job);

            var createdJob = this.testRepository.Get(j => j.Id == jobId);

            Assert.NotNull(createdJob);
            Assert.Equal(job.Id, createdJob.Id);
            Assert.Equal(job.Status, createdJob.Status);
			Assert.Equal(job.Version, createdJob.Version);
			Assert.Equal(job.Message, createdJob.Message);

			this.testRepository.Delete (createdJob);
        }

		[Fact]
        public void when_updating_job_then_succeeds()
        {
            var job = new DataGenerationJob
            {
				Status = DataGenerationJobStatus.InProgress,
				Version = new Random().Next(),
				Message = "Job In Progress",
				DateStarted = DateTime.Now.AddDays(-1).ToString()
            };
			var jobId = job.Id;

            this.testRepository.Create(job);

            var createdJob = this.testRepository.Get(j => j.Id == jobId);
			var errorMessage = "The Test Job failed";

			createdJob.Status = DataGenerationJobStatus.Failed;
			createdJob.Message = errorMessage;
			createdJob.DateFinished = DateTime.Now.ToString();

			this.testRepository.Update (createdJob);

			var updatedJob = this.testRepository.Get(j => j.Id == jobId);

            Assert.NotNull(updatedJob);
            Assert.Equal(job.Id, updatedJob.Id);
            Assert.Equal(DataGenerationJobStatus.Failed, updatedJob.Status);
			Assert.Equal(job.Version, updatedJob.Version);
			Assert.Equal(errorMessage, updatedJob.Message);

			this.testRepository.Delete (updatedJob);
        }

		[Fact]
        public void when_getting_jobs_with_predicate_then_succeeds()
        {
            var job1 = new DataGenerationJob
            {
				Status = DataGenerationJobStatus.InProgress,
				Version = 2,
				Message = "Job In Progress",
				DateStarted = DateTime.Now.AddDays(-1).ToString()
            };
			var job1Id = job1.Id;

			var job2 = new DataGenerationJob
            {
				Status = DataGenerationJobStatus.Completed,
				Version = 3,
				Message = "Job Completed",
				DateStarted = DateTime.Now.AddDays(-1).ToString()
            };
			var job2Id = job1.Id;

			var job3 = new DataGenerationJob
            {
				Status = DataGenerationJobStatus.Completed,
				Version = 4,
				Message = "Job Completed",
				DateStarted = DateTime.Now.AddDays(-1).ToString()
            };
			var job3Id = job1.Id;

			var job4 = new DataGenerationJob
            {
				Status = DataGenerationJobStatus.Failed,
				Version = 5,
				Message = "Job Failed",
				DateStarted = DateTime.Now.AddDays(-1).ToString()
            };
			var job4Id = job1.Id;

            this.testRepository.Create(job1);
			this.testRepository.Create(job2);
			this.testRepository.Create(job3);
			this.testRepository.Create(job4);

			var completedJobs = this.testRepository.GetAll (j => j.Status == DataGenerationJobStatus.Completed);
			var failedJobs = this.testRepository.GetAll (j => j.Status == DataGenerationJobStatus.Failed);
			var inProgressJobs = this.testRepository.GetAll (j => j.Status == DataGenerationJobStatus.InProgress);
			var versio5Job =  this.testRepository.Get (j => j.Version == 5);

			Assert.Equal (2, completedJobs.Count ());
			Assert.Equal (1, failedJobs.Count ());
			Assert.Equal (1, inProgressJobs.Count ());
			Assert.NotNull (versio5Job);

			this.testRepository.Delete (job1);
			this.testRepository.Delete (job2);
			this.testRepository.Delete (job3);
			this.testRepository.Delete (job4);
        }
	}
}
