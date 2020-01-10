# API REST - PRACTICA 2

Documentación  básica de las operaciones soportadas por la API REST de la **Práctica 2 - Tecnologías de Servicios de Internet**


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

* #### URL:  http://localhost:8080/api/post/id

* #### RESPONSE
	```
	{
		"id": 3,
		"title": "Pepe",
		"content": "Contenido de mi post",
		"comments": [
			{
				"id": 4,
				"author": {
					"id": 1,
					"name": "Juanito"
				},
				"comment": "Comentario de prueba"
			},
			{
				"id": 5,
				"author": {
					"id": 2,
					"name": "Jupiter"
				},
				"comment": "Comentario 2"
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
		"content": "Creacion de post desde API REST",
		"comments": []
	}
	```

* #### RESPONSE

	```
	{
		"id": 8,
		"title": "Practica 1",
		"content": "Creacion de post desde API REST",
		"comments": []
	}
	```

## CREATE COMMENT

_Obtener toda la información de un post determinado (comentarios incluídos)_

* #### MÉTODO POST

* #### URL:  http://localhost:8080/api/post/POST_ID/comment/AUTHOR_ID

* #### BODY

	```
	{

		"comment": "Comentario 3"
	}
	```

* #### RESPONSE

	```
	{
		"id": null,
		"author": {
			"id": 2,
			"name": "Jupiter",
			"age": 800
		},
		"comment": "Comentario 3"
	}
	```

## DELETE COMMENT

_Borrar un comentario_

* #### MÉTODO DELETE

* #### URL:  http://localhost:8080/api/post/POST_ID/comment/COMMENT_ID

* #### RESPONSE

	```
	{
		"id": 7,
		"author": {
			"id": 2,
			"name": "Jupiter",
			"age": 800
		},
		"comment": "wwwwwwwww"
	}
	```

## CREATE AUTHOR


* #### MÉTODO POST

* #### URL:  http://localhost:8080/api/authors

* #### BODY

	```
	{
		"name": "Jose Luis",
		"age": "522"
	}
	```

* #### RESPONSE

	```
	{
		"id": 9,
		"name": "Jose Luis",
		"age": 522
	}
	```

## GET AUTHOR COMMENTS


* #### MÉTODO GET

* #### URL:  http://localhost:8080/api/comments/author/AUTHOR_ID

* #### RESPONSE
	```
	[
		{
			"id": 3,
			"comments": [
				{
					"id": 8,
					"comment": "Comentario 3"
				}
			]
		}
	]
	```
