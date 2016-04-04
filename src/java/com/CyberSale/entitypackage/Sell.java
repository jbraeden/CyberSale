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
@Table(name = "Sell")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sell.findAll", query = "SELECT s FROM Sell s"),
    @NamedQuery(name = "Sell.findByUserId", query = "SELECT s FROM Sell s WHERE s.sellPK.userId = :userId"),
    @NamedQuery(name = "Sell.findByItemId", query = "SELECT s FROM Sell s WHERE s.sellPK.itemId = :itemId")})
public class Sell implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SellPK sellPK;

    public Sell() {
    }

    public Sell(SellPK sellPK) {
        this.sellPK = sellPK;
    }

    public Sell(int userId, int itemId) {
        this.sellPK = new SellPK(userId, itemId);
    }

    public SellPK getSellPK() {
        return sellPK;
    }

    public void setSellPK(SellPK sellPK) {
        this.sellPK = sellPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sellPK != null ? sellPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sell)) {
            return false;
        }
        Sell other = (Sell) object;
        if ((this.sellPK == null && other.sellPK != null) || (this.sellPK != null && !this.sellPK.equals(other.sellPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.Sell[ sellPK=" + sellPK + " ]";
    }
    
}
