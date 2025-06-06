package com.ecommerce.ecommerce.catalogo.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.productos.entities.Caracteristica;
import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.entities.PropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.entities.ValorPropiedadProducto;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;

/**
 * Este servicio se encarga del catalogo de administración de Productos. Tiene las responsabilidades
 * en cuanto a Producto se refiere, tales como, la creación, obtencion, actualizacion, etc.. de productos,
 * subcategorias, skus, propiedades de producto y valores.
 *
 * Es el servicio que une toda la logica de negocio acerca de los productos y lo que deriva de los mismos.
 *
 * TODO --> Desasignar PROPIEDADES PRODUCTO de productos y/o subcategorías, en caso de quererlo.
 */
public interface CatalogoAdminService {

    /**
     * Crea un nuevo {@link Producto}.
     * @param producto Producto nuevo.
     * @return Producto creado y guardado.
     */
    Producto crearProducto(Producto producto);

    /**
     * Crea, sube y vincula la foto principal a un {@link Producto}.
     * @param productoId Long id del producto a subir su foto.
     * @param foto Multipartfile archivo con la imagen a subir.
     * @return {@link Imagen} con los datos del archivo subido.
     */
    Imagen subirFotoPpalProducto(Long productoId, MultipartFile foto);

    /**
     * Obtiene la foto principal de un {@link Producto}.
     * @param productoId Long id del producto.
     * @return imagen descargada en bytes.
     */
    byte[] obtenerFotoPpalProducto(Long productoId);

    /**
     * Obtiene el path de foto principal de un {@link Producto}.
     * @param productoId Long id del producto.
     * @return String path de foto del producto.
     */
    String obtenerPathFotoPpalProducto(Long productoId);

    /**
     * Elimina una foto asociada a un {@link Producto}.
     * @param productoId Long id del producto.
     */
    void eliminarFotoPpalProducto(Long productoId);

    /**
     * Crea, sube y vincula una imagen secundaria para un {@link Producto}.
     * @param productoId Long id del producto a cargar imagen secundaria.
     * @param foto MultipartFile con el archivo que contiene la imagen.
     * @return {@link Imagen} con los datos de la nueva foto secundaria creada.
     */
    Imagen subirFotoSecundariaProducto(Long productoId, MultipartFile foto);

    /**
     * Obtiene todas las imagenes secundarias asociadas a un {@link Producto} requerido.
     * @param productoId Long id del producto a obtener sus imagenes secundarias.
     * @return {@link  Imagen}es secundarias del producto requerido.
     */
    List<Imagen> obtenerFotosSecundariasProducto(Long productoId);

    /**
     * Obtiene la {@link Imagen} secundaria requerida para un producto solicitado.
     * @param productoId Long id del producto.
     * @param imagenId Long id de la imagen a obtener del producto.
     * @return {@link Imagen} con los datos de la imagen secundaria.
     */
    Imagen obtenerFotoSecundariaProducto(Long productoId, Long imagenId);

    /**
     * Cambia una {@link Imagen} secundaria de un {@link Producto} por otra.
     * @param productoId Long id del producto a la que pertenece la imagen.
     * @param imagenId Long id de la imagen a cambiar.
     * @param foto  {@link MultipartFile} con el archivo que contiene la nueva imagen.
     * @return {@link Imagen} secundaria nueva asociada al producto.
     */
    Imagen cambiarImagenSecundariaProducto(Long productoId, Long imagenId, MultipartFile foto);

    /**
     * Obtiene el path de la foto secundaria de un {@link Producto} solicitada.
     * @param productoId Long id del producto.
     * @param imagenId Long id de la imagen secundaria a obtener path.
     * @return String path de foto secundaria del producto.
     */
    String obtenerPathImagenSecundaria(Long productoId, Long imagenId);

    /**
     * Descarga una imagen secundaria requerida para un producto solicitado.
     * @param productoId Long id del producto.
     * @param imagenId Long id d ela imagen a descargar del producto.
     * @return byte[] del archivo de imagen.
     */
    byte[] descargarImagenSecundariaProducto(Long productoId, Long imagenId);

    /**
     * Elimina una imagen secundaria del {@link Producto}.
     * @param productoId Long id del producto a eliminar la foto secundaria.
     * @param imagenId Long id de la imagen a eliminar.
     */
    void eliminarFotoSecundariaProducto(Long productoId, Long imagenId);

