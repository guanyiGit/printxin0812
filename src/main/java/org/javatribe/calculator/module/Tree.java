package org.javatribe.calculator.module;

import java.io.Serializable;
import java.util.List;

public class Tree implements Serializable{
	
	private Integer resourceId;		//资源id
	
	 private String resourceName;   //资源名称
	 
	 private boolean checked = false;
	 
	 private Object attributes;   //存储资源url
	 
	 private List<Tree> children;
	 
	 private Integer pid;		//父id

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	 
	 
	 
	
}
