package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Movie.MovieInfo;

public class MovieDayList {
	private final WebDriver driver;
	
	private final String myTitle = "Cinemark - Theatre Detail";
	
	By datePicker = By.className("day-picker-ajax-link");
	
	// Instantiate this object and make sure we are on the correct page
	public MovieDayList (WebDriver driver)
	{
		this.driver = driver;
		
		if (driver.getTitle().startsWith(myTitle) == false) {
			throw new IllegalStateException("This is not the Cinemark movie list page. Current page is: "
			                                 + driver.getCurrentUrl());
		}
	}
	
	public List<MovieInfo> getAllMoviesInAWeek(){
		
		CinemarkMovieList moviePage;
		
		List<MovieInfo> movies = new ArrayList<MovieInfo>();
		
		List<WebElement> days = this.availableDays();
		
		for(WebElement day : days)
		{
			if(day.getText() != "MORE")
			{
				// do stuff
				day.click();
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				moviePage = new CinemarkMovieList(driver);
				
				List<MovieInfo> daysMovies = moviePage.getMovies();
				
				movies.addAll(daysMovies);
			}
		}
		
		return movies;
	}

	
	// Returns a list of web elements that corresponding to clickable days on the list
	public List<WebElement> availableDays()
	{
		return driver.findElements(datePicker);
	}
}
