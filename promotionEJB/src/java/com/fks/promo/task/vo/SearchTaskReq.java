/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.task.vo;

/**
 *
 * @author Paresb
 */
public class SearchTaskReq {
    
    private SearchTaskType type;
    private int startIndex;
    private Long assignerId;
    private Long creatorId;

    public SearchTaskType getType() {
        return type;
    }

    public void setType(SearchTaskType type) {
        this.type = type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public Long getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    } 
    
    
}
