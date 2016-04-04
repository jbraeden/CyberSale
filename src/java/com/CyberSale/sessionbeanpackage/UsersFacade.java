/*
 * Created by Joseph Sebastian on 2016.04.04  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
}
