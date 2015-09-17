package Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// This class stores movie information
public class MovieInfo {
	
	private String name;
	private String imgURL;
	private List<Date> movieTimes;
	
	public MovieInfo(String name, String imgURL, List<Date> times)
	{
		this.name = name;
		this.imgURL = imgURL;
		this.movieTimes = times;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getURL()
	{
		return imgURL;
	}
	
	public List<Date> getMovieTimes()
	{
		return movieTimes;
	}
}
