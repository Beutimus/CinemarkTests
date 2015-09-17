package PageObjects;

import org.openqa.selenium.*;

// This class provides search functionality
public class CinemarkSearchFeature {
	private final WebDriver driver;
	
	private final String myTitle = "Cinemark - Movie Information, Showtimes and Tickets";
	
	// Create a new page object. Make sure we are on the correct page
	public CinemarkSearchFeature (WebDriver driver)
	{
		this.driver = driver;
		
		if (driver.getTitle().equals(myTitle) == false) {
			throw new IllegalStateException("This is not the cinemark home page. Current page is: "
			                                 + driver.getCurrentUrl());
		}	
	}
	
	// Locators for the important parts of the search feature
	By searchFieldLocator = By.id("main_inp1");
	By submitButtonLocator = By.xpath("//input[contains(@src,\"btn-submit.gif\")]");
	By byTitleSearchLocator = By.className("btn-search");
	
	// Get this page's title
	public String getTitle()
	{
		return myTitle;
	}
	
	// Search the current page given the search Text
	public CinemarkTheatreList search(String searchText)
	{
		driver.findElement(searchFieldLocator).sendKeys(searchText);
		driver.findElement(submitButtonLocator).click();
		
		return new CinemarkTheatreList(driver);
	}
}
