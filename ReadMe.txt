Proyecto manejo de archivos xml
===============================

Colocar los archivos que se encuentran en la carpeta files/incoming/backup en la carpeta files/incoming

El resultado del procesamiento se verá en la ruta files/outgoing

Para correr el proyecto, desde consola ejecutar:

    mvn celan camel:run

Desde JBoss developer studio, crear un perfil de ejecución:

	clean camel:run