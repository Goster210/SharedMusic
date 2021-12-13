# SharedMusic

Proyecto web MVC que permite el uso de una plataforma musical

## Consideraciones Técnicas

1. Java11
2. Mysql
3. Spring-boot  
   * JPA
   * Patron Repository
   * MVC
   * Thymeleaf
5. Tomcat 9 

## Guia para clonar el proyecto

1. ubique una carpeta en su disco duro, para almacenar el proyecto ejemplo 
F:\proyectos\

2. abra una terminal y coloque el comando git clone y la ruta del repositorio git donde esta el proyecto

nota: tenga cuidado de cambiar la ruta del repositorio de acuerdo al que usted este utilizando

~~~
   git clone https://github.com/LUALROAL/DESARROLLO-DE-SOFTWARE_GRUPO3.git
~~~

Una vez este descargado el proyecto podrá abrirlo con cualquier editor de texto o IDE de desarrollo,

para mas información puede ver los siguientes recursos :

* [Abrir proyecto maven en eclipse](https://www.youtube.com/watch?v=FckwSuNnn9g)
* [Abrir proyecto maven en Netbeans](https://www.youtube.com/watch?v=ejchUBB_9SY)
* [Abrir proyecto maven en Visual Studio Code](https://www.youtube.com/watch?v=rYaEuDdpMFc)

## Base de Datos Mysql

Es importante tener en cuenta que se debe tener una base de datos Mysql instalada localmente y configurada
de la siguiente manera:

1. debe correr en localhost 
2. debe correr por el puerto por defecto de mysql 3306
3. debe tener usuario y contraseña con permisos de lectura y escritura (se recomienda dejar el usuario root que tiene todos los privilegios solicitados)
4. crear una base de datos llamada DB_Tienda



## Preparacion de la base de datos en el proyecto


En este apartado vamos a enlazar la base de datos con nuestro proyecto , para esto requerimos tener presente los siguietes datos:

* servidor: localhost
* puerto: 3306
* Base de datos: sharedmusicdb
* UsuarioMysql : root
* ContraseñaMysql: <la que usted definio>

ahora si procedamos a realizar el ajuste en el archivo de configuracion reemplazado por los datos apropiados para la conexion.


1. en el proyecto ubiquese en la ruta  tienda\src\main\resources y abra el archivo application.properties
2. modifique la cadena de conexion cambiando los datos necesarios para la comunicacion, como son:



~~~
  spring.datasource.url=jdbc:mysql://localhost:3306/sharedmusicdb
  spring.datasource.username=root
  spring.datasource.password=
~~~

## correr el proyecto en desarrollo

Antes de correr el proyecto verifique que la base de datos este conectada y el proyecto este apuntando correctamente con la cadena de conexion.

1. verifique que tenga java11 instalado
1. ubiquese en la raiz del proyecto
2. abra una terminal de acuerdo a su sistema operativo y corra el siguiente comando

linux o Mac

~~~
  ./mvnw spring-boot:run
~~~


Windows

~~~
  mvnw spring-boot:run
~~~



## Crear datos iniciales en la base de datos

Ejecute los siguientes script en una consola de la base de datos:

1. Crear Roles

~~~sql
	INSERT INTO rol ( rol_nombre) VALUES( 'ROLE_ADMIN');
  INSERT INTO rol ( rol_nombre) VALUES( 'ROLE_USER');
~~~

2. Crear Usuario Admin

~~~sql
	
	INSERT INTO usuario ( cedula, correo, nombre_completo, nombre_usuario, password) VALUES(NULL, NULL, NULL, 'admininicial', '$2a$10$zfecf9gT4kj65EEkaZPPFODmNaz/PqBiLZf9YDf2z.3CjN4lNGU06');    
	
	INSERT INTO usuario_rol (usuario_id, rol_id) VALUES(1, 1);
    INSERT INTO usuario_rol (usuario_id, rol_id) VALUES(1, 2);

	
~~~


### visualizar el proyecto

1. abra un navegador y coloque la ruta http://localhost:8080
2. inicie sesion con los siguientes datos
   usuario: admininicial
   contraseña: admin123456


### crear .war

~~~
  ./mvnw package -Dmaven.test.skip
~~~
