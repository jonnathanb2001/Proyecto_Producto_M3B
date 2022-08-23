package Modelo;

import Modelo.Persona;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-08-23T07:55:32")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile SingularAttribute<Usuario, Integer> idusario;
    public static volatile SingularAttribute<Usuario, Persona> idpersona;
    public static volatile SingularAttribute<Usuario, String> usuario;

}