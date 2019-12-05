class Comment {
	constructor(nickname, comment, creationDate) {
		this.nickname = nickname;
		this.comment = comment;
		this.creationDate = new Date(creationDate);
		console.log(this.creationDate);
	}

	isValid() {
		return (
			this.nickname != null &&
			this.comment != null &&
			this.creationDate instanceof Date &&
			!isNaN(this.creationDate)
		);
	}
}

module.exports = Comment;