    /**
     * Cancela la creación de un producto en caso de que se desee, provocando su
     * eliminación completa del sistema.
     * <br>
     * NOTA**: SOLO SE PUEDE CANCELAR/ELIMINAR EN CASO DE NO CONTINUAR CON LA CREACIÓN DEL
     * PRODUCTO, Y EN NINGÚN OTRO LUGAR.
     * @param productoId Long id del producto a cancelar su creación.
     */
    void cancelarCreacionProducto(Long productoId);

    /**
     * Crea un nuevo {@link Sku} de un {@link Producto}.
     * @param productoId Long id del producto al que se requiere crear el Sku.
     * @param sku Sku nuevo.
     * @return Sku creado y guardado.
     */
    Sku crearSku(Long productoId, Sku sku);

    /**
     * Crea, sube y vincula foto de {@link Sku}.
     * @param skuId Long id del sku a subir su foto.
     * @param foto MultipartFile archivo que contiene la foto a subir.
     * @return {@link Imagen} con los datos del archivo subido.
     */
    Imagen subirFotoSku(Long skuId, MultipartFile foto);

    /**
     * Obtiene la foto de un {@link Producto}.
     * @param skuId Long id del sku.
     * @return imagen descargada en bytes.
     */
    byte[] obtenerFotoSku(Long skuId);

    /**
     * Obtiene el path de foto de un {@link Sku}.
     * @param skuId Long id del sku.
     * @return String path de foto del sku.
     */
    String obtenerPathFotoSku(Long skuId);

    /**
     * Elimina la foto asociada a un {@link Sku}.
     * @param skuId Long id del sku.
     */
    void eliminarFotoSku(Long skuId);

    /**
     * Elimina un {@link Sku} requerido.
     * @param skuId Long id del SKU a eliminar.
     */
    void eliminarSku(Long skuId);

    /**
     * Baja lógica de un {@link Sku}.
     * @param skuId Long id del sku a dar de baja.
     */
    void bajaSku(Long skuId);

    /**
     * Alta de un {@link Sku} existente.
     * @param skuId Long id del sku a dar de alta.
     */
    void altaSku(Long skuId);

    /**
     * Elimina todos los SKUs {@link Sku} de un producto requerido.
     * @param productoId Long id del producto a eliminar sus Skus.
     */
    void eliminarSkusProducto(Long productoId);

