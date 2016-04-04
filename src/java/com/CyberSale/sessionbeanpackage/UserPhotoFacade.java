/*
 * Created by Joseph Sebastian on 2016.04.04  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.UserPhoto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class UserPhotoFacade extends AbstractFacade<UserPhoto> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserPhotoFacade() {
        super(UserPhoto.class);
    }
    
}
