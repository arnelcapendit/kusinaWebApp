/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.api.model;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author marlon.naraja
 */
@JsonPropertyOrder({"TOTAL", "USERNAME", "CAREER_LEVEL_DESCRIPTION", "CAREERTRACKS", "GEOGRAPHY", "CAREERTRACK_DESCRIPTION", "CAREER_LEVEL", "COUNTRY", "VISITS", "AVG_VISIT_DURATION", "AVG_PAGES_PER_VISIT", "HITS"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUsageModel{

	private String user;
	private String careerLevelDescription;
	private String careerTrackDescription;
	private String careerLevel;
	private String country;
    private String careerTracks;
    private String geography;
	private String visits;
    private String avgVisit;
    private String avgPagesPerVisit;
    private String hits;
    private String total;

	public UserUsageModel(String user, String visits, String avgVisit, String avgPagesPerVisit, String hits) {
        this.user = user;
        this.visits = visits;
        this.avgVisit = avgVisit;
        this.avgPagesPerVisit = avgPagesPerVisit;
        this.hits = hits;
    }

    public UserUsageModel() {
    }

    @JsonGetter("USERNAME")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @JsonGetter("VISITS")
    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    @JsonGetter("AVG_VISIT_DURATION")
    public String getAvgVisit() {
        return avgVisit;
    }

    public void setAvgVisit(String avgVisit) {
        this.avgVisit = avgVisit;
    }

    @JsonGetter("AVG_PAGES_PER_VISIT")
    public String getAvgPagesPerVisit() {
        return avgPagesPerVisit;
    }

    public void setAvgPagesPerVisit(String avgPagesPerVisit) {
        this.avgPagesPerVisit = avgPagesPerVisit;
    }

    @JsonGetter("HITS")
    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
    
    @JsonGetter("CAREER_LEVEL")
    public String getCareerLevel() {
		return careerLevel;
	}

	public void setCareerLevel(String careerLevel) {
		this.careerLevel = careerLevel;
	}

	@JsonGetter("CAREERTRACKS")
	public String getCareerTracks() {
		return careerTracks;
	}

	public void setCareerTracks(String careerTracks) {
		this.careerTracks = careerTracks;
	}

	@JsonGetter("GEOGRAPHY")
	public String getGeography() {
		return geography;
	}

	public void setGeography(String geography) {
		this.geography = geography;
	}
	
	@JsonGetter("TOTAL")
    public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	@JsonGetter("CAREERTRACK_DESCRIPTION")
	public String getCareerTrackDescription() {
		return careerTrackDescription;
	}

	public void setCareerTrackDescription(String careerTrackDescription) {
		this.careerTrackDescription = careerTrackDescription;
	}

	@JsonGetter("COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@JsonGetter("CAREER_LEVEL_DESCRIPTION")
    public String getCareerLevelDescription() {
		return careerLevelDescription;
	}

	public void setCareerLevelDescription(String careerLevelDescription) {
		this.careerLevelDescription = careerLevelDescription;
	}

	//-----------------------User Sort--------------------------------------//
	public static Comparator<UserUsageModel> UserComparatorAsc= new Comparator<UserUsageModel>() {
		
		public int compare(UserUsageModel user1, UserUsageModel user2) {
		
		String userName1 = user1.getUser().toUpperCase();
		String userName2 = user2.getUser().toUpperCase();
		
		//ascending order
		return userName1.compareTo(userName2);
		}
	};
	
	public static Comparator<UserUsageModel> UserComparatorDesc= new Comparator<UserUsageModel>() {
		
		public int compare(UserUsageModel user1, UserUsageModel user2) {
		
		String userName1 = user1.getUser().toUpperCase();
		String userName2 = user2.getUser().toUpperCase();
	
		//descending order
		return userName2.compareTo(userName1);
		}
	};
	
	//-----------------------Visits Sort--------------------------------------//
		public static Comparator<UserUsageModel> VisitsComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel visit1, UserUsageModel visit2) {
			
			String visitName1 = visit1.getVisits().toUpperCase();
			String visitName2 = visit2.getVisits().toUpperCase();
			
			//ascending order
			return visitName1.compareTo(visitName2);
			}
		};
		
		public static Comparator<UserUsageModel> VisitsComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel visit1, UserUsageModel visit2) {
				
			String visitName1 = visit1.getVisits().toUpperCase();
			String visitName2 = visit2.getVisits().toUpperCase();
			
			//descending order
			return visitName2.compareTo(visitName1);
			}
		};
		
	//-----------------------Average Visit Duration Sort--------------------------------------//
		public static Comparator<UserUsageModel> AveVisitComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel aveVisit1, UserUsageModel aveVisit2) {
			
			String aveVisitName1 = aveVisit1.getAvgVisit().toUpperCase();
			String aveVisitName2 = aveVisit2.getAvgVisit().toUpperCase();
			
			//ascending order
			return aveVisitName1.compareTo(aveVisitName2);
			}
		};
		
		public static Comparator<UserUsageModel> AveVisitComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel aveVisit1, UserUsageModel aveVisit2) {
				
			String aveVisitName1 = aveVisit1.getAvgVisit().toUpperCase();
			String aveVisitName2 = aveVisit2.getAvgVisit().toUpperCase();
			
			//descending order
			return aveVisitName2.compareTo(aveVisitName1);
			}
		};
		
	//-----------------------Average Pages per Visit Sort--------------------------------------//
		public static Comparator<UserUsageModel> AvePageVisitComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel avePagevisit1, UserUsageModel avePagevisit2) {
			
			String avePagevisitName1 = avePagevisit1.getAvgPagesPerVisit().toUpperCase();
			String avePagevisitName2 = avePagevisit2.getAvgPagesPerVisit().toUpperCase();
			
			//ascending order
			return avePagevisitName1.compareTo(avePagevisitName2);
			}
		};
		
		public static Comparator<UserUsageModel> AvePageVisitComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel avePagevisit1, UserUsageModel avePagevisit2) {
				
			String avePagevisitName1 = avePagevisit1.getAvgPagesPerVisit().toUpperCase();
			String avePagevisitName2 = avePagevisit2.getAvgPagesPerVisit().toUpperCase();
			
			//descending order
			return avePagevisitName2.compareTo(avePagevisitName1);
			}
		};
		
	//-----------------------Hits Sort--------------------------------------//
		public static Comparator<UserUsageModel> HitsComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel hits1, UserUsageModel hits2) {
			
			String hitsName1 = hits1.getHits().toUpperCase();
			String hitsName2 = hits2.getHits().toUpperCase();
			
			//ascending order
			return hitsName1.compareTo(hitsName2);
			}
		};
		
		public static Comparator<UserUsageModel> HitsComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel hits1, UserUsageModel hits2) {
			
			String hitsName1 = hits1.getHits().toUpperCase();
			String hitsName2 = hits2.getHits().toUpperCase();
			
			//descending order
			return hitsName2.compareTo(hitsName1);
			}
		};
			
	//-----------------------CareerTrackDesc Sort--------------------------------------//
		public static Comparator<UserUsageModel> CareerTrackDescComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel ctd1, UserUsageModel ctd2) {
			
			String ctdName1 = ctd1.getCareerTrackDescription().toUpperCase();
			String ctdName2 = ctd2.getCareerTrackDescription().toUpperCase();
			
			//ascending order
			return ctdName1.compareTo(ctdName2);
			}
		};
		
		public static Comparator<UserUsageModel> CareerTrackDescComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel ctd1, UserUsageModel ctd2) {
				
				String ctdName1 = ctd1.getCareerTrackDescription().toUpperCase();
				String ctdName2 = ctd2.getCareerTrackDescription().toUpperCase();
				
				//descending order
				return ctdName2.compareTo(ctdName1);
			}
		};
	
	//-----------------------CareerLevel Sort--------------------------------------//
		public static Comparator<UserUsageModel> CareerLevelComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel cl1, UserUsageModel cl2) {
			
			String cl1Name1 = cl1.getCareerLevel().toUpperCase();
			String cl2Name2 = cl2.getCareerLevel().toUpperCase();
			
			//ascending order
			return cl1Name1.compareTo(cl2Name2);
			}
		};
		
		public static Comparator<UserUsageModel> CareerLevelComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel cl1, UserUsageModel cl2) {
				
				String cl1Name1 = cl1.getCareerLevel().toUpperCase();
				String cl2Name2 = cl2.getCareerLevel().toUpperCase();
				
				//descending order
				return cl2Name2.compareTo(cl1Name1);
			}
		};
		
	//-----------------------Country Sort--------------------------------------//
		public static Comparator<UserUsageModel> CountryComparatorAsc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel country1, UserUsageModel country2) {
			
			String country1Name1 = country1.getCountry().toUpperCase();
			String country2Name2 = country2.getCountry().toUpperCase();
			
			//ascending order
			return country1Name1.compareTo(country2Name2);
			}
		};
		
		public static Comparator<UserUsageModel> CountryComparatorDesc= new Comparator<UserUsageModel>() {
			
			public int compare(UserUsageModel country1, UserUsageModel country2) {
				
				String country1Name1 = country1.getCountry().toUpperCase();
				String country2Name2 = country2.getCountry().toUpperCase();
				
				//descending order
				return country2Name2.compareTo(country1Name1);
			}
		};
	
	
	
}
