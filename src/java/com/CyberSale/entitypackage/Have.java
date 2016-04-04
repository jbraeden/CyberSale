/*
 * Created by Joseph Sebastian on 2016.04.03  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Braeden
 */
@Entity
@Table(name = "Have")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Have.findAll", query = "SELECT h FROM Have h"),
    @NamedQuery(name = "Have.findByCommentId", query = "SELECT h FROM Have h WHERE h.havePK.commentId = :commentId"),
    @NamedQuery(name = "Have.findByItemId", query = "SELECT h FROM Have h WHERE h.havePK.itemId = :itemId")})
public class Have implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HavePK havePK;

    public Have() {
    }

    public Have(HavePK havePK) {
        this.havePK = havePK;
    }

    public Have(int commentId, int itemId) {
        this.havePK = new HavePK(commentId, itemId);
    }

    public HavePK getHavePK() {
        return havePK;
    }

    public void setHavePK(HavePK havePK) {
        this.havePK = havePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (havePK != null ? havePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Have)) {
            return false;
        }
        Have other = (Have) object;
        if ((this.havePK == null && other.havePK != null) || (this.havePK != null && !this.havePK.equals(other.havePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.Have[ havePK=" + havePK + " ]";
    }
    
}
