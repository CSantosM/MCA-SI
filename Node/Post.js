class Post {
	constructor(name, nickname, title, content, comments) {
		this.name = name;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.comments = comments;
	}

	isValid() {
		return (
			this.name != null &&
			this.nickname != null &&
			this.title != null &&
			this.content != null &&
			this.comments != null
		);
	}
}

module.exports = Post;
