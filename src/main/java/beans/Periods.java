package beans;

public class Periods {
	private int id;
	private String name;
	private String startPeriod;
	private String finishPeriod;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getFinishPeriod() {
		return finishPeriod;
	}

	public void setFinishPeriod(String finishPeriod) {
		this.finishPeriod = finishPeriod;
	}

	public Periods getPeriod(String startYear, String startMonth, String startDay, String finishYear, String finishMonth,
			String finishDay, int id) {
		Periods period = new Periods();
		period.id = id;
		
		
		char[] a = startMonth.toCharArray();
		int x = Integer.parseInt(startMonth);
		String t1 ="0";
		if(a[0]=='0'){
			if(a[1] == 9){
				t1 = "10";
			}
			x++;
			t1 = t1 + x;
		} else {
			x++;
			t1 = ""+x;
		}
		
		char[] a1 = finishMonth.toCharArray();
		int x1 = Integer.parseInt(finishMonth);
		String t2 ="0";
		if(a1[0]=='0'){
			
			x1++;
			t2 = t2 + x1;
			if(a1[1] == '9'){
				t2 = "10";
			}
		} else {
			x1++;
			t2 = "" + x1;
		}
		
		period.startPeriod = startYear + "-" + t1 + "-" + startDay;
		period.finishPeriod = finishYear + "-" + t2 + "-" + finishDay;
		
		period.name = "" + startDay + "." + t1 + "." + startYear + " - " + finishDay + "."
				+ t2 + "." + finishYear;
		return period;
	}

}
