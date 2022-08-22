/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto_pv;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author HP
 */
public class ManagerFactory {
    private EntityManagerFactory emf = null;
    public EntityManagerFactory getEntityManagerFactory(){
        return emf=Persistence.createEntityManagerFactory("Proyecto_pvPU");
    }
    
}
