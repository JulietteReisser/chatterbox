import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { User } from "../models/user";

@Injectable({
	providedIn: 'root'
})
export class UserService {
	private userSubject = new BehaviorSubject<User | null>(this.loadUser());
	user$ = this.userSubject.asObservable();

	constructor() {}

	/**
	 * Saves the current user in local storage.
	 * @param user The user object to store.
	 */
	saveUser(user: User): void {
		localStorage.setItem('user', JSON.stringify(user));
		this.userSubject.next(user);
	}

	/**
	 * Loads the user from the local storage.
	 * @returns The user object if found, otherwise null.
	 */
	private loadUser(): User | null {
		const userData = localStorage.getItem('user');
		return userData ? JSON.parse(userData) : null;
	}

	/**
	 * Clears the stored user and logs the user out.
	 */
	logout(): void {
		localStorage.removeItem('user');
		this.userSubject.next(null);
	}
}
