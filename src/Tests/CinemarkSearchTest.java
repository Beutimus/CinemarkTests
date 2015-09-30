package Tests;


import java.io.File;
import java.util.List;

import org.testng.annotations.*;
import org.testng.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Movie.MovieInfo;
import PageObjects.CinemarkSearchFeature;
import PageObjects.CinemarkTheatreList;
import PageObjects.MovieDayList;
import TestHelper.MovieListWriter;
import jxl.*;

public class CinemarkSearchTest {
	
	private WebDriver driver;
	
	private final String cinemarkHomePageURL = "http://www.cinemark.com/";
	
	@BeforeClass
	public void setUp() throws Exception {
		driver = new FirefoxDriver();		
	}
	
	@Test
	public void searchTest() {
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
	public void paramTest() {
		
	}
	
	@AfterClass
	public void tearDown() throws Exception {
	    driver.quit();
	}
}
