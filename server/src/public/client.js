//const WebSocket = require('ws');

const axios = require('axios').default;

$(document).ready(function() {
	const urlRest = 'http://localhost:8080';

	let taskId;

	async function createTask() {
		console.log('Creating task through API REST');
		const url = urlRest + '/tasks';
		const body = { text: 'creando tarea desde el cliente en NodeJS' };

		try {

			console.log('TEXT: creando tarea desde el cliente en NodeJS');
			let response = await axios.post(url, body);
			taskId = response.data.id;
			console.log(response.data);
			console.log('ID task received: ', taskId);

		} catch (error) {
			console.error(error);
		}
	}

	async function getTaskInfo() {
		if (taskId != undefined) {
			console.log('Getting task info through API REST');
			const url = urlRest + '/tasks/' + taskId;
			try {
				let response = await axios.get(url);
				console.log(response.data);
			} catch (error) {
				console.error(error);
			}
		} else {
			console.warn('Task not created');
		}
	}

	function subscribeToNotificationTask() {
		let socket = new WebSocket('ws://localhost:8080/notifications');

		console.log(
			'Subscribing to WebSocket to get progress task notifications',
		);

		socket.onopen = e => {
			console.log('WebSocket connection established');
		};

		socket.onmessage = data => {
			console.log('[message]: ' + data.data );
		};

		socket.onclose = event => {
			if (event.wasClean) {
				console.log(
					`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`,
				);
			} else {
				console.log('[close] Connection died');
			}
		};

		socket.onerror = error => {
			console.log(`[error] ${error.message}`);
		};
	}
	$('#createTask').click(createTask);
	$('#getTask').click(getTaskInfo);
	$('#subscribeToNotificationTask').click(subscribeToNotificationTask);
});
//createTask();
