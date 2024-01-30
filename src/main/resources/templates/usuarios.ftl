<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Listado de Usuarios</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2>Listado de Usuarios</h2>

            <table class="table table-bordered">
                <thead>
                    <tr style="background-color: #18406a; color: #fff;">
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Saldo</th>
                        <th>Enlace</th>
                    </tr>
                </thead>
                <tbody>
                    <#--
                    <#list usuarios as usuario>
                    <tr style="background-color: ${articulo_index % 2 == 0 ? '#f9f9f9' : '#ffffff'};">
                        <td>${usuario.id}</td>
                        <td>${usuario.fullName}</td>
                        <td>${usuario.saldo}</td>
                        <td><a href="/articulos/${usuario.id}">COMPRARLE ALGO</a></td>
                    </tr>
                    </#list>
                    -->
                    <tr style="background-color: #f9f9f9;">
                        <td>ID</td>
                        <td>Nombre Apellido1 Apellido2</td>
                        <td>1234</td>
                        <td><a href="/articulos/1">COMPRARLE ALGO</a></td>
                    </tr>
                    <tr style="background-color: #ffffff;">
                        <td>ID</td>
                        <td>Nombre Apellido1 Apellido2</td>
                        <td>1234</td>
                        <td><a href="/articulos/1">COMPRARLE ALGO</a></td>
                    </tr>
                    <tr style="background-color: #f9f9f9;">
                        <td>ID</td>
                        <td>Nombre Apellido1 Apellido2</td>
                        <td>1234</td>
                        <td><a href="/articulos/1">COMPRARLE ALGO</a></td>
                    </tr>
                    <tr style="background-color: #ffffff;">
                        <td>ID</td>
                        <td>Nombre Apellido1 Apellido2</td>
                        <td>1234</td>
                        <td><a href="/articulos/1">COMPRARLE ALGO</a></td>
                    </tr>
                </tbody>
            </table>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>