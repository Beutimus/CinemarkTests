package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;

public class CinemarkTheatreList {
	private final WebDriver driver;
	
	private final String myTitle = "Cinemark - Theatres";
	private final String emptyList = "No theaters found based on search criteria.";
	
	// Instantiate this object and make sure we are on the correct page
	public CinemarkTheatreList (WebDriver driver)
	{
		this.driver = driver;
		
		if (driver.getTitle().equals(myTitle) == false) {
			throw new IllegalStateException("This is not the Cinemark theatre list page. Current page is: "
			                                 + driver.getCurrentUrl());
		}
	}
	
	By theatreListLocator = By.className("h");
	
	// Pull off all the theaters listed on the screen
	public ArrayList<String> getTheatres()
	{
		ArrayList<String> theatres = new ArrayList<String>();
		
		// Get the entire list
		WebElement list = driver.findElement(theatreListLocator);
		
		// Get each list element
		List<WebElement> listElements = list.findElements(By.tagName("li"));
		
		// Iterate through each list element and extract the theater name
		for(WebElement element : listElements)
		{
			theatres.add(element.findElement(By.className("type")).getText());
		}
		
		return theatres;
	}
	
	// Selects a theater from the list on the screen
	public CinemarkMovieList gotoTheatre(String theatreName)
	{
		driver.findElement(By.linkText(theatreName)).click();
		
		return new CinemarkMovieList(driver);
	}

	public String getTitle()
	{
		return myTitle;
	}
}
