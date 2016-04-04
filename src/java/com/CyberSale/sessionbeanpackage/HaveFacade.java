/*
 * Created by Joseph Sebastian on 2016.04.04  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Have;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class HaveFacade extends AbstractFacade<Have> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HaveFacade() {
        super(Have.class);
    }
    
}
