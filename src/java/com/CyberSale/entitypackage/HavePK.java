/*
 * Created by Joseph Sebastian on 2016.04.03  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Braeden
 */
@Embeddable
public class HavePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "comment_id")
    private int commentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "item_id")
    private int itemId;

    public HavePK() {
    }

    public HavePK(int commentId, int itemId) {
        this.commentId = commentId;
        this.itemId = itemId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) commentId;
        hash += (int) itemId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HavePK)) {
            return false;
        }
        HavePK other = (HavePK) object;
        if (this.commentId != other.commentId) {
            return false;
        }
        if (this.itemId != other.itemId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.HavePK[ commentId=" + commentId + ", itemId=" + itemId + " ]";
    }
    
}
