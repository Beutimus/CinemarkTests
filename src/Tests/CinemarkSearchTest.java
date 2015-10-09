package Tests;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.*;

import Movie.MovieInfo;
import PageObjects.CinemarkSearchFeature;
import PageObjects.CinemarkTheatreList;
import PageObjects.MovieDayList;
import TestHelper.MovieListWriter;

import org.testng.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import jxl.*;

public class CinemarkSearchTest {
	
	//private RemoteWebDriver driver;
	List<RemoteWebDriver> activeDrivers;
	
	private final String cinemarkHomePageURL = "http://www.cinemark.com/";
	
	@BeforeClass
	public void setUp() throws Exception {
		activeDrivers = new ArrayList<RemoteWebDriver>();
		//DesiredCapabilities capability = DesiredCapabilities.firefox();
		
		//driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
		
		//driver = new FirefoxDriver();		
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		// Clean up any remaining drivers
		for(RemoteWebDriver driver : activeDrivers)
		{
			if (driver != null)
			{
				driver.quit();
			}
		}
	}
	
	@DataProvider(name = "searchTerms", parallel = true)
	public static Object[][] searchTerms() {
		return new Object[][] {{"84124"}, {"California"}, {"Idaho"}, {"Layton, UT"}, {"Washington, DC"}};
	}
	
	@DataProvider(name = "brokenSearchTerms")
	public static Object[][] brokenSearchTerms() {
		return new Object[][] {{"St. George, Utah"}, {"Washington DC"}};
	}
	
	public RemoteWebDriver getRemoteDriver()
	{
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		
		try {
			RemoteWebDriver newDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
			activeDrivers.add(newDriver);
			return newDriver;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	public void searchTest() {
		RemoteWebDriver driver = getRemoteDriver();
		// Load test parameters from the Excel sheet
		Workbook test = null;
		
		try {
			test = Workbook.getWorkbook(new File("./test.xls"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertEquals(false, true, "Unable to open file");
		}		
		
		Sheet sheet = test.getSheet(0);
		
		Cell searchTermCell = sheet.getCell(0, 0);
		
		String searchTerm = searchTermCell.getContents();
		
		// Begin the search
		driver.get(cinemarkHomePageURL);
		CinemarkSearchFeature feature = new CinemarkSearchFeature(driver);
		
		feature = new CinemarkSearchFeature(driver);
		CinemarkTheatreList theatres = feature.search(searchTerm);
		
		// Verify we are on the right page
		String currentTitle = driver.getTitle();
		
		Assert.assertEquals(currentTitle, theatres.getTitle(), "This isn't the right page title");
		
		// Get list of theatres
		List<String> theatreList = theatres.getTheatres();
		
		// Pick a threatre
		theatres.gotoTheatre(theatreList.get(0));
		
		// Get a list of all of the movies
		MovieDayList dayList = new MovieDayList(driver);
		
		List<MovieInfo> list = dayList.getAllMoviesInAWeek();
		
		// Write out the list
		//MovieListWriter.writeMovies(list);
		MovieListWriter.newWrite(list);
	}	
	
	@Test
	@Parameters("searchTerm")
	public void paramXMLTest(String searchTerm) {
		RemoteWebDriver driver = getRemoteDriver();
		
		// Begin the search
		driver.get(cinemarkHomePageURL);
		CinemarkSearchFeature feature = new CinemarkSearchFeature(driver);
		
		feature = new CinemarkSearchFeature(driver);
		CinemarkTheatreList theatres = feature.search(searchTerm);
		
		// Verify we are on the right page
		String currentTitle = driver.getTitle();
		
		Assert.assertEquals(currentTitle, theatres.getTitle(), "This isn't the right page title");
		
		// Get list of theatres
		List<String> theatreList = theatres.getTheatres();
		
		// Pick a threatre
		theatres.gotoTheatre(theatreList.get(0));
		
		// Get a list of all of the movies
		MovieDayList dayList = new MovieDayList(driver);
		
		List<MovieInfo> list = dayList.getAllMoviesInAWeek();
		
		// Write out the list
		//MovieListWriter.writeMovies(list);
		MovieListWriter.newWrite(list);
	}
	
	@Test(dataProvider = "searchTerms")
	public void paramDataProverTest(String searchTerm) {
		RemoteWebDriver driver = getRemoteDriver();
		
		// Begin the search
		driver.get(cinemarkHomePageURL);
		CinemarkSearchFeature feature = new CinemarkSearchFeature(driver);
		
		feature = new CinemarkSearchFeature(driver);
		CinemarkTheatreList theatres = feature.search(searchTerm);
		
		// Verify we are on the right page
		String currentTitle = driver.getTitle();
		
		Assert.assertEquals(currentTitle, theatres.getTitle(), "This isn't the right page title");
		
		// Get list of theatres
		List<String> theatreList = theatres.getTheatres();
		
		// Pick a threatre
		theatres.gotoTheatre(theatreList.get(0));
		
		// Get a list of all of the movies
		MovieDayList dayList = new MovieDayList(driver);
		
		List<MovieInfo> list = dayList.getAllMoviesInAWeek();
		
		// Write out the list
		//MovieListWriter.writeMovies(list);
		MovieListWriter.newWrite(list);
	}
}
