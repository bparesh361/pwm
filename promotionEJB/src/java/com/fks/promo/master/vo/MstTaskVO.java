/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

/**
 *
 * @author Paresb
 */
public class MstTaskVO {
    
    private Long taskId;
    private String taskName;
     private Short isBlocked;

    public MstTaskVO() {
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }    

    public MstTaskVO(Long taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }    
    
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    
    
}
