import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Message } from "../models/message";

/**
 * Service responsible for handling chat messages.
 */
@Injectable({
	providedIn: 'root'
})
export class MessageService {
	private apiUrl = 'http://localhost:8080/api/messages';

	constructor(private http: HttpClient) {}

	private get headers() {
		const token = localStorage.getItem('token');
		return new HttpHeaders().set('Authorization', `Bearer ${token}`);
	}

	/**
	 * Retrieves all chat messages.
	 * @returns An observable containing an array of messages.
	 */
	getMessages(): Observable<Message[]> {
		return this.http.get<Message[]>(this.apiUrl, { headers: this.headers });
	}

	/**
	 * Sends a new message.
	 * @param message The message to send.
	 * @returns An observable containing the saved message.
	 */
	sendMessage(message: Message): Observable<Message> {
		return this.http.post<Message>(this.apiUrl, message, { headers: this.headers });
	}

	/**
	 * Clears all messages.
	 * @returns An observable indicating the success of the operation.
	 */
	clearMessages(): Observable<void> {
		return this.http.delete<void>(this.apiUrl, { headers: this.headers });
	}
}
