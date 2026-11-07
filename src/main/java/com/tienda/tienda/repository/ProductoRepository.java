package com.tienda.tienda.repository;

import com.tienda.tienda.domain.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    //Consulta derivada que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(BigDecimal precioInf, BigDecimal precioSup);

    //Consulta JPQL que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(BigDecimal precioInf, BigDecimal precioSup);

    //Consulta SQL que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(BigDecimal precioInf, BigDecimal precioSup);
    
    /* Para la practica #2:
    Consulta ampliada: descripción, categoría y existencias*/
    @Query("SELECT p FROM Producto p "
            + "WHERE (:descripcion IS NULL OR "
            + "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))) "
            + "AND (:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) "
            + "AND (:soloDisponibles = false OR p.existencias > 0) "
            + "ORDER BY p.descripcion ASC")
    public List<Producto> consultaAmpliada(
            @Param("descripcion") String descripcion,
            @Param("idCategoria") Integer idCategoria,
            @Param("soloDisponibles") boolean soloDisponibles);
}
