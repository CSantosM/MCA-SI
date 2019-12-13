const amqp = require('amqplib/callback_api');

const CONN_URL = 'amqp://guest:guest@localhost';

const TASK_QUEUE_NAME = 'newTasks';

let ch = null;

function sendMessageToQueue(text) {
	process.on('exit', code => {
		ch.close();
		console.log(`Closing rabbitmq channel`);
	});

	amqp.connect(CONN_URL, async (err, conn) => {
		if (err != null) console.log(err);
		ch = await conn.createChannel();

		console.log("publishToQueue: '" + text + "'");
		ch.sendToQueue(TASK_QUEUE_NAME, Buffer.from(text));
	});
}

module.exports = {
	sendMessageToQueue,
};
