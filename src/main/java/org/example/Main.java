package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        ClienteDAO dao = new ClienteDAO();

        // Crear cliente
        Cliente nuevo = new Cliente("12345678A", "Juan", "Pérez Gómez");
        dao.crear(nuevo);

        // Leer cliente
        Cliente cliente = dao.leer(1);
        if (cliente != null) {
            System.out.println("Cliente leído: " + cliente.getNombre());
        }

        // Listar clientes
        for (Cliente c : dao.listar()) {
            System.out.println(c.getId() + " - " + c.getNombre() + " " + c.getApellidos());
        }

        // Actualizar cliente
        if (cliente != null) {
            cliente.setNombre("Juanito");
            dao.actualizar(cliente);
        }

        // Eliminar cliente
        if (cliente != null) {
            dao.eliminar(cliente.getId());
        }
    }
}