    /**
     * Crea una nueva {@link PropiedadProducto}.
     * @param propiedad PropiedadProducto nuevo.
     * @return PropiedadProducto creada y guardada.
     */
    PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedad);

    /**
     * Crea una nueva {@link PropiedadProducto} para un {@link Producto}. Asigna automáticamente
     * la propiedad al producto requerido, y, si es necesario, a la subcategoría del mismo.
     * @param productoId Long id del producto a crear la nueva propiedad.
     * @param propiedad PropiedadProducto nueva.
     * @return PropiedadProducto creada, guardada y asignada al producto correspondiente.
     */
    PropiedadProducto crearPropiedadProducto(Long productoId, PropiedadProducto propiedad);

    /**
     * Crea una nueva {@link PropiedadProducto} para una {@link Subcategoria}.
     * Asigna automáticamente la propiedad a la subcategoría requerida.
     * @param subcategoriaId Long id de la subcategoría a crear la nueva propiedad.
     * @param propiedad PropiedadProducto nueva.
     * @return PropiedadProducto creada, guardada y asignada a la subcategoría correspondiente.
     */
    PropiedadProducto crearPropiedadProductoSubcategoria(Long subcategoriaId, PropiedadProducto propiedad);

    /**
     * Modifica los datos de una {@link PropiedadProducto} y devuelve el objeto actualizado.
     * @param propiedadId Long id de la propiedad a modifcar sus datos.
     * @param propiedadActualizada propiedad con los datos actualizados.
     * @return {@link PropiedadProducto} actualizada y guardada.
     */
    PropiedadProducto actualizarPropiedadProducto(Long propiedadId, PropiedadProducto propiedadActualizada);

    /**
     * Elimina, si no tiene referencias con otros objetos, una propiedad producto existente (desasociada).
     * @param propiedadId Long id de la propiedad a eliminar.
     */
    void eliminarPropiedadProducto(Long propiedadId);

    /**
     * Crea un nuevo {@link ValorPropiedadProducto} que pertenecerá a una {@link PropiedadProducto} requerida.
     * @param propiedadId Long id de la propiedad a la cual se asignará el nuevo valor.
     * @param valor ValorPropiedadProducto nuevo.
     * @return ValorPropiedadProducto creado, guardado y asignado a la propiedad correspondiente.
     */
    PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor);

    /**
     * Actualiza un valor de propiedad existente de una propiedad.
     * @param propiedadId Long id de la propiedad.
     * @param valorId Long id del valor a modificar datos.
     * @param valor ValorPropiedadProducto con los datos actualizados.
     * @return {@link ValorPropiedadProducto} actualizado y guardado en la base de datos.
     */
    PropiedadProducto actualizarValorPropiedad(Long propiedadId, Long valorId, ValorPropiedadProducto valor);

    /**
     * Elimina un valor de propiedad en caso de que no tenga referencia con otros objetos.
     * @param propiedadId Long id de la propiedad a la que pertenece el valor.
     * @param valorId Long id del valor a eliminar.
     */
    void eliminarValorPropiedad(Long propiedadId, Long valorId);

    /**
     * Genera un listado de {@link Sku} a partir de un {@link Producto} requerido. Por cada propiedad
     * y cada valor de dicha propiedad del producto requerido, se generarán las distintas combinaciones
     * posibles, y asignarán a un SKU individual, lo que será finalmente un producto vendible con sus
     * propiedades seleccionadas.
     * @param productoId Long id del producto a generar sus SKUs.
     * @return Map que contiene la cantidad y combinaciones generadas.
     */
    Map<String, Object> generarSkusProducto(Long productoId);

    /**
     * Asigna una {@link PropiedadProducto} existente, a un {@link Producto} existente.
     * @param productoId Long id del producto.
     * @param propiedadId Long id de la propiedad a asignar.
     */
    void asignarPropiedadAProducto(Long productoId, Long propiedadId);

    /**
     * Asigna una {@link PropiedadProducto} existente, a una {@link Subcategoria}
     * @param subcategoriaId Long id de la subcategoría.
     * @param propiedadId Long id de la propiedad a asignar.
     */
    void asignarPropiedadASubcategoria(Long subcategoriaId, Long propiedadId);

    /**
     * Obtiene un listado con todas las {@link PropiedadProducto}.
     * @return List propiedades.
     */
    List<PropiedadProducto> obtenerPropiedadesProducto();

    /**
     * Obtiene una {@link PropiedadProducto} requerida a través de su id.
     * @param propiedadId Long id de la propiedad.
     * @return PropiedadProducto
     */
    PropiedadProducto obtenerPropiedadProducto(Long propiedadId);

    /**
     * Obtiene los {@link ValorPropiedadProducto} de una {@link PropiedadProducto}
     * @param propiedadId Long id de la propiedad a obtener sus valores.
     * @return List listado de los valores de la propiedad.
     */
    List<ValorPropiedadProducto> obtenerValoresDePropiedad(Long propiedadId);

    /**
     * Crea una nueva característica y la asocia al producto requerido.
     * @param caracteristica {@link Caracteristica} nueva a crearle al producto.
     * @param productoId Long id del producto que tendrá la nueva característica.
     * @return {@link Caracteristica} creada y asignada al producto.
     */
    Caracteristica crearCaracteristicaProducto(Caracteristica caracteristica, Long productoId);

    /**
     * Método que se encarga de modificar una característica existente, a través de su id, validando
     * que pertenece al producto requerido.
     * @param caracteristica {@link Caracteristica} actualizada con los datos nuevos.
     * @param caracteristicaId Long id de la caracteristica a modificar.
     * @param productoId Long id del producto al que pertenece la característica.
     * @return {@link Caracteristica} actualizada y guardada en la base de datos.
     */
    Caracteristica modificarCaracteristicaProducto(Caracteristica caracteristica,
                                                   Long caracteristicaId,
                                                   Long productoId);

    /**
     * Elimina una característica existente, que pertenece a un producto en particular.
     * @param caracteristicaId Long id de la característica a eliminar.
     * @param productoId Long id del producto al que pertenece la característica.
     */
    void eliminarCaracteristicaProducto(Long caracteristicaId, Long productoId);

    /**
     * Lista todas las características de un producto requerido.
     * @param productoId Long id del producto a listar sus características.
     * @return {@link List<Caracteristica>} del producto.
     */
    List<Caracteristica> listarCaracteristicasProducto(Long productoId);
}
