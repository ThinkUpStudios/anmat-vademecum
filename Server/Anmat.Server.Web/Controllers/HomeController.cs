using System.Web.Mvc;
using Anmat.Server.Web.Filters;

namespace Anmat.Server.Web.Controllers
{
	[Authorize]
	[InitializeSimpleMembership]
	public class HomeController : Controller
	{
		public ActionResult Index ()
		{
			return View ();
		}

		public ActionResult DownloadTemplates ()
		{
			var filePath = HttpContext.Server.MapPath("~/bin/VNMTemplates.zip");
			var fileContent = System.IO.File.ReadAllBytes (filePath);

			return File (fileContent, "application/zip", System.IO.Path.GetFileName (filePath));
		}

		//public ActionResult About()
		//{
		//    ViewBag.Message = "Your app description page.";

		//    return View();
		//}

		//public ActionResult Contact()
		//{
		//    ViewBag.Message = "Your contact page.";

		//    return View();
		//}
	}
}
