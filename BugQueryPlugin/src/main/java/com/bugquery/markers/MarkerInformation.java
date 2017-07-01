package com.bugquery.markers;

/**
 * A class for handling marker information gathered from different parts of the code
 * @author Doron
 * @since 24 05 2017
 */

public class MarkerInformation {
	private String packageName;
	private String fileName;
	private Integer lineNumber;

	public MarkerInformation(String packageName, String fileName, Integer lineNumber) {
		this.packageName = packageName;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
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
