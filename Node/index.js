var express = require('express');
var db = require('./repository');

var Post = require('./Post');
var Comment = require('./Comment');

var app = express();
app.use(express.json());

app.get('/', async (req, res) => {
	var allPost = await db.getAllPost();
	res.send(allPost);
});

app.get('/:id', async (req, res) => {
	var id = req.params.id;
	try {
		res.send(await db.getPostById(id));
	} catch (error) {
		console.log(error);
		res.status(error.status).send(error.message);
	}
});

app.post('/', async (req, res) => {
	var post = createPost(req.body);
	if (post.isValid()) {
		res.send(await db.addPost(post));
	} else {
		res.status(400).send({
			error: 'Content post was wrong. Post received is not a Post.',
		});
	}
});

app.delete('/:id', async (req, res) => {
	var id = req.params.id;
	try {
		res.send(await db.deletePostById(id));
	} catch (error) {
		console.log(error);
		res.status(error.status).send(error.message);
	}
});

app.put('/:id', async (req, res) => {
	var id = req.params.id;
	var post = createPost(req.body);
	if (post.isValid()) {
		try {
			res.send(await db.updatePostById(id, post));
		} catch (error) {
			res.status(500).send('Error updating post');
		}
	} else {
		res.status(400).send({
			error: 'Content post was wrong. Post received is not a Post.',
		});
	}
});

app.post('/:id/comments', async (req, res) => {
	var id = req.params.id;
	var comment = createComment(req.body);
	if (comment.isValid()) {
		try {
			res.send(await db.addComment(id, req.body));
		} catch (error) {
			res.status(500).send('Error adding comment');
		}
	} else {
		res.status(400).send({
			error: 'Content post was wrong. Post received is not a Post.',
		});
	}
});

app.delete('/:postId/comments/:commentId', async (req, res) => {
	var postId = req.params.postId;
	var commentId = req.params.commentId;
	res.send(await db.deleteComment(postId, commentId));
});

function createPost(body) {
	return new Post(
		body.name,
		body.nickname,
		body.title,
		body.content,
		body.comments,
	);
}

function createComment(body) {
	return new Comment(body.nickname, body.comment, body.creationDate);
}

var server = app.listen(8080, () => {
	console.log('......................');

	db.initBD();

	console.log('Listening in port 8080');
});
