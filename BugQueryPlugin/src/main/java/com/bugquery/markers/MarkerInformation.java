package com.bugquery.markers;

public class MarkerInformation {
	private String packageName;
	private String fileName;
	private Integer lineNumber;
	
	public MarkerInformation(String pn, String fn, Integer ln) {
		this.packageName = pn;
		this.fileName = fn;
		this.lineNumber = ln;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	

}
