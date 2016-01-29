#Jurm
![Jurm logo](Jurm.ico)

##¿Que es Jurm?
Jurm es un interprete interactivo de lenguaje URM(unlimited register machine).
Su objetivo es facilitar la comprención de este modelo teórico de computación permitiendote saber en cada momento que instrucción se está ejecutando y que cambio hace eb los registros.

##Requerimientos

	* JDK 1.8 o superior (para la compilación)
	* Java 1.8 o superior (para la ejecución)

##Compilación y ejecución
```
Windows
	cd scripts\windows
	build
	createjar
	java -jar dist\Jurm.jar
Linux
	cd scripts/linux
	./build
	./createjar
	java -jar dist/Jurm.jar
```


_____________________________________________________________________
#Manual de usuario

##Funciones

Sean n y m registros y q una instrucción cualquiera.
|	Función		| Efecto			|	Descripción			 |
|---------------| ------------------|------------------------|
|	Z(n)		| n := 0			|Hace cero al registro n |
|	T(n, m)		| m := n 			|Transferencia			 |
|	J(n, m, q)	| if n = m goto q 	|Salto condicional		 |
|	S(n)		| n := n + 1		|Suma 					 |

Los paréntesis son opcionales, siempre y cuando se deje al menos un espacio entre la función y el primer argumento, no obstante se aconseja usarlos por legibilidad.


##Registros

La declaración explícita de los registros se hace asignandoles un valor.

```
registro = valor
```

Donde valor es un numero natural (incluido el cero) y registro puede ser un numero natural o una palabra (identificador).
Al ser de registros ilimitados, usted puede usar dentro de su código un registro que no haya inicializado explícitamente, dicho registro tiene el valor 0.



##Instrucciones

Cada instrucción debe estar enumerada, la secuencia de ejecución que seguirá el programa depende de la enumeración.
Además, el intérprete comienza la ejecución en la instrucción 1, si dicha instrucción no fue declarada el programa no iniciará.

```
1: T(numero, 1)
2:
```

##Comentarios
Se pueden ingresar comentarios en el código, cualquier cosa a la derecha de un **#** es considerada como comentario y no será evaluada por el intérprete.

El siguiente ejemplo es un programa sencillo que realiza la suma de dos numeros.

```
# Suma dos números x e y
x = 10
y = 5

1:	T(x, 1)
2:	J(y, auxiliar, 6)
3:		S(auxiliar)
4:		S(1)
5:	J(1, 1, 2)
6:
```

El resultado es el siguiente
```
_Main_
1 = 15
auxiliar = 5
x = 10
y = 5
```

> Por definición el resultado final siempre debe quedar en el registro 1.

##Macros

Jurm soporta macros, es decir, usted puede escribir un programa para algún propósito y reutilizarlo después dentro de otro.

La estructura de una macro no es mas que un bloque de registros e instrucciones encerrados por el mismo identificador, dicho idetificador es el nombre de la macro.
Pueden ser declaradas antes o después de ser usadas en el código.

```
Nombre
	registros...
	instrucciones...
Nombre

Nombre(parámetros) # llamada a la macro
```

> De hecho el ejemplo anterior de la suma de dos numeros también es una macro, dicho bloque de código al no estar encerrado entre un par de identificadores forma parte de la macro principal creada por el interprete llamada **_Main_**. Esta macro es la primera en ejecutarse y es el padre de todas las macros.

Es posible pasar parámetros a las macros cuando son llamadas.
Para que la macro pueda trabajar con los parámetros recibidos es necesario que sean definidos la misma cantidad de registros que de parámetros. Así cuando la macro sea ejecutada estos registros serán inicializados con los valores de los parámetros de entrada.

> Tenga en cuenta que las asignaciones se hacen en el orden en que son pasadas.

Cuando la macro llamada termine su ejecución, el valor de su registro 1 será copiado al registro 1 de la macro que hizo la llamada. Por lo tanto debe procurar que el resultado de la operación de su macro siempre quede en el registro 1.

```
# Suma 1 al registro pasado como argumento
SumaUnoMacro
	X = 0
	
	1:	S(X)
	2:	T(X,1)
SumaUnoMacro

a = 10
1:	SumaUnoMacro(a)
2:
```

El intérprete nos arroja la siguiente salida.

```
_Main_
1 = 11
a = 10
```

Como puede verse, el valor del registro 1 de la macro fue copiado al registro 1 de la macro que hizo la llamada, debe tener esto en cuenta para evitar que los datos se corrompan.

> Cuando esté en el modo ejecución paso a paso, se le mostraran las macros que son llamadas y sus registros.


##Macros de sistema

Además de poder incluir las macros en sus programas, puede hacer que el intérprete las tenga disponibles para su uso en cualquier momento, sin necesidad de copiarlas al programa que las vaya a utilizar, esto se hace poniendo la macro en un archivo y colocando este en la carpeta `data/macros`.

> La carpeta `data/macros` debe estar en la misma carpeta que Jurm, de no encontrarse el programa la creará automáticamente.

Asegúrese de que la primer linea del archivo sea el nombre de la macro, de lo contrario  el intérprete no podrá cargarla.

El intérprete carga al inicio todas las macros de la carpeta, es por eso que un gran número de macros puede ralentizar el arranque del intérprete, se recomienda colocar sólo las de uso mas común.

Si ponemos la macro SumaUnoMacro en un archivo dentro de la carpeta `data/macros` y reiniciamos el intérprete, cuando corremos el siguiente programa el resultado es el mismo del ejemplo anterior.

```
a = 10
1:	SumaUnoMacro(a)
2:
```
```
_Main_
1 = 11
a = 10
```
##¿Saber más?
Para comprender mejor el funcionamiento de Jurm vea lo ejemplos que se encuentran en `data/examples`.

