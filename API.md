# API REST - PRACTICA 1

Documentación  básica de las operaciones soportadas por la API REST de la **Práctica 1 - Tecnologías de Servicios de Internet**


## GET ALL POST INFO

_Obtener un listado con el identificador y el título de cada uno de los posts_

* #### MÉTODO GET

* #### URL:  http://localhost:8080/api/posts

* #### RESPONSE
	```
	{
		"0": {
			"id": 0,
			"title": "Master Cloud Apps"
			}
	}
	```

## GET POST INFO

_Obtener toda la información de un post determinado (comentarios incluídos)_

* #### MÉTODO GET

* #### URL:  http://localhost:8080/api/post/0

* #### RESPONSE
	```
	{
		"id": 0,
		"title": "Master Cloud Apps",
		"body": "Tecnologias de Servicios de Internet",
		"commentList": [
			{
				"id": 0,
				"name": "Carlos",
				"comment": "Comentario de prueba"
			}
		]
	}
	```

## CREATE POST

_Crear un post_

* #### MÉTODO POST

* #### URL:  http://localhost:8080/api/posts

* #### BODY
	```
	{
		"title": "Practica 1",
		"body": "Creacion de post desde API REST"
	}
	```

* #### RESPONSE

	```
	{
		"id": 1,
		"title": "Practica 1",
		"body": "Creacion de post desde API REST",
		"commentList": []
	}
	```

## CREATE COMMENT

_Obtener toda la información de un post determinado (comentarios incluídos)_

* #### MÉTODO POST

* #### URL:  http://localhost:8080/api/post/0/comment

* #### BODY

	```
	{
		"name": "Carlos",
		"comment": "Comentario consumiendo la API REST"
	}
	```

* #### RESPONSE

	```
	{
		"id": 1,
		"name": "Carlos",
		"comment": "Comentario consumiendo la API REST"
	}
	```

## DELETE COMMENT

_Borrar un comentario_

* #### MÉTODO DELETE

* #### URL:  http://localhost:8080/api/post/0/comment/0

* #### RESPONSE

	```
	{
		"id": 0,
		"name": "Carlos",
		"comment": "Comentario de prueba"
	}
	```