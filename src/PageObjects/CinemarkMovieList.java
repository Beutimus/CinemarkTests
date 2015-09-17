package PageObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Movie.MovieInfo;

public class CinemarkMovieList {
	private final WebDriver driver;
	
	private final String myTitle = "Cinemark - Theatre Detail";
	
	// Instantiate this object and make sure we are on the correct page
	public CinemarkMovieList (WebDriver driver)
	{
		this.driver = driver;
		
		if (driver.getTitle().startsWith(myTitle) == false) {
			throw new IllegalStateException("This is not the Cinemark movie list page. Current page is: "
			                                 + driver.getCurrentUrl());
		}
	}
	
	By showtimeDate = By.id("showtimes-for");
	By movieList = By.className("movies-box");
	
	// Retrieve list of movies from the page
	public List<MovieInfo> getMovies()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy H:mma");
		WebElement list = driver.findElement(movieList);
		
		List<WebElement> movies = list.findElements(By.xpath("//div[@class='movies-box']/li"));
		List<MovieInfo> movieInfoList = new ArrayList<MovieInfo>();
		
		// For each movie listing on the page
		for(WebElement movie : movies)
		{
			// Pull out the image URL
			WebElement moviePicture = movie.findElement(By.tagName("img"));
			
			String imageURL = moviePicture.getAttribute("src");
			
			WebElement movieLink = movie.findElement(By.tagName("a"));
			
			String movieName = movieLink.getText();
			
			// Pull out the show time information
			List<WebElement> showTimes = movie.findElements(By.className("theatreShowtimeSingle"));
			List<Date> dates = new ArrayList<Date>();		
			
			for(WebElement showTime : showTimes)
			{
				String dateString = showTime.getAttribute("title");
				Date newDate = null;				
				
				// I need to figure out a better way to handle this parse exception
				try {
					newDate = formatter.parse(dateString);
				}
				catch (ParseException e)
				{
					org.junit.Assert.assertFalse(true);
				}			
				
				dates.add(newDate);
			}
			
			// Still not done
			movieInfoList.add(new MovieInfo(movieName, imageURL, dates));
		}
		
		return movieInfoList;
	}
}
