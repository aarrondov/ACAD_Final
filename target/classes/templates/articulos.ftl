<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Listado de Artículos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2>Listado de Artículos</h2>

             <#if usuario?has_content && usuario?size gt 0>
                <table class="table table-bordered">
                    <thead>
                        <tr style="background-color: #18406a; color: #fff;">
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Valor</th>
                            <th>Propietario</th>
                            <!--
                                <th>Enlace compra</th>
                            -->
                        </tr>
                    </thead>
                    <tbody>
                        <#list articulos as articulo>
                        <tr style='background-color: #f9f9f9;'>
                            <td>${articulo.id}</td>
                            <td>${articulo.nombre}</td>
                            <td>${articulo.valor}</td>
                            <td>${articulo.userId}</td>
                            <#if mostrarIdExtra>
                                <td><a href="/comprar/:${articulo.id}">comprar</a></td>
                            </#if>
                        </tr>
                        </#list>
                        <tr style="background-color: #f9f9f9;">
                            <td>ID</td>
                            <td>Nombre</td>
                            <td>Valor</td>
                            <td>Propietario</td>
                        </tr>
                        <tr style="background-color: #ffffff;">
                            <td>ID</td>
                            <td>Nombre</td>
                            <td>Valor</td>
                            <td>Propietario</td>
                        </tr>
                        <tr style="background-color: #f9f9f9;">
                            <td>ID</td>
                            <td>Nombre</td>
                            <td>Valor</td>
                            <td>Propietario</td>
                        </tr>
                        <tr style="background-color: #ffffff;">
                            <td>ID</td>
                            <td>Nombre</td>
                            <td>Valor</td>
                            <td>Propietario</td>
                        </tr>
                    </tbody>
                </table>
            <#else>
                <p>Oh... parece que no dispone de articulos.</p>
            </#if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>