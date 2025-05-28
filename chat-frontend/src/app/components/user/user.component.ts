import { Component, EventEmitter, OnInit, Output } from '@angular/core';

interface User {
	userId: string;
	pseudo: string;
	avatar: string;
}

@Component({
  selector: 'app-user',
	standalone: true,
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit {
	user: User | null = null;

	@Output() userRegistered = new EventEmitter<User>();

	ngOnInit(): void {
			// this.loadUser();
	}

	registerUser(pseudo: string) {
		if (!pseudo.trim()) return;

		const userId = 'user-' + Math.random().toString(36).substring(2, 9);
		this.user = {
			userId: userId,
			pseudo: pseudo,
			avatar: ''
		}
	}
}
