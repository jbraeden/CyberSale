/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Comment;
import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.ItemComment;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class ItemCommentFacade extends AbstractFacade<ItemComment> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemCommentFacade() {
        super(ItemComment.class);
    }
    
    /*
        The following methods are added to the generated code
    */
    
    public List<Comment> findCommentsForItem(Integer itemId) {
        List<ItemComment> itemComments = null;
        try {
            itemComments = em.createNamedQuery("findItemCommentByItem", ItemComment.class)
                    .setParameter("itemId", itemId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<Comment> comments = new ArrayList<>();
        if (itemComments != null && !itemComments.isEmpty()) {
            for (ItemComment ic : itemComments) {
                if ((ic.getItemId().getId()).equals(itemId)) {
                    try {
                        comments.add(em.find(Comment.class, ic.getCommentId().getId()));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return comments;
        }
        return null;
    }
    
    public Item findItemForComment(Integer commentId) {
        ItemComment itemComment = null;
        try {
            itemComment = em.createNamedQuery("findItemCommentByComment", ItemComment.class)
                    .setParameter("commentId", commentId)
                    .getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Item item = null;
        if (itemComment != null) {
            try {
                item = em.find(Item.class, itemComment.getItemId().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return item;
    }
    
}
