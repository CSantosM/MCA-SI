var express = require('express');
var app = express();

var expressWs = require('express-ws')(app);
const uuidv1 = require('uuid/v1');

var amqp = require('amqplib');

const CONN_URL = 'amqp://guest:guest@localhost';
const QUEUE_NAME = 'tasksProgress';

var sendToQueue = require('./producer').sendMessageToQueue;

var id, websocket, taskProgress;
var taskCompleted = true;

app.use(function(req, res, next) {
	res.header('Access-Control-Allow-Origin', '*');
	res.header(
		'Access-Control-Allow-Headers',
		'Origin, X-Requested-With, Content-Type, Accept',
	);
	next();
});
app.use(express.json());

app.post('/tasks', function(req, res) {
	var text = req.body.text;
	console.log('/task endpoint executed');
	console.log('Executing task with text: ', text);

	if (taskCompleted) {
		id = uuidv1();

		res.json({ id: id, text: text, progress: 0, completed: false });

		sendToQueue(text);
	} else {
		res.send('Task is not completed. Wait until complete');
	}
	res.end();
});

app.get('/tasks/:id', (req, res, next) => {
	console.log('/test/id endpoint executed');
	var idParam = req.params.id;
	if (id === idParam) {
		res.json({ progress: taskProgress, id: id, completed: taskCompleted });
	} else {
		res.json({ message: 'TASK NOT FOUND' });
	}
	res.end();
});

app.ws('/notifications', async (ws, req) => {
	console.log('User connected');

	websocket = ws;
});

function connectToRabbitMQ() {
	amqp.connect(CONN_URL).then(conn => {
		conn.createChannel().then(ch => {
			ch.consume(QUEUE_NAME,	msg => {
				if (msg !== null) {
					console.log(msg.content.toString());
					let content = JSON.parse(msg.content);
					taskCompleted = content.completed;
					taskProgress = content.progress;
					if (websocket != null) {
						if (taskCompleted) {
							websocket.send(content.result);
							websocket = null
						} else {
							websocket.send(taskProgress + '%');
						}
					}
				}
			},{noAck: true}
			);
		}).catch(err => reject(err));
	});
}

app.listen(8080, () => {
	connectToRabbitMQ();

	console.log('......................');
	console.log('Listening in port 8080');
});
