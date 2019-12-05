var MongoClient = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectID;

var url = 'mongodb://localhost:27017/mydb';

var myDB, postCollection, commentCollection;

var POST_COLLECTION = 'posts';

function initBD() {
	MongoClient.connect(
		url,
		{
			useUnifiedTopology: true,
			useNewUrlParser: true,
		},
		async (err, db) => {
			if (err) throw err;
			console.log('Database created!');

			myDB = db.db('mydb');

			postCollection = await myDB.createCollection(POST_COLLECTION);
		},
	);
}

async function getAllPost() {
	return await postCollection.find({}).toArray();
}

async function addPost(post) {
	try {
		await postCollection.insertOne(post);
		return post;
	} catch (error) {
		throw error;
	}
}

async function getPostById(id) {
	var query = { _id: new ObjectId(id) };
	try {
		var post = await postCollection.findOne(query);
		if (post != null) {
			return post;
		}
		throw { status: 404, message: 'Post not found' };
	} catch (error) {
		throw error;
	}
}

async function deletePostById(id) {
	try {
		var post = await getPostById(id);
		await postCollection.deleteOne(post);
		return post;
	} catch (error) {
		throw error;
	}
}

async function updatePostById(id, post) {
	try {
        var query = { _id: new ObjectId(id) };
        var newValues = { $set: post };
		await postCollection.updateOne(query, newValues);
		return post;
	} catch (error) {
		throw error;
	}
}

async function addComment(postId, comment) {
	try {
        var query = { _id: new ObjectId(postId) };
		var newValues = { $push: { comments: comment } };
		var post = await getPostById(postId);
		comment._id = new ObjectId();
		await postCollection.updateOne(post, newValues);
		return comment;
	} catch (error) {
		throw error;
	}
}

async function deleteComment(postId, commentId) {
	var query = { _id: new ObjectId(postId) };
	var newValues = { $pull: { comments: { _id: new ObjectId(commentId) } } };
	try {
		var post = await getPostById(postId);
        await postCollection.updateOne(post, newValues);
        return null;
	} catch (error) {
		throw error;
	}
}

module.exports = {
	initBD,
	getAllPost,
	addPost,
	deletePostById,
	getPostById,
	updatePostById,
	addComment,
	deleteComment,
};
