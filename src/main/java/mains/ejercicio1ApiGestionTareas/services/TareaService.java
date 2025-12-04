package mains.ejercicio1ApiGestionTareas.services;

import mains.ejercicio1ApiGestionTareas.dto.TareaDTO;
import mains.ejercicio1ApiGestionTareas.exceptions.DatosInvalidosException;
import mains.ejercicio1ApiGestionTareas.exceptions.TareaNoEncontradaException;
import mains.ejercicio1ApiGestionTareas.models.Tarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TareaService {

    private final Map<Integer, Tarea> tareas = new ConcurrentHashMap<>();
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public TareaService() {
        inicializarDatosEjemplo();
    }

    /*
     * Obtiene todas las tareas
     */
    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas.values());
    }

    /*
     * Obtiene una tarea por ID
     * @throws TareaNoEncontradaException si no existe
     */
    public Tarea obtenerPorId(int id) {
        Tarea tarea = tareas.get(id);

        if (tarea == null) {
            throw new TareaNoEncontradaException(
                    "No se encontró la tarea con ID: " + id
            );
        }

        return tarea;
    }

    /*
     * Crea una nueva tarea
     * @throws DatosInvalidosException si los datos no son válidos
     */
    public Tarea crear(TareaDTO tareaDTO) {
        // VALIDACIONES
        validarTitulo(tareaDTO.getTitulo());

        // Crear la tarea
        int nuevoId = contadorId.getAndIncrement();
        Tarea nuevaTarea = new Tarea(
                nuevoId,
                tareaDTO.getTitulo().trim(),
                tareaDTO.getDescripcion() != null ? tareaDTO.getDescripcion().trim() : "",
                false
        );

        tareas.put(nuevoId, nuevaTarea);

        return nuevaTarea;
    }

    /*
     * Actualiza una tarea existente
     * @throws TareaNoEncontradaException si no existe
     * @throws DatosInvalidosException si los datos no son válidos
     */
    public Tarea actualizar(int id, TareaDTO tareaDTO) {
        // Verificar que existe
        Tarea tareaExistente = obtenerPorId(id);

        // VALIDACIONES
        validarTitulo(tareaDTO.getTitulo());

        // Actualizar campos
        tareaExistente.setTitulo(tareaDTO.getTitulo().trim());
        tareaExistente.setDescripcion(
                tareaDTO.getDescripcion() != null ? tareaDTO.getDescripcion().trim() : ""
        );

        return tareaExistente;
    }

    /*
     * Elimina una tarea
     * @throws TareaNoEncontradaException si no existe
     */
    public void eliminar(int id) {
        // Verificar que existe antes de eliminar
        obtenerPorId(id);

        tareas.remove(id);
    }

    /*
     * Marca una tarea como completada
     * @throws TareaNoEncontradaException si no existe
     */
    public Tarea marcarCompletada(int id) {
        Tarea tarea = obtenerPorId(id);
        tarea.setCompletada(true);
        return tarea;
    }

    /*
     * VALIDACIÓN: Título no vacío y longitud máxima
     */
    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatosInvalidosException("El título es obligatorio y no puede estar vacío");
        }

        if (titulo.length() > 100) {
            throw new DatosInvalidosException(
                    "El título no puede superar 100 caracteres (tiene " + titulo.length() + ")"
            );
        }
    }

    /*
     * Inicializa datos de ejemplo
     */
    private void inicializarDatosEjemplo() {
        tareas.put(1, new Tarea(1, "Aprender Javalin", "Completar tutorial de API REST", false));
        tareas.put(2, new Tarea(2, "Hacer ejercicios", "Practicar CRUD completo", false));
        tareas.put(3, new Tarea(3, "Revisar código", "Code review del proyecto", true));
        contadorId.set(4);
    }
}
