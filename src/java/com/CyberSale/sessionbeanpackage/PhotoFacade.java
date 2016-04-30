/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Photo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 * @author Patrick Abod
 * @author Shawn Amjad
 */
@Stateless
public class PhotoFacade extends AbstractFacade<Photo> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhotoFacade() {
        super(Photo.class);
    }
    
    /*
        The following code is added to the generated code
    */
    
    /**
     * Find a particular photo by its id
     * @param id the id of the photo
     * @return the photo found, null otherwise
     */
    public Photo findPhotoById(Integer id) {
        try {
            if (em.createNamedQuery("Photo.findById", Photo.class)
                    .setParameter("id", id)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Photo.findById", Photo.class)
                    .setParameter("id", id).getResultList().get(0);
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
}
