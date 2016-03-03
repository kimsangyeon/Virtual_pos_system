package com.pos;

public class Staff 
{
	private int id;
	private String name; // 이름
	private int timemoney; // 시급
	private int starttime; // 시작시간
	private int endtime; // 마감시간
	private int totalmoney; // 총 번 돈
	private int fire;	// 해고
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getTimemoney() { return timemoney; }
	public void setTimemoney(int timemoney) { this.timemoney = timemoney; }
	
	public int getStarttime() { return starttime; }
	public void setStarttime(int starttime) { this.starttime = starttime; }
	
	public int getEndtime() { return endtime; }
	public void setEndtime(int endtime) { this.endtime = endtime; }
	
	public int getTotal() { return totalmoney; }
	public void setTotal(int totalmoney) { this.totalmoney = totalmoney; }
	
	public int getFire() { return fire; }
	public void setFire(int fire) { this.fire = fire; }
}
