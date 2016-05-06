package beans;

import java.util.ArrayList;
import java.util.List;

public class AllPeriods {
	List<Periods> periods = new ArrayList<Periods>();

	public List<Periods> getAllPeriods() {
		periods.clear();
		periods.add(new Periods().getPeriod("2014", "10", "01", "2015", "00", "31", 0));
		periods.add(new Periods().getPeriod("2015", "01", "01", "2015", "03", "31", 1));
		periods.add(new Periods().getPeriod("2015", "04", "01", "2016", "06", "31", 2));
		periods.add(new Periods().getPeriod("2015", "07", "01", "2016", "09", "31", 3));
		periods.add(new Periods().getPeriod("2015", "10", "01", "2016", "00", "31", 4));
		periods.add(new Periods().getPeriod("2016", "01", "01", "2016", "03", "30", 5));
		return periods;
	}

}
