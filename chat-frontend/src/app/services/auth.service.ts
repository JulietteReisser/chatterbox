import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface AuthResponse {
	token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
	private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

	/**
	 * Registers a new user with the provided email and password.
	 * @param email The user's email address.
	 * @param password The user's password.
	 * @returns An observable containing the authentication response, including a token.
	 */
	register(email: string, password: string): Observable<AuthResponse> {
		return this.http.post<AuthResponse>(`${this.apiUrl}/register`, { email, password });
	}

	/**
	 * Authenticates the user with the provided email and password.
	 * @param email The user's email address.
	 * @param password The user's password.
	 * @returns An observable containing the authentication response, including a token.
	 */
	login(email: string, password: string): Observable<AuthResponse> {
		return this.http.post<AuthResponse>(`${this.apiUrl}/login`, { email, password });
	}
}
