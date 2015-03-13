package com.lysum.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "smdept")
public class Dept extends IdEntity {
	private String deptName ;
    private Dept parentDept;
    private Long parentId;
    private List<Dept> childDepts;
    
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    
	
	@ManyToOne
	@JoinColumn(name = "parent_id",insertable=false,updatable=false)
	public Dept getParentDept() {
		return parentDept;
	}
    
	public void setParentDept(Dept parentDept) {
		this.parentDept = parentDept;
	}

    @OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.LAZY,mappedBy="parentDept")
	public List<Dept> getChildDepts() {
		return childDepts;
	}
     
	public void setChildDepts(List<Dept> childDepts) {
		this.childDepts = childDepts;
	}
	
    @Column(name="parent_id")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
